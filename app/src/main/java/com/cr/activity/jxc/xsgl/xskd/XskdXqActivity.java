package com.cr.activity.jxc.xsgl.xskd;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cr.RequestAddress;
import com.cr.data.http.response.XskdBillDetailResponseData;
import com.cr.data.http.response.XskdBillMasterResponseData;
import com.cr.http.NetworkRequestDataUtils;
import com.cr.tools.FigureTools;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class XskdXqActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_djbh)
    TextView tvDjbh;
    @BindView(R.id.iv_shzt)
    ImageView ivShzt;
    @BindView(R.id.tv_ckck)
    TextView tvCkck;
    @BindView(R.id.tv_kh)
    TextView tvKh;
    @BindView(R.id.tv_lxr)
    TextView tvLxr;
    @BindView(R.id.et_lxdh)
    EditText etLxdh;
    @BindView(R.id.tv_fplx)
    TextView tvFplx;
    @BindView(R.id.tv_skrq)
    TextView tvSkrq;
    @BindView(R.id.tv_xm)
    TextView tvXm;
    @BindView(R.id.tv_xzspsl)
    TextView tvXzspsl;
    @BindView(R.id.et_khqk)
    EditText etKhqk;
    @BindView(R.id.et_hjje)
    EditText etHjje;
    @BindView(R.id.et_skje)
    EditText etSkje;
    @BindView(R.id.tv_sklx)
    TextView tvSklx;
    @BindView(R.id.tv_zjzh)
    TextView tvZjzh;
    @BindView(R.id.tv_wlgs)
    TextView tvWlgs;
    @BindView(R.id.tv_dszh)
    TextView tvDszh;
    @BindView(R.id.et_dsje)
    EditText etDsje;
    @BindView(R.id.et_skhj)
    EditText etSkhj;
    @BindView(R.id.tv_djrq)
    TextView tvDjrq;
    @BindView(R.id.tv_bm)
    TextView tvBm;
    @BindView(R.id.tv_ywy)
    TextView tvYwy;
    @BindView(R.id.tv_zdr)
    TextView tvZdr;
    @BindView(R.id.et_bzxx)
    EditText etBzxx;
    @BindView(R.id.et_jhdz)
    EditText etJhdz;


    private XskdBillMasterResponseData mMasterResponseData;
    private List<XskdBillDetailResponseData> mDetailResponseDataList;

    public static Intent getMyIntent(Context packageContext, String billid) {
        Intent intent = new Intent(packageContext, XskdXqActivity.class);
        intent.putExtra("billid", billid);
        return intent;
    }


    private String mBillid;

    private Map<String, Object> mRequest;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mBillid = getIntent().getStringExtra("billid");
        mRequest = NetworkRequestDataUtils.getBillMaster(mActivity, "XSKD", mBillid);
        presenter.post(RequestAddress.BILLMASTER_CODE, RequestAddress.BILLMASTER, mRequest);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_xskd_xq;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        titlebar.setTitleText(this, "销售开单");
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
            case RequestAddress.BILLMASTER_CODE:
                LogUtils.d(data.toString());
                mMasterResponseData = JSON.parseArray(data.toString(), XskdBillMasterResponseData.class).get(0);
                presenter.post(RequestAddress.BILLDETAIL_CODE, RequestAddress.BILLDETAIL, mRequest);
                setMasterData();
                break;
            case RequestAddress.BILLDETAIL_CODE:
                LogUtils.d(data.toString());
                mDetailResponseDataList=JSON.parseArray(data.toString(), XskdBillDetailResponseData.class);
                break;
        }
    }


    private void setMasterData() {
        tvDjbh.setText(mMasterResponseData.getCode());
        switch (mMasterResponseData.getShzt()) {
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
        tvCkck.setText(mMasterResponseData.getStorename());
        tvKh.setText(mMasterResponseData.getCname());
        tvLxr.setText(mMasterResponseData.getContator());
        etLxdh.setText(mMasterResponseData.getPhone());
        tvFplx.setText(mMasterResponseData.getBilltypename());
        tvSkrq.setText(mMasterResponseData.getSkrq());
        tvXm.setText(mMasterResponseData.getProjectname());
        etJhdz.setText(mMasterResponseData.getShipto());
        etKhqk.setText(FigureTools.sswrFigure(mMasterResponseData.getOweamt()));
        etHjje.setText(FigureTools.sswrFigure(mMasterResponseData.getTotalamt()));
        etSkje.setText(FigureTools.sswrFigure(mMasterResponseData.getReceipt()));
        tvSklx.setText(mMasterResponseData.getIspcname());
        tvZjzh.setText(mMasterResponseData.getBankname());
        tvWlgs.setText(mMasterResponseData.getLogisticname());
        tvDszh.setText(mMasterResponseData.getProxybankname());
        etDsje.setText(FigureTools.sswrFigure(mMasterResponseData.getProxyamt()));
        etSkhj.setText(FigureTools.sswrFigure(mMasterResponseData.getSumamt()));
        tvDjrq.setText(mMasterResponseData.getBilldate());
        tvBm.setText(mMasterResponseData.getDepname());
        tvYwy.setText(mMasterResponseData.getEmpname());
        tvZdr.setText(mMasterResponseData.getOpname());
        etBzxx.setText(mMasterResponseData.getMemo());
    }



    private void save(){
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mActivity));
        parmMap.put("tabname", "tb_invoice");
        parmMap.put("parms", "XSKD");
        parmMap.put("master", "");
        parmMap.put("detail", "");
        parmMap.put("serialinfo", "");
    }


}
