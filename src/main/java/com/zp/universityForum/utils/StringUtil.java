package com.zp.universityForum.utils;

import com.zp.universityForum.common.constant.Constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zp
 * @date 2021-04-13 12:46
 */
public class StringUtil {

    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_img = ".*<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>.*";// 定义image标签的正则表达式
    private static final String regEx_emoji = "[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\ud83e\\udd00-\\ud83e\\udfff]|[\\u2600-\\u27ff]";// 定义表情标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
    private static final String regEx_special = "\\&[a-zA-Z]{1,10};";//定义特殊字符

    private static final String NULL_STR = "";

    private static boolean isNull(String str) {
        return null == str;
    }

    public static boolean isEmpty(String str) {
        return isNull(str) || NULL_STR.equals(str.trim());
    }

    public static String join(String... args) {
        StringBuilder sb = new StringBuilder();
        for(String str : args) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String delHTMLTag(String htmlStr) {

        // 过滤image标签
//        Pattern p_img = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Pattern p_img = Pattern.compile(regEx_img);
        Matcher m_img = p_img.matcher(htmlStr);
        if(m_img.matches()) {
            return Constant.OMIT_IMG_STR;
        }
//        htmlStr = m_img.replaceAll("");

        // 过滤script标签
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");

        // 过滤style标签
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");



        // 过滤emoji标签
        Pattern p_emoji = Pattern.compile(regEx_emoji, Pattern.CASE_INSENSITIVE);
        Matcher m_emoji = p_emoji.matcher(htmlStr);
        htmlStr = m_emoji.replaceAll("");

        // 过滤html标签
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");

        // 过滤特殊字符
        Pattern p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        Matcher m_special = p_special.matcher(htmlStr);
        htmlStr = m_special.replaceAll("");

        return htmlStr.trim(); // 返回文本字符串
    }

    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll(" ", "");
        if (htmlStr.length()>50){
            htmlStr = htmlStr.substring(0,50);
        }
        return htmlStr;
    }

}
