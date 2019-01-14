package com.cr.activity.jxc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.EnterSerialNumberActivity;
import com.update.actiity.WeChatCaptureActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseRecycleAdapter;
import com.update.dialog.DialogFactory;
import com.update.dialog.OnDialogClickInterface;
import com.update.model.Serial;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加序列号
 */
public class JxcTjXlhActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.et_serial_number)
    EditText etSerialNumber;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;

    List<Serial> mSerials;
    private int mPosition;
    private String mBillid;//主单id
    private String mSerialinfo;//序列号

    @Override
    protected void initVariables() {
        mPosition = getIntent().getIntExtra("position", 0);
        mBillid = getIntent().getStringExtra("billid");
        mSerialinfo = getIntent().getStringExtra("serialinfo");
        mSerials = new Gson().fromJson(getIntent().getStringExtra("serials"), new TypeToken<List<Serial>>() {
        }.getType());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_serial_number_add;
    }

    @Override
    protected void init() {
        setTitlebar();
        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.SerialNumberHolder, Serial>(mSerials) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getSerialNumberHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.SerialNumberHolder holder, final Serial data) {
                holder.tvSerialNumber.setText(data.getSerno());
                holder.ivEditor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFactory.getModifySerialNumberDialog(mActivity, data.getSerno(), new OnDialogClickInterface() {
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
                        DialogFactory.getButtonDialog(mActivity, "确定删除该序列号吗？", new OnDialogClickInterface() {
                            @Override
                            public void OnClick(int requestCode, Object object) {
                                mSerials.remove(holder.getLayoutPosition());
                                mAdapter.setList(mSerials);
                            }


                        }).show();

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
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra("position", mPosition)
                        .putExtra("data", mPGson.toJson(mSerials)));
                finish();
            }
        });
    }

    @OnClick({R.id.iv_scan, R.id.bt_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class), 11);
                break;
            case R.id.bt_add:
                String serial_number = etSerialNumber.getText().toString();
                if (TextUtils.isEmpty(serial_number))
                    showShortToast("请添加序列号");
                else {
                    addSerialNumber(serial_number);
                }
                break;
        }
    }

    private void addSerialNumber(String serial_number) {
        if (mSerials == null)
            mSerials = new ArrayList<>();
        else {
            for (int i = 0; i < mSerials.size(); i++) {
                if (mSerials.get(i).getSerno().equals(serial_number)) {
                    showShortToast("不能录入重复序列号！");
                    return;
                }
            }
        }
        Serial serial = new Serial();
        serial.setBillid(mBillid);
        serial.setSerialinfo(mSerialinfo);
        serial.setSerno(serial_number);

        mSerials.add(serial);
        mAdapter.setList(mSerials);
        etSerialNumber.setText("");

    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        addSerialNumber(data.getStringExtra("qr"));
    }
}
