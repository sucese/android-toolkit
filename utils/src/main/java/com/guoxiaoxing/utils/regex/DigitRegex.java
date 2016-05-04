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
     * 判断是否为浮点数，包括double和float
     * @autor:chenssy
     * @data:2014年8月7日
     *
     * @param value
     * 			传入的字符串
     * @return
     */
    public static boolean isDouble(String value) {
        Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
        return pattern.matcher(value).matches();
    }

    /**
     * 判断是否为整数
     * @autor:chenssy
     * @data:2014年8月7日
     *
     * @param value
     * 			传入的字符串
     * @return
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(value).matches();
    }
}  