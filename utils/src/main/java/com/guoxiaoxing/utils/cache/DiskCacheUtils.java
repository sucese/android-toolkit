package com.guoxiaoxing.utils.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/4/1 下午2:11
 * Function: disk cache util
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * ------------------------------------------------------------------------
 * 16/4/1 下午2:11      guoxiaoxing          1.0                 disk cache
 */
public class DiskCacheUtils {

    /**
     * get app version
     *
     * @param context context
     * @return app version
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * get cache directory file
     *
     * @param context    context
     * @param uniqueName unique name, for example: image, file etc.
     * @return cache directory file
     */
    public static File getDiskCacheDirectory(Context context, String uniqueName) {

        String cacheDirectory = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()
        ) || !Environment.isExternalStorageRemovable()) {
            File sdDirectory = context.getExternalCacheDir();
            if (sdDirectory != null) {
                cacheDirectory = sdDirectory.getPath();
            }
        } else {
            cacheDirectory = context.getCacheDir().getPath();
        }
        return new File(cacheDirectory + File.separator + uniqueName);
    }

    /**
     * get MD5 key
     *
     * @param key key
     * @return md5 key for disk
     */
    public static String getMD5KeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * bytes to hex string
     *
     * @param bytes bytes
     * @return hex string
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}  