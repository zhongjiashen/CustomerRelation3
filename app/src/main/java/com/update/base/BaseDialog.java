package com.update.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.crcxj.activity.R;
import com.update.dialog.OnDialogClickInterface;

import butterknife.ButterKnife;


/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/9/15 0015 下午 3:49
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/9/15 0015         申中佳               V1.0
 */
public abstract class BaseDialog extends Dialog {
    protected Context mContext;
    protected OnDialogClickInterface mDialogClickInterface;
    private double hight;
    private double width;
    private int gravity;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.mContext = context;
        init();
        ButterKnife.bind(this);

    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;

    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        width = 0.8;
        gravity = Gravity.CENTER;
        setParameter();
        initWindowParams();

    }

    /**
     * width 宽度设置
     * gravity 位置设置
     */
    protected void setParameter() {

    }

    public void setDialogClickInterface(OnDialogClickInterface dialogClickInterface) {
        mDialogClickInterface = dialogClickInterface;
    }

    protected void initWindowParams() {
        Window dialogWindow = getWindow();
        // 获取屏幕宽、高用
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (dm.widthPixels * width); // 宽度设置为屏幕的0.5
        dialogWindow.setGravity(gravity);
        dialogWindow.setAttributes(lp);
    }

    /**
     * 初始化
     */
    protected abstract void init();
}
