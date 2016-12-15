package org.whut.meterManagement.aes256;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-12-7
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
public class AES {
    static public byte[] encrypt(byte[] buff, byte[] key) throws Exception {
        if (key.length != 32) {
            throw new Exception("密钥长度不合法，请使用256位密钥！字节数组长度应为32");
        }
        //将输入的字节数组不齐成16的整数倍
        int X = buff.length / 16;
        if (buff.length % 16 > 0)
            X++;
        byte[] allbuff = new byte[X * 16];
        for (int i = 0; i < allbuff.length; i++) {
            if (i < buff.length)
                allbuff[i] = buff[i];
            else
                allbuff[i] = 0;
        }
        //对数据进行分段加密
        AES256Context ctx = new AES256Context();
        AES256 aes = new AES256();
        aes.aes256_init(ctx, key);


        for (int i = 0; i < X; i++) {
            byte[] buf = new byte[16];
            for (int j = 0; j < 16; j++)
                buf[j] = allbuff[i * 16 + j];
            aes.aes256_encrypt_ecb(ctx, buf);
            for (int j = 0; j < 16; j++)
                allbuff[i * 16 + j] = buf[j];
        }

        return allbuff;
    }
    static public byte[] decrypt(byte[] buff, byte[] key) throws Exception {
        if (key.length != 32)
        {
            throw new Exception("密钥长度不合法，请使用256位密钥！字节数组长度应为32");
        }
        if ((buff.length % 16) != 0)
        {
            throw new Exception("需要解密数据长度不合法！字节数组长度应为16的整数倍");
        }

        AES256Context ctx = new AES256Context();
        AES256 aes = new AES256();
        aes.aes256_init(ctx, key);

        int X = buff.length / 16;
        byte[] allbuff = new byte[buff.length];
        for (int i = 0; i < buff.length; i++)
            allbuff[i] = buff[i];

        for (int i = 0; i < X; i++)
        {
            byte[] buf = new byte[16];
            for (int j = 0; j < 16; j++)
                buf[j] = allbuff[i * 16 + j];
            aes.aes256_decrypt_ecb(ctx,buf);
            for (int j = 0; j < 16; j++)
                allbuff[i * 16 + j] = buf[j];
        }

        //去掉解密后数组尾部的0
        //2013.12.18日修改，由于帧校验码可能为0，会被错误的舍弃，因此去掉帧尾部的0重新设计，判断帧长度进行处理
        //int L = allbuff.Length;
        //for (int i = L - 1; i >= 0; i--)
        //{
        //    if (allbuff[i] == 0)
        //    {
        //        L--;
        //    }
        //    else
        //    {
        //        break;
        //    }
        //}
        //return allbuff;
        int L = (int)allbuff[1] + 3;
        if (L > allbuff.length)
        {
            L = allbuff.length;
        }
        byte[] rst = new byte[L];
        for (int i = 0; i < rst.length; i++)
            rst[i] = allbuff[i];
        return rst;
    }
    static public byte[] decryptSame(byte[] buff,byte[] key) throws Exception {
        if (key.length != 32)
        {
            throw new Exception("密钥长度不合法，请使用256位密钥！字节数组长度应为32");
        }
        if ((buff.length % 16) != 0)
        {
            throw new Exception("需要解密数据长度不合法！字节数组长度应为16的整数倍");
        }

        AES256Context ctx = new AES256Context();
        AES256 aes = new AES256();
        aes.aes256_init(ctx, key);

        int X = buff.length / 16;
        byte[] allbuff = new byte[buff.length];
        for (int i = 0; i < buff.length; i++) allbuff[i] = buff[i];

        for (int i = 0; i < X; i++)
        {
            byte[] buf = new byte[16];
            for (int j = 0; j < 16; j++)
                buf[j] = allbuff[i * 16 + j];
            aes.aes256_decrypt_ecb(ctx,buf);
            for (int j = 0; j < 16; j++)
                allbuff[i * 16 + j] = buf[j];
        }
        return allbuff;
    }
    static public byte[] getKey(String sKey)
    {
        String str = sKey;
        if (sKey.length() < 32)
        {
            for(int i=1;i<=32-sKey.length();i++)
                str +="0";
        }
        str = str + str;
        System.out.println(str);
        byte[] key = new byte[32];
        for (int i = 0; i < 32; i++)
        {
           // key[i] = byte.Parse(str.Substring(i * 2, 2), System.Globalization.NumberStyles.HexNumber);
            key[i] = (byte)(int)Integer.valueOf(str.substring(i*2,i*2+2),16);
            //System.out.println(key[i]);
        }
        return key;
    }
}
