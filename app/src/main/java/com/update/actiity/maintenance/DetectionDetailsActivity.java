package com.update.actiity.maintenance;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jph.takephoto.model.TResult;
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
import com.update.model.Attfiles;
import com.update.model.DetectionDetailsData;
import com.update.model.FileChooseData;
import com.update.model.Serial;
import com.update.model.request.PerformSituationData;
import com.update.model.request.RqShlbData;
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
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    @BindView(R.id.bt_bottom)
    Button btBottom;
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
    private boolean firstSerial;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        firstSerial = true;

        mDetail = new PerformSituationData();
        mFileChooseDatas = new ArrayList<>();
        mGson = new GsonBuilder().disableHtmlEscaping().create();
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
                            btBottom.setText("审核");
                            /*标题设置*/
                            titlebar.setRightText("保存");
                            titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
                                @Override
                                public void onClick(int i) {
                                    switch (i) {
                                        case 2:
//                                            if (TextUtils.isEmpty(tvStartTime.getText().toString())
//                                                    || TextUtils.isEmpty(tvEndTime.getText().toString()))
//                                                showShortToast("请选择起始结束时间");
//                                            else
                                            if (!ShareUserInfo.getKey(mActivity, "bj").equals("1")) {
                                                showShortToast("你没有该权限，请向管理员申请权限！");
                                                return;
                                            }
                                            saveData();
                                            titlebar.setTvRightEnabled(false);
                                            break;

                                    }
                                }
                            });
                            break;
                        case 1://已审
                            tvAuditStatus.setText("已审核");
                            btBottom.setText("弃审");
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
                        tvGoodsInformation.setText(mData.getGoodscode() + "    " + mData.getGoodsname() + "    " + mData.getSpecs() + "    " + mData.getModel());
                        tvRegistrationNumber.setText("登记数量：" + (mData.getYesqty() + mData.getNoqty() + mData.getDesqty()) + mData.getUnitname());
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
                //获取文件列表
                Map map = new ArrayMap();
                map.put("dbname", ShareUserInfo.getDbName(this));
                map.put("attcode", "JCWX");
                map.put("billid", itemno);
                map.put("curpage", "0");
                map.put("pagesize", "100");
                presenter.post(1, "attfilelist", map);
                break;
            case 1:
                List<Attfiles> attfilesList = mGson.fromJson((String) data,
                        new TypeToken<List<Attfiles>>() {
                        }.getType());
                if (attfilesList != null && attfilesList.size() > 0) {
                    LogUtils.e(attfilesList.size() + "");
                    saveFile(attfilesList);

                }
                break;
            case 2:
                showShortToast("保存成功");
                break;

            case 3:
                List<RqShlbData> shlb = mGson.fromJson((String) data,
                        new TypeToken<List<RqShlbData>>() {
                        }.getType());
                Map smap = new ArrayMap<>();
                smap.put("dbname", ShareUserInfo.getDbName(this));
                smap.put("tabname", "tb_servicejob");
                smap.put("pkvalue", billid);
                smap.put("levels", shlb.get(0).getLevels() + "");
                smap.put("opid", ShareUserInfo.getUserId(this));

                switch (tvAuditStatus.getText().toString()) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
                    case "未审核"://未审
                        smap.put("shzt", "1");
                        presenter.post(4, "billsh", smap);
                        break;
                    case "已审核"://已审
                        smap.put("shzt", "0");
                        presenter.post(5, "billsh", smap);
                        break;

                }

                break;
            case 4:
                LogUtils.e(data.toString());
                if (data.toString().equals("")) {
                    presenter.post(0, ServerURL.BILLDETAIL, mParmMap);
//                    btBottom.setText("弃审");
//                    tvAuditStatus.setText("已审核");
//                    titlebar.setRightText("");
//                    tvAuditStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                } else
                    showShortToast("该单据已经最终审核，不能重复审核");

                break;
            case 5:
                if (data.toString().equals("")) {
                    presenter.post(0, ServerURL.BILLDETAIL, mParmMap);
//                    tvAuditStatus.setText("未审核");
//                    btBottom.setText("审核");
//                    titlebar.setRightText("保存");
//                    tvAuditStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                } else
                    showShortToast(data.toString());

                break;
        }

    }

    private void saveFile(final List<Attfiles> attfilesList) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 0; i < attfilesList.size(); i++) {
                        FileUtils.decoderBase64File(attfilesList.get(i).getXx(), mActivity, FileUtils.getPath(mActivity, "AZDJ/", billid + attfilesList.get(i).getFilenames()), Context.MODE_PRIVATE);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                subscriber.onNext("");
                subscriber.onCompleted();
            }


        })
                .subscribeOn(Schedulers.computation()) // 指定 subscribe() 发生在 运算 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>() {

                    @Override
                    public void onNext(String s) {//主线程执行的方法
                        LogUtils.e(s);
                        for (int i = 0; i < attfilesList.size(); i++) {
                            FileChooseData fileChooseData = new FileChooseData();
                            String fileName = attfilesList.get(i).getFilenames();
                            int dotIndex = fileName.lastIndexOf(".");
                            /* 获取文件的后缀名*/
                            String type = fileName.substring(dotIndex, fileName.length()).toLowerCase();
                            LogUtils.e(type);
                            switch (type) {
                                case ".bmp":
                                case ".gif":
                                case ".jpeg":
                                case ".jpg":
                                case ".png":
                                    fileChooseData.setType(0);
                                    break;
                                default:
                                    fileChooseData.setType(1);
                                    break;
                            }


                            fileChooseData.setUrl(FileUtils.getPath(mActivity, "AZDJ/", billid + fileName));
                            mFileChooseDatas.add(fileChooseData);

                        }
                        mFileChooseAdapter.setList(mFileChooseDatas);

                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.e("-------------1---------------");


                    }

                    @Override
                    public void onError(Throwable e) {
//

                    }
                });

    }

    @OnClick({R.id.bt_registration_details, R.id.rl_goods_information, R.id.ll_maintenance_results, R.id.ll_rework, R.id.iv_scan, R.id.ll_start_time, R.id.ll_end_time, R.id.bt_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_registration_details://查看登记详情
                startActivityForResult(new Intent(DetectionDetailsActivity.this, MaintenanceDetailsActivity.class)
                        .putExtra("billid", mData.getServiceregid() + ""), DATA_REFERSH);
                break;
            case R.id.rl_goods_information://商品信息
                if (mData.getLb() == 1) {
                    mData.setUnitqty(mData.getYesqty() + mData.getNoqty() + mData.getDesqty());
                    startActivityForResult(new Intent(mActivity, GoodsDetailsActivity.class)
                            .putExtra("xlh", false)
                            .putExtra("kind", 2)
                            .putExtra("DATA", mGson.toJson(mData)), 18);
//                    startActivity(new Intent(DetectionDetailsActivity.this, ChooseGoodsDetailsActivity.class)
//                            .putExtra("xlh", false)
//                            .putExtra("xzm", true)
//                            .putExtra("tabname","tb_servicejob")
//                            .putExtra("kind", 2)
//                            .putExtra("DATA", mGson.toJson(mData)));
                } else
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
                        if (serialList == null)
                            serialList = new ArrayList<>();
                        startActivityForResult(new Intent(DetectionDetailsActivity.this, EnterSerialNumberActivity.class)
                                .putExtra("billid", billid)
                                .putExtra("itemno", itemno)
                                .putExtra("kind", kind)
                                .putExtra("first", firstSerial)
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
            case R.id.bt_bottom://（审核/弃审）
//                Map map = new ArrayMap<>();
//                map.put("dbname", ShareUserInfo.getDbName(this));
//                map.put("tabname", "tb_servicejob");
//                map.put("pkvalue", billid);
//                presenter.post(3, "billshlist", map);
                Map smap = new ArrayMap<>();
                smap.put("dbname", ShareUserInfo.getDbName(this));
                smap.put("tabname", "tb_servicejobdetail");
                smap.put("pkvalue", itemno);
                smap.put("levels", "0");
                smap.put("opid", ShareUserInfo.getUserId(this));

                switch (tvAuditStatus.getText().toString()) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
                    case "未审核"://未审
                        smap.put("shzt", "1");
                        presenter.post(4, "billsh", smap);
                        break;
                    case "已审核"://已审
                        smap.put("shzt", "0");
                        presenter.post(5, "billsh", smap);
                        break;

                }
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
                firstSerial = false;
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

        double yesqty = Double.parseDouble(etInstallationNumber.getText().toString());
        double noqty = Double.parseDouble(etUnloaded.getText().toString());
        double number = yesqty + noqty;
        if (mData.getUnitqty() < number) {
            showShortToast("合计数量大于登记数量！");
            titlebar.setTvRightEnabled(true);
            return;
        }
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

        List<Attfiles> attfilesList = new ArrayList<>();
        mFileChooseDatas = mFileChooseAdapter.getList();
        for (int i = 0; i < mFileChooseDatas.size(); i++) {
            Attfiles attfiles = new Attfiles();
            attfiles.setBillid(mData.getItemno() + "");
            attfiles.setClientid(mData.getBillid() + "");
            File file = new File(mFileChooseDatas.get(i).getUrl());
            attfiles.setFilenames(file.getName());
            attfiles.setOpid(ShareUserInfo.getUserId(this));
            LogUtils.e(mFileChooseDatas.get(i).getUrl());
            //进行Base64编码
//            String uploadBuffer = new String(Base64.encode(PicUtil.compressImage(bitmap2)));
//            setCache( uploadBuffer , this, "myF.txt", Context.MODE_PRIVATE);
            try {
                attfiles.setXx(FileUtils.encodeBase64File(mFileChooseDatas.get(i).getUrl()));
//                setCache(attfiles.getXx(), this, "my.txt", Context.MODE_PRIVATE);
//                decoderBase64File(attfiles.getXx(),"/data/data/com.crenp.activity/cache/takephoto_cache/19.png");
            } catch (Exception e) {
                e.printStackTrace();
            }
            attfilesList.add(attfiles);
        }
        Map map = new ArrayMap<>();
        map.put("dbname", ShareUserInfo.getDbName(this));
        map.put("parms", "JCWX");
        map.put("master", "");
        map.put("detail", mGson.toJson(list));
        if (serialList != null)
            map.put("serialinfo", mGson.toJson(serialList));
        map.put("attfiles", mGson.toJson(attfilesList));
        presenter.post(2, "billsavenew", map);
    }

    @Override
    public void takeSuccess(TResult result) {
        LogUtils.e(result.getImage().toString());
        FileChooseData fileChooseData = new FileChooseData();
        fileChooseData.setType(0);
        fileChooseData.setUrl(result.getImage().getCompressPath());
        mFileChooseDatas.add(fileChooseData);
        mFileChooseAdapter.setList(mFileChooseDatas);

    }

    @Override
    public void httpFinish(int requestCode) {
        super.httpFinish(requestCode);
        switch (requestCode) {
            case 2:
                titlebar.setTvRightEnabled(true);
                break;
        }
    }
}
