package com.update.actiity.choose;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1363655717 on 2018-04-07.
 */

public class ScreeningAuditActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_document_type)
    TextView tvDocumentType;
    @BindView(R.id.et_unit_name)
    EditText etUnitName;
    @BindView(R.id.tv_audit_status)
    TextView tvAuditStatus;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.et_document_number)
    EditText etDocumentNumber;


    private int kind;
    String mZdbm;
    private TimePickerView mTimePickerView;//时间选择弹窗
    private  String mBilltypeid;//单据类型ID
    private String mDepartmentid;//部门ID
    private String mEmpid;//业务员
    private String mShzt;//(0未审 1已审 2 审核中   9全部)


    @Override
    protected void initVariables() {
        kind = getIntent().getIntExtra("kind", 0);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_screening_audit;
    }

    @Override
    protected void init() {
        Date date = new Date();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day=aCalendar.getActualMaximum(Calendar.DATE);
        String rq=DateUtil.DateToString(date, "yyyy-MM-");
        tvStartTime.setText(rq + "01");
        if(day>9)
            tvEndTime.setText(rq+day);
        else
            tvEndTime.setText(rq+"0"+day);

        mShzt = "9";
        tvAuditStatus.setText("我的审核");
        mDepartmentid = "0";
        mEmpid = "0";

        setTitlebar();

    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {

        titlebar.setTitleText(this, "筛选");
    }



    public void selectTime(final int i) {
        if (mTimePickerView == null)
            mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mTimePickerView.setTime(new Date());
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                switch (i) {
                    case 0:
                        tvStartTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                    case 1:
                        tvEndTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
                        break;
                }

            }
        });
        mTimePickerView.show();
    }

    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.ll_document_type, R.id.ll_audit_status, R.id.ll_department, R.id.ll_salesman, R.id.cx_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start_time:
                selectTime(0);
                break;
            case R.id.ll_end_time:
                selectTime(1);
                break;
            case R.id.ll_document_type:
                startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                        .putExtra("zdbm", "BILLTYPENAME").putExtra("title", "单据类型选择"), 11);
                break;
            case R.id.ll_audit_status:
                startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class).putExtra("kind",5), 12);
                break;
            case R.id.ll_department:
                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 13);
                break;
            case R.id.ll_salesman:
                startActivityForResult(new Intent(this, SelectSalesmanActivity.class)
                        .putExtra("depid", mDepartmentid ), 14);
                break;
            case R.id.cx_imageview://查询
                Intent intent = new Intent();
                intent.putExtra("qsrq", tvStartTime.getText().toString());
                intent.putExtra("zzrq", tvEndTime.getText().toString());
                intent.putExtra("cname", etUnitName.getText().toString());
                intent.putExtra("shzt", mShzt);
                intent.putExtra("depid", mDepartmentid);
                intent.putExtra("empid", mEmpid);
                intent.putExtra("billcode", etDocumentNumber.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11://单据类型
                mBilltypeid = data.getStringExtra("CHOICE_RESULT_ID");
                tvDocumentType.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 12://审核状态
                tvAuditStatus.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                mShzt = data.getStringExtra("CHOICE_RESULT_ID");
                break;
            case 13://部门
                mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                tvDepartment.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;
            case 14://业务员
                mEmpid = data.getStringExtra("CHOICE_RESULT_ID");
                tvSalesman.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                break;

        }
    }


}
