package com.cr.activity.jxc.spxq;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.cr.activity.response.XsddDetailResponseData;
import com.cr.activity.tjfx.kcbb.TjfxKcbbSpjg2Activity;
import com.cr.myinterface.SLViewValueChange;
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.model.Serial;
import com.update.utils.CustomTextWatcher;
import com.update.utils.EditTextHelper;
import com.update.viewbar.TitleBar;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: CustomerRelation3
 * @Package: com.cr.activity.jxc.spxq
 * @ClassName: SpxqActivity
 * @Description: java类作用描述
 * @Author: 申中佳
 * @CreateDate: 2020-06-25 17:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-06-25 17:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SpxqActivity extends BaseActivity {
    private XsddDetailResponseData mXsddDetailResponseData;

    private Boolean mIsRateEditor;
    private int mPosition;

    /**
     * @param activity
     * @param data
     * @param billtypename //发票类型名称
     * @return
     */
    public static Intent getMyIntent(Activity activity, String data, String billtypename, int position) {
        Intent intent = new Intent(activity, SpxqActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("billtypename", billtypename);
        intent.putExtra("position", position);
        return intent;
    }


    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_spmc)
    TextView tvSpmc;
    @BindView(R.id.tv_spbm)
    TextView tvSpbm;
    @BindView(R.id.tv_spgg)
    TextView tvSpgg;
    @BindView(R.id.tv_spxh)
    TextView tvSpxh;
    @BindView(R.id.tv_spkz)
    TextView tvSpkz;
    @BindView(R.id.tv_sl)
    TextView tvSl;
    @BindView(R.id.slv_sl)
    SLView2 slvSl;
    @BindView(R.id.et_cpph)
    EditText etCpph;
    @BindView(R.id.et_scrq)
    EditText etScrq;
    @BindView(R.id.et_yxqz)
    EditText etYxqz;
    @BindView(R.id.ll_pcsp)
    LinearLayout llPcsp;
    @BindView(R.id.et_dj)
    EditText etDj;
    @BindView(R.id.et_sl)
    EditText etSl;
    @BindView(R.id.tv_hsdj)
    TextView tvHsdj;
    @BindView(R.id.et_bz)
    EditText etBz;
    @BindView(R.id.tv_serial_number)
    TextView tvSerialNumber;
    @BindView(R.id.et_cbj)
    EditText etCbj;
    @BindView(R.id.ll_cbj)
    LinearLayout llCbj;


    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mXsddDetailResponseData = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<XsddDetailResponseData>() {
        }.getType());

        String billtypename = getIntent().getStringExtra("billtypename");
        switch (billtypename) {
            case "收据":
                mIsRateEditor = false;
                break;
            default:
                mIsRateEditor = true;
                break;
        }

        mPosition = getIntent().getIntExtra("position", 0);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_jxc_spbj;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {

        setTitlebar();
        tvSerialNumber.setVisibility(View.GONE);
        slvSl.setVisibility(View.VISIBLE);
        tvSl.setVisibility(View.GONE);
        llPcsp.setVisibility(View.GONE);

        tvSpmc.setText("名称：" + mXsddDetailResponseData.getGoodsname());
        tvSpbm.setText("编码：" + mXsddDetailResponseData.getGoodscode());
        tvSpgg.setText("规格：" + mXsddDetailResponseData.getSpecs());
        tvSpxh.setText("型号：" + mXsddDetailResponseData.getModel());
        slvSl.setSl(Double.parseDouble(mXsddDetailResponseData.getUnitqty()));
        etBz.setText(mXsddDetailResponseData.getMemo());
        etDj.setText(mXsddDetailResponseData.getUnitprice());//单价
        etSl.setText(mXsddDetailResponseData.getTaxrate());//税率
        EditTextHelper.EditTextEnable(mIsRateEditor, etSl);
        tvHsdj.setText(mXsddDetailResponseData.getTaxunitprice());//含税单价

        slvSl.setOnValueChange(new SLViewValueChange() {
            @Override
            public void onValueChange(double sl) {
                mXsddDetailResponseData.setUnitqty(sl + "");
                double amount = Double.parseDouble(mXsddDetailResponseData.getTaxunitprice()) * sl;
                mXsddDetailResponseData.setAmount(amount + "");
            }
        });

        etSl.addTextChangedListener(new CustomTextWatcher(new CustomTextWatcher.UpdateTextListener() {
            @Override
            public void updateText(String string) {
                if (etSl.hasFocus() && !TextUtils.isEmpty(string)) {
                    mXsddDetailResponseData.setTaxrate(string);
                    Double csje = Double.parseDouble(mXsddDetailResponseData.getUnitprice()) * (Double.parseDouble(mXsddDetailResponseData.getTaxrate()) + 100) / 100;
                    mXsddDetailResponseData.setTaxunitprice(FigureTools.sswrFigure(csje));
                    tvHsdj.setText(mXsddDetailResponseData.getTaxunitprice());//含税单价
                    double amount = Double.parseDouble(mXsddDetailResponseData.getTaxunitprice()) * slvSl.getSl();
                    mXsddDetailResponseData.setAmount(amount + "");
                }
            }
        }));

        etDj.addTextChangedListener(new CustomTextWatcher(new CustomTextWatcher.UpdateTextListener() {
            @Override
            public void updateText(String string) {
                if (!TextUtils.isEmpty(string)) {
                    mXsddDetailResponseData.setUnitprice(string);
                    Double csje = Double.parseDouble(mXsddDetailResponseData.getUnitprice()) * (Double.parseDouble(mXsddDetailResponseData.getTaxrate()) + 100) / 100;
                    mXsddDetailResponseData.setTaxunitprice(FigureTools.sswrFigure(csje));
                    tvHsdj.setText(mXsddDetailResponseData.getTaxunitprice());//含税单价
                    double amount = Double.parseDouble(mXsddDetailResponseData.getTaxunitprice()) * slvSl.getSl();
                    mXsddDetailResponseData.setAmount(amount + "");
                }
            }
        }));


    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "商品详情");
        titlebar.setRightText("完成");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {

                mXsddDetailResponseData.setMemo(etBz.getText().toString());
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra("position", mPosition)
                        .putExtra("data", new Gson().toJson(mXsddDetailResponseData)));
                finish();
            }
        });
    }


    @OnClick({R.id.xzjg_iv, R.id.bt_sc})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.xzjg_iv:
                Intent intent2 = new Intent();
                intent2.setClass(mActivity, TjfxKcbbSpjg2Activity.class);
                intent2.putExtra("goodsid", mXsddDetailResponseData.getGoodsid());
                intent2.putExtra("storied", mXsddDetailResponseData.getSorderid());
                intent2.putExtra("unitid", mXsddDetailResponseData.getUnitid());
                intent2.putExtra("clientid", "0");
                intent2.putExtra("index", "0");
                startActivityForResult(intent2, 3);
                break;
            case R.id.bt_sc:
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra("position", mPosition)
                        .putExtra("data", ""));
                finish();
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3:
                mXsddDetailResponseData.setUnitprice(data.getStringExtra("dj"));
                etDj.setText(mXsddDetailResponseData.getUnitprice());//单价
                break;

        }
    }
}
