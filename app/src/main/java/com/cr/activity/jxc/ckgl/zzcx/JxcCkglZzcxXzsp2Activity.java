package com.cr.activity.jxc.ckgl.zzcx;

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
import com.cr.adapter.jxc.ckgl.zzcx.JxcCkglZzcxXzspAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-库存管理-组装拆卸-选择商品（选择部件信息）
 * 
 * @author Administrator
 * 
 */
public class JxcCkglZzcxXzsp2Activity extends BaseActivity implements
		OnClickListener {
	private JxcCkglZzcxXzspAdapter adapter;
	private XListView listView;
	EditText searchEditText;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	String qsrq, jzrq, shzt = "0", cname;
	private TextView qrTextView;
	ImageButton flImageButton;
	private String code;
	private String storeid="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jxc_ckgl_zzcx_xzsp);
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
		if(this.getIntent().hasExtra("storeid")){
			storeid=this.getIntent().getExtras().getString("storeid");
		}
		flImageButton=(ImageButton) findViewById(R.id.fl);
		flImageButton.setOnClickListener(this);
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
		adapter = new JxcCkglZzcxXzspAdapter(list, this);
		listView.setAdapter(adapter);
		listView.setXListViewListener(xListViewListener);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			}
		});
	}

	/**
	 * 连接网络的操作
	 */
	private void searchDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("tabname", "tb_combin");
		parmMap.put("goodscode", "");
		parmMap.put("goodstype", code);
		parmMap.put("storied", storeid);
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
			for (Map<String, Object> obj : myList) {
				obj.put("isDetail", "0");
				obj.put("ischecked", "0");
				list.add(obj);
				Map<String, Object> obj2 = new HashMap<String, Object>();
				obj2.put("isDetail", "1");
				obj2.put("dj", obj.get("aprice").toString());
				obj2.put("sl", "1");
				list.add(obj2);
			}
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
			break;
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