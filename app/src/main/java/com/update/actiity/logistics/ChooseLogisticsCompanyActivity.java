package com.update.actiity.logistics;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.base.BaseRecycleAdapter;
import com.update.model.request.RqLogisticsCompanyData;
import com.update.viewbar.TitleBar;
import com.update.viewholder.ViewHolderFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 1363655717 on 2018-04-10.
 */

public class ChooseLogisticsCompanyActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.rcv_list)
    RecyclerView rcvList;
    private Map<String, Object> mParmMap;
    private List<RqLogisticsCompanyData> mList;

    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("opid", ShareUserInfo.getUserId(this));
        mParmMap.put("curpage", "0");//当前页
        mParmMap.put("pagesize", "100");//每页加载数据大小
        mParmMap.put("types", "6");// 客户：types=1,供应商types=2,竞争对手types=3, 渠道types=4, 员工types=5 ，物流types=6
        mParmMap.put("isused ", "1");//有效状态  0无效 ,1有效, 非0和1则为全部   （选择物流公司时传1，即只取有效的）
        presenter.post(0, "clientlist", mParmMap);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_choose_department;
    }

    @Override
    protected void init() {
        setTitlebar();
        search.setHint("名称");
        rcvList.setLayoutManager(new LinearLayoutManager(this));
        rcvList.setAdapter(mAdapter = new BaseRecycleAdapter<ViewHolderFactory.ChoiceLogisticsCompanyHolder,RqLogisticsCompanyData >(mList) {
            //
            @Override
            protected RecyclerView.ViewHolder MyonCreateViewHolder() {
                return ViewHolderFactory.getChoiceLogisticsCompanyHolder(ChooseLogisticsCompanyActivity .this);
            }

            @Override
            protected void MyonBindViewHolder(ViewHolderFactory.ChoiceLogisticsCompanyHolder holder, final RqLogisticsCompanyData  data) {
                holder.tvCode.setText(data.getCode());
                holder.tvName.setText(data.getCname());
                holder.tvType.setText(data.getTypesname());

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
                    mParmMap.put("filter ", searchText);//(模糊查询用)
                    presenter.post(0, "clientlist", mParmMap);
                }

            }
        });

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {

        titlebar.setTitleText(this, "选择");
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
                new TypeToken<List<RqLogisticsCompanyData>>() {
                }.getType());
        mAdapter.setList(mList);

    }

}

