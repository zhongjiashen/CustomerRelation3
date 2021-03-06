package com.cr.activity.jxc.ckgl.kcpd;

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
import com.baiiu.filter.adapter.MyMenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzsplbActivity;
import com.cr.adapter.jxc.ckgl.kcpd.JxcCkglKcpdXzspAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.AppData;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.WeChatCaptureActivity;
import com.update.model.Serial;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 进销存-库存管理-库存盘点-选择商品
 *
 * @author Administrator
 */
@Deprecated
public class JxcCkglKcpdXzspActivity extends BaseActivity implements
        OnClickListener {
    @BindView(R.id.tv_jxtj)
    TextView tvJxtj;
    private JxcCkglKcpdXzspAdapter adapter;
    private XListView listView;
    EditText searchEditText;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String qsrq, jzrq, shzt = "0", cname, pdckId = "";
    private TextView qrTextView;
    ImageButton flImageButton;
    private String code;
    DropDownMenu dropDownMenu;
    private String barcode;//新增条码
    private String cartypeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_kcpd_xzsp);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        initListView();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        qsrq = sdf.format(new Date()) + "01";
        jzrq = sdf.format(new Date())
                + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        fenlei();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        dropDownMenu = findViewById(R.id.dropDownMenu);


        if (this.getIntent().hasExtra("pdckId")) {
            pdckId = this.getIntent().getExtras().getString("pdckId");
        }

        //判断是否是扫一扫
        if (this.getIntent().hasExtra("barcode")) {
            barcode = this.getIntent().getExtras().getString("barcode");
            tvJxtj.setVisibility(View.VISIBLE);
        }
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
        adapter = new JxcCkglKcpdXzspAdapter(list, this, pdckId);
        listView.setAdapter(adapter);
        listView.setXListViewListener(xListViewListener);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(false);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
            }
        });
    }

    private void fenlei() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        findServiceData2(1, "multitype", parmMap, false);
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));

        parmMap.put("storeid", pdckId);
        parmMap.put("goodscode", "");
        parmMap.put("goodstype", code);
        parmMap.put("cartypeid", cartypeid);
        parmMap.put("barcode", barcode);//新增条码
        parmMap.put("goodsname", searchEditText.getText().toString());
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData2(0, ServerURL.SELECTGOODSKCPD, parmMap, false);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {

        switch (returnSuccessType) {
            case 0:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                    return;
                }
                ArrayList<Map<String, Object>> myList = (ArrayList<Map<String, Object>>) PaseJson
                        .paseJsonToObject(returnJson);
                for (Map<String, Object> obj : myList) {
                    obj.put("isDetail", "0");
                    obj.put("ischecked", "0");
                    list.add(obj);
                    Map<String, Object> obj2 = new HashMap<String, Object>();
                    obj2.put("isDetail", "1");
                    obj2.put("zmsl", obj.get("onhand").toString());
                    obj2.put("spsl", obj.get("onhand").toString());
                    obj2.put("aprice", obj.get("aprice").toString());
                    obj2.put("yksl", "0");
                    obj2.put("cpph", "");
                    obj2.put("scrq", "");
                    obj2.put("yxqz", "");
                    obj2.put("memo", "");
                    obj2.put("batchctrl", obj.get("batchctrl").toString());
                    UUID uuid = UUID.randomUUID();
                    obj2.put("serialinfo", uuid.toString().toUpperCase());
                    obj2.put("serials", new ArrayList<Serial>());
                    list.add(obj2);
                }
                adapter.notifyDataSetChanged();
                break;
            case 1://获取分类数据
                searchDate();
                List<Map<String, Object>> fenlinList = (List<Map<String, Object>>) PaseJson
                        .paseJsonToObject(returnJson);

                List<Map<String, Object>> lbList = new ArrayList<>();
                List<Map<String, Object>> cllxList = new ArrayList<>();
                Map<String, Object> map=new HashMap<>();
                map.put("name","全部");
                map.put("id","0");
                map.put("lcode","0");
                lbList.add(map);
                cllxList.add(map);
                for (int i = 0; i < fenlinList.size(); i++) {
                    switch (fenlinList.get(i).get("lb").toString()) {
                        case "2":
                            lbList.add(fenlinList.get(i));
                            break;
                        case "3":
                            cllxList.add(fenlinList.get(i));
                            break;
                    }
                }
                setMenu(lbList,cllxList);
                break;
        }

    }
    private void setMenu( List<Map<String, Object>> lbList,List<Map<String, Object>> cllxList){
        String[] titleList;
        List<View> views=new ArrayList<>();
        views.add(createSingleListView(lbList,0));
        if(AppData.AppType==1){//判断是否是汽配版
            titleList = new String[]{"类别","车辆类别"};
            views.add(createSingleListView(cllxList,1));
        }else {
            titleList = new String[]{"类别"};
        }
        dropDownMenu.setMenuAdapter(new MyMenuAdapter(activity, titleList, views));
    }
    private View createSingleListView(List<Map<String, Object>> items, final int kind) {
        SingleListView<Map<String, Object>> singleListView = new SingleListView<Map<String, Object>>(activity)
                .adapter(new SimpleTextAdapter<Map<String, Object>>(null, activity) {
                    @Override
                    public String provideText(Map<String, Object> string) {
                        return string.get("name").toString();
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(activity, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<Map<String, Object>>() {
                    @Override
                    public void onItemClick(Map<String, Object> item) {
                        dropDownMenu.close();
                        dropDownMenu.setPositionIndicatorText(kind,item.get("name").toString());
                        switch (kind){
                            case 0://商品类别
                                code=item.get("id").toString();
                                break;
                            case 1://车辆类别
                                cartypeid=item.get("id").toString();
                                break;

                        }
                        currentPage=1;
                        list.clear();
                        searchDate();

                    }
                });


        singleListView.setList(items, -1);

        return singleListView;
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
//                        if(map2.get("sl").equals("0")||map2.get("sl").equals("")){
//                       	 Toast.makeText(activity, "请输入已选择商品的数量信息", Toast.LENGTH_SHORT).show();
//                       	 return;
//                        }
                            if (map2.get("batchctrl").equals("T") && map2.get("cpph").equals("")) {
                                Toast.makeText(activity, "选择的批号商品，必须填写批号信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (map.get("serialctrl").equals("T")) {
                                ArrayList<Serial> serials = (ArrayList<Serial>) map2.get("serials");
                                if (serials.size() != Integer.parseInt(map2.get("sl").toString())) {
                                    Toast.makeText(activity, "商品序列号个数与数量不一致", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
//                        map2.put("dj",FigureTools.sswrFigure(map2.get("dj").toString()) );
                        }
                    }
                }
                intent.putExtra("object", (Serializable) list);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    int index = Integer.parseInt(data.getExtras()
                            .getString("index"));
                    list.get(index).put("cpph", data.getExtras().getString("name"));
                    list.get(index).put("scrq", data.getExtras().getString("scrq"));
                    list.get(index).put("yxqz", data.getExtras().getString("yxrq"));
                    list.get(index).put("zmsl", data.getExtras().getString("onhand"));
                    list.get(index).put("spsl", data.getExtras().getString("onhand"));
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
                case 11:
                    int i = data.getExtras()
                            .getInt("position");
                    List<Serial> serials = new Gson().fromJson(data.getExtras().getString("DATA"), new TypeToken<List<Serial>>() {
                    }.getType());
                    list.get(i).put("serials", serials);
                    if (list.size() != 0 && list.get(i).get("serialctrl").equals("T")) {

                        list.get(i).put("sl", serials.size());

                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 18://扫一扫选择商品
                    currentPage = 1;
                    barcode = data.getStringExtra("qr");
                    searchDate();
                    break;
            }
           /* if (requestCode == 0) {
                int index = Integer.parseInt(data.getExtras()
                        .getString("index"));
                list.get(index).put("cpph", data.getExtras().getString("name"));
                list.get(index).put("scrq", data.getExtras().getString("scrq"));
                list.get(index).put("yxqz", data.getExtras().getString("yxrq"));
                list.get(index).put("zmsl", data.getExtras().getString("onhand"));
                list.get(index).put("spsl", data.getExtras().getString("onhand"));
                adapter.notifyDataSetChanged();
            } else if (requestCode == 1) {
                // list.remove(adapter.getSelectIndex());
                // adapter.notifyDataSetChanged();
            } else if (requestCode == 2) {
                code = data.getExtras().getString("id");
                list.clear();
                searchDate();
            } else if (requestCode == 11) {
                int index = data.getExtras()
                        .getInt("position");
                list.get(index).put("serials", new Gson().fromJson(data.getExtras().getString("DATA"), new TypeToken<List<Serial>>() {
                }.getType()));

                adapter.notifyDataSetChanged();
            }*/
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
////                        if(map2.get("sl").equals("0")||map2.get("sl").equals("")){
////                       	 Toast.makeText(activity, "请输入已选择商品的数量信息", Toast.LENGTH_SHORT).show();
////                       	 return;
////                        }
//                            if (map2.get("batchctrl").equals("T") && map2.get("cpph").equals("")) {
//                                Toast.makeText(activity, "选择的批号商品，必须填写批号信息", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
////                        map2.put("dj",FigureTools.sswrFigure(map2.get("dj").toString()) );
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