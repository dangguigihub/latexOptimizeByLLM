package com.demo.backend.util;
import org.springframework.util.DigestUtils;

public class PasswordUtil {

    // 密码加密
    public static String encode(String rawPassword) {
        //字符串密码md5加密
        return DigestUtils.md5DigestAsHex(rawPassword.getBytes());
    }

    // 密码验证
    public static boolean matches(String rawPassword, String encodedPassword) {
        String res = DigestUtils.md5DigestAsHex(rawPassword.getBytes());
        return res.equals(encodedPassword);
    }
}
