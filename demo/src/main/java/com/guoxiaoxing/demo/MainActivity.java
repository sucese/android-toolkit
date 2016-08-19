package com.guoxiaoxing.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.btn_log_activity)
    Button mBtnLog;

    private Context mContext;
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupView();
        setupData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void setupView() {
        mBtnLog.setOnClickListener(this);
    }

    private void setupData() {
        mContext = MainActivity.this;

        mList.add("CacheActivity");
        mList.add("BitmapActivity");
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_log_activity:
                startActivity(new Intent(mContext, LogActivity.class));
                break;
        }

    }
}
