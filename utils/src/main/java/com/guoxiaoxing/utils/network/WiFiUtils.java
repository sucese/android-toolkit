package com.guoxiaoxing.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/4/15 下午3:38
 * Function: wifi utils
 * <p>
 * Modification history:
 * Date                 Author              Version             Description
 * ------------------------------------------------------------------------
 * 16/4/15 下午3:38      guoxiaoxing          1.0                wifi utils
 */
public class WiFiUtils {

    /**
     * check network connection
     *
     * @param context true: connect, false: disconnect
     */
    public static boolean isNetworkConnect(Context context) {
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }
}
