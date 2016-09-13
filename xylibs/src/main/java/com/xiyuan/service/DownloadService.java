package com.xiyuan.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.webkit.MimeTypeMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiyuan_fengyu on 2016/9/13.
 */
public class DownloadService extends Service {

    private IBinder binder;

    public boolean isServiceRunning = true;

    private List<String> downloadQueen = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        if(binder == null)
        {
            binder = new DownloadBinder();
        }
        return binder;
    }

    public void newDownload(String url)
    {
        if(isServiceRunning)
        {
            downloadQueen.add(url);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(){
            @Override
            public void run() {
                while (isServiceRunning)
                {
                    int size = downloadQueen.size();
                    if(size > 0)
                    {
                        String url = downloadQueen.remove(size - 1);
                        Uri resource = Uri.parse(url);
                        DownloadManager.Request request = new DownloadManager.Request(resource);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                        request.setAllowedOverRoaming(false);
                        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
                        request.setMimeType(mimeString);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        request.setVisibleInDownloadsUi(true);

                        String[] strs = url.split("/");
                        String fileName = strs[strs.length - 1];
                        request.setDestinationInExternalPublicDir("/", fileName);
                        request.setTitle(fileName);

                        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                        downloadManager.enqueue(request);
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        isServiceRunning = false;
                        break;
                    }
                }
            }
        }.start();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        isServiceRunning = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class DownloadBinder extends Binder
    {
        public DownloadService getService()
        {
            return  DownloadService.this;
        }
    }

}
