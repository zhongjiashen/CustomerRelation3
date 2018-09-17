package com.cr.activity.jxc.ckgl.chtj;

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

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.adapter.MyMenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzsplbActivity;
import com.cr.adapter.jxc.ckgl.chtj.JxcCkglChtjXzspAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

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

/**
 * 进销存-库存管理-存货调价-选择商品
 *
 * @author Administrator
 */
public class JxcCkglChtjXzspActivity extends BaseActivity implements
        OnClickListener {
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private JxcCkglChtjXzspAdapter adapter;
    private XListView listView;
    EditText searchEditText;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String qsrq, jzrq, shzt = "0", cname;
    private TextView qrTextView;
    ImageButton flImageButton;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_chtj_xzsp);
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
        listView = (XListView) findViewById(R.id.mFilterContentView);
        adapter = new JxcCkglChtjXzspAdapter(list, this);
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
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        findServiceData2(1, "multitype", parmMap, false);
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("tabname", "tb_adjap");
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
        switch (returnSuccessType) {
            case 0:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    ArrayList<Map<String, Object>> myList = (ArrayList<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson);
                    for (Map<String, Object> obj : myList) {
                        obj.put("isDetail", "0");
                        obj.put("ischecked", "0");
                        list.add(obj);
                        Map<String, Object> obj2 = new HashMap<String, Object>();
                        obj2.put("isDetail", "1");
                        obj2.put("dw", "个");
                        obj2.put("sl", obj.get("onhand").toString());
                        obj2.put("tqdj", FigureTools.sswrFigure(obj.get("aprice").toString()));
                        obj2.put("thdj", "");
                        list.add(obj2);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case 1:
                searchDate();
                List<Map<String, Object>> fenlinList = (List<Map<String, Object>>) PaseJson
                        .paseJsonToObject(returnJson);
                List<Map<String, Object>> ckList = new ArrayList<>();
                List<Map<String, Object>> lbList = new ArrayList<>();
                Map<String, Object> map=new HashMap<>();
                map.put("name","全部");
                map.put("id","0");
                map.put("lcode","0");
                lbList.add(map);
                ckList.add(map);
                for (int i = 0; i < fenlinList.size(); i++) {
                    switch (fenlinList.get(i).get("lb").toString()) {
                        case "1":
                            ckList.add(fenlinList.get(i));
                            break;
                        case "2":
                            lbList.add(fenlinList.get(i));
                            break;
                    }
                }
                setMenu(ckList,lbList);
                break;
        }

    }
    private void setMenu( List<Map<String, Object>> ckList,List<Map<String, Object>> lbList){
        String[] titleList = new String[]{"类别"};
        List<View> views=new ArrayList<>();
//        views.add(createSingleListView(ckList,0));
        views.add(createSingleListView(lbList,0));
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

                        }
                        currentPage=1;
                        list.clear();
                        searchDate();

                    }
                });


        singleListView.setList(items, -1);

        return singleListView;
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
                            if (map2.get("thdj").toString().equals("")) {
                                showToastPromopt("请输入调后单价！");
                                return;
                            }
                            map2.put("tqdj", FigureTools.sswrFigure(map2.get("tqdj").toString()));
                            map2.put("thdj", FigureTools.sswrFigure(map2.get("thdj").toString()));
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
            }
        }
    }
}