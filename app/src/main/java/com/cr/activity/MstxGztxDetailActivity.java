package com.cr.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cr.model.GSLXRChild;
import com.cr.model.GZTX;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class MstxGztxDetailActivity extends BaseActivity {
	private String title;
	private TextView titleTextView;
	private TextView describeTextView;
	private Button jrkhglTextView;
	GZTX gztx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mstx_gztx_detail);
		title=this.getIntent().getExtras().getString("title");
		titleTextView=(TextView) findViewById(R.id.title_textview);
		describeTextView=(TextView) findViewById(R.id.describe_textview);
		jrkhglTextView=(Button) findViewById(R.id.jrkhgl);
		addFHMethod();
		addZYMethod();
		initActivity();
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		
	}
	
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("title", title);
		findServiceData(0,ServerURL.BROADCASTPROMPTXX,parmMap);
	}
	@Override
	public void executeSuccess() {
//	    gztxList.clear();
	    gztx=new GZTX();
	    if(!returnJson.equals("")){
	    	gztx=GZTX.parseJsonToObject2(returnJson).get(0);
	    	if(gztx.getTypes().equals("5")||gztx.getTypes().equals("6")){
	    	    jrkhglTextView.setVisibility(View.VISIBLE);
	    	    jrkhglTextView.setOnClickListener(new OnClickListener() {
	                @Override
	                public void onClick(View arg0) {
	                    if(gztx.getTypes().equals("5")){
	                        Intent intent=new Intent();
	                        intent.setClass(MstxGztxDetailActivity.this, GzptKhglActivity.class);
	                        ShareUserInfo.setKey(mContext, "khzlname","yybf");
	                        intent.putExtra("cname", gztx.getCname());
	                        startActivity(intent);
	                    }else if(gztx.getTypes().equals("6")){
	                    	ShareUserInfo.setKey(mContext, "khzlname","yybf");
	                        Intent intent=new Intent(MstxGztxDetailActivity.this,GzptHjzxKhzlActivity.class);
	                        GSLXRChild child=new GSLXRChild();
	                        child.setLxrid(gztx.getId());
	                        intent.putExtra("object", child);
	                        startActivity(intent);
	                    }
	                }
	            });
	    	}else{
	    	    jrkhglTextView.setVisibility(View.GONE);
	    	}
	    	titleTextView.setText(title);
	    	describeTextView.setText(gztx.getMemo());
	    }
	}
}