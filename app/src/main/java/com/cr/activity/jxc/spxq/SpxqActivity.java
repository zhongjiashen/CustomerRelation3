package com.cr.activity.jxc.spxq;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.cr.activity.response.XsddDetailResponseData;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.viewbar.TitleBar;

import butterknife.BindView;

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
    public static Intent getMyIntent(Activity activity, String data) {
        Intent intent = new Intent(activity, SpxqActivity.class);
        intent.putExtra("data", data);
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



    private XsddDetailResponseData mXsddDetailResponseData;
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mXsddDetailResponseData= new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<XsddDetailResponseData>() {
        }.getType());

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
        tvSpmc.setText("名称：" + mXsddDetailResponseData.getGoodsname());
        tvSpbm.setText( "编码：" +mXsddDetailResponseData.getGoodscode());
        tvSpgg.setText("规格：" + mXsddDetailResponseData.getSpecs());
        tvSpxh.setText("型号：" +mXsddDetailResponseData.getModel());
    }
}
