package com.update.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.base.BaseDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/12 0012 下午 2:09
 * Description: 公有云私有云链接方式设置
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/12 0012         申中佳               V1.0
 */
public class LinkSetDialog extends BaseDialog {

    @BindView(R.id.tv_ljfs)
    TextView tvLjfs;
    @BindView(R.id.sp_ljfs)
    Spinner spLjfs;
    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.sp_ip)
    Spinner spIp;
    @BindView(R.id.ll_ip)
    LinearLayout llIp;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.et_dk)
    EditText etDk;
    @BindView(R.id.ll_dk)
    LinearLayout llDk;


    private ArrayAdapter<String> ljfsAdapter;
    private ArrayAdapter<String> ipAdapter;
    private boolean isPublic;

    public LinkSetDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.dialog_link_set);
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        ljfsAdapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, new String[]{"私有云", "公有云"});
        ljfsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLjfs.setAdapter(ljfsAdapter);
        spLjfs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        tvLjfs.setText("私有云");
                        isPublic = false;
                        llName.setVisibility(View.GONE);
                        llDk.setVisibility(View.VISIBLE);
                        etIp.setText(ShareUserInfo.getKey(mContext, "privateIp", "saas.hengshicrm.com"));
                        etDk.setText(ShareUserInfo.getKey(mContext, "privateDk", "8031"));
                        break;
                    case 1:
                        tvLjfs.setText("公有云");
                        isPublic = true;
                        llName.setVisibility(View.VISIBLE);
                        llDk.setVisibility(View.GONE);
                        etIp.setText(ShareUserInfo.getKey(mContext, "publicIp", "saas.hengshicrm.com"));
                        etName.setText(ShareUserInfo.getKey(mContext, "usercode"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if ("true".equals(ShareUserInfo.getKey(mContext, "isPublic", "false"))) {
            isPublic = true;
            spLjfs.setSelection(1);
        } else {
            isPublic = false;
            spLjfs.setSelection(0);
        }
    }

    @OnClick({R.id.bt_ok, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_ok:
                String ip = etIp.getText().toString();
                if (TextUtils.isEmpty(ip)) {
                    showToastPromopt("IP地址不能为空！");
                    return;
                }
                if (isPublic) {
                    String name = etName.getText().toString();

                    if (TextUtils.isEmpty(name)) {
                        showToastPromopt("用户名不能为空！");
                        return;
                    }
                    ShareUserInfo.setKey(mContext, "usercode", name);
                    ShareUserInfo.setKey(mContext, "publicIp", ip);
                } else {
                    String dk = etDk.getText().toString();

                    if (TextUtils.isEmpty(dk)) {
                        showToastPromopt("端口号不能为空！");
                        return;
                    }
                    ShareUserInfo.setDK(mContext, dk);
                    ShareUserInfo.setKey(mContext, "privateDk", dk);
                    ShareUserInfo.setKey(mContext, "privateIp", ip);
                    ShareUserInfo.setIP(mContext, "http://" + ip);
                }
                ShareUserInfo.setKey(mContext, "isPublic", isPublic + "");
                mDialogClickInterface.OnClick(0, "");
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

}
