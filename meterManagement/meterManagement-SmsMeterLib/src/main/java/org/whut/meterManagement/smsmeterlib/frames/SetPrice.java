package org.whut.meterManagement.smsmeterlib.frames;

import org.whut.meterManagement.smsmeterlib.send.SendFrame;

/**
 * Created by chenfu on 2016/12/13.
 */

/// <summary>
/// 设置表具计费单价
/// </summary>
public class SetPrice extends SendFrame {

    /**
     * @param mId   表具编号
     * @param fId   帧ID
     * @param price 单价
     */
    public SetPrice(String mId, byte fId, double price) {
        super();
        meterID = mId;
        funcCode = 0x08;
        frameID = fId;
        addParam(price, 2);

    }
}
