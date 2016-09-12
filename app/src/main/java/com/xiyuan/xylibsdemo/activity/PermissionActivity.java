package com.xiyuan.xylibsdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import com.xiyuan.util.PermissionUtil;
import com.xiyuan.util.XYLog;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PermissionActivity extends Activity {

    @Bind(R.id.resultTV)
    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission);
        ButterKnife.bind(this);

        PermissionUtil.apply(this, 0,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        resultTV.setText(XYLog.argsToString("权限申请结果：\n", requestCode, "\t", permissions, "\t", grantResults));
    }

}
