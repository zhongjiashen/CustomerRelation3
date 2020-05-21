package com.cr.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.cr.adapter.GzptSelectXzxmAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 选择项目
 * @author Administrator
 *
 */
public class GzptSelectXzxmActivity extends BaseActivity implements OnClickListener {
	private GzptSelectXzxmAdapter adapter;
	private XListView xzdwListView;
//	private EditText searchEditText;
//	private ImageView searchImageView;
	private String clientId;
	private List<Map<String, Object>>lxrList=new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_select_xzxm);
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
//		searchEditText=(EditText) findViewById(R.id.search_edittext);
//		searchImageView=(ImageView) findViewById(R.id.search_imageview);
//		searchImageView.setOnClickListener(this);
		clientId=this.getIntent().getExtras().getString("clientId");
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new GzptSelectXzxmAdapter(lxrList, mContext, this);
		xzdwListView.setAdapter(adapter);
		xzdwListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			    Map<String, Object> dw=lxrList.get(arg2-1);
			    Intent intent=new Intent();
			    intent.putExtra("id",dw.get("id").toString());
			    intent.putExtra("title", dw.get("title").toString());
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
		parmMap.put("clientid", clientId);
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
        findServiceData(0,ServerURL.ITEMLIST,parmMap);
	}
	
	@Override
	public void executeSuccess() {
		if(returnSuccessType==0){
		    if(returnJson.equals("")){
		        showToastPromopt(2);
		        return;
		    }
		    try {
		        JSONArray array=new JSONArray(returnJson);
		        for(int i=0;i<array.length();i++){
		            JSONObject jsonObject=array.getJSONObject(i);
		            Map<String, Object> map=new HashMap<String, Object>();
		            map.put("id",jsonObject.get("id").toString());
		            map.put("title",jsonObject.get("title").toString());
		            map.put("xmjdmc",jsonObject.get("xmjdmc").toString());
		            map.put("upopdate",jsonObject.get("upopdate").toString());
		            lxrList.add(map);
		        }
//		    lxrList=GSLXRChild.parseJsonToObject(returnJson);
//		    lxrList.addAll(GSLXRChild.parseJsonToObject(returnJson));
		        adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
