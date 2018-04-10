package com.update.actiity.choose;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.viewbar.TitleBar;

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


    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_choose_department;
    }

    @Override
    protected void init() {


        setTitlebar();

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {

        titlebar.setTitleText(this, "选择");
    }


}

