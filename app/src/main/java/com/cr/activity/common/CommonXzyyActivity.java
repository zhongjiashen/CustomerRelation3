package com.cr.activity.common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcSearchActivity;
import com.cr.adapter.common.CommonXzyyAdapter;
import com.cr.adapter.jxc.cggl.cgfk.JxcCgglCgfkXzyyAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-选择选择引用
 * 
 * @author Administrator
 * 
 */
public class CommonXzyyActivity extends BaseActivity implements OnClickListener {
	private CommonXzyyAdapter adapter;
	private XListView listView;
	EditText searchEditText;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	String qsrq, jzrq, shzt = "0", cname;
	private TextView qrTextView;
	private ImageButton sxButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_xzyy);
		addFHMethod();
		initActivity();
		initListView();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
		qsrq = sdf.format(new Date()) + "01";
		jzrq = sdf.format(new Date())
				+ calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		sxButton = (ImageButton) findViewById(R.id.sx);
		sxButton.setOnClickListener(this);
		qrTextView = (TextView) findViewById(R.id.qr_textview);
		qrTextView.setOnClickListener(this);
		searchEditText = (EditText) findViewById(R.id.search);
		searchEditText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
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
	private void initListView() {
		listView = (XListView) findViewById(R.id.xlistview);
		adapter = new CommonXzyyAdapter(list, this);
		listView.setAdapter(adapter);
		listView.setXListViewListener(xListViewListener);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// Intent intent = new Intent(context,
				// JxcCgglCgddDetailActivity.class);
				// intent.putExtra("billid",
				// list.get(arg2-1).get("billid").toString());
				// startActivityForResult(intent,1);
				// adapter.setSelectIndex(arg2);
			}
		});
	}

	/**
	 * 连接网络的操作
	 */
	private void searchDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("parms", this.getIntent().getExtras().getString("type"));
		parmMap.put("clientid",
				this.getIntent().getExtras().getString("clientid"));
		parmMap.put("qsrq", qsrq);
		parmMap.put("zzrq", jzrq);
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0, ServerURL.REFBILLMASTER, parmMap, false);
	}
	
	/**
	 * 连接网络的操作(查询从表的信息)
	 */
	private void searchDate2(String billid) {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("reftypeid", this.getIntent().getExtras().getString("reftypeid"));
		parmMap.put("billid",billid);
		findServiceData2(1, ServerURL.REFBILLDETAIL, parmMap, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
		if(returnSuccessType==0){
			if (returnJson.equals("")) {
				showToastPromopt(2);
			} else {
				ArrayList<Map<String, Object>> myList = (ArrayList<Map<String, Object>>) PaseJson
						.paseJsonToObject(returnJson);
				list.addAll(myList);
			}
			adapter.notifyDataSetChanged();
		}else if(returnSuccessType==1){
//			returnJson.replace("}]\"}","}]}");
//			returnJson.replace("\"serials\":\"[","\"serials\":[");
			List<Map<String, Object>> l=(List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
			double totalAmount=0;
			if(l!=null) {
				for (Map<String, Object> m : l) {
					totalAmount += Double.parseDouble(m.get("amount").toString().equals("") ? "0" : m.get("amount").toString());
				}
				Intent intent = new Intent();
				intent.putExtra("list", (Serializable) l);
				intent.putExtra("totalAmount", totalAmount + "");
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.qr_textview:
			List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
			String billids="";
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				if (map.get("ischecked") != null
						&& map.get("ischecked").equals("1")) {
					l.add(map);
					billids+=","+map.get("billid").toString();
				}
			}
			
			searchDate2(billids.equals("")?"":billids.substring(1));
//			list.clear();
//			list.addAll(l);
			break;
		case R.id.sx:
			intent.setClass(activity, JxcCgglCgddShlcSearchActivity.class);
			intent.putExtra("qr", qsrq);
			intent.putExtra("zr", jzrq);
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
				int index = Integer.parseInt(data.getExtras()
						.getString("index"));
				list.get(index).put("cpph", data.getExtras().getString("name"));
				adapter.notifyDataSetChanged();
			} else if (requestCode == 1) {
				qsrq = data.getExtras().getString("qr");
				jzrq = data.getExtras().getString("zr");
				list.clear();
				currentPage=1;
				searchDate();
			}
		}
	}
}