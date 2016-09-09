package com.xiyuan.util;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xiyuan_fengyu on 2016/7/25.
 */
public class ActivityUtil {

    private static final ArrayList<Activity> activities = new ArrayList<>();

    public static void initialize(Application context) {
        context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activities.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activities.remove(activity);
            }
        });
    }

    public static ArrayList<Activity> getActivities() {
        return activities;
    }

    public static void startActivity(Activity from, Class<?> to) {
        startActivity(from, to, null);
    }

    public static void startActivity(Activity from, Class<?> to, Serializable param) {
        if (from != null && to != null) {
            Intent intent = new Intent(from, to);
            if (param != null) {
                intent.putExtra(param.getClass().getName(), param);
            }
            from.startActivity(intent);
        }
    }

    public static <T extends Serializable> T getParam(Activity activity, Class<T> clazz) {
        Intent intent = activity.getIntent();
        Object obj = intent.getSerializableExtra(clazz.getName());
        if (obj != null) {
            return (T) obj;
        }
        else {
            return null;
        }
    }

}
