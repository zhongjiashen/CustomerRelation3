package com.cr.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.utils.DateUtil;

public class JhzjXzzdyjhActivity extends BaseActivity implements OnClickListener {

    EditText ksrqEditText;
    TextView titlename;
    ImageButton saveButton;

    private TimePickerView mTimePickerView;//时间选择弹窗

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhzj_xzrjh);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        titlename = (TextView) findViewById(R.id.titlename);
        titlename.setText("新增年计划");
        ksrqEditText = (EditText) findViewById(R.id.ksrq_edit);
        ksrqEditText.setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        ksrqEditText.setText(sdf.format(new Date()));
        saveButton = (ImageButton) findViewById(R.id.save);
        saveButton.setOnClickListener(this);
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("jhid", "0");
        parmMap.put("nd", ksrqEditText.getText().toString());
        findServiceData(0, "jhrwgzzjnzjsave", parmMap);
    }

    @Override
    public void executeSuccess() {
        if (returnJson.length() > 5 && returnJson.substring(0, 5).equals("false")) {
            showToastPromopt("保存失败," + returnJson.substring(5));
        } else {
            showToastPromopt("保存成功！");
            setResult(RESULT_OK);
            finish();
        }
    }

    /**
     * 时间选择器
     *

     */
    public void selectTime() {
        if (mTimePickerView == null) {
            mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR);
        }
        mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                //单据日期
                ksrqEditText.setText(DateUtil.DateToString(date, "yyyy"));



        }
    });

		mTimePickerView.setTime(new

    Date());
		mTimePickerView.show();
}

   /* *//**
     * 创建日期及时间选择对话框
     *//*
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        Calendar c = null;
        switch (id) {
            case 0:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                month++;
                                String myMonth = "";
                                String myDay = "";
                                if (month < 10) {
                                    myMonth = "0" + month;
                                } else {
                                    myMonth = "" + month;
                                }
                                if (dayOfMonth < 10) {
                                    myDay = "0" + dayOfMonth;
                                } else {
                                    myDay = "" + dayOfMonth;
                                }
                                ksrqEditText.setText(year + "-" + myMonth + "-" + myDay);
                            }
                        }, c.get(Calendar.YEAR),
                        // 传入年份
                        c.get(Calendar.MONTH),
                        // 传入月份
                        c.get(Calendar.DAY_OF_MONTH)
                        // 传入天数
                );
                break;
        }
        return dialog;
    }*/

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.ksrq_edit:
//                showDialog(0);
                selectTime();
                break;
            case R.id.save:
                searchDate();
                break;
            default:
                break;
        }
    }
}
