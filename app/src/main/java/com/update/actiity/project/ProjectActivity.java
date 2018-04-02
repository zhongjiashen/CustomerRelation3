package com.update.actiity.project;

import android.support.v4.util.ArrayMap;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.base.BaseActivity;
import com.update.base.BaseP;

import java.util.Map;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/4/2 0002 下午 5:37
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/4/2 0002         申中佳               V1.0
 */
public class ProjectActivity extends BaseActivity {
    private Gson mGson;
    private Map<String, Object> mParmMap;
    private String mBillid;//项目ID
    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
        mGson = new Gson();
        mParmMap = new ArrayMap<>();
        mBillid=getIntent().getStringExtra("billid");
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "XMD");
        mParmMap.put("billid", mBillid);
        presenter.post(0, ServerURL.BILLMASTER, mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_project;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {

    }
}
