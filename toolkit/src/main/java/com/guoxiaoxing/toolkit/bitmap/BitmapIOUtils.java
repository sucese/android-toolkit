package com.guoxiaoxing.toolkit.bitmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/4/26 下午1:52
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/4/26 下午1:52     guoxiaoxing          1.0
 */
public class BitmapIOUtils {


    /**
     * 将bitmap保存到本地
     * <p/>
     * 添加权限<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     *
     * @param bitmap     原文件
     * @param bitmapName 保存的文件名
     * @return 保存的绝对路径
     */
    public static String saveBitmapToSD(Bitmap bitmap, String bitmapName) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }

        String bitmapPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera";
        File pathFile = new File(bitmapPath);
        String absolutePath = bitmapPath + File.separator + bitmapName;
        File nameFile = new File(absolutePath);

        try {
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            if (!nameFile.exists()) {
                nameFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(nameFile);
            boolean isCompress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            if (isCompress) {
                fos.flush();
                fos.close();
                bitmap.recycle();
            }
            return absolutePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void notifyAddNewPhoto(Context context, String newPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(newPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    static class SaveBitmapTask extends AsyncTask<Bitmap, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Bitmap... params) {


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}  