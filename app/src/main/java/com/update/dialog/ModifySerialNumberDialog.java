package com.update.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crcxj.activity.R;
import com.update.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/10 0010 上午 9:26
 * Description: 修改序列号
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/10 0010         申中佳               V1.0
 */
public class ModifySerialNumberDialog extends BaseDialog {
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public ModifySerialNumberDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        setContentView(R.layout.dialog_modify_serial_number);

    }

    public void setTitle(String title) {
       tvTitle.setText(title);

    }

    public void setData(Object object) {
        etText.setText((String) object);

    }

    @OnClick({R.id.btn_cancel, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.bt_ok:
                if(TextUtils.isEmpty(etText.getText().toString())){
                    Toast.makeText(mContext,"序列号不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                mDialogClickInterface.OnClick(0, etText.getText().toString());
                dismiss();
                break;
        }
    }
}
