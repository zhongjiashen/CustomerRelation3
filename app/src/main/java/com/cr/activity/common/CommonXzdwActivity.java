package com.cr.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.GzptKhglActivity;
import com.cr.activity.GzptKhzlLxrActivity;
import com.cr.activity.GzptKhzlXzdwActivity;
import com.cr.activity.gzpt.dwzl.GzptDwzlDwBjdwActivity;
import com.cr.adapter.common.CommonXzdwAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公用模块-选择单位
 *
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class CommonXzdwActivity extends BaseActivity implements OnClickListener {
    private CommonXzdwAdapter adapter;
    private XListView listView;
    private EditText searchEditText;
    private String type = "0";//单位的类型 客户：types=1（）,供应商types=2,竞争对手types=3,渠道types=4,员工types=5
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_xzdw);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        initListView();
        list.clear();
        if (this.getIntent().hasExtra("type")) {
            type = this.getIntent().getExtras().getString("type");
        }
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        listView = (XListView) findViewById(R.id.listview);
        searchEditText = (EditText) findViewById(R.id.search_edittext);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    list.clear();
                    currentPage = 1;
                    searchDate();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        adapter = new CommonXzdwAdapter(list, this, type);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, Object> map = list.get(arg2 - 1);
                Intent intent = new Intent();
                intent.putExtra("id", map.get("id").toString());
                intent.putExtra("cname", map.get("cname").toString());
                intent.putExtra("name", map.get("cname").toString());
                intent.putExtra("types", map.get("types").toString());
                intent.putExtra("code", map.get("code").toString());

                intent.putExtra("yf", map.get("suramt").toString());
                intent.putExtra("yf2", map.get("balance").toString());
                intent.putExtra("qk", map.get("oweamt").toString());
                intent.putExtra("lxrid", map.get("lxrid").toString());
                intent.putExtra("lxrname", map.get("lxrname").toString());
                intent.putExtra("typesname", map.get("typesname").toString());
                intent.putExtra("phone", map.get("phone").toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        listView.setXListViewListener(xListViewListener);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(false);
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("types", type);
        parmMap.put("filter", searchEditText.getText().toString());
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData2(0, ServerURL.CLIENTLIST, parmMap, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson.equals("")) {
                showToastPromopt(2);
            } else {
                list.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
                adapter.notifyDataSetChanged();
            }
        }
    }
    @OnClick(R.id.xz)
    public void onClick() {
        Intent intent=new Intent(activity, GzptDwzlDwBjdwActivity.class);
        intent.putExtra("clientid", "0");// 单位ID为0，表示新增
        startActivity(intent);
    }
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.search_imageview:
                list.clear();
                searchDate();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
           if (requestCode == 0) {//新增成功
                list.clear();
                currentPage = 1;
                searchDate();
            }
        }
    }
    /**
     * 刷新
     */
    private IXListViewListener xListViewListener = new IXListViewListener() {
        @Override
        public void onRefresh() {
            handler.postDelayed(new Runnable() {//实现延迟2秒加载刷新
                @Override
                public void run() {
                    //不实现顶部刷新
                }
            }, 2000);
        }

        @Override
        public void onLoadMore() {
            handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
                @Override
                public void run() {
                    currentPage++;
                    searchDate();
                    onLoad();
                }
            }, 2000);
        }
    };

    @SuppressWarnings("deprecation")
    private void onLoad() {//停止刷新和加载功能，并且显示时间
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(new Date().toLocaleString());
    }


}
