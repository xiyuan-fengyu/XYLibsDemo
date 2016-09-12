package com.xiyuan.xylibsdemo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiyuan.image.ImgLoader;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImgLoadActivity extends Activity {

    @Bind(R.id.testImg)
    SimpleDraweeView testImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_load);
        ButterKnife.bind(this);

        new ImgLoader("http://img.lanrentuku.com/img/allimg/1304/13650654047668.jpg", testImg);
    }

}
