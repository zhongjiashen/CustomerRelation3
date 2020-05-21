package com.cr.activity.tjfx.kcbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cr.activity.BaseActivity;
import com.cr.activity.xjyh.fkd.XjyhFkdDetailActivity;
import com.cr.adapter.tjfx.kcbb.TjfxKcbbSpjgAdapter;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 统计分析-库存报表-商品价格（商品订单中使用）
 * 
 * @author Administrator
 * 
 */
public class TjfxKcbbSpjg2Activity extends BaseActivity implements OnClickListener {
	private TjfxKcbbSpjgAdapter adapter;
	private ListView listView;
	ImageButton sxButton, xzButton;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tjfx_kcbb_spjg);
		addFHMethod();
		initActivity();
		initListView();
		searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		sxButton = (ImageButton) findViewById(R.id.sx);
		sxButton.setOnClickListener(this);
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		listView = (ListView) findViewById(R.id.xlistview);
		adapter = new TjfxKcbbSpjgAdapter(list, this);
		listView.setAdapter(adapter);
//		listView.setXListViewListener(xListViewListener);
//		listView.setPullLoadEnable(true);
//		listView.setPullRefreshEnable(false);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, Object> map=list.get(arg2);
				Intent intent = new Intent();
				Log.v("dddd", TjfxKcbbSpjg2Activity.this.getIntent().getExtras().getString("index"));
				intent.putExtra("index", TjfxKcbbSpjg2Activity.this.getIntent().getExtras().getString("index"));
				intent.putExtra("dj",map.get("price").toString());
				setResult(RESULT_OK, intent);
                finish();
                Log.v("dddd", "我執行完了");
			}
		});
	}

	/**
	 * 连接网络的操作
	 */
	private void searchDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("clientid",this.getIntent().getExtras().getString("clientid"));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("storeid",this.getIntent().getExtras().getString("storeid"));
		parmMap.put("goodsid", this.getIntent().getExtras().getString("goodsid"));
		parmMap.put("unitid", this.getIntent().getExtras().getString("unitid"));
		findServiceData2(0, ServerURL.SHOWGOODSPRICEINFO, parmMap, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
//		if(returnJson.equals("nmyqx")){
//    		Toast.makeText(this,"您没有该功能菜单的权限！请与管理员联系！", Toast.LENGTH_SHORT)
//			.show();
//    		new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					finish();
//				}
//			}, 300);
//    		return;
//    	}
		if (returnJson.equals("")) {
			showToastPromopt(2);
		} else {
			list.addAll((List<Map<String, Object>>) PaseJson
					.paseJsonToObject(returnJson));
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View arg0) {
//		Intent intent = new Intent();
		switch (arg0.getId()) {
//		case R.id.sx:
//			intent.setClass(context, TjfxKcbbSearchActivity.class);
//			intent.putExtra("storeid", storeid);
//			intent.putExtra("storename", storename);
//			intent.putExtra("goodsname", goodsname);
//			startActivityForResult(intent, 0);
//			break;
		}
	}

	/**
	 * 刷新
	 */
//	private IXListViewListener xListViewListener = new IXListViewListener() {
//		@Override
//		public void onRefresh() {
//			handler.postDelayed(new Runnable() {// 实现延迟2秒加载刷新
//						@Override
//						public void run() {
//							// 不实现顶部刷新
//						}
//					}, 2000);
//		}
//
//		@Override
//		public void onLoadMore() {
//			handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
//						@Override
//						public void run() {
//							currentPage++;
//							searchDate();
//							onLoad();
//						}
//					}, 2000);
//		}
//	};

//	@SuppressWarnings("deprecation")
//	private void onLoad() {// 停止刷新和加载功能，并且显示时间
//		listView.stopRefresh();
//		listView.stopLoadMore();
//		listView.setRefreshTime(new Date().toLocaleString());
//	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == RESULT_OK) {
//			if (requestCode == 0) {
//				storeid = data.getExtras().getString("storeid");
//				storename=data.getExtras().getString("storename");
//				goodsname = data.getExtras().getString("goodsname");
//				list.clear();
//				currentPage = 1;
//				searchDate();
//			} 
//		}
//	}
}