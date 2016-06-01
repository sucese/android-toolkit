package com.guoxiaoxing.utils.regex;

import java.util.regex.Pattern;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/4 下午3:55
 * Function: string regex utils
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * -----------------------------------------------------------------------------
 * 16/5/4 下午3:55     guoxiaoxing          1.0                string regex utils
 */
public class StringRegex {

    /**
     * 是否为Email
     *
     * @param value value
     * @return result
     */
    public static boolean isEmail(String value) {
        if (value == null || value.length() < 1 || value.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(value).matches();
    }

    /**
     * 是否为纯汉字
     *
     * @param value value
     * @return result
     */
    public static boolean isChinese(String value) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(value).matches();
    }

    /**
     * 是否为车牌号
     *
     * @param value value
     * @return result
     */
    public static boolean isLicensePlateNumber(String value) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}");
        return pattern.matcher(value).matches();
    }

    /**
     * 是否为手机号
     *
     * @param value value
     * @return result
     */
    public static boolean isPhoneNumber(String value) {
        Pattern pattern = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$");
        return pattern.matcher(value).matches();
    }

    /**
     * 是否为移动手机号
     *
     * @param value value
     * @return result
     */
    public static boolean isCCPhoneNumber(String value) {
        Pattern pattern = Pattern.compile("(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)");
        return pattern.matcher(value).matches();
    }

    /**
     * 是否为联通手机号
     *
     * @param value value
     * @return result
     */
    public static boolean isCUPhoneNumber(String value) {
        Pattern pattern = Pattern.compile("(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)");
        return pattern.matcher(value).matches();
    }

    /**
     * 是否为电信手机号
     *
     * @param value value
     * @return result
     */
    public static boolean isCTPhoneNumber(String value) {
        Pattern pattern = Pattern.compile("(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)");
        return pattern.matcher(value).matches();
    }
}