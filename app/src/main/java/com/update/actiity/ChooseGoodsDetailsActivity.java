package com.update.actiity;

import android.widget.CheckBox;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.viewbar.TitleBar;

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
    @BindView(R.id.cb_view)
    CheckBox cbView;
    @BindView(R.id.tv_serial_number)
    TextView tvSerialNumber;
    @BindView(R.id.sl_view)
    SLView2 slView;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {

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

    }

    @OnClick(R.id.bt_view)
    public void onClick() {
    }
}
