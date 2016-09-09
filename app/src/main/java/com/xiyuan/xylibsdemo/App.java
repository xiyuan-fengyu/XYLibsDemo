package com.xiyuan.xylibsdemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xiyuan.BasicApp;
import com.xiyuan.http.HttpBean;
import com.xiyuan.image.FrescoConfig;

/**
 * Created by xiyuan_fengyu on 2016/7/22.
 */
public class App extends BasicApp{

    @Override
    protected String getImageCacheDir() {
        return "xiyuan/AndroidLibs/image";
    }

}
