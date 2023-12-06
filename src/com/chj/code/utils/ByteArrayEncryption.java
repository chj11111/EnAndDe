package com.chj.code.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;


public class ByteArrayEncryption {
    public static void main(String[] args) throws Exception {
        // 生成对称密钥
        SecretKey secretKey = generateSecretKey();

        // 待加密的字节数组

        String src = "C:\\Users\\lx\\Desktop\\作业\\数据库08.pdf";

        String dst = "C:\\Users\\lx\\Desktop\\作业\\2.pdf";
        FileInputStream fileInputStream = null;

        byte[] fileBytes = new byte[(int)new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);//将src文件读入到程序的字节数组

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 加密
        byte[] encryptedData = encryptData(fileBytes, secretKey);
        System.out.println("Encrypted Data: " + new String(encryptedData));

        // 解密
        byte[] decryptedData = decryptData(encryptedData, secretKey);
        System.out.println("Decrypted Data: " + new String(decryptedData));


        FileOutputStream fileOutputStream = new FileOutputStream(dst, true);
        fileOutputStream.write(decryptedData);
        fileOutputStream.close();
    }

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
