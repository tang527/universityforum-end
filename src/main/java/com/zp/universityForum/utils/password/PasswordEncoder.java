package com.zp.universityForum.utils.password;

import java.util.Base64;

/**
 * @author zp
 * @date 2021-04-02 14:48
 */
public class PasswordEncoder {

    public static String getEncodeBase64(String plainText) {
        String cipherText = null;
        try {
            // 由于密码其实并不允许中文,所以，没必要使用 "UTF-8"
//            byte[] bytes = plainText.getBytes("UTF-8");
            byte[] bytes = plainText.getBytes();
            cipherText = Base64.getEncoder().encodeToString(bytes);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    public static String getDecoderBase64(String cipherText) {
        String plainText = null;

        byte[] bytes = Base64.getDecoder().decode(cipherText);
        plainText = new String(bytes);
        return plainText;
    }

}