package com.cr.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.adapter.common.CommonXzczyAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.crcxj.activity.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公用模块-选择操作员
 * 
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class CommonXzczyActivity extends BaseActivity implements OnClickListener {
	private CommonXzczyAdapter adapter;
	private XListView listView;
	private EditText searchEditText;
	private LinearLayout searchLayout;
	List<Map<String, Object>>list=new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_xzjbr);
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
		searchEditText=(EditText) findViewById(R.id.search_edittext);
		searchEditText.setVisibility(View.GONE);
		searchLayout=(LinearLayout) findViewById(R.id.search_layout);
		searchLayout.setVisibility(View.GONE);
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
		adapter=new CommonXzczyAdapter(list,this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			    Map<String, Object> map=list.get(arg2-1);
				//[{"id":1,"opcode":"000","opname":"系统管理员","fullname":"000--系统管理员","empid":0,"departmentid":1,"depcode":"01","depname":"总部"},{"id":5,"opcode":"001","opname":"管理员","fullname":"001--管理员","empid":1,"departmentid":1,"depcode":"01","depname":"总部"},{"id":6,"opcode":"002","opname":"张凯","fullname":"002--张凯","empid":2,"departmentid":1,"depcode":"01","depname":"总部"},{"id":7,"opcode":"003","opname":"王月芳","fullname":"003--王月芳","empid":3,"departmentid":3,"depcode":"02","depname":"销售部"},{"id":8,"opcode":"004","opname":"王丽丽","fullname":"004--王丽丽","empid":4,"departmentid":3,"depcode":"02","depname":"销售部"},{"id":9,"opcode":"005","opname":"张青青","fullname":"005--张青青","empid":5,"departmentid":4,"depcode":"03","depname":"人事部"},{"id":10,"opcode":"006","opname":"李雯","fullname":"006--李雯","empid":68,"departmentid":4,"depcode":"03","depname":"人事部"}]
			    Intent intent=new Intent();
			    intent.putExtra("id",map.get("opcode").toString());
			    intent.putExtra("name", map.get("fullname").toString());
			    intent.putExtra("empid", map.get("empid").toString());
				intent.putExtra("opname", map.get("opname").toString());
				intent.putExtra("departmentid", map.get("departmentid").toString());
				intent.putExtra("depname", map.get("depname").toString());

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
        parmMap.put("dbname", this.getIntent().getExtras().getString("dbname"));
//        parmMap.put("opid", ShareUserInfo.getUserId(context));
//        parmMap.put("empname",searchEditText.getText().toString());
        parmMap.put("curpage",currentPage);
        parmMap.put("pagesize",pageSize);
        findServiceData2(0,ServerURL.SELECTOPER,parmMap,true);
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
