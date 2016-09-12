package com.xiyuan.xylibsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.xiyuan.http.HttpBean;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HttpActivity extends Activity {

    @Bind(R.id.resultTV)
    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http);
        ButterKnife.bind(this);

        new HttpBean(this, "http://xiyuanfengyu.duapp.com/test/session", 0) {
            @Override
            public void onResponseSuccess(JsonObject jsonObject) {
                super.onResponseSuccess(jsonObject);
                resultTV.setText(jsonObject.toString());
            }

            @Override
            public void onErrorResponse(Exception e) {
                super.onErrorResponse(e);
                resultTV.setText("加载失败");
            }
        }.start();
    }



}
