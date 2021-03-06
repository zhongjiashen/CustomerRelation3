package com.update.actiity.logistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshActivity;
import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshDetailActivity;
import com.cr.activity.jxc.cggl.cgth.JxcCgglCgthActivity;
import com.cr.activity.jxc.ckgl.kcbd.JxcCkglKcbdActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdActivity;
import com.cr.activity.jxc.xsgl.xsth.JxcXsglXsthActivity;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.LocalDataSingleOptionActivity;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.model.DataDictionaryData;
import com.update.model.request.WldlxtTabData;
import com.update.utils.DateUtil;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.tv_dszhbt)
    TextView tvDszhbt;
    @BindView(R.id.tv_dsjebt)
    TextView tvDsjebt;
    private TimePickerView mTimePickerView;//时间选择弹窗
    Gson mGson;
    private Map<String, Object> mMap;
    private Map<String, Object> mParmMap;
    private Date mDate;


    private String mLogistictype;//物流类型 0-收货 1-发货
    private String mBilltype;//单据类型
    private String mReferbillid;//引用单ID
    private String mRefertype;//引用类型
    private String mClientid;// 物流公司ID
    private String mShipclientid;// 收货单位ID
    private String mLxrid;// 联系人ID
    private String mShiptype;// 运输方式ID
    private String mBeartype;// 运费承担 0我方 1对方
    private String mIsproxy;// 是否代收 1是 0否
    private String mProxybankid;//代收账户ID
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
        mDepartmentid = ShareUserInfo.getKey(this, "departmentid");
        tvDepartment.setText(ShareUserInfo.getKey(this, "depname"));
        mEmpid = ShareUserInfo.getKey(this, "empid");
        tvSalesman.setText(ShareUserInfo.getKey(this, "opname"));


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
        titlebar.setTitleText(this, "物流");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 2:
                        titlebar.setTvRightEnabled(false);
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
                startActivityForResult(new Intent(this, CommonXzdwActivity.class)
                        .putExtra("type", "6"), 12);
                break;
            case R.id.ll_document_type_choice://单据类型选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "WLDLX").putExtra("title", "单据类型选择"), 13);
                break;
            case R.id.ll_associated_documents_choice://关联单据选择
//                单据类型选择了其他，则选择关联单据项不可点击，收货单位可以点击选择
                if (mBilltype.equals("9")) {
                    showShortToast("单据类型选择了其他，则选择关联单据项不可点击");
                } else {

                    Map map = new ArrayMap();
                    map.put("dbname", ShareUserInfo.getDbName(this));
                    map.put("zdbm", "WLDLXTAB");
                    presenter.post(1, ServerURL.DATADICT, map);

                }
                break;
            case R.id.ll_unit_name_choice://收货单位选择
//                单据类型选择了其他以外的单据类型，则选择关联单据项可点击选择，收货单位不可以点击
                if (!mBilltype.equals("9")) {
//                    showShortToast("单据类型未选择了其他，则收货单位不可以点击");
                } else {
                    startActivityForResult(new Intent(this, CommonXzkhActivity.class), 15);
                }
                break;
            case R.id.ll_contacts_choice://联系人选择
                if (TextUtils.isEmpty(mShipclientid))
                    showShortToast("请先选择单位");
                else
                    startActivityForResult(new Intent(this, CommonXzlxrActivity.class)
                            .putExtra("clientid", mShipclientid), 16);
                break;
            case R.id.ll_transport_way_choice://运输方式选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                                .putExtra("zdbm", "YSFS")
                                .putExtra("title", "运输方式选择"),
                        17);

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
            case 11://物流类型选择结果处理
                mLogistictype = data.getStringExtra("CHOICE_RESULT_ID");
                tvLogisticsType.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 12://物流公司选择结果处理
                mClientid = data.getStringExtra("id");
                tvLogisticsCompany.setText(data.getStringExtra("name"));
                break;
            case 13://单据类型选择结果处理
                //如果切换了单据类型，选择关联单据、是否代收代付、代收代付账户、代收代付金额、收货单位、联系人、联系电话信息置空；
                mBilltype = data.getStringExtra("CHOICE_RESULT_ID");
                tvDocumentType.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mReferbillid = "";
                tvAssociatedDocuments.setText("");
                mIsproxy = "0";
                tvCollecting.setText("否");//否代收代付默认否
                mProxybankid = "";
                tvCollectingAccount.setText("");
                tvCollectingAmount.setText("0.00");//（要求大于等于0）代收代付金额默认为0.00
                mShipclientid = "";
                mLxrid = "";
                tvUnitName.setText("");
                tvContacts.setText("");
                etContactNumber.setText("");
                etShippingAddress.setText("");
                if (tvDocumentType.getText().toString().equals("其他")) {
                    LogUtils.e("asdfsa");
                    etFreightAmount.setFocusable(false);
                    etFreightAmount.setFocusableInTouchMode(false);
                    etFreightAmount.setClickable(false);
                } else {
                    etFreightAmount.setFocusable(true);
                    etFreightAmount.setFocusableInTouchMode(true);
                    etFreightAmount.setClickable(true);
                }
                if (tvDocumentType.getText().toString().equals("采购收货") || tvDocumentType.getText().toString().equals("资产购置")) {
                    tvDszhbt.setText("代付账号");
                    tvDsjebt.setText("代付金额");
                } else {
                    tvDszhbt.setText("代收账户");
                    tvDsjebt.setText("代收金额");
                }

                break;
            case 14://关联单据
                //是否代收代付、代收代付账户、代收代付金额是根据所选择的关联单据带过来的信息
                mReferbillid = data.getStringExtra("referbillid");
                mRefertype= data.getStringExtra("refertype");
                tvAssociatedDocuments.setText(data.getStringExtra("code"));
                mIsproxy = data.getStringExtra("isproxy");
                switch (mIsproxy) {
                    case "0":
                        tvCollecting.setText("否");//否代收代付默认否
                        break;
                    case "1":
                        tvCollecting.setText("是");//否代收代付默认否
                        break;
                }
                mShipclientid = data.getStringExtra("shipclientid");
                LogUtils.e(mShipclientid);
                tvUnitName.setText(data.getStringExtra("shipcname"));
                mLxrid = data.getStringExtra("lxrid");
                tvContacts.setText(data.getStringExtra("lxrname"));
                etContactNumber.setText(data.getStringExtra("phone"));
                etShippingAddress.setText(data.getStringExtra("shipto"));

                //代收代付账户
                mProxybankid = data.getStringExtra("bankid");
                tvCollectingAccount.setText(data.getStringExtra("bankname"));
                tvCollectingAmount.setText(data.getStringExtra("proxyamt"));//（要求大于等于0）代收代付金额默认为0.00
                break;
            case 15://收货单位选择结果处理
                mShipclientid = data.getStringExtra("id");
                mLxrid = data.getStringExtra("lxrid");
                tvUnitName.setText(data.getStringExtra("name"));
                tvContacts.setText(data.getStringExtra("lxrname"));
                etContactNumber.setText(data.getStringExtra("phone"));
                etShippingAddress.setText(data.getStringExtra("shipto"));
                break;
            case 16://联系人选择结果处理
                mLxrid = data.getStringExtra("id");
                tvContacts.setText(data.getStringExtra("name"));
                break;
            case 17://运费承担方选择结果处理
                mShiptype = data.getStringExtra("CHOICE_RESULT_ID");
                tvTransportWay.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
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
                mEmpid = "";
                tvSalesman.setText("");
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
     * 新增物流
     */
    private void saveContract() {
//        物流公司、收货单位、物流单号、部门为必填
        if (TextUtils.isEmpty(mClientid)) {
            showShortToast("请选择物流公司！");
            titlebar.setTvRightEnabled(true);
            return;
        }
        String shipno = etLogisticsNumber.getText().toString();
        if (TextUtils.isEmpty(shipno)) {
            showShortToast("请输入物流单号！");
            titlebar.setTvRightEnabled(true);
            return;
        }
//        if (TextUtils.isEmpty(mShipclientid)) {
//            showShortToast("请选择收货单位！");
//            return;
//        }
        String phone = etContactNumber.getText().toString();
//        if (TextUtils.isEmpty(phone)) {
//            showShortToast("请输入联系电话");
//            titlebar.setTvRightEnabled(true);
//            return;
//        }
        String shipto = etShippingAddress.getText().toString();
//        if (TextUtils.isEmpty(shipto)) {
//            showShortToast("请输入收货地址！");
//            titlebar.setTvRightEnabled(true);
//            return;
//        }
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
            titlebar.setTvRightEnabled(true);
            return;
        }
//        if (TextUtils.isEmpty(mEmpid)) {
//            showShortToast("请先选择业务员");
//            return;
//        }
        mMap.put("billid", "0");//主键ID;0或空表示新增
        mMap.put("clientid", mClientid);//物流公司ID
        mMap.put("billdate", tvDocumentDate.getText().toString()
        );//单据日期
        mMap.put("Logistictype", mLogistictype);//物流类型 0-收货 1-发货
        mMap.put("billtype", mBilltype);//单据类型 (字典ZDBM=’ WLDLX’)
        mMap.put("shipno ", shipno);//物流单号
        mMap.put("shiptype", mShiptype);//运输方式ID
        mMap.put("beartype", mBeartype);//运费承担 0我方 1对方
        mMap.put("isproxy", mIsproxy);//是否代收 1是 0否
        mMap.put("isnotice", mIsnotice);//通知放货 1是 0 否
        mMap.put("proxybankid", mProxybankid);//代收账户ID
        mMap.put("proxyamt", tvCollectingAmount.getText().toString());//代收金额
        mMap.put("shipclientid", mShipclientid);// 收货单位ID
        LogUtils.e(mShipclientid);
        mMap.put("lxrid", mLxrid);// 联系人ID
        mMap.put("phone", phone);// 电话
        mMap.put("shipto", shipto);//收货地址
        mMap.put("departmentid", mDepartmentid);// 部门ID
        mMap.put("empid", mEmpid);//业务员ID
        mMap.put("opid", ShareUserInfo.getUserId(this));//操作员ID
        mMap.put("memo", etAbstract.getText().toString());//摘要
        mMap.put("referbillid", mReferbillid);//摘要
        mMap.put("refertype", mRefertype);//摘要
        //提交数据到网络接口
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "WLD");
        mParmMap.put("master", "[" + mGson.toJson(mMap) + "]");
        presenter.post(0, "billsave", mParmMap);
    }
    @Override
    public void httpfaile(int requestCode) {
        switch (requestCode){
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
        switch (requestCode) {
            case 0:
                String result = (String) data;
                if (TextUtils.isEmpty(result) || result.equals("false")) {
                    showShortToast("添加失败");
                    titlebar.setTvRightEnabled(true);
                } else {
                    showShortToast("添加成功");
                    setResult(RESULT_OK);
                    finish();
                }
                break;
            case 1:
                LogUtils.e(data.toString());
                startActivityForResult(new Intent(this, ChoiceLogisticsActivity.class)
                                .putExtra("billtype", mBilltype)
                                .putExtra("data", data.toString())
                        , 14);
                break;
        }

    }

}
