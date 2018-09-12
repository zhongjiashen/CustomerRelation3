package com.cr.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.choose.KtAreaSelectionActivity;
import com.update.actiity.choose.KtRegionMainActivity;
import com.update.actiity.choose.NetworkDataSingleOptionActivity;
import com.update.model.DataDictionaryData;
import com.update.model.KtRegionData;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 工作平台-呼叫中心——客户管理
 */
@SuppressLint("SimpleDateFormat")
public class GzptKhglActivity extends BaseActivity implements OnClickListener {
    TextView xzdwTextView;
    RadioButton ybfRadioButton, wbfRadioButton;
    LinearLayout searchLayout;
    EditText ksrqEditText, jsrqEditText, dwEditText, xmEditText, dhEditText, qqEditText,
            lxEditText, jdEditText;
    ImageButton cxButton, operButton;

    @BindView(R.id.ll_dj)
    LinearLayout llDj;
    @BindView(R.id.tv_dj)
    TextView tvDj;
    @BindView(R.id.tv_qy)
    TextView tvQy;
    @BindView(R.id.tv_dq)
    TextView tvDq;
    private String bf = "0";                    // 未拜访(默认)
    private String flag = "1";
    ImageView khglImageView;
    String type = "kh";
    String jdId = "", xmjd = "";
    private String mTypes;//单位类型
    private String typeid;//客户等级
    private String areatypeid;//区域ID
    private String areaid;//省ID
    private String cityid;//市ID
    private String countyid;//县区ID
    private CheckBox xmjdCheckBox, bfCheckBox;
    private RadioButton qbRadioButton, yxRadioButton, wxRadioButton;
//    private ImageButton fhButton;

    // private int selectIndex;


    private List<KtRegionData> mProvinceDatas;
    private List<KtRegionData> mCityDatas;
    private List<KtRegionData> mAreaDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_khgl);
        ButterKnife.bind(this);
        addFHMethod();
        addZYMethod();
        initActivity();
        initListView();
        // searchDate();
        searchXmjdDate();
        if (this.getIntent().hasExtra("type")) {
            type = this.getIntent().getExtras().getString("type");
            lxEditText.setText("供应商");
            mTypes = "2";
        }
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {

        ksrqEditText = (EditText) findViewById(R.id.ksrq_edit);
        ksrqEditText.setOnClickListener(this);
        lxEditText = (EditText) findViewById(R.id.lx_edit);
        lxEditText.setOnClickListener(this);
        jdEditText = (EditText) findViewById(R.id.jd_edit);
        jdEditText.setOnClickListener(this);
        jdEditText.setText("全部");
        jdId = "0";
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
                    jdId = "";
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
        mTypes = "1";
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
        parmMap.put("types", mTypes);

        parmMap.put("typeid", typeid);
        parmMap.put("areatypeid", areatypeid);
        parmMap.put("areaid", areaid);
        parmMap.put("cityid", cityid);
        parmMap.put("countyid", countyid);


        parmMap.put("dwmc", dwEditText.getText().toString());
        parmMap.put("lxrname", xmEditText.getText().toString());
        parmMap.put("phone", dhEditText.getText().toString());
        parmMap.put("qq", qqEditText.getText().toString());
        parmMap.put("xmjd", xmjdCheckBox.isChecked() ? jdId : "-1");
        parmMap.put("flag", flag);
        parmMap.put("zt", bfCheckBox.isChecked() ? wbfRadioButton.isChecked() ? "0" : "1" : "-1");
        parmMap.put("qsrq", ksrqEditText.getText().toString());
        parmMap.put("zzrq", jsrqEditText.getText().toString());
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        Intent intent = new Intent(this, GzptKhglResultActivity.class);
        intent.putExtra("object", (Serializable) parmMap);
        startActivity(intent);
    }


    private void getAddressData(String levels, String parentid, int returnSuccessType) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("levels", levels);
        parmMap.put("parentid", parentid);
        findServiceData(returnSuccessType, ServerURL.AREALIST, parmMap);
    }

    @Override
    public void executeSuccess() {
        if (returnJson.equals("")) {
            showToastPromopt(2);
            return;
        }
        Gson gson = new Gson();
        switch (returnSuccessType) {
            case 0:
                mProvinceDatas = gson.fromJson(returnJson,
                        new TypeToken<List<KtRegionData>>() {
                        }.getType());
                mProvinceDatas.add(0,new KtRegionData("0","全部"));
                showAddressDialog("选择省",mProvinceDatas,0);
                break;
            case 1:
                mCityDatas = gson.fromJson(returnJson,
                        new TypeToken<List<KtRegionData>>() {
                        }.getType());
                mCityDatas.add(0,new KtRegionData("0","全部"));
                showAddressDialog("选择市", mCityDatas,1);
                break;
            case 2:
                mAreaDatas = gson.fromJson(returnJson,
                        new TypeToken<List<KtRegionData>>() {
                        }.getType());
                mAreaDatas.add(0,new KtRegionData("0","全部"));
                showAddressDialog("选择区（县）",mAreaDatas,2);
                break;
        }


    }

    @OnClick({R.id.ll_dj, R.id.ll_qy, R.id.ll_dq})
    public void onClickA(View view) {
        switch (view.getId()) {
            case R.id.ll_dj://等级选择
                Intent intent = new Intent(activity, NetworkDataSingleOptionActivity.class);
                intent.putExtra("title", "客户等级");
                switch (mTypes) {
                    case "1"://客户
                    case "4"://渠道
                        intent.putExtra("zdbm", "KHDJ");
                        break;
                    case "2"://供应商
                        intent.putExtra("zdbm", "GYSDJ");
                        break;
                    case "3"://竞争对手
                        intent.putExtra("zdbm", "DSDJ");
                        break;
                    case "6"://物流
                        intent.putExtra("zdbm", "WLDJ");
                        break;

                }
                intent.putExtra("isAll", true);
                startActivityForResult(intent, 3);
                break;
            case R.id.ll_qy://区域选择
                startActivityForResult(new Intent(activity, KtAreaSelectionActivity.class), 4);
                break;
            case R.id.ll_dq://地区选择
//                if(mProvinceDatas==null||mProvinceDatas.size()==0) {
                    getAddressData("2", "0", 0);
//                }else {
//
//                }
//                startActivityForResult(new Intent(activity, KtRegionMainActivity.class), 5);
                break;
        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.cx_but:
                if (xmjdCheckBox.isChecked()) {
                    if (jdId.equals("")) {
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
            case R.id.lx_edit://客户类型选择
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
        findServiceData2(4, ServerURL.DATADICT, parmMap, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    searchDate();
                    break;
                case 1://客户类型选择
                    lxEditText.setText(data.getExtras().getString("name"));
                    mTypes = data.getExtras().getString("id");
                    if (mTypes.equals("0") || mTypes.equals("5")) {
                        llDj.setVisibility(View.GONE);
                    } else {
                        llDj.setVisibility(View.VISIBLE);
                    }
                    typeid = "";
                    tvDj.setText("");
                    String name = data.getExtras().getString("name");
                    if (name.equals("客户") || name.equals("渠道")) {
                        xmjdCheckBox.setEnabled(true);
                    } else {
                        xmjdCheckBox.setEnabled(false);
                    }
                    break;
                case 2:
                    jdEditText.setText(data.getExtras().getString("dictmc"));
                    jdId = data.getExtras().getString("id");
                    break;
                case 3://客户等级
                    typeid = data.getStringExtra("CHOICE_RESULT_ID");
                    tvDj.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                    break;
                case 4://区域
                    areatypeid = data.getStringExtra("id");
                    tvQy.setText(data.getStringExtra("name"));
                    break;
                case 5:
                    tvDq.setText(data.getStringExtra("text"));
                    /*areaid  省ID
                    cityid 市ID
                    countyid 县区ID*/
                    areaid = data.getStringExtra("provinceId");
                    cityid = data.getStringExtra("cityId");
                    countyid = data.getStringExtra("areaId");
                    break;

            }

        }
    }


    /**
     * 选择省
     */
    private void showAddressDialog(String title, final List<KtRegionData> list, final int kind) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);

        String[] strings = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strings[i] = list.get(i).getCname();
        }
        // 设置一个下拉的列表选择项
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (kind) {
                    case 0:
                        areaid = list.get(which).getId();
                        if (areaid.equals("0")) {
                            tvDq.setText("全部");
                            cityid = "0";
                            countyid = "0";
                        }else {
                            tvDq.setText(list.get(which).getCname());
                            getAddressData("3",areaid,1);
                        }
                        break;
                    case 1:
                        cityid = list.get(which).getId();
                        if (areaid.equals("0")) {
                            tvDq.setText(tvDq.getText().toString()+"-"+list.get(which).getCname());
                            countyid = "0";
                        }else {
                            tvDq.setText(tvDq.getText().toString()+"-"+list.get(which).getCname());
                            getAddressData("4",areaid,2);
                        }
                        break;
                    case 2:
                        countyid = list.get(which).getId();
                        tvDq.setText(tvDq.getText().toString()+"-"+list.get(which).getCname());

                        break;
                }
            }
        });
        builder.show();
    }

}
