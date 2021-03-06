package com.cr.activity.xm;

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
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.adapter.tjfx.kcbb.TjfxKcbbAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 项目
 * 
 * @author Administrator
 * 
 */
public class XmActivity extends BaseActivity implements OnClickListener {
	private XmAdapter adapter;
	private XListView listView;
	ImageButton sxButton, xzButton;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//	String storeid="",storename="",goodsname="";
	 String  qsrq="", jzrq="";
	 String clientid="0";//单位Id 0代表所有
	 String dwmc="",xmmc="";//单位名称，项目名称
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xm);
		addFHMethod();
		initActivity();
		initListView();
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        qsrq = sdf.format(new Date()) + "01";
        jzrq = sdf.format(new Date()) + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(this.getIntent().hasExtra("clientid")){
        	clientid=this.getIntent().getExtras().getString("clientid");
        }
        searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		sxButton = (ImageButton) findViewById(R.id.sx);
		sxButton.setOnClickListener(this);
		xzButton = (ImageButton) findViewById(R.id.xz);
		xzButton.setOnClickListener(this);
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		listView = (XListView) findViewById(R.id.xlistview);
		adapter = new XmAdapter(list, this);
		listView.setAdapter(adapter);
		listView.setXListViewListener(xListViewListener);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				Map<String, Object> map=list.get(arg2 - 1);
				intent.putExtra("xmid", map.get("projectid").toString());
				intent.putExtra("xmname", map.get("title").toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	/**
	 * 连接网络的操作
	 */
	private void searchDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("clientid",clientid);
		parmMap.put("qsrq", qsrq);
		parmMap.put("zzrq", jzrq);
		parmMap.put("cname", dwmc);
		parmMap.put("title", xmmc);
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0, ServerURL.PROJECTLIST, parmMap, false);
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
			intent.setClass(mContext, XmSearchActivity.class);
			intent.putExtra("qsrq", qsrq);
			intent.putExtra("jzrq", jzrq);
			intent.putExtra("dwmc", dwmc);
			intent.putExtra("xmmc", xmmc);
			startActivityForResult(intent, 0);
			break;
		case R.id.xz:
			intent.setClass(mContext, XzxmActivity.class);
//			intent.putExtra("dwmc",dw);
//			intent.putExtra("jzrq", jzrq);
			startActivityForResult(intent, 1);
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
				qsrq = data.getExtras().getString("qsrq");
				jzrq=data.getExtras().getString("jzrq");
				dwmc = data.getExtras().getString("dwmc");
				xmmc = data.getExtras().getString("xmmc");
				list.clear();
				currentPage = 1;
				searchDate();
			} else if(requestCode==1){
				searchDate();
			}
		}
	}
}