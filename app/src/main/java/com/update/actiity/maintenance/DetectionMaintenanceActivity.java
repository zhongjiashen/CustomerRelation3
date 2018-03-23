package com.update.actiity.maintenance;

import android.content.Intent;
import android.widget.EditText;

import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.choose.ScreeningActivity;
import com.update.base.BaseActivity;
import com.update.base.BaseP;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/26 0026 上午 10:49
 * Description:检测维修
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/26 0026         申中佳               V1.0
 */
public class DetectionMaintenanceActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.search)
    EditText search;


    private Map<String, Object> mParmMap;
    /*上传参数变量*/
    private int page_number;//页码
    private String start_time;//起始日期
    private String end_time;//截止日期
    private String shzt;//审核状态 (0未审 1已审 2 审核中   9全部)

    private String fwjg;//维修结果ID	是 （0全部）
    private String fwry;//维修人员ID	是 （0全部）

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        Date date = new Date();
        start_time = DateUtil.DateToString(date, "yyyy-MM-") + "01";
        end_time = DateUtil.DateToString(date, "yyyy-MM-dd");
        shzt = "9";
        fwjg = "0";
        fwry = "0";
        presenter = new BaseP(this, this);
        mParmMap = new HashMap<String, Object>();
        presenter = new BaseP(this, this);
        mParmMap.put("dbname", ShareUserInfo.getDbName(this));
        mParmMap.put("parms", "JCWX");
        mParmMap.put("clientid", "0");
        mParmMap.put("opid", ShareUserInfo.getUserId(this));
        mParmMap.put("pagesize", "10");//每页加载数据大小
        http();
    }

    private void http() {
        page_number = 1;
        mParmMap.put("qsrq", start_time);
        mParmMap.put("zzrq", end_time);
        mParmMap.put("shzt", shzt);
        mParmMap.put("fwjg", fwjg);
        mParmMap.put("fwry", fwry);
        mParmMap.put("curpage", page_number);//当前页
        presenter.post(0, "billlistnew", mParmMap);
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_detection_maintenance;
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
        titlebar.setTitleText(this, "检测维修");
        titlebar.setIvRightImageResource(R.drawable.oper);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://打开右边侧滑菜单
                        startActivityForResult(new Intent(DetectionMaintenanceActivity.this, ScreeningActivity.class)
                                .putExtra("kind", 3), 11);
                        break;
                }
            }
        });
    }
}
