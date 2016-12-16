package com.test;

import org.whut.meterManagement.smsmeterlib.receive.MeterStatus;
import org.whut.meterManagement.smsmeterlib.receive.ReceiveFrame;

/**
 * Created by zhang_minzhong on 2016/12/14.
 */
public class TestReceiveFrame {
//123
    public static void main(String[] args) {
        String sendFrame = TestSendFrame.getSendFrame();
        System.out.println("短信："+sendFrame);
        String keyStr = "";
        for(int i=1;i<=16;i++){
            String temp = Integer.toHexString(i);
            if(temp.length()<2)
                temp = "0"+temp;
            keyStr +=temp;
        }
        ReceiveFrame rf = new ReceiveFrame();
        //rf.ParseFrom(sendFrame);
        rf.ParseFrom(sendFrame,keyStr);
        //System.out.println(rf.ParseFrom(sendFrame,keyStr));
        System.out.println("命令码："+rf.getFuncCode());
        System.out.println("表号："+rf.getMeterID());
        System.out.println("帧id："+rf.getFrameID());
        System.out.println("表具状态字符串dataStr（表号之后，帧id之前的一段）："+rf.getDataStr());
        MeterStatus meterStatus = rf.MeterST;
        System.out.println("表号："+meterStatus.getMeterID());
    }
}
