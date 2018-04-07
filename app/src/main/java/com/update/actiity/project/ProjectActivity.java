package com.update.actiity.project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.contract.ContractActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.model.request.RqProjectData;
import com.update.viewbar.TitleBar;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/4/2 0002 下午 5:37
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/4/2 0002         申中佳               V1.0
 */
public class ProjectActivity extends BaseActivity {
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
    @BindView(R.id.tv_unit_type)
    TextView tvUnitType;
    @BindView(R.id.et_project_name)
    EditText etProjectName;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_current_stage)
    TextView tvCurrentStage;
    @BindView(R.id.tv_related_contract)
    TextView tvRelatedContract;
    @BindView(R.id.tv_project_type)
    TextView tvProjectType;
    @BindView(R.id.tv_project_source)
    TextView tvProjectSource;
    @BindView(R.id.et_budget_amount)
    EditText etBudgetAmount;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_document_date)
    TextView tvDocumentDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.et_project_purpose)
    EditText etProjectPurpose;
    @BindView(R.id.ll_related_contract_choice)
    LinearLayout llRelatedContractChoice;


    private Gson mGson;
    private Map<String, Object> mParmMap;
    private String mBillid;//项目ID
    private int mShzt;//审核状态


    RqProjectData mData;

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
        mParmMap.put("parms", "XMD");
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
        return R.layout.activity_project;
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
        titlebar.setTitleText(this, "项目");

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
                List<RqProjectData> rqProjectData = mGson.fromJson((String) data,
                        new TypeToken<List<RqProjectData>>() {
                        }.getType());
                setData(rqProjectData.get(0));
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
    private void setData(RqProjectData data) {
        mData = data;
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
        tvUnitType.setText(data.getTypesname());
        etProjectName.setText(data.getTitle());
        tvCurrentStage.setText(data.getGmmc());
        tvRelatedContract.setText(data.getContractname());
        tvProjectType.setText(data.getXmlxname());
        tvProjectSource.setText(data.getXmlyname());
        etBudgetAmount.setText(data.getAmount() + "");
        tvStartTime.setText(data.getQsrq());
        tvEndTime.setText(data.getZzrq());
        tvDocumentDate.setText(data.getBilldate());
        tvDepartment.setText(data.getDepname());
        tvSalesman.setText(data.getEmpname());
        etProjectPurpose.setText(data.getObjective());


    }

    @OnClick(R.id.ll_related_contract_choice)
    public void onClick() {
        if(mData==null)
            return;
        if(!TextUtils.isEmpty(mData.getContractname()))
            startActivity(new Intent(this, ContractActivity.class)
                    .putExtra("billid", mData.getContractid()+""));
    }
}
