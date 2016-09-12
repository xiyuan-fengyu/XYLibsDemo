package com.xiyuan.xylibsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiyuan.util.PreferenceUtil;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreferenceActivity extends Activity {

    @Bind(R.id.keyEdit)
    EditText keyEdit;
    @Bind(R.id.keyWapper)
    TextInputLayout keyWapper;
    @Bind(R.id.valueEdit)
    EditText valueEdit;
    @Bind(R.id.valueWapper)
    TextInputLayout valueWapper;
    @Bind(R.id.saveTV)
    TextView saveTV;
    @Bind(R.id.readTV)
    TextView readTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.saveTV, R.id.readTV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveTV: {
                String key = keyEdit.getText().toString();
                String value = valueEdit.getText().toString();
                PreferenceUtil.put(key, value);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.readTV: {
                String key = keyEdit.getText().toString();
                Toast.makeText(this, "读取成功，" + key + "的值为：" + PreferenceUtil.getString(key, ""), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
