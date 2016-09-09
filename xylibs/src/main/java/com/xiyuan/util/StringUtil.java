package com.xiyuan.util;

/**
 * Created by xiyuan_fengyu on 2016/7/26.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean nonEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isBlank(String str) {
        return str == null || removeAllBlankChar(str).isEmpty();
    }

    public static boolean nonBlank(String str) {
        return str != null && !removeAllBlankChar(str).isEmpty();
    }

    public static String removeAllBlankChar(String str) {
        if (str != null) {
            int len = str.length();
            if (len > 0) {
                char[] dest = new char[len];
                int destPos = 0;
                for (int i = 0; i < len; ++i) {
                    char c = str.charAt(i);
                    if (!Character.isWhitespace(c)) {
                        dest[destPos++] = c;
                    }
                }
                return new String(dest, 0, destPos);
            }
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(isBlank("\t "));
    }

}
