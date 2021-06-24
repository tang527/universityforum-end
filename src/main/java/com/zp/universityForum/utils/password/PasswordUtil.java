package com.zp.universityForum.utils.password;

import java.util.Base64;
import java.util.UUID;

/**
 * @author zp
 * @date 2021-04-02 14:51
 * 密码工具类
 */
public class PasswordUtil {

    /**
     * 密码匹配
     * @param plainText 明文密码
     * @param cipherText 经MD5加密后 再经Base64编码后的存在数据库的密码
     * @return
     */
    public static boolean matches(String plainText, String cipherText) {
        String psw = PasswordEncoder.getEncodeBase64(MD5Util.getMD5(plainText));
        return cipherText.equals(psw);
    }

    /**
     * 明文密码加密
     * @param plainText 明文
     */
    public static String encode(String plainText) {
        return PasswordEncoder.getEncodeBase64(MD5Util.getMD5(plainText));
    }

}
