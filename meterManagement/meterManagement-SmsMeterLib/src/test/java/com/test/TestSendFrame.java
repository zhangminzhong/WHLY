package com.test;

import org.whut.meterManagement.smsmeterlib.send.SendFrame;

/**
 * Created by zhang_minzhong on 2016/12/13.
 */
public class TestSendFrame {
    public static void main(String[] args) {

        System.out.println(getSendFrame());
    }
    public static String getSendFrame(){
        SendFrame sf = new SendFrame();
        sf.setMeterID("1049721501423");
        sf.setFuncCode((byte)0x23);
        sf.setFrameID((byte)1);//帧id
        sf.addParam((double)2,2);//基本单价
        sf.addParam((double)2.5,2);//气价1
        sf.addParam(200,2,true);//起始量1
        sf.addParam((double)3,2);//气价2
        sf.addParam(300,2,true);//起始量2
        sf.addParam((double)3.5,2);//气价3
        sf.addParam(400,2,true);//起始量3
        sf.addParam((double)1000,4);//开通金额
        byte[] key = new byte[16];
        for(int i=1;i<=16;i++){
            key[i-1] = (byte)i;
        }
        sf.addParam(key);//新密钥数组
        sf.addParam(20,1);//起始年1
        sf.addParam(16,1);//起始年2
        sf.addParam(9,1);//起始月
        sf.addParam(1,1);//起始日
        sf.addParam(30,1);//周期长度
        sf.addParam(0,1);//表具运行周期
        sf.addParam(28,1);//抄表日
        sf.addParam(200,2,true);//本周期量
        sf.addParam(450,2,true);//上周期量
        StringBuffer sb = new StringBuffer();
        String keyStr = "";
        for(int i=1;i<=16;i++){
            String temp = Integer.toHexString(i);
            if(temp.length()<2)
                temp = "0"+temp;
            keyStr +=temp;
        }
        sf.ProcFrame(sb,keyStr);
        return sb.toString();
    }
}
