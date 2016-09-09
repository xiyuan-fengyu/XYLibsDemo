package com.xiyuan;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xiyuan.http.HttpBean;
import com.xiyuan.image.FrescoConfig;
import com.xiyuan.util.ActivityUtil;
import com.xiyuan.util.PreferenceUtil;

/**
 * Created by xiyuan_fengyu on 2016/7/22.
 */
public abstract class BasicApp extends Application{

    private static BasicApp instance = null;

    public static BasicApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //ActivityUtil初始化
        ActivityUtil.initialize(this);

        //网络请求
        HttpBean.initialize(this);

        //本地SharedPreference存储和读取工具初始化
        PreferenceUtil.initialize(this);

        FrescoConfig.initCacheDir(this, getImageCacheDir());
        Fresco.initialize(this, FrescoConfig.getImagePipelineConfig(this));
    }

    protected abstract String getImageCacheDir();

}
