package com.cr.activity.jxc.cggl.cgdd;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.alibaba.fastjson.JSON;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.JxcXzspActivity;
import com.cr.activity.jxc.KtXzspData;
import com.cr.activity.jxc.SpxqCkActivity;
import com.cr.activity.jxc.spxq.SpxqActivity;
import com.cr.activity.request.CgddDetailRequestData;
import com.cr.activity.request.CgddMasterRequestData;
import com.cr.activity.request.XsddDetailRequestData;
import com.cr.activity.request.XsddMasterRequestData;
import com.cr.activity.response.CgddDetailResponseData;
import com.cr.activity.response.CgddMasterResponseData;
import com.cr.tools.FigureTools;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.KtXzfplxActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.utils.DateUtil;
import com.update.utils.LogUtils;
import com.update.viewholder.ViewHolderFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 采购订单
 */
public class CgddActivity extends BaseActivity {


    @BindView(R.id.ll_gldj_xx)
    LinearLayout llGldjXx;
    @BindView(R.id.nsv_view)
    NestedScrollView nsvView;

    public static Intent getMyIntent(Activity activity, String billid) {
        Intent intent = new Intent(activity, CgddActivity.class);
        intent.putExtra("billid", billid);
        return intent;

    }

    @BindView(R.id.ib_save)
    ImageButton ibSave;
    @BindView(R.id.tb_gldj)
    ToggleButton tbGldj;
    @BindView(R.id.ll_gldj)
    LinearLayout llGldj;
    @BindView(R.id.et_bh)
    EditText etBh;
    @BindView(R.id.ll_bh)
    LinearLayout llBh;
    @BindView(R.id.v_bh)
    View vBh;
    @BindView(R.id.iv_shzt)
    ImageView ivShzt;
    @BindView(R.id.et_gys)
    EditText etGys;
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
    @BindView(R.id.et_yfje)
    EditText etYfje;
    @BindView(R.id.tv_xzspsl)
    TextView tvXzspsl;
    @BindView(R.id.rcv_xzsp)
    RecyclerView rcvXzsp;
    @BindView(R.id.et_hjje)
    EditText etHjje;
    @BindView(R.id.et_djrq)
    EditText etDjrq;
    @BindView(R.id.et_bm)
    EditText etBm;
    @BindView(R.id.et_jbr)
    EditText etJbr;
    @BindView(R.id.zdr_edittext)
    EditText zdrEdittext;
    @BindView(R.id.et_bzxx)
    EditText etBzxx;
    @BindView(R.id.ll_sh_sd)
    LinearLayout llShSd;


    CgddMasterRequestData mMasterRequestData;
    /**
     * 税率
     */
    private String mTaxrate;
    /**
     * 是否可编辑
     */
    private boolean isCanEdit;

    List<CgddDetailResponseData> mDetailResponseDataList;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mMasterRequestData = new CgddMasterRequestData();
        /**
         * 默认可编辑
         */
        isCanEdit = true;
        presenter = new BaseP(this, this);

    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_cgdd;
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
            llGldj.setVisibility(View.GONE);
        } else {
            llBh.setVisibility(View.GONE);
            vBh.setVisibility(View.GONE);
            llGldj.setVisibility(View.VISIBLE);
            setDefaultData();
        }

        tbGldj.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                llGldjXx.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                nsvView.setVisibility(!isChecked ? View.VISIBLE : View.GONE);
            }
        });

        /* 选择商品集合信息处理 */
        rcvXzsp.setLayoutManager(new LinearLayoutManager(this));
        rcvXzsp.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ChooseGoodsResultHolder, CgddDetailResponseData>(mDetailResponseDataList, false) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getChooseGoodsResultHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.ChooseGoodsResultHolder holder, final CgddDetailResponseData data) {

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

    }


    @OnClick({R.id.ll_ck, R.id.ll_xzxsdd, R.id.ib_fh, R.id.ib_save, R.id.ll_gys, R.id.ll_lxr, R.id.ll_fplx_xz, R.id.ll_xgxm, R.id.ll_jhrq, R.id.ll_zjzh_xz, R.id.ll_xzsp, R.id.ll_djrq_xz, R.id.ll_bm_xz, R.id.ll_jbr, R.id.bt_sh, R.id.bt_sd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_fh:
                finish();
                break;
            case R.id.ib_save:
                save();
                break;
            case R.id.ll_ck:
                break;
            case R.id.ll_xzxsdd:
                break;
            case R.id.ll_gys:
                startActivityForResult(CommonXzdwActivity.getMyIntent(mActivity, "2"), 11);
                break;
            case R.id.ll_lxr:
                if (TextUtils.isEmpty(mMasterRequestData.getClientid())) {
                    showShortToast("请先选择客户信息");
                    return;
                }
                startActivityForResult(CommonXzlxrActivity.getMyIntent(mActivity, mMasterRequestData.getClientid()), 12);

                break;
            case R.id.ll_fplx_xz:
                startActivityForResult(KtXzfplxActivity.Companion.getMyIntent(mActivity, "0"), 13);

                break;
            case R.id.ll_xgxm:
                break;
            case R.id.ll_jhrq:
                selectTime("jhrq");
                break;
            case R.id.ll_zjzh_xz:
                startActivityForResult(CommonXzzdActivity.getMyIntent(mActivity, "BANK"), 15);

                break;
            case R.id.ll_xzsp:
                startActivityForResult(JxcXzspActivity.getMyIntent(mActivity, "CGDD", "0", etFplx.getText().toString() == "收据", mTaxrate, "tb_sorder"), 16);

                break;
            case R.id.ll_djrq_xz:
                selectTime("djrq");
                break;
            case R.id.ll_bm_xz:
                startActivityForResult(ChooseDepartmentActivity.getMyIntent(mActivity), 18);

                break;
            case R.id.ll_jbr:
                if (TextUtils.isEmpty(mMasterRequestData.getDepartmentid())) {
                    showShortToast("请先选择部门");
                    return;
                }
                startActivityForResult(SelectSalesmanActivity.getMyIntent(mActivity, mMasterRequestData.getDepartmentid()), 19);

                break;
            case R.id.bt_sh:
                break;
            case R.id.bt_sd:
                delete();
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
                if (mDetailResponseDataList != null) {
                    mDetailResponseDataList.clear();
                }
                etGys.setText(data.getStringExtra("name"));
                mMasterRequestData.setClientid(data.getStringExtra("id"));
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
                break;
            case 15://选择资金账户
                mMasterRequestData.setBankid(data.getStringExtra("id"));
                etZjzh.setText(data.getStringExtra("dictmc"));
                break;
            case 16://选择商品
                LogUtils.e(data.getStringExtra("data"));
                List<KtXzspData> mXzspDataList = new Gson().fromJson(data.getExtras().getString("data"), new TypeToken<List<KtXzspData>>() {
                }.getType());
                if (mDetailResponseDataList == null) {
                    mDetailResponseDataList = new ArrayList<>();
                }
                for (int i = 0; i < mXzspDataList.size(); i++) {
                    CgddDetailResponseData xsddDetailRequestData = new CgddDetailResponseData(mXzspDataList.get(i));
                    mDetailResponseDataList.add(xsddDetailRequestData);
                }
                mAdapter.setList(mDetailResponseDataList);
                calculationTotalAmount();
                break;
            case 17://商品编辑
                String gson = data.getStringExtra("data");
                int position = data.getIntExtra("position", 0);
                mDetailResponseDataList.remove(position);
//                if (!TextUtils.isEmpty(gson)) {
//                    XsddDetailResponseData xsddDetailRequestData = new Gson().fromJson(data.getExtras().getString("data"), new TypeToken<XsddDetailResponseData>() {
//                    }.getType());
//                    mXsddDetailResponseData.add(position, xsddDetailRequestData);
//                }
                mAdapter.setList(mDetailResponseDataList);
//                calculationTotalAmount();
                break;
            case 18://选择部门
                mMasterRequestData.setDepartmentid(data.getStringExtra("CHOICE_RESULT_ID"));
                etBm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mMasterRequestData.setExemanid("");
                etJbr.setText("");
                break;
            case 19://选择业务员
                mMasterRequestData.setExemanid(data.getStringExtra("CHOICE_RESULT_ID"));
                etJbr.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
        }
    }
    /**
     * 计算合计金额
     */
    private void calculationTotalAmount() {
        double zje = 0.0;
        for (int i = 0; i < mDetailResponseDataList.size(); i++) {
            zje += Double.parseDouble(mDetailResponseDataList.get(i).getAmount());
        }
        etHjje.setText("￥" + FigureTools.sswrFigure(zje));
        tvXzspsl.setText("共选择了" + mDetailResponseDataList.size() + "商品");

    }


    /**
     * 单据保存
     */
    private void save() {
        if (TextUtils.isEmpty(etGys.getText().toString())) {
            showShortToast("请选择客户");
            return;
        }
        if (mDetailResponseDataList == null || mDetailResponseDataList.size() == 0) {
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
        mMasterRequestData.setPhone(etLxdh.getText().toString());
        mMasterRequestData.setBillto(etJhdz.getText().toString());
        mMasterRequestData.setSuramt(etYfje.getText().toString());
        mMasterRequestData.setAmount(etHjje.getText().toString().replace("￥",""));
        mMasterRequestData.setMemo(etBzxx.getText().toString());
        mMasterRequestData.setOpid(ShareUserInfo.getUserId(mActivity));

        List<CgddMasterRequestData> masterRequestDataList = new ArrayList<>();
        masterRequestDataList.add(mMasterRequestData);
        List<CgddDetailRequestData> detailRequestDataList = new ArrayList<>();
        for (int i = 0; i < mDetailResponseDataList.size(); i++) {
            CgddDetailRequestData xsddDetailRequestData = new CgddDetailRequestData(mDetailResponseDataList.get(i));
            detailRequestDataList.add(xsddDetailRequestData);
        }

        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("tabname", "tb_porder");
        parmMap.put("parms", "CGDD");
        parmMap.put("master", JSON.toJSONString(masterRequestDataList));
        parmMap.put("detail", JSON.toJSONString(detailRequestDataList));
        presenter.post(0, "billsavenew", parmMap);
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
                        mMasterRequestData.setRevdate(etJhrq.getText().toString());
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

    private void getBillMaster() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("parms", "CGDD");
        parmMap.put("billid", getIntent().getStringExtra("billid"));
        presenter.post(1, ServerURL.BILLMASTER, parmMap);
    }


    /**
     * 获取从表内容
     */
    private void getBillDetail() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("parms", "CGDD");
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
                showShortToast("保存成功");
                break;
            case 1:
                getBillDetail();
                List<CgddMasterResponseData> list = new Gson().fromJson(result, new TypeToken<List<CgddMasterResponseData>>() {
                }.getType());
                CgddMasterResponseData masterResponseData = list.get(0);
                switch (masterResponseData.getShzt()) {
                    case 0:
                        ivShzt.setBackgroundResource(R.drawable.wsh);
                        break;
                    case 1:
                        ivShzt.setBackgroundResource(R.drawable.ysh);
                        break;
                    case 2:
                        ivShzt.setBackgroundResource(R.drawable.shz);
                        break;
                }
                mMasterRequestData.setBillid(masterResponseData.getOrderid() + "");
                mMasterRequestData.setCode(masterResponseData.getCode());
                etBh.setText(masterResponseData.getCode());
                mMasterRequestData.setClientid(masterResponseData.getClientid() + "");
                etGys.setText(masterResponseData.getCname());
                mMasterRequestData.setLinkmanid(masterResponseData.getLinkmanid());
                etLxr.setText(masterResponseData.getContator());
                mMasterRequestData.setPhone(masterResponseData.getPhone());
                etLxdh.setText(masterResponseData.getPhone());
                mMasterRequestData.setBillto(masterResponseData.getBillto());
                etJhdz.setText(masterResponseData.getBillto());
                mMasterRequestData.setBilltypeid(masterResponseData.getBilltypeid() + "");
                etFplx.setText(masterResponseData.getBilltypename());
                mTaxrate = masterResponseData.getTaxrate();//设置税率
                mMasterRequestData.setProjectid(masterResponseData.getProjectid());
                etXgxm.setText(masterResponseData.getProjectname());
                mMasterRequestData.setRevdate(masterResponseData.getRevdate());
                etJhrq.setText(masterResponseData.getRevdate());
                mMasterRequestData.setBankid(masterResponseData.getBankid() + "");
                etZjzh.setText(masterResponseData.getBankname());
                mMasterRequestData.setSuramt(masterResponseData.getSuramt() + "");
                etYfje.setText(masterResponseData.getSuramt() + "");
                mMasterRequestData.setAmount(masterResponseData.getAmount() + "");
                etHjje.setText(masterResponseData.getAmount() + "");
                mMasterRequestData.setBilldate(masterResponseData.getBilldate());
                etDjrq.setText(masterResponseData.getBilldate());
                mMasterRequestData.setDepartmentid(masterResponseData.getDepartmentid() + "");
                etBm.setText(masterResponseData.getDepname());
                mMasterRequestData.setExemanid(masterResponseData.getExemanid() + "");
                etJbr.setText(masterResponseData.getEmpname());
                zdrEdittext.setText(masterResponseData.getOpname());
                mMasterRequestData.setMemo(masterResponseData.getMemo());
                etBzxx.setText(masterResponseData.getMemo());
                mMasterRequestData.setOpid(masterResponseData.getOpid() + "");
                switch (masterResponseData.getReadonly()) {
                    case 0:
                        ibSave.setVisibility(View.VISIBLE);
                        isCanEdit = true;
                        break;
                    case 1:
                        ibSave.setVisibility(View.GONE);
                        isCanEdit = false;
                        break;
                }
                break;
            case 2:
                LogUtils.e(result);
                mDetailResponseDataList = new Gson().fromJson(result, new TypeToken<List<CgddDetailResponseData>>() {
                }.getType());
                LogUtils.e(JSON.toJSONString(mDetailResponseDataList));
                mAdapter.setList(mDetailResponseDataList);

                if (mDetailResponseDataList != null) {
                    tvXzspsl.setText("共选择了" + mDetailResponseDataList.size() + "商品");
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
//        if (result.equals("false")) {
//            LogUtils.e("失败");
//            titlebar.setTvRightEnabled(true);
//            showShortToast(data.toString());
//        } else {
//            LogUtils.e("失败");
//            showShortToast("添加成功");
//            finish();
//        }
    }

}
