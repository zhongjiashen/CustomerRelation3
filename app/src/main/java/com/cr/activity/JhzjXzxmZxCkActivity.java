package com.cr.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.cr.model.JHRW;
import com.cr.model.SJZD;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 计划总结-新增项目-执行-查看
 * 
 * @author Administrator
 * 
 */
public class JhzjXzxmZxCkActivity extends BaseActivity {
	private EditText nameEditText, xmnrEditText, xmclEditText, zxqkEditText,
			yyfxEditText, qsrqEditText, syrqEditText;
	private String jhid, zxjgid;

	private Spinner zqSpinner, zxjgSpinner;
	private CheckBox txCheckbox, syCheckBox;
	private TimePickerDialog tpd = null;
	private DatePickerDialog dpd = null;
	List<SJZD> zxjgList = new ArrayList<SJZD>();
	List<Map<String, Object>> jhrwList = new ArrayList<Map<String, Object>>();
	private ArrayAdapter<String> zxjgAdapter;
	private JHRW jhrw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jhzj_xzxm_zx_ck);
		initActivity();
		addFHMethod();
		searchDate2();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		jhid = this.getIntent().getExtras().getString("jhid");
		nameEditText = (EditText) findViewById(R.id.name_edittext);
		xmnrEditText = (EditText) findViewById(R.id.xmnr_eidttext);
		xmclEditText = (EditText) findViewById(R.id.cl_edittext);
		zxqkEditText = (EditText) findViewById(R.id.zxqk_edittext);
		// zxjgEditText=(EditText) findViewById(R.id.zxjg_edittext);
		yyfxEditText = (EditText) findViewById(R.id.yyfx_edittext);
		qsrqEditText = (EditText) findViewById(R.id.ksrq_edittext);
		qsrqEditText.setEnabled(false);

		syrqEditText = (EditText) findViewById(R.id.syrq_edittext);
		syrqEditText.setEnabled(false);


		zqSpinner = (Spinner) findViewById(R.id.zq_spinner);
		zqSpinner.setEnabled(false);
		zxjgSpinner = (Spinner) findViewById(R.id.zxjg_spinner);
		zxjgSpinner.setEnabled(false);
		txCheckbox = (CheckBox) findViewById(R.id.tx_checkbox);
		txCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					zqSpinner.setEnabled(true);
					qsrqEditText.setEnabled(true);
				} else {
					zqSpinner.setEnabled(false);
					qsrqEditText.setEnabled(false);
					qsrqEditText.setText("");
				}
			}
		});
		syCheckBox = (CheckBox) findViewById(R.id.sy_checkbox);
		syCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					syrqEditText.setEnabled(true);
					Calendar calendar=Calendar.getInstance();
		            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		            calendar.add(Calendar.DAY_OF_MONTH, 1);
		            syrqEditText.setText(sdf.format(calendar.getTime()));
				} else {
					syrqEditText.setEnabled(false);
					syrqEditText.setText("");
				}
			}
		});
		jhrw = (JHRW) this.getIntent().getExtras().getSerializable("object");
	}

	
	/**
	 * 连接网络的操作（查询某个项目的信息）
	 */
	private void searchDate2() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("itemid", jhrw.getId());
		findServiceData2(2, ServerURL.JHRWGZZJITEMXX, parmMap, true);
		
	}

	@Override
	public void executeSuccess() {
		if (returnSuccessType == 0) {
			showToastPromopt("保存成功！");
			finish();
		} else if (returnSuccessType == 1) {
			zxjgList = SJZD.parseJsonToObject(returnJson);
			int selectIndex=0;
			String[] bffsString = new String[zxjgList.size()];
			for (int i = 0; i < zxjgList.size(); i++) {
				bffsString[i] = zxjgList.get(i).getDictmc();
				if(zxjgList.get(i).getId().equals(zxjgid)){
					selectIndex=i;
				}
			}
			zxjgAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, bffsString);
			zxjgAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			zxjgSpinner.setAdapter(zxjgAdapter);
			zxjgSpinner.setSelection(selectIndex);
			
		} else if (returnSuccessType == 2) {
//			Log.v("dddd", returnJson);
			if (!returnJson.equals("")) {
				jhrwList = JHRW.parseJsonToObject4(returnJson);
				Map<String, Object> map=jhrwList.get(0);
				nameEditText.setText(map.get("itemname").toString());
				xmnrEditText.setText(map.get("jhnr").toString());
				xmclEditText.setText(map.get("zdcl").toString());
				zxqkEditText.setText(map.get("zxcl").toString());
				yyfxEditText.setText(map.get("zxyy").toString());
				qsrqEditText.setText(map.get("qsrq").toString());
				syrqEditText.setText(map.get("syrq").toString().equals("null")?"":map.get("syrq").toString());
//				if(map.get("qsrq").toString().equals("")){
//					Calendar calendar=Calendar.getInstance();
//					calendar.setTime(new Date());
//					calendar.add(Calendar.DAY_OF_MONTH, 1);
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					syrqEditText.setText(sdf.format(calendar.getTime()));
//				}else{
//					syrqEditText.setText(map.get("qsrq").toString());
//				}
				if(map.get("isprompt").toString().equals("1")){
					txCheckbox.setChecked(true);
				}else{
					txCheckbox.setChecked(false);
					qsrqEditText.setText("");
				}
				if(map.get("issy").toString().equals("1")){
					syCheckBox.setChecked(true);
				}else{
					syCheckBox.setChecked(false);
					syrqEditText.setText("");
				}
//				if(map.get("pl").toString().equals("1")){
//					zqSpinner.setSelection(0);
//				}else if(map.get("pl").toString().equals("2")){
//					zqSpinner.setSelection(1);
//				}else if(map.get("pl").toString().equals("3")){
//					zqSpinner.setSelection(2);
//				}else if(map.get("pl").toString().equals("4")){
//					zqSpinner.setSelection(3);
//				}
				zxjgid=map.get("zxjg").toString();
				yyfxEditText.setText(map.get("zxyy").toString());
				searchZxjgDate();//查询执行结果
			}
		}
	}



	@Override
	public void onExecuteFh() {
		setResult(RESULT_OK);
		super.onExecuteFh();
	}


	/* tpd初始化 */
	void tpd_init() {
		TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
			int i = 0;

			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String hour = "";
				if (hourOfDay < 10) {
					hour = "0" + hourOfDay;
				} else {
					hour = hourOfDay + "";
				}
				String myMinute = "";
				if (minute < 10) {
					myMinute = "0" + minute;
				} else {
					myMinute = minute + "";
				}
				if (i == 0) {
					// ksrqEditText.setText(ksrqEditText.getText().toString()+" "+hour+":"+myMinute+":00");
					qsrqEditText.setText(qsrqEditText.getText().toString()
							+ " " + hour + ":" + myMinute + ":00");
					i++;
				}
				tpd.dismiss();
			}
		};
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		tpd = new TimePickerDialog(this, otsl, hourOfDay, minute, true);
		tpd.show();
	}

	/** 初始化日期 */
	void dpd_init() {
		OnDateSetListener odsl = new OnDateSetListener() {
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
				qsrqEditText.setText(year + "-" + myMonth + "-" + myDay);
				dpd.dismiss();
			}
		};
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		dpd = new DatePickerDialog(this, odsl, year, month, day);
		dpd.show();
	}

	/**
	 * 连接网络的操作(查询执行结果)
	 */
	private void searchZxjgDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("zdbm", "ZXJG");
		findServiceData2(1, ServerURL.DATADICT, parmMap, false);
	}

}
