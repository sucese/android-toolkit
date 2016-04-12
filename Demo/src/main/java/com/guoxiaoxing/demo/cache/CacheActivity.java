package com.guoxiaoxing.demo.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.guoxiaoxing.demo.R;
import com.guoxiaoxing.utils.cache.DiskCacheUtils;
import com.guoxiaoxing.utils.cache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CacheActivity extends AppCompatActivity {

    @Bind(R.id.iv_display)
    ImageView mIvDisplay;
    private Context mContext;
    private DiskLruCache mDiskLruCache;
    private String mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);
        initParam();
        initDiskLruCache();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initParam() {
        mContext = CacheActivity.this;
        mImageUrl = "http://upload-images.jianshu.io/upload_images/1067252-955eb2bd63cc86a7.png?imageMogr2/auto-orient/strip%7CimageView2/1/w/300/h/300";
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //写入缓存
                String key = DiskCacheUtils.getMD5KeyForDisk(mImageUrl);
                try {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream os = editor.newOutputStream(0);
                        if (decodeBitmapFromUrl(mImageUrl, os)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //读取缓存

        String key = DiskCacheUtils.getMD5KeyForDisk(mImageUrl);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if(snapshot != null){
                InputStream is = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                mIvDisplay.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDiskLruCache() {

        try {
            File cacheDirectory = DiskCacheUtils.getDiskCacheDirectory(mContext, "bitmap");
            if (!cacheDirectory.exists()) {
                cacheDirectory.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDirectory, DiskCacheUtils.getAppVersion(mContext)
                    , 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean decodeBitmapFromUrl(String imageUrl, OutputStream os) {
        HttpURLConnection connection = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(connection.getInputStream(), 8 * 1024);
            bos = new BufferedOutputStream(os, 8 * 1024);
            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
