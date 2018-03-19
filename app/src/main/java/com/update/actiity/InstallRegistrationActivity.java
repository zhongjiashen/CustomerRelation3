package com.update.actiity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.LocalDataSingleOptionActivity;
import com.update.actiity.choose.ScreeningActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.InstallRegistrationData;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.ViewHolderFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/22 0022 上午 11:51
 * Description:安装登记界面
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/22 0022         申中佳               V1.0
 */
public class InstallRegistrationActivity extends BaseActivity implements
        PullToRefreshLayout.OnRefreshListener {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    @BindView(R.id.tv_audit_status)
    TextView tvAuditStatus;
    @BindView(R.id.et_unit_name)
    EditText etUnitName;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.pullRecycle_view)
    PullableRecyclerView pullRecycleView;
    @BindView(R.id.ll_audit_status)
    LinearLayout llAuditStatus;
    @BindView(R.id.bt_reset)
    Button btReset;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.pullToRefreshLayout_view)
    PullToRefreshLayout pullToRefreshLayoutView;


    private List<InstallRegistrationData> mList;

    private TimePickerView mTimePickerView;//时间选择弹窗
    private Map<String, Object> mParmMap;
    /*上传参数变量*/
    private int page_number;//页码
    private String start_time;//起始日期
    private String end_time;//截止日期
    private String shzt;//审核状态 (0未审 1已审 2 审核中   9全部)
    private String djzt;//登记状态ID （维修登记、安装登记用传0表示全部 取字典zdbm=’WXDJZT’维修登记状态，zdbm=’AZDJZT’安装登记状态，其他单据传空）

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        Date date = new Date();
        page_number = 1;
        start_time = DateUtil.DateToString(date, "yyyy-MM-") + "01";
        end_time = DateUtil.DateToString(date, "yyyy-MM-dd");
        shzt = "9";
        djzt = "0";
        mList = new ArrayList();
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("opid", ShareUserInfo.getUserId(this));
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("tabname", "tb_installreg");
        mParmMap.put("clientid", "0");
        mParmMap.put("qsrq", start_time);
        mParmMap.put("zzrq", end_time);
        mParmMap.put("shzt", shzt);
        mParmMap.put("djzt", djzt);
        mParmMap.put("depid", "0");
        mParmMap.put("empid", "0");
        mParmMap.put("curpage", page_number);//当前页
        mParmMap.put("pagesize", "10");//每页加载数据大小
        presenter.post(0, ServerURL.BILLLIST, mParmMap);
//        mList.add("");
//        mList.add("");
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_install_registration;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        pullToRefreshLayoutView.setOnRefreshListener(this);
        pullRecycleView.setLayoutManager(new LinearLayoutManager(this));
        pullRecycleView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.InstallRegistrationHolder, InstallRegistrationData>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getInstallRegistrationHolder(InstallRegistrationActivity.this);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.InstallRegistrationHolder holder, final InstallRegistrationData data) {
                /*
                *下图审核状态：未审核（颜色FF6600）、审核中（颜色0066FF）、已审核（颜色00CC00）
                * 登记状态：未处理（颜色FF6600）、处理中（颜色0066FF）、已完成（颜色00CC00）
                 */
                holder.tvData.setText(data.getBilldate());//单据日期设置
                holder.tvReceiptNumber.setText("单据编号" + data.getCode());//单据编号设置
                holder.tvCompanyName.setText(data.getCname());//公司名称设置
                switch (data.getShzt()) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
                    case 0://未审
                        holder.tvAuditStatus.setText("未审核");
                        holder.tvAuditStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                        break;
                    case 1://已审
                        holder.tvAuditStatus.setText("已审核");
                        holder.tvAuditStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                        break;
                    case 2://审核中
                        holder.tvAuditStatus.setText("审核中");
                        holder.tvAuditStatus.setBackgroundColor(Color.parseColor("#00CC00"));
                        break;
                }
                switch (data.getDjzt()) {//登记状态设置,审核状态(0未审 1已审 2 审核中)
                    case 1://未处理
                        holder.tvMaintenanceStatus.setText("未处理");
                        holder.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                        break;
                    case 0://已审
                        holder.tvMaintenanceStatus.setText("已审核");
                        holder.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                        break;
                    case 2://审核中
                        holder.tvMaintenanceStatus.setText("审核中");
                        holder.tvMaintenanceStatus.setBackgroundColor(Color.parseColor("#00CC00"));
                        break;
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(InstallRegistrationActivity.this, InstallRegistrationDetailsActivity.class)
                                .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                    }
                });
            }

        });
        mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        initRightPopWindow();
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "安装登记");
        titlebar.setIvRightTwoImageResource(R.drawable.oper);
        titlebar.setIvRightImageResource(R.mipmap.ic_add);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://增加安装登记
                        startActivity(new Intent(InstallRegistrationActivity.this, AddInstallRegistrationActivity.class));
                        break;
                    case 1://打开右边侧滑菜单
                        startActivityForResult(new Intent(InstallRegistrationActivity.this, ScreeningActivity.class)
                                .putExtra("kind",0), 11);

                        break;
                }
            }
        });
    }

    // 右边菜单开关事件
    public void openRightLayout() {
        if (drawerLayout.isDrawerOpen(llMenu)) {
            drawerLayout.closeDrawer(llMenu);
        } else {
            drawerLayout.openDrawer(llMenu);
        }
    }

    /**
     * 初始化右边弹窗
     */
    private void initRightPopWindow() {
        Date date = new Date();
        tvTimeLeft.setText(DateUtil.DateToString(date, "yyyy-MM-") + "01");
        tvTimeRight.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
        tvAuditStatus.setText("全部");
        etUnitName.setText("");
    }


    @OnClick({R.id.tv_time_left, R.id.tv_time_right, R.id.ll_audit_status, R.id.bt_reset, R.id.bt_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time_left://开始时间选择
                selectTime(0);
                break;
            case R.id.tv_time_right://结束时间选择
                selectTime(1);
                break;
            case R.id.ll_audit_status://审核状态选择
                startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class), 11);
                break;
            case R.id.bt_reset://重置
                initRightPopWindow();
                break;
            case R.id.bt_query://查询
                openRightLayout();

                break;
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh(PullToRefreshLayout playout) {
        page_number = 1;
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, ServerURL.BILLLIST, mParmMap);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullLayout) {
        mParmMap.put("curpage", (page_number + 1));
        presenter.post(1, ServerURL.BILLLIST, mParmMap);
    }


    public void selectTime(final int i) {
        // TimePickerView 同样有上面设置样式的方法
        // 设置是否循环
//        mTimePickerView.setCyclic(true);
        // 设置滚轮文字大小
//        mTimePickerView.setTextSize(TimePickerView.TextSize.SMALL);
        // 设置时间可选范围(结合 setTime 方法使用,必须在)
//        Calendar calendar = Calendar.getInstance();
//        mTimePickerView.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR));
        // 设置选中时间
        mTimePickerView.setTime(new Date());
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                switch (i) {
                    case 0:
                        tvTimeLeft.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                    case 1:
                        tvTimeRight.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                }

            }
        });
        mTimePickerView.show();
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 11:
                tvAuditStatus.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
        }
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        Gson gson = new Gson();
        List<InstallRegistrationData> list = gson.fromJson((String) data,
                new TypeToken<List<InstallRegistrationData>>() {
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
