package com.cr.activity.gzpt.dwzl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.cr.activity.BaseActivity;
import com.cr.activity.gzpt.dwzl.main.FjzxPageAdapter;
import com.cr.activity.gzpt.dwzl.main.view.BaseView;
import com.cr.activity.gzpt.dwzl.main.view.ContactView;
import com.cr.activity.gzpt.dwzl.main.view.OpportunityView;
import com.cr.activity.gzpt.dwzl.main.view.OrdersView;
import com.cr.activity.gzpt.dwzl.main.view.ProjectView;
import com.cr.activity.gzpt.dwzl.main.view.ServiceView;
import com.cr.activity.gzpt.dwzl.main.view.UnitView;
import com.cr.activity.gzpt.dwzl.main.view.VisitView;
import com.cr.activity.jxc.xsgl.xsdd.JxcXsglXsddAddActivity;
import com.cr.activity.khfw.KhfwAddActivity;
import com.cr.activity.xm.XzxmActivity;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.project.AddProjectActivity;
import com.update.actiity.project.ProjectManagementActivity;
import com.update.actiity.sales.AddSalesOpportunitiesActivity;
import com.update.actiity.sales.SalesOpportunitiesManagementActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作平台-单位资料
 *
 * @author Administrator
 */
public class GzptDwzlActivity extends BaseActivity implements OnClickListener {
    private ViewPager viewPager;
    FjzxPageAdapter myAdapter;
    List<BaseView> viewPage = new ArrayList<BaseView>();
    private LayoutInflater inflater;
    private BaseView dwView, lxrView, bfView, jhView, fwView, ddView, xmView;

    private boolean islxr = false, isBf = false, isJh = false, isFw = false,
            isDd = false, isXm = false;                                                                          // 是否是第一次加载

    List<Map<String, Object>> lxrList = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> bfList = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> jhList = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> fwList = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> ddList = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> xmList = new ArrayList<Map<String, Object>>();


    private String clientId = "", khdjid = "", khmc = "";                                                          // 单位的ID
    private String types = "";                                                     //客户的类型
    private Map<String, Object> object = new HashMap<String, Object>();
    private Map<String, Object> dwObject = new HashMap<String, Object>();
    TabLayout tabLayout;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_dwzl_main);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        clientId = ((Map<String, Object>) this.getIntent().getExtras().getSerializable("object"))
                .get("clientid").toString();
        object = (Map<String, Object>) this.getIntent().getExtras().getSerializable("object");
        types = ((Map<String, Object>) this.getIntent().getExtras().getSerializable("object")).get(
                "types").toString();

        initActivity();
        addFHMethod();
        addZYMethod();
        searchDateDw(0);
        if (types.equals("1")) {//客户
            myAdapter.mTitles = new String[]{"单位", "联系人", "拜访", "机会", /*"服务",*/ "订单", "项目"};
            //设置可以滑动
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            List<BaseView> v = new ArrayList<BaseView>();
            for (int i = 0; i < viewPage.size(); i++) {
                switch (i) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        v.add(viewPage.get(i));
                        break;
                    case 5:

                        break;
                    default:
                        break;
                }
            }
            viewPage.removeAll(v);

        } else if (types.equals("2")) {//供应商
            myAdapter.mTitles = new String[]{"单位", "联系人", "拜访", "项目"};
            //设置
            tabLayout.setTabMode(TabLayout.MODE_FIXED);

            List<BaseView> v = new ArrayList<BaseView>();
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
            myAdapter.mTitles = new String[]{"单位", "联系人", "拜访", "项目"};
            //设置
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            List<BaseView> v = new ArrayList<BaseView>();
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
            myAdapter.mTitles = new String[]{"单位", "联系人", "拜访", "机会", /*"服务",*/ "订单", "项目"};
            //设置可以滑动
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            List<BaseView> v = new ArrayList<BaseView>();
            for (int i = 0; i < viewPage.size(); i++) {
                switch (i) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        v.add(viewPage.get(i));
                        break;
                    case 5:

                        break;
                    default:
                        break;
                }
            }
            viewPage.removeAll(v);
        } else if (types.equals("5")) {//员工
//            dwTextView.setText("基本信息");
            myAdapter.mTitles = new String[]{"基本信息", "拜访", "项目"};
            //设置
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            UnitView view = (UnitView) dwView;
            view.Visibility();

            List<BaseView> v = new ArrayList<BaseView>();
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
        myAdapter.setmViewList(viewPage);
        myAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);

        dwView = new UnitView(this);
        lxrView = new ContactView(this, clientId);
        bfView = new VisitView(this, clientId, khmc);
        jhView = new OpportunityView(this, clientId, khmc);
        fwView = new ServiceView(this);
        ddView = new OrdersView(this);
        xmView = new ProjectView(this);
        viewPage.add(dwView);
        viewPage.add(lxrView);
        viewPage.add(bfView);
        viewPage.add(jhView);
        viewPage.add(fwView);
        viewPage.add(ddView);
        viewPage.add(xmView);
        myAdapter = new FjzxPageAdapter();
        viewPager.setAdapter(myAdapter);
        // 下面的点图
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (!viewPage.get(arg0).isFirst) {
                    viewPage.get(arg0).initData();
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
     * 连接网络的操作(单位)
     */
    public void searchDateDw(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("clientid", clientId);
        findServiceData(type, ServerURL.CLIENTINFO, parmMap);
    }

    /**
     * 连接网络的操作(联系人)
     */
    public void searchDateLxr(int type) {
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
    public void searchDateBf(int type) {
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
    public void searchDateJh(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("tabname", "tb_chance");
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("qsrq", "1901-01-01");
        parmMap.put("zzrq", "3000-01-01");
        parmMap.put("clientid", clientId);
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData(type, ServerURL.BILLLIST, parmMap);
    }

    /**
     * 连接网络的操作(服务)
     */
    public void searchDateFw(int type) {
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
    public void searchDateDd(int type) {
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
    public void searchDateXm(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("tabname", "tb_project");
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("clientid", clientId);
        parmMap.put("qsrq", "1901-01-01");
        parmMap.put("zzrq", "3000-01-01");
        parmMap.put("cname", "");
        parmMap.put("title", "");
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData(type, ServerURL.BILLLIST, parmMap);
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
                intent.putExtra("khdjname", dwObject.get("typename").toString());// 客户类型 的名字
                intent.setClass(GzptDwzlActivity.this, GzptDwzlDwBjdwActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.xzlxr_textview:// 新增联系人
                intent.setClass(this, GzptDwzlLxrBjlxrActivity.class);
                intent.putExtra("lxrid", "0");
                intent.putExtra("clientid", clientId);
                startActivityForResult(intent, 0);
                break;
            case R.id.bflr_textview:
                intent.setClass(this, GzptDwzlBfBflrActivity.class);
                intent.putExtra("clientname", dwObject.get("cname").toString());
                intent.putExtra("clientid", clientId);
                intent.putExtra("object", (Serializable) object);
                startActivityForResult(intent, 2);
                break;
            case R.id.xzxsjh_textview:
                startActivityForResult(new Intent(this, AddSalesOpportunitiesActivity.class)
                        .putExtra("clientid", clientId)
                        .putExtra("clientname", dwObject.get("cname").toString()), 3);
//                intent.setClass(this, GzptDwzlJhXzxsjhActivity.class);
//                intent.putExtra("clientid", clientId);
//                intent.putExtra("clientname", dwObject.get("cname").toString());
//                startActivityForResult(intent, 3);
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
                startActivityForResult(new Intent(this, AddProjectActivity.class)
                                .putExtra("clientid", clientId)
                                .putExtra("clientname", dwObject.get("cname").toString())
                        , 6);
//                intent.setClass(this, XzxmActivity.class);
//                //                intent.putExtra("lxrid", "0");
//                //                intent.putExtra("clientid", clientId);
//                intent.putExtra("dwObject", (Serializable) dwObject);
//                intent.putExtra("types", types);
//                startActivityForResult(intent, 6);
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
                khdjid = dwObject.get("typeid").toString();
                khmc = dwObject.get("cname").toString();
                dwView.setData((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));

                break;
            case 1:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    lxrView.setData((List<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson));
                }

                break;
            case 2:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    bfView.setData((List<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson));

                }

                break;
            case 3:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    jhView.setData((List<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson));

                }

                break;
            case 4:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    fwView.setData((List<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson));
                }

                break;
            case 5:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    ddView.setData((List<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson));
                }

                break;
            case 6:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
//                    Toast.makeText(GzptDwzlActivity.this,"fasf",Toast.LENGTH_SHORT).show();
                } else {
                    xmView.setData((List<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson));
                }

                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {// 新增联系人
                lxrView.initData();

            } else if (requestCode == 1) {// 单位
                dwView.initData();
            } else if (requestCode == 2) {// 拜访
                bfView.initData();
            } else if (requestCode == 3) {// 机会
                jhView.initData();
            } else if (requestCode == 4) {// 服务
                fwView.initData();
            } else if (requestCode == 5) {// 订单
                ddView.initData();
            } else if (requestCode == 6) {// 項目
                xmView.initData();
            }
        }
    }
}
