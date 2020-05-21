package com.cr.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

import com.cr.model.GSLXRDetail;
import com.cr.model.SJZD;
import com.cr.model.XMLB;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class GzptBflrActivity extends BaseActivity implements OnClickListener{
	
	EditText yysjEditText,bfnrEditText,xmmcEditText;
	private Spinner bffsSpinner,xmjdSpinner;
	private CheckBox xcjCheckBox,yyCheckBox;
	private String xmmcId,bffsId,xmjdId;
	private ArrayAdapter<String>bffsAdapter,xmjdAdapter;
	private GSLXRDetail gslxrDetail;
	ImageButton saveButton,addButton;;
	List<XMLB>xmlbList=new ArrayList<XMLB>();
	List<SJZD>bffsList=new ArrayList<SJZD>();
	List<SJZD>xmjdList=new ArrayList<SJZD>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_bflr);
		addFHMethod();
		initActivity();
		searchBffsDate();
//		searchBffsDate();
//		searchXmjdDate();
		
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		gslxrDetail=(GSLXRDetail) this.getIntent().getSerializableExtra("object");
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		addButton=(ImageButton) findViewById(R.id.add);
		addButton.setOnClickListener(this);
		xcjCheckBox=(CheckBox) findViewById(R.id.xcj_checkbox);
		yyCheckBox=(CheckBox) findViewById(R.id.yy_checkbox);
		yysjEditText=(EditText) findViewById(R.id.yyrq_edit);
		yysjEditText.setOnClickListener(this);
		bfnrEditText=(EditText) findViewById(R.id.bfnr_edit);
//		xmmcSpinner=(Spinner) findViewById(R.id.xmmc_spinner);
		xmmcEditText=(EditText) findViewById(R.id.xmmc_edit);
		xmmcEditText.setOnClickListener(this);
		bffsSpinner=(Spinner) findViewById(R.id.bffs_spinner);
		xmjdSpinner=(Spinner) findViewById(R.id.xmjd_spinner);
		yysjEditText.setEnabled(false);
//		bfnrEditText.setEnabled(false);
		yyCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(yyCheckBox.isChecked()){
					yysjEditText.setEnabled(true);
//					bfnrEditText.setEnabled(true);
				}else{
					yysjEditText.setEnabled(false);
//					bfnrEditText.setEnabled(false);
				}
			}
		});
		/*xmmcSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				xmmcId=xmlbList.get(arg2).getId();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});*/
		bffsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				bffsId=bffsList.get(arg2).getId();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		xmjdSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				xmjdId=xmjdList.get(arg2).getId();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	/**
	 * 连接网络的操作(查询项目列表)
	 */
//	private void searchXmmcDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("clientid", gslxrDetail.getClientid());
//		parmMap.put("curpage", currentPage);
//		parmMap.put("pagesize", pageSize);
//		findServiceData2(0,ServerURL.ITEMLIST,parmMap,false);
//	}
	/**
	 * 连接网络的操作(查询拜访方式)
	 */
	private void searchBffsDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("zdbm","BFLX");
		findServiceData2(1,ServerURL.DATADICT,parmMap,false);
	}
	/**
	 * 连接网络的操作(查询项目进度)
	 */
	private void searchXmjdDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("zdbm","XMGM");
		findServiceData2(2,ServerURL.DATADICT,parmMap,false);
	}
	/**
	 * 连接网络的操作(拜访录入保存)
	 */
	private void saveBflrDate(){
//		Log.v("dddd", this.getIntent().getExtras().getString("jhbid"));
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("id","0");
		parmMap.put("jhbid",this.getIntent().getExtras().getString("jhbid"));
		parmMap.put("lxrid",gslxrDetail.getId());
		parmMap.put("rclx","1");
		parmMap.put("rcid",this.getIntent().getExtras().getString("jhbid"));
		parmMap.put("xmid",xmmcId);
		parmMap.put("bflx",bffsId);
		parmMap.put("gmid",xmjdId);
		parmMap.put("isnewcjd",xcjCheckBox.isChecked()?"1":"0");//是否是新成交
		parmMap.put("isyy",yyCheckBox.isChecked()?"1":"0");//是否预约
		parmMap.put("nextdate",yysjEditText.getText().toString());//预约日期
		parmMap.put("memo",bfnrEditText.getText().toString());//
		findServiceData2(3,ServerURL.JHRWRECORD,parmMap,false);
	}
	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
//		case 0:
//			if(!returnJson.equals("")){
//				xmlbList=XMLB.parseJsonToObject(returnJson);
//				String[] dwString = new String[xmlbList.size()];
//				for (int i = 0; i < xmlbList.size(); i++) {
//					dwString[i] = xmlbList.get(i).getTitle();
//				}
//				xmmcAdapter = new ArrayAdapter<String>(this,
//						android.R.layout.simple_spinner_item, dwString);
//				xmmcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				xmmcSpinner.setAdapter(xmmcAdapter);
//			}
//            searchBffsDate();
//			break;
		case 1:
			bffsList=SJZD.parseJsonToObject(returnJson);
			String[] bffsString = new String[bffsList.size()];
            for (int i = 0; i < bffsList.size(); i++) {
            	bffsString[i] = bffsList.get(i).getDictmc();
            }
			bffsAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,bffsString);
			bffsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bffsSpinner.setAdapter(bffsAdapter);
            searchXmjdDate();
			break;
		case 2:
			xmjdList=SJZD.parseJsonToObject(returnJson);
			String[] xmjdString = new String[xmjdList.size()];
            for (int i = 0; i < xmjdList.size(); i++) {
            	xmjdString[i] = xmjdList.get(i).getDictmc();
            }
			xmjdAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, xmjdString);
			xmjdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            xmjdSpinner.setAdapter(xmjdAdapter);
			break;
		case 3:
			if(returnJson.equals("")){
				showToastPromopt("保存成功！");
				setResult(RESULT_OK);
				onExecuteFh();
			}else{
				showToastPromopt("保存失败！"+returnJson.substring(5));
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
							yysjEditText.setText(year + "-"+ myMonth + "-"+ myDay);
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
		case R.id.yyrq_edit:
			showDialog(0);
			break;
		case R.id.save:
			if(validateMsg()){
				saveBflrDate();
			}
			break;
//		case R.id.xmmc_spinner:
//			searchXmmcDate();
//			break;
		case R.id.add:
			Intent intent=new Intent(GzptBflrActivity.this,GzptXzxmActivity.class);
			startActivityForResult(intent,0);
			break;
		case R.id.bffs_spinner:
//			searchBffsDate();
			break;
		case R.id.xmjd_spinner:
//			searchXmjdDate();
			break;
		case R.id.xmmc_edit:
			Intent intent2=new Intent(GzptBflrActivity.this,GzptSelectXzxmActivity.class);
			intent2.putExtra("clientId", gslxrDetail.getClientid());
			startActivityForResult(intent2,0);
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
		if(bfnrEditText.getText().toString().equals("")){
			showToastPromopt("拜访内容不能为空");
			return false;
		}
		if(yyCheckBox.isChecked()){
			if(yysjEditText.getText().toString().equals("")){
				showToastPromopt("预约时间不能为空");
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if(resultCode==RESULT_OK){
	        if(requestCode==0){
	            xmmcEditText.setText(data.getExtras().getString("title"));
	            xmmcId=data.getExtras().getString("id");
	        }
	    }
	}
}