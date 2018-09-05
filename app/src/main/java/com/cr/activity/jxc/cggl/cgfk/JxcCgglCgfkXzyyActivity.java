package com.cr.activity.jxc.cggl.cgfk;

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
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcSearchActivity;
import com.cr.adapter.jxc.cggl.cgfk.JxcCgglCgfkXzyyAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-采购管理-采购付款-选择选择引用
 * 
 * @author Administrator
 * 
 */
public class JxcCgglCgfkXzyyActivity extends BaseActivity implements
		OnClickListener {
	private JxcCgglCgfkXzyyAdapter adapter;
	private XListView listView;
	EditText searchEditText;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	String qsrq, jzrq, shzt = "0", cname;
	private TextView qrTextView;
	private ImageButton sxButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jxc_cggl_cgfk_xzyy);
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
		sxButton=(ImageButton) findViewById(R.id.sx);
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
		adapter = new JxcCgglCgfkXzyyAdapter(list, this);
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
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("parms", this.getIntent().getExtras().getString("type"));
		parmMap.put("clientid", this.getIntent().getExtras().getString("clientid"));
		parmMap.put("qsrq", qsrq);
		parmMap.put("zzrq", jzrq);
		 parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData2(0, ServerURL.REFBILLMASTER, parmMap, false);
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
				obj2.put("bcjs", "0");
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
			List<Map<String, Object>> l=new ArrayList<Map<String,Object>>();
//			double total=0;
			for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                if (map.get("isDetail").equals("0")) {
                    if (map.get("ischecked").equals("1")) {
                        Map<String, Object> map2 = list.get(i + 1);
                        if(map2.get("bcjs").toString().equals("")){
                            Toast.makeText(context, "请填写本次结算金额！", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        double bcjs=Double.parseDouble(map2.get("bcjs").toString());

                        double wfje=Double.parseDouble(map.get("shouldpayamt").toString());
//                        if(bcjs<0||bcjs>wfje){
//                            showToastPromopt("本次结算金额必须小于等于未付款金额！");
//                            return;
//                        }
                        map.put("bcjs", map2.get("bcjs"));
                        l.add(map);
                    }
                }
            }
			list.clear();
			list.addAll(l);
			intent.putExtra("object",(Serializable)list);
			setResult(RESULT_OK,intent);
			finish();
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
				qsrq=data.getExtras().getString("qr");
				jzrq=data.getExtras().getString("zr");
				list.clear();
				currentPage=1;
				searchDate();
			}
		}
	}
}