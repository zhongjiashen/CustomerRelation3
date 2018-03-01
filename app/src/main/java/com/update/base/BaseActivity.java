package com.update.base;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;



import butterknife.ButterKnife;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/9/14 0014 下午 2:04
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/9/14 0014         申中佳               V1.0
 */
public abstract class BaseActivity<T extends BaseP> extends AppCompatActivity implements BaseV {
    protected T presenter;
    protected BaseRecycleAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        setContentView(getLayout());
        ButterKnife.bind(this);
        init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消所有正在进行的网络请求

    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "现在是竖屏", Toast.LENGTH_SHORT).show();
        }
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "现在是横屏", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    protected abstract void initVariables();

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    protected abstract int getLayout();

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 获取登录信息
     */
    protected void getLoginData() {

    }


    /**
     * 显示显示吐司
     *
     * @param text 吐司显示文本
     */
    @Override
    public void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {

    }

    /**
     * 网络请求失败
     *
     * @param requestCode 请求码
     */
    @Override
    public void httpfaile(int requestCode) {

    }

    /**
     * 网络请求结束
     */
    @Override
    public void httpFinish() {

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            onMyActivityResult( requestCode, resultCode, data);
        }
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
