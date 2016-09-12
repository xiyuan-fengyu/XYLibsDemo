package com.xiyuan.xylibsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiyuan.view.CircleLoadingView;
import com.xiyuan.xylibsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadingAnimActivity extends Activity {

    @Bind(R.id.loading)
    CircleLoadingView loading;
    @Bind(R.id.startTV)
    TextView startTV;
    @Bind(R.id.stopTV)
    TextView stopTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_anim);
        ButterKnife.bind(this);

        loading.hide(false);
    }


    @OnClick({R.id.startTV, R.id.stopTV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startTV: {
                loading.show();
                break;
            }
            case R.id.stopTV: {
                loading.hide();
                break;
            }
        }
    }

}
