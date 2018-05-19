package com.update.actiity.choose;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.crcxj.activity.R;
import com.update.base.BaseActivity;
import com.update.utils.DateUtil;
import com.update.viewbar.TitleBar;

import java.net.URISyntaxException;
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
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_a)
    TextView tvA;
    @BindView(R.id.tv_b)
    TextView tvB;
    @BindView(R.id.tv_b_text)
    TextView tvBText;
    @BindView(R.id.ll_b)
    LinearLayout llB;
    @BindView(R.id.tv_c_text)
    TextView tvCText;
    @BindView(R.id.tv_c)
    TextView tvC;
    @BindView(R.id.ll_c)
    LinearLayout llC;
    @BindView(R.id.ll_et_b)
    LinearLayout llEtB;
    @BindView(R.id.et_a)
    EditText etA;
    @BindView(R.id.et_b)
    EditText etB;


    private TimePickerView mTimePickerView;//时间选择弹窗

    private int kind;

    private String aId;
    private String bId;
    private String cId;

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
        switch (kind) {
            case 0:
            case 2:
                aId = "0";
                bId = "0";
                break;
            case 1:
                aId = "0";
                bId = "0";
                cId = "0";
                tvBText.setText("执行结果");
                llC.setVisibility(View.VISIBLE);
                tvCText.setText("技术人员");
                llEtB.setVisibility(View.VISIBLE);
                break;
            case 3://检测维修
                aId = "0";
                bId = "0";
                cId = "0";
                tvBText.setText("维修结果");
                llC.setVisibility(View.VISIBLE);
                tvCText.setText("维修人员");
                llEtB.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 标题头设置
     */
    private void setTitlebar() {

        titlebar.setTitleText(this, "筛选");
    }

    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.ll_a, R.id.ll_b, R.id.ll_c, R.id.cx_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start_time:
                selectTime(0);
                break;
            case R.id.ll_end_time:
                selectTime(1);
                break;
            case R.id.ll_a:
                switch (kind) {
                    case 0://审核状态
                        startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class), 11);
                        break;
                    case 1://审核状态
                        startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class), 11);
                        break;
                    case 2://审核状态
                        startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class), 11);
                        break;
                    case 3://审核状态
                        startActivityForResult(new Intent(this, LocalDataSingleOptionActivity.class), 11);
                        break;
                }
                break;
            case R.id.ll_b:
                switch (kind) {
                    case 0://登记状态
                        startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                                .putExtra("zdbm", "AZDJZT").putExtra("title", "安装登记状态选择"), 12);
                        break;
                    case 1://执行结果
                        startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                                .putExtra("zdbm", "AZJG").putExtra("title", "执行结果选择"), 12);
                        break;
                    case 2://维修登记状态
                        startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                                .putExtra("zdbm", "WXDJZT").putExtra("title", "执行结果选择"), 12);
                        break;
                    case 3://维修结果
                        startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                                .putExtra("zdbm", "WXJG").putExtra("title", "维修结果选择"), 12);
                        break;
                }
                break;
            case R.id.ll_c:
                switch (kind) {
                    case 1://技术人员
                    case 3://技术人员
                        startActivityForResult(new Intent(this, NetworkDataSingleOptionActivity.class)
                                .putExtra("zdbm", "WXRY").putExtra("title", "技术人员选择"), 13);
                        break;
                }
                break;
            case R.id.cx_imageview:
                Intent intent = new Intent();
                switch (kind) {
                    case 0:
                    case 2:
                        intent.putExtra("qsrq", tvStartTime.getText().toString());
                        intent.putExtra("zzrq", tvEndTime.getText().toString());
                        intent.putExtra("shzt", aId);
                        intent.putExtra("djzt", bId);
                        intent.putExtra("cname", etA.getText().toString());
                        break;
                    case 1:
                    case 3:
                        intent.putExtra("qsrq", tvStartTime.getText().toString());
                        intent.putExtra("zzrq", tvEndTime.getText().toString());
                        intent.putExtra("shzt", aId);
                        intent.putExtra("fwjg", bId);
                        intent.putExtra("fwry", cId);
                        intent.putExtra("cname", etA.getText().toString());
                        intent.putExtra("goodsname", etB.getText().toString());
                        break;
                }
                setResult(RESULT_OK, intent);
                finish();
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

    @Override
    public void onMyActivityResult(int requestCode, int resultCode, Intent data) throws URISyntaxException {
        super.onMyActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11://审核状态
                tvA.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                aId = data.getStringExtra("CHOICE_RESULT_ID");
                break;
            case 12://审核状态
                tvB.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                bId = data.getStringExtra("CHOICE_RESULT_ID");
                break;
            case 13://审核状态
                tvC.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                cId = data.getStringExtra("CHOICE_RESULT_ID");
                break;
        }
    }
}
