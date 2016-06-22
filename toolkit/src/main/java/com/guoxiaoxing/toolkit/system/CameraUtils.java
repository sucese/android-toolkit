package com.guoxiaoxing.toolkit.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.ExifInterface;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.List;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/6/20 上午10:12
 * Function: 相机工具类
 * <p>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/6/20 上午10:12      guoxiaoxing          1.0               相机工具类
 */
public class CameraUtils {

    /**
     * 是否有拍照应用
     *
     * @param context context
     * @return result
     */
    public static boolean hasCamera(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 获取图片真实宽高, 在BitmapFactory.Options 无法获取真实宽高时可以掉哟过
     *
     * @param absolutePath 图片绝对路径
     */
    public static void getDegree(String absolutePath) {
        int degree = 0;
        try {
            //从指定路径读取图片, 并获取EXIF信息
            ExifInterface exifInterface = new ExifInterface(absolutePath);
            int width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);
            int height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片真实宽高, 在BitmapFactory.Options 无法获取真实宽高时可以掉哟过
     *
     * @param absolutePath 图片绝对路径
     * @return 图片角度
     */
    public static int getRealWidthAndHeight(String absolutePath) {
        int degree = 0;
        try {
            //从指定路径读取图片, 并获取EXIF信息
            ExifInterface exifInterface = new ExifInterface(absolutePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
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

}

