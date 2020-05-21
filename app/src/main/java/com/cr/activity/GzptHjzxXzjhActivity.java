package com.cr.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cr.adapter.GzptHjzxXzjhAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.JHRW;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class GzptHjzxXzjhActivity extends BaseActivity implements OnClickListener{
	private BaseAdapter adapter;
	private XListView yybfListView;
	List<JHRW>xzjhList=new ArrayList<JHRW>();
	
	EditText ksrqEditText,jsrqEditText;
	ImageButton cxButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_hjzx_xzjh);
		addFHMethod();
		initActivity();
		initListView();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		yybfListView=(XListView) findViewById(R.id.xzjh_listview);
		ksrqEditText=(EditText) findViewById(R.id.ksrq_edit);
		ksrqEditText.setOnClickListener(this);
		jsrqEditText=(EditText) findViewById(R.id.jsrq_edit);
		jsrqEditText.setOnClickListener(this);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-");
		ksrqEditText.setText(sdf.format(new Date())+"01");
		jsrqEditText.setText(sdf.format(new Date())+calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		cxButton=(ImageButton) findViewById(R.id.cx_but);
		cxButton.setOnClickListener(this);
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new GzptHjzxXzjhAdapter(xzjhList, mContext, this);
		yybfListView.setAdapter(adapter);
		yybfListView.setXListViewListener(xListViewListener);
		yybfListView.setPullLoadEnable(true);
		yybfListView.setPullRefreshEnable(false);
		yybfListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent(mContext,GzptHjzxHjzxActivity.class);
				JHRW jhrw=xzjhList.get(arg2-1);
				intent.putExtra("object",jhrw);
				startActivity(intent);
			}
		});
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("qsrq", ksrqEditText.getText().toString());
		parmMap.put("zzrq", jsrqEditText.getText().toString());
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0,ServerURL.GETJHBLIST,parmMap,true);
	}
	@Override
	public void executeSuccess() {
		if(returnJson.equals("")){
			showToastPromopt(2);
		}
//		xzjhList.clear();
		xzjhList.addAll(JHRW.parseJsonToObject(returnJson));
		adapter.notifyDataSetChanged();
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
							ksrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
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
							jsrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
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
		case R.id.jsrq_edit:
			showDialog(1);
			break;
		case R.id.cx_but:
			if(validMsg()){
				currentPage=1;
				xzjhList.clear();
				searchDate();
			}
			break;
		default:
			break;
		}
		
	}
	/**
	 * 查询前验证条件
	 */
	public boolean validMsg(){
		if(ksrqEditText.getText().toString().equals("")){
			showToastPromopt("请输入查询的开始日期");
			return false;
		}
		if(jsrqEditText.getText().toString().equals("")){
			showToastPromopt("请输入查询的结束日期");
			return false;
		}
		return true;
	}
	/**
	 * 刷新
	 */
	private IXListViewListener xListViewListener = new IXListViewListener() {
		@Override
		public void onRefresh() {
			handler.postDelayed(new Runnable() {//实现延迟2秒加载刷新
				@Override
				public void run() {
					//不实现顶部刷新
				}
			},2000);
		}
		@Override
		public void onLoadMore() {
			handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
				@Override
				public void run() {
					currentPage++;
					searchDate();
					onLoad();
				}
			}, 2000);
		}
	};
	@SuppressWarnings("deprecation")
	private void onLoad() {//停止刷新和加载功能，并且显示时间
		yybfListView.stopRefresh();
		yybfListView.stopLoadMore();
		yybfListView.setRefreshTime(new Date().toLocaleString());
	}
}
