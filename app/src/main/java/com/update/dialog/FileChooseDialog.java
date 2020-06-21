package com.update.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.crcxj.activity.R;
import com.update.base.BaseDialog;

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
public class FileChooseDialog extends BaseDialog {

    public FileChooseDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setContentView(R.layout.dialog_file_choose);
    }



    @OnClick({R.id.bt_taking_pictures, R.id.bt_choose_picture, R.id.bt_choose_file})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_taking_pictures:
                mDialogClickInterface.OnClick(0, "");
                dismiss();
                break;
            case R.id.bt_choose_picture:
                mDialogClickInterface.OnClick(1, "");
                dismiss();
                break;
            case R.id.bt_choose_file:
                mDialogClickInterface.OnClick(2, "");
                dismiss();
                break;
        }
    }
}
