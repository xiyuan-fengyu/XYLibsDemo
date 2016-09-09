package com.xiyuan.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by YT on 2015/8/25.
 * ViewPager中嵌套使用PhotoView的时候高频率手势缩放，会出现异常，这个重写就是为了解决这个问题
 */
public class MyViewPager extends ViewPager {

    private boolean scrollable = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(scrollable)
        {
            return super.onTouchEvent(ev);
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(scrollable)
        {
            try {
                return super.onInterceptTouchEvent(event);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
