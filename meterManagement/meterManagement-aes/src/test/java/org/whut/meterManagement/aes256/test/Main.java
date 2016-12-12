package org.whut.meterManagement.aes256.test;
import org.whut.meterManagement.aes256.AES;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-12-7
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        byte[] content = new byte[32];
        byte[] password = new byte[32];
        for (int i = 0; i < content.length; i++)
        {
            content[i] = (byte)i;
            password[i] = (byte)i;
        }
        System.out.print("明文：");
        for(int i=0;i<content.length;i++)
            System.out.print(content[i]+" ");
        System.out.println();
        byte[] buff = AES.encrypt(content,password);
        String miwen = JDKAES256Util.convertByteToHexString(buff);
        System.out.println("密文字符串："+miwen+"，长度："+miwen.length());

        byte[] buff1 = AES.decrypt(buff,password);
        System.out.print("解密后：");
        for(int i=0;i<buff1.length;i++){
            System.out.print(buff1[i]+" ");
        }
        System.out.println();
        //System.out.println(AES256Util.convertByteToHexString(new byte[]{12,13,14,15}));
    }
}
