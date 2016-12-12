package org.whut.meterManagement.aes256;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-12-7
 * Time: 上午9:17
 * To change this template use File | Settings | File Templates.
 */
public class AES256Context {
    public byte[] key;
    public byte[] enkey;
    public byte[] deckey;

    public AES256Context() {
        this.deckey = new byte[32];
        this.enkey = new byte[32];
        this.key = new byte[32];
    }
}
