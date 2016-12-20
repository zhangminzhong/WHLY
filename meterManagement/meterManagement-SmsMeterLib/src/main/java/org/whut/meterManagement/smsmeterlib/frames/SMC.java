package org.whut.meterManagement.smsmeterlib.frames;

import org.whut.meterManagement.date.DateUtil;
import org.whut.meterManagement.smsmeterlib.send.SendFrame;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by zhang_minzhong on 2016/12/16.
 */
public class SMC {
    /// <summary>
    /// 表具开通帧
    /// </summary>
    /// <param name="meterId">表具编号</param>
    /// <param name="key">表具密钥</param>
    /// <param name="fid">帧ID</param>
    /// <param name="money">开通金额</param>
    /// <param name="p0">初始价格</param>
    /// <param name="p1">阶梯价格1</param>
    /// <param name="p2">阶梯价格2</param>
    /// <param name="p3">阶梯价格3</param>
    /// <param name="a1">起始量1</param>
    /// <param name="a2">起始量2</param>
    /// <param name="a3">起始量3</param>
    /// <param name="nkey">新秘钥</param>
    /// <param name="beginDT">开通日期</param>
    /// <param name="clen">周期长度</param>
    /// <param name="cbr">抄表日</param>
    /// <param name="bzql">本周期量</param>
    /// <param name="szql">上周期量</param>
    /// <returns></returns>
    public static String getMeterOpenFrame(String meterId,String key,byte fid,double money,double p0,double p1,double p2,double p3,int a1,int a2,int a3,String nkey,Timestamp beginDT,byte clen,byte cbr,int bzql,int szql){
        String strResult = "";
        //生成新密钥数组
        byte[] bKey = new byte[16];
        for(int i=0;i<16;i++){
            bKey[i] = (byte) Integer.parseInt(nkey.substring(i*2,i*2+2),16);
        }
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterId);
        sf.setFuncCode((byte) 0x23);
        sf.setFrameID(fid);
        sf.addParam(p0, 2);
        sf.addParam(p1, 2);
        sf.addParam(a1, 2, true);
        sf.addParam(p2, 2);
        sf.addParam(a2, 2, true);
        sf.addParam(p3, 2);
        sf.addParam(a3, 2, true);
        sf.addParam(money, 4);
        sf.addParam(bKey);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(beginDT.getTime());
        //System.out.println(c.get(Calendar.MONTH)+1);
        sf.addParam(c.get(Calendar.YEAR)/100,1);
        sf.addParam(c.get(Calendar.YEAR)%100,1);
        sf.addParam(c.get(Calendar.MONTH)+1,1);
        sf.addParam(c.get(Calendar.DAY_OF_MONTH),1);
        sf.addParam(clen,1);
        sf.addParam(0,1);
        sf.addParam(cbr, 1);
        sf.addParam(bzql, 2, true);
        sf.addParam(szql, 2, true);
        StringBuffer sb = new StringBuffer();
        sf.ProcFrame(sb, key);
        strResult = sb.toString();
        return strResult;
    }

    /// <summary>
    /// 变更金额帧
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">充值ID</param>
    /// <param name="money">充值金额，可为负数</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
    public static String getChangeMoneyFrame(String resid,String key,int fid,double money,int timeCorrection){
        String strResult = "";
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setFrameID((byte)(fid%256));
        sf.setFuncCode((byte)0x0A);
        double dMon;
        int czfs = 0;
        if(money>0){
            dMon = money;
            czfs = 0;
        }else{
            dMon = money*-1;
            czfs = 1;
        }
        sf.addParam(dMon,4);
        sf.addParam(czfs,1);
        sf.addParam(fid/256,1);
        sf.setTimeCorrection(timeCorrection);
        StringBuffer sb = new StringBuffer();
        sf.ProcFrame(sb,key);
        strResult = sb.toString();
        return strResult;
    }

    /// <summary>
    /// 读取表具数据
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">帧ID</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
    public static String getMeterDataFrame(String resid,String key,byte fid,int timeCorrection){
        String strResult = "";
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setTimeCorrection(timeCorrection);
        sf.setFuncCode((byte)5);
        sf.setFrameID(fid);
        StringBuffer sb = new StringBuffer();
        sf.ProcFrame(sb,key);
        strResult = sb.toString();
        return strResult;
    }

    /// <summary>
    /// 变更价格
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">帧ID</param>
    /// <param name="p0">初始价格</param>
    /// <param name="p1">阶梯价格1</param>
    /// <param name="p2">阶梯价格2</param>
    /// <param name="p3">阶梯价格3</param>
    /// <param name="a1">起始量1</param>
    /// <param name="a2">起始量2</param>
    /// <param name="a3">起始量3</param>
    /// <param name="beginDT">阶梯价格起始日期</param>
    /// <param name="clen">阶梯周期长度</param>
    /// <param name="atDT">定时更改时间点，为NULL表示即时更改</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
    public static String getChangePriceFrame(String resid,String key,byte fid,double p0,double p1,double p2,double p3,int a1,int a2,int a3,Timestamp beginDT,byte clen,Timestamp atDT,int timeCorrection){
        String strResult = "";
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setFrameID(fid);
        sf.setTimeCorrection(timeCorrection);
        if(atDT!=null) {
            long millisecond = atDT.getTime() - DateUtil.createDate("2000-01-01 00:00:00").getTime();
            millisecond = millisecond/1000;
            sf.setFuncCode((byte) 0x1F);
            sf.addParam((int) millisecond, 4);
        }
        else{
            sf.setFuncCode((byte)0x08);
        }
        sf.addParam(p0,2);
        sf.addParam(p1,2);
        sf.addParam(a1,2,true);
        sf.addParam(p2,2);
        sf.addParam(a2, 2, true);
        sf.addParam(p3, 2);
        sf.addParam(a3, 2, true);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(beginDT.getTime());
        sf.addParam(c.get(Calendar.YEAR) / 100, 1);
        sf.addParam(c.get(Calendar.YEAR) % 100, 1);
        sf.addParam(c.get(Calendar.MONTH)+1, 1);
        sf.addParam(c.get(Calendar.DAY_OF_MONTH), 1);
        sf.addParam(clen, 1);
        sf.addParam(0, 1);
        StringBuffer sb = new StringBuffer();
        sf.ProcFrame(sb, key);
        strResult = sb.toString();
        return strResult;
    }

    /// <summary>
    /// 更改透支方式
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">帧ID</param>
    /// <param name="style">透支方式。0：10元，1：3天，2：无限</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
}
