package com.cr.activity.index;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.cr.activity.BaseActivity;
import com.cr.activity.FKActivity;
import com.cr.activity.GYActivity;
import com.cr.activity.GzptHjzxXzjhActivity;
import com.cr.activity.GzptJqxzdwActivity;
import com.cr.activity.GzptKhglActivity;
import com.cr.activity.GzptShhfActivity;
import com.cr.activity.GzptXzldActivity;
import com.cr.activity.GzptYybfActivity;
import com.cr.activity.JhzjRjhActivity;
import com.cr.activity.JhzjYjhActivity;
import com.cr.activity.JhzjZdyjhActivity;
import com.cr.activity.JhzjZjhActivity;
import com.cr.activity.MainActivity;
import com.cr.activity.MstxGrrcActivity;
import com.cr.activity.MstxGsggActivity;
import com.cr.activity.khgl.mstx.gztx.MstxGztxActivity;
import com.cr.activity.QdXzqdActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddActivity;
import com.cr.activity.jxc.cggl.cgfk.JxcCgglCgfkActivity;
import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshActivity;
import com.cr.activity.jxc.cggl.cgth.JxcCgglCgthActivity;
import com.cr.activity.jxc.ckgl.chtj.JxcCkglChtjActivity;
import com.cr.activity.jxc.ckgl.ckdb.JxcCkglCkdbActivity;
import com.cr.activity.jxc.ckgl.kcbd.JxcCkglKcbdActivity;
import com.cr.activity.jxc.ckgl.kcpd.JxcCkglKcpdActivity;
import com.cr.activity.jxc.ckgl.zzcx.JxcCkglZzcxActivity;
import com.cr.activity.jxc.xsgl.xsdd.JxcXsglXsddActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdActivity;
import com.cr.activity.jxc.xsgl.xssk.JxcXsglXsskActivity;
import com.cr.activity.jxc.xsgl.xsth.JxcXsglXsthActivity;
import com.cr.activity.khfw.KhfwActivity;
import com.cr.activity.tjfx.fzb.KtTjfxFzbActivity;
import com.cr.activity.tjfx.jyzk.TjfxJyzkActivity;
import com.cr.activity.tjfx.kcbb.TjfxKcbbActivity;
import com.cr.activity.tjfx.khbftj.TjfxKhbftjActivity;
import com.cr.activity.tjfx.khdjtj.TjfxKhdjtjActivity;
import com.cr.activity.tjfx.khfwtj.TjfxKhfwtjActivity;
import com.cr.activity.tjfx.lrb.KtTjfxLrbActivity;
import com.cr.activity.tjfx.xkhtj.TjfxXkhtjActivity;
import com.cr.activity.tjfx.xsjdtj.TjfxXsjdtjActivity;
import com.cr.activity.tjfx.xsjhtj.TjfxXsjhtjActivity;
import com.cr.activity.tjfx.xsskhzb.TjfxXsskhzbActivity;
import com.cr.activity.tjfx.ysyf.TjfxYsyfActivity;
import com.cr.activity.tjfx.zjzh.TjfxZjzhActivity;
import com.cr.activity.xjyh.fkd.XjyhFkdActivity;
import com.cr.activity.xjyh.fyzc.XjyhFyzcActivity;
import com.cr.activity.xjyh.qtsr.XjyhQtsrActivity;
import com.cr.activity.xjyh.skd.XjyhSkdActivity;
import com.cr.activity.xjyh.yhcq.XjyhYhcqActivity;
import com.cr.adapter.IndexAdapter;
import com.cr.adapter.SlidePageAdapter;
import com.cr.model.IndexModel;
import com.cr.service.SocketService;
import com.cr.tools.AppData;
import com.cr.tools.CustomGridView;
import com.cr.tools.MyApplication;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.contract.ContractManagementActivity;
import com.update.actiity.logistics.LogisticsManagementActivity;
import com.update.actiity.maintenance.DetectionMaintenanceActivity;
import com.update.actiity.installation.InstallRegistrationActivity;
import com.update.actiity.maintenance.MaintenanceRegistrationActivity;
import com.update.actiity.installation.PerformInstallationActivity;
import com.update.actiity.project.ProjectManagementActivity;
import com.update.actiity.sales.SalesOpportunitiesManagementActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主界面
 *
 * @author Administrator
 */
public class IndexActivity extends BaseActivity implements OnClickListener {
    private ImageButton setButton;
    private TextView fkTextView, gyTextView, tcTextView;

    private ViewPager viewPager;
    private LayoutInflater inflater;
    private View gzptView, jxcView, xjyhView, tjfxView, setView;
    private CustomGridView mstxGridView, wdgzGridView, azwxGridView, jhzjGridView, cgglGridView,
            xsglGridView, ckglGridView, xjyhGridView, tjfxGridView, khgxbbGridView;
    private List<IndexModel> mstxIndexModelList, gzptIndexModelList, azwxIndexModelList, jhzjIndexModelList,
            cgglIndexModelList, xsglIndexModelList, ckglIndexModelList, xjyhIndexModelList, tjfxIndexModelList,
            khgxbbIndexModelList;

    private PopupWindow mPopupWindow;
    private ImageView corsor1, corsor2, corsor3, corsor4;
    private TextView gzptTextView, jxcTextView, xjyhTextView, tjfxTextView, tvAzwx;
    private LinearLayout gyLayout;

    private String lng, lat; // 经度，纬度
    private long exitTime = 0;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    private Intent mIntent;//当前跳转视图


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initActivity();

        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        InitLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
        int span = 1000;
//        try {
//            span = Integer.valueOf(frequence.getText().toString());
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
        option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
//        option.setIsNeedAddress(checkGeoLocation.isChecked());
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            lng = location.getLongitude() + "";
            lat = location.getLatitude() + "";
            saveQdMsg();//保存签到信息
//            showToastPromopt(lng+":"+lat);

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("LoginMax");
        // 注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
        setButton = (ImageButton) findViewById(R.id.gy);
        setButton.setOnClickListener(this);
        corsor1 = (ImageView) findViewById(R.id.corsor1);
        corsor2 = (ImageView) findViewById(R.id.corsor2);
        corsor3 = (ImageView) findViewById(R.id.corsor3);
        corsor4 = (ImageView) findViewById(R.id.corsor4);
        gzptTextView = (TextView) findViewById(R.id.gzpt_textview);
        jxcTextView = (TextView) findViewById(R.id.jxc_textview);
        xjyhTextView = (TextView) findViewById(R.id.xjyh_textview);
        tjfxTextView = (TextView) findViewById(R.id.tjfx_textview);
        gyLayout = (LinearLayout) findViewById(R.id.gy_layout);
        gyLayout.setOnClickListener(this);
        gzptTextView.setOnClickListener(this);
        jxcTextView.setOnClickListener(this);
        xjyhTextView.setOnClickListener(this);
        tjfxTextView.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<View> viewPage = new ArrayList<View>();
        gzptView = inflater.inflate(R.layout.activity_index_index_gzpt, null);
        jxcView = inflater.inflate(R.layout.activity_index_index_jxc, null);
        xjyhView = inflater.inflate(R.layout.activity_index_index_xjyh, null);
        tjfxView = inflater.inflate(R.layout.activity_index_index_tjfx, null);
        setView = inflater.inflate(R.layout.activity_index_set, null);
        viewPage.add(gzptView);
        viewPage.add(jxcView);
        viewPage.add(xjyhView);
        viewPage.add(tjfxView);
        SlidePageAdapter myAdapter = new SlidePageAdapter(viewPage, this);
        viewPager.setAdapter(myAdapter);
        // 下面的点图
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                } else if (arg0 == 1) {
                    corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                } else if (arg0 == 2) {
                    corsor3.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                } else if (arg0 == 3) {
                    corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    corsor4.setBackgroundResource(R.drawable.index_selectcorsor);
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
        mstxGridView = (CustomGridView) gzptView.findViewById(R.id.mstx_gridview);
        wdgzGridView = (CustomGridView) gzptView.findViewById(R.id.wdgz_gridview);
        azwxGridView = (CustomGridView) gzptView.findViewById(R.id.azwx_gridview);
        tvAzwx = (TextView) gzptView.findViewById(R.id.tv_azwx);
        jhzjGridView = (CustomGridView) gzptView.findViewById(R.id.jhzj_gridview);
        cgglGridView = (CustomGridView) jxcView.findViewById(R.id.cggl_gridview);
        xsglGridView = (CustomGridView) jxcView.findViewById(R.id.xsgl_gridview);
        ckglGridView = (CustomGridView) jxcView.findViewById(R.id.ckgl_gridview);
        xjyhGridView = (CustomGridView) xjyhView.findViewById(R.id.xjyh_gridview);
        tjfxGridView = (CustomGridView) tjfxView.findViewById(R.id.tjfx_gridview);
        khgxbbGridView = (CustomGridView) tjfxView.findViewById(R.id.khgxbb_gridview);
        mstxIndexModelList = new ArrayList<IndexModel>();
        gzptIndexModelList = new ArrayList<IndexModel>();
        azwxIndexModelList = new ArrayList<IndexModel>();
        jhzjIndexModelList = new ArrayList<IndexModel>();
        cgglIndexModelList = new ArrayList<IndexModel>();
        xsglIndexModelList = new ArrayList<IndexModel>();
        ckglIndexModelList = new ArrayList<IndexModel>();
        xjyhIndexModelList = new ArrayList<IndexModel>();
        tjfxIndexModelList = new ArrayList<IndexModel>();
        khgxbbIndexModelList = new ArrayList<IndexModel>();
        mstx();//秘书提醒
        gzpt();//工作平台
        azwx();//安装维修
        jhzj();//计划总结

        cggl();//采购管理
        xsgl();//销售管理
        ckgl();//仓库管理

        xjyh();//现金银行

        khgxbb();//客户关系报表
        cwtj();//财务统计

        String category = ShareUserInfo.getKey(mContext, "zt");
//        租赁企业版、IT企业版、IT专业版这三个版屏蔽客户服务(标准专业版版\汽配版等保留)，统计分析中客户服务统计也给屏蔽了
//        if (category.equals("租赁企业版") || category.equals("IT企业版") || category.equals("IT专业版")) {
//            gzptIndexModelList.remove(6);//屏蔽
//            khgxbbIndexModelList.remove(5);//屏蔽
//            ckglIndexModelList.remove(1);//屏蔽
//            xjyhIndexModelList.remove(0);//屏蔽现金银行费用支出
//            xjyhIndexModelList.remove(3);//屏蔽现金银行其他收入
//
//        }else {
//            azwxGridView.setVisibility(View.GONE);
//            tvAzwx.setVisibility(View.GONE);
//        }
//        gzptIndexModelList.remove(6);//屏蔽客户服务

        switch (AppData.AppType){
            case 1:// 1、汽配版
            case 2://2、标准专业版
                tvAzwx.setVisibility(View.GONE);
                azwxGridView.setVisibility(View.GONE);
                break;
            case 3://3、租赁企业版
            case 4://3、内部版
            case 5://3、IT企业版
            case 6://3、IT专业版
                tvAzwx.setVisibility(View.VISIBLE);
                azwxGridView.setVisibility(View.VISIBLE);
                gzptIndexModelList.remove(6);
                break;
        }
        khgxbbIndexModelList.remove(5);//屏蔽客户服务统计

//        xjyhIndexModelList.remove(0);//屏蔽现金银行费用支出
//        xjyhIndexModelList.remove(3);//屏蔽现金银行其他收入

//        ckglIndexModelList.remove(1);//屏蔽库存盘点
//        ckglIndexModelList.remove(2);//屏蔽存货调价
        ckglIndexModelList.remove(3);//屏蔽库存变动
        ckglIndexModelList.remove(3);//屏蔽库存变动

        tjfxIndexModelList.remove(4);//屏蔽经营状况
    }

    /**
     * 秘书提醒初始化和数据设置
     */
    private void mstx() {
        for (int i = 0; i < 4; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.index_gztx);
                    im.setLogoName("工作提醒");
                    im.setIntent(new Intent(IndexActivity.this, MstxGztxActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("401408");
                    break;
                case 1:
                    im.setLogoId(R.drawable.index_gsgg);
                    im.setLogoName("公司公告");
                    im.setIntent(new Intent(IndexActivity.this, MstxGsggActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("401407");
                    break;
                case 2:
                    im.setLogoId(R.drawable.index_qd);
                    im.setLogoName("签到");
                    im.setIntent(new Intent(IndexActivity.this, QdXzqdActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("401409");
                    break;
                case 3:
                    im.setLogoId(R.drawable.index_grrc);
                    im.setLogoName("个人日程");
                    im.setIntent(new Intent(IndexActivity.this, MstxGrrcActivity.class));
                    im.setKhzlname("");
                    break;
                default:
                    break;
            }
            mstxIndexModelList.add(im);
        }
        mstxGridView.setAdapter(new IndexAdapter(mContext, mstxIndexModelList));
        mstxGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ShareUserInfo.setKey(mContext, "cpphType", "mstx");//判断是否是采购订单
                switch (mstxIndexModelList.get(arg2).getLogoName()) {

                    case "个人日程":
                        startActivity(mstxIndexModelList.get(arg2).getIntent());
                        break;
                    case "签到":
                        if (!isLocationEnabled()) {
                            showToastPromopt("定位服务未开启!请开启定位服务");
                            return;
                        }
                    default:
                        mIntent = mstxIndexModelList.get(arg2).getIntent();
                        CheckOperPriv(mstxIndexModelList.get(arg2).getMenuid());

                        break;

                }
//                if (arg2 == 2) {
//                    if (isLocationEnabled()) {
//                        startActivity(mstxIndexModelList.get(arg2).getIntent());
//                    } else {
//                        Toast.makeText(IndexActivity.this, "定位服务未开启!请开启定位服务", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    startActivity(mstxIndexModelList.get(arg2).getIntent());
//                }

            }
        });
    }

    /**
     * 工作平台初始化和数据设置
     */
    private void gzpt() {
        //工作平台
        for (int i = 0; i < 11; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.index_hjzx);
                    im.setLogoName("计划拜访");
                    im.setIntent(new Intent(this,
                            GzptHjzxXzjhActivity.class));
                    im.setKhzlname("hjzx");
                    im.setMenuid("3003");
                    break;
                case 1:
                    im.setLogoId(R.drawable.index_yybf);
                    im.setLogoName("预约拜访");
                    im.setIntent(new Intent(IndexActivity.this, GzptYybfActivity.class));
                    im.setKhzlname("yybf");
                    im.setMenuid("3003");
                    break;
                case 2:
                    im.setLogoId(R.drawable.index_shhf);
                    im.setLogoName("售后回访");
                    im.setIntent(new Intent(IndexActivity.this, GzptShhfActivity.class));
                    im.setKhzlname("shhf");
                    im.setMenuid("3003");
                    break;
                case 3:
                    im.setLogoId(R.drawable.index_khgl);
                    im.setLogoName("客户管理");
                    im.setIntent(new Intent(IndexActivity.this, GzptKhglActivity.class));
                    im.setKhzlname("khgl");
                    im.setMenuid("3003");
                    break;
                case 4:
                    im.setLogoId(R.drawable.index_xzld);
                    im.setLogoName("来电录入");
                    im.setIntent(new Intent(IndexActivity.this, GzptXzldActivity.class));
                    im.setKhzlname("xzld");
                    im.setMenuid("3003");
                    break;
                case 5:
                    im.setLogoId(R.drawable.menu_xzjqdw);
                    im.setLogoName("近期新增单位");
                    im.setIntent(new Intent(IndexActivity.this, GzptJqxzdwActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("3003");
                    break;
                case 6:
                    im.setLogoId(R.drawable.menu_khfw);
                    im.setLogoName("客户服务");
                    im.setIntent(new Intent(IndexActivity.this, KhfwActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("3005");
                    break;
                case 7:
                    im.setLogoId(R.mipmap.menu_wlgl);
                    im.setLogoName("物流管理");
                    im.setIntent(new Intent(IndexActivity.this, LogisticsManagementActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400602");
                    break;
                case 8:
                    im.setLogoId(R.mipmap.menu_xsjh);
                    im.setLogoName("销售机会");
                    im.setIntent(new Intent(IndexActivity.this, SalesOpportunitiesManagementActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("3012");
                    break;
                case 9:
                    im.setLogoId(R.mipmap.menu_htgl);
                    im.setLogoName("合同管理");
                    im.setIntent(new Intent(IndexActivity.this, ContractManagementActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("3014");
                    break;
                case 10:
                    im.setLogoId(R.mipmap.menu_xmgl);
                    im.setLogoName("项目管理");
                    im.setIntent(new Intent(IndexActivity.this, ProjectManagementActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("3021");
                    break;
                default:
                    break;
            }
            gzptIndexModelList.add(im);
        }
        wdgzGridView.setAdapter(new IndexAdapter(mContext, gzptIndexModelList));
        wdgzGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ShareUserInfo.setKey(mContext, "cpphType", "wdgz");//判断是否是采购订单
                ShareUserInfo.setKey(mContext, "khzlname", gzptIndexModelList.get(arg2).getKhzlname());
//                switch (gzptIndexModelList.get(arg2).getLogoName()) {
//                    case "客户拜访统计":
//                    case "客户服务统计":
//                    case "新客户统计":
//                        startActivity(gzptIndexModelList.get(arg2).getIntent());
//                        break;
//                    default:
                mIntent = gzptIndexModelList.get(arg2).getIntent();
                CheckOperPriv(gzptIndexModelList.get(arg2).getMenuid());

//                        break;
//
//                }
//                startActivity(gzptIndexModelList.get(arg2).getIntent());
            }

        });

    }

    /**
     * 安装维修初始化和数据设置
     */
    private void azwx() {
        //安装维修
        for (int i = 0; i < 4; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.mipmap.ic_registration);
                    im.setLogoName("安装登记");
                    im.setIntent(new Intent(IndexActivity.this, InstallRegistrationActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("401201");
                    break;
                case 1:
                    im.setLogoId(R.mipmap.ic_perform);
                    im.setLogoName("安装执行");
                    im.setIntent(new Intent(IndexActivity.this, PerformInstallationActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("401203");
                    break;
                case 2:
                    im.setLogoId(R.mipmap.ic_registration);
                    im.setLogoName("维修登记");
                    im.setIntent(new Intent(IndexActivity.this, MaintenanceRegistrationActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("401101");
                    break;
                case 3:
                    im.setLogoId(R.mipmap.ic_perform);
                    im.setLogoName("检测维修");
                    im.setIntent(new Intent(IndexActivity.this, DetectionMaintenanceActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("401103");
                    break;
                default:
                    break;
            }
            azwxIndexModelList.add(im);
        }
        azwxGridView.setAdapter(new IndexAdapter(mContext, azwxIndexModelList));
        azwxGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
//				ShareUserInfo.setKey(context, "cpphType", "wdgz");//判断是否是采购订单
                ShareUserInfo.setKey(mContext, "cpphType", "ckgl");//判断是否是采购订单
                CheckOperPriv(azwxIndexModelList.get(arg2).getMenuid());
                mIntent = azwxIndexModelList.get(arg2).getIntent();
//                Intent intent = new Intent();
//                switch (arg2) {
//                    case 0:
//                        intent.setClass(IndexActivity.this, InstallRegistrationActivity.class);
////						ShareUserInfo.setKey(context, "khzlname", "hjzx");
//                        break;
//                    case 1:
//                        intent.setClass(IndexActivity.this,
//                                PerformInstallationActivity.class);
////						ShareUserInfo.setKey(context, "khzlname", "yybf");
//                        break;
//                    case 2:
//                        intent.setClass(IndexActivity.this,
//                                MaintenanceRegistrationActivity.class);
//                        break;
//                    case 3:
//                        intent.setClass(IndexActivity.this,
//                                DetectionMaintenanceActivity.class);
//                        break;
//                    default:
//                        break;
//                }
//                startActivity(intent);
            }
        });
    }

    /**
     * 计划总结初始化和数据设置
     */
    private void jhzj() {
        //计划总结
        for (int i = 0; i < 4; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.index_rjh);
                    im.setLogoName("日计划");
                    break;
                case 1:
                    im.setLogoId(R.drawable.index_zjh);
                    im.setLogoName("周计划");
                    break;
                case 2:
                    im.setLogoId(R.drawable.index_yjh);
                    im.setLogoName("月计划");
                    break;
                case 3:
                    im.setLogoId(R.drawable.index_zdyjh);
                    im.setLogoName("年计划");
                    break;
                default:
                    break;
            }
            jhzjIndexModelList.add(im);
        }
        jhzjGridView.setAdapter(new IndexAdapter(mContext, jhzjIndexModelList));
        jhzjGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ShareUserInfo.setKey(mContext, "cpphType", "jhzj");//判断是否是采购订单
                Intent intent = new Intent();
                switch (arg2) {
                    case 0:
                        intent.setClass(IndexActivity.this, JhzjRjhActivity.class);
                        break;
                    case 1:
                        intent.setClass(IndexActivity.this, JhzjZjhActivity.class);
                        break;
                    case 2:
                        intent.setClass(IndexActivity.this, JhzjYjhActivity.class);
                        break;
                    case 3:
                        intent.setClass(IndexActivity.this, JhzjZdyjhActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }


    /***********************进存销***********************/
    /**
     * 采购管理初始化和数据设置
     */
    private void cggl() {
        for (int i = 1; i < 5; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.index_gztx);
                    im.setLogoName("供应商");
//                    im.setIntent(new Intent(IndexActivity.this, ProjectManagementActivity.class));

                    break;
                case 1:
                    im.setLogoId(R.drawable.menu_cgdd);
                    im.setLogoName("采购订单");
                    im.setIntent(new Intent(IndexActivity.this, JxcCgglCgddActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400101");
                    break;
                case 2:
                    im.setLogoId(R.drawable.menu_cgsh);
                    im.setLogoName("采购收货");
                    im.setIntent(new Intent(IndexActivity.this, JxcCgglCgshActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400102");
                    break;
                case 3:
                    im.setLogoId(R.drawable.menu_cgfk);
                    im.setLogoName("采购付款");
                    im.setIntent(new Intent(IndexActivity.this, JxcCgglCgfkActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400104");
                    break;
                case 4:
                    im.setLogoId(R.drawable.menu_cgth);
                    im.setLogoName("采购退货");
                    im.setIntent(new Intent(IndexActivity.this, JxcCgglCgthActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400103");
                    break;
                default:
                    break;
            }
            cgglIndexModelList.add(im);
        }
        cgglGridView.setAdapter(new IndexAdapter(mContext, cgglIndexModelList));
        cgglGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ShareUserInfo.setKey(mContext, "cpphType", "wdgz");//判断是否是采购订单
                if (TextUtils.isEmpty(cgglIndexModelList.get(arg2).getKhzlname())) {
                    ShareUserInfo.setKey(mContext, "khzlname", cgglIndexModelList.get(arg2).getKhzlname());
                }
                CheckOperPriv(cgglIndexModelList.get(arg2).getMenuid());
                mIntent = cgglIndexModelList.get(arg2).getIntent();
//                startActivity(cgglIndexModelList.get(arg2).getIntent());
//                ShareUserInfo.setKey(context, "cpphType", "cggl");//判断是否是采购订单
//                Intent intent = new Intent();
//                switch (arg2) {
////				case 0:
////				    intent.putExtra("type", "gys");
////					intent.setClass(IndexActivity.this, GzptKhglActivity.class);
////					break;
//                    case 0:
//                        intent.setClass(IndexActivity.this, JxcCgglCgddActivity.class);
//                        break;
//                    case 1:
//                        intent.setClass(IndexActivity.this, JxcCgglCgshActivity.class);
//                        break;
//                    case 2:
//                        intent.setClass(IndexActivity.this, JxcCgglCgfkActivity.class);
//                        break;
//                    case 3:
//                        intent.setClass(IndexActivity.this, JxcCgglCgthActivity.class);
//                        break;
//                }
//                startActivity(intent);
            }
        });
    }

    /**
     * 销售管理初始化和数据设置
     */
    private void xsgl() {
        for (int i = 1; i < 5; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.index_gztx);
                    im.setLogoName("客户");
                    break;
                case 1:
                    im.setLogoId(R.drawable.menu_xsdd);
                    im.setLogoName("销售订单");
                    im.setIntent(new Intent(IndexActivity.this, JxcXsglXsddActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400201");
                    break;
                case 2:
                    im.setLogoId(R.drawable.menu_xskd);
                    im.setLogoName("销售开单");
                    im.setIntent(new Intent(IndexActivity.this, JxcXsglXskdActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400202");
                    break;
                case 3:
                    im.setLogoId(R.drawable.menu_xssk);
                    im.setLogoName("销售收款");
                    im.setIntent(new Intent(IndexActivity.this, JxcXsglXsskActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400204");
                    break;
                case 4:
                    im.setLogoId(R.drawable.menu_xsth);
                    im.setLogoName("销售退货");
                    im.setIntent(new Intent(IndexActivity.this, JxcXsglXsthActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400203");
                    break;
                default:
                    break;
            }
            xsglIndexModelList.add(im);
        }
        xsglGridView.setAdapter(new IndexAdapter(mContext, xsglIndexModelList));
        xsglGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ShareUserInfo.setKey(mContext, "cpphType", "wdgz");//判断是否是采购订单
                if (TextUtils.isEmpty(xsglIndexModelList.get(arg2).getKhzlname())) {
                    ShareUserInfo.setKey(mContext, "khzlname", xsglIndexModelList.get(arg2).getKhzlname());
                }
                CheckOperPriv(xsglIndexModelList.get(arg2).getMenuid());
                mIntent = xsglIndexModelList.get(arg2).getIntent();
//                ShareUserInfo.setKey(context, "cpphType", "xsgl");//判断是否是采购订单
//                Intent intent = new Intent();
//                switch (arg2) {
////				case 0:
////					intent.setClass(IndexActivity.this, GzptKhglActivity.class);
////					break;
//                    case 0:
//                        intent.setClass(IndexActivity.this, JxcXsglXsddActivity.class);
//                        break;
//                    case 1:
//                        intent.setClass(IndexActivity.this, JxcXsglXskdActivity.class);
//                        break;
//                    case 2:
//                        intent.setClass(IndexActivity.this, JxcXsglXsskActivity.class);
//                        break;
//                    case 3:
//                        intent.setClass(IndexActivity.this, JxcXsglXsthActivity.class);
//                        break;
//                }
//                startActivity(intent);
            }
        });

    }

    /**
     * 仓库管理初始化和数据设置
     */
    private void ckgl() {
        /*进存销——库存管理*/
        for (int i = 0; i < 6; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.menu_ckdb);
                    im.setLogoName("仓库调拨");
                    im.setIntent(new Intent(IndexActivity.this, JxcCkglCkdbActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400301");
                    break;
                case 1:
                    im.setLogoId(R.drawable.menu_kcpd);
                    im.setLogoName("库存盘点");
                    im.setIntent(new Intent(IndexActivity.this, JxcCkglKcpdActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400302");
                    break;
                case 2:
                    im.setLogoId(R.drawable.menu_chtj);
                    im.setLogoName("存货调价");
                    im.setIntent(new Intent(IndexActivity.this, JxcCkglChtjActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400304");
                    break;
                case 3:
                    im.setLogoId(R.drawable.menu_kcbd);
                    im.setLogoName("库存变动");
                    im.setIntent(new Intent(IndexActivity.this, JxcCkglKcbdActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("400305");
                    break;
                case 4:
                    im.setLogoId(R.drawable.menu_zzcx);
                    im.setLogoName("组装拆卸");
                    im.setIntent(new Intent(IndexActivity.this, JxcCkglZzcxActivity.class));
                    im.setKhzlname("");
//                    im.setMenuid("400201");
                    break;
                case 5:
                    im.setLogoId(R.drawable.menu_kcbb);
                    im.setLogoName("库存报表");
                    im.setIntent(new Intent(IndexActivity.this, TjfxKcbbActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("500410");
                    break;
                default:
                    break;
            }
            ckglIndexModelList.add(im);
        }
        ckglGridView.setAdapter(new IndexAdapter(mContext, ckglIndexModelList));
        ckglGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ShareUserInfo.setKey(mContext, "cpphType", "ckgl");//判断是否是采购订单
                CheckOperPriv(ckglIndexModelList.get(arg2).getMenuid());
                mIntent = ckglIndexModelList.get(arg2).getIntent();
//                startActivity(ckglIndexModelList.get(arg2).getIntent());
            }
        });
    }

    /**
     * 现金银行初始化和数据设置
     */
    private void xjyh() {
        // 现金银行
        for (int i = 0; i < 5; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.menu_fyzc);
                    im.setLogoName("费用支出");
                    im.setIntent(new Intent(IndexActivity.this, XjyhFyzcActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("600101");
                    break;
                case 1:
                    im.setLogoId(R.drawable.menu_yhcq);
                    im.setLogoName("银行存取");
                    im.setIntent(new Intent(IndexActivity.this, XjyhYhcqActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("600103");
                    break;
                case 2:
                    im.setLogoId(R.drawable.menu_fkd);
                    im.setLogoName("付款单");
                    im.setIntent(new Intent(IndexActivity.this, XjyhFkdActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("600105");
                    break;
                case 3:
                    im.setLogoId(R.drawable.menu_skd);
                    im.setLogoName("收款单");
                    im.setIntent(new Intent(IndexActivity.this, XjyhSkdActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("600104");
                    break;
                case 4:
                    im.setLogoId(R.drawable.menu_qtsr);
                    im.setLogoName("其他收入");
                    im.setIntent(new Intent(IndexActivity.this, XjyhQtsrActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("600102");
                    break;
                default:
                    break;
            }
            xjyhIndexModelList.add(im);
        }
        xjyhGridView.setAdapter(new IndexAdapter(mContext, xjyhIndexModelList));
        xjyhGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ShareUserInfo.setKey(mContext, "cpphType", "xjyh");//判断是否是采购订单
                CheckOperPriv(xjyhIndexModelList.get(arg2).getMenuid());
                mIntent = xjyhIndexModelList.get(arg2).getIntent();
//                startActivity(xjyhIndexModelList.get(arg2).getIntent());
            }
        });
    }

    /**
     * 客户关系报表初始化和数据设置
     */
    private void khgxbb() {
        //客户关系报表
        for (int i = 0; i < 6; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.menu_khbftj);
                    im.setLogoName("客户拜访统计");
                    im.setIntent(new Intent(IndexActivity.this, TjfxKhbftjActivity.class));
                    im.setKhzlname("");
                    break;
                case 1:
                    im.setLogoId(R.drawable.menu_xkhtj);
                    im.setLogoName("新客户统计");
                    im.setIntent(new Intent(IndexActivity.this, TjfxXkhtjActivity.class));
                    im.setKhzlname("");
                    break;
                case 2:
                    im.setLogoId(R.drawable.menu_xsjhtj);
                    im.setLogoName("销售机会统计");
                    im.setIntent(new Intent(IndexActivity.this, TjfxXsjhtjActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("500101");
                    break;
                case 3:
                    im.setLogoId(R.drawable.menu_khdjtj);
                    im.setLogoName("客户等级统计");
                    im.setIntent(new Intent(IndexActivity.this, TjfxKhdjtjActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("500106");
                    break;
                case 4:
                    im.setLogoId(R.drawable.menu_xsjdtj);
                    im.setLogoName("销售阶段统计");
                    im.setIntent(new Intent(IndexActivity.this, TjfxXsjdtjActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("500115");
                    break;
                case 5:
                    im.setLogoId(R.drawable.menu_khfwtj);
                    im.setLogoName("客户服务统计");
                    im.setIntent(new Intent(IndexActivity.this, TjfxKhfwtjActivity.class));
                    im.setKhzlname("");
//                    im.setMenuid("500106");
                    break;
                default:
                    break;
            }
            khgxbbIndexModelList.add(im);
        }
        khgxbbGridView.setAdapter(new IndexAdapter(mContext, khgxbbIndexModelList));
        khgxbbGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                switch (khgxbbIndexModelList.get(arg2).getLogoName()) {
                    case "客户拜访统计":
                    case "客户服务统计":
                    case "新客户统计":
                        startActivity(khgxbbIndexModelList.get(arg2).getIntent());
                        break;
                    default:
                        mIntent = khgxbbIndexModelList.get(arg2).getIntent();
                        CheckOperPriv(khgxbbIndexModelList.get(arg2).getMenuid());

                        break;

                }
//                CheckOperPriv(khgxbbIndexModelList.get(arg2).getMenuid());
//                mIntent = khgxbbIndexModelList.get(arg2).getIntent();
//                startActivity(khgxbbIndexModelList.get(arg2).getIntent());
            }
        });
    }

    /**
     * 财务统计初始化和数据设置
     */
    private void cwtj() {
        //财务统计
        for (int i = 0; i < 7; i++) {
            IndexModel im = new IndexModel();
            switch (i) {
                case 0:
                    im.setLogoId(R.drawable.menu_kcbb);
                    im.setLogoName("库存报表");
                    im.setIntent(new Intent(IndexActivity.this, TjfxKcbbActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("500410");
                    break;
                case 1:
                    im.setLogoId(R.drawable.menu_ysyf);
                    im.setLogoName("应收应付");
                    im.setIntent(new Intent(IndexActivity.this, TjfxYsyfActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("501505");
                    break;
                case 2:
                    im.setLogoId(R.drawable.menu_zjzh);
                    im.setLogoName("资金账户");
                    im.setIntent(new Intent(IndexActivity.this, TjfxZjzhActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("2014");
                    break;
                case 3:
                    im.setLogoId(R.drawable.menu_xsskhzb);
                    im.setLogoName("销售收款汇总表");
                    im.setIntent(new Intent(IndexActivity.this, TjfxXsskhzbActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("500318");
                    break;
                case 4:
                    im.setLogoId(R.drawable.menu_jyzk);
                    im.setLogoName("经营状况");
                    im.setIntent(new Intent(IndexActivity.this, TjfxJyzkActivity.class));
                    im.setKhzlname("");
//                    im.setMenuid("500318");
                    break;
                case 5:
                    im.setLogoId(R.drawable.menu_lrb);
                    im.setLogoName("利润表");
                    im.setIntent(new Intent(IndexActivity.this, KtTjfxLrbActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("501501");
                    break;
                case 6:
                    im.setLogoId(R.drawable.menu_zcfzb);
                    im.setLogoName("资产负债表");
                    im.setIntent(new Intent(IndexActivity.this, KtTjfxFzbActivity.class));
                    im.setKhzlname("");
                    im.setMenuid("501503");
                    break;
                default:
                    break;
            }
            tjfxIndexModelList.add(im);
        }
        tjfxGridView.setAdapter(new IndexAdapter(mContext, tjfxIndexModelList));
        tjfxGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ShareUserInfo.setKey(mContext, "cpphType", "tjfx");//判断是否是采购订单
                switch (tjfxIndexModelList.get(arg2).getLogoName()) {
                    case "经营状况":
                        startActivity(tjfxIndexModelList.get(arg2).getIntent());
                        break;
                    default:
                        mIntent = tjfxIndexModelList.get(arg2).getIntent();
                        CheckOperPriv(tjfxIndexModelList.get(arg2).getMenuid());
                        break;

                }
//                startActivity(tjfxIndexModelList.get(arg2).getIntent());

            }
        });
    }

    /**
     * 监听事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gy:
                // initPopuptWindow(view);
                break;
            case R.id.gy_layout:
                initPopuptWindow(view);
                break;
            case R.id.fk_textview:
                Intent intent2 = new Intent(IndexActivity.this, FKActivity.class);
                startActivity(intent2);
                mPopupWindow.dismiss();
                break;
            case R.id.gy_textview:
                Intent intent = new Intent(IndexActivity.this, GYActivity.class);
                startActivity(intent);
                mPopupWindow.dismiss();
                break;
            case R.id.tc_textview:
                MyApplication.getInstance().exit();
                break;
            case R.id.gzpt_textview:
                corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                viewPager.setCurrentItem(0);
                break;
            case R.id.jxc_textview:
//			case R.id.tjfx_textview:
                corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                viewPager.setCurrentItem(1);
                break;
            case R.id.xjyh_textview:
                corsor3.setBackgroundResource(R.drawable.index_selectcorsor);
                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                viewPager.setCurrentItem(2);
                break;
            case R.id.tjfx_textview:
                corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                corsor4.setBackgroundResource(R.drawable.index_selectcorsor);
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    /*
     * 创建PopupWindow
     */
    @SuppressWarnings("deprecation")
    private void initPopuptWindow(View v) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        setView = layoutInflater.inflate(R.layout.activity_index_set, null);
        // 创建一个PopupWindow
        // 参数1：contentView 指定PopupWindow的内容
        mPopupWindow = new PopupWindow(setView, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        fkTextView = (TextView) setView.findViewById(R.id.fk_textview);
        gyTextView = (TextView) setView.findViewById(R.id.gy_textview);
        tcTextView = (TextView) setView.findViewById(R.id.tc_textview);
        fkTextView.setOnClickListener(this);
        gyTextView.setOnClickListener(this);
        tcTextView.setOnClickListener(this);
        mPopupWindow.showAsDropDown(v, 0, 20);
    }

    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 0:
                showToastPromopt("签到成功！");
                mLocationClient.stop();//开启定位功能
                break;

            default:
                break;
        }
    }

    @Override
    protected void UserPermissionsCallBack() {
        if (!ShareUserInfo.getKey(this, "ll").equals("1")) {
            showToastPromopt("你没有该权限，请向管理员申请权限！");
            return;
        }
        startActivity(mIntent);
    }


    /**
     * 获得签到信息
     */
//	private void getQdData() {
//		try {
//			manager = (LocationManager) getSystemService(LOCATION_SERVICE);// 获取手机位置信息
//			// 获取的条件
//			Criteria criteria = new Criteria();
//			criteria.setAccuracy(Criteria.ACCURACY_FINE);// 获取精准位置
//			criteria.setCostAllowed(true);// 允许产生开销
//			criteria.setPowerRequirement(Criteria.POWER_HIGH);// 消耗大的话，获取的频率高
//			criteria.setSpeedRequired(true);// 手机位置移动
//			criteria.setAltitudeRequired(true);// 海拔
//			// 获取最佳provider: 手机或者模拟器上均为gps
//			String bestProvider = manager.getBestProvider(criteria, true);// 使用GPS卫星
//			Location location = manager.getLastKnownLocation(bestProvider);
//			String latitude = location.getLatitude() + "";// 纬度
//			String longitude = location.getLongitude() + "";// 经度
//			CustomerSignIn csi = new CustomerSignIn();
//			csi.setLng(longitude);
//			csi.setLat(latitude);
//			CustomerSignInDao csid = new CustomerSignInDao(context);
//			csid.add(csi);
//			// ljgz.setOpdate(sdf.format(new Date()));
//			lng = longitude;
//			lat = latitude;
//			// lng=(Float.parseFloat(lng))+0.011100+"";
//			lng = (Float.parseFloat(lng)) + 0.01266724 + "";
//			// lat=(Float.parseFloat(lat))+0.00450+"";
//			lat = (Float.parseFloat(lat)) + 0.002880 + "";
//			saveQdMsg();
//			// List<CustomerSignIn> list=csid.findAllHotSearch();
//			// List<Map<String, Object>> mapList=new
//			// ArrayList<Map<String,Object>>();
//			// for(CustomerSignIn cs:list){
//			// Map<String, Object> map=new HashMap<String, Object>();
//			// map.put("time",cs.getTime());
//			// map.put("lng",cs.getLng());
//			// map.put("lat",cs.getLat());
//			// mapList.add(map);
//			// }
//		} catch (Exception e) {
//			e.printStackTrace();
//			showToastPromopt("签到失败，请检查手机Gps设置！");
//		}
//
//	}

    /**
     * 保存签到信息到服务器当中
     */
    public void saveQdMsg() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("opid", ShareUserInfo.getUserId(mContext));
        parmMap.put("jd", lng);
        parmMap.put("wd", lat);
        findServiceData2(0, ServerURL.REGISTERWRITE, parmMap, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                try {
                    this.stopService(MainActivity.intent);
                    if (SocketService.socket != null)
                        SocketService.closeSocket();
                    MyApplication.getInstance().exit();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("LoginMax")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示信息");
                builder.setMessage("与服务器断开连接，您将被迫推出！");
                builder.setPositiveButton("确定退出",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // Intent intent2=new
                                // Intent(LoginActivity.this,MainActivity.class);
                                // startActivity(intent2);
                                MyApplication.getInstance().exit();
                            }
                        });
                builder.show();
            }
        }

    };

    /**
     * 判断定位服务是否开启
     *
     * @param
     * @return true 表示开启
     */
    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
//
//        xjyhGridView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                ShareUserInfo.setKey(context, "cpphType", "xjyh");//判断是否是采购订单
//                Intent intent = new Intent();
//                switch (arg2) {
//                    case 0:
//                        intent.setClass(IndexActivity.this, XjyhFyzcActivity.class);
//                        break;
//                    case 1:
//                        intent.setClass(IndexActivity.this, XjyhYhcqActivity.class);
//                        break;
//                    case 2:
//                        intent.setClass(IndexActivity.this, XjyhFkdActivity.class);
//                        break;
//                    case 3:
//                        intent.setClass(IndexActivity.this, XjyhSkdActivity.class);
//                        break;
//                    case 4:
//                        intent.setClass(IndexActivity.this, XjyhQtsrActivity.class);
//                        break;
//                }
//                startActivity(intent);
//            }
//        });
//        ckglGridView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                ShareUserInfo.setKey(context, "cpphType", "ckgl");//判断是否是采购订单
//                Intent intent = new Intent();
//                switch (arg2) {
//                    case 0:
//                        intent.setClass(IndexActivity.this, JxcCkglCkdbActivity.class);
//                        break;
//                    case 1:
//                        intent.setClass(IndexActivity.this, JxcCkglKcpdActivity.class);
//                        break;
//                    case 2:
//                        intent.setClass(IndexActivity.this, JxcCkglChtjActivity.class);
//                        break;
//                    case 3:
//                        intent.setClass(IndexActivity.this, JxcCkglKcbdActivity.class);
//                        break;
//                    case 4:
//                        intent.setClass(IndexActivity.this, JxcCkglZzcxActivity.class);
//                        break;
//                }
//                startActivity(intent);
//            }
//        });
//        khgxbbGridView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                Intent intent = new Intent();
//                switch (arg2) {
//                    case 0:
//                        //统计分析客户拜访统计
//                        intent.setClass(IndexActivity.this, TjfxKhbftjActivity.class);
//                        break;
//                    case 1:
//                        //统计分析客户拜访统计
//                        intent.setClass(IndexActivity.this, TjfxXkhtjActivity.class);
//                        break;
//                    case 2:
//                        intent.setClass(IndexActivity.this, TjfxXsjhtjActivity.class);
//                        break;
//                    case 3:
//                        intent.setClass(IndexActivity.this, TjfxKhdjtjActivity.class);
//                        break;
//                    case 4:
//                        //统计分析-销售阶段统计
//                        intent.setClass(IndexActivity.this, TjfxXsjdtjActivity.class);
//                        break;
//                    case 5:
//                        intent.setClass(IndexActivity.this, TjfxKhfwtjActivity.class);
//                        break;
//                }
//                startActivity(intent);
//            }
//        });
//        wdgzGridView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                ShareUserInfo.setKey(context, "cpphType", "wdgz");//判断是否是采购订单
//                Intent intent = new Intent();
//                switch (arg2) {
//                    case 0:
//                        intent.setClass(IndexActivity.this,
//                                GzptHjzxXzjhActivity.class);
//                        ShareUserInfo.setKey(context, "khzlname", "hjzx");
//                        break;
//                    case 1:
//                        intent.setClass(IndexActivity.this, GzptYybfActivity.class);
//                        ShareUserInfo.setKey(context, "khzlname", "yybf");
//                        break;
//                    case 2:
//                        intent.setClass(IndexActivity.this, GzptShhfActivity.class);
//                        ShareUserInfo.setKey(context, "khzlname", "shhf");
//                        break;
//                    case 3:
//                        intent.setClass(IndexActivity.this, GzptKhglActivity.class);
//                        ShareUserInfo.setKey(context, "khzlname", "khgl");
//                        break;
//                    case 4:
//                        intent.setClass(IndexActivity.this, GzptXzldActivity.class);
//                        ShareUserInfo.setKey(context, "khzlname", "xzld");
//                        break;
//                    case 5:
//                        intent.setClass(IndexActivity.this, GzptJqxzdwActivity.class);
//                        break;
//                    case 6:
//                        intent.setClass(IndexActivity.this, KhfwActivity.class);
//                        break;
//                    case 7://物流管理
//                        intent.setClass(IndexActivity.this, LogisticsManagementActivity.class);
//                        break;
//                    case 8://销售机会
//                        intent.setClass(IndexActivity.this,SalesOpportunitiesManagementActivity.class);
//                        break;
//                    case 9://合同管理
//                        intent.setClass(IndexActivity.this, ContractManagementActivity.class);
//                        break;
//                    case 10://项目管理
//                        intent.setClass(IndexActivity.this, ProjectManagementActivity.class);
//                        break;
//                    default:
//                        break;
//                }
//                startActivity(intent);
//            }
//        });

 /* switch (arg2) {
          case 0:

          intent.setClass(IndexActivity.this, MstxGztxActivity.class);
        startActivity(intent);
        break;
        case 1:
        intent.setClass(IndexActivity.this, MstxGsggActivity.class);
        startActivity(intent);
        break;
        case 2:
        if (isLocationEnabled()) {
        intent.setClass(activity, QdXzqdActivity.class);
        startActivity(intent);
        } else {
        Toast.makeText(IndexActivity.this, "定位服务未开启!请开启定位服务", Toast.LENGTH_SHORT).show();

        }
//					getQdData();// 查询出签到信息，并保存到服务器当中
        // saveQdMsg();
//				    mLocationClient.start();//开启定位功能

        return;
        // break;
        case 3:
        intent.setClass(IndexActivity.this, MstxGrrcActivity.class);
        startActivity(intent);
        break;
default:
        break;
        }*/
//
//    Intent intent = new Intent();
//                switch (arg2) {
//                        case 0:
//                        //统计分析库存报表
//                        intent.setClass(IndexActivity.this, TjfxKcbbActivity.class);
//        break;
//        case 1:
//        //统计分析应收应付
//        intent.setClass(IndexActivity.this, TjfxYsyfActivity.class);
//        break;
//        case 2:
//        //统计分析资金账户
//        intent.setClass(IndexActivity.this, TjfxZjzhActivity.class);
//        break;
//        case 3:
//        //统计分析销售收款汇总表
//        intent.setClass(IndexActivity.this, TjfxXsskhzbActivity.class);
//        break;
//        case 4:
//        //统计分析经营状况
//        intent.setClass(IndexActivity.this, TjfxJyzkActivity.class);
//        break;
//        }
//        startActivity(intent);