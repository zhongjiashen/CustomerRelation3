package com.update.actiity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzywyActivity;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.actiity.choose.ProjectSelectionActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.base.BaseActivity;
import com.update.model.GoodsOrOverviewData;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/23 0023 下午 3:21
 * Description:添加安装登记
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/23 0023         申中佳               V1.0
 */
public class AddInstallRegistrationActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.et_contact_number)
    EditText etContactNumber;
    @BindView(R.id.et_customer_address)
    EditText etCustomerAddress;
    @BindView(R.id.tv_submit_time)
    TextView tvSubmitTime;
    @BindView(R.id.tv_document_date)
    TextView tvDocumentDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.tv_service_mode)
    TextView tvServiceMode;
    @BindView(R.id.tv_rating_category)
    TextView tvRatingCategory;
    @BindView(R.id.tv_priority)
    TextView tvPriority;
    @BindView(R.id.tv_profile_information)
    TextView tvProfileInformation;
    @BindView(R.id.tv_registration_number)
    TextView tvRegistrationNumber;
    @BindView(R.id.rl_profile_information)
    RelativeLayout rlProfileInformation;

    private TimePickerView mTimePickerView;//时间选择弹窗


    private String clientid;//客户ID
    private String lxrid;//联系人ID
    private String sxfsid;// 服务方式ID
    private String regtype;// 服务类型id
    private String priorid;// 优先级ID
    private String departmentid;// 部门id
    private String empid;//业务员ID
    private GoodsOrOverviewData mOverviewData;//概况信息
    Gson mGson;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量。
     */
    @Override
    protected void initVariables() {
        mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mGson = new Gson();
    }

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_add_install_registration;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        setTitlebar();
        rlProfileInformation.setVisibility(View.GONE);//未添加概况信息，概况信息隐藏
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {
        titlebar.setTitleText(this, "安装登记");
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

    @OnClick({R.id.ll_unit_name_choice, R.id.ll_contacts_choice, R.id.ll_related_projects_choice, R.id.ll_submit_time_choice, R.id.ll_service_mode_choice, R.id.ll_rating_category_choice, R.id.ll_priority_choice, R.id.tv_choose_goods, R.id.tv_adding_profile, R.id.rl_profile_information, R.id.ll_document_date_choice, R.id.ll_department_choice, R.id.ll_salesman_choice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_unit_name_choice://单位选择
                startActivityForResult(new Intent(this, CommonXzkhActivity.class), 11);
                break;
            case R.id.ll_contacts_choice://联系人选择
                if (TextUtils.isEmpty(clientid))
                    showShortToast("请先选择单位");
                else
                    startActivityForResult(new Intent(this, CommonXzlxrActivity.class).putExtra("clientid", clientid), 12);
                break;
            case R.id.ll_related_projects_choice://关联项目选择
                if (TextUtils.isEmpty(clientid))
                    showShortToast("请先选择单位");
                else
                    startActivityForResult(new Intent(this, ProjectSelectionActivity.class).putExtra("clientid", clientid), 12);
                break;
            case R.id.ll_submit_time_choice://报送时间选择
                selectTime(0);
                break;
            case R.id.ll_service_mode_choice://服务方式选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class), 14);
                break;
            case R.id.ll_rating_category_choice://登记类别选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class).putExtra("kind", 1), 15);
                break;
            case R.id.ll_priority_choice://优先级选择
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class).putExtra("kind", 2), 16);
                break;
            case R.id.tv_choose_goods://商品选择
                startActivityForResult(new Intent(this, ChooseGoodsActivity.class), 17);
                break;
            case R.id.tv_adding_profile://增加概况
                if (rlProfileInformation.getVisibility() == View.VISIBLE) {
                    showShortToast("已添加过概况信息");
                } else
                    startActivityForResult(new Intent(this, IncreaseOverviewActivity.class), 18);
                break;
            case R.id.rl_profile_information://概况详情查看
                startActivityForResult(new Intent(this, IncreaseOverviewActivity.class).putExtra("kind", 1)
                        .putExtra("DATA", new Gson().toJson(mOverviewData)), 19);

                break;
            case R.id.ll_document_date_choice://单据日期选择
                selectTime(1);
                break;
            case R.id.ll_department_choice://部门选择
                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 20);
                break;
            case R.id.ll_salesman_choice://业务员选择
                if (TextUtils.isEmpty(departmentid))
                    showShortToast("请先选择部门");
                else
                startActivityForResult(new Intent(this, SelectSalesmanActivity.class).putExtra("depid",departmentid), 21);
                break;
        }
    }

    public void onMyActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 11://单位选择结果处理
                clientid = data.getStringExtra("id");
                lxrid = data.getStringExtra("lxrid");
                tvUnitName.setText(data.getStringExtra("name"));
                tvContacts.setText(data.getStringExtra("lxrname"));
                etContactNumber.setText(data.getStringExtra("phone"));
                etCustomerAddress.setText(data.getStringExtra("shipto"));
                break;
            case 12://联系人选择结果处理
                lxrid = data.getStringExtra("id");
                tvContacts.setText(data.getStringExtra("name"));
                etContactNumber.setText(data.getStringExtra("phone"));
                break;
            case 13://关联项目选择结果处理

                break;
            case 14://服务方式选择结果处理
                sxfsid = data.getStringExtra("CHOICE_RESULT_ID");
                tvServiceMode.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 15://登记类别选择结果处理
                regtype = data.getStringExtra("CHOICE_RESULT_ID");
                tvRatingCategory.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 16://优先级选择结果处理
                priorid = data.getStringExtra("CHOICE_RESULT_ID");
                tvPriority.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 18://增加概况结果处理
                mOverviewData = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<GoodsOrOverviewData>() {
                }.getType());
                rlProfileInformation.setVisibility(View.VISIBLE);
                tvRegistrationNumber.setText("登记数量：" + mOverviewData.getUnitqty() + "个");
                break;
            case 19://概况详情查看结果处理
                switch (data.getIntExtra("KIND",0)){
                    case 0://修改处理结果
                        mOverviewData = mGson.fromJson(data.getStringExtra("DATA"), new TypeToken<GoodsOrOverviewData>() {
                        }.getType());
                        tvRegistrationNumber.setText("登记数量：" + mOverviewData.getUnitqty() + "个");
                        break;
                    case 1://删除处理结果
                        rlProfileInformation.setVisibility(View.GONE);
                        mOverviewData=null;
                        break;
                }
                break;
            case 20://部门选择结果处理
                departmentid = data.getStringExtra("CHOICE_RESULT_ID");
                tvDepartment.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 21://业务员选择结果处理
                empid = data.getStringExtra("id");
                tvSalesman.setText(data.getStringExtra("name"));
                break;
        }
    }

    /**
     * 时间选择器
     *
     * @param i
     */
    public void selectTime(final int i) {
        mTimePickerView.setTime(new Date());
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                switch (i) {
                    case 0:
                        tvSubmitTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                    case 1:
                        tvDocumentDate.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                }

            }
        });
        mTimePickerView.show();
    }
}
