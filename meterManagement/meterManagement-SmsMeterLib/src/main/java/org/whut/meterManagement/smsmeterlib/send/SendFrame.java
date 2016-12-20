package org.whut.meterManagement.smsmeterlib.send;

import org.whut.meterManagement.aes256.AES;
import org.whut.meterManagement.smsmeterlib.base.CommandFrame;
import org.whut.meterManagement.smsmeterlib.base.FrameParam;
import org.whut.meterManagement.smsmeterlib.enums.FrameDirection;
import org.whut.meterManagement.smsmeterlib.enums.FrameResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang_minzhong on 2016/12/13.
 */

/// <summary>
/// 发送命令帧类型，用于组成发送短信命令帧
/// </summary>
public class SendFrame extends CommandFrame {
    private boolean V2 = false;
    private long timeCorrection;//表具时间调整值

    public long getTimeCorrection() {
        return timeCorrection;
    }

    public void setTimeCorrection(long timeCorrection) {
        this.timeCorrection = timeCorrection;
        setV2();
    }

    public void setV2() {
        V2 = true;
    }

    //构造方法


    public SendFrame() {
        super();
        frmDirection = FrameDirection.REQUEST;
        frmResult = FrameResult.SUCCESS;
    }

    /// <summary>
    /// 加入整数类型区域，用于生成帧
    /// </summary>
    /// <param name="value">整数值</param>
    /// <param name="len">在帧中的长度</param>
    public void addParam(int value, int len)
    {
        FrameParam fp = new FrameParam();
        fp.SetValue(value, len);
        ParamList.add(fp);
    }

    /// <summary>
    /// 加入整数类型区域，是否按照字节间100进制的方式转换
    /// </summary>
    /// <param name="value"></param>
    /// <param name="len"></param>
    /// <param name="sjz">false:不转换，同AddParam(int,int)方法，true转换，将输入整数转换个len长度的字节数组，每个字节间100倍数关系</param>
    public void addParam(int value, int len, boolean sjz)
    {
        if (sjz)
        {
            byte[] d = new byte[len];
            Integer v = value;
            String str = v.toString();
            while(str.length()<len*2) {
                str = "0" + str;
            }
            for (int i = 0; i < len; i++)
            {
                d[i] = (byte) Integer.parseInt(str.substring(i*2,i*2+2));

            }
            addParam(d);
        }
        else
        {
            addParam(value, len);
        }
    }

    /// <summary>
    /// 加入浮点数区域，用与生成帧
    /// </summary>
    /// <param name="value">浮点数值</param>
    /// <param name="len">在帧中的长度</param>
    public void addParam(double value, int len)
    {
        FrameParam fp = new FrameParam();
        fp.SetValue(value, len);
        ParamList.add(fp);
    }

    /// <summary>
    /// 加入字符串区域，用于生成帧
    /// </summary>
    /// <param name="str">字符串值</param>
    public void addParam(String str)
    {
        FrameParam fp = new FrameParam();
        fp.SetValue(str, str.length());
        ParamList.add(fp);
    }

    /// <summary>
    /// 加入byte数组区域，用于生成帧
    /// </summary>
    /// <param name="bta"></param>
    public void addParam(byte[] bta)
    {
        FrameParam fp = new FrameParam();
        fp.SetValue(bta, bta.length);
        ParamList.add(fp);
    }

    /// <summary>
    /// 根据各项已设置的属性生成帧
    /// </summary>
    /// <param name="buff">生产的帧字节数组</param>
    /// <returns>生成结果，等于0表示执行失败，否则返回帧字节数组长度</returns>
    protected int MakeBuff(List<Byte> buff)
    {
        byte[] tmpA = new byte[64];
        tmpA[0] = 0x68;
        tmpA[1] = (byte)(funcCode + (frmDirection.ordinal()) * 128 + (frmResult.ordinal()) * 64);
        //长度L
        tmpA[2] = 13;
        //System.out.print(tmpA[2] + " ");
        //将表号加入到tmpA
        for (int i = 0; i < 13; i++)
        {
            tmpA[3 + i] = (byte) meterID.charAt(i);//Convert.ToByte(meterID[i]);
        }

        int pz = 16;        //tmpA下标位置

       // 处理参数对象
        for (int i = 0; i < ParamList.size(); i++)
        {
            FrameParam fp = ParamList.get(i);
            //System.out.print(fp.getByteLen()+" ");
            tmpA[2] += (byte)fp.getByteLen();
            //System.out.print(tmpA[2]+" ");
            switch (fp.PT)
            {
                case INT: //整数，将int类型转成16进制字符串（长度是int类型*2），再转成4个字节的对应字节数组
                    int iva=0;
                    iva = fp.GetValue(iva);
                   // System.out.println(iva);
                    //String stmp = iva.ToString("X" + (fp.getByteLen() * 2).ToString());
                    String stmp = Integer.toHexString(iva);

                    while(stmp.length()<fp.getByteLen()*2){
                        stmp = "0"+stmp;
                    }
                    //System.out.println(stmp);
                    for (int j = 0; j < fp.getByteLen(); j++)
                    {
                        String ss = stmp.substring(j * 2, j * 2 + 2);
                        tmpA[pz] = (byte) Integer.parseInt(ss,16);
                        pz++;
                        //System.out.println(tmpA[pz]);
                    }
                    break;
                case FLT: //浮点数
                    double dva=0.0;
                    dva = fp.GetValue(dva);
                    //stmp = string.Format("{0:000000.00}", dva);
                    stmp = new DecimalFormat("000000.00").format(dva);
                    //System.out.println(stmp);
                    //stmp = stmp.Remove(6, 1);
                    stmp = stmp.substring(0,6)+stmp.substring(7);
                    //System.out.println(stmp);
                    //stmp = stmp.Remove(0, (4 - fp.ByteLen) * 2);
                    stmp = stmp.substring((4 - fp.getByteLen()) * 2);
                    //System.out.println(stmp);
                    for (int j = 0; j < fp.getByteLen(); j++)
                    {
                        String ss = stmp.substring(j * 2, j * 2 + 2);
                        //tmpA[pz] = Convert.ToByte(ss);
                        tmpA[pz] = (byte) Integer.parseInt(ss);
                        pz++;
                    }
                    break;
                case STR: //字符串
                    String sva="";
                    sva = fp.GetValue(sva);
                    for (int j = 0; j < fp.getByteLen(); j++)
                    {
                        //tmpA[pz] = Convert.ToByte(sva[j]);
                        tmpA[pz] = (byte) sva.charAt(j);
                        //System.out.println(tmpA[pz]);
                        pz++;
                    }
                    break;
                case BTA:
                    byte[] bva=null;
                    bva = fp.GetValue(bva);

                    for (int j = 0; j < fp.getByteLen(); j++)
                    {
                        tmpA[pz] = bva[j];
                        //System.out.println(tmpA[i]);
                        pz++;
                    }
                    break;
            }
        }
        //增加时间时间调整参数，总共5个字节
        ///第1个字节为控制字节，其中第1位（最高位）表示是否启用，0：不启用，1：启用
        ///第2位表示调整方向，0：增加；1：递减
        ///第2~5字节为调整时间值
        if (V2)
        {
            //System.out.println("V2");
            tmpA[2] += 5;
            if (timeCorrection == 0)
            {
                for (int i = 0; i < 5; i++)
                {
                    tmpA[pz] = 0;
                    pz++;
                }
            }
            else
            {
                if (timeCorrection > 0)
                {
                    tmpA[pz] = (byte) 128; //累加
                    pz++;
                    //String stmp = timeCorrection.toString("X8");
                    String stmp = Long.toHexString(timeCorrection);
                    while(stmp.length()<8){
                        stmp = "0"+stmp;
                    }
                    for (int i = 0; i < 4; i++)
                    {
                        //tmpA[pz] = Convert.ToByte("0x" + stmp.Substring(i * 2, 2), 16);
                        tmpA[pz] = (byte) Integer.parseInt(stmp.substring(i*2,i*2+2),16);
                        pz++;
                    }
                }
                else
                {
                    tmpA[pz] = (byte) 192; //递减
                    pz++;
                    //String stmp = (timeCorrection * -1).ToString("X8");
                    String stmp = Long.toHexString(timeCorrection * -1);
                    while(stmp.length()<8){
                        stmp = "0"+stmp;
                    }
                    for (int i = 0; i < 4; i++)
                    {
                        //tmpA[pz] = Convert.ToByte("0x" + stmp.Substring(i * 2, 2), 16);
                        tmpA[pz] = (byte) Integer.parseInt(stmp.substring(i*2,i*2+2),16);
                        pz++;
                    }
                }
            }
        }

        //帧ID
        tmpA[2] += 1;
        //System.out.println(tmpA[2]);
        tmpA[pz] = frameID;
        //System.out.println(frameID);
        //计算校验和
        int cs = 0;
        for (int j = 0; j <= pz; j++)
        {
            cs += Byte.toUnsignedInt(tmpA[j]);
        }
        cs = cs % 256;
        //System.out.println(cs);
        pz++;
        tmpA[pz] = (byte)cs;
        pz++;
        //截至码
        tmpA[pz] = 0x16;

        //buff = new byte[pz + 1];
        for (int i = 0; i <= pz; i++)
        {
            //System.out.println(tmpA[2]);
            //buff[i] = tmpA[i];
            buff.add(tmpA[i]);
            //System.out.print(tmpA[i] + " ");
        }
        //System.out.println();

        return buff.size();
    }

    /// <summary>
    /// 获取BYTE数组组成的帧
    /// </summary>
    /// <returns></returns>
    public byte[] ByteFrame()
    {
        //byte[] buff=null;
        List<Byte> buff = new ArrayList<Byte>();
        if (MakeBuff(buff) == 0)
        {
            byte[] b = new byte[1];
            b[0] = 0;
            return b;
        }
        byte[] rst = new byte[buff.size() - 2];
        for (int i = 1; i < buff.size() - 1; i++)
            rst[i - 1] = buff.get(i);
        //System.out.println();
        return rst;
    }

    /// <summary>
    /// 生成帧命令字符串
    /// </summary>
    /// <param name="SMS">命令字符串</param>
    /// <returns>成功返回true，失败返回false</returns>
    public boolean ProcFrame(StringBuffer SMS)
    {
        //SMS.append("");
        if (funcCode == 0)
        {
            return false;
        }
        if (meterID == "")
        {
            return false;
        }

        //byte[] buff = null;
        List<Byte> buff = new ArrayList<Byte>();
        if (MakeBuff(buff) == 0)
        {
            return false;
        }

        //开始对帧字节数组进行处理，生成字符串帧
        SMS.append("h");  //帧头
        String s1 = Integer.toHexString(Byte.toUnsignedInt(buff.get(1)));
        String s2 = Integer.toHexString(Byte.toUnsignedInt(buff.get(2)));
        if(s1.length()<2)
            s1 = "0"+s1;
        if(s2.length()<2)
            s2 = "0"+s2;
        SMS.append(s1+s2); //+= buff[1].ToString("X2") + buff[2].ToString("X2"); //帧命令码F与数据长度L
        for (int i = 3; i < 16; i++)
        {
            int k = Byte.toUnsignedInt(buff.get(i));//SMS += Convert.ToChar(buff[i]);
            char c = (char)k;
            SMS.append(c);

        }
        for (int i = 16; i < buff.size(); i++)
        {
            String s = Integer.toHexString(Byte.toUnsignedInt(buff.get(i)));
            if(s.length()<2){
                s = "0"+s;
            }
            SMS.append(s);
        }
        return true;
    }

    /// <summary>
    /// 生成帧命令字符串，带加密
    /// </summary>
    /// <param name="SMS"></param>
    /// <param name="sKey"></param>
    /// <returns></returns>
    public boolean ProcFrame(StringBuffer SMS, String sKey)
    {
        //SMS.append("");
        if (funcCode == 0)
        {
            return false;
        }
        if (meterID == "")
        {
            return false;
        }

        byte[] frame = ByteFrame();
        byte[] key = getKey(sKey);

       /*System.out.print("加密前帧字节数组：");
        for(int i=0;i<frame.length;i++){
            System.out.print(frame[i]+" ");
        }
        System.out.println("字节长度："+frame.length);
        System.out.print("密钥：");
        for(int i=0;i<key.length;i++){
            System.out.print(key[i]+" ");
        }
        System.out.println();*/

        //加密
        byte[] buff = new byte[0];
        try {
            buff = AES.encrypt(frame, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //生成字符串
        SMS.append("h");
        String buffLen = Integer.toHexString(buff.length);
        if(buffLen.length()<2)
            buffLen = "0" + buffLen;
        buffLen.toUpperCase();
        SMS.append(buffLen);
        //System.out.print("加密字符串：");
        for (int i = 0; i < buff.length; i++)
        {
            //SMS = SMS + buff[i].ToString("x2").ToUpper();
            String s = Integer.toHexString(Byte.toUnsignedInt(buff[i])).toUpperCase();
            if(s.length()<2) {
                s = "0" + s;
            }
            //System.out.print(s);
            SMS.append(s);
        }
        //System.out.println();
        SMS.append("16");
        System.out.println("加密后的字符串: " + SMS.toString());
        return true;
    }
}
