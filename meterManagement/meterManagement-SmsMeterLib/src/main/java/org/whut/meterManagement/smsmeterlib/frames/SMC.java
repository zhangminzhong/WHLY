package org.whut.meterManagement.smsmeterlib.frames;

import java.sql.Timestamp;

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
        byte[] bkey = new byte[16];
        //for(int i=0;i)
        return null;

    }
}
