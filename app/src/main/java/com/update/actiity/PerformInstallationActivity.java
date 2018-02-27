package com.update.actiity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.base.BaseRecycleAdapter;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.ViewHolderFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/23 0023 下午 5:06
 * Description:安装执行列表
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/23 0023         申中佳               V1.0
 */
public class PerformInstallationActivity extends BaseActivity implements
        PullToRefreshLayout.OnRefreshListener {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    @BindView(R.id.tv_audit_status)
    TextView tvAuditStatus;
    @BindView(R.id.et_unit_name)
    EditText etUnitName;
    @BindView(R.id.bt_reset)
    Button btReset;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.pullRecycle_view)
    PullableRecyclerView pullRecycleView;
    @BindView(R.id.pullToRefreshLayout_view)
    PullToRefreshLayout pullToRefreshLayoutView;
    @BindView(R.id.ll_audit_status)
    LinearLayout llAuditStatus;
    @BindView(R.id.tv_installation_result)
    TextView tvInstallationResult;
    @BindView(R.id.ll_installation_result)
    LinearLayout llInstallationResult;
    @BindView(R.id.tv_installation_personnel)
    TextView tvInstallationPersonnel;
    @BindView(R.id.ll_installation_personnel)
    LinearLayout llInstallationPersonnel;
    @BindView(R.id.et_goods_name)
    EditText etGoodsName;

    private List mList;
    private TimePickerView mTimePickerView;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mList=new ArrayList();
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
        pullRecycleView.setAdapter(mAdapter=new BaseRecycleAdapter<ViewHolderFactory.PerformInstallationHolder>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getPerformInstallationHolder(PerformInstallationActivity .this);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.PerformInstallationHolder holder, int position) {

            }
        });
        mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "安装执行");
        titlebar.setIvRightImageResource(R.drawable.oper);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://打开右边侧滑菜单
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
    private void initRightPopWindow(){
        Date date=new Date();
        tvTimeLeft.setText(DateUtil.DateToString(date,"yyyy-MM-")+"01");
        tvTimeRight.setText(DateUtil.DateToString(date,"yyyy-MM-dd"));
        tvAuditStatus.setText("全部");
        etUnitName.setText("");
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

    public void selectTime(final int i){
        mTimePickerView.setTime(new Date());
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                switch (i){
                    case 0:
                        tvTimeLeft.setText(DateUtil.DateToString(date,"yyyy-MM-dd"));
                        break;
                    case 1:
                        tvTimeRight.setText(DateUtil.DateToString(date,"yyyy-MM-dd"));
                        break;
                }

            }
        });
        mTimePickerView.show();
    }
}
