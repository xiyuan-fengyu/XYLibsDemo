package com.xiyuan.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YT on 2015/8/13.
 */
public class LoopPageAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {

    protected Context context;

    private List<T> datas;

    protected List<View> viewList;

    private ViewPager viewPager;

    private boolean isSelectedChanged  = false;

    protected int curPosition;

    public boolean showPageIndex = true;

    public boolean autoChange = true;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(((Activity)context).hasWindowFocus())
            {
                if(curPosition + 1 < getCount())
                {
                    viewPager.setCurrentItem(curPosition + 1, true);
                }
            }
        }
    };

    public LoopPageAdapter(ViewPager viewPager, List<T> picUrls)
    {
        this.context = viewPager.getContext();
        this.viewPager = viewPager;
        this.datas = picUrls;
        this.viewList = new ArrayList<>();
        if(datas.size() > 1)
        {
            viewList.add(getView(datas.get(datas.size() - 1)));
        }
        for (int i = 0; i < datas.size(); i ++)
        {
            viewList.add(getView(datas.get(i)));
        }
        if(datas.size() > 1)
        {
            viewList.add(getView(datas.get(0)));
        }
        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(this);
        viewPager.setCurrentItem(viewList.size() > 1 ? 1 : 0);
        changeViewPageScroller(600);
        if(autoChange)
        {
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 2800);
        }
    }

    public void restartAutoLoop()
    {
        if(autoChange)
        {
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 2800);
        }
    }

    public void stopAutoLoop()
    {
        handler.removeMessages(0);
    }

    @Override
    public void notifyDataSetChanged() {
        viewPager.removeAllViews();
        viewList.clear();
        if(datas.size() > 1) {
            viewList.add(getView(datas.get(datas.size() - 1)));
        }
        for (int i = 0; i < datas.size(); i ++)
        {
            viewList.add(getView(datas.get(i)));
        }
        if(datas.size() > 1)
        {
            viewList.add(getView(datas.get(0)));
        }
        super.notifyDataSetChanged();
        viewPager.setCurrentItem(datas.size() > 1 ? 1 : 0, false);
        if(autoChange)
        {
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, 2800);
        }
    }

    protected View getView(T data) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView img = new ImageView(context);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setLayoutParams(layoutParams);
        return img;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        if(position < viewList.size())
        {
            container.removeView(viewList.get(position));
        }
    }

    @Override
    public int getItemPosition(Object object) {
        if (((View)object).getParent() == null) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(viewList.get(position).getParent() == null)
        {
            container.addView(viewList.get(position));
        }
        return viewList.get(position);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int position) {
        isSelectedChanged = true;
        int count = getCount();
        if(position == 0)
        {
            curPosition = count - 2;
        }
        else if(position == count - 1)
        {
            curPosition = 1;
        }
        else
        {
            curPosition = position;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(ViewPager.SCROLL_STATE_IDLE == state)
        {
            if(isSelectedChanged)
            {
                isSelectedChanged = false;
                if(curPosition != viewPager.getCurrentItem())
                {
                    viewPager.setCurrentItem(curPosition, false);
                }
            }
            if(autoChange)
            {
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 2800);
            }
        }
        else
        {
            handler.removeMessages(0);
        }
    }

    private void changeViewPageScroller(int duration) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            MyScroller scroller = new MyScroller(context,new LinearInterpolator());
            scroller.setmDuration(duration);
            mField.set(viewPager, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class MyScroller extends Scroller {
        private int mDuration = 1600;

        public MyScroller(Context context) {
            super(context);
        }

        public MyScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy,
                                int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }

    }

}