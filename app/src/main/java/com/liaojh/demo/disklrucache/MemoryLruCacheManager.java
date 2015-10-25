package com.liaojh.demo.disklrucache;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import java.util.Objects;

/**
 * @author LiaoJH
 * @DATE 15/10/14
 * @VERSION 1.0
 * @DESC TODO
 */
public class MemoryLruCacheManager {

    private int mMaxMemoryCacheSize;
    private LruCache<String, Bitmap> mMemoryLruCache;
    private static Object object = new Object();
    private static MemoryLruCacheManager mInstance;

    public static MemoryLruCacheManager newInstance() {
        if (mInstance == null) {
            synchronized (object) {
                if (mInstance == null) {
                    mInstance = new MemoryLruCacheManager();
                }
            }
        }
        return mInstance;
    }

    private MemoryLruCacheManager() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        mMaxMemoryCacheSize = maxMemory / 8;
        mMemoryLruCache = new LruCache<String, Bitmap>(mMaxMemoryCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //value.getHeight() * value.getRowBytes();
                return value.getByteCount();
            }
        };
    }

    public void putDataToMemoryCache(String key, Bitmap value) {
        if (TextUtils.isEmpty(key) || value == null)
            return;
        if (getDataFromMemoryCache(key) == null) {
            mMemoryLruCache.put(key, value);
        }
    }

    public Bitmap getDataFromMemoryCache(String key) {
        return TextUtils.isEmpty(key) ? null : mMemoryLruCache.get(key);
    }
}
