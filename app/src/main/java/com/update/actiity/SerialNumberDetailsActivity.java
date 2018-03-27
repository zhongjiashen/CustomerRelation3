package com.update.actiity;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.Serial;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 1363655717 on 2018-03-17.
 */

public class SerialNumberDetailsActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;

    private String billid;//单据ID
    private String serialinfo;//单据对应序列号列表的唯一guid
    private Map<String, Object> mParmMap;
    List<Serial> serials;

    @Override
    protected void initVariables() {
        billid = getIntent().getStringExtra("billid");
        serialinfo = getIntent().getStringExtra("serialinfo");
        presenter = new BaseP(this, this);
        mParmMap = new ArrayMap<>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("tabname", getIntent().getStringExtra("tabname"));
        mParmMap.put("billid", billid);
        mParmMap.put("serno", "");
        mParmMap.put("serialinfo", serialinfo);
        mParmMap.put("curpage", "0");
        mParmMap.put("pagesize", 100);
        presenter.post(0, "seriallist", mParmMap);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_serial_number_details;
    }

    @Override
    protected void init() {
        setTitlebar();
        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.SerialNumberHolder, Serial>(serials) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getSerialNumberHolder(SerialNumberDetailsActivity.this);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.SerialNumberHolder holder, final Serial data) {
                holder.tvSerialNumber.setText(data.getSerno());
                holder.ivEditor.setVisibility(View.GONE);
                holder.ivDelete.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "查看序列号");

    }

    @Override
    public void returnData(int requestCode, Object data) {
        super.returnData(requestCode, data);
        serials = new Gson().fromJson((String) data, new TypeToken<List<Serial>>() {
        }.getType());
        mAdapter.setList(serials);
    }
}
