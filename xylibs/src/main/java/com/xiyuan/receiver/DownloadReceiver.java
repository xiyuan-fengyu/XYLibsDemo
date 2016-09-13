package com.xiyuan.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

/**
 * Created by xiyuan_fengyu on 2015/10/22.
 */
public class DownloadReceiver extends BroadcastReceiver {

    private OnReceiveListener listener;

    public DownloadReceiver(OnReceiveListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
            DownloadManager.Query query = new DownloadManager.Query();
            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(id);
            Cursor c = manager.query(query);
            if(c.moveToFirst()) {
                //获取文件下载路径
                String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                if(filePath != null && listener != null){
                    listener.onDownloadCompleted(filePath);
                }
            }
        }
        else if(DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(action)){
            long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
            //点击通知栏取消下载
            manager.remove(ids);
            Toast.makeText(context, "已经取消下载", Toast.LENGTH_LONG).show();
        }

    }

    public interface OnReceiveListener
    {
        void onDownloadCompleted(String filePath);
    }

}
