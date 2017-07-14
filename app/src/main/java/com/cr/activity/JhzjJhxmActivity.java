package com.cr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cr.adapter.JhzjJhxmAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.JHRW;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JhzjJhxmActivity extends BaseActivity implements OnClickListener{
	private BaseAdapter adapter;
	private XListView jhxmListView;
	List<JHRW>jhxmList=new ArrayList<JHRW>();
	JHRW jhrw;
	TextView jhnameTextView;
	ImageView xzxmImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jhzj_jhxm);
		addFHMethod();
		addZYMethod();
		initActivity();
		initListView();
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		jhrw=(JHRW) this.getIntent().getExtras().getSerializable("object");
		jhxmListView=(XListView) findViewById(R.id.jhxm_listview);
		jhnameTextView=(TextView) findViewById(R.id.jhname);
		jhnameTextView.setText(jhrw.getQsrq());
		xzxmImageView=(ImageView) findViewById(R.id.xzxm);
		xzxmImageView.setOnClickListener(this);
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new JhzjJhxmAdapter(jhxmList, context, this);
		jhxmListView.setAdapter(adapter);
		jhxmListView.setXListViewListener(xListViewListener);
		jhxmListView.setPullLoadEnable(true);
		jhxmListView.setPullRefreshEnable(false);
		jhxmListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				JHRW jhrw2=jhxmList.get(arg2-1);
				if(jhrw2.getLx().equals("0")){
					Intent intent = new Intent(context,JhzjXzxmZxActivity.class);

					intent.putExtra("jhid", jhrw.getId());
					intent.putExtra("object",jhrw2);
					startActivity(intent);
				}else{
					Intent intent = new Intent(context,JhzjXzxmZxCkActivity.class);
					intent.putExtra("jhid", jhrw.getId());
					intent.putExtra("object",jhrw2);
					startActivity(intent);
				}

			}
		});
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("jhid", jhrw.getId());
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0,ServerURL.JHRWGZZJITEM,parmMap,false);
	}
	@Override
	public void executeSuccess() {
//		jhxmList.clear();
		if(returnJson.equals("")){
			showToastPromopt(2);
		}else{
			jhxmList.addAll(JHRW.parseJsonToObject3(returnJson));
		}
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.xzxm:
			Intent intent=new Intent(JhzjJhxmActivity.this,JhzjXzxmActivity.class);
			intent.putExtra("jhid", jhrw.getId());
			intent.putExtra("start", "1");
			startActivityForResult(intent, 0);
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
		jhxmListView.stopRefresh();
		jhxmListView.stopLoadMore();
		jhxmListView.setRefreshTime(new Date().toLocaleString());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0){
			jhxmList.clear();
			currentPage=1;
			searchDate();
		}
	}
}
