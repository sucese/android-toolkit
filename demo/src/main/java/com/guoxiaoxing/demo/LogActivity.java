package com.guoxiaoxing.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.guoxiaoxing.toolkit.log.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LogActivity extends AppCompatActivity {

    private static final String TAG = LogActivity.class.getSimpleName();

    @Bind(R.id.btn_log_normal)
    Button mBtnLogNormal;
    @Bind(R.id.btn_log_json)
    Button mBtnLogJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);

        mBtnLogNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.t(TAG).d("This is a normal log");
            }
        });

        mBtnLogJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.json("[\n" +
                        "{\n" +
                        "     \"text\":\"This is the text\",\"color\":\"dark_red\",\"bold\":\"true\",\"strikethough\":\"true\",\"clickEvent\":\n" +
                        "          {\"action\":\"open_url\",\"value\":\"zh.wikipedia.org\"},\n" +
                        "     \"hoverEvent\":\n" +
                        "          {\"action\":\"show_text\",\"value\":\n" +
                        "               {\"extra\":\"something\"}\n" +
                        "          }\n" +
                        "},\n" +
                        "{\n" +
                        "     \"translate\":\"item.dirt.name\",\"color\":\"blue\",\"italic\":\"true\"\n" +
                        "}\n" +
                        "]");
            }
        });
    }
}
