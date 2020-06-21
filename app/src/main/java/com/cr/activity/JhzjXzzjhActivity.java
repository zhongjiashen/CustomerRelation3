package com.cr.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cr.activity.common.CommonXzzjhActivity;
import com.cr.model.JHRW;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 计划总结-新增周计划
 * @author Administrator
 *
 */
public class JhzjXzzjhActivity extends BaseActivity implements OnClickListener{
	
	EditText ksrqEditText;
	EditText zhouEditText;
	TextView titlename;
	ImageButton saveButton;
	String zhou="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jhzj_xzzjh);
		addFHMethod();
		initActivity();
		searchDate2();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		titlename=(TextView) findViewById(R.id.titlename);
		titlename.setText("新增周计划");
		ksrqEditText=(EditText) findViewById(R.id.ksrq_edit);
		ksrqEditText.setOnClickListener(this);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		ksrqEditText.setText(sdf.format(new Date()));
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		zhouEditText=(EditText) findViewById(R.id.zhou_edit);
		zhouEditText.setOnClickListener(this);
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		if(ksrqEditText.getText().toString().equals("")){
			showToastPromopt("请选择年度");
			return;
		}
		if(zhouEditText.getText().toString().equals("")){
			showToastPromopt("请选择周");
			return;
		}
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("jhid", "0");
		parmMap.put("years", ksrqEditText.getText().toString());
		parmMap.put("weekno", zhou);
		parmMap.put("jhrq", ksrqEditText.getText().toString());
		findServiceData(0,ServerURL.JHRWGZZJZZJSAVE,parmMap);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
		if(returnSuccessType==0){
			if(returnJson.length()>5&&returnJson.substring(0, 5).equals("false")){
				showToastPromopt("保存失败,"+returnJson.substring(5));
			}else{
				showToastPromopt("保存成功！");
				setResult(RESULT_OK);
				finish();
			}
		}else if(returnSuccessType==1){
			List<Map<String, Object>> list=(List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson);
			zhou=list.get(0).get("weekno").toString();
			zhouEditText.setText(list.get(0).get("fullname").toString());
		}
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate2(){
	    Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("years", ksrqEditText.getText().toString());
        parmMap.put("istoday", "1");
//        parmMap.put("empname",searchEditText.getText().toString());
        parmMap.put("curpage",currentPage);
        parmMap.put("pagesize",pageSize);
        findServiceData2(1,ServerURL.JHRWSELECTWEEK,parmMap,true);
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
					new DatePickerDialog.OnDateSetListener() {
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
							ksrqEditText.setText(year + ""+ "" + ""+"" );
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
		case R.id.ksrq_edit:
			showDialog(0);
			break;
		case R.id.zhou_edit:
			Intent intent=new Intent();
			intent.putExtra("year", ksrqEditText.getText().toString());
			intent.setClass(this, CommonXzzjhActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.save:
			searchDate();
			break;
		default:
			break;
		}
	}
	@Override
	public void onExecuteFh() {
//		setResult(RESULT_OK);
		super.onExecuteFh();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			if(requestCode==0){
				zhouEditText.setText(data.getExtras().getString("name"));
				zhou=data.getExtras().getString("id");
			}
		}
	}
}
