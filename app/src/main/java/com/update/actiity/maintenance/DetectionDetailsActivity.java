package com.update.actiity.maintenance;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.EnterSerialNumberActivity;
import com.update.actiity.SerialNumberDetailsActivity;
import com.update.actiity.choose.LocalDataSingleOptionActivity;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.actiity.installation.ChooseGoodsDetailsActivity;
import com.update.actiity.installation.IncreaseOverviewActivity;
import com.update.adapter.FileChooseAdapter;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.dialog.DialogFactory;
import com.update.dialog.OnDialogClickInterface;
import com.update.model.DetectionDetailsData;
import com.update.model.FileChooseData;
import com.update.model.Serial;
import com.update.model.request.PerformSituationData;
import com.update.utils.DateUtil;
import com.update.utils.FileUtils;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/21 0021 下午 2:55
 * Description:检测维修列表详情页
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/21 0021         申中佳               V1.0
 */
public class DetectionDetailsActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_audit_status)
    TextView tvAuditStatus;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_contact_number)
    TextView tvContactNumber;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_submit_time)
    TextView tvSubmitTime;
    @BindView(R.id.tv_technical_personnel)
    TextView tvTechnicalPersonnel;
    @BindView(R.id.tv_dispatch_documents)
    TextView tvDispatchDocuments;
    @BindView(R.id.tv_dispatch_date)
    TextView tvDispatchDate;
    @BindView(R.id.tv_execution_status)
    TextView tvExecutionStatus;
    @BindView(R.id.tv_goods_information)
    TextView tvGoodsInformation;
    @BindView(R.id.tv_registration_number)
    TextView tvRegistrationNumber;
    @BindView(R.id.tv_maintenance_results)
    TextView tvMaintenanceResults;
    @BindView(R.id.tv_rework)
    TextView tvRework;
    @BindView(R.id.et_installation_number)
    EditText etInstallationNumber;
    @BindView(R.id.et_unloaded)
    EditText etUnloaded;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_maintenance_measures)
    EditText etMaintenanceMeasures;
    @BindView(R.id.rcv_choose_file_list)
    RecyclerView rcvChooseFileList;
    @BindView(R.id.et_scrap_number)
    EditText etScrapNumber;
    @BindView(R.id.et_fault)
    EditText etFault;
    private List<FileChooseData> mFileChooseDatas;
    private Map<String, Object> mParmMap;
    private static final int FILE_SELECT_CODE = 66;
    private String billid;//单据ID
    private String itemno;//明细ID
    Gson mGson;
    private TimePickerView mTimePickerView;//时间选择弹窗
    DetectionDetailsData mData;
    private FileChooseAdapter mFileChooseAdapter;

    PerformSituationData mDetail;
    List<Serial> serialList;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        serialList = new ArrayList<>();
        mDetail = new PerformSituationData();
        mGson = new Gson();
        billid = getIntent().getStringExtra("billid");
        itemno = getIntent().getStringExtra("itemno");
        presenter = new BaseP(this, this);
        mParmMap = new ArrayMap<>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "JCWX");
        mParmMap.put("billid", itemno);
        presenter.post(0, ServerURL.BILLDETAIL, mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_detection_details;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
 /* 选择文件集合信息处理 */
        rcvChooseFileList.setLayoutManager(new GridLayoutManager(this, 4));
        rcvChooseFileList.setAdapter(mFileChooseAdapter = new FileChooseAdapter(this) {
            @Override
            public void checkFile() {
                DialogFactory.getFileChooseDialog(DetectionDetailsActivity.this, new OnDialogClickInterface() {
                    @Override
                    public void OnClick(int requestCode, Object object) {
                        switch (requestCode) {
                            case 0:
                                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                                Uri imageUri = Uri.fromFile(file);
                                takePhoto.onPickFromCapture(imageUri);
                                break;
                            case 1:
                                takePhoto.onPickFromGallery();
                                break;
                            case 2:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                try {
                                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
                                } catch (ActivityNotFoundException ex) {
                                    // Potentially direct the user to the Market with a Dialog
                                    showShortToast("Please install a File Manager.");

                                }

                                break;
                        }

                    }
                }).show();
            }
        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "检测维修");


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
                List<DetectionDetailsData> list = mGson.fromJson((String) data,
                        new TypeToken<List<DetectionDetailsData>>() {
                        }.getType());
                if (list != null && list.size() != 0) {
                    mData = list.get(0);
                    switch (mData.getJobshzt()) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
                        case 0://未审
                            tvAuditStatus.setText("未审核");
                            tvAuditStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                     /*标题设置*/
                            titlebar.setRightText("保存");
                            titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
                                @Override
                                public void onClick(int i) {
                                    switch (i) {
                                        case 2:
                                            if (TextUtils.isEmpty(tvStartTime.getText().toString())
                                                    || TextUtils.isEmpty(tvEndTime.getText().toString()))
                                                showShortToast("请选择起始结束时间");
                                            else
                                                saveData();
                                            break;

                                    }
                                }
                            });
                            break;
                        case 1://已审
                            tvAuditStatus.setText("已审核");
                            tvAuditStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                            break;
                    }
                    tvUnitName.setText(mData.getCname());//单位名称
                    tvContacts.setText(mData.getLxrname());//联系人
                    tvContactNumber.setText(mData.getPhone());//联系电话
                    tvAddress.setText(mData.getShipto());//地址
                    tvSubmitTime.setText(mData.getBsrq());//报送时间
                    tvTechnicalPersonnel.setText(mData.getEmpnames());//技术人员
                    tvDispatchDocuments.setText(mData.getCode());//派工单据
                    tvDispatchDate.setText(mData.getBilldate());//派工日期
                    tvExecutionStatus.setText(mData.getZxjg());//执行状态
                    if (mData.getLb() == 1) {
                        tvGoodsInformation.setText(mData.getGoodsname());
                        tvRegistrationNumber.setText("登记数量：" + mData.getUnitqty() + mData.getUnitname());
                    } else {
                        tvGoodsInformation.setText("概况信息");
                        tvRegistrationNumber.setText("登记数量：" + mData.getUnitqty() + "个");
                    }
                    tvMaintenanceResults.setText(mData.getWxjgname());//维修结果名称设置
                    if (mData.getIsreturn() == 1)//是否重新派工
                        tvRework.setText("是");
                    else
                        tvRework.setText("否");
                    etInstallationNumber.setText(mData.getYesqty() + "");//安装数量
                    etUnloaded.setText(mData.getNoqty() + "");//未装数量
                    etScrapNumber.setText(mData.getDesqty() + "");//报废数量
                    tvStartTime.setText(mData.getBegindate());
                    tvEndTime.setText(mData.getEnddate());
                    etFault.setText(mData.getFactfault());//实际故障
                    etMaintenanceMeasures.setText(mData.getPlaninfo());//维修措施
                    if (TextUtils.isEmpty(mData.getSerialinfo())) {
                        UUID uuid = UUID.randomUUID();
                        mDetail.setSerialinfo(uuid.toString().toUpperCase());
                    } else {
                        mDetail.setSerialinfo(mData.getSerialinfo());
                    }
                }
                break;
            case 1:
                showShortToast("保存成功");
                break;
        }

    }

    @OnClick({R.id.bt_registration_details, R.id.rl_goods_information, R.id.ll_maintenance_results, R.id.ll_rework, R.id.iv_scan, R.id.ll_start_time, R.id.ll_end_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_registration_details://查看登记详情
                startActivityForResult(new Intent(DetectionDetailsActivity.this, MaintenanceDetailsActivity.class)
                        .putExtra("billid", mData.getServiceregid() + ""), DATA_REFERSH);
                break;
            case R.id.rl_goods_information://商品信息
                if (mData.getLb() == 1)
                    startActivity(new Intent(DetectionDetailsActivity.this, ChooseGoodsDetailsActivity.class).putExtra("kind", 2)
                            .putExtra("DATA", mGson.toJson(mData)));
                else
                    startActivity(new Intent(this, IncreaseOverviewActivity.class).putExtra("kind", 2)
                            .putExtra("DATA", mGson.toJson(mData)));
                break;
            case R.id.ll_maintenance_results://维修结果
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "WXJG").putExtra("title", "维修结果选择"), 11);
                break;
            case R.id.ll_rework://重新派工
                startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class)
                        .putExtra("kind", 1), 12);
                break;
            case R.id.iv_scan://序列号
                if (mData != null) {
                    if (mData.getJobshzt() == 0) {
                        int kind = 0;
                        if (!TextUtils.isEmpty(mData.getSerialinfo())) {
                            kind = 1;
                        }
                        startActivityForResult(new Intent(DetectionDetailsActivity.this, EnterSerialNumberActivity.class)
                                .putExtra("billid", billid)
                                .putExtra("kind", kind)
                                .putExtra("uuid", mData.getSerialinfo())
                                .putExtra("tabname", "tb_servicejob")
                                .putExtra("DATA", mGson.toJson(serialList)), 13);
                    } else {
                        startActivity(new Intent(DetectionDetailsActivity.this, SerialNumberDetailsActivity.class)
                                .putExtra("billid", billid)
                                .putExtra("serialinfo", mData.getSerialinfo())
                                .putExtra("tabname", "tb_servicejob"));
                    }
                }
                break;
            case R.id.ll_start_time://开始时间
                selectTime(0);
                break;
            case R.id.ll_end_time://结束时间
                serialList.clear();
                selectTime(1);
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                tvMaintenanceResults.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mData.setWxjgid(Integer.parseInt(data.getStringExtra("CHOICE_RESULT_ID")));
                break;
            case 12://重新派工
                tvRework.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mData.setIsreturn(Integer.parseInt(data.getStringExtra("CHOICE_RESULT_ID")));
                break;
            case 13:
                //处理返回的序列号信息
                LogUtils.d(data.getStringExtra("DATA"));
                serialList = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<List<Serial>>() {
                }.getType());
                break;
            case FILE_SELECT_CODE://文件选择处理结果
                Uri uri = data.getData();
                LogUtils.e("File Uri: " + uri.toString());
                // Get the path
                String path = FileUtils.getUrlPath(this, uri);
                LogUtils.e("File Path: " + path);
                FileChooseData fileChooseData = new FileChooseData();
                fileChooseData.setType(1);
                fileChooseData.setUrl(path);
                mFileChooseDatas.add(fileChooseData);
                mFileChooseAdapter.setList(mFileChooseDatas);
                break;
        }
    }

    /**
     * 时间选择器
     *
     * @param i
     */
    public void selectTime(final int i) {
        if (mTimePickerView == null)
            mTimePickerView = new TimePickerView(this, TimePickerView.Type.ALL);
        mTimePickerView.setTime(new Date());
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                switch (i) {
                    case 0:
                        tvStartTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd HH:mm:ss"));
                        break;
                    case 1:
                        tvEndTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd HH:mm:ss"));
                        break;
                }

            }
        });
        mTimePickerView.show();
    }

    /**
     * 保存数据
     */
    private void saveData() {
//        Billid    主单ID
//        itemno  明细ID
//        wxjgid  维修结果ID
//        isreturn 重新派工 0否 1是
//        yesqty  已装数量
//        noqty   未装数量
//        desqty  报废数量
//        begindate 开始时间
//        enddate  结束时间
//        factfault  实际故障
//        planinfo  维修措施
//        serialinfo  序列号GUID
//        opid      操作员ID


        mDetail.setBillid(mData.getBillid() + "");
        mDetail.setItemno(mData.getItemno() + "");
        mDetail.setWxjgid(mData.getWxjgid() + "");
        mDetail.setIsreturn(mData.getIsreturn() + "");
        mDetail.setYesqty(etInstallationNumber.getText().toString());
        mDetail.setNoqty(etUnloaded.getText().toString());
        mDetail.setDesqty(etScrapNumber.getText().toString());
        mDetail.setBegindate(tvStartTime.getText().toString().replace(":", "|"));
        mDetail.setEnddate(tvEndTime.getText().toString().replace(":", "|"));
        mDetail.setFactfault(etFault.getText().toString());
        mDetail.setPlaninfo(etMaintenanceMeasures.getText().toString());
        mDetail.setOpid(ShareUserInfo.getUserId(this));
        List list = new ArrayList();
        list.add(mDetail);
        Map map = new ArrayMap<>();
        map.put("dbname", ShareUserInfo.getDbName(this));
        map.put("parms", "JCWX");
        map.put("master", "");
        map.put("detail", mGson.toJson(list));
        map.put("serialinfo", mGson.toJson(serialList));
        map.put("attfiles", "");
        presenter.post(1, "billsavenew", map);
    }

}