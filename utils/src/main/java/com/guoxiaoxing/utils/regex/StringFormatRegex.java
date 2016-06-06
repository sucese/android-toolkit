package com.guoxiaoxing.utils.regex;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/6/6 下午6:03
 * Function:
 * <p>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/6/6 下午6:03      guoxiaoxing          1.0
 */

public class StringFormatRegex {

    /**
     * 手机号码，中间4位星号替换
     *
     * @param phone 手机号
     * @return
     */
    public static String hidePhoneNumber(String phone) {
        // 括号表示组，被替换的部分$n表示第n组的内容
        // 正则表达式中，替换字符串，括号的意思是分组，在replace()方法中，
        // 参数二中可以使用$n(n为数字)来依次引用模式串中用括号定义的字串。
        // "(\d{3})\d{4}(\d{4})", "$1****$2"的这个意思就是用括号，
        // 分为(前3个数字)中间4个数字(最后4个数字)替换为(第一组数值，保持不变$1)(中间为*)(第二组数值，保持不变$2)
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 银行卡号，保留最后4位，其他星号替换
     *
     * @param cardId 卡号
     * @return
     */
    public static String hideBankCard(String cardId) {
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }
}
