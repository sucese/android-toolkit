package com.guoxiaoxing.utils.file;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/4/26 下午5:51
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/4/26 下午5:51     guoxiaoxing          1.0
 */
public class InternalStorageUtils {

    public static void writeFileToInternal(Context context, String fileName) {
        String text = "the content you want write to internal storage";
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}  