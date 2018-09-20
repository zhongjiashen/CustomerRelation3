package com.update.actiity.audit;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddDetailActivity;
import com.cr.activity.jxc.cggl.cgfk.KtJxcCgglCgfkDetailActivity;
import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshDetailActivity;
import com.cr.activity.jxc.cggl.cgth.JxcCgglCgthDetailActivity;
import com.cr.activity.jxc.ckgl.chtj.JxcCkglChtjDetailActivity;
import com.cr.activity.jxc.ckgl.kcbd.JxcCkglKcbdDetailActivity;
import com.cr.activity.jxc.ckgl.kcpd.JxcCkglKcpdDetailActivity;
import com.cr.activity.jxc.ckgl.zzcx.JxcCkglZzcxDetailActivity;
import com.cr.activity.jxc.xsgl.xsdd.KtJxcXsglXsddDetailActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdDetailActivity;
import com.cr.activity.jxc.xsgl.xssk.JxcXsglXsskDetailActivity;
import com.cr.activity.jxc.xsgl.xsth.JxcXsglXsthDetailActivity;
import com.cr.activity.xjyh.fkd.XjyhFkdDetailActivity;
import com.cr.activity.xjyh.fyzc.XjyhFyzcDetailActivity;
import com.cr.activity.xjyh.qtsr.XjyhQtsrDetailActivity;
import com.cr.activity.xjyh.skd.XjyhSkdDetailActivity;
import com.cr.activity.xjyh.yhcq.XjyhYhcqDetailActivity;
import com.cr.tools.FigureTools;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ScreeningAuditActivity;
import com.update.actiity.contract.ContractActivity;
import com.update.actiity.installation.InstallRegistrationDetailsActivity;
import com.update.actiity.installation.InstallationDetailsActivity;
import com.update.actiity.logistics.LogisticsActivity;
import com.update.actiity.maintenance.DetectionDetailsActivity;
import com.update.actiity.maintenance.MaintenanceDetailsActivity;
import com.update.actiity.project.ProjectActivity;
import com.update.actiity.sales.SalesOpportunitiesActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.request.RqMyAuditListData;
import com.update.utils.DateUtil;
import com.update.utils.LogUtils;
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
    private String mBillcode;//单据编号（模糊查询用）
    private String mBilltypeid;//单据类型ID
    private String mDepid;//部门id             ,必填 (0全部)
    private String mEmpid;//业务员ID
    private String mShzt;//审核状态 (0未审 1已审 2 审核中   9全部)

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
        mParmMap.put("billcode", mBillcode);//
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
        return R.layout.activity_search_list;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        etSearch.setHint("输入单位名称/单据编号");
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    mBillcode=v.getText().toString();
                    http();
                    return true;
                }
                return false;
            }
        });
//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                mBillcode=s.toString();
//                http();
//            }
//        });



        prlView.setOnRefreshListener(this);
        prvView.setLayoutManager(new LinearLayoutManager(this));
        prvView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ProjectHolder, RqMyAuditListData>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getProjectHolder(mActivity,parent);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.ProjectHolder holder, final RqMyAuditListData data) {
                holder.tvData.setText(data.getBilldate());
                holder.tvReceiptNumber.setText(data.getCode());//单据编号设置
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
                holder.tvPhase.setText("￥" + FigureTools.sswrFigure(data.getAmount().toString()));
                holder.tvProjectName.setText(data.getBilltypename());
                holder.tvMoney.setText(data.getOpname());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(data.getParms())){
                            showShortToast("该单据不能在手机上审核！");
                        }else {
                            switch (data.getParms()){
                                case "CGDD"://采购订单
                                    startActivityForResult(new Intent(mActivity, JxcCgglCgddDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;
                                case "CGSH"://采购收货
                                    startActivityForResult(new Intent(mActivity, JxcCgglCgshDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;
                                case "CGTH"://采购退货
                                    startActivityForResult(new Intent(mActivity, JxcCgglCgthDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;
                                case "CGFK"://采购付款
                                    startActivityForResult(new Intent(mActivity, KtJxcCgglCgfkDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;
                                case "XSDD"://销售订单
                                    startActivity(new Intent(mActivity, KtJxcXsglXsddDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "XSKD"://销售开单
                                    startActivity(new Intent(mActivity, JxcXsglXskdDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "XSTH"://销售退货
                                    startActivity(new Intent(mActivity, JxcXsglXsthDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "XSSK"://销售收款
                                    startActivity(new Intent(mActivity, JxcXsglXsskDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "CHTJ"://存货调价
                                    startActivity(new Intent(mActivity, JxcCkglChtjDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "KCBD"://库存变动
                                    startActivity(new Intent(mActivity, JxcCkglKcbdDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "KCPD"://库存盘点
                                    startActivity(new Intent(mActivity, JxcCkglKcpdDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "ZZCX"://组装拆卸
                                    startActivity(new Intent(mActivity, JxcCkglZzcxDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "FYKZ"://费用支出
                                    startActivity(new Intent(mActivity, XjyhFyzcDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "QTSR"://其他收入
                                    startActivity(new Intent(mActivity, XjyhQtsrDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "YHCQ"://银行存取
                                    startActivity(new Intent(mActivity, XjyhYhcqDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "FKD"://付款单
                                    startActivity(new Intent(mActivity, XjyhFkdDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "SKD"://收款单
                                    startActivity(new Intent(mActivity, XjyhSkdDetailActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "XSJH"://销售机会单
                                    startActivity(new Intent(mActivity, SalesOpportunitiesActivity.class)
                                            .putExtra("billid", data.getBillid()+""));
                                    break;
                                case "BJD"://销售机会单
                                    showShortToast(data.getBilltypename());
//                                    startActivity(new Intent(mActivity, SalesOpportunitiesActivity.class)
//                                            .putExtra("billid", data.getBillid()+""));
                                    break;
                                case "XSHT"://销售合同单
                                    startActivity(new Intent(mActivity, ContractActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "XMD"://项目单
                                    startActivity(new Intent(mActivity, ProjectActivity.class)
                                            .putExtra("billid", data.getBillid()+""));
                                    break;
                                case "WLD"://物流单
                                    startActivity(new Intent(mActivity, LogisticsActivity.class)
                                            .putExtra("billid", data.getBillid() + ""));
                                    break;
                                case "WXDJ"://维修登记单
                                    startActivityForResult(new Intent(mActivity,  MaintenanceDetailsActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;
                                case "AZDJ"://安装登记单
                                    startActivityForResult(new Intent(mActivity, InstallRegistrationDetailsActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;
                                case "AZZX"://安装执行
                                    startActivityForResult(new Intent(mActivity, InstallationDetailsActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;
                                case "JCWX"://检测维修
                                    startActivityForResult(new Intent(mActivity,  DetectionDetailsActivity.class)
                                            .putExtra("billid", data.getBillid() + ""), DATA_REFERSH);
                                    break;

                                    default:
                                        showShortToast(data.getBilltypename());
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
                        startActivityForResult(new Intent(mActivity, ScreeningAuditActivity.class)
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
                mBilltypeid = data.getStringExtra("billtypeid");
                LogUtils.e("onMyActivityResult");
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
        presenter.post(1, "waitbillshlist", mParmMap);
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        if (data.toString().equals("nmyqx")) {
            showShortToast("您没有操作该功能菜单的权限");
            finish();
            return;
        }
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
        LogUtils.e("onResume");
    }
}
