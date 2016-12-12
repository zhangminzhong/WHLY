package org.whut.meterManagement.smsmeterlib.smsprocess;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-12-12
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */

/// <summary>
/// 表具数据类，存储表具回传帧中表具状态数据
/// </summary>
public class MeterStatus {
    private String meterID;
    private double remainMoney;
    private int meterRead;
    private double price;
    private byte xtzt;

    private Date meterTime;

    private int amount1;
    private int amount2;
    private int amount3;
    private int sumamount;
    private int presumamount;


    private Date createDate(String s){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            System.out.println("日起产生出错！");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return date;
    }

    /// <summary>
    /// 构造方法
    /// </summary>
    public MeterStatus() {
        meterID = "";
        meterTime = createDate("2000-01-01 00:00:00");
        //System.out.println(df.format(meterTime));
    }

    /// <summary>
    /// 构造方法。重载1
    /// </summary>
    /// <param name="mid">表具编号</param>
    public MeterStatus(String meterID){
        this.meterID = meterID;
        meterTime = createDate("2000-01-01 00:00:00");
    }

    /// <summary>
    /// 构造方法。重载2
    /// </summary>
    /// <param name="mid">表具编号</param>
    /// <param name="dataStr">表具状态数据字符串</param>
    public MeterStatus(String meterID,String dataStr){
        this.meterID = meterID;
        getFromStr(dataStr);
    }

    public Date getMeterTime() {
        return meterTime;
    }

    public void setMeterTime(Date meterTime) {
        this.meterTime = meterTime;
    }

    public int getAmount1() {
        return amount1;
    }

    public void setAmount1(int amount1) {
        this.amount1 = amount1;
    }

    public int getAmount2() {
        return amount2;
    }

    public void setAmount2(int amount2) {
        this.amount2 = amount2;
    }

    public int getAmount3() {
        return amount3;
    }

    public void setAmount3(int amount3) {
        this.amount3 = amount3;
    }

    public int getPresumamount() {
        return presumamount;
    }

    public void setPresumamount(int presumamount) {
        this.presumamount = presumamount;
    }

    public int getSumamount() {
        return sumamount;
    }

    public void setSumamount(int sumamount) {
        this.sumamount = sumamount;
    }

    private void getFromStr(String dataStr) {
        remainMoney = 0.0;
        meterRead = 0;
        price = 0.00;
        amount1 = amount2 = amount3 = sumamount = presumamount = 0;
        if(dataStr.length()<=2){
            xtzt = (byte) Integer.parseInt(dataStr.substring(0,2),16);
        }
        if(dataStr.length()<10)
            return;
        remainMoney += Integer.parseInt(dataStr.substring(0,2),16)*10000;
        remainMoney += Integer.parseInt(dataStr.substring(2,4),16)*100;
        remainMoney += Integer.parseInt(dataStr.substring(4,6),16);
        remainMoney += Integer.parseInt(dataStr.substring(6,8),16)*0.01;

        meterRead += Integer.parseInt(dataStr.substring(8,10),16)*1000000;
        meterRead += Integer.parseInt(dataStr.substring(10,12),16)*1000;
        meterRead += Integer.parseInt(dataStr.substring(12,14),16)*100;
        meterRead += Integer.parseInt(dataStr.substring(14,16),16);
        xtzt = (byte) Integer.parseInt(dataStr.substring(16,18),16);
        if(dataStr.length()>=92){////数据字符串包含46个字节数据,统一回传帧数据
            price += Integer.parseInt(dataStr.substring(22,24),16);
            price += Integer.parseInt(dataStr.substring(24,26),16)*0.01;

            amount1 += Integer.parseInt(dataStr.substring(26,28),16)*100;
            amount1 += Integer.parseInt(dataStr.substring(28,30),16);
            amount2 += Integer.parseInt(dataStr.substring(30,32),16)*100;
            amount2 += Integer.parseInt(dataStr.substring(32,34),16);
            amount3 += Integer.parseInt(dataStr.substring(34,36),16)*100;
            amount3 += Integer.parseInt(dataStr.substring(36,38),16);
            sumamount += Integer.parseInt(dataStr.substring(38,40),16)*100;
            sumamount += Integer.parseInt(dataStr.substring(40,42),16);
            presumamount += Integer.parseInt(dataStr.substring(18, 20), 16)*100;
            presumamount += Integer.parseInt(dataStr.substring(20,22),16);
            //取得表具时间
            Long longTime = Long.parseLong(dataStr.substring(42,50),16);
            Calendar c = Calendar.getInstance();
            c.setTime(meterTime);
            long millis = c.getTimeInMillis();
            c.setTimeInMillis(millis+longTime);
            meterTime = c.getTime();
        }
    }

    public static void main(String[] args) {
        new MeterStatus();
    }

}