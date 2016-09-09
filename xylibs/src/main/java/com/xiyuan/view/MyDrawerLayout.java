package com.xiyuan.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xiyuan_fengyu on 2016/8/1.
 * 重写DrawerLayout,覆盖两个默认效果：
 * 1.灰色阴影改为透明阴影
 * 2.菜单拖出来之后，没被菜单挡住的部分能够响应触摸事件
 */
public class MyDrawerLayout extends DrawerLayout {
    public MyDrawerLayout(Context context) {
        super(context);
        setScrimTransparent();
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScrimTransparent();
    }

    public MyDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setScrimTransparent();
    }

    private void setScrimTransparent() {
        setScrimColor(Color.TRANSPARENT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        boolean flag = true;
        if (action == MotionEvent.ACTION_DOWN) {
            final float x = ev.getX();
            if (isDrawerOpen(GravityCompat.START)) {
                View startMenu = findDrawerWithGravity(GravityCompat.START);
                if (startMenu != null && x > startMenu.getMeasuredWidth()) {
                    flag =  false;
                }
            }
            else if (isDrawerOpen(GravityCompat.END)) {
                View endMenu = findDrawerWithGravity(GravityCompat.END);
                if (endMenu != null && x < getMeasuredWidth() - endMenu.getMeasuredWidth()) {
                    flag =  false;
                }
            }
        }

        flag = super.onInterceptTouchEvent(ev) && flag;
        return flag;
    }

    View findDrawerWithGravity(int gravity) {
        final int absHorizGravity = GravityCompat.getAbsoluteGravity(
                gravity, ViewCompat.getLayoutDirection(this)) & Gravity.HORIZONTAL_GRAVITY_MASK;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final int childAbsGravity = getDrawerViewAbsoluteGravity(child);
            if ((childAbsGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == absHorizGravity) {
                return child;
            }
        }
        return null;
    }

    int getDrawerViewAbsoluteGravity(View drawerView) {
        final int gravity = ((LayoutParams) drawerView.getLayoutParams()).gravity;
        return GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));
    }

}
