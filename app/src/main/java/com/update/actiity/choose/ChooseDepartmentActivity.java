package com.update.actiity.choose;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.AddInstallRegistrationActivity;
import com.update.actiity.InstallRegistrationActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.DataDictionaryData;
import com.update.model.DepartmentData;
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
public class ChooseDepartmentActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    private Map<String, Object> mParmMap;
    private List<DepartmentData> mList;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mList=new ArrayList<>();
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
//        mParmMap.put("opid", ShareUserInfo.getUserId(this));
        mParmMap.put("depid", "0");//部门id –0表示全部
        mParmMap.put("curpage", "0");//当前页
        mParmMap.put("pagesize", "20");//每页加载数据大小
        presenter.post(0, "selectdep", mParmMap);
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
        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.StateAuditChoiceHolder>(mList) {
            //
            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getStateAuditChoiceHolder(ChooseDepartmentActivity.this);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.StateAuditChoiceHolder holder, final int position) {
                holder.tvText.setText(mList.get(position).getDepname());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_OK, new Intent().putExtra("CHOICE_RESULT_TEXT", mList.get(position).getDepname())
                                .putExtra("CHOICE_RESULT_ID", mList.get(position).getId() + ""));
                        finish();
                    }
                });

            }
        });
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "选择部门");
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
                new TypeToken<List<DepartmentData>>() {
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
    public void httpFinish() {
        super.httpFinish();
    }
}
