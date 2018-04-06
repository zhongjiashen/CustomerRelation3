package com.update.actiity.contract;

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
import com.update.model.request.RqContractData;
import com.update.viewbar.TitleBar;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/4/2 0002 下午 5:37
 * Description:合同详情
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/4/2 0002         申中佳               V1.0
 */
public class ContractActivity extends BaseActivity {


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
    @BindView(R.id.tv_unit_type)
    TextView tvUnitType;
    @BindView(R.id.et_contract_name)
    EditText etContractName;
    @BindView(R.id.et_contract_amount)
    EditText etContractAmount;
    @BindView(R.id.tv_current_stage)
    TextView tvCurrentStage;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_opportunit_name)
    TextView tvOpportunitName;
    @BindView(R.id.tv_related_projects)
    TextView tvRelatedProjects;
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
        mParmMap.put("parms", "XSHT");
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
        return R.layout.activity_contract;
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
        titlebar.setTitleText(this, "合同");

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
               List<RqContractData> rqContractDatas = mGson.fromJson((String) data,
                        new TypeToken<List<RqContractData>>() {
                        }.getType());
                setData(rqContractDatas.get(0));
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
    private void setData(RqContractData data) {
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
        tvUnitType.setText("渠道");
        e
        etProjectName.setText(data.getTitle());
        tvCurrentStage.setText(data.getGmmc());
        tvProjectType.setText(data.getXmlxname());
        tvProjectSource.setText(data.getXmlyname());
        etBudgetAmount.setText(data.getAmount()+"");
        tvStartTime.setText(data.getQsrq());
        tvEndTime.setText(data.getZzrq());
        tvDocumentDate.setText(data.getBilldate());
        tvDepartment.setText(data.getDepname());
        tvSalesman.setText(data.getEmpname());
        etAbstract.setText(data.getMemo());


    }
}
