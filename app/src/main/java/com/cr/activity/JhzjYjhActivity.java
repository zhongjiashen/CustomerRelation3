package com.cr.activity;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.cr.adapter.JhzjYjhAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.JHRW;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JhzjYjhActivity extends BaseActivity implements OnClickListener{
	private BaseAdapter adapter;
	private XListView rjhListView;
	private JHRW jhrw;
	List<JHRW>xzjhList=new ArrayList<JHRW>();
	
	EditText ksrqEditText,jsrqEditText;
	ImageButton cxButton;
	TextView daoTextView;
	TextView titlename;
	ImageView xzjhImageView;
	boolean findState=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jhzj_yjh);
		addFHMethod();
		initActivity();
		initListView();
//		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		titlename=(TextView) findViewById(R.id.titlename);
		titlename.setText("月计划");
		rjhListView=(XListView) findViewById(R.id.xzjh_listview);
		ksrqEditText=(EditText) findViewById(R.id.ksrq_edit);
		ksrqEditText.setOnClickListener(this);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		ksrqEditText.setText(sdf.format(new Date()));
		jsrqEditText=(EditText) findViewById(R.id.jsrq_edit);
		jsrqEditText.setOnClickListener(this);
		jsrqEditText.setVisibility(View.GONE);
		cxButton=(ImageButton) findViewById(R.id.cx_but);
		daoTextView=(TextView) findViewById(R.id.dao);
		daoTextView.setVisibility(View.GONE);
		cxButton.setOnClickListener(this);
		xzjhImageView=(ImageView) findViewById(R.id.xzjh);
		xzjhImageView.setOnClickListener(this);
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new JhzjYjhAdapter(xzjhList, context, this);
		rjhListView.setXListViewListener(xListViewListener);
		rjhListView.setPullLoadEnable(true);
		rjhListView.setPullRefreshEnable(false);
		rjhListView.setAdapter(adapter);
		rjhListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent(context,JhzjJhxmActivity.class);
				JHRW jhrw=xzjhList.get(arg2-1);
				intent.putExtra("object",jhrw);
				startActivity(intent);
			}
		});
//		rjhListView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				longClickDialog("", 0,arg2);
//				jhrw=xzjhList.get(arg2);
//				return true;
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
		String time=ksrqEditText.getText().toString();
		String []times=time.split("-");
		parmMap.put("years",times[0] );
		parmMap.put("months", times[1]);
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0,ServerURL.JHRWGZZJYZJ,parmMap,true);
	}
	@Override
	public void executeSuccess() {
	    findState=true;
//		xzjhList.clear();
		if(returnJson.equals("")){
			showToastPromopt(2);
		}else{
			xzjhList.addAll(JHRW.parseJsonToObject2(returnJson));
		}
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
//							String myDay="";
							if(month<10){
								myMonth="0"+month;
							}else{
								myMonth=""+month;
							}
//							if(dayOfMonth<10){
//								myDay="0"+dayOfMonth;
//							}else{
//								myDay=""+dayOfMonth;
//							}
							ksrqEditText.setText(year + "-"+ myMonth );
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
//							String myDay="";
							if(month<10){
								myMonth="0"+month;
							}else{
								myMonth=""+month;
							}
//							if(dayOfMonth<10){
//								myDay="0"+dayOfMonth;
//							}else{
//								myDay=""+dayOfMonth;
//							}
							jsrqEditText.setText(year + "-"+ myMonth);
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
		    if(findState){
		        xzjhList.clear();
		        currentPage=1;
		        searchDate();
		    }
			findState=false;
			break;
		case R.id.xzjh:
			Intent intent=new Intent(JhzjYjhActivity.this,JhzjXzyjhActivity.class);
			startActivity(intent);
		default:
			break;
		}
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
		rjhListView.stopRefresh();
		rjhListView.stopLoadMore();
		rjhListView.setRefreshTime(new Date().toLocaleString());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0){
			if(!ksrqEditText.getText().toString().equals("")&&!jsrqEditText.getText().toString().equals("")){
				xzjhList.clear();
				currentPage=1;
				searchDate();
			}
			
		}
	}
}