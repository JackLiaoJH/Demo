package com.liaojh.demo.disklrucache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ImageView;

import com.liaojh.demo.utils.HttpUtil;
import com.liaojh.demo.utils.IOUtils;
import com.liaojh.demo.utils.MD5Util;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LiaoJHø
 * @DATE 15/10/13
 * @VERSION 1.0
 * @DESC TODO
 */
public class LightSpeed {

    /**
     * 记录所有正在下载或等待下载的任务。
     */
    private static Set<LSAsyncTask> mTaskCollection;
    private static DiskLruCacheManager mLruCacheManager;

    public static void load(Context context, String imageUrl, ImageView imageView) {
        if (TextUtils.isEmpty(imageUrl))
            return;
        imageView.setTag(imageUrl);

        MemoryLruCacheManager memoryLruCacheManager = MemoryLruCacheManager.newInstance();
        Bitmap bitmap = memoryLruCacheManager.getDataFromMemoryCache(imageUrl);
        if (bitmap == null) {

            mLruCacheManager = DiskLruCacheManager.newInstance(context);
            mTaskCollection = new HashSet<>();
            LSAsyncTask task = new LSAsyncTask(mLruCacheManager, memoryLruCacheManager, imageView, imageUrl);
            mTaskCollection.add(task);
            task.execute();
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }


    public static class LSAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        private DiskLruCacheManager mLruCacheManager;
        private MemoryLruCacheManager mMemoryLruCacheManager;
        private ImageView mImageView;
        private String mImgUrl;


        public LSAsyncTask(DiskLruCacheManager lruCacheManager, MemoryLruCacheManager memoryLruCacheManager,
                           ImageView imageView, String imgUrl) {
            this.mLruCacheManager = lruCacheManager;
            mMemoryLruCacheManager = memoryLruCacheManager;
            mImageView = imageView;
            mImgUrl = imgUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            InputStream in = null;
            try {
                Bitmap bitmap = null;
                String key = MD5Util.hashKeyForDisk(mImgUrl);
                if (mLruCacheManager.validateKey(key)) {
                    //从磁盘获取
                    in = mLruCacheManager.readCacheFromDisk(key);
                    if (in == null) {
                        //从网络获取,且存到硬盘
                        in = HttpUtil.getInputStringFromUrl(mImgUrl);
                        mLruCacheManager.writeCacheToDisk(in, key);
                        //TODO 暂时找不到好的解决方法
                        in = mLruCacheManager.readCacheFromDisk(key);
                        bitmap = BitmapFactory.decodeStream(in);
                    } else {
                        //TODO 简单处理
                        bitmap = BitmapFactory.decodeStream(in);
                    }
                }
                mMemoryLruCacheManager.putDataToMemoryCache(mImgUrl, bitmap);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(in);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null && mImgUrl.equals(mImageView.getTag())) {
                mImageView.setImageBitmap(bitmap);
            }
            mTaskCollection.remove(this);
        }
    }

    /**
     * 取消所有正在下载或等待下载的任务,一般在onDestroy生命周期中调用
     */
    public static void cancelAllTasks() {
        if (mTaskCollection != null) {
            for (LSAsyncTask task : mTaskCollection) {
                task.cancel(false);
            }
        }
    }

    /**
     * 刷新所有数据到Disk缓存中去,一般在onPause生命周期调用
     */
    public static void flushCache() {
        if (mLruCacheManager != null) {
            mLruCacheManager.flushDiskCache();
        }
    }
}
