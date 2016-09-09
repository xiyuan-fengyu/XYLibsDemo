package com.xiyuan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by YT on 2015/8/13.
 */

/**
 * 能够兼容ViewPager的ScrollView
 * @Description: 解决了ViewPager在ScrollView中的滑动反弹问题
 */
public class MyScrollView extends ScrollView {
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    private ScrollListener mScrollListener;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDistance = yDistance = 0f;
                    xLast = ev.getX();
                    yLast = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float curX = ev.getX();
                    final float curY = ev.getY();

                    xDistance += Math.abs(curX - xLast);
                    yDistance += Math.abs(curY - yLast);
                    xLast = curX;
                    yLast = curY;

                    if(xDistance > yDistance){
                        return false;
                    }
            }

            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private int vScrollRange = 0;
    private int hScrollRange = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        vScrollRange = computeVerticalScrollRange();
        hScrollRange = computeHorizontalScrollRange();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){
        if(mScrollListener != null)
        {
            int b = t + getHeight();
            if(b >=  vScrollRange * 0.95){
                //ScrollView滑动到底部了
                if (b == vScrollRange) {
                    mScrollListener.scrollBottom();
                }
                else {
                    mScrollListener.scrollNearBottom();
                }
            }

        }
    }

    public void setScrollListener(ScrollListener scrollBottomListener){
        this.mScrollListener = scrollBottomListener;
    }

    public interface ScrollListener{
        public void scrollBottom();

        public void scrollNearBottom();
    }

}
