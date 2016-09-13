package com.xiyuan.xylibsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.xiyuan.receiver.DownloadReceiver;
import com.xiyuan.util.ApkInstallUtil;
import com.xiyuan.util.DownloadUtil;
import com.xiyuan.util.XYLog;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateActivity extends Activity implements DownloadReceiver.OnReceiveListener {

    @Bind(R.id.updateTV)
    TextView updateTV;

    private DownloadUtil downloadUtil;

    private String downloadUrl = "http://www.315che.com/apk/chinaCar_20160913_19.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        ButterKnife.bind(this);

        downloadUtil = new DownloadUtil(this);
    }

    @OnClick(R.id.updateTV)
    public void onClick() {
        downloadUtil.newDownload(downloadUrl);
    }

    @Override
    public void onDownloadCompleted(String filePath) {
        try {
            String[] split = downloadUrl.split("/");
            String downloadName = split[split.length - 1];

            split = filePath.split("/");
            String fileName = split[split.length - 1];
            if (fileName.replace(".apk", "").indexOf(downloadName.replace(".apk", "")) == 0) {
                //开始安装apk
                ApkInstallUtil.install(this, filePath);
            }
        }
        catch (Exception e) {
            XYLog.e(e);
        }
    }

    @Override
    protected void onDestroy() {
        downloadUtil.destory();
        super.onDestroy();
    }
}
