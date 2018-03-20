package com.update.actiity;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.choose.ScreeningActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
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


    private Map<String, Object> mParmMap;
    /*上传参数变量*/
    private int page_number;//页码
    private String start_time;//起始日期
    private String end_time;//截止日期
    private String shzt;//审核状态 (0未审 1已审 2 审核中   9全部)
    private String fwjg;//安装结果ID	是 （0全部）
    private String fwry;//安装人员ID	是 （0全部）

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mParmMap=new ArrayMap<>();
        mList=new ArrayList();
        Date date = new Date();
        page_number = 1;
        start_time = DateUtil.DateToString(date, "yyyy-MM-") + "01";
        end_time = DateUtil.DateToString(date, "yyyy-MM-dd");
        shzt = "9";
        fwjg="0";
        fwry="0";
        mList = new ArrayList();
        presenter = new BaseP(this, this);
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "AZZX");
        mParmMap.put("clientid", "0");
        mParmMap.put("qsrq", start_time);
        mParmMap.put("zzrq", end_time);
        mParmMap.put("shzt", shzt);
        mParmMap.put("fwjg", fwjg);
        mParmMap.put("fwry", fwry);
        mParmMap.put("opid", ShareUserInfo.getUserId(this));
        mParmMap.put("curpage", page_number);//当前页
        mParmMap.put("pagesize", "10");//每页加载数据大小
        presenter.post(0,"billlistnew", mParmMap);
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
        pullRecycleView.setAdapter(mAdapter=new BaseRecycleAdapter<ViewHolderFactory.PerformInstallationHolder,String>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getPerformInstallationHolder(PerformInstallationActivity .this);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.PerformInstallationHolder holder, String data) {

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
                        startActivityForResult(new Intent(PerformInstallationActivity.this, ScreeningActivity.class)
                                .putExtra("kind",1), 11);
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


}
