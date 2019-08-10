package com.update.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.update.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/12 0012 下午 2:09
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/12 0012         申中佳               V1.0
 */
public class ButtonDialog extends BaseDialog {

    @BindView(R.id.tv_text)
    TextView tvText;

    public ButtonDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        setContentView(R.layout.dialog_button);
    }

    public void setTitle(String s){
        tvText.setText(s);
    }


    @OnClick({R.id.btn_cancel, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.bt_ok:
                mDialogClickInterface.OnClick(0, "");
                dismiss();
                break;
        }
    }
}
