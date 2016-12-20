package org.whut.meterManagement.smsmeterlib.receive;

import org.whut.meterManagement.date.DateUtil;

import java.sql.Timestamp;

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

    private byte XTSJC;
    private byte FMWZ;
    private byte TZZT;
    private byte FMCW;
    private byte CGGZ;

    private Timestamp meterTime;

    private int amount1;
    private int amount2;
    private int amount3;
    private int sumamount;
    private int presumamount;




   /* private Timestamp createDate(String s){
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
    }*/

    /// <summary>
    /// 构造方法
    /// </summary>
    public MeterStatus() {
        meterID = "";
        meterTime = DateUtil.createDate("2000-01-01 00:00:00");
        //System.out.println(meterTime);
        //System.out.println(df.format(meterTime));
    }

    /// <summary>
    /// 构造方法。重载1
    /// </summary>
    /// <param name="mid">表具编号</param>
    public MeterStatus(String meterID){
        this.meterID = meterID;
        meterTime = DateUtil.createDate("2000-01-01 00:00:00");
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

    /// <summary>
    /// 表具编号.
    /// </summary>
    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    /// <summary>
    /// 表具剩余金额。
    /// </summary>


    public double getRemainMoney() {
        if ((Byte.toUnsignedInt(xtzt) / 64) % 2 == 1)
        {
            return remainMoney * -1;
        }
        else
        {
            return remainMoney;
        }
    }

    /// <summary>
    /// 表具读数。基表止码
    /// </summary>


    public int getMeterRead() {
        return meterRead;
    }

    public void setMeterRead(int meterRead) {
        this.meterRead = meterRead;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /// <summary>
    /// 系统状态字节
    /// </summary>
    public byte getXtzt() {
        return xtzt;
    }

    public void setXtzt(byte xtzt) {
        this.xtzt = xtzt;
    }

    /// <summary>
    /// 传感故障
    /// </summary>
    public byte getCGGZ() {
        int i = Byte.toUnsignedInt(xtzt) / 4;
        i = i % 2;
        return (byte)i;
    }

    public void setCGGZ(byte CGGZ) {
        this.CGGZ = CGGZ;
    }

    /// <summary>
    /// 阀门位置。  0：正常；    1：错误
    /// </summary>
    public byte getFMCW() {
        int i = Byte.toUnsignedInt(xtzt) / 2;
        i %= 2;
        return (byte)i;
    }

    public void setFMCW(byte FMCW) {
        this.FMCW = FMCW;
    }

    /// <summary>
    /// 阀门位置,0表示阀门开，1表示阀门关
    /// </summary>
    public byte getFMWZ() {
        int i = Byte.toUnsignedInt(xtzt)%2;
        return (byte) i;
    }

    public void setFMWZ(byte FMWZ) {
        this.FMWZ = FMWZ;
    }

     /// <summary>
    /// 表具透支状态。0：正常状态；  1：透支状态
    /// </summary>
    public byte getTZZT() {
        int i = Byte.toUnsignedInt(xtzt) / 64;
        i = i % 2;
        return (byte)i;
    }

    public void setTZZT(byte TZZT) {
        this.TZZT = TZZT;
    }

    /// <summary>
    /// 系统数据错 0：正常；  1：错误
    /// </summary>
    public byte getXTSJC() {
        int i = Byte.toUnsignedInt(xtzt)/128;
        return (byte) i;
    }

    public void setXTSJC(byte XTSJC) {
        this.XTSJC = XTSJC;
    }



    public void setRemainMoney(double remainMoney) {
        this.remainMoney = remainMoney;
    }

    public Timestamp getMeterTime() {
        return meterTime;
    }

    public void setMeterTime(Timestamp meterTime) {
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

    public void getFromStr(String dataStr) {


        //System.out.println("数据区："+dataStr+"，长度："+dataStr.length());

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

            meterTime.setTime(meterTime.getTime()+longTime);
        }
    }

    public static void main(String[] args) {
        new MeterStatus();
    }

}
