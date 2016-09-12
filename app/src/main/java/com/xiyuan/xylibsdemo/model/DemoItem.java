package com.xiyuan.xylibsdemo.model;

/**
 * Created by xiyuan_fengyu on 2016/9/12.
 */
public class DemoItem {

    public String name;

    public Class<?> activity;

    public int level;

    public DemoItem(String name, Class<?> activity, int level) {
        this.name = name;
        this.activity = activity;
        this.level = Math.min(Math.max(1, level), 10);
    }

}
