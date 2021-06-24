package com.zp.universityForum.utils.password;

/**
 * @author zp
 * @date 2021-04-02 15:32
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMD5(String plainText) {
        String cipherText = null;
        try {
            // 加密对象，指定加密格式
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] bytes = plainText.getBytes();
            // 加密
            byte[] digest = md5.digest(bytes);
            // 16进制的字符
            char[] chars = new char[] { '0', '1', '2', '3', '4', '5',
                    '6', '7' , '8', '9', 'A', 'B', 'C', 'D', 'E','F'};
            StringBuffer sb = new StringBuffer();
            // 处理成16进制的字符串
            for (byte b : digest) {
                sb.append(chars[(b >> 4) & 15]);
                sb.append(chars[b & 15]);
            }
            // 打印加密后的字符串
            cipherText = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cipherText;
    }

}
