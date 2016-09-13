package com.xiyuan.xylibsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiyuan.dialog.MaterialDialog;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends Activity {

    @Bind(R.id.showTV)
    TextView showTV;

    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        ButterKnife.bind(this);

        dialog = new MaterialDialog(this);
        dialog.setDialogTitle("Just a test!");
        dialog.setDialogMessage("一个简单的使用示例");
        dialog.setDialogNegative("取消");
        dialog.setDialogPositive("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DialogActivity.this, "点击了确定", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.showTV)
    public void onClick() {
        dialog.show();
    }
}
