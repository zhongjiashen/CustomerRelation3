package com.update.actiity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.DepartmentData;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.ViewHolderFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/26 0026 上午 11:04
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/26 0026         申中佳               V1.0
 */
public class ChooseGoodsActivity extends BaseActivity implements
        PullToRefreshLayout.OnRefreshListener {

    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.pullRecycle_view)
    PullableRecyclerView pullRecycleView;
    @BindView(R.id.pullToRefreshLayout_view)
    PullToRefreshLayout pullToRefreshLayoutView;
    private Map<String, Object> mParmMap;
    private int page_number;
    private List<DepartmentData> mList;
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        page_number=1;
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("curpage", page_number);
        mParmMap.put("pagesize", 10);
        presenter.post(0, ServerURL.SELECTGOODS,mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_choose_good;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        pullToRefreshLayoutView.setOnRefreshListener(this);
        pullRecycleView.setLayoutManager(new LinearLayoutManager(this));
        pullRecycleView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.InstallRegistrationHolder,String>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getInstallRegistrationHolder(ChooseGoodsActivity.this);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.InstallRegistrationHolder holder, String data) {

            }

        });
    }
    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择商品");
        titlebar.setIvRightTwoImageResource(R.drawable.oper);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://增加安装登记

                        break;
                    case 1://打开右边侧滑菜单

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
