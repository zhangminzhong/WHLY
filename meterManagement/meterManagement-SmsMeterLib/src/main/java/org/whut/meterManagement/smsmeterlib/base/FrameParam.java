package org.whut.meterManagement.smsmeterlib.base;

import org.whut.meterManagement.smsmeterlib.enums.ParamType;

/**
 * Created by chenfu on 2016/12/9.
 *
 * 参数类，表示帧中的数据对象
 */
public class FrameParam {

    public ParamType PT;
    private int intValue;
    private double fltValue;
    private String strValue;
    private byte[] btaValue;
    private int byteLen;
    public int getByteLen() {
        return byteLen;
    }

    public void setByteLen(int byteLen) {
        this.byteLen = byteLen;
    }

    public FrameParam()
    {
        byteLen = 0;
        intValue = 0;
        fltValue = 0.0;
        strValue = "";
    }

    /**
     * 整数参数赋值
     * @param value
     * @param L
     */
    public void SetValue(int value, int L)
    {
        PT = ParamType.INT;
        byteLen = L;
        intValue = value;
    }

    /**
     * 浮点数参数赋值
     * @param value
     * @param L
     */
    public void SetValue(double value, int L)
    {
        //System.out.println("浮点数："+value+"，在帧中的长度："+L);
        PT = ParamType.FLT;
        byteLen = L;
        fltValue = value;
    }

    /**
     * 字符串参数赋值
     * @param value
     * @param L
     */
    public void SetValue(String value, int L)
    {
        PT = ParamType.STR;
        byteLen = L;
        strValue = value;
    }

    /**
     * 字节参数赋值
     * @param value
     * @param L
     */
    public void SetValue(byte[] value, int L)
    {
        PT = ParamType.BTA;
        byteLen = L;
        btaValue = new byte[L];
        for (int i = 0; i < L; i++)
        {
            btaValue[i] = value[i];
        }
    }

    public int GetValue(int value)
    {
        if (PT == ParamType.INT)
        {
            value = intValue;
        }
        else
        {
            value = 0;
        }
        return value;
    }
    public double GetValue(double value)
    {
        if (PT == ParamType.FLT)
        {
            value = fltValue;
        }
        else
        {
            value = 0.0;
        }
        return value;
    }
    public String GetValue(String value)
    {
        if (PT == ParamType.STR)
        {
            value = strValue;
        }
        else
        {
            value = "";
        }
        return value;
    }
    public byte[] GetValue(byte[] value)
    {
        if (PT == ParamType.BTA)
        {
            value = btaValue;
        }
        else
        {
            value = null;
        }
        return value;
    }

}
