package com.cr.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
/**
 * 计划总结-新增项目
 * @author Administrator
 *
 */
public class JhzjXzxmActivity extends BaseActivity implements OnClickListener{
	private EditText nameEditText,xmnrEditText,xmclEditText,qsrqEditText,syrqEditText;
	private String jhid;
	private ImageView saveImageView;
	private Spinner zqSpinner;
	private CheckBox txCheckbox;
	private TimePickerDialog tpd=null;
	private DatePickerDialog dpd=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jhzj_xzxm);
		initActivity();
		addFHMethod();
//		searchZxjgDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		jhid=this.getIntent().getExtras().getString("jhid");
		nameEditText=(EditText) findViewById(R.id.name_edittext);
		xmnrEditText=(EditText) findViewById(R.id.xmnr_eidttext);
		xmclEditText=(EditText) findViewById(R.id.cl_edittext);
//		zxqkEditText=(EditText) findViewById(R.id.zxqk_edittext);
//		zxjgEditText=(EditText) findViewById(R.id.zxjg_edittext);
//		yyfxEditText=(EditText) findViewById(R.id.yyfx_edittext);
		qsrqEditText=(EditText) findViewById(R.id.ksrq_edittext);
		qsrqEditText.setEnabled(false);
		qsrqEditText.setOnClickListener(this);
//		syrqEditText=(EditText) findViewById(R.id.syrq_edittext);
//		syrqEditText.setEnabled(false);
//		syrqEditText.setOnClickListener(this);
		saveImageView=(ImageView) findViewById(R.id.save);
		saveImageView.setOnClickListener(this);
		zqSpinner=(Spinner) findViewById(R.id.zq_spinner);
		zqSpinner.setEnabled(false);
//		zxjgSpinner=(Spinner) findViewById(R.id.zxjg_spinner);
//		zxjgSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				zxjgid=zxjgList.get(arg2).getId();
//			}
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//			}
//		});
		String[]zqStrings={"一次性","按天","按周","按月"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 android.R.layout.simple_spinner_item, zqStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zqSpinner.setAdapter(adapter);
        txCheckbox=(CheckBox) findViewById(R.id.tx_checkbox);
        txCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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
//        syCheckBox=(CheckBox) findViewById(R.id.sy_checkbox);
//        syCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//				if(arg1){
//					syrqEditText.setEnabled(true);
//				}else{
//					syrqEditText.setEnabled(false);
//				}
//			}
//		});
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("jhid", jhid);
		parmMap.put("itemid","0");
		parmMap.put("items",nameEditText.getText().toString());
		parmMap.put("jhnr",xmnrEditText.getText().toString());
		parmMap.put("zdcl",xmclEditText.getText().toString());
		parmMap.put("zxcl","");
		parmMap.put("zxjg","0");
//		parmMap.put("zxyy","");
		parmMap.put("isused",txCheckbox.isChecked()?"1":"0");
//		String zq = null;
//		String itemValue=zqSpinner.getSelectedItem().toString();
//		if(itemValue.equals("一次性")){
//			zq="1";
//		}else if(itemValue.equals("按天")){
//			zq="2";
//		}else if(itemValue.equals("按周")){
//			zq="3";
//		}else if(itemValue.equals("按月")){
//			zq="4";
//		}
//		parmMap.put("pl",zq);
//		parmMap.put("qsrq",qsrqEditText.getText().toString());
		String ksrqString=qsrqEditText.getText().toString();
		String[]s=ksrqString.split(" ");
		parmMap.put("startdate",s.length>0?s[0]:"");
		parmMap.put("starttime",s.length>1?s[1]:"");
		findServiceData2(0,ServerURL.JHRWGZZJITEMSAVE,parmMap,true);
		if(txCheckbox.isChecked()){
			if(qsrqEditText.getText().toString().equals("")){
				showToastPromopt("请选择起始日期");
				return;
			}
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
			setAlarmTime(this.getApplicationContext(), calendar,0,1);
		}
	}
	@Override
	public void executeSuccess() {
		if(returnSuccessType==0){
			if(returnJson.equals("")){
				showToastPromopt("保存成功！");
				finish();
				if(this.getIntent().getExtras().getString("start").equals("0")){
					Intent intent=new Intent(JhzjXzxmActivity.this,JhzjJhxmActivity.class);
					intent.putExtra("object", this.getIntent().getExtras().getSerializable("object"));
					startActivity(intent);
				}else{
					setResult(RESULT_OK);
					finish();
				}
			}else{
				showToastPromopt("保存失败；"+returnJson.substring(5));
			}
		}else if(returnSuccessType==1){
//			zxjgList=SJZD.parseJsonToObject(returnJson);
//			String[] bffsString = new String[zxjgList.size()];
//            for (int i = 0; i < zxjgList.size(); i++) {
//            	bffsString[i] = zxjgList.get(i).getDictmc();
//            }
//			zxjgAdapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_spinner_item,bffsString);
//			zxjgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			zxjgSpinner.setAdapter(zxjgAdapter);
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.save:
			if(nameEditText.getText().toString().equals("")){
				showToastPromopt("计划项目不能为空");
			}else if(xmnrEditText.getText().toString().equals("")){
				showToastPromopt("计划内容不能为空");
			}else{
				searchDate();
			}
			break;
		case R.id.ksrq_edittext:
//			showDialog(0);
			tpd_init();
			dpd_init();
			break;
		case R.id.syrq_edittext:
			showDialog(1);
			break;
		default:
			break;
		}
		
	}
	@Override
	public void onExecuteFh() {
		setResult(RESULT_OK);
		super.onExecuteFh();
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
							syrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
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
	/*tpd初始化*/  
    void tpd_init(){  
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
    
    /**
	 * 连接网络的操作(查询执行结果)
	 */
//	private void searchZxjgDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("zdbm","ZXJG");
//		findServiceData2(1,ServerURL.DATADICT,parmMap,false);
//	}
}
