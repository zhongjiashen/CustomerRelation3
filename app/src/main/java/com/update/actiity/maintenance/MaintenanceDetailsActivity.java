package com.update.actiity.maintenance;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.installation.AddInstallRegistrationActivity;
import com.update.actiity.installation.InstallRegistrationDetailsActivity;
import com.update.adapter.FileChooseAdapter;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.dialog.DialogFactory;
import com.update.dialog.OnDialogClickInterface;
import com.update.model.Attfiles;
import com.update.model.GoodsOrOverviewData;
import com.update.model.InstallRegistrationDetailsData;
import com.update.model.request.RqShlbData;
import com.update.utils.FileUtils;
import com.update.utils.LogUtils;
import com.update.utils.SeeFileUtils;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

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
 * Date:      2018/3/15 0015 上午 10:03
 * Description:维修登记列表详情
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/15 0015         申中佳               V1.0
 */
public class MaintenanceDetailsActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_unit_number)
    TextView tvUnitNumber;
    @BindView(R.id.tv_audit_status)
    TextView tvAuditStatus;
    @BindView(R.id.tv_registration_status)
    TextView tvRegistrationStatus;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_contact_number)
    TextView tvContactNumber;
    @BindView(R.id.tv_customer_address)
    TextView tvCustomerAddress;
    @BindView(R.id.tv_related_projects)
    TextView tvRelatedProjects;
    @BindView(R.id.tv_messenger)
    TextView tvMessenger;
    @BindView(R.id.tv_submit_time)
    TextView tvSubmitTime;
    @BindView(R.id.tv_service_mode)
    TextView tvServiceMode;
    @BindView(R.id.tv_rating_category)
    TextView tvRatingCategory;
    @BindView(R.id.tv_priority)
    TextView tvPriority;
    @BindView(R.id.rcv_choose_goods_list)
    RecyclerView rcvChooseGoodsList;
    @BindView(R.id.rcv_choose_file_list)
    RecyclerView rcvChooseFileList;
    @BindView(R.id.tv_document_date)
    TextView tvDocumentDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.tv_note)
    TextView tvNote;

    InstallRegistrationDetailsData master;//安装登记单详细（主表）数据
    @BindView(R.id.tv_zdr)
    TextView tvZdr;
    @BindView(R.id.bt_sh)
    Button btSh;


    private Map<String, Object> mParmMap;
    private String billid;//单据ID
    Gson mGson;
    private List<GoodsOrOverviewData> mGoodsOrOverviewDatas;


    List<Attfiles> attfilesList;
    protected BaseRecycleAdapter mFileAdapter;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mGson = new Gson();
        billid = getIntent().getStringExtra("billid");
        presenter = new BaseP(this, this);
        mParmMap = new ArrayMap<>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "WXDJ");
        mParmMap.put("billid", billid);
        presenter.post(0, ServerURL.BILLMASTER, mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_install_registration_details;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        /* 选择商品集合信息处理 */
        rcvChooseGoodsList.setLayoutManager(new LinearLayoutManager(this));
        rcvChooseGoodsList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ChooseGoodsResultHolder, GoodsOrOverviewData>(mGoodsOrOverviewDatas, false) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getChooseGoodsResultHolder(MaintenanceDetailsActivity.this, parent);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.ChooseGoodsResultHolder holder, final GoodsOrOverviewData data) {
                holder.tvRegistrationNumber.setText("登记数量：" + data.getUnitqty() + "个");
                switch (data.getLb()) {//0 概况（goodsname记录内容） ,1 商品
                    case "0":
                        holder.tvGoodsInformation.setText("概况信息");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MaintenanceDetailsActivity.this, OverviewActivity.class).putExtra("kind", 2)
                                        .putExtra("DATA", new Gson().toJson(mGoodsOrOverviewDatas.get(holder.getLayoutPosition()))));
                            }
                        });
                        break;
                    case "1":
                        holder.tvGoodsInformation.setText("示商品编码    名称    规格    型号");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MaintenanceDetailsActivity.this, GoodsDetailsActivity.class).putExtra("kind", 2)
                                        .putExtra("DATA", new Gson().toJson(mGoodsOrOverviewDatas.get(holder.getLayoutPosition()))));
                            }
                        });
                        break;
                }

            }

        });

        /* 选择文件集合信息处理 */
        rcvChooseFileList.setLayoutManager(new GridLayoutManager(this, 4));
        rcvChooseFileList.setAdapter(mFileAdapter = new BaseRecycleAdapter<ViewHolderFactory.ChooseFileResultHolder, Attfiles>(attfilesList, false) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getChooseFileResultHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.ChooseFileResultHolder holder, final Attfiles data) {
                LogUtils.e( FileUtils.getPath(mActivity,"AZDJ/", billid + attfilesList.get(holder.getLayoutPosition()).getFilenames()));
                holder.ivDelete.setVisibility(View.GONE);
                Glide.with(mActivity).load(FileUtils.getPath(mActivity,"AZDJ/", billid + attfilesList.get(holder.getLayoutPosition()).getFilenames())).error(R.mipmap.ic_file).into(holder.sivImage);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SeeFileUtils.openFile(mActivity,FileUtils.getPath(mActivity,"AZDJ/", billid + attfilesList.get(holder.getLayoutPosition()).getFilenames()));
                    }
                });
            }

        });

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "维修登记");

    }
    @OnClick({R.id.bt_sh, R.id.bt_delete})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_sh:
                Map map = new ArrayMap<>();
                map.put("dbname", ShareUserInfo.getDbName(this));
                map.put("tabname", "tb_servicereg");
                map.put("pkvalue", billid);
                presenter.post(3, "billshlist", map);
                break;
            case R.id.bt_delete:
                DialogFactory.getButtonDialog(this, "确定要删除该单据吗？", new OnDialogClickInterface() {
                    @Override
                    public void OnClick(int requestCode, Object object) {
                        Map dMap= new ArrayMap<>();
                        dMap.put("dbname", ShareUserInfo.getDbName(mActivity));
                        dMap.put("tabname", "tb_servicereg");
                        dMap.put("pkvalue", billid);
                        dMap.put("opid", ShareUserInfo.getUserId(mActivity));
                        presenter.post(6, "billdelmaster", dMap);
                    }


                }).show();

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
                List<InstallRegistrationDetailsData> list = mGson.fromJson((String) data,
                        new TypeToken<List<InstallRegistrationDetailsData>>() {
                        }.getType());
                master = list.get(0);
                tvUnitNumber.setText(master.getCode());//单据编号
                switch (master.getShzt()) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
                    case 0://未审
                        tvAuditStatus.setText("未审核");
                        tvAuditStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                        btSh.setText("审核");
                        break;
                    case 1://已审
                        tvAuditStatus.setText("已审核");
                        btSh.setText("弃审");
                        tvAuditStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                        break;
                    case 2://审核中
                        tvAuditStatus.setText("审核中");
                        tvAuditStatus.setBackgroundColor(Color.parseColor("#00CC00"));
                        break;
                }
                switch (master.getDjzt()) {//登记状态设置,{"id":1,"dictmc":"未处理"},{"id":2,"dictmc":"处理中"},{"id":3,"dictmc":"已完成"}
                    case 1://未处理
                        tvRegistrationStatus.setText("未处理");
                        tvRegistrationStatus.setBackgroundColor(Color.parseColor("#FF6600"));
                        break;
                    case 2://审核中
                        tvRegistrationStatus.setText("处理中");
                        tvRegistrationStatus.setBackgroundColor(Color.parseColor("#00CC00"));
                        break;
                    case 3://已审
                        tvRegistrationStatus.setText("已完成");
                        tvRegistrationStatus.setBackgroundColor(Color.parseColor("#0066FF"));
                        break;
                }
                tvUnitName.setText(master.getCname());//单位名称
                tvContacts.setText(master.getLxrname());//联系人
                tvContactNumber.setText(master.getPhone());//联系电话
                tvCustomerAddress.setText(master.getShipto());//客户地址
                tvMessenger.setText(master.getBxr());//报送人
                tvSubmitTime.setText(master.getBsrq());//报送日期
                tvServiceMode.setText(master.getSxfsname());//服务方式
                tvRatingCategory.setText(master.getRegtypename());//登记类别
                tvPriority.setText(master.getPriorname());//优先级
                tvDocumentDate.setText(master.getBilldate());//单据日期
                tvDepartment.setText(master.getDepname());//部门名称
                tvSalesman.setText(master.getEmpname());//业务员姓名
                tvZdr.setText(master.getOpname());//制单人
                tvNote.setText(master.getMemo());//摘要
//                Map map= new ArrayMap<>();
//                map.put("dbname", ShareUserInfo.getDbName(this));
//                map.put("billid", billid);
                presenter.post(1, ServerURL.BILLDETAIL, mParmMap);
                break;
            case 1:
                mGoodsOrOverviewDatas = mGson.fromJson((String) data,
                        new TypeToken<List<GoodsOrOverviewData>>() {
                        }.getType());
                mAdapter.setList(mGoodsOrOverviewDatas);
                //获取文件列表
                Map map = new ArrayMap();
                map.put("dbname", ShareUserInfo.getDbName(this));
                map.put("attcode", "WXDJ");
                map.put("billid", billid);
                map.put("curpage", "0");
                map.put("pagesize", "100");
                presenter.post(2, "attfilelist", map);
                break;
            case 2:
                attfilesList = mGson.fromJson((String) data,
                        new TypeToken<List<Attfiles>>() {
                        }.getType());
                if (attfilesList != null && attfilesList.size() > 0) {
                    saveFile();

                }
                break;
            case 3:

                List<RqShlbData> shlb = mGson.fromJson((String) data,
                        new TypeToken<List<RqShlbData>>() {
                        }.getType());
                Map smap = new ArrayMap<>();
                smap.put("dbname", ShareUserInfo.getDbName(this));
                smap.put("tabname", "tb_servicereg");
                smap.put("pkvalue", billid);
                smap.put("levels", shlb.get(0).getLevels()+"");
                smap.put("opid", ShareUserInfo.getUserId(this));

                switch (master.getShzt()) {//审核状态设置,审核状态(0未审 1已审 2 审核中)
                    case 0://未审
                        smap.put("shzt", "1");
                        presenter.post(4, "billsh", smap);
                        break;
                    case 1://已审
                        smap.put("shzt", "0");
                        presenter.post(5, "billsh", smap);
                        break;
                    case 2://审核中

                        break;
                }

                break;
            case 4:
                LogUtils.e(data.toString());
                if(data.toString().equals("")) {
                    btSh.setText("弃审");
                    master.setShzt(1);
                }else
                    showShortToast(data.toString());
                break;
            case 5:
                if(data.toString().equals("")) {
                    btSh.setText("审核");
                    master.setShzt(0);
                }else
                    showShortToast(data.toString());
                break;
            case 6:
                if (data.toString().equals("")) {
                    finish();
                }else
                    showShortToast(data.toString());
                break;
        }


    }

    private void saveFile() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 0; i < attfilesList.size(); i++) {
                        FileUtils.decoderBase64File(attfilesList.get(i).getXx(), mActivity, FileUtils.getPath(mActivity,"AZDJ/", billid + attfilesList.get(i).getFilenames()), Context.MODE_PRIVATE);
                        LogUtils.e("baocun");
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
                        mFileAdapter.setList(attfilesList);

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
}
