package com.update.actiity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.cr.myinterface.SLViewValueChange;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.ChooseGoodsData;
import com.update.model.Serial;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.ViewHolderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

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
    private List<ChooseGoodsData> mList;

    private int possion;//item出发点击事件的位置
    private Gson mGson;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mGson = new Gson();
        page_number = 1;
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("curpage", page_number);
        mParmMap.put("pagesize", 10);
        presenter.post(0, ServerURL.SELECTGOODS, mParmMap);
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
        pullRecycleView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ChooseGoodsHolder, ChooseGoodsData>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getChooseGoodsHolder(ChooseGoodsActivity.this);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.ChooseGoodsHolder holder, final ChooseGoodsData data) {
                holder.tvGoodName.setText("名称：" + data.getName());
                holder.tvCoding.setText("编码：" + data.getCode());
                holder.tvSpecifications.setText("规格：" + data.getSpecs());
                holder.tvModel.setText("型号：" + data.getModel());
                holder.tvUnit.setText("名称：" + data.getUnitname());
                holder.slView.setSl(data.getNumber());//设置数量
                if (data.isCheck()) {//判断是否选中
                    holder.cbView.setChecked(true);//设置ChecBox的选中状态
                    holder.llNumber.setVisibility(View.VISIBLE);//数量选择条目显示
                    holder.vLine.setVisibility(View.VISIBLE);//横线显示
                } else {
                    holder.cbView.setChecked(false);
                    holder.llNumber.setVisibility(View.GONE);
                    holder.vLine.setVisibility(View.GONE);
                }
                holder.cbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//ChecBox点击事件
                        data.setCheck(holder.cbView.isChecked());
                        if (holder.cbView.isChecked()) {//判断是否选中
                            holder.cbView.setChecked(true);//设置ChecBox的选中状态
                            holder.llNumber.setVisibility(View.VISIBLE);//数量选择条目显示
                            holder.vLine.setVisibility(View.VISIBLE);//横线显示
                            if (TextUtils.isEmpty(data.getSerialinfo())) {
                                UUID uuid = UUID.randomUUID();
                                data.setSerialinfo(uuid.toString().toUpperCase());
                            }
                        } else {
                            holder.cbView.setChecked(false);
                            holder.llNumber.setVisibility(View.GONE);
                            holder.vLine.setVisibility(View.GONE);
                            data.setNumber(1.0);//取消选中数量恢复默认
                            holder.slView.setSl(data.getNumber());
                        }
                    }
                });
                holder.slView.setOnValueChange(new SLViewValueChange() {//数量控件数量变换监听
                    @Override
                    public void onValueChange(double sl) {
                        LogUtils.e("的身高多少");
                        data.setNumber(sl);
                    }
                });
                //序列号点击事件
                holder.tvSerialNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        possion = holder.getLayoutPosition();
                        showShortToast(possion + "");
                        startActivityForResult(new Intent(ChooseGoodsActivity.this, EnterSerialNumberActivity.class)
                                .putExtra("billid", "0")
                                .putExtra("uuid", data.getSerialinfo())
                                .putExtra("DATA", mGson.toJson(data.getSerials())), 11);

                    }
                });
            }

        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择商品");
        titlebar.setIvRightImageResource(R.drawable.oper);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://增加安装登记

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
        presenter.post(0, ServerURL.SELECTGOODS, mParmMap);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullLayout) {
        mParmMap.put("curpage", (page_number + 1));
        presenter.post(1, ServerURL.SELECTGOODS, mParmMap);
    }

    @Override
    public void returnData(int requestCode, Object data) {
        Gson gson = new Gson();
        List<ChooseGoodsData> list = gson.fromJson((String) data,
                new TypeToken<List<ChooseGoodsData>>() {
                }.getType());
        switch (requestCode) {
            case 0://第一次加载数据或者刷新数据
                mList = list;
                mAdapter.setList(mList);
                break;
            case 1:
                if (list == null || list.size() == 0) {

                } else {
                    page_number = page_number + 1;
                    mList.addAll(list);
                    mAdapter.setList(mList);
                }
                break;
        }

    }

    @Override
    public void httpfaile(int requestCode) {
        super.httpfaile(requestCode);
    }

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

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        //处理返回的序列号信息
        List<Serial> serials = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<List<Serial>>() {
        }.getType());
        mList.get(possion).setSerials(serials);
    }

    @OnClick(R.id.bt_view)
    public void onClick() {
        if(mList==null||mList.size()==0)//判断选择商品数据是否为空
            return;
        List<ChooseGoodsData> list =new ArrayList<>();
        for (int i=0;i<mList.size();i++){//挑出选中商品
            ChooseGoodsData chooseGoodsData=mList.get(i);
            if(chooseGoodsData.isCheck())//判断商品选中状态
                list.add(chooseGoodsData);
        }
        setResult(RESULT_OK,new Intent().putExtra("DATA",mGson.toJson(list)));
        finish();

    }
}
