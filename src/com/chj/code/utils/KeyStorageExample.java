package com.chj.code.utils;

import javax.crypto.SecretKey;
import java.security.KeyStore;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class KeyStorageExample {

    public static void storeKey(SecretKey secretKey, String keyAlias, char[] keystorePassword, String keystorePath) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, keystorePassword);

        // 将密钥存储在 KeyStore 中
        KeyStore.SecretKeyEntry keyEntry = new KeyStore.SecretKeyEntry(secretKey);
        keyStore.setEntry(keyAlias, keyEntry, new KeyStore.PasswordProtection(keystorePassword));

        // 将 KeyStore 保存到文件
        try (FileOutputStream fos = new FileOutputStream(keystorePath)) {
            keyStore.store(fos, keystorePassword);
        }
    }

    public static SecretKey loadKey(String keyAlias, char[] keystorePassword, String keystorePath) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");

        // 从文件加载 KeyStore
        try (FileInputStream fis = new FileInputStream(keystorePath)) {
            keyStore.load(fis, keystorePassword);
        }

        // 从 KeyStore 中获取密钥
        KeyStore.SecretKeyEntry keyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(keyAlias, new KeyStore.PasswordProtection(keystorePassword));
        return keyEntry.getSecretKey();
    }
}
