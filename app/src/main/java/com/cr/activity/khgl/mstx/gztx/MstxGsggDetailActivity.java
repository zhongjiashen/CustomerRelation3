package com.cr.activity.khgl.mstx.gztx;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.model.GSGG;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class MstxGsggDetailActivity extends BaseActivity {
	private GSGG gsgg;
	private TextView titleTextView,timeTextView,describeTextView;
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mstx_gsgg_detail);
		gsgg=(GSGG) this.getIntent().getExtras().getSerializable("object");
		addFHMethod();
		addZYMethod();
		initActivity();
		searchDate();
	}
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		titleTextView=(TextView) findViewById(R.id.title_textview);
		timeTextView=(TextView) findViewById(R.id.time_textview);
//		describeTextView=(TextView) findViewById(R.id.describe_textview);
		webView=(WebView) findViewById(R.id.describe);
//		titleTextView.setText(gsgg.getTitle());
//		timeTextView.setText(gsgg.getOpdate());
	}
	
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("id", gsgg.getId());
		findServiceData(0,ServerURL.BROADCASTXX,parmMap);
	}
	@Override
	public void executeSuccess() {
		GSGG gsgg=GSGG.parseJsonToObject2(returnJson).get(0);
//		describeTextView.setText(gsgg.getMemo());
//		webView.loadData(gsgg.getMemo(), "text/html", "gbk");  
		 final String mimeType = "text/html";         
		 final String encoding = "utf-8";         
		 final String html = gsgg.getMemo();// TODO 从本地读取HTML文件          
		 webView.loadDataWithBaseURL("", html, mimeType,encoding, "");      
		titleTextView.setText(gsgg.getTitle());
		timeTextView.setText(gsgg.getOpdate());
	}
	
}
