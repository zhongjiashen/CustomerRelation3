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

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.mContext = context;
        init();
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        init();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(getLayout());
        initWindowParams();

    }
    protected void initWindowParams() {
        Window dialogWindow = getWindow();
        // 获取屏幕宽、高用
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width3 = dm.widthPixels;
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width3 * 0.8); // 宽度设置为屏幕的0.5
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
    }
    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    public <T extends View> T $(View v, int id) {
        return (T) v.findViewById(id);
    }
    /**
     * 初始化
     */
    protected abstract void init();
}
