package com.xiyuan.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.HashSet;

/**
 * Created by xiyuan_fengyu on 2016/7/22.
 */
public class PermissionUtil {

    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void apply(Activity activity, int uniqueCode, String ...permissions) {
        HashSet<String> tempPermissionSet = new HashSet<>();
        for (String p: permissions) {
            Context context = activity.getApplicationContext();
            if (!hasPermission(context, p)) {
                tempPermissionSet.add(p);
            }
        }
        if (!tempPermissionSet.isEmpty()) {
            String[] tempPermissionArr = new String[tempPermissionSet.size()];
            tempPermissionSet.toArray(tempPermissionArr);
            ActivityCompat.requestPermissions(activity, tempPermissionArr, uniqueCode);
        }
    }

}
