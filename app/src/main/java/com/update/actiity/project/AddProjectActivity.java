package com.update.actiity.project;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;



/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/30 0030 下午 2:58
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/30 0030         申中佳               V1.0
 */
public class AddProjectActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_unit_type)
    TextView tvUnitType;
    @BindView(R.id.et_project_name)
    EditText etProjectName;
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
    private TimePickerView mTimePickerView;//时间选择弹窗

    Gson mGson;
    private Map<String, Object> mMap;
    private Map<String, Object> mParmMap;
    private Date mDate;

    private String mBilldate;//单据日期
    private String mClientid;// 单位ID
    private String mProjecttype;// 项目类型id
    private String mContractid;// 合同ID
    private String mGmid;//阶段ID
    private String mQsrq;//开始日期
    private String mZzrq;//截止日期
    private String mBflx;//机会来源ID
    private String mDepartmentid;//部门ID
    private String mEmpid;//业务员ID
    private String mSourceid;//项目来源id


    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mGson = new Gson();
        mMap = new ArrayMap<>();
        mParmMap = new ArrayMap<>();
        mDate = new Date();

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
        tvStartTime.setText(DateUtil.DateToString(mDate, "yyyy-MM-dd"));//起始时间默认当天
        tvDocumentDate.setText(DateUtil.DateToString(mDate, "yyyy-MM-dd"));//单据日期默认当日
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "项目");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 2:
                        saveProject();
                        break;

                }
            }
        });
    }

    @OnClick({R.id.ll_unit_name_choice, R.id.ll_related_contract_choice, R.id.ll_project_type_choice, R.id.ll_project_source_choice, R.id.ll_start_time_choice, R.id.ll_end_time_choice, R.id.ll_document_date_choice, R.id.ll_department_choice, R.id.ll_salesman_choice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_name_choice://单位选择
                startActivityForResult(new Intent(this, CommonXzkhActivity.class), 11);
                break;
            case R.id.ll_related_contract_choice://相关合同
                break;
            case R.id.ll_project_type_choice://项目类型
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "PROJXMLX").putExtra("title", "项目类型选择"), 13);
                break;
            case R.id.ll_project_source_choice://项目来源
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "PROJXMLY").putExtra("title", "项目来源选择"), 14);
                break;
            case R.id.ll_start_time_choice:
                selectTime(0);
                break;
            case R.id.ll_end_time_choice:
                selectTime(1);
                break;
            case R.id.ll_document_date_choice:
                selectTime(2);
                break;
            case R.id.ll_department_choice://部门选择
                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 15);
                break;
            case R.id.ll_salesman_choice://业务员选择
                if (TextUtils.isEmpty(mDepartmentid))
                    showShortToast("请先选择部门");
                else
                    startActivityForResult(new Intent(this, SelectSalesmanActivity.class)
                            .putExtra("depid", mDepartmentid), 16);
                break;
        }
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        switch (requestCode) {
            case 11://单位选择结果处理
                mClientid = data.getStringExtra("id");
                tvUnitName.setText(data.getStringExtra("name"));
                break;
            case 12://联系人选择结果处理

                break;
            case 13://项目类型选择结果处理
                mProjecttype = data.getStringExtra("CHOICE_RESULT_ID");
                tvProjectType.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 14://项目来源选择结果处理
                mSourceid = data.getStringExtra("CHOICE_RESULT_ID");
                tvProjectSource.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 15://部门选择结果处理
                mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                tvDepartment.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 16://业务员选择结果处理
                mEmpid = data.getStringExtra("CHOICE_RESULT_ID");
                tvSalesman.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
        }
    }

    /**
     * 时间选择器
     *
     * @param i
     */
    public void selectTime(final int i) {
        if (mTimePickerView == null) {
            mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        }
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                switch (i) {
                    case 0://起始日期
                        tvStartTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                    case 1://截止日期
                        tvEndTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                    case 2://单据日期
                        tvDocumentDate.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                }

            }
        });

        mTimePickerView.setTime(mDate);
        mTimePickerView.show();
    }

    /**
     * 新增项目
     */
    private void saveProject() {
        if (TextUtils.isEmpty(mClientid)) {
            showShortToast("请选择单位名称！");
            return;
        }
        String title = etProjectName.getText().toString();
        if (TextUtils.isEmpty(title)) {
            showShortToast("请输入项目名称！");
            return;
        }
        if (TextUtils.isEmpty(mProjecttype)) {
            showShortToast("请选择项目类型！");
            return;
        }
        if (TextUtils.isEmpty(mSourceid)) {
            showShortToast("请选择项目来源！");
            return;
        }
        String amount = etBudgetAmount.getText().toString();
        if (TextUtils.isEmpty(amount)) {
            showShortToast("请输入预算金额！");
            return;
        }
        mZzrq = tvEndTime.getText().toString();
        if (TextUtils.isEmpty(mZzrq)) {
            showShortToast("请选择截止日期！");
            return;
        }
        if (TextUtils.isEmpty(mDepartmentid)) {
            showShortToast("请先选择部门");
            return;
        }
        if (TextUtils.isEmpty(mEmpid)) {
            showShortToast("请先选择业务员");
            return;
        }
        mMap.put("billid", "0");//主键ID;0或空表示新增
        mMap.put("billdate", tvDocumentDate.getText().toString()
        );//单据日期
        mMap.put("clientid", mClientid);// 单位ID
        mMap.put("title", title);//  项目名称
        mMap.put("projecttype", mProjecttype);//项目类型id
        mMap.put("sourceid", mSourceid);//项目类型id
        mMap.put("contractid", mContractid);// 合同ID
        mMap.put("amount", amount);//预算金额
        mMap.put("gmid", "0");//阶段ID
        mMap.put("qsrq", tvStartTime.getText().toString());//开始日期
        mMap.put("zzrq", mZzrq);//截止日期
//        mMap.put("bflx", mBflx);// 机会来源ID
        mMap.put("departmentid", mDepartmentid);// 部门ID
        mMap.put("empid", mEmpid);//业务员ID
        mMap.put("objective", etProjectPurpose.getText().toString());//项目目的
        mMap.put("opid", ShareUserInfo.getUserId(this));//操作员ID

        //提交数据到网络接口
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "XMD");
        mParmMap.put("master","["+ mGson.toJson(mMap)+"]");
        presenter.post(0, "billsave", mParmMap);
    }

}
