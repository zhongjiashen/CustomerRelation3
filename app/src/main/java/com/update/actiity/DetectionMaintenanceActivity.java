package com.update.actiity;

import android.content.Intent;
import android.widget.EditText;

import com.crcxj.activity.R;
import com.update.actiity.choose.ScreeningActivity;
import com.update.base.BaseActivity;
import com.update.viewbar.TitleBar;

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

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {

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
