package com.cr.activity.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cr.activity.BaseActivity;
import com.cr.adapter.common.CommonXzzdAdapter;
import com.crcxj.activity.R;

/**
 * 公用模块-选择付款类型
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class CommonXzfklxActivity extends BaseActivity implements OnClickListener {
    private CommonXzzdAdapter adapter;
    private ListView          listView;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String                    type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_xzzd);
        addFHMethod();
        initActivity();
        initListView();

    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        listView = (ListView) findViewById(R.id.listview);
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, Object> map = list.get(arg2);
                Intent intent = new Intent();
                intent.putExtra("id", map.get("id").toString());
                intent.putExtra("name", map.get("dictmc").toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", i);
            switch (i) {
                case 0:
                    map.put("dictmc", "应付账款");
                    break;
                case 1:
                    map.put("dictmc", "预付账款");
                    break;
                case 2:
                    map.put("dictmc", "预付冲应付");
                    break;
                default:
                    break;
            }
            list.add(map);
        }
        adapter = new CommonXzzdAdapter(list, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void executeSuccess() {
    }

    @Override
    public void onClick(View arg0) {
    }
}