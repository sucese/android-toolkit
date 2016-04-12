package com.guoxiaoxing.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.guoxiaoxing.demo.cache.ListAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rv_list)
    RecyclerView mRvList;

    private Context mContext;
    private ListAdapter mAdapter;
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initData(){
        mContext = MainActivity.this;

        mList.add("CacheActivity");
        mList.add("BitmapActivity");

        mAdapter = new ListAdapter(mContext);
        mAdapter.setList(mList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRvList.setLayoutManager(manager);
        mRvList.setHasFixedSize(true);
        mRvList.setAdapter(mAdapter);
    }
}
