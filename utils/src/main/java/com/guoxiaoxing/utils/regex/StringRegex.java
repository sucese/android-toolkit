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
     * 判断输入的字符串是否符合Email格式.
     * @autor:chenssy
     * @data:2014年8月7日
     *
     * @param email
     * 				传入的字符串
     * @return 符合Email格式返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }

    /**
     * 判断输入的字符串是否为纯汉字
     * @autor:chenssy
     * @data:2014年8月7日
     *
     * @param value
     * 				传入的字符串
     * @return
     */
    public static boolean isChinese(String value) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(value).matches();
    }
}