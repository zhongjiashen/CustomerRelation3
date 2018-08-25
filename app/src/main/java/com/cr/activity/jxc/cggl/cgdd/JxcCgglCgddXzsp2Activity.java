package com.cr.activity.jxc.cggl.cgdd;

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
import android.widget.Toast;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.adapter.DropMenuAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzsplbActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddXzsp2Adapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.WeChatCaptureActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 进销存-采购管理-采购订单-选择商品(批号不是必填)（采购订单、销售订单）
 *
 * @author Administrator
 */
public class JxcCgglCgddXzsp2Activity extends BaseActivity implements
        OnClickListener {
    @BindView(R.id.tv_jxtj)
    TextView tvJxtj;
    private JxcCgglCgddXzsp2Adapter adapter;
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
    private String barcode;//新增条码


    DropDownMenu dropDownMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_cggl_cgdd_xzsp);
        ButterKnife.bind(this);
        addFHMethod();
        if (this.getIntent().hasExtra("rkckId")) {
            storeid = this.getIntent().getExtras().getString("rkckId");
        }
        if (this.getIntent().hasExtra("tabname")) {
            tabname = this.getIntent().getExtras().getString("tabname");
        }
        //判断是否是扫一扫
        if (this.getIntent().hasExtra("barcode")) {
            barcode = this.getIntent().getExtras().getString("barcode");
            tvJxtj.setVisibility(View.VISIBLE);
        }
        initActivity();
        initListView();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        qsrq = sdf.format(new Date()) + "01";
        jzrq = sdf.format(new Date())
                + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        fenleiDate();
        mTaxrate = getIntent().getStringExtra("taxrate");
        issj = getIntent().getBooleanExtra("issj", true);
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        dropDownMenu = findViewById(R.id.dropDownMenu);
        flImageButton = (ImageButton) findViewById(R.id.fl);
        flImageButton.setOnClickListener(this);

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
        listView = (XListView) findViewById(R.id.mFilterContentView);
        adapter = new JxcCgglCgddXzsp2Adapter(list, this, storeid);
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
        parmMap.put("barcode", barcode);//新增条码
        parmMap.put("goodsname", searchEditText.getText().toString());
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData2(0, ServerURL.SELECTGOODS, parmMap, false);
    }

    /**
     * 连接网络的操作
     */
    private void fenleiDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        findServiceData2(1, ServerURL.GOODSTYPE, parmMap, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnJson.equals("")) {
            showToastPromopt(2);
        } else {
            switch (returnSuccessType) {
                case 0:
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
                        list.add(obj2);
                    }
                    adapter.notifyDataSetChanged();

                    break;
                case 1://获取分类数据

                    String[] titleList = new String[]{"分类"};
                    List[] contextList = new List[]{(List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson)};
                    dropDownMenu.setMenuAdapter(new DropMenuAdapter(activity, titleList, contextList, new OnFilterDoneListener() {
                        @Override
                        public void onFilterDone(int position, Map map) {
                            dropDownMenu.setPositionIndicatorText(position, map.get("name").toString());
                            dropDownMenu.close();
                            code = map.get("lcode").toString();
                            list.clear();
                            searchDate();
                        }


                    }));
                    searchDate();
                    break;
            }

        }

    }

    @OnClick({R.id.fl, R.id.tv_jxtj, R.id.tv_qrxz})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.fl:
                intent.setClass(activity, CommonXzsplbActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_jxtj:
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class), 18);
                break;
            case R.id.tv_qrxz:
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = list.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = list.get(i + 1);
                            if (map2.get("sl").equals("0") || map2.get("sl").equals("")) {
                                Toast.makeText(activity, "请输入已选择商品的数量信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
//                         if(map2.get("batchctrl").equals("T")&&map2.get("cpph").equals("")){
//                        	 Toast.makeText(activity, "选择的批号商品，必须填写批号信息", Toast.LENGTH_SHORT).show();
//                        	 return;
//                         }
                            map2.put("dj", FigureTools.sswrFigure(map2.get("dj").toString()));
                        }
                    }
                }
                intent.putExtra("object", (Serializable) list);
                setResult(RESULT_OK, intent);
                finish();
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
                case 18:
                    currentPage=1;
                   barcode= data.getStringExtra("qr");
                    searchDate();
                    break;
            }
//            if (requestCode == 0) {
//                int index = Integer.parseInt(data.getExtras()
//                        .getString("index"));
//                list.get(index).put("cpph", data.getExtras().getString("name"));
//                list.get(index).put("scrq", data.getExtras().getString("scrq"));
//                list.get(index).put("yxqz", data.getExtras().getString("yxrq"));
//                adapter.notifyDataSetChanged();
//            } else if (requestCode == 1) {
//                // list.remove(adapter.getSelectIndex());
//                // adapter.notifyDataSetChanged();
//            } else if (requestCode == 2) {
//                code = data.getExtras().getString("id");
//                list.clear();
//                searchDate();
//            } else if (requestCode == 3) {
//                int index = Integer.parseInt(data.getExtras()
//                        .getString("index"));
//                list.get(index).put("dj", data.getExtras().getString("dj"));
//                adapter.notifyDataSetChanged();
//            }
        }
    }


}
//    @Override
//    public void onClick(View arg0) {
//        Intent intent = new Intent();
//        switch (arg0.getId()) {
//            case R.id.qr_textview:
//                for (int i = 0; i < list.size(); i++) {
//                    Map<String, Object> map = list.get(i);
//                    if (map.get("isDetail").equals("0")) {
//                        if (map.get("ischecked").equals("1")) {
//                            Map<String, Object> map2 = list.get(i + 1);
//                            if (map2.get("sl").equals("0") || map2.get("sl").equals("")) {
//                                Toast.makeText(activity, "请输入已选择商品的数量信息", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
////                         if(map2.get("batchctrl").equals("T")&&map2.get("cpph").equals("")){
////                        	 Toast.makeText(activity, "选择的批号商品，必须填写批号信息", Toast.LENGTH_SHORT).show();
////                        	 return;
////                         }
//                            map2.put("dj", FigureTools.sswrFigure(map2.get("dj").toString()));
//                        }
//                    }
//                }
//                intent.putExtra("object", (Serializable) list);
//                setResult(RESULT_OK, intent);
//                finish();
//                break;
//            case R.id.fl:
//                intent.setClass(activity, CommonXzsplbActivity.class);
//                startActivityForResult(intent, 2);
//                break;
//        }
//    }