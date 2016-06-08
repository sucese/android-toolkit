package com.guoxiaoxing.utils.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;

import com.guoxiaoxing.utils.crypto.CryptoUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


import static com.guoxiaoxing.utils.log.Log.DEBUG;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site: https://github.com/guoxiaoxing
 * Date: 16/6/7 下午10:07
 * Function: Apk工具类
 * <p>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/6/7 下午10:07      guoxiaoxing          1.0                Apk工具类
 */

public class ApkUtils {

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param file    APK文件uri
     */
    public static void installApk(Context context, Uri file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载apk
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    /**
     * 获取应用签名
     *
     * @param context 上下文
     * @param pkgName 包名
     * @return 返回应用的签名
     */
    public static String getSign(Context context, String pkgName) {
        try {
            PackageInfo pis = context.getPackageManager()
                    .getPackageInfo(pkgName,
                            PackageManager.GET_SIGNATURES);
            return hexdigest(pis.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将签名字符串转换成需要的32位签名
     *
     * @param paramArrayOfByte 签名byte数组
     * @return 32位签名字符串
     */
    private static String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
                98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("PackageManagerGetSignatures")
    private static Signature getPackageSignature(Context context) {
        final PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (Exception ignored) {
        }
        Signature signature = null;
        if (info != null) {
            Signature[] signatures = info.signatures;
            if (signatures != null && signatures.length > 0) {
                signature = signatures[0];
            }
        }
        return signature;
    }

    public static String getSignature(Context context) {
        final Signature signature = getPackageSignature(context);
        if (signature != null) {
            try {
                return CryptoUtils.HASH.sha1(signature.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getSignatureInfo(Context context) {
        final Signature signature = getPackageSignature(context);
        if (signature == null) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        try {
            final byte[] signatureBytes = signature.toByteArray();
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            final InputStream is = new ByteArrayInputStream(signatureBytes);
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(is);
            final String chars = signature.toCharsString();
            final String hex = CryptoUtils.HEX.encodeHex(signatureBytes, false);
            final String md5 = CryptoUtils.HASH.md5(signatureBytes);
            final String sha1 = CryptoUtils.HASH.sha1(signatureBytes);
            builder.append("SignName:").append(cert.getSigAlgName()).append("\n");
            builder.append("Chars:").append(chars).append("\n");
            builder.append("Hex:").append(hex).append("\n");
            builder.append("MD5:").append(md5).append("\n");
            builder.append("SHA1:").append(sha1).append("\n");
            builder.append("SignNumber:").append(cert.getSerialNumber()).append("\n");
            builder.append("SubjectDN:").append(cert.getSubjectDN().getName()).append("\n");
            builder.append("IssuerDN:").append(cert.getIssuerDN().getName()).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String text = builder.toString();
        return text;
    }

}
