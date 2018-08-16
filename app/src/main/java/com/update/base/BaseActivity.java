package com.update.base;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.TimeZone;

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
public abstract class BaseActivity<T extends BaseP> extends AppCompatActivity implements BaseV, TakePhoto.TakeResultListener, InvokeListener {
    protected T presenter;
    protected BaseRecycleAdapter mAdapter;
    protected TakePhoto takePhoto;
    private InvokeParam invokeParam;
    protected final int DATA_REFERSH = 10;//刷新

    protected Activity mActivity;
    private DatePickerDialog dateDialog = null;
    private TimePickerDialog timeDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        mActivity=this;
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
    public void httpFinish(int requestCode) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case DATA_REFERSH:
                    refersh();
                    break;
                default:
                    try {
                        onMyActivityResult(requestCode, resultCode, data);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
            }

        }
    }

    /**
     * 刷新界面数据
     */
    protected void refersh(){};

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*------------------------------TakePhoto配置开始--------------------*/
     /*图片选择和拍照*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public void takeSuccess(TResult result) {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {
        showShortToast("取消当前操作！");

    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        takePhoto.onEnableCompress(new CompressConfig.Builder()
                .setMaxSize(50 * 1024).setMaxPixel(800)
                .create(), true);
        return takePhoto;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }



    /* 初始化日期的弹出选择框 */
    public void date_init(final EditText editText) {
        DatePickerDialog.OnDateSetListener otsl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month,
                                  int dayOfMonth) {
                month++;
                String myMonth = "";
                String myDay = "";
                if (month < 10) {
                    myMonth = "0" + month;
                } else {
                    myMonth = "" + month;
                }
                if (dayOfMonth < 10) {
                    myDay = "0" + dayOfMonth;
                } else {
                    myDay = "" + dayOfMonth;
                }
                editText.setText(year + "-" + myMonth + "-" + myDay);
                dateDialog.dismiss();
            }
        };
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateDialog = new DatePickerDialog(this, otsl, year, month, day);
        dateDialog.show();
    }

}
