package com.update.actiity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.LocalDataSingleOptionActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseRecycleAdapter;
import com.update.dialog.DialogFactory;
import com.update.dialog.OnDialogClickInterface;
import com.update.model.Serial;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
public class EnterSerialNumberActivity extends BaseActivity {

    Dialog mDialog;
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.et_serial_number)
    EditText etSerialNumber;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    private Gson mGson;
    List<Serial> serials;
    private String serialinfo;	//序列号GUID
    private String billid;	//主单据ID
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mGson=new Gson();
        billid=getIntent().getStringExtra("billid");
        serialinfo=getIntent().getStringExtra("uuid");
        serials=mGson.fromJson(getIntent().getStringExtra("DATA"),new TypeToken<List<Serial>>() {
        }.getType());
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
        setTitlebar();
        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.SerialNumberHolder,Serial>(serials) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getSerialNumberHolder(EnterSerialNumberActivity.this);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.SerialNumberHolder holder, final Serial data) {
                holder.tvSerialNumber.setText(data.getSerno());
                holder.ivEditor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFactory.getModifySerialNumberDialog(EnterSerialNumberActivity.this, data.getSerno(), new OnDialogClickInterface() {
                            @Override
                            public void OnClick(int requestCode, Object object) {
                                data.setSerno((String) object);
                                holder.tvSerialNumber.setText(data.getSerno());
                            }
                        }).show();
                    }
                });
                holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//删除该序列号
                        serials.remove(holder.getLayoutPosition());
                        mAdapter.setList(serials);
                    }
                });
            }
        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "录入序列号");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 2:
                        setResult(RESULT_OK,new Intent().putExtra("DATA",mGson.toJson(serials)));
                        finish();
                        break;

                }
            }
        });
    }

    @OnClick({R.id.iv_scan, R.id.bt_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class),11);
                break;
            case R.id.bt_add:
                String serial_number=etSerialNumber.getText().toString();
                if(TextUtils.isEmpty(serial_number))
                    showShortToast("请添加序列号");
                else {
                    Serial serial=new Serial();
                    serial.setBillid(billid);
                    serial.setSerialinfo(serialinfo);
                    serial.setSerno(serial_number);
                    if(serials==null)
                        serials=new ArrayList<>();
                    serials.add(serial);
                }
                mAdapter.setList(serials);
                etSerialNumber.setText("");
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        super.onMyActivityResult(requestCode, resultCode, data);
        etSerialNumber.setText(data.getStringExtra("qr"));
    }
}
