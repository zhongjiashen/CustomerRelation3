package com.cr.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.cr.model.SJZD;
import com.cr.model.XMLB;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class GzptBflrXzxmActivity extends BaseActivity implements OnClickListener{
	
	EditText jhrqEditText,qsrqEditText,jhxmEditText,jhnrEditText,clEditText;
	private Spinner jhlxSpinner,zqSpinner;
	private CheckBox txCheckBox;
//	private String jhId,zqId;
//	int i=0;
	private ArrayAdapter<String>jhlxAdapter,zqAdapter;
	private TimePickerDialog tpd=null;
	private DatePickerDialog dpd=null;
//	private GSLXRDetail gslxrDetail;
	ImageButton saveButton;
	List<XMLB>jhlxList=new ArrayList<XMLB>();
	List<SJZD>zqList=new ArrayList<SJZD>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_bflr_xzxm);
		addFHMethod();
		initActivity();
//		searchXmmcDate();
//		searchBffsDate();
//		searchXmjdDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
//		gslxrDetail=(GSLXRDetail) this.getIntent().getSerializableExtra("object");
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		txCheckBox=(CheckBox) findViewById(R.id.tx_checkbox);
		jhrqEditText=(EditText) findViewById(R.id.jhrq_edit);
		jhrqEditText.setOnClickListener(this);
		jhxmEditText=(EditText) findViewById(R.id.jhxm_edit);
		jhnrEditText=(EditText) findViewById(R.id.jhnr_edit);
		clEditText=(EditText) findViewById(R.id.cl_edit);
		qsrqEditText=(EditText) findViewById(R.id.qsrq_edit);
		qsrqEditText.setOnClickListener(this);
		jhlxSpinner=(Spinner) findViewById(R.id.jhlx_spinner);
		String[]jhlxStrings={"日计划","月计划","年计划"};
		jhlxAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, jhlxStrings);
		jhlxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		jhlxSpinner.setAdapter(jhlxAdapter);
		zqSpinner=(Spinner) findViewById(R.id.zq_spinner);
		String[]zqStrings={"一次性","按天","按星期","按月"};
		zqAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, zqStrings);
		zqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		zqSpinner.setAdapter(zqAdapter);
		zqSpinner.setEnabled(false);
		qsrqEditText.setEnabled(false);
		txCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					zqSpinner.setEnabled(true);
					qsrqEditText.setEnabled(true);
				}else{
					zqSpinner.setEnabled(false);
					qsrqEditText.setEnabled(false);
				}
			}
		});
	}
	/**
	 * 连接网络的操作(拜访录入新增项目保存)
	 */
	private void saveBflrDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("itemid","0");
		parmMap.put("clientid",ShareUserInfo.getKey(context, "clientid"));
		parmMap.put("title",jhxmEditText.getText().toString());
		String zq = null;
		String itemValue=zqSpinner.getSelectedItem().toString();
		if(itemValue.equals("一次性")){
			zq="1";
		}else if(itemValue.equals("按天")){
			zq="2";
		}else if(itemValue.equals("按周")){
			zq="3";
		}else if(itemValue.equals("按月")){
			zq="4";
		}
		findServiceData2(0,ServerURL.ITEMSAVE,parmMap,true);
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date=sdf.parse(qsrqEditText.getText().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      calendar.setTime(date);
      setAlarmTime(this.getApplicationContext(), calendar,Integer.parseInt(zq)-1,1);
	}
	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
		case 0:
			if(returnJson.equals("")){
				showToastPromopt("保存成功！");
				onExecuteFh();
			}else{
				showToastPromopt("保存失败！");
			}
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
			dialog = new DatePickerDialog(this,
					new OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							month++;
							String myMonth="";
							String myDay="";
							if(month<10){
								myMonth="0"+month;
							}else{
								myMonth=""+month;
							}
							if(dayOfMonth<10){
								myDay="0"+dayOfMonth;
							}else{
								myDay=""+dayOfMonth;
							}
							jhrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
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
			dialog = new DatePickerDialog(this,
					new OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							month++;
							String myMonth="";
							String myDay="";
							if(month<10){
								myMonth="0"+month;
							}else{
								myMonth=""+month;
							}
							if(dayOfMonth<10){
								myDay="0"+dayOfMonth;
							}else{
								myDay=""+dayOfMonth;
							}
							qsrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
							tpd_init();
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

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.jhrq_edit:
			showDialog(0);
			break;
		case R.id.qsrq_edit:
//			showDialog(1);
			tpd_init();
			dpd_init();
			break;
		case R.id.save:
			if(validateMsg()){
				saveBflrDate();
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 验证用户输入的信息
	 * @return
	 */
	private boolean validateMsg(){
		if(jhrqEditText.getText().toString().equals("")){
			showToastPromopt("计划日期不能为空");
			return false;
		}else if(jhxmEditText.getText().toString().equals("")){
			showToastPromopt("计划项目不能为空");
			return false;
		}
		if(txCheckBox.isChecked()){
			if(qsrqEditText.getText().toString().equals("")){
				showToastPromopt("起始时间不能为空");
				return false;
			}
		}
		return true;
	}
	/*tpd初始化*/  
    void tpd_init(){  
//    	i++;
//    	if(i>1){
//    		return;
//    	}
        TimePickerDialog.OnTimeSetListener otsl=new TimePickerDialog.OnTimeSetListener(){ 
            int i=0;
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {  
            	String hour="";
            	if(hourOfDay<10){
            		hour="0"+hourOfDay;
            	}else{
            		hour=hourOfDay+"";
            	}
            	String myMinute="";
            	if(minute<10){
            		myMinute="0"+minute;
            	}else{
            		myMinute=minute+"";
            	}
            	if(i==0){
//                    ksrqEditText.setText(ksrqEditText.getText().toString()+" "+hour+":"+myMinute+":00"); 
                    qsrqEditText.setText(qsrqEditText.getText().toString()+" "+hour+":"+myMinute+":00"); 
                    i++;
                }
                tpd.dismiss();  
            }  
        };  
        Calendar calendar=Calendar.getInstance(TimeZone.getDefault());  
        int hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);  
        int minute=calendar.get(Calendar.MINUTE);  
        tpd=new TimePickerDialog(this,otsl,hourOfDay,minute,true); 
        tpd.show();
    }  
    /**初始化日期*/
    void dpd_init(){
    	OnDateSetListener odsl=new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {
				month++;
				String myMonth="";
				String myDay="";
				if(month<10){
					myMonth="0"+month;
				}else{
					myMonth=""+month;
				}
				if(dayOfMonth<10){
					myDay="0"+dayOfMonth;
				}else{
					myDay=""+dayOfMonth;
				}
				qsrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
				dpd.dismiss();
			}
		};
        Calendar calendar=Calendar.getInstance(TimeZone.getDefault());  
        int year=calendar.get(Calendar.YEAR);  
        int month=calendar.get(Calendar.MONTH);  
        int day=calendar.get(Calendar.DAY_OF_MONTH);  
        dpd=new DatePickerDialog(this,odsl,year,month,day); 
        dpd.show();
    }  
}