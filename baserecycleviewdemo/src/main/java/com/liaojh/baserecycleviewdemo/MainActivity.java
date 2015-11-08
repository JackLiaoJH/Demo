package com.liaojh.baserecycleviewdemo;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.TemplatesHandler;



public class MainActivity extends AppCompatActivity implements PullRefreshRecyclerView.OnRefreshListener, PullRefreshRecyclerView.OnLoadMoreListener
{
    private PullRefreshRecyclerView mRecyclerView;
    MyAdapter adapter;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    adapter.addItem("new Item",0);
//                    adapter.removeItem(0);
                    mRecyclerView.setRefreshComplement(true);
                    break;
                case 2:
//                    adapter.addDatas(getDatas());
                    mRecyclerView.setLoadMoreComplement(true);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (PullRefreshRecyclerView) findViewById(R.id.recycler_view);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

//        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new MyAdapter(this, getDatas());

        TextView textView = new TextView(this);
        textView.setText("正在加载中..");
        mRecyclerView.addFooterView(textView);


        mRecyclerView.setAdapter(adapter);
        adapter.setItemOnClickListener(new MyAdapter.OnItemOnClickListener()
        {
            @Override
            public void onItemOnClick(View view, int position)
            {
                Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT)
                     .show();
            }
        });

        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setLoadMoreListener(this);
    }

    private List<String> getDatas()
    {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 31; i++)
        {
            dataList.add("Item  " + i);
        }
        return dataList;
    }

    @Override
    public void onRefresh()
    {
        Toast.makeText(MainActivity.this, "刷新中...", Toast.LENGTH_SHORT).show();

        new Thread(){
            @Override
            public void run()
            {
                super.run();
                SystemClock.sleep(3000);

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    @Override
    public void onLoadMore()
    {
        new Thread(){
            @Override
            public void run()
            {
                super.run();
                SystemClock.sleep(3000);
                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }
}
