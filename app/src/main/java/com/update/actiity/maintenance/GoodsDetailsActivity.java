package com.update.actiity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.cr.myinterface.SLViewValueChange;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.EnterSerialNumberActivity;
import com.update.actiity.SerialNumberDetailsActivity;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.base.BaseActivity;
import com.update.model.ChooseGoodsData;
import com.update.model.GoodsOrOverviewData;
import com.update.model.InstallRegistrationScheduleData;
import com.update.model.Serial;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/14 0014 上午 10:26
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/14 0014         申中佳               V1.0
 */
public class GoodsDetailsActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_coding)
    TextView tvCoding;
    @BindView(R.id.tv_specifications)
    TextView tvSpecifications;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_serial_number)
    TextView tvSerialNumber;
    @BindView(R.id.sl_view)
    SLView2 slView;
    @BindView(R.id.bt_view)
    Button btView;
    @BindView(R.id.tv_warranty_status)
    TextView tvWarrantyStatus;
    @BindView(R.id.tv_fault_type)
    TextView tvFaultType;
    @BindView(R.id.et_fault_description)
    EditText etFaultDescription;
    @BindView(R.id.et_bz)
    EditText etBz;

    private int mKind;//
    ChooseGoodsData chooseGoodsData;

    private Gson mGson;
    private InstallRegistrationScheduleData installRegistrationScheduleData;
    GoodsOrOverviewData mGoodsOrOverviewData;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mKind = getIntent().getIntExtra("kind", 0);
        mGson = new Gson();

    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_good_details;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "商品详情");
        switch (mKind) {
            case 1://修改删除商品详情
                titlebar.setRightText("保存");
                titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
                    @Override
                    public void onClick(int i) {
                        switch (i) {
                            case 2:
                                chooseGoodsData.setFaultinfo(etFaultDescription.getText().toString());
                                setResult(RESULT_OK, new Intent()
                                        .putExtra("KIND", 1)
                                        .putExtra("DATA", mGson.toJson(chooseGoodsData)));
                                finish();
                                break;

                        }
                    }
                });
                chooseGoodsData = mGson.fromJson(getIntent().getStringExtra("DATA"), new TypeToken<ChooseGoodsData>() {
                }.getType());
                tvGoodName.setText("名称：" + chooseGoodsData.getName());
                tvCoding.setText("编码：" + chooseGoodsData.getCode());
                tvSpecifications.setText("规格：" + chooseGoodsData.getSpecs());
                tvModel.setText("型号：" + chooseGoodsData.getModel());
                tvUnit.setText("单位：" + chooseGoodsData.getUnitname());
                slView.setSl(chooseGoodsData.getNumber());//设置数量
                slView.setOnValueChange(new SLViewValueChange() {//数量控件数量变换监听
                    @Override
                    public void onValueChange(double sl) {
                        LogUtils.e("的身高多少");
                        chooseGoodsData.setNumber(sl);
                    }
                });
                tvWarrantyStatus.setText(chooseGoodsData.getEnsurename());
                tvFaultType.setText(chooseGoodsData.getFaultname());
                etFaultDescription.setText(chooseGoodsData.getFaultinfo());
                etBz.setText(chooseGoodsData.getMemo());
                break;
            case 2://只能查看概况
                if (getIntent().getBooleanExtra("xlh", true)) {
                    tvSerialNumber.setVisibility(View.VISIBLE);
                } else {
                    tvSerialNumber.setVisibility(View.GONE);
                }
                btView.setVisibility(View.GONE);
                mGoodsOrOverviewData = mGson.fromJson(getIntent().getStringExtra("DATA"), new TypeToken<GoodsOrOverviewData>() {
                }.getType());
                tvGoodName.setText("名称：" + mGoodsOrOverviewData.getGoodsname());
                tvCoding.setText("编码：" + mGoodsOrOverviewData.getGoodscode());
                tvSpecifications.setText("规格：" + mGoodsOrOverviewData.getSpecs());
                tvModel.setText("型号：" + mGoodsOrOverviewData.getModel());
                tvUnit.setText("单位：" + mGoodsOrOverviewData.getUnitname());
                slView.setSl(mGoodsOrOverviewData.getUnitqty());//设置数量
                tvWarrantyStatus.setText(mGoodsOrOverviewData.getEnsurename());
                tvFaultType.setText(mGoodsOrOverviewData.getFaultname());
                etFaultDescription.setText(mGoodsOrOverviewData.getFaultinfo());
                if (mGoodsOrOverviewData.getMemo() != null)
                    etBz.setText(mGoodsOrOverviewData.getMemo());
                break;
        }
    }


    @OnClick({R.id.tv_serial_number, R.id.ll_warranty_status, R.id.ll_fault_type, R.id.bt_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_serial_number:
                if (mKind == 1) {
                    startActivityForResult(new Intent(GoodsDetailsActivity.this, EnterSerialNumberActivity.class)
                            .putExtra("billid", "0")
                            .putExtra("uuid", chooseGoodsData.getSerialinfo())
                            .putExtra("DATA", mGson.toJson(chooseGoodsData.getSerials())), 11);
                } else
                    startActivity(new Intent(GoodsDetailsActivity.this, SerialNumberDetailsActivity.class)
                            .putExtra("billid", mGoodsOrOverviewData.getServiceregid())
                            .putExtra("serialinfo", mGoodsOrOverviewData.getSerialinfo())
                            .putExtra("tabname", "tb_servicereg"));
                break;
            case R.id.ll_warranty_status:
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "BXZT").putExtra("title", "保修状态选择"), 12);
                break;
            case R.id.ll_fault_type:
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "GZLB").putExtra("title", "故障类别选择"), 13);
                break;
            case R.id.bt_view:
                setResult(RESULT_OK, new Intent().putExtra("KIND", 0));
                finish();
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 11:
                //处理返回的序列号信息
                List<Serial> serials = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<List<Serial>>() {
                }.getType());
                chooseGoodsData.setSerials(serials);
                break;
            case 12://保修状态
                chooseGoodsData.setEnsureid(data.getStringExtra("CHOICE_RESULT_ID"));
                chooseGoodsData.setEnsurename(data.getStringExtra("CHOICE_RESULT_TEXT"));
                tvWarrantyStatus.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 13://保修状态
                chooseGoodsData.setFaultid(data.getStringExtra("CHOICE_RESULT_ID"));
                chooseGoodsData.setFaultname(data.getStringExtra("CHOICE_RESULT_TEXT"));
                tvFaultType.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
        }


    }


}
