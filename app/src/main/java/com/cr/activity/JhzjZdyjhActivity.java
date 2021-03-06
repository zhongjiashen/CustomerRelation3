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
import android.widget.ImageView;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.cr.adapter.JhzjZdyjhAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.JHRW;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.utils.DateUtil;

/**
 * 年计划列表
 */
public class JhzjZdyjhActivity extends BaseActivity implements OnClickListener{
	private BaseAdapter adapter;
	private XListView rjhListView;
	private JHRW jhrw;
	List<JHRW>xzjhList=new ArrayList<JHRW>();
	
	EditText ksrqEditText,jsrqEditText;
	ImageButton cxButton;
	TextView titlename;
	ImageView xzjhImageView;
	boolean             findState = true;

	private TimePickerView mTimePickerView;//时间选择弹窗
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jhzj_zdyjh);
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
		titlename.setText("年计划");
		rjhListView=(XListView) findViewById(R.id.xzjh_listview);
		ksrqEditText=(EditText) findViewById(R.id.ksrq_edit);
		ksrqEditText.setOnClickListener(this);
		jsrqEditText=(EditText) findViewById(R.id.jsrq_edit);
		jsrqEditText.setOnClickListener(this);
		cxButton=(ImageButton) findViewById(R.id.cx_but);
		cxButton.setOnClickListener(this);
		xzjhImageView=(ImageView) findViewById(R.id.xzjh);
		xzjhImageView.setOnClickListener(this);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		ksrqEditText.setText(sdf.format(new Date()));
//		jsrqEditText.setText(sdf.format(new Date()));
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new JhzjZdyjhAdapter(xzjhList, mContext, this);
		rjhListView.setXListViewListener(xListViewListener);
		rjhListView.setPullLoadEnable(true);
		rjhListView.setPullRefreshEnable(false);
		rjhListView.setAdapter(adapter);
		rjhListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent(mContext,JhzjJhxmActivity.class);
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
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("years", ksrqEditText.getText().toString());
//		parmMap.put("zzrq", jsrqEditText.getText().toString());
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0,"jhrwgzzjnzj",parmMap,true);
	}
	@Override
	public void executeSuccess() {
	    findState = true;
//		xzjhList.clear();
		if(returnJson.equals("")){
			showToastPromopt(2);
		}else{
			xzjhList.addAll(JHRW.parseJsonToObject5(returnJson));
		}
		adapter.notifyDataSetChanged();
	}
	/**     
	 * 创建日期及时间选择对话框    
	 */    
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		Dialog dialog = null;
//		Calendar c = null;
//		switch (id) {
//		case 0:
//			c = Calendar.getInstance();
//			dialog = new DatePickerDialog(this,
//					new DatePickerDialog.OnDateSetListener() {
//						public void onDateSet(DatePicker dp, int year,
//								int month, int dayOfMonth) {
//							month++;
//							String myMonth="";
//							String myDay="";
//							if(month<10){
//								myMonth="0"+month;
//							}else{
//								myMonth=""+month;
//							}
//							if(dayOfMonth<10){
//								myDay="0"+dayOfMonth;
//							}else{
//								myDay=""+dayOfMonth;
//							}
//							ksrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
//						}
//					}, c.get(Calendar.YEAR),
//					// 传入年份
//					c.get(Calendar.MONTH),
//					// 传入月份
//					c.get(Calendar.DAY_OF_MONTH)
//			// 传入天数
//			);
//			break;
//		case 1:
//			c = Calendar.getInstance();
//			dialog = new DatePickerDialog(this,
//					new DatePickerDialog.OnDateSetListener() {
//						public void onDateSet(DatePicker dp, int year,
//								int month, int dayOfMonth) {
//							month++;
//							String myMonth="";
//							String myDay="";
//							if(month<10){
//								myMonth="0"+month;
//							}else{
//								myMonth=""+month;
//							}
//							if(dayOfMonth<10){
//								myDay="0"+dayOfMonth;
//							}else{
//								myDay=""+dayOfMonth;
//							}
//							jsrqEditText.setText(year + "-"+ myMonth + "-"+ myDay);
//						}
//					}, c.get(Calendar.YEAR),
//					// 传入年份
//					c.get(Calendar.MONTH),
//					// 传入月份
//					c.get(Calendar.DAY_OF_MONTH)
//			// 传入天数
//			);
//			break;
//		}
//		return dialog;
//	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ksrq_edit:
			selectTime();
			break;
		case R.id.jsrq_edit:
			showDialog(1);
			break;
		case R.id.cx_but:
		    if(findState){
		        currentPage=1;
		        xzjhList.clear();
		        searchDate();
		    }
		    findState=false;
			break;
		case R.id.xzjh:
			Intent intent=new Intent(JhzjZdyjhActivity.this,JhzjXzzdyjhActivity.class);
			
			startActivityForResult(intent,1);
		default:
			break;
		}
	}


	/**
	 * 时间选择器
	 *

	 */
	public void selectTime() {
		if (mTimePickerView == null) {
			mTimePickerView = new TimePickerView(this, TimePickerView.Type.YEAR);
		}
		mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
			@Override
			public void onTimeSelect(Date date) {
				//单据日期
				ksrqEditText.setText(DateUtil.DateToString(date, "yyyy"));



			}
		});

		mTimePickerView.setTime(new

				Date());
		mTimePickerView.show();
	}
	
//	/**
//	 * 长按事件
//	 */
//	private void longClickDialog(String title,final int type,final int index){
//		final AlertDialog.Builder builder=new AlertDialog.Builder(JhzjZdyjhActivity.this);
//		title="请选择";
//		if(type==0){
//			builder.setTitle(title).setItems(new String[]{"总结","删除"},new DialogInterface.OnClickListener() {  
//				@Override 
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.cancel();
//					switch (which) {
//					case 0:
//						Intent intent=new Intent(JhzjZdyjhActivity.this,JhzjJrzjActivity.class);
//						intent.putExtra("jhid",jhrw.getId());
//						startActivity(intent);
//						break;
//					case 1:
//						xzjhList.remove(index);
//						adapter.notifyDataSetChanged();
//						break;
//					default:
//						break;
//					}
//				}  
//				
//			}); 
//			AlertDialog dialog=builder.create();
//			dialog.setCancelable(true);
//			dialog.setCanceledOnTouchOutside(true);
//			dialog.show();
//		}
//	}
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
		if(resultCode==RESULT_OK){
			if(requestCode==0){
				if(!ksrqEditText.getText().toString().equals("")&&!jsrqEditText.getText().toString().equals("")){
					xzjhList.clear();
					currentPage=1;
					searchDate();
				}
			}else if(requestCode==1){
				currentPage=1;
				xzjhList.clear();
				searchDate();
			}
		}
	}
}
