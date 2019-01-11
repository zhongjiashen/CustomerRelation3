package com.cr.activity.jxc;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cr.activity.tjfx.kcbb.TjfxKcbbSpjg2Activity;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.Serial;
import com.update.utils.CustomTextWatcher;
import com.update.utils.EditTextHelper;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.JxcXzspHolder;
import com.update.viewholder.XzXlhHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 选择序列号
 */
public class XzXlhActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.pullRecycle_view)
    PullableRecyclerView pullRecycleView;
    @BindView(R.id.mFilterContentView)
    PullToRefreshLayout mFilterContentView;

    private Map<String, Object> mParmMap;
    private int page_number = 0;//页码
    List<KtXzXlhData> list = new ArrayList<KtXzXlhData>();

    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("pagesize", "10");
        mParmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        mParmMap.put("parms", getIntent().getStringExtra("parms"));//类型（CGTH =采购退货,XSKD=销售开单,KCPD=库存盘点,CKDB=仓库调拨）
        mParmMap.put("storeid", getIntent().getStringExtra("storeid"));//仓库ID
        mParmMap.put("goodsid", getIntent().getStringExtra("goodsid"));//商品ID
        mParmMap.put("refertype", getIntent().getStringExtra("refertype"));// 引用类型 （采购退货有两种情况，一种手工录入商品时，传0或空；另一种是引用采购收货单时，传7 ；其他单据类型都传0或空）
        mParmMap.put("referitemno", getIntent().getStringExtra("referitemno"));//引用单明细ID（采购退货有两种情况，一种手工录入商品时，传0或空；另一种是引用采购收货单时，传引用明细的referitemno；其他单据类型都传0或空）
        http(0);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_xzxlh;
    }

    @Override
    protected void init() {
        setTitlebar();
        mFilterContentView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page_number = 0;
                http(0);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                http(1);

            }
        });
        pullRecycleView.setLayoutManager(new LinearLayoutManager(this));
        pullRecycleView.setAdapter(mAdapter = new BaseRecycleAdapter<XzXlhHolder.ViewHolder, KtXzXlhData>(list) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return XzXlhHolder.getViewHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final XzXlhHolder.ViewHolder holder, final KtXzXlhData data) {
                holder.cbView.setChecked(data.isCheck());
                //选择
                holder.cbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setCheck(holder.cbView.isChecked());
                    }
                });
                holder.tvSerialNumber.setText(data.getSerno());
            }
        });

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择序列号");
        titlebar.setRightText("完成");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {

            }
        });
    }

    private void http(int requestCode) {
        mParmMap.put("curpage", page_number + 1);//当前页
        presenter.post(requestCode, "selectcanserialinfo", mParmMap);
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        switch (requestCode) {
            case 0:
                list = new Gson().fromJson((String) data,
                        new TypeToken<List<KtXzXlhData>>() {
                        }.getType());
                if (list == null || list.size() == 0) {

                } else {
                    page_number = page_number + 1;
                    mAdapter.setList(list);
                }
            case 1:
                ArrayList<KtXzXlhData> myList = new Gson().fromJson((String) data,
                        new TypeToken<List<KtXzXlhData>>() {
                        }.getType());
                if (myList == null || myList.size() == 0) {
                    showShortToast("没有更多内容");
                } else {
                    page_number = page_number + 1;
                    list.addAll(myList);
                    mAdapter.setList(list);
                }
                break;
            case 2:

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
                mFilterContentView.refreshFinish(true);//刷新完成
                break;
            case 1:
                mFilterContentView.loadMoreFinish(true);
                break;
        }

    }

}
