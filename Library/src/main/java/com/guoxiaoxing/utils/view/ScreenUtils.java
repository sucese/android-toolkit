package com.guoxiaoxing.utils.view;

import android.content.Context;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/4/1 上午11:21
 * Function: screen utils
 *
 * Modification history:
 * Date                 Author              Version             Description
 * ------------------------------------------------------------------------
 * 16/4/1 上午11:21      guoxiaoxing         1.0
 */
public class ScreenUtils {

    private static float density = -1F;
    private static int widthPixels = -1;
    private static int heightPixels = -1;

    private ScreenUtils() {
        throw new AssertionError();
    }


    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int)(dpToPx(context, dp) + 0.5f);
    }

    public static int pxToDpCeilInt(Context context, float px) {
        return (int)(pxToDp(context, px) + 0.5f);
    }

}
