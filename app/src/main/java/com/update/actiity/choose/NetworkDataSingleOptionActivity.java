package com.update.actiity.choose;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.update.model.DataDictionaryData;
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
 * Description:本地数据单项选择
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/1 0001         申中佳               V1.0
 */
public class NetworkDataSingleOptionActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private int kind;//
    private Map<String, Object> mParmMap;
    private List<DataDictionaryData> mList;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        kind = getIntent().getIntExtra("kind", 0);
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("zdbm",getIntent().getStringExtra("zdbm"));
//        switch (kind) {
//            case 0://安装服务类型
//                mParmMap.put("zdbm", "AZFWLX");
//                break;
//            case 1://安装登记类型
//                mParmMap.put("zdbm", "AZDJLX");
//                break;
//            case 2://优先级
//                mParmMap.put("zdbm", "FWYXJ");
//                break;
//            case 3://维修服务类型（同ZDBM=’FWFS’,为了统一另加了这个）
//                mParmMap.put("zdbm", "WXFWLX");
//                break;
//            case 4://维修登记类型
//                mParmMap.put("zdbm", "WXDJLX");
//                break;
//            case 5://安装登记状态
//                mParmMap.put("zdbm", "AZDJZT");
//                break;
//            case 6://安装登记状态
//                mParmMap.put("zdbm", "AZDJZT");
//                break;
//        }
        presenter.post(0, ServerURL.DATADICT, mParmMap);
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
        titlebar.setTitleText(this, getIntent().getStringExtra("title"));
//        switch (kind) {
//            case 0://服务方式
//
//                break;
//            case 1://登记类别
//                titlebar.setTitleText(this, "登记类别选择");
//                break;
//            case 2://优先级
//                titlebar.setTitleText(this, "优先级选择");
//                break;
//            case 3://维修服务类型（同ZDBM=’FWFS’,为了统一另加了这个）
//                titlebar.setTitleText(this, "维修服务类型选择");
//                break;
//            case 4://维修登记类型
//                titlebar.setTitleText(this, "维修登记类型选择");
//                break;
//            case 5://安装登记状态
//                titlebar.setTitleText(this, "安装登记状态选择");
//                break;
//        }

    }

    /**
     * 网路请求返回数据
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    @Override
    public void returnData(int requestCode, Object data) {
        Gson gson = new Gson();
        mList = gson.fromJson((String) data,
                new TypeToken<List<DataDictionaryData>>() {
                }.getType());
        if(getIntent().getBooleanExtra("isAll",false)){
            mList.add(new DataDictionaryData(0,"全部"));
        }
        rvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.StateAuditChoiceHolder,DataDictionaryData>( mList) {
//
            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getStateAuditChoiceHolder(NetworkDataSingleOptionActivity.this,parent);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.StateAuditChoiceHolder holder, final DataDictionaryData data) {
                holder.tvText.setText(data.getDictmc());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_OK, new Intent().putExtra("CHOICE_RESULT_TEXT", data.getDictmc())
                                .putExtra("CHOICE_RESULT_ID",data.getId()+""));
                        finish();
                    }
                });
            }

        });
    }
}
