package com.cr.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.CkxzResponseData;
import com.update.model.DataDictionaryData;
import com.update.utils.LogUtils;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/1 0001 上午 9:43
 * Description:仓库选择
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/1 0001         申中佳               V1.0
 */
public class CkxzActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private int kind;//
    private Map<String, Object> mParmMap;
    private List<CkxzResponseData> mList;

    private String mStoreid="0";

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        kind = getIntent().getIntExtra("kind", 0);
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("storeid",mStoreid);
        mParmMap.put("opid", ShareUserInfo.getUserId(mActivity));
        presenter.post(0, "storelist", mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_state_audit_choice;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        rvList.setLayoutManager(new LinearLayoutManager(this));
//
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "仓库选择");


    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        if(TextUtils.isEmpty(data.toString())){
            showShortToast("目前无仓库信息或无仓库的使用权限");
            return;
        }
        Gson gson = new Gson();
        mList = gson.fromJson((String) data,
                new TypeToken<List<CkxzResponseData>>() {
                }.getType());
        rvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.StateAuditChoiceHolder,CkxzResponseData>( mList) {
//
            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getStateAuditChoiceHolder(CkxzActivity.this,parent);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.StateAuditChoiceHolder holder, final CkxzResponseData data) {
                holder.tvText.setText(data.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_OK, new Intent().putExtra("dictmc", data.getName())
                                .putExtra("id",data.getId()+""));
                        finish();
                    }
                });
            }

        });
    }
}
