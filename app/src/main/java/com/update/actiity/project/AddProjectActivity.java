package com.update.actiity.project;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.viewbar.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/30 0030 下午 2:58
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/30 0030         申中佳               V1.0
 */
public class AddProjectActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_unit_type)
    TextView tvUnitType;
    @BindView(R.id.et_project_name)
    EditText etProjectName;
    @BindView(R.id.tv_current_stage)
    TextView tvCurrentStage;
    @BindView(R.id.tv_related_contract)
    TextView tvRelatedContract;
    @BindView(R.id.tv_project_type)
    TextView tvProjectType;
    @BindView(R.id.tv_project_source)
    TextView tvProjectSource;
    @BindView(R.id.et_budget_amount)
    EditText etBudgetAmount;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_document_date)
    TextView tvDocumentDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.et_project_purpose)
    EditText etProjectPurpose;

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
        return R.layout.activity_project;
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
        titlebar.setTitleText(this, "项目");
        titlebar.setRightText("保存");
        titlebar.setTitleOnlicListener(new TitleBar.TitleOnlicListener() {
            @Override
            public void onClick(int i) {
                switch (i) {
                    case 2:


                        break;

                }
            }
        });
    }

    @OnClick({R.id.ll_unit_name_choice, R.id.ll_related_contract_choice, R.id.ll_project_type_choice, R.id.ll_project_source_choice, R.id.ll_start_time_choice, R.id.ll_end_time_choice, R.id.ll_document_date_choice, R.id.ll_department_choice, R.id.ll_salesman_choice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_name_choice:
                break;
            case R.id.ll_related_contract_choice:
                break;
            case R.id.ll_project_type_choice:
                break;
            case R.id.ll_project_source_choice:
                break;
            case R.id.ll_start_time_choice:
                break;
            case R.id.ll_end_time_choice:
                break;
            case R.id.ll_document_date_choice:
                break;
            case R.id.ll_department_choice:
                break;
            case R.id.ll_salesman_choice:
                break;
        }
    }
}
