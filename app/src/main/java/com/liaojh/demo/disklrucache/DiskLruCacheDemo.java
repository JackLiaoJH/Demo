package com.liaojh.demo.disklrucache;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liaojh.demo.MainActivity;
import com.liaojh.demo.R;
import com.liaojh.demo.resources.Images;

/**
 * @author LiaoJH
 * @DATE 15/10/13
 * @VERSION 1.0
 * @DESC TODO
 */
public class DiskLruCacheDemo extends Activity {


    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disk_cache_test);
        mRecyclerView = (RecyclerView) findViewById(R.id.disk_imgs);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView imageView = ((MyViewHolder) holder).mDiskImageView;
            String imgUrl = Images.imageThumbUrls[position];
            LightSpeed.load(DiskLruCacheDemo.this, imgUrl, imageView);
        }

        @Override
        public int getItemCount() {
            return Images.imageThumbUrls.length;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mDiskImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mDiskImageView = (ImageView) itemView.findViewById(R.id.iv_cache_disk);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LightSpeed.flushCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        LightSpeed.cancelAllTasks();
    }
}
