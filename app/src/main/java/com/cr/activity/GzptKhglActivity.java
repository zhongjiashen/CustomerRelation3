package com.cr.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cr.activity.common.CommonXzkhlxActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.gzpt.dwzl.GzptDwzlDwBjdwActivity;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

@SuppressLint("SimpleDateFormat")
public class GzptKhglActivity extends BaseActivity implements OnClickListener {
    TextView            xzdwTextView;
    RadioButton         ybfRadioButton, wbfRadioButton;
    LinearLayout        searchLayout;
    EditText            ksrqEditText, jsrqEditText, dwEditText, xmEditText, dhEditText, qqEditText,
            lxEditText, jdEditText;
    ImageButton         cxButton, operButton;
    private String      bf   = "0";                    // 未拜访(默认)
    private String      flag = "1";
    ImageView           khglImageView;
    String              type = "kh";
    String              jdId="", lxId="", xmjd="";
    private CheckBox    xmjdCheckBox, bfCheckBox;
    private RadioButton    qbRadioButton,yxRadioButton,wxRadioButton;
//    private ImageButton fhButton;

    // private int selectIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_khgl);
         addFHMethod();
        addZYMethod();
        initActivity();
        initListView();
        // searchDate();
        searchXmjdDate();
        if (this.getIntent().hasExtra("type")) {
            type = this.getIntent().getExtras().getString("type");
            lxEditText.setText("供应商");
            lxId="2";
        }
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        // inflater=getLayoutInflater();
//        fhButton = (ImageButton) findViewById(R.id.fh);
//        fhButton.setOnClickListener(this);
        ksrqEditText = (EditText) findViewById(R.id.ksrq_edit);
        ksrqEditText.setOnClickListener(this);
        lxEditText = (EditText) findViewById(R.id.lx_edit);
        lxEditText.setOnClickListener(this);
        jdEditText = (EditText) findViewById(R.id.jd_edit);
        jdEditText.setOnClickListener(this);
        jdEditText.setText("全部");
        jdId="0";
        jdEditText.setEnabled(false);
        ksrqEditText.setOnClickListener(this);
        searchLayout = (LinearLayout) findViewById(R.id.operLayout);
        jsrqEditText = (EditText) findViewById(R.id.jsrq_edit);
        jsrqEditText.setEnabled(false);
        jsrqEditText.setOnClickListener(this);
        ksrqEditText.setEnabled(false);
        jsrqEditText.setEnabled(false);
        cxButton = (ImageButton) findViewById(R.id.cx_but);
        cxButton.setOnClickListener(this);
        ybfRadioButton = (RadioButton) findViewById(R.id.ybf);
        ybfRadioButton.setEnabled(false);
        ybfRadioButton.setOnClickListener(this);
        wbfRadioButton = (RadioButton) findViewById(R.id.wbf);
        wbfRadioButton.setEnabled(false);
        wbfRadioButton.setOnClickListener(this);
        //		qbjlRadioButton = (RadioButton) findViewById(R.id.qbbf);
        //		qbjlRadioButton.setOnClickListener(this);
        xzdwTextView = (TextView) findViewById(R.id.xzdw_textview);
        xzdwTextView.setOnClickListener(this);
        xmjdCheckBox = (CheckBox) findViewById(R.id.kglc_cmjd_check);
        xmjdCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    jdEditText.setEnabled(true);
                } else {
                    jdEditText.setEnabled(false);
                    jdEditText.setText("");
                    jdId="";
                }
            }
        });
        bfCheckBox = (CheckBox) findViewById(R.id.kglc_bf_check);
        bfCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    wbfRadioButton.setEnabled(true);
                    ybfRadioButton.setEnabled(true);
                    ksrqEditText.setEnabled(true);
                    jsrqEditText.setEnabled(true);
                    wbfRadioButton.setChecked(true);
                } else {
                    wbfRadioButton.setEnabled(false);
                    ybfRadioButton.setEnabled(false);
                    ksrqEditText.setEnabled(false);
                    jsrqEditText.setEnabled(false);
                }
            }
        });

        dwEditText = (EditText) findViewById(R.id.dw_edit);
        // jxsxmjdCheckBox=(CheckBox) findViewById(R.id.jxsyxmjd);
        if (this.getIntent().hasExtra("cname")) {
            dwEditText.setText(this.getIntent().getExtras().getString("cname"));
        }
        xmEditText = (EditText) findViewById(R.id.xm_edit);
        dhEditText = (EditText) findViewById(R.id.dh_edit);
        qqEditText = (EditText) findViewById(R.id.qq_edit);
        ybfRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    ksrqEditText.setEnabled(true);
                    jsrqEditText.setEnabled(true);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
                    ksrqEditText.setText(sdf.format(new Date()) + "01");
                    jsrqEditText.setText(sdf.format(new Date())
                                         + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                } else {
                    ksrqEditText.setEnabled(true);
                    jsrqEditText.setEnabled(true);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
                    ksrqEditText.setText(sdf.format(new Date()) + "01");
                    jsrqEditText.setText(sdf.format(new Date())
                                         + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
            }
            
        });
        wbfRadioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    ksrqEditText.setEnabled(true);
                    jsrqEditText.setEnabled(true);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
                    ksrqEditText.setText(sdf.format(new Date()) + "01");
                    jsrqEditText.setText(sdf.format(new Date())
                                         + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                } else {
                    ksrqEditText.setEnabled(true);
                    jsrqEditText.setEnabled(true);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
                    ksrqEditText.setText(sdf.format(new Date()) + "01");
                    jsrqEditText.setText(sdf.format(new Date())
                                         + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
            }
            
        });
        lxEditText.setText("客户");
        lxId="1";
//        qbRadioButton=(RadioButton) findViewById(R.id.qb);
//        qbRadioButton.setSelected(true);
//        yxRadioButton=(RadioButton) findViewById(R.id.yx);
//        wxRadioButton=(RadioButton) findViewById(R.id.wx);
    }

    /**
     * 初始化ListView
     */
    private void initListView() {

    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("types", lxId);
        parmMap.put("dwmc", dwEditText.getText().toString());
        parmMap.put("lxrname", xmEditText.getText().toString());
        parmMap.put("phone", dhEditText.getText().toString());
        parmMap.put("qq", qqEditText.getText().toString());
        parmMap.put("xmjd", xmjdCheckBox.isChecked() ? jdId : "-1");
        parmMap.put("flag", flag);
        parmMap.put("zt", bfCheckBox.isChecked() ? wbfRadioButton.isChecked()?"0":"1" : "-1");
        parmMap.put("qsrq", ksrqEditText.getText().toString());
        parmMap.put("zzrq", jsrqEditText.getText().toString());
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        Intent intent = new Intent(this, GzptKhglResultActivity.class);
        intent.putExtra("object", (Serializable) parmMap);
        startActivity(intent);
    }

    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {

        } else if (returnSuccessType == 1) {

        } else if (returnSuccessType == 2) {

        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.cx_but:
                if(xmjdCheckBox.isChecked()){
                    if(jdId.equals("")){
                        showToastPromopt("请选择项目进度");
                        return;
                    }
                }
                searchDate();
                break;
            case R.id.xzdw_textview:
                intent.setClass(GzptKhglActivity.this, GzptDwzlDwBjdwActivity.class);
                intent.putExtra("clientid", "0");// 单位ID为0，表示新增
                startActivity(intent);
                break;
            case R.id.ksrq_edit:
                date_init(ksrqEditText);
                break;
            case R.id.jsrq_edit:
                date_init(jsrqEditText);
                break;
            case R.id.wbf:
                bf = "0";
                flag = "1";
                break;
            case R.id.ybf:
                bf = "1";
                flag = "1";
                break;
            case R.id.qbbf:
                flag = "0";
                break;
            case R.id.lx_edit:
                intent.setClass(GzptKhglActivity.this, CommonXzkhlxActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.jd_edit:
                intent.setClass(GzptKhglActivity.this, CommonXzzdActivity.class);
                intent.putExtra("type", "*XMGM");
                startActivityForResult(intent, 2);
                break;
            default:
                break;
        }

    }

    /**
     * 创建日期及时间选择对话框
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        Calendar c = null;
        switch (id) {
            case 0:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
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
            case 1:
                c = Calendar.getInstance();
                dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
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
                        jsrqEditText.setText(year + "-" + myMonth + "-" + myDay);
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
    }

    /**
     * 连接网络的操作(查询项目进度)
     */
    private void searchXmjdDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("zdbm", "XMGM");
        findServiceData2(1, ServerURL.DATADICT, parmMap, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                searchDate();
            }else if(requestCode==1){
                lxEditText.setText(data.getExtras().getString("name"));
                lxId=data.getExtras().getString("id");
                String name=data.getExtras().getString("name");
                if(name.equals("客户")||name.equals("渠道")){
                	xmjdCheckBox.setEnabled(true);
                }else{
                	xmjdCheckBox.setEnabled(false);
                }
            }else if(requestCode==2){
                jdEditText.setText(data.getExtras().getString("dictmc"));
                jdId=data.getExtras().getString("id");
            }
        }
    }
}
