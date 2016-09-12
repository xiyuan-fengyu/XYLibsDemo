package com.xiyuan.xylibsdemo.activity;

import android.os.Bundle;
import android.app.Activity;

import com.xiyuan.util.XYLog;
import com.xiyuan.xylibsdemo.R;
import com.xiyuan.xylibsdemo.model.DemoItem;

import java.util.HashMap;

public class XYLogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xylog);

        XYLog.d("普通类型：", 1);

        int[] arr = {1,2,3};
        XYLog.d("数组：", arr);

        HashMap<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("msg", "test");
        XYLog.d("可迭代对象：", map);

        DemoItem item = new DemoItem("test", XYLogActivity.class, 1);
        XYLog.d("普通对象：", item);

        XYLog.d("多个不同类型的对象：", 1, "\n", arr, "\n", map, "\n", item);
    }

}
