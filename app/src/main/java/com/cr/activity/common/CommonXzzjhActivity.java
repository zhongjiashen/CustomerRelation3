package com.cr.activity.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.adapter.common.CommonXzzjhAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 公用模块-选择周计划
 * 
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class CommonXzzjhActivity extends BaseActivity implements OnClickListener {
	private CommonXzzjhAdapter adapter;
	private XListView listView;
	private EditText searchEditText;
	private ImageButton bzImageButton;
	List<Map<String, Object>>list=new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_xzzjh);
		addFHMethod();
		initActivity();
		initListView();
		list.clear();
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
	    listView=(XListView) findViewById(R.id.listview);
	    bzImageButton=(ImageButton)findViewById(R.id.bz);
	    bzImageButton.setOnClickListener(this);
		searchEditText=(EditText) findViewById(R.id.search_edittext);
		searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    list.clear();
                    currentPage = 1;
                    searchDate();
                    return true;
                }
                return false;
            }
        });
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new CommonXzzjhAdapter(list,this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			    Map<String, Object> map=list.get(arg2-1);
			    Intent intent=new Intent();
			    intent.putExtra("id",map.get("weekno").toString());
			    intent.putExtra("name", map.get("fullname").toString());
			    setResult(RESULT_OK, intent);
			    finish();
			}
		});
		listView.setXListViewListener(xListViewListener);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
	    Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("years", this.getIntent().getExtras().getString("year"));
        parmMap.put("istoday", "0");
//        parmMap.put("empname",searchEditText.getText().toString());
        parmMap.put("curpage",currentPage);
        parmMap.put("pagesize",pageSize);
        findServiceData2(0,ServerURL.JHRWSELECTWEEK,parmMap,true);
	}
	
	/**
	 * 连接网络的操作
	 */
	private void searchDate2(){
	    Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("years", this.getIntent().getExtras().getString("year"));
        parmMap.put("istoday", "1");
//        parmMap.put("empname",searchEditText.getText().toString());
        parmMap.put("curpage",currentPage);
        parmMap.put("pagesize",pageSize);
        findServiceData2(1,ServerURL.JHRWSELECTWEEK,parmMap,true);
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public void executeSuccess() {
	    if(returnSuccessType==0){
            if(returnJson.equals("")){
                showToastPromopt(2);
            }else{
                list.addAll((List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson));
                adapter.notifyDataSetChanged();
            }
        }else if(returnSuccessType==1){
			List<Map<String, Object>> list=(List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson);
//			zhou=list.get(0).get("weekno").toString();
//			jsrqEditText.setText(list.get(0).get("fullname").toString());
//			bzName=list.get(0).get("fullname").toString();
			Intent intent=new Intent();
		    intent.putExtra("id",list.get(0).get("weekno").toString());
		    intent.putExtra("name", list.get(0).get("fullname").toString());
		    setResult(RESULT_OK, intent);
		    finish();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		    case R.id.search_imageview:
		        list.clear();
		        searchDate();
		        break;
		    case R.id.bz:
		    	searchDate2();
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
	    listView.stopRefresh();
	    listView.stopLoadMore();
	    listView.setRefreshTime(new Date().toLocaleString());
	}
}
