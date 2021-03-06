package com.update.actiity.contract;

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

import com.cr.tools.FigureTools;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ScreeningProjectActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.request.RqProjectListData;
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
 * Description:合同管理表
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/28 0028         申中佳               V1.0
 */
public class ContractManagementActivity extends BaseActivity implements
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
    private String mTitle;//名称（仅机会、合同、项目）
    private String mGmid;//阶段ID
    private String mEmpid;//业务员ID
    private String mShzt;//审核状态 (0未审 1已审 2 审核中   9全部)

    private List<RqProjectListData> mList;


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
        mShzt = "0";
        mGmid = "0";
        mEmpid = "0";
        mParmMap.put("opid", ShareUserInfo.getUserId(this));//登录操作员ID
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("tabname", "tb_contract");
        mParmMap.put("clientid", "0");
        mParmMap.put("depid", "0");//
        mParmMap.put("pagesize", "10");//每页加载数据大小

    }

    private void http() {
        page_number = 1;
        mParmMap.put("qsrq", mQsrq);//
        mParmMap.put("zzrq", mZzrq);//
        mParmMap.put("cname", mCname);//
        mParmMap.put("title", mTitle);//
        mParmMap.put("gmid", mGmid);//项目单阶段ID（0全部）
        mParmMap.put("shzt", mShzt);//审核状态
        mParmMap.put("empid", mEmpid);//业务员ID (没有的话传空或0)
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, ServerURL.BILLLIST, mParmMap);
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
                    mParmMap.put("billcode", v.getText().toString());//

                    http();
                    return true;
                }
                return false;
            }
        });

        prlView.setOnRefreshListener(this);
        prvView.setLayoutManager(new LinearLayoutManager(this));
        prvView.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ProjectHolder, RqProjectListData>(mList) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getProjectHolder(ContractManagementActivity.this,parent);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.ProjectHolder holder, final RqProjectListData data) {
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
                if(TextUtils.isEmpty(data.getGmmc())){
                    holder.tvPhase.setText("阶段：" );
                }else {
                    holder.tvPhase.setText("阶段：" + data.getGmmc());
                }
                holder.tvProjectName.setText(data.getTitle());//项目名称
                holder.tvMoney.setText("合同金额：￥" + FigureTools.sswrFigure(data.getAmount()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(mActivity, ContractActivity.class)
                                .putExtra("billid", data.getBillid() + "")
                                .putExtra("shzt", data.getShzt()));
                    }
                });

            }

        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "合同");
        titlebar.setIvRightTwoImageResource(R.drawable.oper);
        titlebar.setIvRightImageResource(R.mipmap.ic_add);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://增加安装登记
                        if (!ShareUserInfo.getKey(mActivity, "xz").equals("1")) {
                            showShortToast("你没有该权限，请向管理员申请权限！");
                            return;
                        }
                        startActivity(new Intent(ContractManagementActivity.this, AddContractActivity.class));
                        break;
                    case 1://打开右边侧滑菜单
                        startActivityForResult(new Intent(ContractManagementActivity.this, ScreeningProjectActivity.class)
                                .putExtra("kind", 1), 11);
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
                mTitle = data.getStringExtra("title");
                mGmid = data.getStringExtra("gmid");
                mEmpid = data.getStringExtra("empid");
                mShzt = data.getStringExtra("shzt");
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
        if (data.toString().equals("nmyqx")) {
            showShortToast("您没有操作该功能菜单的权限");
            finish();
            return;
        }
        Gson gson = new Gson();
        List<RqProjectListData> list = gson.fromJson((String) data,
                new TypeToken<List<RqProjectListData>>() {
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
        http();
    }
}
