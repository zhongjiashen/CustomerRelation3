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
import com.cr.activity.xjyh.fkd.XjyhFkdDetailActivity;
import com.cr.adapter.tjfx.kcbb.TjfxKcbbAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.WeChatCaptureActivity;

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
    @BindView(R.id.mFilterContentView)
    XListView xlistview;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private TjfxKcbbAdapter adapter;

    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String  storename = "", goodsname = "";
    private String barcode;//新增条码

    private String storeid= "";
    private String goodstypeid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjfx_kcbb);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        initListView();
//        searchDate();
        fenlei();
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
                    goodsname = search.getText().toString();
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

        parmMap.put("barcode", barcode);//新增条码
        parmMap.put("storeid", storeid.equals("") ? "0" : storeid);
        parmMap.put("goodstypeid ", goodstypeid);

        parmMap.put("goodsname", goodsname);
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
//		Log.v("dddd", "kkk");
        findServiceData2(0, ServerURL.STOREQUERYRPT, parmMap, false);
    }

    private void fenlei() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        findServiceData2(1, "multitype", parmMap, false);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 0:
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
        String[] titleList = new String[]{"仓库","类别"};
        List<View> views=new ArrayList<>();
        views.add(createSingleListView(ckList,0));
        views.add(createSingleListView(lbList,1));
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
                           case 0:
                               storeid=item.get("id").toString();
                               break;
                           case 1:
                               goodstypeid=item.get("lcode").toString();
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
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class), 18);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    storeid = data.getExtras().getString("storeid");
                    storename = data.getExtras().getString("storename");
                    goodsname = data.getExtras().getString("goodsname");
                    list.clear();
                    currentPage = 1;
                    searchDate();
                    break;
                case 18://扫一扫选择商品                    currentPage = 1;
                    currentPage = 1;
                    barcode = data.getStringExtra("qr");
                    list.clear();
                    searchDate();
                    break;
            }

        }
    }
}