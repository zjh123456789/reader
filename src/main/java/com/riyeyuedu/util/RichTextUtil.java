package com.riyeyuedu.util;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author haojie tang
 * @date 2018/10/8 14:14
 */
public class RichTextUtil {
    public static String byteToString(byte[] bytes) {
        String htmlString = new String(bytes, Charset.forName("UTF-8"));
        String oldString = HtmlUtils.htmlUnescape(htmlString);
        String newString = StringUtils.replace(oldString, "<br>", "");
        return newString;
    }

    public static String byteToStringWithBr(byte[] bytes) {
        String htmlString = new String(bytes, Charset.forName("UTF-8"));
        String oldString = HtmlUtils.htmlUnescape(htmlString);
        return oldString;
    }

    public static byte[] stringToByte(String s) {
        byte[] htmlBytes = s.getBytes(StandardCharsets.UTF_8);
        return htmlBytes;
    }
}
