package org.whut.meterManagement.smsmeterlib.frame;

import org.whut.meterManagement.aes256.AES;
import org.whut.meterManagement.hex.Hex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenfu on 2016/12/9.
 * <p/>
 * 帧解析类，解析收到的字符串帧
 */
public class ReceiveFrame extends CommandFrame {

    protected String dataStr;
    private List<CFunction> aryFunc;
    private int funcCount;

    public List<CFunction> getAryFunc() {
        return aryFunc;
    }

    public int getFuncCount() {
        return funcCount;
    }

    /**
     * 帧中的数据域字符串
     *
     * @return
     */
    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    /**
     * 表具状态数据对象
     */
    public MeterStatus MeterST;

    /**
     * PDA设备数据类型
     */
    public PDAInfo PDA;

    /**
     * 表具统一上报码
     */
    public byte reportCode;

    public byte getReportCode() {
        return reportCode;
    }

    public void setReportCode(byte reportCode) {
        this.reportCode = reportCode;
    }

    /**
     * 构造方法
     */
    public ReceiveFrame() {
        dataStr = "";
        aryFunc = new ArrayList<CFunction>();
        MeterST = new MeterStatus();
        PDA = new PDAInfo();
    }

    /**
     * 对字符串帧进行解析，将解析后数据放到对象字段，同时可判断帧是否合法
     *
     * @param SMS 字符串帧
     * @return 成功解析帧，返回true，失败返回false
     */
    public boolean ParseFrom(String SMS) {
        //帧头判断
        if (!SMS.substring(0, 1).toUpperCase().equals("H")) {
            return false;
        }
        //取得命令码
        try {
            funcCode = (byte) Integer.parseInt(SMS.substring(1, 3), 16);
        } catch (Exception e) {
            return false;
        }
        //取得数据区域长度
        int dataLen = 0;
        //如果为统一回传帧，dataLen直接等于=0x3C
        if (funcCode == 0x3E) {
            dataLen = 0x3C;
        } else {
            try {
                dataLen = Integer.valueOf(SMS.substring(3, 5), 16);
            } catch (Exception e) {
                return false;
            }
        }
        //判断截止码是否为16
        try {
            if (!SMS.substring(dataLen * 2 - 6, dataLen * 2 - 4).equals("16")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        //表具编号（或设备编号）
        meterID = SMS.substring(5, 18);
        //数据区域
        dataStr = SMS.substring(18, 18 + (dataLen - 14) * 2);
        //帧ID FrameID
        try {
            int begin = 18 + getDataStr().length();
            frameID = (byte) Integer.parseInt(SMS.substring(begin, 2 + begin), 16);
        } catch (Exception e) {
            return false;
        }
        //校验和
        int CS = 0;
        try {
            int begin = 20 + getDataStr().length();
            CS = Integer.valueOf(SMS.substring(begin, 2 + begin), 16);
        } catch (Exception e) {
            return false;
        }
        //结束符 0x16
        int begin = 22 + getDataStr().length();
        if (!SMS.substring(begin, 2 + begin).equals("16")) {
            return false;
        }

        //对命令码进行分解；命令码说明： D7：传送方向，1表示回传，0表示起始帧；；D6:执行结果，0表示正常；1表示异常; D5-D0:功能码
        int itmp = funcCode / 0x40;
        if (itmp == 0) {
            frmDirection = FrameDirection.REQUEST;
            frmResult = FrameResult.SUCCESS;
        } else if (itmp == 2) {
            frmDirection = FrameDirection.REPLY;
            frmResult = FrameResult.SUCCESS;
        } else if (itmp == 3) {
            frmDirection = FrameDirection.REPLY;
            frmResult = FrameResult.FAIL;
        }
        funcCode = (byte) (funcCode % 64);

        ParseDataStr(); //对数据域进行解析
        return true;
    }

    /**
     * 对字符串帧进行解析，将解析后数据放到对象字段，同时可判断帧是否合法
     * @param SMS  短信内容
     * @param sKey  表具密钥
     * @return
     */
    public boolean ParseFrom(String SMS, String sKey) throws Exception {
        //帧头判断
        if (!SMS.substring(0, 1).toUpperCase().equals("H")) {
            return false;
        }
        //取得数据长度
        String str = SMS.substring(1, 3);
        int L = Integer.valueOf(str, 16);
        //判断截止字符串 16
        if (!SMS.substring(L * 2 + 3, L * 2 + 5).equals("16")) {
              return false;
        }
        //取得加密字符串
        str = SMS.substring(3, 3 + L * 2);
        byte[] frame = new byte[L];
        for (int i = 0; i < L; i++) {
            frame[i] = (byte) Integer.parseInt(str.substring(i*2, i*2 + 2), 16);
        }
        //解密
        byte[] key = getKey(sKey);
        byte[] buff = AES.decrypt(frame, key);

        //将解密后的明文转换为先前版本的字符串帧
        str = "h" + Hex.encode2(buff[0]) + Hex.encode2(buff[1]);
        for (int i = 2; i < 15; i++) {
            str += (char) buff[i];
        }
        for (int i = 15; i < buff.length; i++) {
             str += Hex.encode2(buff[i]);
        }
        str += "16";

        //调用ParseFrom函数，解析帧
        return ParseFrom(str);
    }

    private void ParseDataStr() {
        MeterST.setMeterID(meterID);
        MeterST.getFromStr(dataStr);
        if (funcCode == 0x3E) {
            //统一回传帧
            funcCount = Integer.parseInt(dataStr.substring(58, 60), 16);
            if (funcCount > 8) {
                funcCount = 8;
            }
            for (int i = 0; i < funcCount; i++) {
                CFunction cf = new CFunction();
                cf.setCode((byte) Integer.parseInt(dataStr.substring(60 + i * 4, 62 + i * 4),16));
                cf.setFid((byte) Integer.parseInt(dataStr.substring(62 + i * 4, 64 + i * 4),16));
                aryFunc.add(cf);
            }
        }
        if (funcCode == 0x3D) {
            //统一上报帧
            try {
                reportCode = (byte) Integer.parseInt(dataStr.substring(60, 62), 16);
            } catch (Exception e) {
                reportCode = 0;
            }
        }
        PDA.setPdaID(meterID);
        PDA.getFromStr(dataStr, funcCode);
    }
}