package com.xiyuan.xylibsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.xiyuan.util.SignatureUtil;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignatureActivity extends Activity {

    @Bind(R.id.md5TV)
    TextView md5TV;
    @Bind(R.id.sha1TV)
    TextView sha1TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature);
        ButterKnife.bind(this);

        md5TV.setText("签名md5:\n" + SignatureUtil.get(this, SignatureUtil.MD5));
        sha1TV.setText("签名sha1:\n" + SignatureUtil.get(this, SignatureUtil.SHA1));
    }

}
