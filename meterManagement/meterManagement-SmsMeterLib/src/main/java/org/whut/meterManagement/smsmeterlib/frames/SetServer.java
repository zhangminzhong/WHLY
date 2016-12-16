package org.whut.meterManagement.smsmeterlib.frames;

import org.whut.meterManagement.smsmeterlib.send.SendFrame;

/**
 * Created by chenfu on 2016/12/13.
 */

/// <summary>
/// 设置表具内服务器号码
/// </summary>
public class SetServer extends SendFrame {

    /**
     * @param mId         表具编号
     * @param fId         帧ID
     * @param serverIndex 服务器号码序号
     * @param serverNum   服务器号码
     */
    public SetServer(String mId, byte fId, int serverIndex, String serverNum) {
        super();
        meterID = mId;
        funcCode = 0x0D;
        frameID = fId;
        addParam(serverIndex, 1);
        addParam(serverNum);

    }
}
