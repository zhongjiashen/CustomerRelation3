package com.cr.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cr.model.GSLXRDetail;
import com.cr.model.SJZD;
import com.cr.model.XSDLB;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
/**
 * 销售单据-新增销售单
 * 
 * @author caiyanfei
 * @version $Id: GzptXsdjXzxsdActivity.java, v 0.1 2015-1-13 下午4:56:37 caiyanfei Exp $
 */
@SuppressLint("SimpleDateFormat") public class GzptXsdjXzxsdActivity extends BaseActivity implements OnClickListener{
	private EditText djrqEditText,hkrqEditText,sjEditText,dhEditText,shdzEditText,hjjeEditText,dwmcEditText,lxrEditText;
	private Spinner hkfsSpinner;
	private ArrayAdapter<String> hkfsAdapter;
	private ImageButton saveButton;
	private String dwid;
	private String lxrid;//联系人id
	List<SJZD>hkfsList=new ArrayList<SJZD>();
	private XSDLB xsdlb=null;
	private TextView titleTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_xsdj_xzxsd);
		addFHMethod();
		initActivity();
		searchHkfsDate();
//		searchLxrDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
//		dwmcSpinner=(Spinner) findViewById(R.id.dwmc);
//		dwmcSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				dwid=dwList.get(arg2).getId();
//				dwNmae=dwList.get(arg2).getName();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//			}
//		});
	    titleTextView=(TextView) findViewById(R.id.title);
	    dwmcEditText=(EditText) findViewById(R.id.dwmc);
	    dwmcEditText.setOnClickListener(this);
	    lxrEditText=(EditText) findViewById(R.id.lxr);
	    lxrEditText.setOnClickListener(this);
		hkfsSpinner=(Spinner) findViewById(R.id.hkfs);
//		String[] hkString = new String[]{"现金","网络支付"};
//		hkfsAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, hkString);
//		hkfsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        hkfsSpinner.setAdapter(hkfsAdapter);
		djrqEditText=(EditText) findViewById(R.id.djrq);
		djrqEditText.setOnClickListener(this);
		hkrqEditText=(EditText) findViewById(R.id.hkrq);
		hkrqEditText.setOnClickListener(this);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		hkrqEditText.setText(sdf.format(new Date()));
//		lxrEditText=(EditText) findViewById(R.id.lxr);
//		lxrSpinner=(Spinner) findViewById(R.id.lxr);
//		lxrSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				lxrid=lxrList.get(arg2).getId();
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//			}
//		});
		sjEditText=(EditText) findViewById(R.id.sj);
		dhEditText=(EditText) findViewById(R.id.dh);
		shdzEditText=(EditText) findViewById(R.id.shdz);
		hjjeEditText=(EditText) findViewById(R.id.hjje);
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		if(getIntent().hasExtra("object")){
			xsdlb=(XSDLB) this.getIntent().getExtras().get("object");
			djrqEditText.setText(xsdlb.getBilldate());
			hkrqEditText.setText(xsdlb.getWkrq());
			sjEditText.setText(xsdlb.getSjhm().equals("null")?"":xsdlb.getSjhm());
			dhEditText.setText(xsdlb.getPhone().equals("null")?"":xsdlb.getPhone());
			shdzEditText.setText(xsdlb.getShdz().equals("null")?"":xsdlb.getShdz());
			hjjeEditText.setText(xsdlb.getZje().equals("null")?"":xsdlb.getZje());
			dwid=xsdlb.getClientid().equals("null")?"":xsdlb.getClientid();
			lxrid=xsdlb.getLxrid().equals("null")?"":xsdlb.getLxrid();
			dwmcEditText.setText(xsdlb.getCname().equals("null")?"":xsdlb.getCname());
			lxrEditText.setText(xsdlb.getLxrname().equals("null")?"":xsdlb.getLxrname());
			titleTextView.setText("编辑销售单");
		}else{
		    titleTextView.setText("新增销售单");
//		    SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		    djrqEditText.setText(sdf.format(new Date()));
		}
		if(this.getIntent().hasExtra("object2")){
			GSLXRDetail gslxrDetail=(GSLXRDetail) this.getIntent().getExtras().getSerializable("object2");
			dwmcEditText.setText(gslxrDetail.getCname());
			dwid=gslxrDetail.getClientid();
			lxrEditText.setText(gslxrDetail.getLxrname());
			lxrid=gslxrDetail.getId();
			sjEditText.setText(gslxrDetail.getSjhm1().equals("null")?"":gslxrDetail.getSjhm1());
			dhEditText.setText(gslxrDetail.getPhone1().equals("null")?"":gslxrDetail.getPhone1());
		}
	}
	/**
	 * 连接网络的操作（保存）
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("saleid",xsdlb==null?"0":xsdlb.getSaleid());
		parmMap.put("billdate",djrqEditText.getText().toString());
		parmMap.put("clientid",dwid);
		parmMap.put("lxrid",lxrid);
		parmMap.put("phone",dhEditText.getText().toString().equals("null")?"":dhEditText.getText().toString());
		parmMap.put("sjhm",sjEditText.getText().toString().equals("null")?"":sjEditText.getText().toString());
		parmMap.put("shdz",shdzEditText.getText().toString().equals("null")?"":shdzEditText.getText().toString());
		parmMap.put("wkfs",hkfsSpinner.getSelectedItem().toString().equals("null")?"":hkfsSpinner.getSelectedItem().toString());
		parmMap.put("wkrq",hkrqEditText.getText().toString().equals("null")?"":hkrqEditText.getText().toString());
		findServiceData(0,ServerURL.SALEMASTERSAVE,parmMap);
	}
	/**
	 * 连接网络的操作(查询单位)
	 */
//	private void searchDwDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("opid", ShareUserInfo.getUserId(context));
//		parmMap.put("filter","");
//		parmMap.put("curpage",currentPage);
//		parmMap.put("pagesize",pageSize);
//		findServiceData(1,ServerURL.CLIENTLIST,parmMap);
//	}
	/**
	 * 连接网络的操作(查询联系人)
	 */
//	private void searchLxrDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("opid", ShareUserInfo.getUserId(context));
//		parmMap.put("filter",dwNmae);
//		parmMap.put("curpage",currentPage);
//		parmMap.put("pagesize",100);
//		findServiceData(2,ServerURL.LXRLIST,parmMap);
//	}
	/**
     * 连接网络的操作(查询联系人)
     */
    private void searchDateLxr(String lxrid){
        Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("lxrid",lxrid);
        findServiceData(4,ServerURL.LXRINFO,parmMap);
    }
	/**
	 * 连接网络的操作(查询回款方式)
	 */
	private void searchHkfsDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("zdbm","ZFFS");
		findServiceData2(3,ServerURL.DATADICT,parmMap,true);
	}
	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
		case 0:
			if(returnJson.equals("")){
				showToastPromopt("保存成功！");
//				setResult(RESULT_OK);
//				onExecuteFh();
				Intent intent=new Intent(GzptXsdjXzxsdActivity.this,GzptXsdjXsdActivity.class);
				XSDLB xsdlb=new XSDLB();
				xsdlb.setSaleid(returnJsonId);
				intent.putExtra("object", xsdlb);
				startActivity(intent);
				finish();
			}else{
				showToastPromopt("保存失败！");
			}
			break;
		case 3:
			hkfsList=SJZD.parseJsonToObject(returnJson);
			String[] bffsString = new String[hkfsList.size()];
            for (int i = 0; i < hkfsList.size(); i++) {
            	bffsString[i] = hkfsList.get(i).getDictmc();
            }
			hkfsAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,bffsString);
			hkfsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            hkfsSpinner.setAdapter(hkfsAdapter);
			break;
		case 4:
		    GSLXRDetail gslxrDetail=GSLXRDetail.parseJsonToObject(returnJson).get(0);
		    sjEditText.setText(gslxrDetail.getSjhm1());
		    dhEditText.setText(gslxrDetail.getPhone1());
		    break;
		default:
			break;
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.djrq:
			showDialog(0);
			break;
		case R.id.hkrq:
			showDialog(1);
			break;
		case R.id.save:
			if(validateMsg()){
				searchDate();
			}
			break;
		case R.id.delete:
			
			break;
		case R.id.dwmc:
		    Intent intent=new Intent(GzptXsdjXzxsdActivity.this,GzptXzdwActivity.class);
            startActivityForResult(intent,0);
		    break;
		case R.id.lxr:
		    if(dwid==null||dwid.equals("")){
		        showToastPromopt("请先选择单位名称");
		        return;
		    }
		    Intent intent2=new Intent(GzptXsdjXzxsdActivity.this,GzptXzlxrActivity.class);
		    intent2.putExtra("dwmc",dwmcEditText.getText().toString());
		    intent2.putExtra("dwid",dwid);
            startActivityForResult(intent2,1);
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
							djrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
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
							hkrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
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
	public void onExecuteFh() {
		setResult(RESULT_OK);
		super.onExecuteFh();
	}
	/**
	 * 验证用户输入的信息
	 * @return
	 */
	private boolean validateMsg(){
		if(djrqEditText.getText().toString().equals("")){
			showToastPromopt("单据日期不能为空");
			return false;
		}else if(hkrqEditText.getText().toString().equals("")){
			showToastPromopt("回款日期不能为空");
			return false;
		}
//		else if(sjEditText.getText().toString().equals("")){
//			showToastPromopt("手机不能为空");
//			return false;
//		}
		return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(resultCode==RESULT_OK){
            if(requestCode==0){
                dwmcEditText.setText(data.getExtras().getString("name"));
                dwid=data.getExtras().getString("id");
                lxrEditText.setText("");
                lxrid="";
            }else if(requestCode==1){
                lxrEditText.setText(data.getExtras().getString("name"));
                lxrid=data.getExtras().getString("id");
                searchDateLxr(lxrid);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
