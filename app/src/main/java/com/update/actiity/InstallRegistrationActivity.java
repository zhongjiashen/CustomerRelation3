package com.update.actiity;

import android.content.Intent;
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
import com.update.actiity.choose.LocalDataSingleOptionActivity;
import com.update.actiity.choose.StateAuditChoiceActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
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


    private List mList;

    private TimePickerView mTimePickerView;//时间选择弹窗
    private Map<String, Object> mParmMap;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mList = new ArrayList();
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("opid", ShareUserInfo.getUserId(this));
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
        pullRecycleView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.InstallRegistrationHolder>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getInstallRegistrationHolder(InstallRegistrationActivity.this);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.InstallRegistrationHolder holder, int position) {

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
                        openRightLayout();
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
                startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class),11);
                break;
            case R.id.bt_reset://重置
                initRightPopWindow();
                break;
            case R.id.bt_query://查询
                openRightLayout();
                mParmMap.put("dbname", ShareUserInfo.getDbName(this));
                mParmMap.put("tabname", "tb_installreg");
                mParmMap.put("clientid", "0");
                mParmMap.put("qsrq", tvTimeLeft.getText().toString());
                mParmMap.put("zzrq", tvTimeRight.getText().toString());
                mParmMap.put("shzt", "0");
                mParmMap.put("djzt", "0");
                mParmMap.put("depid", "0");
                mParmMap.put("empid", "0");
                mParmMap.put("curpage", "0");//当前页
                mParmMap.put("pagesize", "10");//每页加载数据大小
                presenter.post(0, "billlist", mParmMap);
                break;
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh(PullToRefreshLayout playout) {
        pullToRefreshLayoutView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshLayoutView.refreshFinish(true);

            }
        }, 2000); //
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullLayout) {
        pullToRefreshLayoutView.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshLayoutView.loadMoreFinish(true);
                mList.add("");
                mList.add("");
            }
        }, 2000); //
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
        switch (requestCode){
            case 11:
                tvAuditStatus.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
        }
    }


}
