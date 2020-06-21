package com.cr.activity.common;

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
import com.cr.adapter.common.CommonXzjhAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 公用模块-选择机会
 * 
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class CommonXzjhActivity extends BaseActivity implements OnClickListener {
	private CommonXzjhAdapter adapter;
	private XListView listView;
	private EditText searchEditText;
	private TextView xzxsjhTextView;
	private ImageButton sxImageButton;
	private String clientid="";//项目ID=5测试
	private String qr,zr;
	List<Map<String, Object>>list=new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_xzjh);
		addFHMethod();
		initActivity();
		initListView();
		list.clear();
		if(this.getIntent().hasExtra("clientid")){
		    clientid=this.getIntent().getExtras().getString("clientid");
		}
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        qr = sdf.format(new Date()) + "01";
        zr = sdf.format(new Date()) + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		xzxsjhTextView=(TextView) findViewById(R.id.xzxsjh_textview);
		xzxsjhTextView.setOnClickListener(this);
		sxImageButton=(ImageButton) findViewById(R.id.sx);
		sxImageButton.setOnClickListener(this);
	    listView=(XListView) findViewById(R.id.listview);
		searchEditText=(EditText) findViewById(R.id.search_edittext);
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
		adapter=new CommonXzjhAdapter(list,this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			    Map<String, Object> map=list.get(arg2-1);
			    Intent intent=new Intent();
			    intent.putExtra("id",map.get("chanceid").toString());
			    intent.putExtra("name", map.get("title").toString());
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
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
//        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("clientid",clientid);
        parmMap.put("qsrq",qr);
        parmMap.put("zzrq",zr);
        parmMap.put("curpage",currentPage);
        parmMap.put("pagesize",pageSize);
        findServiceData2(0,ServerURL.ITEMLIST,parmMap,true);
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public void executeSuccess() {
		if(returnSuccessType==0){
//		    Log.v("dddd", dwList.size()+"");
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
		    case R.id.xzxsjh_textview:
//		    	Intent intent=new Intent();
//		    	intent.setClass(this, GzptDwzlJhXzxsjhActivity.class);
                // intent.putExtra("lxrid", "0");
//                intent.putExtra("clientid", clientId);
//                intent.putExtra("clientname", gsmcEditText.getText());
//                startActivityForResult(intent, 4);
		    	break;
		    case R.id.sx:
		    	Intent intent=new Intent();
		    	intent.putExtra("qr",qr );
		    	intent.putExtra("zr", zr);
		    	intent.setClass(this, CommonXzjhSearchActivity.class);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			if(requestCode==0){
				qr=data.getExtras().getString("qr");
				zr=data.getExtras().getString("zr");
				list.clear();
				searchDate();
			}
		}
	}
}
