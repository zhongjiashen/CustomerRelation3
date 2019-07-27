package com.cr.activity.jxc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.Serial;
import com.update.model.SerialData;
import com.update.viewbar.TitleBar;
import com.update.viewholder.XzXlhHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 选择序列号
 */
public class XzXlh1Activity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;


    List<SerialData> mSerials;
    List<SerialData> mSerialsA;
    private int mPosition;


    @Override
    protected void initVariables() {

        mSerials = new Gson().fromJson(getIntent().getStringExtra("serials"), new TypeToken<List<SerialData>>() {
        }.getType());
        mSerialsA = new Gson().fromJson(getIntent().getStringExtra("serials1"), new TypeToken<List<SerialData>>() {
        }.getType());
        processData();

        mPosition = getIntent().getIntExtra("position", 0);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_kcpd_xzxlh;
    }

    @Override
    protected void init() {
        setTitlebar();

        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<XzXlhHolder.ViewHolder, SerialData>(mSerialsA) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return XzXlhHolder.getViewHolder(mActivity, parent);
            }

            @Override
            protected void MyonBindViewHolder(final XzXlhHolder.ViewHolder holder, final SerialData data) {
                holder.cbView.setChecked(data.isCheck());
                //选择
                holder.cbView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setCheck(holder.cbView.isChecked());
                    }
                });
                holder.tvSerialNumber.setText(data.getSerno());
            }
        });

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择序列号");
        titlebar.setRightText("完成");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                if(mSerialsA==null|mSerialsA.size()==0){
                    return;
                }

                mSerials = new ArrayList<>();
                for (int l = 0; l < mSerialsA.size(); l++) {
                    if (mSerialsA.get(l).isCheck())
                        mSerials.add(mSerialsA.get(l));
                }
                if (mSerials.size() == 0) {
                    showShortToast("严格序列号商品序列号不能为空！");
                    return;
                }
                setResult(Activity.RESULT_OK, new Intent()
                        .putExtra("position", mPosition)
                        .putExtra("data", mPGson.toJson(mSerials)));
                finish();
            }
        });
    }


    private void processData() {
        if (mSerials != null && mSerials.size() > 0) {
            for (int i = 0; i < mSerialsA.size(); i++) {
                if (mSerials.size() == 0)
                    break;
                for (int l = 0; l < mSerials.size(); l++) {
                    if (mSerialsA.get(i).getSerno().equals(mSerials.get(l).getSerno())) {
                        mSerialsA.get(i).setCheck(true);
                        mSerials.remove(l);
                        l = l - 1;
                        break;
                    }
                }
            }
        }

    }


}
