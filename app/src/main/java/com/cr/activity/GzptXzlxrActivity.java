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

import com.cr.adapter.GzptXzlxrAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.GSLXRChild;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 选择联系人
 * 
 * @author caiyanfei
 * @version $Id: GzptXzlxrActivity.java, v 0.1 2015-1-13 下午3:20:49 caiyanfei Exp $
 */
public class GzptXzlxrActivity extends BaseActivity implements OnClickListener {
	private GzptXzlxrAdapter adapter;
	private XListView xzdwListView;
	private EditText searchEditText;
	private ImageView searchImageView;
	private List<GSLXRChild>lxrList=new ArrayList<GSLXRChild>();
	private String dwid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_xzlxr);
		addFHMethod();
		initActivity();
		initListView();
		lxrList.clear();
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		xzdwListView=(XListView) findViewById(R.id.xzdw_listview);
		searchEditText=(EditText) findViewById(R.id.search_edittext);
		if(this.getIntent().hasExtra("dwmc")){
//		    searchEditText.setText(this.getIntent().getExtras().getString("dwmc").toString());
		    dwid=this.getIntent().getExtras().getString("dwid").toString();
		}
		searchImageView=(ImageView) findViewById(R.id.search_imageview);
		searchImageView.setOnClickListener(this);
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new GzptXzlxrAdapter(lxrList, mContext, this);
		xzdwListView.setAdapter(adapter);
		xzdwListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			    GSLXRChild dw=lxrList.get(arg2-1);
			    Intent intent=new Intent();
			    intent.putExtra("id",dw.getId());
			    intent.putExtra("name", dw.getLxrname());
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
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("opid", ShareUserInfo.getUserId(mContext));
        parmMap.put("filter",searchEditText.getText().toString());
        parmMap.put("clientid",dwid);
        parmMap.put("curpage",currentPage);
        parmMap.put("pagesize",pageSize);
        findServiceData(0,ServerURL.LXRLIST,parmMap);
	}
	
	@Override
	public void executeSuccess() {
		if(returnSuccessType==0){
//		    Log.v("dddd", dwList.size()+"");
//		    lxrList=GSLXRChild.parseJsonToObject(returnJson);
		    lxrList.addAll(GSLXRChild.parseJsonToObject(returnJson));
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		    case R.id.search_imageview:
		        lxrList.clear();
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
