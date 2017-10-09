package com.yingfeng.modules.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证
 * @author pengc
 * @see com.yingfeng.modules.expression
 */
public class RegExpressionUtil {

    /**
     * ip检查
     * @param ipAddress
     *          IP地址
     * @return 检查是否为IP
     */
    public static boolean isboolIp(String ipAddress) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
        return match(regex, ipAddress);
    }

    /**
     * @param regex
     *          正则表达式字符串
     * @param str
     *          要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
