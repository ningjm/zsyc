package com.zsyc.common;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class WxPhoneUtil {

    /**
     * TODO:在这里你需要注意了:你需要对整个返回的String做处理，把需要的那个手机号拿出来就ok了，
     * 你可以打印一下这个返回值然后处理，因为处理方式很多，我的处理方式不一定适合大家自身的习惯，所以这里改动了一下
     */
    public static String getPhoneNumberBeanS5(String decryptData, String key, String iv) throws Exception {
        /*
         *这里你没必要非按照我的方式写，下面打代码主要是在一个自己的类中 放上decrypts5这个解密工具，工具在下方有代码
         */
        String resultMessage = decryptS5(decryptData,"UTF-8",key,iv);
        return resultMessage;
    }

    /**
     * 解密工具直接放进去即可
     */
    public static String decryptS5(String sSrc, String encodingFormat, String sKey, String ivParameter) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] raw = decoder.decodeBuffer(sKey);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(decoder.decodeBuffer(ivParameter));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] myendicod = decoder.decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(myendicod);
            String originalString = new String(original, encodingFormat);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }




    public static String descrptPhone(String sessionKey1, String ivData1, String encrypData1) throws Exception {
        byte[] encrypData = Base64.decodeBase64(encrypData1);
        byte[] ivData = Base64.decodeBase64(ivData1);
        byte[] sessionKey = Base64.decodeBase64(sessionKey1);

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(sessionKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return new String(cipher.doFinal(encrypData), "UTF-8");
    }

//    public String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
//        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
//        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
//        //解析解密后的字符串
//        return new String(cipher.doFinal(encData), "UTF-8");
//    }
}
