package com.cr.activity.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.adapter.common.CommonXzphAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 公用模块-选择批号
 *
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class CommonXzphActivity extends BaseActivity implements OnClickListener {
    private CommonXzphAdapter adapter;
    private XListView listView;
    private EditText searchEditText;
    private String storied = "0";                                 //仓库ID 23
    //    private String            goodsid = "2396";                              //商品ID 2396
    private String index;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private ImageButton xzImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_xzph);
        addFHMethod();
        initActivity();
        initListView();
        list.clear();
        if (this.getIntent().hasExtra("storied")) {
            storied = this.getIntent().getExtras().getString("storied");
        }
        //		if(this.getIntent().hasExtra("goodsid")){
        //		    goodsid=this.getIntent().getExtras().getString("goodsid");
        //        }
        if (this.getIntent().hasExtra("index")) {
            index = this.getIntent().getExtras().get("index").toString();
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
        adapter = new CommonXzphAdapter(list, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, Object> map = list.get(arg2 - 1);
                Intent intent = new Intent();
                intent.putExtra("id", map.get("id").toString());
                intent.putExtra("name", map.get("batchcode").toString());
                intent.putExtra("scrq", map.get("produceddate").toString());
                intent.putExtra("yxrq", map.get("validdate").toString());
                intent.putExtra("onhand", map.get("onhand").toString());
                intent.putExtra("index", index);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        listView.setXListViewListener(xListViewListener);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        xzImageButton = (ImageButton) findViewById(R.id.xz);


        if (ShareUserInfo.getKey(activity, "cpphType").equals("cggl") || ShareUserInfo.getKey(activity, "cpphType").equals("kcpd")) {
            xzImageButton.setVisibility(View.VISIBLE);
        } else {
            xzImageButton.setVisibility(View.GONE);
        }
        if (getIntent().getStringExtra("type") != null) {
            switch (getIntent().getStringExtra("type")) {
                case "cgth":
                    xzImageButton.setVisibility(View.INVISIBLE);
                    break;
                case "xsth":
                    xzImageButton.setVisibility(View.VISIBLE);
                    break;
            }
        }

        xzImageButton.setOnClickListener(this);
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        //        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("storeid", storied.equals("") ? "0" : storied);
        parmMap.put("goodsid", this.getIntent().getExtras().getString("goodsid"));
//        parmMap.put("index", index);
        //        parmMap.put("curpage",currentPage);
        //        parmMap.put("pagesize",pageSize);
        findServiceData2(0, ServerURL.GETGOODSBATCH, parmMap, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            //		    Log.v("dddd", dwList.size()+"");
            if (returnJson.equals("")) {
                showToastPromopt(2);
            } else {
                list.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.search_imageview:
                list.clear();
                searchDate();
                break;
            case R.id.xz:
                final EditText inputServer = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("输入批号的名称").setView(inputServer)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("id", "0");
                        intent.putExtra("name", inputServer.getText().toString());
                        intent.putExtra("scrq", "");
                        intent.putExtra("yxrq", "");
                        intent.putExtra("onhand", "0");
                        intent.putExtra("index", index);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                builder.show();

                break;
            default:
                break;
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
