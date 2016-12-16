package org.whut.meterManagement.smsmeterlib.frames;

import org.whut.meterManagement.smsmeterlib.send.SendFrame;

/**
 * Created by chenfu on 2016/12/13.
 */

/// <summary>
/// 表具开通。变更表具状态为用户态
/// </summary>
public class ToUserState extends SendFrame {

    /**
     * @param mId   表具编号
     * @param fId   帧ID
     * @param price 单价
     */
    public ToUserState(String mId, byte fId, double price) {
        super();
        meterID = mId;
        funcCode = 0x24;
        frameID = fId;
        addParam(price, 2);

    }
}
