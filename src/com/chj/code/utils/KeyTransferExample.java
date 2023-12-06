package com.chj.code.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static com.chj.code.utils.ByteArrayEncryption.generateSecretKey;

public class KeyTransferExample {

    public static void main(String[] args) throws Exception {
        // 生成对称密钥
        SecretKey secretKey = generateSecretKey();

        // 生成非对称密钥对（公钥和私钥）
        KeyPair keyPair = generateKeyPair();

        // 使用公钥加密对称密钥
        byte[] encryptedKey = encryptKey(secretKey, keyPair.getPublic());
        System.out.println("Encrypted Key: " + Base64.getEncoder().encodeToString(encryptedKey));

        // 在实际应用中，你需要将加密后的密钥发送给另一方，并确保安全传输。
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encryptKey(SecretKey secretKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(secretKey.getEncoded());
    }

    public static SecretKey decryptKey(byte[] encryptedKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKeyBytes = cipher.doFinal(encryptedKey);
        return new SecretKeySpec(decryptedKeyBytes, "AES");
    }
}
