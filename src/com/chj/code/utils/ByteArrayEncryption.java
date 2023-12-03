package com.chj.code.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


public class ByteArrayEncryption {
//    public static void main(String[] args) throws Exception {
//        // 生成对称密钥
//        SecretKey secretKey = generateSecretKey();
//
//        // 待加密的字节数组
//        byte[] dataToEncrypt = "Hello, Encryption!".getBytes();
//
//        // 加密
//        byte[] encryptedData = encryptData(dataToEncrypt, secretKey);
//        System.out.println("Encrypted Data: " + new String(encryptedData));
//
//        // 解密
//        byte[] decryptedData = decryptData(encryptedData, secretKey);
//        System.out.println("Decrypted Data: " + new String(decryptedData));
//    }

    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        return keyGenerator.generateKey();
    }

    public static byte[] encryptData(byte[] data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public static byte[] decryptData(byte[] encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedData);
    }
}
