package com.update.actiity.choose;

import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/5 0005 下午 3:54
 * Description:项目选择
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/5 0005         申中佳               V1.0
 */
public class ProjectSelectionActivity extends BaseActivity implements
        PullToRefreshLayout.OnRefreshListener {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.pullRecycle_view)
    PullableRecyclerView pullRecycleView;
    @BindView(R.id.pullToRefreshLayout_view)
    PullToRefreshLayout pullToRefreshLayoutView;
    private Map<String, Object> mParmMap;


    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("tabname", "tb_project");
        mParmMap.put("clientid", getIntent().getStringExtra("clientid"));
        mParmMap.put("qsrq", "2016-01-01");
        mParmMap.put("zzrq", "2018-03-01");
        mParmMap.put("shzt", "1");
        mParmMap.put("opid", ShareUserInfo.getUserId(this));
        mParmMap.put("curpage", "0");//当前页
        mParmMap.put("pagesize", "10");//每页加载数据大小
        presenter.post(0, "billlist", mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_project_selection;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {

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

            }
        }, 2000); //
    }
}
