package com.cr.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.cr.dao.TxDao;
import com.cr.model.GRRC;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class MstxGrrcDetailActivity extends BaseActivity implements OnClickListener{
	private GRRC grrc;
	private EditText btEditText,nrEditText,ksrqEditText;
	private CheckBox tzCheckbox;
	private Spinner zqSpinner;
	private ImageButton saveButton;
	private String messIsUpdate="0";//未更改
	private TimePickerDialog tpd=null;
	private DatePickerDialog dpd=null;
	private TxDao dao;
//	private String startDate,startTime;
	String zq = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mstx_grrc_detail);
		addFHMethod();
//		Log.v("dddd", this.getIntent().getExtras().getSerializable("object").toString());
		initActivity();
		if(this.getIntent().getExtras().getSerializable("object").equals("")){
			
		}else{
			grrc=(GRRC) this.getIntent().getExtras().getSerializable("object");
			searchDate();
		}
		dao=new TxDao(this.getApplicationContext());
	}
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		btEditText=(EditText) findViewById(R.id.bt_edittext);
		nrEditText=(EditText) findViewById(R.id.nr_edittext);
		tzCheckbox=(CheckBox) findViewById(R.id.tz_checkbox);
		ksrqEditText=(EditText) findViewById(R.id.ksrq_edittext);
		ksrqEditText.setOnClickListener(this);
		ksrqEditText.setEnabled(false);
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		zqSpinner=(Spinner) findViewById(R.id.zq_spinner);
		zqSpinner.setEnabled(false);
		zqSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		String[]zqStrings={"一次性","按天","按周","按月"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                 android.R.layout.simple_spinner_item, zqStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zqSpinner.setAdapter(adapter);
        ksrqEditText=(EditText) findViewById(R.id.ksrq_edittext);
		ksrqEditText.setOnClickListener(this);
		tzCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					zqSpinner.setEnabled(true);
					ksrqEditText.setEnabled(true);
				}else{
					zqSpinner.setEnabled(false);
					ksrqEditText.setEnabled(false);
					ksrqEditText.setText("");
				}
			}
		});
	}
	
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("id", grrc.getId());
		findServiceData(0,ServerURL.RCPROMPTXX,parmMap);
	}
	/**
	 * 保存个人日程
	 */
	private void saveDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("id", grrc==null?"0":grrc.getId());
		parmMap.put("title",btEditText.getText().toString());
		parmMap.put("info", nrEditText.getText().toString());
		parmMap.put("isused", tzCheckbox.isChecked()?"1":"0");
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
//		parmMap.put("startdate", startDate);
//		parmMap.put("starttime", startTime);
		String ksrqString=ksrqEditText.getText().toString();
		String[]s=ksrqString.split(" ");
		parmMap.put("startdate",s.length>0?s[0]:"");
		parmMap.put("starttime",s.length>1?s[1]:"");
		
		if(tzCheckbox.isChecked()){
			Calendar calendar=Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=null;
			try {
				date=sdf.parse(ksrqEditText.getText().toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			calendar.setTime(date);
//			Calendar calendar2=Calendar.getInstance();
//			calendar2.setTime(new Date());
//			if(calendar2.getTimeInMillis()>calendar.getTimeInMillis()){
//			    showToastPromopt("提醒日期必须大于当前日期");
//			    return;
//			}
			setAlarmTime(this.getApplicationContext(), calendar,0,0);
		}
		findServiceData(1,ServerURL.RCPROMPTSAVE,parmMap);
	}
	@Override
	public void executeSuccess() {
	    switch (returnSuccessType) {
            case 0:
//            	Log.v("dddd", returnJson);
                GRRC grrc=GRRC.parseJsonToObject2(returnJson).get(0);
                btEditText.setText(grrc.getTitle());
                nrEditText.setText(grrc.getMemo());
                tzCheckbox.setChecked(grrc.getIsprompt().equals("0")?false:true);
//                zqSpinner.setSelection(Integer.parseInt(grrc.getZq().equals("null")?"1":grrc.getZq())-1);
                if(tzCheckbox.isChecked()){
    			    ksrqEditText.setText(grrc.getQsrq());
    			}else{
    			    ksrqEditText.setText("");
    			}
                break;
            case 1:
                if(returnJson.equals("")){
                    showToastPromopt("保存成功");
                    onExecuteFh();
                }else{
                    showToastPromopt("保存失败"+returnJson.substring(5));
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
						ksrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
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
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ksrq_edittext:
			tpd_init();
			dpd_init();
			break;
		case R.id.save:
			if(validateMsg()){
				saveDate();
//				messIsUpdate="1";
//				//保存信息到数据库中
//                Map<String, Object>map=new HashMap<String, Object>();
//                map.put("name",btEditText.getText());
//                map.put("code","grrc");
//                map.put("time", ksrqEditText.getText().toString());
//                map.put("zq", zq);
//                dao.add(map);
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 返回需要更新的内容
	 */
	@Override
	public void onExecuteFh() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MstxGrrcDetailActivity.this,MstxGrrcActivity.class);
		intent.putExtra("mess", messIsUpdate);
		setResult(RESULT_OK,intent);
		super.onExecuteFh();
	}
	/**
	 * 验证用户输入的信息
	 * @return
	 */
	private boolean validateMsg(){
		if(btEditText.getText().toString().equals("")){
			showToastPromopt("标题不能为空");
			return false;
		}else if(nrEditText.getText().toString().equals("")){
			showToastPromopt("内容不能为空");
			return false;
		}
		if(tzCheckbox.isChecked()){
			if(ksrqEditText.getText().toString().equals("")){
				showToastPromopt("起始时间不能为空");
				return false;
			}
		}
		return true;
	}
	
	/*tpd初始化时间*/  
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
                        ksrqEditText.setText(ksrqEditText.getText().toString()+" "+hour+":"+myMinute+":00"); 
//                        startTime=hour+":"+myMinute+":00";
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
					ksrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
//					startDate=year + "-"+ myMonth + "-"+ myDay;
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