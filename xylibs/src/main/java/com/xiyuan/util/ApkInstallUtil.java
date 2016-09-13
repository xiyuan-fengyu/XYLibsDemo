package com.xiyuan.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by xiyuan_fengyu on 2016/9/13.
 */
public class ApkInstallUtil {

    public static void install(Context context, String filePath)
    {
        try {
            File apkfile = new File(filePath);
            if (!apkfile.exists())
            {
                return;
            }
            Intent installItt = new Intent(Intent.ACTION_VIEW);
            installItt.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            installItt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(installItt);
        }
        catch (Exception e) {
            XYLog.e(e);
        }
    }



}
