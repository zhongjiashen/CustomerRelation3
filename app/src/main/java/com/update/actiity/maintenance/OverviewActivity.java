package com.update.actiity.maintenance;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.base.BaseActivity;
import com.update.model.GoodsOrOverviewData;
import com.update.viewbar.TitleBar;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/5 0005 下午 4:22
 * Description:增加概况
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/5 0005         申中佳               V1.0
 */
public class OverviewActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.et_overview)
    EditText etOverview;
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
    private GoodsOrOverviewData mOverviewData;
    private String ensureid;// 保修状态ID
    private String faultid;// 故障类别ID

    private int mKind;//

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mKind = getIntent().getIntExtra("kind", 0);
        mOverviewData = new GoodsOrOverviewData();

    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_overview;
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

        switch (mKind) {
            case 0://添加概况
                titlebar.setTitleText(this, "增加概况");
                break;
            case 1://修改删除概况
                titlebar.setTitleText(this, "概况详情");
                titlebar.setRightText("保存");
                titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
                    @Override
                    public void onClick(int i) {
                        switch (i) {
                            case 2:
                                String overview = etOverview.getText().toString();//获取输入的概况信息
                                if (TextUtils.isEmpty(overview))//判断输入的概况信息是非非空
                                    showShortToast("请输入概况信息");
                                else {
                                    mOverviewData.setGoodsname(overview);
                                    mOverviewData.setUnitqty(slView.getSl() + "");//数量
                                    setResult(RESULT_OK, new Intent().putExtra("DATA", new Gson().toJson(mOverviewData)));
                                    finish();
                                }
                                break;

                        }
                    }
                });
                btView.setText("删除");
                mOverviewData = new Gson().fromJson(getIntent().getStringExtra("DATA"), new TypeToken<GoodsOrOverviewData>() {
                }.getType());
                etOverview.setText(mOverviewData.getGoodsname());
                slView.setSl(Double.valueOf(mOverviewData.getUnitqty()));
                tvWarrantyStatus.setText(mOverviewData.getEnsurename());
                tvFaultType.setText(mOverviewData.getFaultname());
                etFaultDescription.setText(mOverviewData.getFaultinfo());
                break;
            case 2://只能查看概况
                titlebar.setTitleText(this, "概况详情");
                btView.setVisibility(View.GONE);
                mOverviewData = new Gson().fromJson(getIntent().getStringExtra("DATA"), new TypeToken<GoodsOrOverviewData>() {
                }.getType());
                etOverview.setText(mOverviewData.getGoodsname());
                slView.setSl(Double.valueOf(mOverviewData.getUnitqty()));
                tvWarrantyStatus.setText(mOverviewData.getEnsurename());
                tvFaultType.setText(mOverviewData.getFaultname());
                etFaultDescription.setText(mOverviewData.getFaultinfo());
                break;
        }

    }


    @OnClick({R.id.ll_warranty_status, R.id.ll_fault_type, R.id.bt_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_warranty_status:
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "BXZT").putExtra("title", "保修状态选择"), 11);
                break;
            case R.id.ll_fault_type:
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "GZLB").putExtra("title", "故障类别选择"), 12);
                break;
            case R.id.bt_view:
                switch (mKind) {
                    case 0://增加概况，确认增加
                        String overview = etOverview.getText().toString();//获取输入的概况信息
                        if (TextUtils.isEmpty(overview))//判断输入的概况信息是非非空
                            showShortToast("请输入概况信息");
                        else {
                            mOverviewData.setBillid("0");//单据ID;为0或空时表示新增
                            mOverviewData.setItemno("0");
                            mOverviewData.setLb("0");//概况=0，商品=1
                            mOverviewData.setGoodsid("0");
                            mOverviewData.setSerialinfo(" ");
                            mOverviewData.setUnitid("0");
                            mOverviewData.setGoodsname(overview);
                            mOverviewData.setUnitqty(slView.getSl() + "");//数量
                            mOverviewData.setEnsureid(ensureid);
                            mOverviewData.setEnsurename(tvWarrantyStatus.getText().toString());
                            mOverviewData.setFaultid(faultid);
                            mOverviewData.setFaultname(tvFaultType.getText().toString());
                            mOverviewData.setFaultinfo(etFaultDescription.getText().toString());
                            setResult(RESULT_OK, new Intent().putExtra("DATA", new Gson().toJson(mOverviewData)));
                            finish();
                        }
                        break;
                    case 1://概况详情删除
                        setResult(RESULT_OK, new Intent().putExtra("KIND", 1));
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11://保修状态
                ensureid = data.getStringExtra("CHOICE_RESULT_ID");
                tvWarrantyStatus.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 12://保修状态
                faultid = data.getStringExtra("CHOICE_RESULT_ID");
                tvFaultType.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
        }
    }
}
