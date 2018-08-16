package com.cr.activity.tjfx.kcbb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.activity.xjyh.fkd.XjyhFkdDetailActivity;
import com.cr.adapter.tjfx.kcbb.TjfxKcbbAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 统计分析-库存报表
 *
 * @author Administrator
 */
public class TjfxKcbbActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.xlistview)
    XListView xlistview;
    private TjfxKcbbAdapter adapter;

    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String storeid = "", storename = "", goodsname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjfx_kcbb);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        initListView();
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
       search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if (actionId == EditorInfo.IME_ACTION_SEARCH
                       || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                   list.clear();
                   currentPage = 1;
                   goodsname=search.getText().toString();
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

        adapter = new TjfxKcbbAdapter(list, this);
        xlistview.setAdapter(adapter);
        xlistview.setXListViewListener(xListViewListener);
        xlistview.setPullLoadEnable(true);
        xlistview.setPullRefreshEnable(false);
        xlistview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(context,
                        XjyhFkdDetailActivity.class);
//				intent.putExtra("billid", list.get(arg2 - 1).get("billid")
//						.toString());
                Map<String, Object> map = list.get(arg2 - 1);
                intent.putExtra("object", (Serializable) map);
                intent.setClass(activity, TjfxKcbbSpjgActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("storeid", storeid.equals("") ? "0" : storeid);
        parmMap.put("goodsname", goodsname);
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
//		Log.v("dddd", "kkk");
        findServiceData2(0, ServerURL.STOREQUERYRPT, parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnJson.equals("nmyqx")) {
            Toast.makeText(this, "您没有该功能菜单的权限！请与管理员联系！", Toast.LENGTH_SHORT)
                    .show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 300);
            return;
        }
        if (returnJson.equals("")) {
            showToastPromopt(2);
        } else {
            list.addAll((List<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson));
        }
        adapter.notifyDataSetChanged();
    }



    /**
     * 刷新
     */
    private IXListViewListener xListViewListener = new IXListViewListener() {
        @Override
        public void onRefresh() {
            handler.postDelayed(new Runnable() {// 实现延迟2秒加载刷新
                @Override
                public void run() {
                    // 不实现顶部刷新
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
    private void onLoad() {// 停止刷新和加载功能，并且显示时间
        xlistview.stopRefresh();
        xlistview.stopLoadMore();
        xlistview.setRefreshTime(new Date().toLocaleString());
    }



    @OnClick({R.id.sx, R.id.iv_scan})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.sx://筛选
                intent.setClass(context, TjfxKcbbSearchActivity.class);
                intent.putExtra("storeid", storeid);
                intent.putExtra("storename", storename);
                intent.putExtra("goodsname", goodsname);
                startActivityForResult(intent, 0);
                break;
            case R.id.iv_scan:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                storeid = data.getExtras().getString("storeid");
                storename = data.getExtras().getString("storename");
                goodsname = data.getExtras().getString("goodsname");
                list.clear();
                currentPage = 1;
                searchDate();
            }
        }
    }
}