package com.update.actiity.audit;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ScreeningAuditActivity;
import com.update.actiity.contract.ContractActivity;
import com.update.actiity.installation.InstallRegistrationDetailsActivity;
import com.update.actiity.project.ProjectActivity;
import com.update.actiity.sales.SalesOpportunitiesActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.request.RqMyAuditListData;
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
 * Created by 1363655717 on 2018-04-07.
 * 我的审核列表
 */

public class MyAuditListActivity extends BaseActivity implements
        PullToRefreshLayout.OnRefreshListener {

    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.et_search)
    EditText etSearch;
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
    private String mBilltypeid;//单据类型ID
    private String mDepid;//部门id             ,必填 (0全部)
    private String mEmpid;//业务员ID
    private String mShzt;//审核状态 (0未审 1已审 2 审核中   9全部)
    private String mBillcode;// 单据编号
    private List<RqMyAuditListData> mList;


    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mParmMap = new ArrayMap<>();
        mGson = new Gson();
        mDate = new Date();

        mQsrq = DateUtil.DateToString(mDate, "yyyy-MM-") + "01";
        mZzrq = DateUtil.DateToString(mDate, "yyyy-MM-dd");
        mShzt = "9";
        mBilltypeid = "0";
        mDepid= "0";
        mEmpid = "0";
        mParmMap.put("opid", ShareUserInfo.getUserId(this));//登录操作员ID
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));

        mParmMap.put("pagesize", "10");//每页加载数据大小

    }

    private void http() {

        mParmMap.put("qsrq", mQsrq);//
        mParmMap.put("zzrq", mZzrq);//
        mParmMap.put("cname", mCname);//

        mParmMap.put("billtypeid", mBilltypeid);//项目单阶段ID（0全部）
        mParmMap.put("shzt", mShzt);//审核状态
        mParmMap.put("depid", mDepid);//
        mParmMap.put("empid", mEmpid);//业务员ID (没有的话传空或0)
        mParmMap.put("billcode", mBillcode);//业务员ID (没有的话传空或0)
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, "waitbillshlist", mParmMap);
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
        prvView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ProjectHolder, RqMyAuditListData>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getProjectHolder(MyAuditListActivity.this,parent);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.ProjectHolder holder, final RqMyAuditListData data) {
                holder.tvData.setText(data.getBilldate());
                holder.tvReceiptNumber.setText("单据编号:" + data.getCode());//单据编号设置
                holder.tvCompanyName.setText(data.getCname());//公司名称设置
                switch (data.getShzt()) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
                    case 0://未审
                        holder.tvAuditStatus.setText("未审核");
                        holder.tvAuditStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                        break;
                    case 1://已审
                        holder.tvAuditStatus.setText("已审核");
                        holder.tvAuditStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                        break;
                    case 2://审核中
                        holder.tvAuditStatus.setText("审核中");
                        holder.tvAuditStatus.setBackgroundColor(Color.parseColor("#00CC00"));
                        break;
                }
                holder.tvPhase.setText("￥" + data.getAmount());
                holder.tvProjectName.setText(data.getBilltypename());
                holder.tvMoney.setText(data.getOpname());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(data.getParms())){
                            showShortToast("该单据不能再手机上审核！");
                        }else {
                            switch (data.getParms()){
                                case "AZDJ"://安装登记单
                                    startActivityForResult(new Intent(MyAuditListActivity.this, InstallRegistrationDetailsActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;
                                case "XMD"://项目单
                                    startActivity(new Intent(MyAuditListActivity.this, ProjectActivity.class)
                                            .putExtra("billid", data.getBillid()+"")
                                            .putExtra("shzt", data.getShzt()));
                                    break;
                                case "XSJH"://销售机会单
                                    startActivity(new Intent(MyAuditListActivity.this, SalesOpportunitiesActivity.class)
                                            .putExtra("billid", data.getBillid()+"")
                                            .putExtra("shzt", data.getShzt()));
                                    break;
                                case "XSHT"://销售合同单
                                    startActivity(new Intent(MyAuditListActivity.this, ContractActivity.class)
                                            .putExtra("billid", data.getBillid() + "")
                                            .putExtra("shzt", data.getShzt()));
                                    break;
                                case "XSKD"://销售开单
                                    break;
                                case "WLD"://物流单
                                    break;
                            }
                        }

                    }
                });

            }

        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "我的审核");
        titlebar.setIvRightImageResource(R.drawable.oper);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://打开右边侧滑菜单
                        startActivityForResult(new Intent(MyAuditListActivity.this, ScreeningAuditActivity.class)
                                .putExtra("kind", 2), 11);
                        break;
                }
            }
        });
    }


    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 11:
                mQsrq = data.getStringExtra("qsrq");
                mZzrq = data.getStringExtra("zzrq");
                mCname = data.getStringExtra("cname");
                mShzt = data.getStringExtra("shzt");
                mDepid = data.getStringExtra("depid");
                mEmpid = data.getStringExtra("empid");
                mBillcode = data.getStringExtra("billcode");
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
        presenter.post(0, "waitbillshlist", mParmMap);
    }

    /**
     * 上拉加载
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        mParmMap.put("curpage", (page_number + 1));
        presenter.post(0, "waitbillshlist", mParmMap);
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
        List<RqMyAuditListData> list = gson.fromJson((String) data,
                new TypeToken<List<RqMyAuditListData>>() {
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
    @Override
    protected void onResume() {
        super.onResume();
        page_number = 1;
        http();
    }
}
