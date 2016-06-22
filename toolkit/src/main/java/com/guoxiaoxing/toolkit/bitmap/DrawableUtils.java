package com.guoxiaoxing.toolkit.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/4/11 下午5:02
 * Function: drawable utils
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * ------------------------------------------------------------------------
 * 16/4/11 下午5:02      guoxiaoxing          1.0
 */
public class DrawableUtils {

    /**
     * resize drawable
     *
     * @param context          context
     * @param originalDrawable originalDrawable
     * @param dstWidth         dstWidth
     * @param dstHeight        dstHeight
     * @return resized drawable
     */
    public static Drawable resizeDrawable(Context context, Drawable originalDrawable, int dstWidth, int dstHeight) {
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        Bitmap resizeBitmap = Bitmap.createScaledBitmap(originalBitmap, dstWidth, dstHeight, false);
        return new BitmapDrawable(context.getResources(), resizeBitmap);
    }

}  