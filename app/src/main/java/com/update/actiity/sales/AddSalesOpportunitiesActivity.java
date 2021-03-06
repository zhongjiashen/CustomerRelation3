package com.update.actiity.sales;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
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
 * Description:新增销售机会
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/30 0030         申中佳               V1.0
 */
public class AddSalesOpportunitiesActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titlebar;
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
    private TimePickerView mTimePickerView;//时间选择弹窗

    Gson mGson;
    private Map<String, Object> mMap;
    private Map<String, Object> mParmMap;
    private Date mDate;

    private String mClientid;// 单位ID
    private String mClientname;// 单位名称
    private String mLxrid;// 联系人ID
    private String mLxrname;//联系人姓名
    private String mPhone;// 联系電話
    private String mZzrq;//截止日期
    private String mBflx;//机会来源ID
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
        return R.layout.activity_sales_opportunities;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        mClientid = getIntent().getStringExtra("clientid");
        mClientname = getIntent().getStringExtra("clientname");
        mLxrid = getIntent().getStringExtra("lxrid");
        mLxrname = getIntent().getStringExtra("lxrname");
        mPhone = getIntent().getStringExtra("phone");
        mDepartmentid = ShareUserInfo.getKey(this, "departmentid");
        tvDepartment.setText(ShareUserInfo.getKey(this, "depname"));
        mEmpid = ShareUserInfo.getKey(this, "empid");
        tvSalesman.setText(ShareUserInfo.getKey(this, "opname"));
        tvStartTime.setText(DateUtil.DateToString(mDate, "yyyy-MM-dd"));//起始时间默认当天
        tvDocumentDate.setText(DateUtil.DateToString(mDate, "yyyy-MM-dd"));//单据日期默认当日

        tvUnitName.setText(mClientname);
        tvContacts.setText(mLxrname);
        etContactNumber.setText(mPhone);
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "销售机会");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 2:
                        titlebar.setTvRightEnabled(false);
                        saveOpportunities();
                        break;

                }
            }
        });
    }

    @OnClick({R.id.ll_unit_name_choice, R.id.ll_contacts_choice, R.id.ll_start_time_choice, R.id.ll_end_time_choice, R.id.ll_opportunities_source_choice, R.id.ll_document_date_choice, R.id.ll_department_choice, R.id.ll_salesman_choice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_name_choice://单位选择
                startActivityForResult(new Intent(this, CommonXzkhActivity.class), 11);
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
            case R.id.ll_opportunities_source_choice:
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "BFLX").putExtra("title", "机会来源选择"), 13);
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

                break;
            case 12://联系人选择结果处理
                mLxrid = data.getStringExtra("id");
                tvContacts.setText(data.getStringExtra("name"));
                break;
            case 13://机会来源选择结果处理
                mBflx = data.getStringExtra("CHOICE_RESULT_ID");
                tvOpportunitiesSource.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
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
     * 新增销售机会
     */
    private void saveOpportunities() {
//        销售机会单据除了单位名称、机会名称、部门为必填，其他的都不是必填项，目前是不填写不能保存
        if (TextUtils.isEmpty(mClientid)) {
            showShortToast("请选择单位名称！");
            titlebar.setTvRightEnabled(true);
            return;
        }
        String phone = etContactNumber.getText().toString();
//        if (TextUtils.isEmpty(phone)) {
//            showShortToast("请输入联系电话");
//            return;
//        }
        String title = etOpportunitiesName.getText().toString();
        if (TextUtils.isEmpty(title)) {
            showShortToast("请输入机会名称！");
            titlebar.setTvRightEnabled(true);
            return;
        }

        String amount = etExpectedIncome.getText().toString();
//        if (TextUtils.isEmpty(amount)) {
//            showShortToast("请输入预计收入！");
//            return;
//        }
        mZzrq = tvEndTime.getText().toString();
//        if (TextUtils.isEmpty(mZzrq)) {
//            showShortToast("请选择成交日期！");
//            return;
//        }
//        if (TextUtils.isEmpty(mBflx)) {
//            showShortToast("请先选机会来源");
//            return;
//        }
        String sources = etInterestSize.getText().toString();
//        if (TextUtils.isEmpty(sources)) {
//            showShortToast("请输入兴趣大小！");
//            return;
//        }
        String probability = etChance.getText().toString();
//        if (TextUtils.isEmpty(probability)) {
//            showShortToast("请输入成功机率！");
//            return;
//        }
        if (TextUtils.isEmpty(mDepartmentid)) {
            showShortToast("请先选择部门");
            titlebar.setTvRightEnabled(true);
            return;
        }
//        if (TextUtils.isEmpty(mEmpid)) {
//            showShortToast("请先选择业务员");
//            return;
//        }
        mMap.put("billid", "0");//主键ID;0或空表示新增
        mMap.put("billdate", tvDocumentDate.getText().toString()
        );//单据日期
        mMap.put("clientid", mClientid);// 单位ID
        mMap.put("lxrid", mLxrid);//联系人ID
        mMap.put("phone", phone);// 单位ID
        mMap.put("title", title);//机会名称
        mMap.put("amount", amount);//预算金额
        mMap.put("gmid", "0");//阶段ID
        mMap.put("qsrq", tvStartTime.getText().toString());//开始日期
        mMap.put("preselldate", mZzrq);//预计成交日期
        mMap.put("bflx", mBflx);// 机会来源ID
        mMap.put("sources", sources);// 兴趣大小
        mMap.put("probability", probability);// 成功机率（数字型）
        mMap.put("departmentid", mDepartmentid);// 部门ID
        mMap.put("empid", mEmpid);//业务员ID
        mMap.put("memo", etAbstract.getText().toString());//备注
        mMap.put("opid", ShareUserInfo.getUserId(this));//操作员ID

        //提交数据到网络接口
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "XSJH");
        mParmMap.put("master", "[" + mGson.toJson(mMap) + "]");
        presenter.post(0, "billsave", mParmMap);
    }

    @Override
    public void httpfaile(int requestCode) {
        switch (requestCode) {
            case 0:
                titlebar.setTvRightEnabled(true);
                break;
        }
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
        String result = (String) data;
        if (TextUtils.isEmpty(result) || result.equals("false")) {
            showShortToast("添加失败");
            titlebar.setTvRightEnabled(true);
        } else {
            showShortToast("添加成功");
            Intent intent=new Intent();
            intent.putExtra("name",mMap.get("title").toString());
            intent.putExtra("id",data.toString());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
