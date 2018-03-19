package com.update.actiity.choose;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1363655717 on 2018-03-19.
 * 筛选界面
 */

public class ScreeningActivity extends BaseActivity {
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_a)
    TextView tvA;
    @BindView(R.id.tv_b)
    TextView tvB;
    @BindView(R.id.titlebar)
    TitleBar titlebar;


    private TimePickerView mTimePickerView;//时间选择弹窗

    private int kind;

    @Override
    protected void initVariables() {
        kind = getIntent().getIntExtra("kind", 0);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_screening;
    }

    @Override
    protected void init() {
        setTitlebar();
        Date date = new Date();
        tvStartTime.setText(DateUtil.DateToString(date, "yyyy-MM-") + "01");
        tvEndTime.setText(DateUtil.DateToString(date, "yyyy-MM-dd"));
        mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {

        titlebar.setTitleText(this, "筛选");
    }

    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.tv_a, R.id.ll_b, R.id.et_unit_name, R.id.cx_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start_time:
                selectTime(0);
                break;
            case R.id.ll_end_time:
                selectTime(1);
                break;
            case R.id.tv_a:
                switch (kind){
                    case 0://审核状态
                        startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class), 11);
                        break;
                }
                break;
            case R.id.ll_b:
                switch (kind){
                    case 0:
                        startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class), 11);
                        break;
                }
                break;
            case R.id.et_unit_name:
                break;
            case R.id.cx_imageview:
                break;
        }
    }


    public void selectTime(final int i) {
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
}
