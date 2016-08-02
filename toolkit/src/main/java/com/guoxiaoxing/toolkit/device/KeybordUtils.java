package com.guoxiaoxing.toolkit.device;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Author: guoxiaoxing
 * Date: 16/8/2 下午2:13
 * Function: 键盘工具类
 * <p>
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingv@163.com
 */
public class KeybordUtils {

    /**
     * 关闭软键盘
     *
     * @param activity activity
     */
    public static void closeSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        //如果软键盘已经开启
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 切换软键盘的状态
     *
     * @param context context
     */
    public static void toggleSoftKeyboardState(Context context) {
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
