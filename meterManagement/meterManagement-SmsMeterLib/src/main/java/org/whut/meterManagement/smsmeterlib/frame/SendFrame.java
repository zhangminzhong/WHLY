package org.whut.meterManagement.smsmeterlib.frame;

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
    public void AddParam(int value, int len)
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
    /*public void addParam(int value, int len, boolean sjz)
    {
        if (sjz)
        {
            byte[] d = new byte[len];

            String str = value.ToString("D" + (len * 2).ToString());
            for (int i = 0; i < len; i++)
            {
                d[i] = Convert.ToByte(str.Substring(i * 2, 2));
            }
            AddParam(d);
        }
        else
        {
            AddParam(value, len);
        }
    }*/
}
