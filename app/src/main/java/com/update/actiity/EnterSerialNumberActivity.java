package com.update.actiity;

import android.app.Dialog;

import com.crcxj.activity.R;
import com.update.base.BaseActivity;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/26 0026 下午 1:40
 * Description:录入序列号
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/26 0026         申中佳               V1.0
 */
public class EnterSerialNumberActivity extends BaseActivity{

    Dialog mDialog;
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
        return R.layout.activity_enter_serial_number;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {

    }
}
