package com.skbroadband.doms.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skbroadband.doms.global.component.ApplicationContextProvider;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.util
 * @File : CommUtils
 * @Program :
 * @Date : 2022-12-26
 * @Comment :
 */
@Slf4j
@UtilityClass
public class CommUtils {
    public static boolean continueCharactersCheck(String password) {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String number = "0123456789";

        String reversUpperCase = "ZYXWVUTSRQPONMLKJIHGFEDCBA";
        String reversLowerCase = "zyxwvutsrqponmlkjihgfedcba";
        String reversNumber = "9876543210";

        boolean continueCharactersCheck = false;

        for (int i = 0; i < password.length() - 2; i += 1) {
            if (upperCase.contains(password.substring(i, i + 3))) {
                continueCharactersCheck = true;
                break;
            }
            if (lowerCase.contains(password.substring(i, i + 3))) {
                continueCharactersCheck = true;
                break;
            }
            if (number.contains(password.substring(i, i + 3))) {
                continueCharactersCheck = true;
                break;
            }

            if (reversUpperCase.contains(password.substring(i, i + 3))) {
                continueCharactersCheck = true;
                break;
            }
            if (reversLowerCase.contains(password.substring(i, i + 3))) {
                continueCharactersCheck = true;
                break;
            }
            if (reversNumber.contains(password.substring(i, i + 3))) {
                continueCharactersCheck = true;
                break;
            }
        }
        return continueCharactersCheck;
    }

    /**
     * url 유효성검사
     *
     * @param url
     * @return
     */
    public static boolean isInValidUrl(String url)
    {
        try {
            new URL(url).toURI();
            return false;
        }
        catch (URISyntaxException | MalformedURLException exception) {
            return true;
        }
    }

    public static boolean isInValidUri(String uri)
    {
        if(!uri.startsWith("/")) {
            return true;
        }

        try {
            new URL("http://www.skbroadband.com"+uri).toURI();
            return false;
        }
        catch (URISyntaxException | MalformedURLException exception) {
            return true;
        }
    }
    public static <T> T getBean(Class<T> type) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(type);
    }

    public static void writeResponseBodyLog(Object obj) {
        try {
            Map<String, Object> responseBodyWrapperMap = new HashMap<>();

            ObjectMapper mapper = new ObjectMapper();
            String responseBody = objectToJsonString(obj);
            Map responseBodyMap = mapper.readValue(responseBody, Map.class);

//            responseBodyWrapperMap.put("responseBody", responseBodyMap);

            log.error("Error Message: {}", responseBodyMap);
        } catch (IOException e) {
            log.error("{}", e);
        }
    }

    public static String objectToJsonString(Object o) {
        String json = "{}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            //logger.error("{}", e);
        }

        return json;
    }

    public static String replaceXSS(String value) {
        if(value != null) {

            // SQL Injection 방지를 위한 패턴
            String sqlInjectionPattern = "(?i)(insert|update|delete|having|drop|join|from|select|union|sleep|user_tables|user_table_columns|table_name|column_name|Syscolumns|(\'|%27).(and|or).(\'|%27)|(\'|%27).%7C{0,2}|%7C{2})";
            Pattern sqlRegexPattern = Pattern.compile(sqlInjectionPattern);
            Matcher sqlMatcher = sqlRegexPattern.matcher(value);
            value = sqlMatcher.replaceAll("");

            // XSS 공격 방지를 위한 패턴
            String xssPattern = "%3C|%3E|%26|%23|%253C|%253E|%2526|%2523"; // HTML 특수 문자 및 URL Encoding 문자
            Pattern xssRegexPattern = Pattern.compile(xssPattern);
            Matcher xssMatcher = xssRegexPattern.matcher(value);

            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("#", "&#35;");
            value = value.replaceAll("\\<", "&lt;").replaceAll("\\>", "&gt;");
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("'", "&#39;");
            value = value.replaceAll("\"", "&quot;");
            value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
            value = value.replaceAll("script","");
            value = value.replaceAll("alert","");
            value = value.replaceAll("document","");
            value = value.replaceAll("cookie","");
            value = value.replaceAll("iframe","");
            value = value.replaceAll("frame","");
            value = value.replaceAll("/((\\%3D)|(=))[^\\n]*((\\%27)|(\')|(\\-\\-)|(\\%3B)|(;))/i","");
            value = value.replaceAll("/((\\%27)|(\'))union/ix","");
            value = value.replaceAll("/\\w*((\\%27)|(\\'))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52))/ix","");
            value = value.replaceAll("/((\\%3C)|<)((\\%69)|i|(\\%49))((\\%6D)|m|(\\%4D))((\\%67)|g|(\\%47))[^\n]+((\\%3E)|>)/I","");

        }
        return value;
    }
}