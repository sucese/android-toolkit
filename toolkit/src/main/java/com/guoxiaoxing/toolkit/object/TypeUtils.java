package com.guoxiaoxing.toolkit.object;

/**
 * Author: guoxiaoxing
 * Date: 16/8/2 上午11:00
 * Function: 类型转换工具类
 * <p>
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingv@163.com
 */
public class TypeUtils {

    private TypeUtils() {

    }

    public static int convertToInt(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }
}  