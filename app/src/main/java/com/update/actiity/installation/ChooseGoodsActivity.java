package com.update.actiity.installation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.adapter.DropMenuAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.cr.myinterface.SLViewValueChange;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.EnterSerialNumberActivity;
import com.update.actiity.WeChatCaptureActivity;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.ChooseGoodsData;
import com.update.model.Serial;
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
import butterknife.ButterKnife;
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
    @BindView(R.id.mFilterContentView)
    PullToRefreshLayout pullToRefreshLayoutView;
    @BindView(R.id.bt_jxtj)
    Button btJxtj;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private Map<String, Object> mParmMap;
    private int page_number;
    private List<ChooseGoodsData> mList;

    private int possion;//item出发点击事件的位置
    private Gson mGson;

    private int kind;
    private String barcode;//新增条码

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        kind = getIntent().getIntExtra("kind", 0);
        mGson = new Gson();
        page_number = 1;
        presenter = new BaseP(this, this);
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        presenter.post(3, ServerURL.GOODSTYPE,parmMap);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("curpage", page_number);
        mParmMap.put("pagesize", 10);

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
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getChooseGoodsHolder(ChooseGoodsActivity.this, parent);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.ChooseGoodsHolder holder, final ChooseGoodsData data) {
                holder.tvGoodName.setText("名称：" + data.getName());
                holder.tvCoding.setText("编码：" + data.getCode());
                holder.tvSpecifications.setText("规格：" + data.getSpecs());
                holder.tvModel.setText("型号：" + data.getModel());
                holder.tvUnit.setText("单位：" + data.getUnitname());
                holder.slView.setSl(data.getNumber());//设置数量
                if (kind > 0) {
                    if (TextUtils.isEmpty(data.getFaultinfo()))
                        data.setFaultinfo("");
                    if (TextUtils.isEmpty(data.getEnsureid())) {
                        data.setEnsureid("");
                        data.setEnsurename("");
                    }
                    if (TextUtils.isEmpty(data.getFaultid())) {
                        data.setFaultid("");
                        data.setFaultname("");
                    }
                }
                if (data.isCheck()) {//判断是否选中
                    holder.cbView.setChecked(true);//设置ChecBox的选中状态
                    holder.llNumber.setVisibility(View.VISIBLE);//数量选择条目显示
                    holder.vLine.setVisibility(View.VISIBLE);//横线显示
                    if (kind > 0) {
                        holder.llMaintenance.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.cbView.setChecked(false);
                    holder.llNumber.setVisibility(View.GONE);
                    holder.vLine.setVisibility(View.GONE);
                    if (kind > 0) {
                        holder.llMaintenance.setVisibility(View.GONE);
                    }
                }
                holder.cbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//ChecBox点击事件
                        data.setCheck(holder.cbView.isChecked());
                        if (holder.cbView.isChecked()) {//判断是否选中
                            holder.cbView.setChecked(true);//设置ChecBox的选中状态
                            holder.llNumber.setVisibility(View.VISIBLE);//数量选择条目显示
                            holder.vLine.setVisibility(View.VISIBLE);//横线显示
                            if (kind > 0) {
                                holder.llMaintenance.setVisibility(View.VISIBLE);
                            }
                            if (TextUtils.isEmpty(data.getSerialinfo())) {
                                UUID uuid = UUID.randomUUID();
                                data.setSerialinfo(uuid.toString().toUpperCase());
                            }
                        } else {
                            holder.cbView.setChecked(false);
                            holder.llNumber.setVisibility(View.GONE);
                            holder.vLine.setVisibility(View.GONE);
                            if (kind > 0) {
                                holder.llMaintenance.setVisibility(View.GONE);
                            }
                            data.setNumber(1.0);//取消选中数量恢复默认
                            holder.slView.setSl(data.getNumber());
                        }
                    }
                });
                holder.slView.setOnValueChange(new SLViewValueChange() {//数量控件数量变换监听
                    @Override
                    public void onValueChange(double sl) {
                        data.setNumber(sl);
                    }
                });
                //序列号点击事件
                holder.tvSerialNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        possion = holder.getLayoutPosition();
                        startActivityForResult(new Intent(ChooseGoodsActivity.this, EnterSerialNumberActivity.class)
                                .putExtra("billid", "0")
                                .putExtra("uuid", data.getSerialinfo())
                                .putExtra("DATA", mGson.toJson(data.getSerials())), 11);

                    }
                });
                if (kind > 0) {
                    holder.tvWarrantyStatus.setText(data.getEnsurename());
                    holder.llWarrantyStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            possion = holder.getLayoutPosition();
                            startActivityForResult(new Intent(ChooseGoodsActivity.this, NetworkDataSingleOptionActivity.class)
                                    .putExtra("zdbm", "BXZT").putExtra("title", "保修状态选择"), 12);
                        }
                    });
                    holder.tvFaultType.setText(data.getFaultname());
                    holder.llFaultType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            possion = holder.getLayoutPosition();
                            startActivityForResult(new Intent(ChooseGoodsActivity.this, NetworkDataSingleOptionActivity.class)
                                    .putExtra("zdbm", "GZLB").putExtra("title", "故障类别选择"), 13);
                        }
                    });
                    holder.etFaultDescription.setText(data.getFaultinfo());
                    final int position = holder.getLayoutPosition();
                    holder.etFaultDescription.setTag(position);
                    holder.etFaultDescription.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            //关键点：1.给edittext设置tag，此tag用来与position做对比校验，验证当前选中的edittext是否为需要的控件;
//                            2.焦点判断：只有当前有焦点的edittext才有更改数据的权限，否则会造成数据紊乱
//                            3.edittext内数据变动直接直接更改datalist的数据值，以便滑动view时显示正确
                            if ((Integer) holder.etFaultDescription.getTag() == position && holder.etFaultDescription.hasFocus()) {
                                data.setFaultinfo(s.toString());
                            }
                        }
                    });
                }
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
                mParmMap.put("goodsname", editable.toString());
                page_number = 1;
                mParmMap.put("curpage", page_number);
                presenter.post(0, ServerURL.SELECTGOODS, mParmMap);
            }
        });
        //判断是否是扫一扫
        if (this.getIntent().hasExtra("barcode")) {
            barcode = this.getIntent().getExtras().getString("barcode");
            btJxtj.setVisibility(View.VISIBLE);
            mParmMap. put("barcode", barcode);//新增条码

        }
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择商品");
//        titlebar.setIvRightImageResource(R.drawable.oper);
//        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
//            @Override
//            public void onClick(int i) {
//                switch (i) {
//                    case 0://增加安装登记
//
//                        break;
//                }
//            }
//        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh(PullToRefreshLayout playout) {
        page_number = 1;
        mParmMap.put("curpage", page_number);
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


        switch (requestCode) {
            case 0://第一次加载数据或者刷新数据
                mList = mGson.fromJson((String) data,
                        new TypeToken<List<ChooseGoodsData>>() {
                        }.getType());
                ;
                mAdapter.setList(mList);
                break;
            case 1:
                List<ChooseGoodsData> list = mGson.fromJson((String) data,
                        new TypeToken<List<ChooseGoodsData>>() {
                        }.getType());
                if (list == null || list.size() == 0) {

                } else {
                    page_number = page_number + 1;
                    mList.addAll(list);
                    mAdapter.setList(mList);
                }
                break;
            case 3:

                String[] titleList = new String[]{"分类"};
                List[] contextList = new List[]{(List<Map<String, Object>>) PaseJson.paseJsonToObject(data.toString())};
                dropDownMenu.setMenuAdapter(new DropMenuAdapter(mActivity, titleList, contextList, new OnFilterDoneListener() {
                    @Override
                    public void onFilterDone(int position, Map map) {
                        dropDownMenu.setPositionIndicatorText(position, map.get("name").toString());
                        dropDownMenu.close();
                        mList.clear();
                        mParmMap.put("goodstype", map.get("lcode").toString());
                        mParmMap.put("curpage", page_number);
                        presenter.post(0, ServerURL.SELECTGOODS, mParmMap);
                    }


                }));
                presenter.post(0, ServerURL.SELECTGOODS, mParmMap);
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

    @OnClick({R.id.bt_jxtj, R.id.bt_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_jxtj:
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class), 18);
                break;
            case R.id.bt_view:
                if (mList == null || mList.size() == 0)//判断选择商品数据是否为空
                    return;
                List<ChooseGoodsData> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {//挑出选中商品
                    ChooseGoodsData chooseGoodsData = mList.get(i);
                    if (chooseGoodsData.isCheck())//判断商品选中状态
                        list.add(chooseGoodsData);
                }
                setResult(RESULT_OK, new Intent().putExtra("DATA", mGson.toJson(list)));
                finish();
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 11:
                //处理返回的序列号信息
                List<Serial> serials = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<List<Serial>>() {
                }.getType());
                mList.get(possion).setSerials(serials);
                break;
            case 12://保修状态
                mList.get(possion).setEnsureid(data.getStringExtra("CHOICE_RESULT_ID"));
                mList.get(possion).setEnsurename(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mAdapter.setList(mList);
                break;
            case 13://保修状态
                mList.get(possion).setFaultid(data.getStringExtra("CHOICE_RESULT_ID"));
                mList.get(possion).setFaultname(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mAdapter.setList(mList);
                break;
            case 18://扫一扫选择商品
                page_number = 1;
                barcode = data.getStringExtra("qr");
                mParmMap.put("barcode", barcode);//新增条码
                mParmMap.put("curpage", page_number);
                presenter.post(1, ServerURL.SELECTGOODS, mParmMap);
                break;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
