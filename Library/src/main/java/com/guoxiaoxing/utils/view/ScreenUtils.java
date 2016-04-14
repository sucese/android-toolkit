package com.guoxiaoxing.utils.view;

import android.content.Context;
import android.opengl.GLES10;
import android.util.DisplayMetrics;

import javax.microedition.khronos.opengles.GL10;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/4/1 上午11:21
 * Function: screen utils
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * ------------------------------------------------------------------------
 * 16/4/1 上午11:21      guoxiaoxing         1.0
 */
public class ScreenUtils {

    private ScreenUtils() {
        throw new AssertionError();
    }

    public static int getWindowWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getWindowHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static float getWindowDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    /**
     * @return max canvas draw size, 2048 or 4096
     */
    public static int getMaxCanvasDrawSize() {
        int[] maxTextureSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxTextureSize, 0);
        return Math.max(maxTextureSize[0], 2048);
    }

    /**
     * dp to px
     *
     * @param context context
     * @param dp      dp
     * @return px
     */
    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    /**
     * px to dp
     *
     * @param context context
     * @param px      px
     * @return dp
     */
    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    /**
     * dp to px
     *
     * @param context context
     * @param dp      dp
     * @return px
     */
    public static int dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }

    /**
     * px to dp
     *
     * @param context context
     * @param px      px
     * @return dp
     */
    public static int pxToDpCeilInt(Context context, float px) {
        return (int) (pxToDp(context, px) + 0.5f);
    }

}
