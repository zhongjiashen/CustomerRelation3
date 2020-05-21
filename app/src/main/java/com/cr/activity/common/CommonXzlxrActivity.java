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
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.adapter.common.CommonXzlxrAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 公用模块-选择联系人
 * 
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class CommonXzlxrActivity extends BaseActivity implements OnClickListener {
	private CommonXzlxrAdapter adapter;
	private XListView listView;
	private EditText searchEditText;
	private String clientid="";//单位的id 140
	List<Map<String, Object>>list=new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_xzlxr);
		addFHMethod();
		initActivity();
		initListView();
		list.clear();
		if(this.getIntent().hasExtra("clientid")){
		    clientid=this.getIntent().getExtras().getString("clientid");
		}
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
	    listView=(XListView) findViewById(R.id.listview);
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
		adapter=new CommonXzlxrAdapter(list,this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			    Map<String, Object> map=list.get(arg2-1);
			    Intent intent=new Intent();
			    intent.putExtra("id",map.get("id").toString());
			    intent.putExtra("name", map.get("lxrname").toString());
			    intent.putExtra("phone", map.get("phone").toString());
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
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
//        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("clientid",clientid);
        parmMap.put("lxrname",searchEditText.getText().toString());
        parmMap.put("curpage",currentPage);
        parmMap.put("pagesize",pageSize);
        findServiceData2(0,ServerURL.LXRLIST,parmMap,true);
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
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		    case R.id.search_imageview:
		        list.clear();
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
	    listView.stopRefresh();
	    listView.stopLoadMore();
	    listView.setRefreshTime(new Date().toLocaleString());
	}
}
