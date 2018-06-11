package com.update.actiity.maintenance;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ScreeningActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.PerformInstallationData;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.ViewHolderFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/26 0026 上午 10:49
 * Description:检测维修
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/26 0026         申中佳               V1.0
 */
public class DetectionMaintenanceActivity extends BaseActivity implements
        PullToRefreshLayout.OnRefreshListener {
    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @BindView(R.id.pullRecycle_view)
    PullableRecyclerView pullRecycleView;
    @BindView(R.id.pullToRefreshLayout_view)
    PullToRefreshLayout pullToRefreshLayoutView;

    @BindView(R.id.search)
    EditText search;

    private List<PerformInstallationData> mList;
    private TimePickerView mTimePickerView;


    private Map<String, Object> mParmMap;
    /*上传参数变量*/
    private int page_number;//页码
    private String start_time;//起始日期
    private String end_time;//截止日期
    private String shzt;//审核状态 (0未审 1已审 2 审核中   9全部)
    private String fwjg;//安装结果ID	是 （0全部）
    private String fwry;//安装人员ID	是 （0全部）
    private String commfilter;//快捷查找用，单位名称，商品
    private String goodsname;// 商品名称
    private String cname;// 单位名称

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mParmMap = new ArrayMap<>();
        mList = new ArrayList();
        Date date = new Date();
        start_time = DateUtil.DateToString(date, "yyyy-MM-") + "01";
        end_time = DateUtil.DateToString(date, "yyyy-MM-dd");
        shzt = "0";
        fwjg = "0";
        fwry = "0";
        mList = new ArrayList();
        presenter = new BaseP(this, this);
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "JCWX");
        mParmMap.put("clientid", "0");
        mParmMap.put("opid", ShareUserInfo.getUserId(this));
        mParmMap.put("pagesize", "10");//每页加载数据大小
    }

    private void http() {
        page_number = 1;
        mParmMap.put("qsrq", start_time);
        mParmMap.put("zzrq", end_time);
        mParmMap.put("shzt", shzt);
        mParmMap.put("fwjg", fwjg);
        mParmMap.put("fwry", fwry);
        mParmMap.put("commfilter", commfilter);
        mParmMap.put("goodsname", goodsname);
        mParmMap.put("cname", cname);
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, "billlistnew", mParmMap);
    }

    /**
     * 刷新界面数据
     */
    @Override
    protected void refersh() {
        page_number = 1;
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, "billlistnew", mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_perform_installation;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        pullToRefreshLayoutView.setOnRefreshListener(this);
        pullRecycleView.setLayoutManager(new LinearLayoutManager(this));
        pullRecycleView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.PerformInstallationHolder, PerformInstallationData>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getPerformInstallationHolder(DetectionMaintenanceActivity.this,parent);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.PerformInstallationHolder holder, final PerformInstallationData data) {
                holder.tvCompanyName.setText(data.getCname());
                holder.tvTechnician.setText(data.getEmpnames());
                holder.tvCommodityInformation.setText(data.getGoodsname());
                switch (data.getShzt()) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
                    case 0://未审
                        holder.tvAuditStatus.setText("未审核");
                        holder.tvAuditStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                        break;
                    case 1://已审
                        holder.tvAuditStatus.setText("已审核");
                        holder.tvAuditStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                        break;

                }
                switch (data.getWxjgid()) {//执行结果设置
                    case 1://未处理
                        holder.tvMaintenanceStatus.setText(data.getWxjgname());
                        holder.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                        break;
                    case 2://处理中
                        holder.tvMaintenanceStatus.setText(data.getWxjgname());
                        holder.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                        break;
                    case 3://已完成
                        holder.tvMaintenanceStatus.setText(data.getWxjgname());
                        holder.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#00CC00"));
                        break;
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(DetectionMaintenanceActivity.this, DetectionDetailsActivity.class)
                                .putExtra("itemno", data.getItemno() + "")
                                .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                    }
                });
            }

        });
        mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                commfilter = s.toString();
                http();
            }
        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "检测维修");
        titlebar.setIvRightImageResource(R.drawable.oper);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://打开右边侧滑菜单
                        startActivityForResult(new Intent(DetectionMaintenanceActivity.this, ScreeningActivity.class)
                                .putExtra("kind", 1), 11);
                        break;
                }
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh(PullToRefreshLayout playout) {
        page_number = 1;
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, "billlistnew", mParmMap);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullLayout) {
        mParmMap.put("curpage", (page_number + 1));
        presenter.post(1, "billlistnew", mParmMap);
    }


    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 11:
                start_time = data.getStringExtra("qsrq");
                end_time = data.getStringExtra("zzrq");
                shzt = data.getStringExtra("shzt");
                fwjg = data.getStringExtra("fwjg");
                fwry = data.getStringExtra("fwry");
                cname = data.getStringExtra("cname");
                goodsname = data.getStringExtra("goodsname");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        http();
    }
    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        if (data.toString().equals("nmyqx")) {
            showShortToast("您没有操作该功能菜单的权限");
            finish();
            return;
        }
        Gson gson = new Gson();
        List<PerformInstallationData> list = gson.fromJson((String) data,
                new TypeToken<List<PerformInstallationData>>() {
                }.getType());
        switch (requestCode) {
            case 0://第一次加载数据或者刷新数据
                mList = list;
                mAdapter.setList(mList);
                break;
            case 1:
                if (list == null || list.size() == 0) {
                    showShortToast("没有更多内容");
                } else {
                    page_number = page_number + 1;
                    mList.addAll(list);
                    mAdapter.setList(mList);
                }
                break;
        }
    }

    /**
     * 网络请求结束
     */
    @Override
    public void httpFinish(int requestCode) {
        switch (requestCode) {
            case 0:
                pullToRefreshLayoutView.refreshFinish(true);//刷新完成
                break;
            case 1:
                pullToRefreshLayoutView.loadMoreFinish(true);
                break;
        }

    }
}
//    @BindView(R.id.titlebar)
//    TitleBar titlebar;
//    @BindView(R.id.search)
//    EditText search;
//
//
//    private Map<String, Object> mParmMap;
//    /*上传参数变量*/
//    private int page_number;//页码
//    private String start_time;//起始日期
//    private String end_time;//截止日期
//    private String shzt;//审核状态 (0未审 1已审 2 审核中   9全部)
//
//    private String fwjg;//维修结果ID	是 （0全部）
//    private String fwry;//维修人员ID	是 （0全部）
//
//    /**
//     * 初始化变量，包括Intent带的数据和Activity内的变量。
//     */
//    @Override
//    protected void initVariables() {
//        Date date = new Date();
//        start_time = DateUtil.DateToString(date, "yyyy-MM-") + "01";
//        end_time = DateUtil.DateToString(date, "yyyy-MM-dd");
//        shzt = "9";
//        fwjg = "0";
//        fwry = "0";
//        presenter = new BaseP(this, this);
//        mParmMap = new HashMap<String, Object>();
//        presenter = new BaseP(this, this);
//        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
//        mParmMap.put("parms", "JCWX");
//        mParmMap.put("clientid", "0");
//        mParmMap.put("opid", ShareUserInfo.getUserId(this));
//        mParmMap.put("pagesize", "10");//每页加载数据大小
//        http();
//    }
//
//    private void http() {
//        page_number = 1;
//        mParmMap.put("qsrq", start_time);
//        mParmMap.put("zzrq", end_time);
//        mParmMap.put("shzt", shzt);
//        mParmMap.put("fwjg", fwjg);
//        mParmMap.put("fwry", fwry);
//        mParmMap.put("curpage", page_number);//当前页
//        presenter.post(0, "billlistnew", mParmMap);
//    }
//
//    /**
//     * 指定加载布局
//     *
//     * @return 返回布局
//     */
//    @Override
//    protected int getLayout() {
//        return R.layout.activity_detection_maintenance;
//    }
//
//    /**
//     * 初始化
//     */
//    @Override
//    protected void init() {
//        setTitlebar();
//    }
//
//    /**
//     * 标题头设置
//     */
//    private void setTitlebar() {
//        titlebar.setTitleText(this, "检测维修");
//        titlebar.setIvRightImageResource(R.drawable.oper);
//        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
//            @Override
//            public void onClick(int i) {
//                switch (i) {
//                    case 0://打开右边侧滑菜单
//                        startActivityForResult(new Intent(DetectionMaintenanceActivity.this, ScreeningActivity.class)
//                                .putExtra("kind", 3), 11);
//                        break;
//                }
//            }
//        });
//    }
//}
