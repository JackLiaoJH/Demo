package com.liaojh.demo.disklrucache;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.liaojh.demo.utils.AndroidUtil;
import com.liaojh.demo.utils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import libcore.io.DiskLruCache;

/**
 * @author LiaoJH
 * @DATE 15/10/13
 * @VERSION 1.0
 * @DESC TODO
 */
public class DiskLruCacheManager {
    private static final String TAG = DiskLruCacheManager.class.getSimpleName();
    private File mFileDiskCache;
    private int mAppVersion;
    private int mValueCount = 1;
    private long mMaxSize = 10 * 1024 * 1024;

    private final String uniqueName = "bitmap";

    private DiskLruCache mDiskLruCache;

    public static DiskLruCacheManager mInstance;

    public static DiskLruCacheManager newInstance(Context context) {
        if (mInstance == null) {
            synchronized (DiskLruCacheManager.class) {
                if (mInstance == null) {
                    mInstance = new DiskLruCacheManager(context);
                }
            }
        }
        return mInstance;
    }

    private DiskLruCacheManager(Context context) {
        mFileDiskCache = AndroidUtil.getDiskCacheDir(context, uniqueName);
        if (!mFileDiskCache.exists()) {
            mFileDiskCache.mkdirs();
        }
        mAppVersion = AndroidUtil.getAppVersion(context);
        open();
    }

    private void open() {
        try {
            mDiskLruCache = DiskLruCache.open(mFileDiskCache, mAppVersion, mValueCount, mMaxSize);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "DiskLruCache open error: " + e.getMessage());
        }
    }

    public void writeCacheToDisk(InputStream in,String key) {
        try {
            if (mDiskLruCache != null) {
                Bitmap bitmap = null;
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if ( HttpUtil.writeToDiskCache(in, outputStream)) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public InputStream readCacheFromDisk(String key) {
        try {
            DiskLruCache.Snapshot snapShot = null;
            snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                return snapShot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将缓存记录同步到journal文件中。
     */
    public void flushDiskCache() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validateKey(String key) {
        return !TextUtils.isEmpty(key);
    }

    public DiskLruCache getDiskLruCache() {
        return mDiskLruCache;
    }
}
