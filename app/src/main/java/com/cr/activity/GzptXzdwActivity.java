package com.cr.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.cr.adapter.GzptXzdwAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.DW;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class GzptXzdwActivity extends BaseActivity implements OnClickListener {
	private GzptXzdwAdapter adapter;
	private XListView xzdwListView;
	private EditText searchEditText;
	private ImageView searchImageView;
	List<DW>dwList=new ArrayList<DW>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_xzdw);
		addFHMethod();
		initActivity();
		initListView();
		dwList.clear();
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		xzdwListView=(XListView) findViewById(R.id.xzdw_listview);
		searchEditText=(EditText) findViewById(R.id.search_edittext);
		searchImageView=(ImageView) findViewById(R.id.search_imageview);
		searchImageView.setOnClickListener(this);
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new GzptXzdwAdapter(dwList, context, this);
		xzdwListView.setAdapter(adapter);
		xzdwListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			    DW dw=dwList.get(arg2-1);
			    Intent intent=new Intent();
			    intent.putExtra("id",dw.getId());
			    intent.putExtra("name", dw.getName());
			    setResult(RESULT_OK, intent);
			    finish();
			}
		});
		xzdwListView.setXListViewListener(xListViewListener);
		xzdwListView.setPullLoadEnable(true);
		xzdwListView.setPullRefreshEnable(false);
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
	    Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("filter",searchEditText.getText().toString());
        parmMap.put("curpage",currentPage);
        parmMap.put("pagesize",pageSize);
        findServiceData2(0,ServerURL.CLIENTLIST,parmMap,true);
	}
	
	@Override
	public void executeSuccess() {
		if(returnSuccessType==0){
//		    Log.v("dddd", dwList.size()+"");
		    dwList.addAll(DW.paseJsonToObject(returnJson));
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		    case R.id.search_imageview:
		        dwList.clear();
		        searchDate();
		        break;
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
	    xzdwListView.stopRefresh();
	    xzdwListView.stopLoadMore();
	    xzdwListView.setRefreshTime(new Date().toLocaleString());
	}
}
