package com.skbroadband.doms.global.component.security;

import com.skbroadband.doms.global.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.util
 * @File : JceCryptoComponent
 * @Program :
 * @Date : 2022-12-16
 * @Comment :
 */
@Component
public class JceCryptoComponent implements Crypto{
    private final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
    private final int IV_LENGTH_BYTE = 12;
    private final int SALT_LENGTH_BYTE = 16;
    private final Charset UTF_8 = StandardCharsets.UTF_8;

    private final String password;
    private final CryptoUtils util;

    public JceCryptoComponent(@Value("${application.crypto.password}") String password) {
        this.util = new CryptoUtils();
        this.password = password;
    }

    @Override
    public String encrypt(String text) throws Exception {
        return encrypt(text.getBytes(), password);
    }

    @Override
    public String descrypt(String text) throws Exception {
        return decrypt(text, password);
    }


    // return a base64 encoded AES encrypted text
    private String encrypt(byte[] pText, String password) throws Exception {

        // 16 bytes salt
        byte[] salt = util.getRandomNonce(SALT_LENGTH_BYTE);

        // GCM recommended 12 bytes iv?
        byte[] iv = util.getRandomNonce(IV_LENGTH_BYTE);

        // secret key from password
        SecretKey aesKeyFromPassword = util.getAESKeyFromPassword(password.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        // ASE-GCM needs GCMParameterSpec
        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] cipherText = cipher.doFinal(pText);

        // prefix IV and Salt to cipher text
        byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                .put(iv)
                .put(salt)
                .put(cipherText)
                .array();

        // string representation, base64, send this string to other for decryption.
        return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);

    }

    // we need the same password, salt and iv to decrypt it
    private String decrypt(String cText, String password) throws Exception {

        byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));

        // get back the iv and salt from the cipher text
        ByteBuffer bb = ByteBuffer.wrap(decode);

        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);

        byte[] salt = new byte[SALT_LENGTH_BYTE];
        bb.get(salt);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        // get back the aes key from the same password and salt
        SecretKey aesKeyFromPassword = util.getAESKeyFromPassword(password.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, UTF_8);
    }
}
