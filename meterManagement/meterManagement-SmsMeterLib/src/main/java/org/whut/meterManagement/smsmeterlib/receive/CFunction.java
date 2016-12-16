package org.whut.meterManagement.smsmeterlib.receive;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-12-12
 * Time: 上午10:26
 * To change this template use File | Settings | File Templates.
 */
/// <summary>
/// 命令结构，包括命令码，帧ID，用于统一回传帧中的{Ci,IDi}
/// </summary>
public class CFunction {
    private byte code;
    private byte fid;
    private boolean success;

    public byte getCode() {

        return (byte) (Byte.toUnsignedInt(code)%64);
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public byte getFid() {
        return fid;
    }

    public void setFid(byte fid) {
        this.fid = fid;
    }

    public boolean isSuccess() {
        if((Byte.toUnsignedInt(code)/0x40)==0)
            return true;
        else
            return false;
    }

    /*public void setSuccess(boolean success) {
        this.success = success;
    }*/
}
