package com.update.actiity.choose;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.base.BaseRecycleAdapter;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.util.Arrays;

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
public class LocalDataSingleOptionActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private String[] mStrings;
    private String[] mResults;
    private int kind;//

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        kind = getIntent().getIntExtra("kind", 0);
        switch (kind) {
            case 0://审核状态（安装登记列表）
                mStrings = new String[]{"未审核", "已审核", "审核中", "全部"};
                mResults = new String[]{"0", "1", "2", "9"};
                break;
            case 1://审核状态（安装登记列表）
            case 2:
                mStrings = new String[]{"否", "是"};
                mResults = new String[]{"0", "1"};
                break;
            case 3://物流类型 0-收货 1-发货
                mStrings = new String[]{"收货", "发货"};
                mResults = new String[]{"0", "1"};
                break;
            case 4://运费承担 0我方 1对方
                mStrings = new String[]{"我方", "对方"};
                mResults = new String[]{"0", "1"};
                break;
            case 5://我的审核列表审核状态
                //审核状态 (0未审 1已审 2 审核中   9我的审核 -1全部)
                mStrings = new String[]{"未审核", "已审核", "审核中","我的审核" ,"全部"};
                mResults = new String[]{"0", "1", "2", "9","-1"};
                break;

        }

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
        rvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.StateAuditChoiceHolder, String>(Arrays.asList(mStrings)) {

            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder(ViewGroup parent) {
                return ViewHolderFactory.getStateAuditChoiceHolder(LocalDataSingleOptionActivity.this,parent);
            }

            @Override
            protected void MyonBindViewHolder(final ViewHolderFactory.StateAuditChoiceHolder holder, final String data) {
                holder.tvText.setText(data);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_OK, new Intent().putExtra("CHOICE_RESULT_TEXT", data)
                                .putExtra("CHOICE_RESULT_ID", mResults[holder.getLayoutPosition()]));
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
        titlebar.setTitleText(this, "请选择");
//        switch (kind) {
//            case 0://审核状态（安装登记列表）
//                titlebar.setTitleText(this, "审核状态选择");
//                break;
//            case 1://审核状态（安装登记列表）
//                titlebar.setTitleText(this, "重新派工选择");
//                break;
//            case 2://
//                titlebar.setTitleText(this, "请选择");
//                break;
//            case 3://
//                titlebar.setTitleText(this, "请选择");
//                break;
//        }

    }

}
