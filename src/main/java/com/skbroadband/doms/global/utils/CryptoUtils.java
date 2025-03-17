package com.skbroadband.doms.global.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.util
 * @File : CryptoUtils
 * @Program :
 * @Date : 2022-12-16
 * @Comment :
 */
public class CryptoUtils {
    public byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    public Boolean isWindow() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }


    // AES secret key
    public SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        //keyGen.init(keysize, SecureRandom.getInstance("SHA1PRNG"));
        keyGen.init(keysize, SecureRandom.getInstance("NativePRNG"));

        return keyGen.generateKey();
    }

    // Password derived AES 256 bits secret key
    public SecretKey getAESKeyFromPassword(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // iterationCount = 65536
        // keyLength = 256
        KeySpec spec = new PBEKeySpec(password, salt, 1000, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;

    }

    // hex representation
    public String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // print hex with block size split
    public String hexWithBlockSize(byte[] bytes, int blockSize) {
        String hex = hex(bytes);

        // one hex = 2 chars
        blockSize = blockSize * 2;

        // better idea how to print this?
        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < hex.length()) {
            result.add(hex.substring(index, Math.min(index + blockSize, hex.length())));
            index += blockSize;
        }
        return result.toString();
    }
}
