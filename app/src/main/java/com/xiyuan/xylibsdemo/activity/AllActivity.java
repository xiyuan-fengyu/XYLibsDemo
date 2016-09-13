package com.xiyuan.xylibsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiyuan.image.ImgLoader;
import com.xiyuan.util.ActivityUtil;
import com.xiyuan.util.XYLog;
import com.xiyuan.xylibsdemo.R;
import com.xiyuan.xylibsdemo.model.DemoItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AllActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.allRv)
    RecyclerView allRv;

    private ArrayList<DemoItem> allDatas;

    private AllAdapter allAdapter;

    private int[] colors = new int[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all);
        ButterKnife.bind(this);

        colors[0] = getResources().getColor(R.color.grey_99);
        colors[1] = getResources().getColor(R.color.grey_66);
        colors[2] = getResources().getColor(R.color.light_blue);
        colors[3] = getResources().getColor(R.color.cyan);
        colors[4] = getResources().getColor(R.color.blue);
        colors[5] = getResources().getColor(R.color.green);
        colors[6] = getResources().getColor(R.color.orange);
        colors[7] = getResources().getColor(R.color.violet);
        colors[8] = getResources().getColor(R.color.pink);
        colors[9] = getResources().getColor(R.color.red);

        allDatas = new ArrayList<>();
        allDatas.add(new DemoItem("XYLog日志打印,包括基本类型，数组，可迭代对象，普通对象的打印", XYLogActivity.class, 1));
        allDatas.add(new DemoItem("android6.0权限申请", PermissionActivity.class, 5));
        allDatas.add(new DemoItem("利用fresco的SimpleDraweeView加载图片", ImgLoadActivity.class, 6));
        allDatas.add(new DemoItem("利用volley请求服务器API", HttpActivity.class, 8));
        allDatas.add(new DemoItem("loading加载动画", LoadingAnimActivity.class, 4));
        allDatas.add(new DemoItem("SharedPreference工具类的使用", PreferenceActivity.class, 3));
        allDatas.add(new DemoItem("获取签名信息", SignatureActivity.class, 4));

        allAdapter = new AllAdapter(allDatas);
        allRv.setLayoutManager(new LinearLayoutManager(this));
        allRv.setAdapter(allAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.demoItem: {
                DemoItem item = (DemoItem) v.getTag(R.id.tag_obj);
                ActivityUtil.startActivity(AllActivity.this, item.activity);
                break;
            }
        }
    }

    private class AllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<DemoItem> datas;

        public AllAdapter(ArrayList<DemoItem> datas) {
            this.datas = datas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.demo_item, parent, false);
            return new DemoHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            DemoItem item = datas.get(position);
            DemoHolder demoHolder = (DemoHolder) holder;
            demoHolder.demoName.setText(item.name);
            demoHolder.demoName.setTextColor(colors[item.level - 1]);
            demoHolder.divider.setVisibility(position == datas.size() - 1? View.GONE: View.VISIBLE);
            demoHolder.itemView.setTag(R.id.tag_obj, item);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class DemoHolder extends RecyclerView.ViewHolder{
            public TextView demoName;
            public View divider;
            public DemoHolder(View itemView) {
                super(itemView);
                this.demoName = (TextView) itemView.findViewById(R.id.demoName);
                this.divider = itemView.findViewById(R.id.divider);
                itemView.setOnClickListener(AllActivity.this);
            }
        }

    }


}
