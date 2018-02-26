package com.update.actiity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.viewbar.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/26 0026 上午 10:42
 * Description:维修登记
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/26 0026         申中佳               V1.0
 */
public class MaintenanceRegistrationActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.ll_menu)
    LinearLayout llMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    @BindView(R.id.tv_audit_status)
    TextView tvAuditStatus;
    @BindView(R.id.et_unit_name)
    EditText etUnitName;

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
        return R.layout.activity_install_registration;
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
        titlebar.setTitleText(this, "维修登记");
        titlebar.setIvRightTwoImageResource(R.drawable.oper);
        titlebar.setIvRightImageResource(R.mipmap.ic_add);
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 0://增加安装登记
                        startActivity(new Intent(MaintenanceRegistrationActivity.this,AddInstallRegistrationActivity.class));
                        break;
                    case 1://打开右边侧滑菜单
                        openRightLayout();
                        break;
                }
            }
        });
    }

    // 右边菜单开关事件
    public void openRightLayout() {
        if (drawerLayout.isDrawerOpen(llMenu)) {
            drawerLayout.closeDrawer(llMenu);
        } else {
            drawerLayout.openDrawer(llMenu);
        }
    }



    @OnClick({R.id.tv_time_left, R.id.tv_time_right, R.id.ll_audit_status, R.id.bt_reset, R.id.bt_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time_left://开始时间选择
                break;
            case R.id.tv_time_right://结束时间选择
                break;
            case R.id.ll_audit_status://审核状态选择
                break;
            case R.id.bt_reset://重置
                break;
            case R.id.bt_query://查询
                break;
        }
    }
}
