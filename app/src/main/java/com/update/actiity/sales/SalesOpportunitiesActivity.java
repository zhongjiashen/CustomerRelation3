package com.update.actiity.sales;

import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.model.request.RqSalesOpportunitiesData;
import com.update.viewbar.TitleBar;

import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/30 0030 下午 2:58
 * Description:新增销售机会
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/30 0030         申中佳               V1.0
 */
public class SalesOpportunitiesActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_receipt_number)
    TextView tvReceiptNumber;
    @BindView(R.id.tv_audit_status)
    TextView tvAuditStatus;
    @BindView(R.id.ll_receipt_number)
    LinearLayout llReceiptNumber;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.et_contact_number)
    EditText etContactNumber;
    @BindView(R.id.et_opportunities_name)
    EditText etOpportunitiesName;
    @BindView(R.id.et_expected_income)
    EditText etExpectedIncome;
    @BindView(R.id.tv_current_stage)
    TextView tvCurrentStage;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_opportunities_source)
    TextView tvOpportunitiesSource;
    @BindView(R.id.et_interest_size)
    EditText etInterestSize;
    @BindView(R.id.et_chance)
    EditText etChance;
    @BindView(R.id.tv_document_date)
    TextView tvDocumentDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.et_abstract)
    EditText etAbstract;
    private Gson mGson;
    private Map<String, Object> mParmMap;
    private String mBillid;//项目ID
    private int mShzt;//审核状态

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mGson = new Gson();
        mParmMap = new ArrayMap<>();
        mBillid = getIntent().getStringExtra("billid");
        mShzt = getIntent().getIntExtra("shzt", 0);
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "XSJH");
        mParmMap.put("billid", mBillid);
        presenter.post(0, ServerURL.BILLMASTER, mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_sales_opportunities;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        llReceiptNumber.setVisibility(View.VISIBLE);
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "销售机会");

    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {

        switch (requestCode) {
            case 0://第一次加载数据或者刷新数据
                List<RqSalesOpportunitiesData> rqSalesOpportunitiesDatas = mGson.fromJson((String) data,
                        new TypeToken<List<RqSalesOpportunitiesData>>() {
                        }.getType());
                setData(rqSalesOpportunitiesDatas.get(0));
                break;
            case 1:

                break;
        }
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private void setData(RqSalesOpportunitiesData data) {
        tvReceiptNumber.setText("单据编号:" + data.getCode());//单据编号设置
        switch (mShzt) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
            case 0://未审
                tvAuditStatus.setText("未审核");
                tvAuditStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                break;
            case 1://已审
                tvAuditStatus.setText("已审核");
                tvAuditStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                break;
            case 2://审核中
                tvAuditStatus.setText("审核中");
                tvAuditStatus.setBackgroundColor(Color.parseColor("#00CC00"));
                break;
        }
        tvUnitName.setText(data.getCname());
        tvContacts.setText(data.getLxrname());//联系人
        etContactNumber.setText(data.getPhone());//联系电话
        etOpportunitiesName.setText(data.getTitle());//机会名称
        etExpectedIncome.setText(data.getAmount()+"");//预计收入
        tvCurrentStage.setText(data.getGmmc());//当前阶段
        tvStartTime.setText(data.getQsrq());//开始时间
        tvEndTime.setText(data.getPreselldate());//预计成交日期
        tvOpportunitiesSource.setText(data.getBflxname());
        etInterestSize.setText(data.getSources());
        etChance.setText(data.getProbability()+"");
        tvDocumentDate.setText(data.getBilldate());//单据日期
        tvDepartment.setText(data.getDepname());
        tvSalesman.setText(data.getEmpname());//业务员
        etAbstract.setText(data.getMemo());//摘要


    }
}
