package com.test;

import org.whut.meterManagement.smsmeterlib.enums.ValveCtrStyle;
import org.whut.meterManagement.smsmeterlib.frames.SMC;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhang_minzhong on 2016/12/13.
 */
public class TestSendFrame {
    public static void main(String[] args) {
        System.out.println(getSendFrame());
    }

    public static String getSendFrame() {
        String keyStr = "";
        for (int i = 1; i <= 16; i++) {
            String temp = Integer.toHexString(i);
            if (temp.length() < 2)
                temp = "0" + temp;
            keyStr += temp;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse("2016-9-1 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(d.getTime());
        String message;
        //message = SMC.getMeterOpenFrame("1049721501423",keyStr,(byte)1,(double)1000,(double)2,(double)2.5,(double)3,(double)3.5,200,300,400,keyStr,timestamp,(byte)30,(byte)28,200,450);
        //message = SMC.getChangeMoneyFrame("1049721501423", keyStr, 1, (double) 1000, 0);
        //message = SMC.getMeterDataFrame("1049721501423", keyStr, (byte) 1, 0);
//        Timestamp atDT = null;
//        try {
//            atDT = new Timestamp(sdf.parse("2016-12-22 00:00:00").getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        message = SMC.getChangePriceFrame("1049721501423", keyStr, (byte) 1, (double) 2, 2.5, (double) 3, 3.5, 200, 300, 400, timestamp, (byte) 30, atDT, 0);
        //message = SMC.getChangeOverdraftFrame("1049721501423", keyStr, (byte) 1, 0, 0);
        //message = SMC.getChangeServerNoFrame("1049721501423", keyStr, (byte) 1, "13237109543", 0);
        message = SMC.getValveControlFrame("1049721501423", keyStr, (byte) 1, ValveCtrStyle.定时关闭, timestamp, 0);

        return message;
    }
}
