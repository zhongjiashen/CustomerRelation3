package com.cr.activity.jxc;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcSearchActivity;
import com.cr.adapter.common.CommonXzyyAdapter;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.installation.InstallRegistrationActivity;
import com.update.actiity.installation.InstallRegistrationDetailsActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.InstallRegistrationData;
import com.update.model.Serial;
import com.update.utils.LogUtils;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.ViewHolderFactory;
import com.update.viewholder.XzyydHolder;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class JxcXzyydActivity extends BaseActivity {
    @BindView(R.id.search)
    EditText search;

    String qsrq, jzrq, shzt = "0", cname;
    @BindView(R.id.pullRecycle_view)
    PullableRecyclerView pullRecycleView;
    @BindView(R.id.pullToRefreshLayout_view)
    PullToRefreshLayout pullToRefreshLayoutView;
    private Map<String, Object> mParmMap;
    List<KtXzYydMastData> list = new ArrayList<>();
    private int page_number = 1;//页码

    KtXzYydMastData mKtXzYydMastData;

    @Override
    protected void initVariables() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        qsrq = sdf.format(new Date()) + "01";
        jzrq = sdf.format(new Date())
                + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("pagesize", "10");//每页加载数据大小
        mParmMap.put("parms", this.getIntent().getExtras().getString("type"));
        mParmMap.put("clientid",
                this.getIntent().getExtras().getString("clientid"));
        mParmMap.put("opid", ShareUserInfo.getUserId(this));

        http(0);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_xzyyd;
    }

    @Override
    protected void init() {
        pullToRefreshLayoutView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page_number = 1;
                http(0);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                http(1);

            }
        });
        pullRecycleView.setLayoutManager(new LinearLayoutManager(this));
        pullRecycleView.setAdapter(mAdapter = new BaseRecycleAdapter<XzyydHolder.ViewHolder, KtXzYydMastData>(list) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return XzyydHolder.getViewHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final XzyydHolder.ViewHolder holder, final KtXzYydMastData data) {
                holder.tvMc.setText("名称："
                        + data.getCname());
                holder.tvBh.setText("编号：" + data.getCode());
                holder.tvRq
                        .setText("日期：" + data.getBilldate());

                holder.cbView.setChecked(data.isCheck());
                holder.cbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setCheck(holder.cbView.isChecked());

                    }
                });
            }

        });


    }

    @OnClick({R.id.fh, R.id.sx, R.id.qr_textview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fh:
                finish();
                break;
            case R.id.sx:
                Intent intent = new Intent();
                intent.setClass(mActivity, JxcCgglCgddShlcSearchActivity.class);
                intent.putExtra("qr", qsrq);
                intent.putExtra("zr", jzrq);
                startActivityForResult(intent, 1);
                break;
            case R.id.qr_textview:
                mKtXzYydMastData = null;
                String billids = "";
                for (int i = 0; i < list.size(); i++) {
                    KtXzYydMastData ktXzYydMastData = list.get(i);
                    if (ktXzYydMastData.isCheck()) {
                        billids += "," + ktXzYydMastData.getBillid();
                        if (mKtXzYydMastData == null) {//判断是否已经获取第一个订单的信息
                            mKtXzYydMastData = ktXzYydMastData;
                            //带入单位名称、联系人、联系电话、发票类型、项目、交货地址
//                            mapMaster.put("dwmc", ktXzYydMastData.getCname());//单位名称
//                            mapMaster.put("lxrId",ktXzYydMastData.getLinkmanid());//联系人Id
//                            mapMaster.put("lxrName", ktXzYydMastData.getContator());//联系人姓名
//                            mapMaster.put("lxrDh", ktXzYydMastData.getPhone());//联系人电话
//                            mapMaster.put("fplxId", ktXzYydMastData.getBilltypeid());//发票类型id
//                            mapMaster.put("fplxMc", ktXzYydMastData.getBilltypename());//发票类型名称
//                            mapMaster.put("projectId", ktXzYydMastData.getProjectid());//项目ID
//                            mapMaster.put("projectName", ktXzYydMastData.getProjectname());//项目名称
//                            mapMaster.put("dz",ktXzYydMastData.getShipto());//地址

                        }
                    }
                }
                httpDratils(billids.equals("") ? "" : billids.substring(1));
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                qsrq = data.getExtras().getString("qr");
                jzrq = data.getExtras().getString("zr");
                if (list != null)
                    list.clear();
                page_number = 1;
                http(0);
                break;
        }
    }

    private void http(int requestCode) {
        mParmMap.put("qsrq", qsrq);
        mParmMap.put("zzrq", jzrq);
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(requestCode, ServerURL.REFBILLMASTER, mParmMap);
    }

    /**
     * 获取订单从表信息
     *
     * @param billid
     */
    private void httpDratils(String billid) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("reftypeid", this.getIntent().getExtras().getString("reftypeid"));
        parmMap.put("billid", billid);
        presenter.post(3, ServerURL.REFBILLDETAIL, parmMap);
    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        switch (requestCode) {
            case 0:
                LogUtils.e((String) data);
                list = mPGson.fromJson((String) data, new TypeToken<List<KtXzYydMastData>>() {
                }.getType());

                mAdapter.setList(list);
                break;
            case 1:
                List<KtXzYydMastData> myList = mPGson.fromJson((String) data, new TypeToken<List<KtXzYydMastData>>() {
                }.getType());
                if (myList == null || myList.size() == 0) {
                    showShortToast("没有更多内容");
                } else {
                    page_number = page_number + 1;
                    list.addAll(myList);
                    mAdapter.setList(list);
                }
                break;
            case 3:
                List<Map<String, Object>> l = (List<Map<String, Object>>) PaseJson.paseJsonToObject((String) data);
                Intent intent = new Intent();
                intent.putExtra("data", mPGson.toJson(mKtXzYydMastData));
                intent.putExtra("spData", (Serializable) l);
                setResult(RESULT_OK, intent);
                finish();
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
