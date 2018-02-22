package com.update.actiity;

import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.viewbar.TitleBar;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/22 0022 上午 11:51
 * Description:安装登记界面
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/22 0022         申中佳               V1.0
 */
public class InstallRegistrationActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;

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
        return R.layout.activity_install_registration;
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
    private void setTitlebar(){
        titlebar.setTitleText(this,"安装登记");
        titlebar.setIvRightTwoImageResource(R.drawable.oper);
    }
}
