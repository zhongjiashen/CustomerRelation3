package com.cr.activity.gzpt.dwzl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.jxc.xsgl.xsdd.JxcXsglXsddAddActivity;
import com.cr.activity.jxc.xsgl.xsdd.JxcXsglXsddDetailActivity;
import com.cr.activity.khfw.KhfwAddActivity;
import com.cr.activity.khfw.KhfwDetailActivity;
import com.cr.activity.xm.BjxmActivity;
import com.cr.activity.xm.XzxmActivity;
import com.cr.adapter.SlidePageAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlBfAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlDdAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlFwAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlJhAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlLxrAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlXmAdapter;
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

/**
 * 工作平台-单位资料
 * 
 * @author Administrator
 * 
 */
public class GzptDwzlActivity extends BaseActivity implements OnClickListener {
    private TextView            dwTextView, lxrTextView, bfTextView, jhTextView, fwTextView,
            ddTextView,xmTextView, xzlxrTextView, bflrTextView, xzxsjhTextView, xzfwdTextView, xzxsddTextView,xzxmTextView;
    private ViewPager           viewPager;
    SlidePageAdapter            myAdapter;
    List<View>                  viewPage = new ArrayList<View>();
    private LayoutInflater      inflater;
    private View                dwView, lxrView, bfView, jhView, fwView, ddView,xmView;
    private XListView           lxrListView, bfListView, jhListView, fwListView, ddListView,xmListView;
    private boolean             islxr    = false, isBf = false, isJh = false, isFw = false,
            isDd = false,isXm=false;                                                                          // 是否是第一次加载
    private GzptDwzlLxrAdapter  lxrAdapter;
    private GzptDwzlBfAdapter   bfAdapter;
    private GzptDwzlJhAdapter   jhAdapter;
    private GzptDwzlFwAdapter   fwAdapter;
    private GzptDwzlDdAdapter   ddAdapter;
    private GzptDwzlXmAdapter   xmAdapter;
    List<Map<String, Object>>   lxrList  = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>>   bfList   = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>>   jhList   = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>>   fwList   = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>>   ddList   = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>>   xmList   = new ArrayList<Map<String, Object>>();
    private TextView            khbhTextView, khmcTextView, khdjTextView, khlxTextView,
            hylxTextView, khlyTextView, dqTextView, frdbTextView, czTextView, dzTextView,
            wzTextView, bzTextView;
    private RelativeLayout      lxfsRelativeLayout;
    private LinearLayout        bjdwLayout;
    private TextView            bjdwTextView, xzdwTextView;
    private ImageView           corsor1, corsor2, corsor3, corsor4, corsor5, corsor6,corsor7;
    private String              clientId="",khdjid="";                                                          // 单位的ID
    private String              types    = "";                                                     //客户的类型
    private Map<String, Object> object   = new HashMap<String, Object>();
    private Map<String, Object> dwObject = new HashMap<String, Object>();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_dwzl);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        clientId = ((Map<String, Object>) this.getIntent().getExtras().getSerializable("object"))
            .get("clientid").toString();
        object = (Map<String, Object>) this.getIntent().getExtras().getSerializable("object");
        types = ((Map<String, Object>) this.getIntent().getExtras().getSerializable("object")).get(
            "types").toString();

        initActivity();
        initDwListView();
        initLxrListView();
        initBfListView();
        initJhListView();
        initFwListView();
        initDdListView();
        initXmListView();
        addFHMethod();
        addZYMethod();
        searchDateDw(0);
        if (types.equals("1")) {//客户

        } else if (types.equals("2")) {//供应商
            jhTextView.setVisibility(View.GONE);
            fwTextView.setVisibility(View.GONE);
            ddTextView.setVisibility(View.GONE);
            corsor4.setVisibility(View.GONE);
            corsor5.setVisibility(View.GONE);
            corsor6.setVisibility(View.GONE);
            List<View> v = new ArrayList<View>();
            for (int i = 0; i < viewPage.size(); i++) {
                switch (i) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:
                        v.add(viewPage.get(i));
                        break;
                    case 4:
                        v.add(viewPage.get(i));
                        break;
                    case 5:
                        v.add(viewPage.get(i));
                        break;
                    default:
                        break;
                }
            }
            viewPage.removeAll(v);
        } else if (types.equals("3")) {//竞争对手
            jhTextView.setVisibility(View.GONE);
            fwTextView.setVisibility(View.GONE);
            ddTextView.setVisibility(View.GONE);
            corsor4.setVisibility(View.GONE);
            corsor5.setVisibility(View.GONE);
            corsor6.setVisibility(View.GONE);
            List<View> v = new ArrayList<View>();
            for (int i = 0; i < viewPage.size(); i++) {
                switch (i) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:
                        v.add(viewPage.get(i));
                        break;
                    case 4:
                        v.add(viewPage.get(i));
                        break;
                    case 5:
                        v.add(viewPage.get(i));
                        break;
                    default:
                        break;
                }
            }
            viewPage.removeAll(v);
        } else if (types.equals("4")) {//渠道

        } else if (types.equals("5")) {//员工
            dwTextView.setText("基本信息");
            bjdwLayout.setVisibility(View.GONE);
            lxrTextView.setVisibility(View.GONE);
            jhTextView.setVisibility(View.GONE);
            fwTextView.setVisibility(View.GONE);
            ddTextView.setVisibility(View.GONE);
            corsor2.setVisibility(View.GONE);
            corsor4.setVisibility(View.GONE);
            corsor5.setVisibility(View.GONE);
            corsor6.setVisibility(View.GONE);
            List<View> v = new ArrayList<View>();
            for (int i = 0; i < viewPage.size(); i++) {
                switch (i) {
                    case 0:

                        break;
                    case 1:
                        v.add(viewPage.get(i));
                        break;
                    case 2:

                        break;
                    case 3:
                        v.add(viewPage.get(i));
                        break;
                    case 4:
                        v.add(viewPage.get(i));
                        break;
                    case 5:
                        v.add(viewPage.get(i));
                        break;
                    default:
                        break;
                }
            }
            viewPage.removeAll(v);
        }
        myAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        dwTextView = (TextView) findViewById(R.id.dw_textview);
        dwTextView.setOnClickListener(this);
        lxrTextView = (TextView) findViewById(R.id.lxr_textview);
        lxrTextView.setOnClickListener(this);
        bfTextView = (TextView) findViewById(R.id.bf_textview);
        bfTextView.setOnClickListener(this);
        jhTextView = (TextView) findViewById(R.id.jh_textview);
        jhTextView.setOnClickListener(this);
        fwTextView = (TextView) findViewById(R.id.fw_textview);
        fwTextView.setOnClickListener(this);
        ddTextView = (TextView) findViewById(R.id.dd_textview);
        ddTextView.setOnClickListener(this);
        xmTextView = (TextView) findViewById(R.id.xm_textview);
        xmTextView.setOnClickListener(this);

        corsor1 = (ImageView) findViewById(R.id.corsor1);
        corsor2 = (ImageView) findViewById(R.id.corsor2);
        corsor3 = (ImageView) findViewById(R.id.corsor3);
        corsor4 = (ImageView) findViewById(R.id.corsor4);
        corsor5 = (ImageView) findViewById(R.id.corsor5);
        corsor6 = (ImageView) findViewById(R.id.corsor6);
        corsor7 = (ImageView) findViewById(R.id.corsor7);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        dwView = inflater.inflate(R.layout.activity_gzpt_dwzl_dw, null);
        lxrView = inflater.inflate(R.layout.activity_gzpt_dwzl_lxr, null);
        bfView = inflater.inflate(R.layout.activity_gzpt_dwzl_bf, null);
        jhView = inflater.inflate(R.layout.activity_gzpt_dwzl_jh, null);
        fwView = inflater.inflate(R.layout.activity_gzpt_dwzl_fw, null);
        ddView = inflater.inflate(R.layout.activity_gzpt_dwzl_dd, null);
        xmView = inflater.inflate(R.layout.activity_gzpt_dwzl_xm, null);
        viewPage.add(dwView);
        viewPage.add(lxrView);
        viewPage.add(bfView);
        viewPage.add(jhView);
        viewPage.add(fwView);
        viewPage.add(ddView);
        viewPage.add(xmView);
        myAdapter = new SlidePageAdapter(viewPage, this);
        viewPager.setAdapter(myAdapter);
        // 下面的点图
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    viewPager.setCurrentItem(0);
                    corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor7.setBackgroundResource(R.drawable.index_unselectcorsor);
                } else if (arg0 == 1) {
                    viewPager.setCurrentItem(1);
                    corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor7.setBackgroundResource(R.drawable.index_unselectcorsor);
                    if (!islxr) {
                        lxrList.clear();
                        searchDateLxr(1);
                        islxr = true;
                    }
                } else if (arg0 == 2) {
                    viewPager.setCurrentItem(2);
                    corsor3.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor7.setBackgroundResource(R.drawable.index_unselectcorsor);
                    if (!isBf) {
                        bfList.clear();
                        searchDateBf(2);
                        isBf = true;
                    }
                } else if (arg0 == 3) {
                    viewPager.setCurrentItem(3);
                    corsor4.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor7.setBackgroundResource(R.drawable.index_unselectcorsor);
                    if (!isJh) {
                        jhList.clear();
                        searchDateJh(3);
                        isJh = true;
                    }
                } else if (arg0 == 4) {
                    corsor5.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor7.setBackgroundResource(R.drawable.index_unselectcorsor);
                    if (!isFw) {
                        fwList.clear();
                        searchDateFw(4);
                        isFw = true;
                    }
                } else if (arg0 == 5) {
                    corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor6.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor7.setBackgroundResource(R.drawable.index_unselectcorsor);
                    if (!isDd) {
                        ddList.clear();
                        searchDateDd(5);
                        isDd = true;
                    }
                }else if (arg0 == 6) {
                    corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor7.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                    if (!isXm) {
                        xmList.clear();
                        searchDateXm(6);
                        isXm = true;
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // Log.v("dddd", "onPageScrollStateChanged");
            }
        });
    }

    /**
     * 初始化”单位“页面
     */
    private void initDwListView() {
        khbhTextView = (TextView) dwView.findViewById(R.id.khbh_textview);
        khmcTextView = (TextView) dwView.findViewById(R.id.khmc_textview);
        khdjTextView = (TextView) dwView.findViewById(R.id.khdj_textview);
        khlxTextView = (TextView) dwView.findViewById(R.id.khlx_textview);
        hylxTextView = (TextView) dwView.findViewById(R.id.hylx_textview);
        khlyTextView = (TextView) dwView.findViewById(R.id.khly_textview);
        dqTextView = (TextView) dwView.findViewById(R.id.dq_textview);
        frdbTextView = (TextView) dwView.findViewById(R.id.frdb_textview);
        czTextView = (TextView) dwView.findViewById(R.id.cz_textview);
        dzTextView = (TextView) dwView.findViewById(R.id.dz_textview);
        wzTextView = (TextView) dwView.findViewById(R.id.wz_textview);
        bzTextView = (TextView) dwView.findViewById(R.id.bz_textview);

        lxfsRelativeLayout = (RelativeLayout) dwView.findViewById(R.id.lxfs_relativelayout);
        lxfsRelativeLayout.setOnClickListener(this);
        bjdwTextView = (TextView) dwView.findViewById(R.id.bjdw_textview);
        bjdwTextView.setOnClickListener(this);
        xzdwTextView = (TextView) dwView.findViewById(R.id.xzdw_textview);
        xzdwTextView.setOnClickListener(this);
        bjdwLayout = (LinearLayout) dwView.findViewById(R.id.bjdw_layout);
    }

    /**
     * 初始化”联系人“页面
     */
    private void initLxrListView() {
        lxrListView = (XListView) lxrView.findViewById(R.id.listview);
        lxrAdapter = new GzptDwzlLxrAdapter(lxrList, activity);
        lxrListView.setXListViewListener(xListViewListenerLxr);
        lxrListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(GzptDwzlActivity.this, GzptDwzlLxrDetailActivity.class);
                intent.putExtra("lxrid", lxrList.get(arg2 - 1).get("id").toString());
                intent.putExtra("clientid", clientId);
                startActivity(intent);
            }
        });
        lxrListView.setPullLoadEnable(true);
        lxrListView.setPullRefreshEnable(false);
        lxrListView.setAdapter(lxrAdapter);
        xzlxrTextView = (TextView) lxrView.findViewById(R.id.xzlxr_textview);
        xzlxrTextView.setOnClickListener(this);
    }

    /**
     * 初始化”拜访“页面
     */
    private void initBfListView() {
        bfListView = (XListView) bfView.findViewById(R.id.listview);
        bfAdapter = new GzptDwzlBfAdapter(bfList, activity);
        bfListView.setXListViewListener(xListViewListenerBf);
        bfListView.setPullLoadEnable(true);
        bfListView.setPullRefreshEnable(false);
        bfListView.setAdapter(bfAdapter);
        bflrTextView = (TextView) bfView.findViewById(R.id.bflr_textview);
        bflrTextView.setOnClickListener(this);
        bfListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent=new Intent();
                intent.setClass(activity, GzptDwzlBfBflrDetailActivity.class);
                intent.putExtra("clientname", khmcTextView.getText());
                intent.putExtra("clientid", clientId);
                intent.putExtra("object",(Serializable)bfList.get(arg2-1));
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化”机会“页面
     */
    private void initJhListView() {
        jhListView = (XListView) jhView.findViewById(R.id.listview);
        jhAdapter = new GzptDwzlJhAdapter(jhList, activity);
        jhListView.setXListViewListener(xListViewListenerJh);
        jhListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.setClass(GzptDwzlActivity.this, GzptDwzlJhXzxsjhActivity.class);
                intent.putExtra("clientid", clientId);
                intent.putExtra("clientname", khmcTextView.getText());
                intent.putExtra("code", jhList.get(arg2 - 1).get("code").toString());
                intent.putExtra("chanceid", jhList.get(arg2 - 1).get("chanceid").toString());
                startActivityForResult(intent, 3);

            }
        });
        jhListView.setPullLoadEnable(true);
        jhListView.setPullRefreshEnable(false);
        jhListView.setAdapter(jhAdapter);
        xzxsjhTextView = (TextView) jhView.findViewById(R.id.xzxsjh_textview);
        xzxsjhTextView.setOnClickListener(this);
    }

    /**
     * 初始化”服务“页面
     */
    private void initFwListView() {
        fwListView = (XListView) fwView.findViewById(R.id.listview);
        fwAdapter = new GzptDwzlFwAdapter(fwList, activity);
        fwListView.setXListViewListener(xListViewListenerFw);
        fwListView.setPullLoadEnable(true);
        fwListView.setPullRefreshEnable(false);
        fwListView.setAdapter(fwAdapter);
        fwListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.putExtra("billid", fwList.get(arg2 - 1).get("id").toString());
                intent.setClass(activity, KhfwDetailActivity.class);
                startActivity(intent);
            }
        });
        xzfwdTextView = (TextView) fwView.findViewById(R.id.xzfwd_textview);
        xzfwdTextView.setOnClickListener(this);
    }

    /**
     * 初始化”订单“页面
     */
    private void initDdListView() {
        ddListView = (XListView) ddView.findViewById(R.id.listview);
        ddAdapter = new GzptDwzlDdAdapter(ddList, activity);
        ddListView.setXListViewListener(xListViewListenerDd);
        ddListView.setPullLoadEnable(true);
        ddListView.setPullRefreshEnable(false);
        ddListView.setAdapter(ddAdapter);
        xzxsddTextView = (TextView) ddView.findViewById(R.id.xzxsdd_textview);
        xzxsddTextView.setOnClickListener(this);
        ddListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.putExtra("billid", ddList.get(arg2 - 1).get("billid").toString());
                intent.setClass(activity, JxcXsglXsddDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    
    /**
     * 初始化”項目“页面
     */
    private void initXmListView() {
        xmListView = (XListView) xmView.findViewById(R.id.listview);
        xmAdapter = new GzptDwzlXmAdapter(xmList, activity);
        xmListView.setXListViewListener(xListViewListenerXm);
        xmListView.setPullLoadEnable(true);
        xmListView.setPullRefreshEnable(false);
        xmListView.setAdapter(xmAdapter);
        xzxmTextView = (TextView) xmView.findViewById(R.id.xzxm_textview);
        xzxmTextView.setOnClickListener(this);
        xmListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.putExtra("xmid", xmList.get(arg2 - 1).get("projectid").toString());
                intent.setClass(activity, BjxmActivity.class);
                startActivityForResult(intent,6);
            }
        });
    }

    /**
     * 连接网络的操作(单位)
     */
    private void searchDateDw(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("clientid", clientId);
        findServiceData(type, ServerURL.CLIENTINFO, parmMap);
    }

    /**
     * 连接网络的操作(联系人)
     */
    private void searchDateLxr(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("clientid", clientId);
        parmMap.put("lxrname", "");
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData(type, ServerURL.LXRLIST, parmMap);
    }

    /**
     * 连接网络的操作(拜访)
     */
    private void searchDateBf(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("clientid ", clientId);
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData(type, ServerURL.VISITINFO, parmMap);
    }

    /**
     * 连接网络的操作(机会)
     */
    private void searchDateJh(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("clientid", clientId);
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData(type, ServerURL.ITEMLIST, parmMap);
    }

    /**
     * 连接网络的操作(服务)
     */
    private void searchDateFw(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("clientid ", clientId);
        parmMap.put("qsrq", "");
        parmMap.put("zzrq", "");
        parmMap.put("billcode", "");
        parmMap.put("billtype", "");
        parmMap.put("shzt", "");
        parmMap.put("filter", "");
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData(type, ServerURL.SHWXINFO, parmMap);
    }

    /**
     * 连接网络的操作(订单)
     */
    private void searchDateDd(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_sorder");
        parmMap.put("qsrq", "1901-01-01");
        parmMap.put("zzrq", "3000-01-01");
        parmMap.put("billcode", "");
        parmMap.put("cname", "");
        parmMap.put("clientid ", clientId);
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData(type, ServerURL.BILLLIST, parmMap);
    }
    
    /**
     * 连接网络的操作(項目)
     */
    private void searchDateXm(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("clientid",clientId);
        parmMap.put("qsrq", "1901-01-01");
        parmMap.put("zzrq", "3000-01-01");
		parmMap.put("cname", "");
		parmMap.put("title", "");
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
        findServiceData(type, ServerURL.PROJECTLIST, parmMap);
    }

    /**
     * 监听事件
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lxfs_relativelayout:
                intent.putExtra("clientid", clientId);
                intent.setClass(GzptDwzlActivity.this, GzptDwzlDwLxfsActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.bjdw_textview:
                intent.putExtra("clientid", clientId);// 单位ID为0，表示新增
                intent.setClass(GzptDwzlActivity.this, GzptDwzlDwBjdwActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.xzdw_textview:
                intent.putExtra("clientid", "0");// 单位ID为0，表示新增
                intent.putExtra("khdjid", khdjid);// 客户类型的ID
                intent.putExtra("khlbid", types);// 客户类型的ID
                intent.putExtra("khdjname", khdjTextView.getText());// 客户类型 的名字
                intent.setClass(GzptDwzlActivity.this, GzptDwzlDwBjdwActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.dw_textview:
                viewPager.setCurrentItem(0);
//                corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
//                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                break;
            case R.id.lxr_textview:
                viewPager.setCurrentItem(1);
//                corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
//                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                if (!islxr) {
                    lxrList.clear();
                    searchDateLxr(1);
                    islxr = true;
                }
                break;
            case R.id.bf_textview:
                viewPager.setCurrentItem(2);
//                corsor3.setBackgroundResource(R.drawable.index_selectcorsor);
//                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                if (!isBf) {
                    bfList.clear();
                    searchDateBf(2);
                    isBf = true;
                }
                break;
            case R.id.jh_textview:
                viewPager.setCurrentItem(3);
//                corsor4.setBackgroundResource(R.drawable.index_selectcorsor);
//                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                if (!isJh) {
                    jhList.clear();
                    searchDateJh(3);
                    isJh = true;
                }
                break;
            case R.id.fw_textview:
                viewPager.setCurrentItem(4);
//                corsor5.setBackgroundResource(R.drawable.index_selectcorsor);
//                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor6.setBackgroundResource(R.drawable.index_unselectcorsor);
                if (!isFw) {
                    fwList.clear();
                    searchDateFw(4);
                    isFw = true;
                }
                break;
            case R.id.dd_textview:
                viewPager.setCurrentItem(5);
//                corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor6.setBackgroundResource(R.drawable.index_selectcorsor);
                if (!isDd) {
                    ddList.clear();
                    searchDateDd(5);
                    isDd = true;
                }
                break;
            case R.id.xm_textview:
                viewPager.setCurrentItem(6);
//                corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
//                corsor6.setBackgroundResource(R.drawable.index_selectcorsor);
                if (!isXm) {
                    xmList.clear();
                    searchDateXm(6);
                    isXm = true;
                }
                break;
            case R.id.xzlxr_textview:// 新增联系人
                intent.setClass(this, GzptDwzlLxrBjlxrActivity.class);
                intent.putExtra("lxrid", "0");
                intent.putExtra("clientid", clientId);
                startActivityForResult(intent, 0);
                break;
            case R.id.bflr_textview:
                intent.setClass(this, GzptDwzlBfBflrActivity.class);
                intent.putExtra("clientname", khmcTextView.getText());
                intent.putExtra("clientid", clientId);
                intent.putExtra("object", (Serializable) object);
                startActivityForResult(intent, 2);
                break;
            case R.id.xzxsjh_textview:
                intent.setClass(this, GzptDwzlJhXzxsjhActivity.class);
                intent.putExtra("clientid", clientId);
                intent.putExtra("clientname", khmcTextView.getText());
                startActivityForResult(intent, 3);
                break;
            case R.id.xzfwd_textview:
                intent.setClass(this, KhfwAddActivity.class);
                //			intent.putExtra("lxrid", "0");
                //			intent.putExtra("clientid", clientId);
                intent.putExtra("dwObject", (Serializable) dwObject);
                startActivityForResult(intent, 4);
                break;
            case R.id.xzxsdd_textview:
                intent.setClass(this, JxcXsglXsddAddActivity.class);
                //                intent.putExtra("lxrid", "0");
                //                intent.putExtra("clientid", clientId);
                intent.putExtra("dwObject", (Serializable) dwObject);
                startActivityForResult(intent, 5);
                break;
            case R.id.xzxm_textview:
                intent.setClass(this, XzxmActivity.class);
                //                intent.putExtra("lxrid", "0");
                //                intent.putExtra("clientid", clientId);
                intent.putExtra("dwObject", (Serializable) dwObject);

                startActivityForResult(intent, 6);
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 0:
                if (returnJson.equals("")) {
                    return;
                }
                dwObject = ((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson))
                    .get(0);
                khbhTextView.setText(dwObject.get("code").toString());
                khmcTextView.setText(dwObject.get("cname").toString());
                khdjTextView.setText(dwObject.get("typename").toString());
                khlxTextView.setText(dwObject.get("khtypename").toString());
                khdjid=dwObject.get("typeid").toString();
                hylxTextView.setText(dwObject.get("hylxname").toString());
                khlyTextView.setText(dwObject.get("khlyname").toString());
                dqTextView.setText(dwObject.get("areafullname").toString());
                frdbTextView.setText(dwObject.get("frdb").toString());
                czTextView.setText(dwObject.get("fax").toString());
                dzTextView.setText(dwObject.get("address").toString());
                wzTextView.setText(dwObject.get("cnet").toString());
                bzTextView.setText(dwObject.get("memo").toString());
                break;
            case 1:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    lxrList.addAll((List<Map<String, Object>>) PaseJson
                        .paseJsonToObject(returnJson));
                }
                lxrAdapter.notifyDataSetChanged();
                break;
            case 2:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    bfList
                        .addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
                }
                bfAdapter.notifyDataSetChanged();
                break;
            case 3:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    jhList
                        .addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
                }
                jhAdapter.notifyDataSetChanged();
                break;
            case 4:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    fwList
                        .addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
                }
                fwAdapter.notifyDataSetChanged();
                break;
            case 5:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    ddList
                        .addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
                }
                ddAdapter.notifyDataSetChanged();
                break;
            case 6:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
//                    Toast.makeText(GzptDwzlActivity.this,"fasf",Toast.LENGTH_SHORT).show();
                } else {
                    xmList
                        .addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
                }
                xmAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 刷新(联系人)
     */
    private IXListViewListener xListViewListenerLxr = new IXListViewListener() {
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
                                                                        searchDateLxr(1);
                                                                        onLoadLxr();
                                                                    }
                                                                }, 2000);
                                                        }
                                                    };

    @SuppressWarnings("deprecation")
    private void onLoadLxr() {// 停止刷新和加载功能，并且显示时间
        lxrListView.stopRefresh();
        lxrListView.stopLoadMore();
        lxrListView.setRefreshTime(new Date().toLocaleString());
    }

    /**
     * 刷新（拜访）
     */
    private IXListViewListener xListViewListenerBf = new IXListViewListener() {
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
                                                                       searchDateBf(2);
                                                                       onLoadBf();
                                                                   }
                                                               }, 2000);
                                                       }
                                                   };

    @SuppressWarnings("deprecation")
    private void onLoadBf() {// 停止刷新和加载功能，并且显示时间
        bfListView.stopRefresh();
        bfListView.stopLoadMore();
        bfListView.setRefreshTime(new Date().toLocaleString());
    }

    /**
     * 刷新(机会)
     */
    private IXListViewListener xListViewListenerJh = new IXListViewListener() {
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
                                                                       searchDateJh(3);
                                                                       onLoadJh();
                                                                   }
                                                               }, 2000);
                                                       }
                                                   };

    @SuppressWarnings("deprecation")
    private void onLoadJh() {// 停止刷新和加载功能，并且显示时间
        jhListView.stopRefresh();
        jhListView.stopLoadMore();
        jhListView.setRefreshTime(new Date().toLocaleString());
    }

    /**
     * 刷新(服务)
     */
    private IXListViewListener xListViewListenerFw = new IXListViewListener() {
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
                                                                       searchDateFw(4);
                                                                       onLoadFw();
                                                                   }
                                                               }, 2000);
                                                       }
                                                   };

    @SuppressWarnings("deprecation")
    private void onLoadFw() {// 停止刷新和加载功能，并且显示时间
        fwListView.stopRefresh();
        fwListView.stopLoadMore();
        fwListView.setRefreshTime(new Date().toLocaleString());
    }

    /**
     * 刷新(订单)
     */
    private IXListViewListener xListViewListenerDd = new IXListViewListener() {
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
                                                                       searchDateDd(5);
                                                                       onLoadDd();
                                                                   }
                                                               }, 2000);
                                                       }
                                                   };
                                                   @SuppressWarnings("deprecation")
                                                   private void onLoadDd() {// 停止刷新和加载功能，并且显示时间
                                                       ddListView.stopRefresh();
                                                       ddListView.stopLoadMore();
                                                       ddListView.setRefreshTime(new Date().toLocaleString());
                                                   }
                                                   /**
                                                    * 刷新(项目)
                                                    */
                                                   private IXListViewListener xListViewListenerXm = new IXListViewListener() {
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
                                                                                                                      searchDateXm(6);
                                                                                                                      onLoadXm();
                                                                                                                  }
                                                                                                              }, 2000);
                                                                                                      }
                                                                                                  };

    
    @SuppressWarnings("deprecation")
    private void onLoadXm() {// 停止刷新和加载功能，并且显示时间
        xmListView.stopRefresh();
        xmListView.stopLoadMore();
        xmListView.setRefreshTime(new Date().toLocaleString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {// 新增联系人
                lxrList.clear();
                searchDateLxr(1);
            } else if (requestCode == 1) {// 单位
                searchDateDw(0);
            } else if (requestCode == 2) {// 拜访
                bfList.clear();
                searchDateBf(2);
            } else if (requestCode == 3) {// 机会
                jhList.clear();
                searchDateJh(3);
            } else if (requestCode == 4) {// 服务
                fwList.clear();
                searchDateFw(4);
            } else if (requestCode == 5) {// 订单
                ddList.clear();
                searchDateDd(5);
            }else if (requestCode == 6) {// 項目
                xmList.clear();
                searchDateXm(6);
            }
        }
    }
}
