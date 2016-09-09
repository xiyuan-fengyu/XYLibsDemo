package com.xiyuan.xylibsdemo.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.xiyuan.http.HttpBean;
import com.xiyuan.image.ImgLoader;
import com.xiyuan.util.PermissionUtil;
import com.xiyuan.util.PreferenceUtil;
import com.xiyuan.util.XYLog;
import com.xiyuan.view.CircleLoadingView;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.testImg)
    SimpleDraweeView testImg;
    @Bind(R.id.loading)
    CircleLoadingView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        XYLog.setLogTag("xiyuan");
        int[] testArr = {1, 2, 3};
        XYLog.d(this, "\n", testArr, "\nlog 测试");

        PermissionUtil.apply(this, 0,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        );

        new ImgLoader("http://img.lanrentuku.com/img/allimg/1304/13650654047668.jpg", testImg);

        loading.show();
        new HttpBean(this, "http://xiyuanfengyu.duapp.com/test/session", 0) {
            @Override
            public void onResponseSuccess(JsonObject jsonObject) {
                super.onResponseSuccess(jsonObject);
                Snackbar.make((View) testImg.getParent(), jsonObject.toString(), Snackbar.LENGTH_LONG).show();
                loading.hide();
            }

            @Override
            public void onErrorResponse(Exception e) {
                super.onErrorResponse(e);
                loading.hide();
            }
        }.start();

        PreferenceUtil.put("just_test_key", "just_test_value");
        Toast.makeText(this, PreferenceUtil.getString("just_test_key", ""), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        XYLog.d("权限申请结果：\n", requestCode, "\t", permissions, "\t", grantResults);
    }

}
