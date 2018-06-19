package com.update.actiity.maintenance;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jph.takephoto.model.TResult;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.actiity.installation.ChooseGoodsActivity;
import com.update.actiity.project.ChoiceProjectActivity;
import com.update.adapter.FileChooseAdapter;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.dialog.DialogFactory;
import com.update.dialog.OnDialogClickInterface;
import com.update.model.Attfiles;
import com.update.model.ChooseGoodsData;
import com.update.model.FileChooseData;
import com.update.model.GoodsOrOverviewData;
import com.update.model.Master;
import com.update.model.Serial;
import com.update.utils.DateUtil;
import com.update.utils.FileUtils;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/23 0023 下午 3:21
 * Description:新增维修登记
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/23 0023         申中佳               V1.0
 */
public class NewMaintenanceRegistrationActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.et_contact_number)
    EditText etContactNumber;
    @BindView(R.id.et_customer_address)
    EditText etCustomerAddress;
    @BindView(R.id.tv_submit_time)
    TextView tvSubmitTime;
    @BindView(R.id.tv_document_date)
    TextView tvDocumentDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.tv_service_mode)
    TextView tvServiceMode;
    @BindView(R.id.tv_rating_category)
    TextView tvRatingCategory;
    @BindView(R.id.tv_priority)
    TextView tvPriority;
    @BindView(R.id.tv_profile_information)
    TextView tvProfileInformation;
    @BindView(R.id.tv_registration_number)
    TextView tvRegistrationNumber;
    @BindView(R.id.rl_profile_information)
    RelativeLayout rlProfileInformation;
    @BindView(R.id.rcv_choose_goods_list)
    RecyclerView rcvChooseGoodsList;
    @BindView(R.id.rcv_choose_file_list)
    RecyclerView rcvChooseFileList;
    @BindView(R.id.et_messenger)
    EditText etMessenger;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.tv_related_projects)
    TextView tvRelatedProjects;

    private TimePickerView mTimePickerView;//时间选择弹窗
    private FileChooseAdapter mFileChooseAdapter;


    private String clientid;//客户ID
    private String lxrid;//联系人ID
    private String mProjectid;//  项目ID
    private String sxfsid;// 服务方式ID
    private String billdate;//生产日期
    private String bsrq;//报送日期
    private String regtype;// 服务类型id
    private String priorid;// 优先级ID
    private String departmentid;// 部门id
    private String empid;//业务员ID
    private GoodsOrOverviewData mOverviewData;//概况信息
    Gson mGson;
    private List<ChooseGoodsData> mChooseGoodsDataList;
    private List<FileChooseData> mFileChooseDatas;
    private Map<String, Object> mParmMap;
    private static final int FILE_SELECT_CODE = 66;

    private int goods_possion;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mFileChooseDatas = new ArrayList<>();
        mChooseGoodsDataList = new ArrayList<>();

        mGson = new GsonBuilder().disableHtmlEscaping().create();
        billdate = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
        bsrq = DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_add_install_registration;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        tvSubmitTime.setText(bsrq);//设置默认报送时间
        tvDocumentDate.setText(billdate);//设置默认单据日期
        sxfsid = "358";
        tvServiceMode.setText("上门服务");
        regtype = "0";
        tvRatingCategory.setText("派工");
        priorid = "0";
        tvPriority.setText("普通");

        departmentid = ShareUserInfo.getKey(this, "departmentid");
        tvDepartment.setText(ShareUserInfo.getKey(this, "depname"));
        empid = ShareUserInfo.getKey(this, "empid");
        tvSalesman.setText(ShareUserInfo.getKey(this, "opname"));
        rlProfileInformation.setVisibility(View.GONE);//未添加概况信息，概况信息隐藏

        /* 选择商品集合信息处理 */
        rcvChooseGoodsList.setLayoutManager(new LinearLayoutManager(this));
        rcvChooseGoodsList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ChooseGoodsResultHolder, ChooseGoodsData>(mChooseGoodsDataList, false) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getChooseGoodsResultHolder(NewMaintenanceRegistrationActivity.this, parent);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.ChooseGoodsResultHolder holder, ChooseGoodsData data) {
                holder.tvRegistrationNumber.setText("登记数量：" + data.getNumber() + "个");
                holder.tvGoodsInformation.setText(data.getCode()+"    "+data.getName()+"    "+data.getSpecs()+"    "+data.getModel());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goods_possion = holder.getLayoutPosition();
                        startActivityForResult(new Intent(NewMaintenanceRegistrationActivity.this, GoodsDetailsActivity.class)
                                .putExtra("kind", 1)
                                .putExtra("DATA", new Gson().toJson(mChooseGoodsDataList.get(goods_possion))), 18);
                    }
                });

            }

        });
        /* 选择文件集合信息处理 */
        rcvChooseFileList.setLayoutManager(new GridLayoutManager(this, 4));
        rcvChooseFileList.setAdapter(mFileChooseAdapter = new FileChooseAdapter(this) {
            @Override
            public void checkFile() {
                DialogFactory.getFileChooseDialog(NewMaintenanceRegistrationActivity.this, new OnDialogClickInterface() {
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
        titlebar.setTitleText(this, "维修登记");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 2:
                        addInstallRegistration();
                        titlebar.setTvRightEnabled(false);
                        break;

                }
            }
        });
    }

    @OnClick({R.id.ll_unit_name_choice, R.id.ll_contacts_choice, R.id.ll_related_projects_choice, R.id.ll_submit_time_choice, R.id.ll_service_mode_choice, R.id.ll_rating_category_choice, R.id.ll_priority_choice, R.id.tv_choose_goods, R.id.tv_adding_profile, R.id.rl_profile_information, R.id.ll_document_date_choice, R.id.ll_department_choice, R.id.ll_salesman_choice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_name_choice://单位选择
                startActivityForResult(new Intent(this, CommonXzkhActivity.class), 11);
                break;
            case R.id.ll_contacts_choice://联系人选择
                if (TextUtils.isEmpty(clientid))
                    showShortToast("请先选择单位");
                else
                    startActivityForResult(new Intent(this, CommonXzlxrActivity.class).putExtra("clientid", clientid), 12);
                break;
            case R.id.ll_related_projects_choice://关联项目选择
                if (TextUtils.isEmpty(clientid))
                    showShortToast("请先选择单位");
                else {
//                    startActivityForResult(new Intent(this, ProjectSelectionActivity.class).putExtra("clientid", clientid), 12);
                    startActivityForResult(new Intent(this, ChoiceProjectActivity.class).putExtra("clientid", clientid), 13);
                }
                break;
            case R.id.ll_submit_time_choice://报送时间选择
                selectTime(0);
                break;
            case R.id.ll_service_mode_choice://服务方式选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "WXFWLX").putExtra("title", "服务方式选择"), 14);
                break;
            case R.id.ll_rating_category_choice://登记类别选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "WXDJLX").putExtra("title", "登记类别选择"), 15);
                break;
            case R.id.ll_priority_choice://优先级选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "FWYXJ").putExtra("title", "优先级选择"), 16);
                break;
            case R.id.tv_choose_goods://商品选择
                startActivityForResult(new Intent(this, ChooseGoodsActivity.class)
                        .putExtra("kind", 1), 17);
                break;
            case R.id.tv_adding_profile://增加概况
                if (rlProfileInformation.getVisibility() == View.VISIBLE) {
                    showShortToast("已添加过概况信息");
                } else
                    startActivityForResult(new Intent(this, OverviewActivity.class), 19);
                break;
            case R.id.rl_profile_information://概况详情查看
                startActivityForResult(new Intent(this, OverviewActivity.class).putExtra("kind", 1)
                        .putExtra("DATA", new Gson().toJson(mOverviewData)), 20);

                break;
            case R.id.ll_document_date_choice://单据日期选择
                selectTime(1);
                break;
            case R.id.ll_department_choice://部门选择
                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 21);
                break;
            case R.id.ll_salesman_choice://业务员选择
                if (TextUtils.isEmpty(departmentid))
                    showShortToast("请先选择部门");
                else
                    startActivityForResult(new Intent(this, SelectSalesmanActivity.class).putExtra("depid", departmentid), 22);
                break;
        }
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        switch (requestCode) {
            case 11://单位选择结果处理
                clientid = data.getStringExtra("id");
                lxrid = data.getStringExtra("lxrid");
                tvUnitName.setText(data.getStringExtra("name"));
                tvContacts.setText(data.getStringExtra("lxrname"));
                etContactNumber.setText(data.getStringExtra("phone"));
                etCustomerAddress.setText(data.getStringExtra("shipto"));
                break;
            case 12://联系人选择结果处理
                lxrid = data.getStringExtra("id");
                tvContacts.setText(data.getStringExtra("name"));
                etContactNumber.setText(data.getStringExtra("phone"));
                break;
            case 13://关联项目选择结果处理
                mProjectid = data.getStringExtra("projectid");
                tvRelatedProjects.setText(data.getStringExtra("title"));
                break;
            case 14://服务方式选择结果处理
                sxfsid = data.getStringExtra("CHOICE_RESULT_ID");
                tvServiceMode.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 15://登记类别选择结果处理
                regtype = data.getStringExtra("CHOICE_RESULT_ID");
                tvRatingCategory.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 16://优先级选择结果处理
                priorid = data.getStringExtra("CHOICE_RESULT_ID");
                tvPriority.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 17://商品选择结果处理
                List<ChooseGoodsData> list = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<List<ChooseGoodsData>>() {
                }.getType());
                mChooseGoodsDataList.addAll(list);
                mAdapter.setList(mChooseGoodsDataList);
                break;
            case 18://商品选择结果处理
                switch (data.getIntExtra("KIND", 0)) {
                    case 0:
                        mChooseGoodsDataList.remove(goods_possion);
                        break;
                    case 1:
                        ChooseGoodsData chooseGoodsData = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<ChooseGoodsData>() {
                        }.getType());
                        LogUtils.d(mGson.toJson(mChooseGoodsDataList));
                        mChooseGoodsDataList.set(goods_possion, chooseGoodsData);
                        LogUtils.d(mGson.toJson(mChooseGoodsDataList));
                        break;
                }

                mAdapter.setList(mChooseGoodsDataList);
                break;
            case 19://增加概况结果处理
                mOverviewData = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<GoodsOrOverviewData>() {
                }.getType());
                rlProfileInformation.setVisibility(View.VISIBLE);
                tvRegistrationNumber.setText("登记数量：" + mOverviewData.getUnitqty() + "个");
                break;
            case 20://概况详情查看结果处理
                switch (data.getIntExtra("KIND", 0)) {
                    case 0://修改处理结果
                        mOverviewData = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<GoodsOrOverviewData>() {
                        }.getType());
                        tvRegistrationNumber.setText("登记数量：" + mOverviewData.getUnitqty() + "个");
                        break;
                    case 1://删除处理结果
                        rlProfileInformation.setVisibility(View.GONE);
                        mOverviewData = null;
                        break;
                }
                break;
            case 21://部门选择结果处理
                departmentid = data.getStringExtra("CHOICE_RESULT_ID");
                tvDepartment.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                empid = "";
                tvSalesman.setText("");
                break;
            case 22://业务员选择结果处理
                empid = data.getStringExtra("CHOICE_RESULT_ID");
                tvSalesman.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
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
        if (i == 0) {
            mTimePickerView = new TimePickerView(this, TimePickerView.Type.ALL);
        } else {
            mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        }

        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                switch (i) {
                    case 0:
                        tvSubmitTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd HH:mm:ss"));
                        break;
                    case 1:
                        tvDocumentDate.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                }

            }
        });
        mTimePickerView.show();
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

    /**
     * 添加安装登记
     */
    private void addInstallRegistration() {
        if (TextUtils.isEmpty(clientid)) {
            showShortToast("请先选择单位");
            titlebar.setTvRightEnabled(true);
            return;
        }
        String phone = etContactNumber.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showShortToast("请输入联系电话");
            titlebar.setTvRightEnabled(true);
            return;
        }
        String shipto = etCustomerAddress.getText().toString();
        if (TextUtils.isEmpty(shipto)) {
            showShortToast("请输入客户地址");
            titlebar.setTvRightEnabled(true);
            titlebar.setTvRightEnabled(true);
            return;
        }
        String bxr = etMessenger.getText().toString();
//        if (TextUtils.isEmpty(bxr)) {
//            showShortToast("请输入报送人姓名");
//            return;
//        }
        if (mOverviewData == null && (mChooseGoodsDataList == null || mChooseGoodsDataList.size() == 0)) {
            showShortToast("请添加商品明细");
            titlebar.setTvRightEnabled(true);
            return;
        }
        if (TextUtils.isEmpty(departmentid)) {
            showShortToast("请先选择部门");
            titlebar.setTvRightEnabled(true);
            return;
        }
        if (TextUtils.isEmpty(empid)) {
            showShortToast("请先选择业务员");
            titlebar.setTvRightEnabled(true);
            return;
        }

        Master master = new Master();
        master.setBillid("0");
        master.setClientid(clientid);
        master.setLxrid(lxrid);
        master.setLxrname(tvContacts.getText().toString());
        master.setPhone(phone);
        master.setShipto(shipto);
        master.setBilldate(tvDocumentDate.getText().toString());
        master.setBsrq(tvSubmitTime.getText().toString().replace(":", "|"));
        master.setBxr(bxr);
        master.setSxfsid(sxfsid);
        master.setProjectid(mProjectid);
        master.setRegtype(regtype);
        master.setPriorid(priorid);
        master.setDepartmentid(departmentid);
        master.setEmpid(empid);
        master.setMemo(etNote.getText().toString());
        master.setOpid(ShareUserInfo.getUserId(this));
        List<Master> masterList = new ArrayList<>();
        masterList.add(master);
        List<GoodsOrOverviewData> goodsOrOverviewDataList = new ArrayList<>();
        List<Serial> serialList = new ArrayList<>();
        for (int i = 0; i < mChooseGoodsDataList.size(); i++) {//增加商品信息
            ChooseGoodsData chooseGoodsData = mChooseGoodsDataList.get(i);
            GoodsOrOverviewData overviewData = new GoodsOrOverviewData();
            overviewData.setBillid("0");
            overviewData.setItemno("0");
            overviewData.setLb("1");
            overviewData.setGoodsid(chooseGoodsData.getGoodsid() + "");
            overviewData.setGoodsname(chooseGoodsData.getName());
            overviewData.setUnitqty(chooseGoodsData.getNumber());
            overviewData.setUnitid(chooseGoodsData.getUnitid() + "");
            overviewData.setSerialinfo(chooseGoodsData.getSerialinfo());
            overviewData.setEnsureid(chooseGoodsData.getEnsureid());
            overviewData.setFaultid(chooseGoodsData.getFaultid());
            overviewData.setFaultinfo(chooseGoodsData.getFaultinfo());
            goodsOrOverviewDataList.add(overviewData);
            if (chooseGoodsData.getSerials() != null)
                serialList.addAll(chooseGoodsData.getSerials());

        }
        if (mOverviewData != null) {
            mOverviewData.setEnsurename(null);
            mOverviewData.setFaultname(null);
            goodsOrOverviewDataList.add(mOverviewData);// //添加概况信息
        }

        List<Attfiles> attfilesList = new ArrayList<>();
        mFileChooseDatas = mFileChooseAdapter.getList();
        for (int i = 0; i < mFileChooseDatas.size(); i++) {
            Attfiles attfiles = new Attfiles();
            attfiles.setBillid("0");
            attfiles.setClientid(clientid);
            File file = new File(mFileChooseDatas.get(i).getUrl());
            attfiles.setFilenames(file.getName());
            attfiles.setOpid(ShareUserInfo.getUserId(this));
            try {
                attfiles.setXx(FileUtils.encodeBase64File(mFileChooseDatas.get(i).getUrl()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            attfilesList.add(attfiles);
        }
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "WXDJ");
        mParmMap.put("master", mGson.toJson(masterList));
        mParmMap.put("detail", mGson.toJson(goodsOrOverviewDataList));
        mParmMap.put("serialinfo", mGson.toJson(serialList));
        if (attfilesList.size() != 0) {
            mParmMap.put("attfiles", mGson.toJson(attfilesList));
//            setCache((String) mParmMap.get("attfiles"), this, "myfirst.txt", Context.MODE_WORLD_READABLE);
        }
        presenter.post(0, "billsavenew", mParmMap);
    }

    /**
     * 设置缓存
     * content是要存储的内容，可以是任意格式的，不一定是字符串。
     */
    public static void setCache(String content, Context context, String cacheFileName, int mode) {
        FileOutputStream fos = null;
        try {
            //打开文件输出流，接收参数是文件名和模式
            fos = context.openFileOutput(cacheFileName, mode);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        String result = (String) data;
        if (TextUtils.isEmpty(result) || result.equals("false")) {
            showShortToast(data.toString());
        } else {
            showShortToast("添加成功");
            finish();
        }
    }

    @Override
    public void httpFinish(int requestCode) {
        super.httpFinish(requestCode);
        switch (requestCode){
            case 0:
                titlebar.setTvRightEnabled(true);
                break;
        }
    }
}
