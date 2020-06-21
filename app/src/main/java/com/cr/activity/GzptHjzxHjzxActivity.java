package com.cr.activity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.cr.activity.gzpt.dwzl.GzptDwzlActivity;
import com.cr.adapter.GzptHjzxHjzxAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.JHRW;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 工作平台-呼叫中心
 * @author Administrator
 *
 */
public class GzptHjzxHjzxActivity extends BaseActivity implements
		OnClickListener {
	private GzptHjzxHjzxAdapter adapter;
	private XListView xListView;
	List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	JHRW jhrw = new JHRW();

	EditText searchEditText, xzrqEditText;
	ImageButton cxButton;
	RadioButton wbfRadioButton, ybfRadioButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_hjzx_hjzx);
		addFHMethod();
		addZYMethod();
		initActivity();
		initListView();
		// searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		jhrw = (JHRW) this.getIntent().getExtras().getSerializable("object");
		searchEditText = (EditText) findViewById(R.id.search_edit);
		xzrqEditText = (EditText) findViewById(R.id.xzrq_edit);
		xzrqEditText.setOnClickListener(this);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		xzrqEditText.setText(sdf.format(new Date()));
		searchEditText = (EditText) findViewById(R.id.search_edit);
		wbfRadioButton = (RadioButton) findViewById(R.id.wbf);
		ybfRadioButton = (RadioButton) findViewById(R.id.ybf);
		cxButton = (ImageButton) findViewById(R.id.cx);
		cxButton.setOnClickListener(this);
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		xListView = (XListView) findViewById(R.id.xlistview);
        adapter = new GzptHjzxHjzxAdapter(list, this);
        xListView.setAdapter(adapter);
        xListView.setXListViewListener(xListViewListener);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(context, GzptDwzlActivity.class);
                intent.putExtra("object", (Serializable)list.get(arg2-1));
                startActivityForResult(intent,1);
                adapter.setSelectIndex(arg2);
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
		parmMap.put("jhbid", jhrw.getId());
		parmMap.put("rq", xzrqEditText.getText().toString());
		parmMap.put("zt", wbfRadioButton.isChecked() ? "0" : "1");
		parmMap.put("filter", searchEditText.getText().toString());
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData(0, ServerURL.GETJHBXX, parmMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
		if (returnJson.equals("")) {
			showToastPromopt(2);
		} else {
			list.addAll((List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson));
		}
		adapter.notifyDataSetChanged();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.cx:
		    currentPage=1;
		    list.clear();
			searchDate();
			break;
		case R.id.xzrq_edit:
			showDialog(0);
			break;
		default:
			break;
		}

	}

	/**
	 * 创建日期及时间选择对话框
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		Calendar c = null;
		switch (id) {
		case 0:
			c = Calendar.getInstance();
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							month++;
							String myMonth = "";
							String myDay = "";
							if (month < 10) {
								myMonth = "0" + month;
							} else {
								myMonth = "" + month;
							}
							if (dayOfMonth < 10) {
								myDay = "0" + dayOfMonth;
							} else {
								myDay = "" + dayOfMonth;
							}
							xzrqEditText.setText(year + "-" + myMonth + "-"
									+ myDay);
						}
					}, c.get(Calendar.YEAR),
					// 传入年份
					c.get(Calendar.MONTH),
					// 传入月份
					c.get(Calendar.DAY_OF_MONTH)
			// 传入天数
			);
			break;
		}
		return dialog;
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
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }
}