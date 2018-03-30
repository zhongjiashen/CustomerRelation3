package com.update.actiity.contract;

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
 * Date:      2018/3/30 0030 上午 10:32
 * Description:新增合同
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/30 0030         申中佳               V1.0
 */
public class AddContractActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.et_contact_number)
    EditText etContactNumber;
    @BindView(R.id.et_unit_type)
    EditText etUnitType;
    @BindView(R.id.et_contract_name)
    EditText etContractName;
    @BindView(R.id.et_contract_amount)
    EditText etContractAmount;
    @BindView(R.id.et_current_stage)
    EditText etCurrentStage;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_opportunit_name)
    TextView tvOpportunitName;
    @BindView(R.id.tv_related_projects)
    TextView tvRelatedProjects;
    @BindView(R.id.tv_document_date)
    TextView tvDocumentDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.et_abstract)
    EditText etAbstract;

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
        return R.layout.activity_contract;
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
        titlebar.setTitleText(this, "合同");
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

    @OnClick({R.id.ll_unit_name_choice, R.id.ll_contacts_choice, R.id.ll_start_time_choice, R.id.ll_end_time_choice, R.id.ll_opportunit_name_choice, R.id.ll_related_projects_choice, R.id.ll_document_date_choice, R.id.ll_department_choice, R.id.ll_salesman_choice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_name_choice:
                break;
            case R.id.ll_contacts_choice:
                break;
            case R.id.ll_start_time_choice:
                break;
            case R.id.ll_end_time_choice:
                break;
            case R.id.ll_opportunit_name_choice:
                break;
            case R.id.ll_related_projects_choice:
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
