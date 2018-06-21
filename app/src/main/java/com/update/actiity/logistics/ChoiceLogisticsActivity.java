package com.update.actiity.logistics;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshActivity;
import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshAddActivity;
import com.cr.activity.jxc.cggl.cgth.JxcCgglCgthActivity;
import com.cr.activity.jxc.cggl.cgth.JxcCgglCgthAddActivity;
import com.cr.activity.jxc.ckgl.kcbd.JxcCkglKcbdActivity;
import com.cr.activity.jxc.ckgl.kcbd.JxcCkglKcbdAddActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdAddActivity;
import com.cr.activity.jxc.xsgl.xsth.JxcXsglXsthActivity;
import com.cr.activity.jxc.xsgl.xsth.JxcXsglXsthAddActivity;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ScreeningProjectActivity;
import com.update.actiity.contract.AddContractActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.request.RqChoiceLogisticsListData;
import com.update.model.request.RqProjectListData;
import com.update.model.request.WldlxtTabData;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;
import com.update.viewbar.refresh.PullToRefreshLayout;
import com.update.viewbar.refresh.PullableRecyclerView;
import com.update.viewholder.ViewHolderFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/28 0028 下午 3:04
 * Description:物理单据引用界面
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/28 0028         申中佳               V1.0
 */
public class ChoiceLogisticsActivity extends BaseActivity implements
        PullToRefreshLayout.OnRefreshListener {

    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @BindView(R.id.prv_view)
    PullableRecyclerView prvView;
    @BindView(R.id.prl_view)
    PullToRefreshLayout prlView;


    private Map<String, Object> mParmMap;
    private Date mDate;
    private Gson mGson;
    /*上传参数变量*/
    private int page_number;//页码
    private String mQsrq;//开始日期
    private String mZzrq;//截止日期
    private String mCname;//单位名称（模糊查询用）


    private List<RqChoiceLogisticsListData> mList;


    private String mBilltype;//单据类型

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mBilltype = getIntent().getStringExtra("billtype");
        presenter = new BaseP(this, this);
        mParmMap = new ArrayMap<>();
        mGson = new Gson();
        mDate = new Date();

        mQsrq = DateUtil.DateToString(mDate, "yyyy-MM-") + "01";
        mZzrq = DateUtil.DateToString(mDate, "yyyy-MM-dd");
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("billtype", mBilltype);//每页加载数据大小
        mParmMap.put("pagesize", "10");//每页加载数据大小
        http();
    }

    private void http() {
        page_number = 1;
        mParmMap.put("qsrq", mQsrq);//
        mParmMap.put("zzrq", mZzrq);//
        mParmMap.put("cname", mCname);//
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, "wldrefbillmaster", mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_list;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        prlView.setOnRefreshListener(this);
        prvView.setLayoutManager(new LinearLayoutManager(this));
        prvView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ChoiceProjectHolder, RqChoiceLogisticsListData>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getChoiceProjectHolder(ChoiceLogisticsActivity.this, parent);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.ChoiceProjectHolder holder, final RqChoiceLogisticsListData data) {
                holder.tvReceiptNumber.setText(data.getCode());//单据编号设置
                holder.tvCompanyName.setText(data.getShipcname());//公司名称设置

                holder.tvMoney.setText("单据日期：" + data.getBilldate());
                holder.tvProjectName.setVisibility(View.GONE);
                holder.tvPhase.setText("单据金额：￥" + data.getTotalamt());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        是否代收代付、代收代付账户、代收代付金额是根据所选择的关联单据带过来的信息，不可修改
                        Intent intent = new Intent();
                        intent.putExtra("referbillid", data.getBillid() + "");//引用单ID
                        intent.putExtra("refertype", data.getBilltypeid() + "");//引用单ID
                        intent.putExtra("code", data.getCode());//引用单据编号
                        intent.putExtra("isproxy", data.getIsproxy() + "");//代收金额
                        intent.putExtra("bankid", data.getBankid() + "");//代收账户ID
                        intent.putExtra("bankname", data.getBankid() + "");//代收账户名称
                        intent.putExtra("proxyamt", data.getProxyamt() + "");//代收金额
                        intent.putExtra("shipclientid", data.getShipclientid());
                        intent.putExtra("shipcname", data.getShipcname());
                        intent.putExtra("name",data.getProjectname());
                        intent.putExtra("lxrid",data.getLxrid());
                        intent.putExtra("lxrname",data.getLxrname());
                        intent.putExtra("phone",data.getPhone());
                        intent.putExtra("shipto",data.getShipto());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }

        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        Gson gson = new Gson();
        List<WldlxtTabData> list = gson.fromJson(getIntent().getStringExtra("data"),
                new TypeToken<List<WldlxtTabData>>() {
                }.getType());
        String title = "";
        final Intent intent=new Intent();
        for (int i = 0; i < list.size(); i++) {
            if (mBilltype.equals(list.get(i).getId() + "")) {
                switch (list.get(i).getDictmc()) {
                    case "tb_received":
                        title = "采购收货";
                        intent.setClass(mActivity, JxcCgglCgshAddActivity.class);
//                        startActivityForResult(new Intent(this, JxcCgglCgshActivity.class)
//                                        .putExtra("select", ""),
//                                14);
                        break;
                    case "tb_preturn":
                        title = "采购退货";
                        intent.setClass(mActivity, JxcCgglCgthAddActivity.class);
//                        startActivityForResult(new Intent(this, JxcCgglCgthActivity.class)
//                                        .putExtra("select", ""),
//                                14);
                        break;
                    case "tb_invoice":
                        title = "销售开单";
                        intent.setClass(mActivity, JxcXsglXskdAddActivity.class);
//                        startActivityForResult(new Intent(this, JxcXsglXskdActivity.class)
//                                        .putExtra("select", ""),
//                                14);
                        break;
                    case "tb_sreturn":
                        title = "销售退货";
                        intent.setClass(mActivity, JxcXsglXsthAddActivity.class);
//                        startActivityForResult(new Intent(this, JxcXsglXsthActivity.class)
//                                        .putExtra("select", ""),
//                                14);
                        break;
                    case "tb_inout":
                        title = "库存变动";
                        intent.setClass(mActivity, JxcCkglKcbdAddActivity.class);
//                        startActivityForResult(new Intent(this, JxcCkglKcbdActivity.class)
//                                        .putExtra("select", ""),
//                                14);
                        break;
                    case "tb_assetsin":


                        break;
                    case "tb_assetsout":

                        break;
                    case "syn_invoice_wxd":

                        break;
                    case "tb_backfact":

                        break;
                    case "tb_svchange":

                        break;
                    case "syn_invoice_azd":

                        break;


                }
            }
        }
        titlebar.setTitleText(this, "选择单据");
//        titlebar.setIvRightTwoImageResource(R.drawable.oper);
        titlebar.setIvRightImageResource(R.drawable.oper);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://增加安装登记
                        startActivityForResult(new Intent(ChoiceLogisticsActivity.this, ScreeningProjectActivity.class)
                                .putExtra("kind", 1), 11);
                        break;
//                    case 1://打开右边侧滑菜单
//                        startActivityForResult(new Intent(ChoiceLogisticsActivity.this, ScreeningProjectActivity.class)
//                                .putExtra("kind", 1), 11);
//                        break;
                }
            }
        });
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 11:
                mQsrq = data.getStringExtra("qsrq");
                mZzrq = data.getStringExtra("zzrq");

                http();
                break;
        }
    }

    /**
     * 下拉刷新
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        page_number = 1;
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, ServerURL.BILLLIST, mParmMap);
    }

    /**
     * 上拉加载
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        mParmMap.put("curpage", (page_number + 1));
        presenter.post(1, ServerURL.BILLLIST, mParmMap, false);
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
        List<RqChoiceLogisticsListData> list = gson.fromJson((String) data,
                new TypeToken<List<RqChoiceLogisticsListData>>() {
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
                prlView.refreshFinish(true);//刷新完成
                break;
            case 1:
                prlView.loadMoreFinish(true);//加载完成
                break;
        }

    }
}
