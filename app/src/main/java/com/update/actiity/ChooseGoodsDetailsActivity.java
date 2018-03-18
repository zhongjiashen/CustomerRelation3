package com.update.actiity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.cr.myinterface.SLViewValueChange;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.model.ChooseGoodsData;
import com.update.model.InstallRegistrationScheduleData;
import com.update.model.Serial;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;

import java.util.List;

import butterknife.BindView;
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
public class ChooseGoodsDetailsActivity extends BaseActivity {
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

    private int mKind;//
    ChooseGoodsData chooseGoodsData;

    private Gson mGson;
    private InstallRegistrationScheduleData installRegistrationScheduleData;

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
        return R.layout.activity_choose_good_details;
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
                                setResult(RESULT_OK, new Intent()
                                        .putExtra("KIND",1)
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
                break;
            case 2://只能查看概况

                btView.setVisibility(View.GONE);
                installRegistrationScheduleData = mGson.fromJson(getIntent().getStringExtra("DATA"), new TypeToken<InstallRegistrationScheduleData>() {
                }.getType());
                tvGoodName.setText("名称：" + installRegistrationScheduleData.getGoodsname());
                tvCoding.setText("编码：" + installRegistrationScheduleData.getGoodscode());
                tvSpecifications.setText("规格：" + installRegistrationScheduleData.getSpecs());
                tvModel.setText("型号：" + installRegistrationScheduleData.getModel());
                tvUnit.setText("单位：" + installRegistrationScheduleData.getUnitname());
                slView.setSl(installRegistrationScheduleData.getUnitqty());//设置数量
                break;
        }
    }

    @OnClick({R.id.tv_serial_number, R.id.bt_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_serial_number:
                if (mKind == 1) {
                    startActivityForResult(new Intent(ChooseGoodsDetailsActivity.this, EnterSerialNumberActivity.class)
                            .putExtra("billid", "0")
                            .putExtra("uuid", chooseGoodsData.getSerialinfo())
                            .putExtra("DATA", mGson.toJson(chooseGoodsData.getSerials())), 11);
                } else
                    startActivity(new Intent(ChooseGoodsDetailsActivity.this, SerialNumberDetailsActivity.class)
                            .putExtra("billid", installRegistrationScheduleData.getInstallregid() + "")
                            .putExtra("serialinfo", installRegistrationScheduleData.getSerialinfo()));
                break;
            case R.id.bt_view:
                setResult(RESULT_OK,new Intent().putExtra("KIND",0));
                finish();
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        //处理返回的序列号信息
        List<Serial> serials = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<List<Serial>>() {
        }.getType());
        chooseGoodsData.setSerials(serials);

    }
}
