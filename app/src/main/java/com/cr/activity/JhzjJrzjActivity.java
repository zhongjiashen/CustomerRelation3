package com.cr.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JhzjJrzjActivity extends BaseActivity implements OnClickListener{
	private EditText nrEditText;
	private String jhid;
	private ImageView saveImageView;
	private TextView titleTextView;
	private TextView descTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jhzj_jrzj);
		addFHMethod();
		initActivity();
		searchDate2();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		jhid=this.getIntent().getExtras().getString("jhid");
		String type=this.getIntent().getExtras().getString("type");
		nrEditText=(EditText) findViewById(R.id.nr_edittext);
		saveImageView=(ImageView) findViewById(R.id.save);
		titleTextView=(TextView) findViewById(R.id.jhname);
		descTextView=(TextView) findViewById(R.id.jrzj);
		if(type.equals("rjh")){
		    titleTextView.setText("日总结");
		    descTextView.setText("今日总结");
		}else if(type.equals("yjh")){
            titleTextView.setText("月总结");
            descTextView.setText("当月总结");
        }else if(type.equals("zdyjh")){
            titleTextView.setText("年计划总结");
            descTextView.setText("计划总结");
        }else if(type.equals("zjh")){
            titleTextView.setText("周总结");
            descTextView.setText("本周总结");
        }
        if(getIntent().getExtras().getString("shzt").equals("1")){
			saveImageView.setVisibility(View.GONE);
			nrEditText.setFocusable(false);
		}else
		saveImageView.setOnClickListener(this);
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
//		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("jhid", jhid);
		parmMap.put("zwzj",nrEditText.getText().toString());
		findServiceData(0,ServerURL.JHRWGZZJZWZJSAVE,parmMap);
	}
	/**
     * 连接网络的操作(查询进入总结)
     */
    private void searchDate2(){
        Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
//        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("jhid", jhid);
        findServiceData(1,ServerURL.JHRWGZZJZWZJREAD,parmMap);
    }
	@Override
	public void executeSuccess() {
	    if(returnSuccessType==0){
	        showToastPromopt("保存成功！");
	        finish();
	    }else if(returnSuccessType==1){
//		    Log.v("dddd", returnJson);
		    try {
                JSONArray array=new JSONArray(returnJson);
                JSONObject jsonObject=array.getJSONObject(0);
                nrEditText.setText(jsonObject.get("zwzj").toString().equals("null")?"":jsonObject.get("zwzj").toString());
            } catch (JSONException e) {
            }
		    
	        
	    }
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.save:
			if (nrEditText.getText().toString().equals("")) {
				showToastPromopt("请填写总结内容！");
			}else{
				searchDate();
				setInputManager(false);
			}
		default:
			break;
		}
		
	}
}
