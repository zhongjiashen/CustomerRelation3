package com.update.actiity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.cr.activity.SLView2;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.base.BaseActivity;
import com.update.model.GoodsOrOverviewData;
import com.update.viewbar.TitleBar;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/5 0005 下午 4:22
 * Description:增加概况
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/5 0005         申中佳               V1.0
 */
public class IncreaseOverviewActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.et_overview)
    EditText etOverview;
    @BindView(R.id.sl_view)
    SLView2 slView;
    private GoodsOrOverviewData mOverviewData;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mOverviewData = new GoodsOrOverviewData();

    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_increase_overview;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "增加概况");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 2:
                        String overview = etOverview.getText().toString();//获取输入的概况信息
                        if (TextUtils.isEmpty(overview))//判断输入的概况信息是非非空
                            showShortToast("请输入概况信息");
                        else {
                            mOverviewData.setBillid("0");//单据ID;为0或空时表示新增
                            mOverviewData.setLb("0");//概况=0，商品=1
                            mOverviewData.setGoodsid("0");
                            mOverviewData.setGoodsname(overview);
                            mOverviewData.setUnitqty(slView.getSl()+"");//数量
                            setResult(RESULT_OK,new Intent().putExtra("DATA",new Gson().toJson(mOverviewData)));
                            finish();
                        }
                        break;

                }
            }
        });
    }
}