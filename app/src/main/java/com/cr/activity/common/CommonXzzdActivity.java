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
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 公用模块-选择字典
 *
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class CommonXzzdActivity extends BaseActivity implements OnClickListener {
    private CommonXzzdAdapter adapter;
    private ListView listView;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String type = "";
    boolean isDwlb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_xzzd);
        addFHMethod();
        initActivity();
        initListView();
        list.clear();
        if (this.getIntent().hasExtra("type")) {
            type = this.getIntent().getExtras().getString("type");
        }
        isDwlb = getIntent().getBooleanExtra("isDwlb", false);
        searchDate();
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
        adapter = new CommonXzzdAdapter(list, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, Object> map = list.get(arg2);
                String dictmc= map.get("dictmc").toString();
                if(type.equals("ZCLB")||type.equals("SRLB")){
                   String c = String.valueOf(dictmc.charAt(0));
                    if(!c.equals("|")){
                        showToastPromopt("必须选到最低级别");
                        return;
                    }
                    dictmc .replace("|", "");
                }

                Intent intent = new Intent();
                intent.putExtra("id", map.get("id").toString());
                intent.putExtra("dictmc", dictmc.replace(">>", ""));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("zdbm", type);
        findServiceData2(0, ServerURL.DATADICT, parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson.equals("")) {
                showToastPromopt(2);
            } else {
                list.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
                if (isDwlb) {//判断是否是单位新增和编辑选择类别，如果是删除员工项
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("dictmc").equals("员工"))
                            list.remove(i);

                    }

                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.search_imageview:
//		        list.clear();
//		        searchDate();
                break;
            default:
                break;
        }
    }
}