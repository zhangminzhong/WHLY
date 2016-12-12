package org.whut.meterManagement.aes256.test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-12-6
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
public class JDKAES256Util {
    public static void main(String[] args){
        //String s = convertByteToHexString(new byte[]{10,11,12,13,14,15,16});
        //System.out.println(s);
        /*String content = "Hello AES";
        String password = "123457";
        System.out.println("明文："+content);
        byte[] encodeMessage = aesEncode(content,password);
        System.out.println("加密后："+convertByteToHexString(encodeMessage));
        byte[] decodeMessage = aesDecode(encodeMessage,password);
        System.out.println("解密后："+new String(decodeMessage));*/
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
        byte[] buff = aesEncode(new String(content),new String(password));
        String miwen = convertByteToHexString(buff);
        System.out.println("密文："+miwen+"，长度："+miwen.length());
        byte[] buff1 = aesDecode(buff,new String(password));
        System.out.print("解密后：");
        for(int i=0;i<buff1.length;i++){
            System.out.print(buff1[i]+" ");
        }
        System.out.println();
    }
    public static String convertByteToHexString(byte[] bytes){
        String result = "";
        for(int i=0;i<bytes.length;i++){
            int temp = bytes[i]&0xff;
            String tempHex = Integer.toHexString(temp).toUpperCase();
            if(tempHex.length()<2)
                result += "0"+tempHex;
            else
                result += tempHex;
        }
       // System.out.print(result.length());
        return result;
    }
    public static byte[] aesEncode(String message,String password){
        try{
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256,new SecureRandom(password.getBytes()));
            SecretKey secretKey = keygen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat,"AES");//产生密钥
            Cipher cipher = Cipher.getInstance("AES"); //创建密码器
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] result = cipher.doFinal(message.getBytes());
            return result;//加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (BadPaddingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
    public static byte[] aesDecode(byte[] message, String password){
        try{
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256,new SecureRandom(password.getBytes()));
            SecretKey secretKey = keygen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] result = cipher.doFinal(message);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (BadPaddingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
