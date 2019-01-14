package com.update.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.cr.myinterface.SelectValueChange;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateSelectDialog {
    private OnDialogClickInterface mDialogClickInterface;
    private DatePickerDialog dateDialog = null;
    private Activity mActivity;
    private long mQsrq;

    public DateSelectDialog(Activity activity, long qsrq, OnDialogClickInterface dialogClickInterface) {
        mDialogClickInterface = dialogClickInterface;
        mActivity = activity;
        mQsrq = qsrq;
        init();
    }

    private void init() {
        DatePickerDialog.OnDateSetListener otsl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month,
                                  int dayOfMonth) {
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
                Map<String, String> map = new HashMap<>();
                map.put("year", year + "");
                map.put("month", myMonth);
                map.put("day", myDay);
                mDialogClickInterface.OnClick(0,year + "-" + myMonth + "-" + myDay);
                dateDialog.dismiss();
            }
        };
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateDialog = new DatePickerDialog(mActivity, otsl, year, month, day);
        DatePicker datePicker = dateDialog.getDatePicker();
        if (mQsrq > 0)
            datePicker.setMinDate(mQsrq);

    }

    public void show() {
        dateDialog.show();
    }


}
