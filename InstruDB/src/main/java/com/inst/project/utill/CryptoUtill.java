package com.inst.project.utill;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtill {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;

    private static final byte[] KEY_BYTES = "YOUR_32_BYTE_SECRET_KEY_HERE!!".getBytes(); 
    // 반드시 32바이트 (환경변수/보안설정으로 관리)

    private static final SecretKeySpec KEY = new SecretKeySpec(KEY_BYTES, "AES");

    public static String encrypt(String plainText) {
        try {
            if (plainText == null || plainText.isEmpty()) return plainText;

            Cipher cipher = Cipher.getInstance(ALGORITHM);

            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, KEY, ivSpec);

            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));

            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);

        } catch (Exception e) {
            throw new RuntimeException("암호화 실패", e);
        }
    }

    public static String decrypt(String cipherText) {
        try {
            if (cipherText == null || cipherText.isEmpty()) return cipherText;

            byte[] combined = Base64.getDecoder().decode(cipherText);

            byte[] iv = new byte[16];
            byte[] encrypted = new byte[combined.length - 16];

            System.arraycopy(combined, 0, iv, 0, 16);
            System.arraycopy(combined, 16, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, KEY, new IvParameterSpec(iv));

            return new String(cipher.doFinal(encrypted), "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException("복호화 실패", e);
        }
    }
}