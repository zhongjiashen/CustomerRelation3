package com.cr.activity.jxc;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cr.activity.SLView2;
import com.cr.activity.response.XsddDetailResponseData;
import com.cr.activity.response.XsddMasterResponseData;
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.utils.EditTextHelper;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;

import java.util.List;

import butterknife.BindView;

/**
 * @ProjectName: CustomerRelation3
 * @Package: com.cr.activity.jxc
 * @ClassName: SpxqCkActivity
 * @Description: java类作用描述
 * @Author: 申中佳
 * @CreateDate: 2020-06-22 10:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-06-22 10:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SpxqCkActivity extends BaseActivity {
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
    @BindView(R.id.tv_serial_number)
    TextView tvSerialNumber;
    @BindView(R.id.slv_sl)
    SLView2 slvSl;
    @BindView(R.id.et_bz)
    EditText etBz;
    @BindView(R.id.et_cpph)
    EditText etCpph;
    @BindView(R.id.ll_cpph)
    LinearLayout llCpph;
    @BindView(R.id.et_scrq)
    EditText etScrq;
    @BindView(R.id.ll_scrq)
    LinearLayout llScrq;
    @BindView(R.id.et_yxqz)
    EditText etYxqz;
    @BindView(R.id.ll_yxqz)
    LinearLayout llYxqz;
    @BindView(R.id.ll_pcsp)
    LinearLayout llPcsp;
    @BindView(R.id.et_dj)
    EditText etDj;
    @BindView(R.id.xzjg_iv)
    ImageView xzjgIv;
    @BindView(R.id.ll_dj)
    LinearLayout llDj;
    @BindView(R.id.et_sl)
    EditText etSl;
    @BindView(R.id.ll_sl)
    LinearLayout llSl;
    @BindView(R.id.tv_hsdj)
    TextView tvHsdj;


    private XsddDetailResponseData mXsddDetailResponseData;
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {

        mXsddDetailResponseData= new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<XsddDetailResponseData>() {
        }.getType());
        LogUtils.e(JSON.toJSONString(mXsddDetailResponseData));
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_cggl_spxq_ck;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        titlebar.setTitleText(mActivity,"商品详情");
        tvSerialNumber.setVisibility(View.GONE);
        tvSpmc.setText("名称：" + mXsddDetailResponseData.getGoodsname());
        tvSpbm.setText( "编码：" +mXsddDetailResponseData.getGoodscode());
        tvSpgg.setText("规格：" + mXsddDetailResponseData.getSpecs());
        tvSpxh.setText("型号：" +mXsddDetailResponseData.getModel());
        etBz.setText(mXsddDetailResponseData.getMemo());
        tvSpkz.setVisibility(View.GONE);
        llPcsp.setVisibility(View.GONE);
        tvSerialNumber.setVisibility(View.GONE);

        slvSl.setSl(Double.parseDouble(mXsddDetailResponseData.getUnitqty()));
        etDj.setText(mXsddDetailResponseData.getUnitprice());//单价
        etSl.setText(mXsddDetailResponseData.getTaxrate());//税率
        EditTextHelper.EditTextEnable(false,etSl);
        tvHsdj.setText(mXsddDetailResponseData.getTaxunitprice());//含税单价


//            if (data["onhand"] != null) {
//                tv_spkz.text = "库存：" + FigureTools.sswrFigure(data["onhand"].toString().toDouble()) + data["unitname"].toString()
//            } else {
//                tv_spkz.visibility = View.GONE
//            }
//            //是批次商品的会显示批号、生产日期、有效日期
//            if (data["batchctrl"].toString().equals("T")) {
//                ll_pcsp.visibility = View.VISIBLE
//                et_cpph.setText(data["batchcode"].toString())
//                et_scrq.setText(data["produceddate"].toString())
//                et_yxqz.setText(data["validdate"].toString())
//            } else {
//                ll_pcsp.visibility = View.GONE
//            }




    }
}
