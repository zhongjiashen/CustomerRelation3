package com.cr.activity.khfw;

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
import com.cr.activity.common.CommonXzsplbActivity;
import com.cr.adapter.khfw.KhfwXzspAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 客户服务-选择商品
 * 
 * @author Administrator
 * 
 */
public class KhfwXzspActivity extends BaseActivity implements
		OnClickListener {
	private KhfwXzspAdapter adapter;
	private XListView listView;
	EditText searchEditText;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	String qsrq, jzrq, shzt = "0", cname;
	ImageButton flImageButton;
	private TextView qrTextView;
	private String code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_khfw_xzsp);
		addFHMethod();
		initActivity();
		initListView();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
		qsrq = sdf.format(new Date()) + "01";
		jzrq = sdf.format(new Date())
				+ calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		flImageButton=(ImageButton) findViewById(R.id.fl);
        flImageButton.setOnClickListener(this);
		searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
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
		adapter = new KhfwXzspAdapter(list, this);
		listView.setAdapter(adapter);
		listView.setXListViewListener(xListViewListener);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Intent intent = new Intent();
				 intent.putExtra("bh",list.get(arg2-1).get("code").toString());
				 intent.putExtra("mc",list.get(arg2-1).get("name").toString());
				 intent.putExtra("gg",list.get(arg2-1).get("specs").toString());
				 intent.putExtra("xh",list.get(arg2-1).get("model").toString());
				 intent.putExtra("dw",list.get(arg2-1).get("unitname").toString());
				 intent.putExtra("cd","");
				 intent.putExtra("id",list.get(arg2-1).get("goodsid").toString());
				 intent.putExtra("unitid",list.get(arg2-1).get("unitid").toString());
				 setResult(RESULT_OK,intent);
				 finish();
			}
		});
	}

	/**
	 * 连接网络的操作
	 */
	private void searchDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("tabname", "");
		parmMap.put("goodstype", code);
        parmMap.put("goodsname", searchEditText.getText().toString());
		// parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0, ServerURL.SELECTGOODS, parmMap, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
		if (returnJson.equals("")) {
			showToastPromopt(2);
		} else {
			ArrayList<Map<String, Object>> myList = (ArrayList<Map<String, Object>>) PaseJson
					.paseJsonToObject(returnJson);
			list.addAll(myList);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View arg0) {
	    Intent intent=new Intent();
		switch (arg0.getId()) {
		case R.id.qr_textview:
			intent.putExtra("object",(Serializable)list);
			setResult(RESULT_OK,intent);
			finish();
		case R.id.fl:
            intent.setClass(activity, CommonXzsplbActivity.class);
            startActivityForResult(intent, 2);
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
				// list.remove(adapter.getSelectIndex());
				// adapter.notifyDataSetChanged();
			}else if (requestCode == 2) {
                code=data.getExtras().getString("id");
                list.clear();
                searchDate();
            }
		}
	}
}