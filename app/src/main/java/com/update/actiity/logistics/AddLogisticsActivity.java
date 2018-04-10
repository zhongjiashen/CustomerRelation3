package com.update.actiity.logistics;

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
import com.update.actiity.choose.LocalDataSingleOptionActivity;
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
 * Date:      2018/3/30 0030 上午 10:32
 * Description:新增物流单
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/30 0030         申中佳               V1.0
 */
public class AddLogisticsActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_logistics_type)
    TextView tvLogisticsType;
    @BindView(R.id.tv_logistics_company)
    TextView tvLogisticsCompany;
    @BindView(R.id.et_logistics_number)
    EditText etLogisticsNumber;
    @BindView(R.id.tv_document_type)
    TextView tvDocumentType;
    @BindView(R.id.tv_associated_documents)
    TextView tvAssociatedDocuments;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.et_contact_number)
    EditText etContactNumber;
    @BindView(R.id.et_shipping_address)
    EditText etShippingAddress;
    @BindView(R.id.tv_transport_way)
    TextView tvTransportWay;
    @BindView(R.id.tv_freight_for)
    TextView tvFreightFor;

    @BindView(R.id.tv_collecting)
    TextView tvCollecting;
    @BindView(R.id.tv_collecting_account)
    TextView tvCollectingAccount;

    @BindView(R.id.tv_release)
    TextView tvRelease;
    @BindView(R.id.tv_document_date)
    TextView tvDocumentDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.et_abstract)
    EditText etAbstract;
    @BindView(R.id.et_freight_amount)
    EditText etFreightAmount;
    @BindView(R.id.tv_collecting_amount)
    TextView tvCollectingAmount;
    private TimePickerView mTimePickerView;//时间选择弹窗
    Gson mGson;
    private Map<String, Object> mMap;
    private Map<String, Object> mParmMap;
    private Date mDate;


    private String mLogistictype;//物流类型 0-收货 1-发货
    private String mBilltype;//单据类型
    private String mClientid;// 单位ID
    private String mLxrid;// 联系人ID
    private String mShiptype;// 运输方式ID
    private String mBeartype;// 运费承担 0我方 1对方
    private String mIsproxy;// 是否代收 1是 0否
    private String mIsnotice;// 通知放货 1是 0 否
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
        return R.layout.activity_logistics;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        mBilltype = "1";
        tvDocumentType.setText("采购收货");//单据类型默认采购收货
        mLogistictype = "0";
        tvLogisticsType.setText("收货");//物流类型默认收货
        mBeartype = "0";
        tvFreightFor.setText("我方");//运费承担默认我方
        mIsproxy = "0";
        tvCollecting.setText("否");//否代收代付默认否
        etFreightAmount.setText("0.00");//运费金额（要求大于等于0）默认为0.00
        tvCollectingAmount.setText("0.00");//（要求大于等于0）代收代付金额默认为0.00
        mIsnotice = "0";
        tvRelease.setText("否");//通知放货默认否


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

    @OnClick({R.id.ll_logistics_type_choice, R.id.ll_logistics_company_choice, R.id.ll_document_type_choice, R.id.ll_associated_documents_choice, R.id.ll_unit_name_choice, R.id.ll_contacts_choice, R.id.ll_transport_way_choice, R.id.ll_freight_for_choice, R.id.ll_collecting_choice, R.id.ll_collecting_account_choice, R.id.ll_release_choice, R.id.ll_document_date_choice, R.id.ll_department_choice, R.id.ll_salesman_choice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_logistics_type_choice://物流类型选择
                startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class)
                        .putExtra("kind", 3), 11);
                break;
            case R.id.ll_logistics_company_choice://物流公司选择
                startActivityForResult(new Intent(this,ChooseLogisticsCompanyActivity.class)
                        .putExtra("kind", 3), 11);
                break;
            case R.id.ll_document_type_choice://单据类型选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "WLDLX").putExtra("title", "单据类型选择"), 13);
                break;
            case R.id.ll_associated_documents_choice://关联单据选择
                break;
            case R.id.ll_unit_name_choice://收货单位选择
                startActivityForResult(new Intent(this, CommonXzkhActivity.class), 15);
                break;
            case R.id.ll_contacts_choice://联系人选择
                if (TextUtils.isEmpty(mClientid))
                    showShortToast("请先选择单位");
                else
                    startActivityForResult(new Intent(this, CommonXzlxrActivity.class)
                            .putExtra("clientid", mClientid), 16);
                break;
            case R.id.ll_transport_way_choice://运输方式选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "YSFS").putExtra("title", "运输方式选择"), 17);

                break;
            case R.id.ll_freight_for_choice://运费承担方选择
                startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class)
                        .putExtra("kind", 4), 18);
                break;
//            case R.id.ll_collecting_choice://是否代收选择
//                startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class)
//                        .putExtra("kind", 2), 19);
//                break;
//            case R.id.ll_collecting_account_choice://代收账户选择
//                break;
            case R.id.ll_release_choice://通知放货
                startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class)
                        .putExtra("kind", 2), 19);
                break;
            case R.id.ll_document_date_choice://单据日期选择
                selectTime(0);
                break;
            case R.id.ll_department_choice://部门选择
                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 20);
                break;
            case R.id.ll_salesman_choice://业务员选择
                if (TextUtils.isEmpty(mDepartmentid))
                    showShortToast("请先选择部门");
                else
                    startActivityForResult(new Intent(this, SelectSalesmanActivity.class)
                            .putExtra("depid", mDepartmentid), 21);
                break;
        }
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        switch (requestCode) {
            case 11://单位选择结果处理
                mLogistictype = data.getStringExtra("CHOICE_RESULT_ID");
                tvLogisticsType.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 12://联系人选择结果处理
                mLxrid = data.getStringExtra("id");
                tvContacts.setText(data.getStringExtra("name"));
                break;
            case 13://单据类型选择结果处理
                mBilltype = data.getStringExtra("CHOICE_RESULT_ID");
                tvDocumentType.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 14://项目来源选择结果处理
//                mSourceid = data.getStringExtra("CHOICE_RESULT_ID");
//                tvProjectSource.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 15://单位选择结果处理
                mClientid = data.getStringExtra("id");
                mLxrid = data.getStringExtra("lxrid");
                tvUnitName.setText(data.getStringExtra("name"));
                tvContacts.setText(data.getStringExtra("lxrname"));
                etContactNumber.setText(data.getStringExtra("phone"));
                break;
            case 16://联系人选择结果处理
                mLxrid = data.getStringExtra("id");
                tvContacts.setText(data.getStringExtra("name"));
                break;
            case 18://运费承担方选择结果处理
                mBeartype = data.getStringExtra("CHOICE_RESULT_ID");
                tvFreightFor.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
//            case 19://是否代收选择结果处理
//                mIsproxy = data.getStringExtra("CHOICE_RESULT_ID");
//                tvCollecting.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
//                break;
            case 19://通知放货选择结果处理
                mIsnotice = data.getStringExtra("CHOICE_RESULT_ID");
                tvRelease.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 20://部门选择结果处理
                mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                tvDepartment.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 21://业务员选择结果处理
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
                    case 0://单据日期
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
    private void saveContract() {
//        物流公司、收货单位、物流单号、部门为必填
        String shipno=etLogisticsNumber.getText().toString();
        if (TextUtils.isEmpty(shipno)) {
            showShortToast("请输入物流单号！");
            return;
        }
        if (TextUtils.isEmpty(mClientid)) {
            showShortToast("请选择收货单位！");
            return;
        }
        String phone = etContactNumber.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showShortToast("请输入联系电话");
            return;
        }
        String shipto = etShippingAddress.getText().toString();
        if (TextUtils.isEmpty(shipto)) {
            showShortToast("请输入收货地址！");
            return;
        }
//
//        String amount = etContractAmount.getText().toString();
//        if (TextUtils.isEmpty(amount)) {
//            showShortToast("请输入预算金额！");
//            return;
//        }
//        mZzrq = tvEndTime.getText().toString();
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
        mMap.put("Logistictype", mLogistictype);//物流类型 0-收货 1-发货
        mMap.put("billtype", mBilltype);//单据类型 (字典ZDBM=’ WLDLX’)
        mMap.put("shipno ", shipno);//物流单号
        mMap.put("shiptype", mShiptype);//预算金额
        mMap.put("beartype", mBeartype);//运费承担 0我方 1对方
        mMap.put("isproxy", mIsproxy);//是否代收 1是 0否
        mMap.put("isnotice", mIsnotice);//通知放货 1是 0 否

        mMap.put("proxyamt", mIsnotice);//代收金额
        mMap.put("shipclientid", mClientid);// 收货单位ID
        mMap.put("lxrid", mLxrid);// 联系人ID
        mMap.put("phone", phone);// 电话
        mMap.put("shipno", shipno);//收货地址
        mMap.put("departmentid", mDepartmentid);// 部门ID
        mMap.put("empid", mEmpid);//业务员ID
        mMap.put("opid", ShareUserInfo.getUserId(this));//操作员ID
        mMap.put("memo", etAbstract.getText().toString());//摘要
        //提交数据到网络接口
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "WLD");
        mParmMap.put("master", "[" + mGson.toJson(mMap) + "]");
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
        String result = (String) data;
        if (TextUtils.isEmpty(result) || result.equals("false")) {

        } else {
            showShortToast("添加成功");
            setResult(RESULT_OK);
            finish();
        }
    }

}
