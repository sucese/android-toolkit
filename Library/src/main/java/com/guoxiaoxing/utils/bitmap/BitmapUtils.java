package com.guoxiaoxing.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/4/12 下午2:18
 * Function: bitmap utils
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * ------------------------------------------------------------------------
 * 16/4/12 下午2:18      guoxiaoxing          1.0
 */
public class BitmapUtils {

    /**
     * get bitmap from view
     *
     * @param view view
     * @return bitmap from view
     */
    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }

}