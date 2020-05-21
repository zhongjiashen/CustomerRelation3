package com.cr.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.cr.activity.khgl.mstx.gztx.MstxGsggDetailActivity;
import com.cr.adapter.MstxGsggAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.GSGG;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class MstxGsggActivity extends BaseActivity {
	private BaseAdapter adapter;
	private XListView gsggListView;
	List<GSGG>gsggList=new ArrayList<GSGG>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mstx_gsgg);
		addFHMethod();
		initActivity();
		initListView();
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		gsggListView=(XListView) findViewById(R.id.gsgg_listview);
		gsggListView.setPullRefreshEnable(false);
		gsggListView.setPullLoadEnable(true);
		gsggListView.setXListViewListener(xListViewListener);
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new MstxGsggAdapter(gsggList, mContext, this);
		gsggListView.setAdapter(adapter);
		gsggListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent(mContext,MstxGsggDetailActivity.class);
				GSGG gsgg=gsggList.get(arg2-1);
				intent.putExtra("object",gsgg);
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
		parmMap.put("curpage",currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData(0,ServerURL.BROADCAST,parmMap);
	}
	@Override
	public void executeSuccess() {
//		gsggList.clear();
//		currentPage++;
	    if(returnJson.equals("")){
            showToastPromopt(2);
        }else{
            gsggList.addAll(GSGG.parseJsonToObject(returnJson));
            adapter.notifyDataSetChanged();
            
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
		gsggListView.stopRefresh();
		gsggListView.stopLoadMore();
		gsggListView.setRefreshTime(new Date().toLocaleString());
	}
	
}
