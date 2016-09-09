package com.xiyuan.image;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * Created by xiyuan_fengyu on 2016/7/22.
 */
public class ImgLoader extends BaseControllerListener<ImageInfo> {

    private SimpleDraweeView imageView = null;

    public ImgLoader(String url, SimpleDraweeView imageView) {
        this.imageView = imageView;

        Uri uri = Uri.parse(url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(this)
                .setUri(uri)
                .build();
        this.imageView.setController(controller);
    }

    @Override
    public final void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
        super.onFinalImageSet(id, imageInfo, animatable);
        onSuccess(imageView, id, imageInfo, animatable);
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        super.onFailure(id, throwable);
        onFailed(imageView, id, throwable);
    }

    protected void onSuccess(SimpleDraweeView imageView, String id, ImageInfo imageInfo, Animatable anim) {
        Log.d(ImgLoader.class.getSimpleName(), "image load success, id: " + id);
    }

    protected void onFailed(SimpleDraweeView imageView, String id, Throwable throwable) {
        Log.w(ImgLoader.class.getSimpleName(), "image load failed, id: " + id);
    }

}

