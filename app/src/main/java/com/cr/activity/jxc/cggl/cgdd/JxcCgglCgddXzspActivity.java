package com.cr.activity.jxc.cggl.cgdd;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.cr.activity.common.CommonXzsplbActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddXzspAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.model.Serial;

/**
 * 进销存-采购管理-采购订单-选择商品（采购收货、采购退货、销售开单、销售退货）
 *
 * @author Administrator
 */
public class JxcCgglCgddXzspActivity extends BaseActivity implements
        OnClickListener {
    private JxcCgglCgddXzspAdapter adapter;
    private XListView listView;
    EditText searchEditText;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String qsrq, jzrq, shzt = "0", cname;
    private TextView qrTextView;
    ImageButton flImageButton;
    private String code;
    private String storeid = "0";
    private String tabname = "";
    private String mTaxrate;//税率
    private boolean issj;//发票类型是否是收据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_cggl_cgdd_xzsp);
        addFHMethod();
        if (this.getIntent().hasExtra("rkckId")) {
            storeid = this.getIntent().getExtras().getString("rkckId");
        }
        if (this.getIntent().hasExtra("tabname")) {
            tabname = this.getIntent().getExtras().getString("tabname");
        }


        initActivity();
        initListView();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        qsrq = sdf.format(new Date()) + "01";
        jzrq = sdf.format(new Date())
                + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        searchDate();

        mTaxrate=getIntent().getStringExtra("taxrate");
        issj=getIntent().getBooleanExtra("issj",true);
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        flImageButton = (ImageButton) findViewById(R.id.fl);
        flImageButton.setOnClickListener(this);
        qrTextView = (TextView) findViewById(R.id.qr_textview);
        qrTextView.setOnClickListener(this);
        searchEditText = (EditText) findViewById(R.id.search);
        searchEditText
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
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
        listView = (XListView) findViewById(R.id.xlistview);
        adapter = new JxcCgglCgddXzspAdapter(list, this, storeid, getIntent().getBooleanExtra("xskd", false),
                getIntent().getStringExtra("type"));
        listView.setAdapter(adapter);
        listView.setXListViewListener(xListViewListener);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(false);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // Intent intent = new Intent(context,
                // JxcCgglCgddDetailActivity.class);
                // intent.putExtra("billid",
                // list.get(arg2-1).get("billid").toString());
                // startActivityForResult(intent,1);
                // adapter.setSelectIndex(arg2);
            }
        });
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("tabname", tabname);
        parmMap.put("storeid", storeid);
        parmMap.put("goodscode", "");
        parmMap.put("goodstype", code);
        parmMap.put("goodsname", searchEditText.getText().toString());
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData2(0, ServerURL.SELECTGOODS, parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnJson.equals("")) {
            showToastPromopt(2);
        } else {
            ArrayList<Map<String, Object>> myList = (ArrayList<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson);
            if (myList == null) {
                return;
            }
            for (Map<String, Object> obj : myList) {
                obj.put("isDetail", "0");
                obj.put("ischecked", "0");
                list.add(obj);
                Map<String, Object> obj2 = new HashMap<String, Object>();
                obj2.put("isDetail", "1");
                obj2.put("dj", obj.get("aprice").toString());
                obj2.put("zkl", "100");
                obj2.put("sl", "1");
                obj2.put("cpph", "");
                obj2.put("scrq", "");
                obj2.put("yxqz", "");
                obj2.put("batchctrl", obj.get("batchctrl").toString());
                obj2.put("taxrate", mTaxrate);//初始化税率
                obj2.put("issj", issj);//发票类型是否是收据
                UUID uuid = UUID.randomUUID();
                obj2.put("serialinfo", uuid.toString().toUpperCase());//
                obj2.put("serials", new ArrayList<Serial>());//
                list.add(obj2);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.qr_textview:
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = list.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = list.get(i + 1);
                            if (map2.get("sl").equals("0") || map2.get("sl").equals("")) {
                                Toast.makeText(activity, "请输入已选择商品的数量信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (map2.get("batchctrl").equals("T") && map2.get("cpph").equals("")) {
                                Toast.makeText(activity, "选择的批号商品，必须填写批号信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            map2.put("dj", FigureTools.sswrFigure(map2.get("dj").toString()));
                        }
                    }
                }
                intent.putExtra("object", (Serializable) list);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.fl:
                intent.setClass(activity, CommonXzsplbActivity.class);
                startActivityForResult(intent, 2);
                break;
        }
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
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(new Date().toLocaleString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case 0:
                    int index = Integer.parseInt(data.getExtras()
                            .getString("index"));
                    list.get(index).put("cpph", data.getExtras().getString("name"));
                    list.get(index).put("scrq", data.getExtras().getString("scrq"));
                    list.get(index).put("yxqz", data.getExtras().getString("yxrq"));
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    // list.remove(adapter.getSelectIndex());
                    // adapter.notifyDataSetChanged();
                    break;
                case 2:
                    code = data.getExtras().getString("id");
                    list.clear();
                    searchDate();
                    break;
                case 3:
                    int i = Integer.parseInt(data.getExtras()
                            .getString("index"));
                    list.get(i).put("dj", data.getExtras().getString("dj"));
                    adapter.notifyDataSetChanged();
                    break;
                case 4:
                    break;
                case 11:
                    int n =data.getExtras()
                            .getInt("position");
                    list.get(n).put("serials", new Gson().fromJson(data.getExtras().getString("DATA"), new TypeToken<List<Serial>>() {
                    }.getType()));

                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    }
}
/*
 if (requestCode == 0) {
         int index = Integer.parseInt(data.getExtras()
         .getString("index"));
         list.get(index).put("cpph", data.getExtras().getString("name"));
         list.get(index).put("scrq", data.getExtras().getString("scrq"));
         list.get(index).put("yxqz", data.getExtras().getString("yxrq"));
         adapter.notifyDataSetChanged();
         } else if (requestCode == 1) {
         // list.remove(adapter.getSelectIndex());
         // adapter.notifyDataSetChanged();
         } else if (requestCode == 2) {
         code = data.getExtras().getString("id");
         list.clear();
         searchDate();
         } else if (requestCode == 3) {
         int index = Integer.parseInt(data.getExtras()
         .getString("index"));
         list.get(index).put("dj", data.getExtras().getString("dj"));
         adapter.notifyDataSetChanged();
         }*/
