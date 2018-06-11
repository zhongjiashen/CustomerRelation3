package com.update.actiity.contract;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.actiity.project.ChoiceProjectActivity;
import com.update.actiity.sales.ChoiceOpportunitiesActivity;
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
 * Date:      2018/3/30 0030 上午 10:32
 * Description:新增合同
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/30 0030         申中佳               V1.0
 */
public class AddContractActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.et_contact_number)
    TextView etContactNumber;
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


    private TimePickerView mTimePickerView;//时间选择弹窗
    Gson mGson;
    private Map<String, Object> mMap;
    private Map<String, Object> mParmMap;
    private Date mDate;

    private String mClientid;// 单位ID
    private String mLxrid;// 联系人ID
    private String mZzrq;//截止日期
    private String mChanceid;// 机会ID
    private String mProjectid;//  项目ID
    private String mDepartmentid;//部门ID
    private String mEmpid;//业务员ID

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
        return R.layout.activity_contract;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        mDepartmentid=ShareUserInfo.getKey(this, "departmentid");
        tvDepartment.setText(ShareUserInfo.getKey(this, "depname"));
        mEmpid=ShareUserInfo.getKey(this, "empid");
        tvSalesman.setText(ShareUserInfo.getKey(this, "opname"));


        tvStartTime.setText(DateUtil.DateToString(mDate, "yyyy-MM-dd"));//起始时间默认当天
        tvDocumentDate.setText(DateUtil.DateToString(mDate, "yyyy-MM-dd"));//单据日期默认当日
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "合同");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 2:
                        saveContract();
                        break;

                }
            }
        });
    }

    @OnClick({R.id.ll_unit_name_choice, R.id.ll_contacts_choice, R.id.ll_start_time_choice, R.id.ll_end_time_choice, R.id.ll_opportunit_name_choice, R.id.ll_related_projects_choice, R.id.ll_document_date_choice, R.id.ll_department_choice, R.id.ll_salesman_choice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_name_choice://单位选择
                startActivityForResult(new Intent(this, CommonXzdwActivity.class).putExtra("type", "0"), 11);
//                startActivityForResult(new Intent(this, CommonXzkhActivity.class), 11);
                break;
            case R.id.ll_contacts_choice://联系人选择
                if (TextUtils.isEmpty(mClientid))
                    showShortToast("请先选择单位");
                else
                    startActivityForResult(new Intent(this, CommonXzlxrActivity.class).putExtra("clientid", mClientid), 12);
                break;
            case R.id.ll_start_time_choice:
                selectTime(0);
                break;
            case R.id.ll_end_time_choice:
                selectTime(1);
                break;
            case R.id.ll_opportunit_name_choice://机会名称选择
                if (TextUtils.isEmpty(mClientid))
                    showShortToast("请先选择单位");
                else
                startActivityForResult(new Intent(this, ChoiceOpportunitiesActivity.class).putExtra("clientid", mClientid), 13);
                break;
            case R.id.ll_related_projects_choice:
                if (TextUtils.isEmpty(mClientid))
                    showShortToast("请先选择单位");
                else
                    startActivityForResult(new Intent(this, ChoiceProjectActivity.class).putExtra("clientid", mClientid), 14);
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
                mLxrid = data.getStringExtra("lxrid");
                tvUnitName.setText(data.getStringExtra("name"));
                tvContacts.setText(data.getStringExtra("lxrname"));
                etContactNumber.setText(data.getStringExtra("phone"));
                tvUnitType.setText(data.getStringExtra("typesname"));
                break;
            case 12://联系人选择结果处理
                mLxrid = data.getStringExtra("id");
                tvContacts.setText(data.getStringExtra("name"));
                break;
            case 13://项目类型选择结果处理
                mChanceid = data.getStringExtra("chanceid");
                tvOpportunitName.setText(data.getStringExtra("title"));
                break;
            case 14://相关项目选择结果处理
                mProjectid = data.getStringExtra("projectid");
                tvRelatedProjects.setText(data.getStringExtra("title"));
                break;
            case 15://部门选择结果处理
                mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                tvDepartment.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mEmpid = "";
                tvSalesman.setText("");
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
     * 新增合同
     */
    private void saveContract() {
//        41.新增合同单据时，合同金额、截止日期不是必须填写的，目前是不填写保存不了
        if (TextUtils.isEmpty(mClientid)) {
            showShortToast("请选择单位名称！");
            return;
        }
        String phone = etContactNumber.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showShortToast("请输入联系电话");
            return;
        }
        String title = etContractName.getText().toString();
        if (TextUtils.isEmpty(title)) {
            showShortToast("请输入合同名称！");
            return;
        }

        String amount = etContractAmount.getText().toString();
//        if (TextUtils.isEmpty(amount)) {
//            showShortToast("请输入预算金额！");
//            return;
//        }
        mZzrq = tvEndTime.getText().toString();
//        if (TextUtils.isEmpty(mZzrq)) {
//            showShortToast("请选择截止日期！");
//            return;
//        }
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
        mMap.put("lxrid", mLxrid);// 联系人ID
        mMap.put("phone", phone);// 单位ID
        mMap.put("title", title);//合同名称
        mMap.put("amount", amount);//预算金额
        mMap.put("gmid", "0");//阶段ID
        mMap.put("qsrq", tvStartTime.getText().toString());//开始日期
        mMap.put("zzrq", mZzrq);//截止日期
        mMap.put("chanceid", mChanceid);// 机会ID
        mMap.put("projectid", mProjectid);// 项目ID
        mMap.put("departmentid", mDepartmentid);// 部门ID
        mMap.put("empid", mEmpid);//业务员ID
        mMap.put("opid", ShareUserInfo.getUserId(this));//操作员ID
        mMap.put("memo", etAbstract.getText().toString());//摘要
        //提交数据到网络接口
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "XSHT");
        mParmMap.put("master","["+ mGson.toJson(mMap)+"]");
        presenter.post(0, "billsave", mParmMap);
    }
    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        String result= (String) data;
        if(TextUtils.isEmpty(result)||result.equals("false")){

        }else {
            showShortToast("添加成功");
            setResult(RESULT_OK);
            finish();
        }
    }
}
