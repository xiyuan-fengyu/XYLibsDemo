package com.xiyuan.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.xiyuan.receiver.DownloadReceiver;
import com.xiyuan.service.DownloadService;

/**
 * Created by xiyuan_fengyu on 2016/9/13.
 */
public class DownloadUtil {

    private Context context;

    private DownloadService downloadService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadService = ((DownloadService.DownloadBinder)service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            downloadService = null;
        }
    };

    private DownloadReceiver downloadReceiver;

    public DownloadUtil(Context context) {
        this.context = context;

        Intent downloadServiceItt = new Intent(context, DownloadService.class);
        context.bindService(downloadServiceItt, serviceConnection, Context.BIND_AUTO_CREATE);

        downloadReceiver = new DownloadReceiver((DownloadReceiver.OnReceiveListener) context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(1000);
        intentFilter.addAction("android.intent.action.DOWNLOAD_COMPLETE");
        intentFilter.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED");
        context.registerReceiver(downloadReceiver, intentFilter);
    }

    public void newDownload(String url) {
        downloadService.newDownload(url);
    }

    public void newDownload(DownloadService.Download download) {
        downloadService.newDownload(download);
    }

    public void destory() {
        if(downloadService != null)
        {
            context.unbindService(serviceConnection);
        }
        if(downloadReceiver != null)
        {
            context.unregisterReceiver(downloadReceiver);
        }
    }

}
