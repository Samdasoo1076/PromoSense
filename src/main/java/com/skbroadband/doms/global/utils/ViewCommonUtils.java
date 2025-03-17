package com.skbroadband.doms.global.utils;

import com.skbroadband.doms.global.component.security.JceCryptoComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViewCommonUtils {

    private final JceCryptoComponent jceCryptoComponent;

    private final String result = "";

    public String getDescrypt(String str) {
        try {
            str = jceCryptoComponent.descrypt(str);
        } catch(Exception e) {
            log.error("crypto error >>>>> {}", e.getMessage());
        }

        return str;
    }

    public String getHypenMaskHphone(String phone) {
        String decPhone = phone==null?"":getDescrypt(phone);

        if(decPhone.length() == 11) {
            return decPhone.substring(0, 3) + "-****-" + decPhone.substring(7);
        } else if(decPhone.length() == 10) {
            return decPhone.substring(0, 3) + "-***-" + decPhone.substring(6);
        } else {
            return decPhone;
        }
    }

    public String getMaskAdmId(String admId) {
        int len = admId.length();

        return String.format("%-" + len + ".3s", admId).replace(" ", "*");
    }

    public String getMaskName(String admName) {
        int len = admName.length() - 1;

        return String.format("%-" + len + ".1s", admName).replace(" ", "*") + admName.substring(len);
    }

    public String getMaskIp(String admIp) {
        String VERSION4_IP_PATTERN = "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$";
        Matcher matcher = Pattern.compile(VERSION4_IP_PATTERN).matcher(admIp);
        if (matcher.matches()) {
            String maskedIp = "";
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String replace = matcher.group(i);
                /** 3번째 자리일 경우 '*' 로 채운다. */
                if (i == 2) {
                    char[] c = new char[replace.length()];
                    Arrays.fill(c, '*');
                    maskedIp = maskedIp + String.valueOf(c);
                }else if (i == 3) {
                        char[] c = new char[replace.length()];
                        Arrays.fill(c, '*');
                        maskedIp = maskedIp + String.valueOf(c);
                } else {
                    maskedIp = maskedIp + replace;
                }
                /** 마지막 그룹이 될 때까지 '.' 을 붙여준다. */
                if (i < matcher.groupCount()) {
                    maskedIp = maskedIp + ".";
                }
            }
            return maskedIp;
        } else {
            //log.error("Not matching... ip : {}", admIp);
            //throw new IllegalArgumentException(String.format("Not matching... ip", admIp));
            return admIp;
        }
    }

    public String getMaskEmail(String email){
        return email.replaceAll("(?<=.{3}).(?=.*@)", "*");
    }
}
