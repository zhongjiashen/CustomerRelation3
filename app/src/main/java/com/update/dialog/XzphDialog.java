package com.update.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crcxj.activity.R;
import com.update.base.BaseDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/10 0010 上午 9:26
 * Description: 新增批号
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/10 0010         申中佳               V1.0
 */
public class XzphDialog extends BaseDialog {


    @BindView(R.id.et_ph)
    EditText etPh;
    @BindView(R.id.et_cbj)
    EditText etCbj;

    private boolean isCbj;

    public XzphDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        setContentView(R.layout.dialog_xzph);

    }

    public void setCbj(boolean cbj) {
        isCbj = cbj;
        if(isCbj){
            etCbj.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn_cancel, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.bt_ok:
                String ph=etPh.getText().toString();
                String cbj=etCbj.getText().toString();
                if(TextUtils.isEmpty(ph)){
                    Toast.makeText(mContext,"请输入批号名称！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isCbj&&TextUtils.isEmpty(cbj)){
                    Toast.makeText(mContext,"请输入成本价！",Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String,String> map=new HashMap<>();
                map.put("ph",ph);
                map.put("cbj",cbj);
                mDialogClickInterface.OnClick(0, map);
                dismiss();
                break;
        }
    }
}
