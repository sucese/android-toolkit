package com.guoxiaoxing.toolkit.bitmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.media.ExifInterface;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Type;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/4/15 下午5:08
 * Function: bitmap utils
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/4/15 下午5:08      guoxiaoxing          1.0                bitmap utils
 */
final class BitmapEffectUtils {

    private static final boolean DEBUG = false;
    private static final String TAG = BitmapEffectUtils.class.getSimpleName();

    /**
     * Don't let anyone instantiate this class.
     */
    private BitmapEffectUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return 圆形Bitmap
     */
    public static Bitmap createRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 获得圆角图片的方法
     *
     * @param bitmap  源Bitmap
     * @param roundPx 圆角大小
     * @return 期望Bitmap
     */
    public static Bitmap createRoundCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param bitmap 源Bitmap
     * @return 带倒影的Bitmap
     */
    public static Bitmap createReflectionBitmap(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }


    /**
     * Returns a Bitmap representing the thumbnail of the specified Bitmap. The
     * size of the thumbnail is defined by the dimension
     * android.R.dimen.launcher_application_icon_size.
     * <p/>
     * This method is not thread-safe and should be invoked on the UI thread
     * only.
     *
     * @param bitmap  The bitmap to get a thumbnail of.
     * @param context The application's context.
     * @return A thumbnail for the specified bitmap or the bitmap itself if the
     * thumbnail could not be created.
     */
    public static Bitmap createThumbnailBitmap(Bitmap bitmap, Context context) {
        int sIconWidth = -1;
        int sIconHeight = -1;
        final Resources resources = context.getResources();
        sIconWidth = sIconHeight = (int) resources
                .getDimension(android.R.dimen.app_icon_size);

        final Paint sPaint = new Paint();
        final Rect sBounds = new Rect();
        final Rect sOldBounds = new Rect();
        Canvas sCanvas = new Canvas();

        int width = sIconWidth;
        int height = sIconHeight;

        sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG,
                Paint.FILTER_BITMAP_FLAG));

        final int bitmapWidth = bitmap.getWidth();
        final int bitmapHeight = bitmap.getHeight();

        if (width > 0 && height > 0) {
            if (width < bitmapWidth || height < bitmapHeight) {
                final float ratio = (float) bitmapWidth / bitmapHeight;

                if (bitmapWidth > bitmapHeight) {
                    height = (int) (width / ratio);
                } else if (bitmapHeight > bitmapWidth) {
                    width = (int) (height * ratio);
                }

                final Config c = (width == sIconWidth && height == sIconHeight) ? bitmap
                        .getConfig() : Config.ARGB_8888;
                final Bitmap thumb = Bitmap.createBitmap(sIconWidth,
                        sIconHeight, c);
                final Canvas canvas = sCanvas;
                final Paint paint = sPaint;
                canvas.setBitmap(thumb);
                paint.setDither(false);
                paint.setFilterBitmap(true);
                sBounds.set((sIconWidth - width) / 2,
                        (sIconHeight - height) / 2, width, height);
                sOldBounds.set(0, 0, bitmapWidth, bitmapHeight);
                canvas.drawBitmap(bitmap, sOldBounds, sBounds, paint);
                return thumb;
            } else if (bitmapWidth < width || bitmapHeight < height) {
                final Config c = Config.ARGB_8888;
                final Bitmap thumb = Bitmap.createBitmap(sIconWidth,
                        sIconHeight, c);
                final Canvas canvas = sCanvas;
                final Paint paint = sPaint;
                canvas.setBitmap(thumb);
                paint.setDither(false);
                paint.setFilterBitmap(true);
                canvas.drawBitmap(bitmap, (sIconWidth - bitmapWidth) / 2,
                        (sIconHeight - bitmapHeight) / 2, paint);
                return thumb;
            }
        }

        return bitmap;
    }

    /**
     * 生成水印图片 水印在右下角
     *
     * @param src       the bitmap object you want proecss
     * @param watermark the water mark above the src
     * @return return a bitmap object ,if paramter's length is 0,return null
     */
    public static Bitmap createWatermarkBitmap(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }

        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }


    /**
     * 旋转图片
     *
     * @param angle  旋转角度
     * @param bitmap 要旋转的图片
     * @return 旋转后的图片
     */
    public static Bitmap rotate(Bitmap bitmap, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * 水平翻转处理
     *
     * @param bitmap 原图
     * @return 水平翻转后的图片
     */
    public static Bitmap reverseByHorizontal(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, false);
    }

    /**
     * 垂直翻转处理
     *
     * @param bitmap 原图
     * @return 垂直翻转后的图片
     */
    public static Bitmap reverseByVertical(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, false);
    }

    /**
     * 更改图片色系，变亮或变暗
     *
     * @param delta 图片的亮暗程度值，越小图片会越亮，取值范围(0,24)
     * @return
     */
    public static Bitmap adjustTone(Bitmap src, int delta) {
        if (delta >= 24 || delta <= 0) {
            return null;
        }
        // 设置高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int idx = 0;
        int[] pixels = new int[width * height];

        src.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR += (pixR * gauss[idx]);
                        newG += (pixG * gauss[idx]);
                        newB += (pixB * gauss[idx]);
                        idx++;
                    }
                }
                newR /= delta;
                newG /= delta;
                newB /= delta;
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param bitmap 源Bitmap
     * @return 返回转换好的位图
     */
    public static Bitmap convertToGrey(Bitmap bitmap) {
        int width = bitmap.getWidth(); // 获取位图的宽
        int height = bitmap.getHeight(); // 获取位图的高

        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    /**
     * 将彩色图转换为黑白图
     *
     * @param bmp 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        int alpha = 0xFF << 24; // 默认将bitmap当成24色图片
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }

    /**
     * 读取图片属性：图片被旋转的角度
     *
     * @param path 图片绝对路径
     * @return 旋转的角度
     */
    public static int getImageDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * Apply a blur to a Bitmap
     *
     * @param context    Application context
     * @param sentBitmap Bitmap to be converted
     * @param radius     Desired Radius, 0 < r < 25
     * @return a copy of the image with a blur
     */
    @SuppressLint("InlinedApi")
    public static Bitmap blur(Context context, Bitmap sentBitmap, int radius) {

        if (radius < 0) {
            radius = 0;
            if (DEBUG) {

            }
        } else if (radius > 25) {
            radius = 25;
            if (DEBUG) {

            }
        }

        if (Build.VERSION.SDK_INT > 16) {
            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

            final RenderScript rs = RenderScript.create(context);
            final Allocation input = Allocation.createFromBitmap(rs,
                    sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs,
                    input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs,
                    Element.U8_4(rs));
            script.setRadius(radius /* e.g. 3.f */);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(bitmap);
            return bitmap;
        }

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }

    /**
     * 饱和度处理
     *
     * @param bitmap          原图
     * @param saturationValue 新的饱和度值
     * @return 改变了饱和度值之后的图片
     */
    public static Bitmap saturation(Bitmap bitmap, int saturationValue) {
        // 计算出符合要求的饱和度值
        float newSaturationValue = saturationValue * 1.0F / 127;
        // 创建一个颜色矩阵
        ColorMatrix saturationColorMatrix = new ColorMatrix();
        // 设置饱和度值
        saturationColorMatrix.setSaturation(newSaturationValue);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(saturationColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 亮度处理
     *
     * @param bitmap   原图
     * @param lumValue 新的亮度值
     * @return 改变了亮度值之后的图片
     */
    public static Bitmap lum(Bitmap bitmap, int lumValue) {
        // 计算出符合要求的亮度值
        float newlumValue = lumValue * 1.0F / 127;
        // 创建一个颜色矩阵
        ColorMatrix lumColorMatrix = new ColorMatrix();
        // 设置亮度值
        lumColorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(lumColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 色相处理
     *
     * @param bitmap   原图
     * @param hueValue 新的色相值
     * @return 改变了色相值之后的图片
     */
    public static Bitmap hue(Bitmap bitmap, int hueValue) {
        // 计算出符合要求的色相值
        float newHueValue = (hueValue - 127) * 1.0F / 127 * 180;
        // 创建一个颜色矩阵
        ColorMatrix hueColorMatrix = new ColorMatrix();
        // 控制让红色区在色轮上旋转的角度
        hueColorMatrix.setRotate(0, newHueValue);
        // 控制让绿红色区在色轮上旋转的角度
        hueColorMatrix.setRotate(1, newHueValue);
        // 控制让蓝色区在色轮上旋转的角度
        hueColorMatrix.setRotate(2, newHueValue);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(hueColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 亮度、色相、饱和度处理
     *
     * @param bitmap          原图
     * @param lumValue        亮度值
     * @param hueValue        色相值
     * @param saturationValue 饱和度值
     * @return 亮度、色相、饱和度处理后的图片
     */
    public static Bitmap lumAndHueAndSaturation(Bitmap bitmap, int lumValue,
                                                int hueValue, int saturationValue) {
        // 计算出符合要求的饱和度值
        float newSaturationValue = saturationValue * 1.0F / 127;
        // 计算出符合要求的亮度值
        float newlumValue = lumValue * 1.0F / 127;
        // 计算出符合要求的色相值
        float newHueValue = (hueValue - 127) * 1.0F / 127 * 180;

        // 创建一个颜色矩阵并设置其饱和度
        ColorMatrix colorMatrix = new ColorMatrix();

        // 设置饱和度值
        colorMatrix.setSaturation(newSaturationValue);
        // 设置亮度值
        colorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1);
        // 控制让红色区在色轮上旋转的角度
        colorMatrix.setRotate(0, newHueValue);
        // 控制让绿红色区在色轮上旋转的角度
        colorMatrix.setRotate(1, newHueValue);
        // 控制让蓝色区在色轮上旋转的角度
        colorMatrix.setRotate(2, newHueValue);

        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 怀旧效果处理
     *
     * @param bitmap 原图
     * @return 怀旧效果处理后的图片
     */
    public static Bitmap nostalgic(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Config.RGB_565);
        int pixColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                pixColor = pixels[width * i + k];
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
                int newColor = Color.argb(255, newR > 255 ? 255 : newR,
                        newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
                pixels[width * i + k] = newColor;
            }
        }
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 柔化效果处理
     *
     * @param bitmap 原图
     * @return 柔化效果处理后的图片
     */
    public static Bitmap soften(Bitmap bitmap) {
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 16; // 值越小图片会越亮，越大则越暗

        int idx = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * gauss[idx]);
                        newG = newG + (int) (pixG * gauss[idx]);
                        newB = newB + (int) (pixB * gauss[idx]);
                        idx++;
                    }
                }

                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 光照效果处理
     *
     * @param bitmap  原图
     * @param centerX 光源在X轴的位置
     * @param centerY 光源在Y轴的位置
     * @return 光照效果处理后的图片
     */
    public static Bitmap sunshine(Bitmap bitmap, int centerX, int centerY) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int radius = Math.min(centerX, centerY);

        final float strength = 150F; // 光照强度 100~150
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                newR = pixR;
                newG = pixG;
                newB = pixB;

                // 计算当前点到光照中心的距离，平面座标系中求两点之间的距离
                int distance = (int) (Math.pow((centerY - i), 2) + Math.pow(
                        centerX - k, 2));
                if (distance < radius * radius) {
                    // 按照距离大小计算增加的光照值
                    int result = (int) (strength * (1.0 - Math.sqrt(distance)
                            / radius));
                    newR = pixR + result;
                    newG = pixG + result;
                    newB = pixB + result;
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 底片效果处理
     *
     * @param bitmap 原图
     * @return 底片效果处理后的图片
     */
    public static Bitmap film(Bitmap bitmap) {
        // RGBA的最大值
        final int MAX_VALUE = 255;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                newR = MAX_VALUE - pixR;
                newG = MAX_VALUE - pixG;
                newB = MAX_VALUE - pixB;

                newR = Math.min(MAX_VALUE, Math.max(0, newR));
                newG = Math.min(MAX_VALUE, Math.max(0, newG));
                newB = Math.min(MAX_VALUE, Math.max(0, newB));

                pixels[pos] = Color.argb(MAX_VALUE, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 锐化效果处理
     *
     * @param bitmap 原图
     * @return 锐化效果处理后的图片
     */
    public static Bitmap sharpen(Bitmap bitmap) {
        // 拉普拉斯矩阵
        int[] laplacian = new int[]{-1, -1, -1, -1, 9, -1, -1, -1, -1};

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int idx = 0;
        float alpha = 0.3F;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + n) * width + k + m];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * laplacian[idx] * alpha);
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);
                        idx++;
                    }
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 浮雕效果处理
     *
     * @param bitmap 原图
     * @return 浮雕效果处理后的图片
     */
    public static Bitmap emboss(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height,
                Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                pixColor = pixels[pos + 1];
                newR = Color.red(pixColor) - pixR + 127;
                newG = Color.green(pixColor) - pixG + 127;
                newB = Color.blue(pixColor) - pixB + 127;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 将YUV格式的图片的源数据从横屏模式转为竖屏模式，注意：将源图片的宽高互换一下就是新图片的宽高
     *
     * @param sourceData YUV格式的图片的源数据
     * @param width      源图片的宽
     * @param height     源图片的高
     * @return
     */
    public static final byte[] yuvLandscapeToPortrait(byte[] sourceData,
                                                      int width, int height) {
        byte[] rotatedData = new byte[sourceData.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = sourceData[x + y
                        * width];
        }
        return rotatedData;
    }


    /**
     * 将给定资源ID的Drawable转换成Bitmap
     *
     * @param context 上下文
     * @param resId   Drawable资源文件的ID
     * @return 新的Bitmap
     */
    public static Bitmap drawableToBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 圆角处理
     *
     * @param pixels 角度，度数越大圆角越大
     * @return 转换成圆角后的图片
     */
    public static Bitmap roundCorner(Bitmap bitmap, float pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  //创建一个同原图一样大小的矩形，用于把原图绘制到这个矩形上
        RectF rectF = new RectF(rect);  //创建一个精度更高的矩形，用于画出圆角效果
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0); //涂上黑色全透明的底色
        paint.setColor(0xff424242);  //设置画笔的颜色为不透明的灰色
        canvas.drawRoundRect(rectF, pixels, pixels, paint); //用给给定的画笔把给定的矩形以给定的圆角的度数画到画布
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint); //用画笔paint将原图bitmap根据新的矩形重新绘制
        return output;
    }

    /**
     * 倒影处理
     *
     * @param reflectionSpacing 原图与倒影之间的间距
     * @return 加上倒影后的图片
     */
    public static Bitmap reflection(Bitmap bitmap, int reflectionSpacing, int reflectionHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

		/* 获取倒影图片，并创建一张宽度与原图相同，但高度等于原图的高度加上间距加上倒影的高度的图片，并创建画布。画布分为上中下三部分，上：是原图；中：是原图与倒影的间距；下：是倒影 */
        Bitmap reflectionImage = reverseByVertical(bitmap);//
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, height + reflectionSpacing + reflectionHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);

		/* 将原图画到画布的上半部分，将倒影画到画布的下半部分，倒影与画布顶部的间距是原图的高度加上原图与倒影之间的间距 */
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionSpacing, null);
        reflectionImage.recycle();

		/* 将倒影改成半透明，创建画笔，并设置画笔的渐变从半透明的白色到全透明的白色，然后再倒影上面画半透明效果 */
        Paint paint = new Paint();
        paint.setShader(new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionSpacing, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height + reflectionSpacing, width, bitmapWithReflection.getHeight() + reflectionSpacing, paint);

        return bitmapWithReflection;
    }

    /**
     * 倒影处理
     *
     * @return 加上倒影后的图片
     */
    public static Bitmap reflection(Bitmap bitmap) {
        return reflection(bitmap, 4, bitmap.getHeight() / 2);
    }

    /**
     * 旋转处理
     *
     * @param angle 旋转角度
     * @param px    旋转中心点在X轴的坐标
     * @param py    旋转中心点在Y轴的坐标
     * @return 旋转后的图片
     */
    public static Bitmap rotate(Bitmap bitmap, float angle, float px, float py) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle, px, py);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 旋转后处理
     *
     * @param angle 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotate(Bitmap bitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 模糊效果处理
     *
     * @return 模糊效果处理后的图片
     */
    public static Bitmap blur(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int newColor = 0;

        int[][] colors = new int[9][3];
        for (int i = 1, length = width - 1; i < length; i++) {
            for (int k = 1, len = height - 1; k < len; k++) {
                for (int m = 0; m < 9; m++) {
                    int s = 0;
                    int p = 0;
                    switch (m) {
                        case 0:
                            s = i - 1;
                            p = k - 1;
                            break;
                        case 1:
                            s = i;
                            p = k - 1;
                            break;
                        case 2:
                            s = i + 1;
                            p = k - 1;
                            break;
                        case 3:
                            s = i + 1;
                            p = k;
                            break;
                        case 4:
                            s = i + 1;
                            p = k + 1;
                            break;
                        case 5:
                            s = i;
                            p = k + 1;
                            break;
                        case 6:
                            s = i - 1;
                            p = k + 1;
                            break;
                        case 7:
                            s = i - 1;
                            p = k;
                            break;
                        case 8:
                            s = i;
                            p = k;
                    }
                    pixColor = bitmap.getPixel(s, p);
                    colors[m][0] = Color.red(pixColor);
                    colors[m][1] = Color.green(pixColor);
                    colors[m][2] = Color.blue(pixColor);
                }

                for (int m = 0; m < 9; m++) {
                    newR += colors[m][0];
                    newG += colors[m][1];
                    newB += colors[m][2];
                }

                newR = (int) (newR / 9F);
                newG = (int) (newG / 9F);
                newB = (int) (newB / 9F);

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                newColor = Color.argb(255, newR, newG, newB);
                newBitmap.setPixel(i, k, newColor);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        return newBitmap;
    }

    /**
     * 虚化图片
     *
     * @param context context
     * @param src src
     * @param radius radius
     * @return
     */
    public static Bitmap blurBitmap(Context context, Bitmap src, int radius) {
        Bitmap dest = src.copy(src.getConfig(), true);
        RenderScript rs = RenderScript.create(context);
        Allocation allocation = Allocation.createFromBitmap(rs, src);
        Type t = allocation.getType();
        Allocation blurredAllocation = Allocation.createTyped(rs, t);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        blurScript.setRadius(radius);
        blurScript.setInput(allocation);
        blurScript.forEach(blurredAllocation);
        blurredAllocation.copyTo(dest);
        allocation.destroy();
        blurredAllocation.destroy();
        blurScript.destroy();
        t.destroy();
        rs.destroy();
        return dest;
    }
}