package com.cr.activity;

import java.io.Serializable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.cr.activity.gzpt.dwzl.GzptDwzlActivity;
import com.cr.adapter.GzptHjzxHjzxAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class GzptShhfActivity extends BaseActivity implements OnClickListener{
    private GzptHjzxHjzxAdapter adapter;
    private XListView xListView;
    List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	RadioButton ybfRadioButton,wbfRadioButton;
	
	EditText ksrqEditText,jsrqEditText;
	ImageButton cxButton;
	private String bf="0";//未拜访(默认)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_shhf);
		addFHMethod();
		initActivity();
		initListView();
//		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		ksrqEditText=(EditText) findViewById(R.id.ksrq_edit);
		ksrqEditText.setOnClickListener(this);
		jsrqEditText=(EditText) findViewById(R.id.jsrq_edit);
		jsrqEditText.setOnClickListener(this);
		cxButton=(ImageButton) findViewById(R.id.cx_but);
		cxButton.setOnClickListener(this);
		ybfRadioButton=(RadioButton) findViewById(R.id.ybf);
		ybfRadioButton.setOnClickListener(this);
		wbfRadioButton=(RadioButton) findViewById(R.id.wbf);
		wbfRadioButton.setOnClickListener(this);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-");
		ksrqEditText.setText(sdf.format(new Date())+"01");
		jsrqEditText.setText(sdf.format(new Date())+calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
	    xListView = (XListView) findViewById(R.id.xlistview);
        adapter = new GzptHjzxHjzxAdapter(list, this);
        xListView.setAdapter(adapter);
        xListView.setXListViewListener(xListViewListener);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(context, GzptDwzlActivity.class);
                intent.putExtra("object", (Serializable)list.get(arg2-1));
                startActivityForResult(intent,1);
                adapter.setSelectIndex(arg2);
            }
        });
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("qsrq",ksrqEditText.getText().toString());
		parmMap.put("zzrq",jsrqEditText.getText().toString());
		parmMap.put("zt",bf);
		parmMap.put("filter","");
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", 1000);
		findServiceData(0,ServerURL.JHRWSHHF,parmMap);
	}
	@SuppressWarnings("unchecked")
    @Override
	public void executeSuccess() {
	    if (returnJson.equals("")) {
            showToastPromopt(2);
        } else {
            list.addAll((List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson));
        }
	    adapter.notifyDataSetChanged();
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.cx_but:
			if(validMsg()){
			    currentPage=1;
                list.clear();
				searchDate();
			}
			break;
		case R.id.ksrq_edit:
			showDialog(0);
			break;
		case R.id.jsrq_edit:
			showDialog(1);
			break;
		case R.id.wbf:
			bf="0";
			break;
		case R.id.ybf:
			bf="1";
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
                                                         handler.postDelayed(new Runnable() {// 实现延迟2秒加载刷新
                                                                 @Override
                                                                 public void run() {
                                                                     // 不实现顶部刷新
                                                                 }
                                                             }, 2000);
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
    private void onLoad() {// 停止刷新和加载功能，并且显示时间
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }
}
