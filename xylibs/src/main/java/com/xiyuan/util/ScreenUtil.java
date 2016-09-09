package com.xiyuan.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by xiyuan_fengyu on 2016/7/25.
 */
public class ScreenUtil {

    public static Size getSize(Activity context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new Size(metrics.widthPixels, metrics.heightPixels);
    }

    public static class Size
    {
        public int width;

        public int height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

}
