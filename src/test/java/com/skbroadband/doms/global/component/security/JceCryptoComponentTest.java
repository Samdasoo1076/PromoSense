package com.skbroadband.doms.global.component.security;

import com.skbroadband.doms.global.utils.ViewCommonUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.security
 * @File : JceCryptoComponentTest
 * @Program : 
 * @author : 안진갑
 * @Date   : 2023-01-12
 * @Comment :
 *
 */

@SpringBootTest
@ActiveProfiles("test")
class JceCryptoComponentTest {
    @Autowired
    private JceCryptoComponent jceCryptoComponent;
    @Autowired
    private ViewCommonUtils viewCommonUtils;

    @Test
    @DisplayName("패스워드 enc")
    void bCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String origin = "11111111";
        String encode = bCryptPasswordEncoder.encode(origin);

        System.out.println("encode value: " + encode);
        boolean isMatch =  bCryptPasswordEncoder.matches(origin, encode);

        assertTrue(isMatch);
    }

    @Test
    @DisplayName("email, 전화번호 enc, dec")
    void crypto() throws Exception {
        String plainText = "test@test.com";
        String encText = jceCryptoComponent.encrypt(plainText);
        String decText = jceCryptoComponent.descrypt("pgMA1Q0/BJ6gIIVovrg1LJZdtKUv+23Rs6KDGdiEYTyDuHWyoK11yp4JIyJ79YAtSIrwVq3NXNl8");

        System.out.println("encText: "+ encText);
        System.out.println("decText: "+ decText);

        assertEquals(plainText, decText);
    }

    @Test
    @DisplayName("hash (email, 전화번호 비교)")
    void sha256Hex() {
        String expect = "bc19d3b3c9e45818c670965f21e9a65e2cb6ef2b91265dbb4baa82124977a58d";
        String hex = DigestUtils.sha256Hex("01088882222");

        System.out.println("sha256Hex: "+ hex);

        assertEquals(expect, hex);
    }

    @Test
    void phone() {
        String test = viewCommonUtils.getHypenMaskHphone("0105556666");
        System.out.println("test >>> " + test);
    }

    @Test
    void testReplace() {
        String aaa = "../AA./BB..\\CC.\\DD%EE;FF";
        System.out.println("result >>> " + aaa.replaceAll("[.././..\\.\\%;]", "-"));
    }
}