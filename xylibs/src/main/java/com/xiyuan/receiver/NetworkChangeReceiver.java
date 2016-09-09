package com.xiyuan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by xiyuan_fengyu on 2016/7/29.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public final void onReceive(Context context, Intent intent) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            int nType = mNetworkInfo.getType();
            if (mNetworkInfo.isAvailable()) {
                if(nType==ConnectivityManager.TYPE_MOBILE)
                {
                    onChange(true, mNetworkInfo.getExtraInfo().toLowerCase());
                }
                else if(nType==ConnectivityManager.TYPE_WIFI)
                {
                    onChange(true, "wifi");
                }
                else {
                    onChange(true, "");
                }
            }
            else {
                onChange(false, "");
            }
        }
        else {
            onChange(false, "");
        }
    }

    protected void onChange(boolean isAvailable, String networkType) {

    }

}
