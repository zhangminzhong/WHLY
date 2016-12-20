package org.whut.meterManagement.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhang_minzhong on 2016/12/20.
 */
public class DateUtil {
    public static Timestamp createDate(String s){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            System.out.println("日起产生出错！");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
