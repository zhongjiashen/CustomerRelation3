package com.cr.activity.tjfx.xsjdtj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 统计分析-销售阶段统计
 * 
 * @author Administrator
 * 
 */
public class TjfxXsjdtjActivity extends BaseActivity implements OnClickListener {
	private TjfxXsjdtjAdapter adapter;
	private XListView listView;
	ImageButton sxButton, xzButton;
	private TextView titleTextView;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//	String lxid="0",lxname="全部",mc="";
	
	String qr, zr,ywyid,ywy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tjfx_khbftj);
		addFHMethod();
		initActivity();
		initListView();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day-30);
		qr = sdf.format(calendar.getTime());
		zr = sdf.format(new Date());
		ywyid=ShareUserInfo.getUserId(context);
		ywy=ShareUserInfo.getKey(context, "empname");
		searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		sxButton = (ImageButton) findViewById(R.id.sx);
		sxButton.setOnClickListener(this);
		titleTextView=(TextView) findViewById(R.id.title_textview);
		titleTextView.setText("销售阶段统计");
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		listView = (XListView) findViewById(R.id.xlistview);
		adapter = new TjfxXsjdtjAdapter(list, this);
		listView.setAdapter(adapter);
		listView.setXListViewListener(xListViewListener);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Intent intent = new Intent(context,
//						XjyhFkdDetailActivity.class);
//				intent.putExtra("billid", list.get(arg2 - 1).get("billid")
//						.toString());
//				if (TjfxKcbbActivity.this.getIntent().hasExtra("select")) {// 如果是添加订单时候关联的操作
//					setResult(RESULT_OK, intent);
//					finish();
//				} else {// 否则就是正常情况的打开
//					startActivityForResult(intent, 1);
//					adapter.setSelectIndex(arg2);
//				}
				Intent intent=new Intent();
				intent.setClass(activity, TjfxXsjdtj2Activity.class);
				intent.putExtra("typeid", list.get(arg2-1).get("gmid").toString());
				startActivity(intent);
			}
		});
	}

	/**
	 * 连接网络的操作
	 */
	private void searchDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("qsrq",qr);
		parmMap.put("zzrq", zr);
		parmMap.put("selopid", ywyid);
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0, ServerURL.SALESTAGERPT, parmMap, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
		if(returnJson.equals("nmyqx")){
    		Toast.makeText(this,"您没有该功能菜单的权限！请与管理员联系！", Toast.LENGTH_SHORT)
			.show();
    		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 300);
    		return;
    	}
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
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.sx:
			intent.setClass(context, TjfxXsjdtjSearchActivity.class);
			intent.putExtra("qr", qr);
			intent.putExtra("zr", zr);
			intent.putExtra("ywy", ywy);
			intent.putExtra("ywyid", ywyid);
			startActivityForResult(intent, 0);
			break;
		}
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
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new Date().toLocaleString());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
//				lxid = data.getExtras().getString("lxid");
//				lxname=data.getExtras().getString("lxname");
//				mc = data.getExtras().getString("mc");
				qr=data.getExtras().getString("qr");
				zr=data.getExtras().getString("zr");
				ywy=data.getExtras().getString("ywy");
				ywyid=data.getExtras().getString("ywyid");
				list.clear();
				currentPage = 1;
				searchDate();
			} 
		}
	}
}