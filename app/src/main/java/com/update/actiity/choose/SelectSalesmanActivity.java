package com.update.actiity.choose;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.SalesmanData;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/6 0006 上午 10:52
 * Description:选择部门
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/6 0006         申中佳               V1.0
 */
public class SelectSalesmanActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    private Map<String, Object> mParmMap;
    private List<SalesmanData> mList;
    private List<SalesmanData> mSearchList;//搜索处理过的数据

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mSearchList = new ArrayList<>();
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
//        mParmMap.put("opid", ShareUserInfo.getUserId(this));
        mParmMap.put("depid", getIntent().getStringExtra("depid"));//部门id –0表示全部
        mParmMap.put("curpage", "0");//当前页
        mParmMap.put("pagesize", "100");//每页加载数据大小
        presenter.post(0, "employlistnew", mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_choose_department;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        search.setHint("业务员名称");
        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.StateAuditChoiceHolder,SalesmanData>(mList) {
            //
            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getStateAuditChoiceHolder(SelectSalesmanActivity.this);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.StateAuditChoiceHolder holder, final SalesmanData data) {
                holder.tvText.setText(data.getEmpname());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_OK, new Intent().putExtra("CHOICE_RESULT_TEXT", data.getEmpname())
                                .putExtra("CHOICE_RESULT_ID",data.getId() + ""));
                        finish();
                    }
                });
            }

        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = search.getText().toString();

                if (!TextUtils.isEmpty(searchText)) {
                    mSearchList.clear();
                    for (int i = 0; i < mList.size(); i++) {//模糊搜索匹配
                        if (mList.get(i).getEmpname().contains(searchText))
                            mSearchList.add(mList.get(i));
                    }
                    mAdapter.setList(mSearchList);
                } else {
                    mAdapter.setList(mList);
                }

            }
        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择业务员");
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
                new TypeToken<List<SalesmanData>>() {
                }.getType());
        mAdapter.setList(mList);

    }

    /**
     * 网络请求失败
     *
     * @param requestCode 请求码
     */
    @Override
    public void httpfaile(int requestCode) {
        super.httpfaile(requestCode);
    }

    /**
     * 网络请求结束
     */
    @Override
    public void httpFinish(int requestCode) {
        super.httpFinish( requestCode);
    }
}
