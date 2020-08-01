package com.cr.activity.jxc.xsgl.xsdd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.alibaba.fastjson.JSON;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.JxcXzspActivity;
import com.cr.activity.jxc.KtXzspData;
import com.cr.activity.jxc.SpxqCkActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcActivity;
import com.cr.activity.jxc.spxq.SpxqActivity;
import com.cr.activity.request.XsddDetailRequestData;
import com.cr.activity.request.XsddMasterRequestData;
import com.cr.activity.response.XsddDetailResponseData;
import com.cr.activity.response.XsddMasterResponseData;
import com.cr.tools.FigureTools;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.KtXzfplxActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.actiity.project.ChoiceProjectActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.ChooseGoodsData;
import com.update.utils.DateUtil;
import com.update.utils.EditTextHelper;
import com.update.utils.LogUtils;
import com.update.viewholder.ViewHolderFactory;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 销售订单
 */
public class XsddActivity extends BaseActivity {

    @BindView(R.id.sv_add)
    NestedScrollView svAdd;

    public static Intent getMyIntent(Activity activity, String billid) {
        Intent intent = new Intent(activity, XsddActivity.class);
        intent.putExtra("billid", billid);
        return intent;

    }

    @BindView(R.id.et_bh)
    EditText etBh;
    @BindView(R.id.iv_shzt)
    ImageView ivShzt;
    @BindView(R.id.ll_bh)
    LinearLayout llBh;
    @BindView(R.id.v_bh)
    View vBh;
    @BindView(R.id.ib_fh)
    ImageButton ibFh;
    @BindView(R.id.ib_bc)
    ImageButton ibBc;
    @BindView(R.id.et_kh)
    EditText etKh;
    @BindView(R.id.et_lxr)
    EditText etLxr;
    @BindView(R.id.et_lxdh)
    EditText etLxdh;
    @BindView(R.id.et_jhdz)
    EditText etJhdz;
    @BindView(R.id.et_fplx)
    EditText etFplx;
    @BindView(R.id.et_xgxm)
    EditText etXgxm;
    @BindView(R.id.et_jhrq)
    EditText etJhrq;
    @BindView(R.id.et_zjzh)
    EditText etZjzh;
    @BindView(R.id.et_ysje)
    EditText etYsje;
    @BindView(R.id.tv_spsl)
    TextView tvSpsl;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.ll_xzsp)
    LinearLayout llXzsp;
    @BindView(R.id.rcv_xzsp)
    RecyclerView rcvXzsp;
    @BindView(R.id.et_hjje)
    EditText etHjje;
    @BindView(R.id.et_djrq)
    EditText etDjrq;
    @BindView(R.id.et_bm)
    EditText etBm;
    @BindView(R.id.et_ywy)
    EditText etYwy;
    @BindView(R.id.et_zdr)
    EditText etZdr;
    @BindView(R.id.ll_zdr)
    LinearLayout llZdr;
    @BindView(R.id.et_bzxx)
    EditText etBzxx;
    @BindView(R.id.ll_sh_sd)
    LinearLayout llShSd;

    /**
     * 是否可编辑
     */
    private boolean isCanEdit;

    XsddMasterRequestData mMasterRequestData;
    List<XsddDetailRequestData> mDetailRequestDataList;

    List<XsddDetailResponseData> mXsddDetailResponseData;
    /**
     * 税率
     */
    private String mTaxrate;
    /**
     * 单位类型名称
     */
    private String mUnityTypename;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        /**
         * 默认可编辑
         */
        isCanEdit = true;
        mMasterRequestData = new XsddMasterRequestData();
        presenter = new BaseP(this, this);


    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_xsdd;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        /**
         * 判断是新增单据还是查看单据详情
         */
        if (getIntent().hasExtra("billid")) {
            getBillMaster();
            llBh.setVisibility(View.VISIBLE);
            vBh.setVisibility(View.VISIBLE);
            llZdr.setVisibility(View.VISIBLE);
            llShSd.setVisibility(View.VISIBLE);
        } else {
            llBh.setVisibility(View.GONE);
            vBh.setVisibility(View.GONE);
            llZdr.setVisibility(View.GONE);
            llShSd.setVisibility(View.GONE);
            setDefaultData();
        }
        /* 选择商品集合信息处理 */
        rcvXzsp.setLayoutManager(new LinearLayoutManager(this));
        rcvXzsp.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ChooseGoodsResultHolder, XsddDetailResponseData>(mXsddDetailResponseData, false) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getChooseGoodsResultHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.ChooseGoodsResultHolder holder, final XsddDetailResponseData data) {

                holder.tvGoodsInformation.setText(data.getGoodscode() + "  " + data.getGoodsname() + "  " + data.getSpecs() + "  " + data.getModel());
                holder.tvRegistrationNumber.setText("￥：" + FigureTools.sswrFigure(data.getTaxunitprice())
                        + "*" + data.getUnitqty() + data.getUnitname()
                        + "        " + "￥" + FigureTools.sswrFigure(Double.parseDouble(data.getAmount())));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isCanEdit) {
                            startActivityForResult(SpxqActivity.getMyIntent(mActivity, new Gson().toJson(data), etFplx.getText().toString(), holder.getLayoutPosition()), 17);
                        } else {
                            startActivity(new Intent(mActivity, SpxqCkActivity.class).putExtra("data", new Gson().toJson(data)));
                        }
                    }
                });

            }

        });

    }

    /**
     * 设置默认数据
     */
    private void setDefaultData() {
        mTaxrate = "0";
        mMasterRequestData.setBilltypeid("1");
        etFplx.setText("收据");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        etDjrq.setText(simpleDateFormat.format(new Date()));
        mMasterRequestData.setDepartmentid(ShareUserInfo.getKey(this, "departmentid"));
        etBm.setText(ShareUserInfo.getKey(this, "depname"));
        mMasterRequestData.setExemanid(ShareUserInfo.getKey(this, "empid"));
        etYwy.setText(ShareUserInfo.getKey(this, "opname"));

        EditTextHelper.EditTextEnable(false, etYsje);

    }

    @OnClick({R.id.ib_fh, R.id.ib_bc, R.id.et_kh, R.id.et_lxr, R.id.et_fplx, R.id.et_xgxm, R.id.et_jhrq, R.id.et_zjzh, R.id.iv_scan, R.id.ll_xzsp, R.id.et_djrq, R.id.et_bm, R.id.et_ywy, R.id.bt_sh, R.id.bt_sd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_fh:
                finish();
                break;
            case R.id.ib_bc:
                save();
                break;
            case R.id.et_kh:
                startActivityForResult(CommonXzdwActivity.getMyIntent(mActivity, "1"), 11);
                break;
            case R.id.et_lxr:
                if (TextUtils.isEmpty(mMasterRequestData.getClientid())) {
                    showShortToast("请先选择客户信息");
                    return;
                }
                startActivityForResult(CommonXzlxrActivity.getMyIntent(mActivity, mMasterRequestData.getClientid()), 12);
                break;
            case R.id.et_fplx:
                startActivityForResult(KtXzfplxActivity.Companion.getMyIntent(mActivity, "1"), 13);
                break;
            case R.id.et_xgxm:
                if (TextUtils.isEmpty(mMasterRequestData.getClientid())) {
                    showShortToast("请先选择客户信息");
                    return;
                }
                startActivityForResult(new Intent(mActivity, ChoiceProjectActivity.class)
                        .putExtra("clientid", mMasterRequestData.getClientid())
                        .putExtra("clientname", etKh.getText().toString())
                        .putExtra("dwmc", true)
                        .putExtra("typesname", mUnityTypename), 23);

                break;
            case R.id.et_jhrq:
                selectTime("jhrq");
                break;
            case R.id.et_zjzh:
                startActivityForResult(CommonXzzdActivity.getMyIntent(mActivity, "BANK", true), 15);
                break;
            case R.id.ll_xzsp:
                startActivityForResult(JxcXzspActivity.getMyIntent(mActivity, "XSDD", "0", etFplx.getText().toString().equals("收据"), mTaxrate, "tb_sorder"), 16);
                break;
            case R.id.iv_scan:
                break;
            case R.id.et_djrq:
                selectTime("djrq");
                break;
            case R.id.et_bm:
                startActivityForResult(ChooseDepartmentActivity.getMyIntent(mActivity), 18);
                break;
            case R.id.et_ywy:
                if (TextUtils.isEmpty(mMasterRequestData.getDepartmentid())) {
                    showShortToast("请先选择部门");
                    return;
                }
                startActivityForResult(SelectSalesmanActivity.getMyIntent(mActivity, mMasterRequestData.getDepartmentid()), 19);
                break;
            case R.id.bt_sh:
                Intent intent = new Intent();
                intent.putExtra("tb", "tb_sorder");
                intent.putExtra("opid", mMasterRequestData.getOpid());
                intent.putExtra("billid", mMasterRequestData.getBillid());
                intent.setClass(mActivity, JxcCgglCgddShlcActivity.class);
                startActivityForResult(intent, 20);
                break;
            case R.id.bt_sd:
                if (!ShareUserInfo.getKey(mActivity, "sc").equals("1")) {
                    showShortToast("你没有该权限，请向管理员申请权限！");
                    return;
                }
                new AlertDialog.Builder(mActivity)
                        .setTitle("确定要删除当前记录吗？")
                        .setNegativeButton("删除",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        delete();
                                    }
                                }).setPositiveButton("取消", null).show();

                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11://客户
                /**
                 * 更换客户，商品需要重新选择
                 */
//                if (mXsddDetailResponseData != null) {
//                    mXsddDetailResponseData.clear();
//                    mAdapter.setList(mXsddDetailResponseData);
//                    calculationTotalAmount();
//                }
                etKh.setText(data.getStringExtra("name"));
                mMasterRequestData.setClientid(data.getStringExtra("id"));
                mUnityTypename = (data.getStringExtra("typesname"));
                etLxr.setText(data.getStringExtra("lxrname"));
                mMasterRequestData.setLinkmanid(data.getStringExtra("lxrid"));
                etLxdh.setText(data.getStringExtra("phone"));
                break;
            case 12://选择联系人
                etLxr.setText(data.getStringExtra("name"));
                mMasterRequestData.setLinkmanid(data.getStringExtra("id"));
                etLxdh.setText(data.getStringExtra("phone"));
                break;
            case 13://选择发票类型
                mMasterRequestData.setBilltypeid(data.getStringExtra("id"));
                etFplx.setText(data.getStringExtra("name"));
                mTaxrate = data.getStringExtra("taxrate");
                if (mXsddDetailResponseData != null) {
                    for (int i = 0; i < mXsddDetailResponseData.size(); i++) {
                        mXsddDetailResponseData.get(i).setTaxrate(mTaxrate);
                        double taxunitprice = Double.parseDouble(mXsddDetailResponseData.get(i).getUnitprice()) * (Double.parseDouble(mXsddDetailResponseData.get(i).getTaxrate()) / 100 + 1);
                        double amount = taxunitprice * Double.parseDouble(mXsddDetailResponseData.get(i).getUnitqty());
                        mXsddDetailResponseData.get(i).setTaxunitprice(FigureTools.sswrFigure(taxunitprice));
                        mXsddDetailResponseData.get(i).setAmount(FigureTools.sswrFigure(amount));
                    }
                    mAdapter.setList(mXsddDetailResponseData);
                    calculationTotalAmount();
                }
                break;
            case 15://选择资金账户
                String bankid = data.getStringExtra("id");
                if ("-1".equals(bankid)) {
                    mMasterRequestData.setBankid("");
                    etZjzh.setText("");
                    etYsje.setText("");
                    EditTextHelper.EditTextEnable(false, etYsje);
                } else {
                    mMasterRequestData.setBankid(bankid);
                    etZjzh.setText(data.getStringExtra("dictmc"));
                    EditTextHelper.EditTextEnable(true, etYsje);
                }
                break;
            case 16://选择商品
                LogUtils.e(data.getStringExtra("data"));
                List<KtXzspData> mXzspDataList = new Gson().fromJson(data.getExtras().getString("data"), new TypeToken<List<KtXzspData>>() {
                }.getType());
                if (mXsddDetailResponseData == null) {
                    mXsddDetailResponseData = new ArrayList<>();
                }
                for (int i = 0; i < mXzspDataList.size(); i++) {
                    XsddDetailResponseData xsddDetailRequestData = new XsddDetailResponseData(mXzspDataList.get(i));
                    mXsddDetailResponseData.add(xsddDetailRequestData);
                }
                mAdapter.setList(mXsddDetailResponseData);
                calculationTotalAmount();
                break;
            case 17://商品编辑
                String gson = data.getStringExtra("data");
                int position = data.getIntExtra("position", 0);
                mXsddDetailResponseData.remove(position);
                if (!TextUtils.isEmpty(gson)) {
                    XsddDetailResponseData xsddDetailRequestData = new Gson().fromJson(data.getExtras().getString("data"), new TypeToken<XsddDetailResponseData>() {
                    }.getType());
                    mXsddDetailResponseData.add(position, xsddDetailRequestData);
                }
                mAdapter.setList(mXsddDetailResponseData);
                calculationTotalAmount();
                break;
            case 18://选择部门
                mMasterRequestData.setDepartmentid(data.getStringExtra("CHOICE_RESULT_ID"));
                etBm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mMasterRequestData.setExemanid("");
                etYwy.setText("");
                break;
            case 19://选择业务员
                mMasterRequestData.setExemanid(data.getStringExtra("CHOICE_RESULT_ID"));
                etYwy.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 20:
                getBillMaster();
                setResult(Activity.RESULT_OK);
                break;
            case 23://相关项目选择
                etXgxm.setText(data.getStringExtra("title"));
                mMasterRequestData.setProjectid(data.getStringExtra("projectid"));
                break;
        }

    }

    /**
     * 计算合计金额
     */
    private void calculationTotalAmount() {
        double zje = 0.0;
        for (int i = 0; i < mXsddDetailResponseData.size(); i++) {
            zje += Double.parseDouble(mXsddDetailResponseData.get(i).getAmount());
        }
        etHjje.setText("￥" + FigureTools.sswrFigure(zje));
        tvSpsl.setText("共选择了" + mXsddDetailResponseData.size() + "商品");

    }

    /**
     * 时间选择器
     *
     * @param type
     */
    public void selectTime(final String type) {
        TimePickerView mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setTime(new Date());
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                switch (type) {
                    case "jhrq":
                        etJhrq.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        mMasterRequestData.setTakedate(etJhrq.getText().toString());
                        break;
                    case "djrq":
                        etDjrq.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        mMasterRequestData.setBilldate(etDjrq.getText().toString());
                        break;
                }

            }
        });
        mTimePickerView.show();
    }


    /**
     * 单据保存
     */
    private void save() {
        if (TextUtils.isEmpty(etKh.getText().toString())) {
            showShortToast("请选择客户");
            return;
        }
        if (mXsddDetailResponseData == null || mXsddDetailResponseData.size() == 0) {
            showShortToast("请选择商品");
            return;
        }
        if (TextUtils.isEmpty(etDjrq.getText().toString())) {
            showShortToast("请选择单据日期");
            return;
        }
        if (TextUtils.isEmpty(mMasterRequestData.getDepartmentid())) {
            showShortToast("请先选择部门");
            return;
        }
        if (!TextUtils.isEmpty(etJhrq.getText().toString()) && DateUtil.StringTolongDate(etDjrq.getText().toString(), "yy-MM-dd") > DateUtil.StringTolongDate(etJhrq.getText().toString(), "yy-MM-dd")) {
            showShortToast("交货日期不能早于单据日期");
            return;
        }

        if (!TextUtils.isEmpty(mMasterRequestData.getBankid()) && TextUtils.isEmpty(etYsje.getText().toString())) {
            showShortToast("预收金额不能为空");
            return;
        }
        if (!TextUtils.isEmpty(mMasterRequestData.getBankid()) && Double.parseDouble(etYsje.getText().toString()) <= 0) {
            showShortToast("预收金额不能小于0");
            return;
        }
        mMasterRequestData.setPhone(etLxdh.getText().toString());
        mMasterRequestData.setShipto(etJhdz.getText().toString());
        mMasterRequestData.setSuramt(etYsje.getText().toString());
        mMasterRequestData.setAmount(etHjje.getText().toString().replace("￥", ""));
        mMasterRequestData.setMemo(etBzxx.getText().toString());
        mMasterRequestData.setOpid(ShareUserInfo.getUserId(mActivity));

        List<XsddMasterRequestData> masterRequestDataList = new ArrayList<>();
        masterRequestDataList.add(mMasterRequestData);
        mDetailRequestDataList = new ArrayList<>();
        for (int i = 0; i < mXsddDetailResponseData.size(); i++) {
            XsddDetailRequestData xsddDetailRequestData = new XsddDetailRequestData(mXsddDetailResponseData.get(i));
            mDetailRequestDataList.add(xsddDetailRequestData);
        }
//        mDetailRequestDataList = new ArrayList<>();
//        for (int i = 0; i < mXzspDataList.size(); i++) {
//            XsddDetailRequestData xsddDetailRequestData = new XsddDetailRequestData();
//            xsddDetailRequestData.setGoodsid(mXzspDataList.get(i).getGoodsid() + "");
//            xsddDetailRequestData.setUnitid(mXzspDataList.get(i).getUnitid() + "");
//            xsddDetailRequestData.setUnitprice(mXzspDataList.get(i).getAprice() + "");
//            xsddDetailRequestData.setUnitqty(mXzspDataList.get(i).getNumber() + "");
//            xsddDetailRequestData.setAmount((Double.parseDouble(mXzspDataList.get(i).getTaxunitprice()) * mXzspDataList.get(i).getNumber()) + "");
//            xsddDetailRequestData.setBatchcode(mXzspDataList.get(i).getCpph());
//            xsddDetailRequestData.setProduceddate(mXzspDataList.get(i).getScrq());
//            xsddDetailRequestData.setValiddate(mXzspDataList.get(i).getYxqz());
//            xsddDetailRequestData.setBatchrefid(mXzspDataList.get(i).getBatchrefid());
//            xsddDetailRequestData.setTaxrate(mXzspDataList.get(i).getTaxrate());
//            xsddDetailRequestData.setTaxunitprice(mXzspDataList.get(i).getTaxunitprice());
//            xsddDetailRequestData.setMemo(mXzspDataList.get(i).getMemo());
//            mDetailRequestDataList.add(xsddDetailRequestData);
//        }


        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("tabname", "tb_porder");
        parmMap.put("parms", "XSDD");
        parmMap.put("master", JSON.toJSONString(masterRequestDataList));
        parmMap.put("detail", JSON.toJSONString(mDetailRequestDataList));
        presenter.post(0, "billsavenew", parmMap);
    }


    private void getBillMaster() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("parms", "XSDD");
        parmMap.put("billid", getIntent().getStringExtra("billid"));
        presenter.post(1, ServerURL.BILLMASTER, parmMap);
    }

    /**
     * 获取从表内容
     */
    private void getBillDetail() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("parms", "XSDD");
        parmMap.put("billid", getIntent().getStringExtra("billid"));
        presenter.post(2, ServerURL.BILLDETAIL, parmMap);
    }


    private void delete() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("opid", ShareUserInfo.getUserId(mActivity));
        parmMap.put("tabname", "tb_porder");
        parmMap.put("pkvalue", getIntent().getStringExtra("billid"));
        presenter.post(3, ServerURL.BILLDELMASTER, parmMap);
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
        LogUtils.e(result);
        switch (requestCode) {
            case 0:
                if (!TextUtils.isEmpty(result) && result.contains("false")) {
                    showShortToast("保存失败" + result);
                } else {
                    showShortToast("保存成功");
                }
                break;
            case 1:
                getBillDetail();
                List<XsddMasterResponseData> list = new Gson().fromJson(result, new TypeToken<List<XsddMasterResponseData>>() {
                }.getType());
                XsddMasterResponseData xsddMasterResponseData = list.get(0);
                switch (xsddMasterResponseData.getShzt()) {
                    case 0:
                        ivShzt.setBackgroundResource(R.drawable.wsh);
                        ibBc.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        ivShzt.setBackgroundResource(R.drawable.ysh);
                        ibBc.setVisibility(View.GONE);
                        break;
                    case 2:
                        ivShzt.setBackgroundResource(R.drawable.shz);
                        ibBc.setVisibility(View.VISIBLE);
                        break;
                }
                mMasterRequestData.setBillid(xsddMasterResponseData.getSorderid() + "");
                mMasterRequestData.setCode(xsddMasterResponseData.getCode());
                etBh.setText(xsddMasterResponseData.getCode());
                mMasterRequestData.setClientid(xsddMasterResponseData.getClientid() + "");
                etKh.setText(xsddMasterResponseData.getCname());
                mMasterRequestData.setLinkmanid(xsddMasterResponseData.getLinkmanid());
                etLxr.setText(xsddMasterResponseData.getContator());
                mMasterRequestData.setPhone(xsddMasterResponseData.getPhone());
                etLxdh.setText(xsddMasterResponseData.getPhone());
                mMasterRequestData.setShipto(xsddMasterResponseData.getShipto());
                etJhdz.setText(xsddMasterResponseData.getShipto());
                mMasterRequestData.setBilltypeid(xsddMasterResponseData.getBilltypeid() + "");
                etFplx.setText(xsddMasterResponseData.getBilltypename());
                mTaxrate = xsddMasterResponseData.getTaxrate();//设置税率
                mMasterRequestData.setProjectid(xsddMasterResponseData.getProjectid());
                etXgxm.setText(xsddMasterResponseData.getProjectname());
                mMasterRequestData.setTakedate(xsddMasterResponseData.getTakedate());
                etJhrq.setText(xsddMasterResponseData.getTakedate());
                mMasterRequestData.setBankid(xsddMasterResponseData.getBankid() + "");
                etZjzh.setText(xsddMasterResponseData.getBankname());
                mMasterRequestData.setSuramt(xsddMasterResponseData.getSuramt() + "");
                if (TextUtils.isEmpty(mMasterRequestData.getBankid())) {
                    EditTextHelper.EditTextEnable(false, etYsje);
                } else {
                    etYsje.setText(xsddMasterResponseData.getSuramt() + "");
                }
                mMasterRequestData.setAmount(xsddMasterResponseData.getAmount() + "");
                etHjje.setText(xsddMasterResponseData.getAmount() + "");
                mMasterRequestData.setBilldate(xsddMasterResponseData.getBilldate());
                etDjrq.setText(xsddMasterResponseData.getBilldate());
                mMasterRequestData.setDepartmentid(xsddMasterResponseData.getDepartmentid() + "");
                etBm.setText(xsddMasterResponseData.getDepname());
                mMasterRequestData.setExemanid(xsddMasterResponseData.getExemanid() + "");
                etYwy.setText(xsddMasterResponseData.getEmpname());
                etZdr.setText(xsddMasterResponseData.getOpname());
                mMasterRequestData.setMemo(xsddMasterResponseData.getMemo());
                etBzxx.setText(xsddMasterResponseData.getMemo());
                mMasterRequestData.setOpid(xsddMasterResponseData.getOpid() + "");
                switch (xsddMasterResponseData.getReadonly()) {
                    case 0:
                        ibBc.setVisibility(View.VISIBLE);
                        isCanEdit = true;
                        break;
                    case 1:
                        ibBc.setVisibility(View.GONE);
                        isCanEdit = false;
                        break;
                }
                break;
            case 2:
                LogUtils.e(result);
                mXsddDetailResponseData = new Gson().fromJson(result, new TypeToken<List<XsddDetailResponseData>>() {
                }.getType());
                LogUtils.e(JSON.toJSONString(mXsddDetailResponseData));
                mAdapter.setList(mXsddDetailResponseData);

                if (mXsddDetailResponseData != null) {
                    tvSpsl.setText("共选择了" + mXsddDetailResponseData.size() + "商品");
                }
                break;
            case 3://删单
                if (TextUtils.isEmpty(result)) {
                    showShortToast("操作成功");
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    showShortToast("操作失败" + result.substring(5));
                }
                break;
        }

    }

}
