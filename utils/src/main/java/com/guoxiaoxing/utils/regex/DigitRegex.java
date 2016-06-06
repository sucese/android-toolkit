package com.guoxiaoxing.utils.regex;

import java.util.regex.Pattern;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/4 下午3:53
 * Function: digit regular tools
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * ------------------------------------------------------------------------------
 * 16/5/4 下午3:53     guoxiaoxing          1.0                digit regular tools
 */
public class DigitRegex {

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex, digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex, decimals);
    }

    /**
     * 判断是否为整数
     *
     * @param value 传入的字符串
     * @return result
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(value).matches();
    }
}  