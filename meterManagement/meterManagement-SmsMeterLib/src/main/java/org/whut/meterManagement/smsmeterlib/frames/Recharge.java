package org.whut.meterManagement.smsmeterlib.frames;

import org.whut.meterManagement.smsmeterlib.send.SendFrame;

/**
 * Created by chenfu on 2016/12/13.
 */

/// <summary>
/// 更新表具金额,派生自 SendFrame 类
/// </summary>
public class Recharge extends SendFrame {

    /**
     * @param mId  表具编号
     * @param fId  帧ID号
     * @param czfs 金额变更方式
     * @param mon  变更金额
     * @param cId  充值ID
     */
    public Recharge(String mId, byte fId, int czfs, double mon, int cId) {
        super();
        meterID = mId;
        frameID = fId;
        funcCode = 0x0A;
        addParam(mon, 4);
        addParam(czfs, 1);
        addParam(cId, 1);

    }
}
