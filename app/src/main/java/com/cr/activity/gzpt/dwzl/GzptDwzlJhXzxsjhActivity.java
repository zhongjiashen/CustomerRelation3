package com.cr.activity.gzpt.dwzl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 单位资料-机会-新增销售机会
 * 
 * @author Administrator
 * 
 */
public class GzptDwzlJhXzxsjhActivity extends BaseActivity implements
		OnClickListener {

	private EditText jhbhEditText, kssjEditText, khmcEditText, lxrEditText,
			lxdhEditText, jhmcEditText, jhlyEditText,dqjdEditText,jbrEditText,bzEditText;
	private String lxrId, jhlyId, dqjdId, jbrId;
	ImageButton saveButton;
	String clientId, chanceid,code,clientName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_dwzl_jh_xzxsjh);
		addFHMethod();
		initActivity();
		searchXsjhDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		if (this.getIntent().hasExtra("chanceid")) {
			chanceid = this.getIntent().getExtras().getString("chanceid");
			code=this.getIntent().getExtras().getString("code");
		}else{
			chanceid="0";
		}
		clientId = this.getIntent().getExtras().getString("clientid");
		clientName = this.getIntent().getExtras().getString("clientname");
		saveButton = (ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		
		jhbhEditText = (EditText) findViewById(R.id.jhbh_edittext);
		jhbhEditText.setText(code);
		kssjEditText = (EditText) findViewById(R.id.kssj_edittext);
		kssjEditText.setOnClickListener(this);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		kssjEditText.setText(sdf.format(new Date()));
		khmcEditText = (EditText) findViewById(R.id.khmc_edittext);
		khmcEditText.setText(clientName);
		lxrEditText = (EditText) findViewById(R.id.lxr_edittext);
		lxrEditText.setOnClickListener(this);
		lxdhEditText = (EditText) findViewById(R.id.lxdh_edittext);
		jhmcEditText = (EditText) findViewById(R.id.jhmc_edittext);
		jhlyEditText = (EditText) findViewById(R.id.jhly_edittext);
		jhlyEditText.setOnClickListener(this);
		dqjdEditText = (EditText) findViewById(R.id.dqjd_edittext);
		dqjdEditText.setOnClickListener(this);
		jbrEditText = (EditText) findViewById(R.id.jbr_edittext);
		jbrEditText.setOnClickListener(this);
		bzEditText = (EditText) findViewById(R.id.bz_edittext);
	}

	/**
	 * 连接网络的操作(现在销售机会保存)
	 */
	private void saveXsjhDate() {
		// Log.v("dddd", this.getIntent().getExtras().getString("jhbid"));
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("chanceid", chanceid);
		parmMap.put("billdate", kssjEditText.getText().toString());
		parmMap.put("clientid", clientId);
		parmMap.put("lxrid", lxrId);
		parmMap.put("phone", lxdhEditText.getText().toString());//
		parmMap.put("title", jhmcEditText.getText().toString());
		parmMap.put("bflx", jhlyId);
		parmMap.put("gmid", dqjdId);
		parmMap.put("empid", jbrId);// 预约日期
		parmMap.put("memo", bzEditText.getText().toString());//
		findServiceData2(3, ServerURL.ITEMSAVE, parmMap, false);
	}
	
	/**
	 * 连接网络的操作(查询销售机会)
	 */
	private void searchXsjhDate() {
		// Log.v("dddd", this.getIntent().getExtras().getString("jhbid"));
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("chanceid", chanceid);
		findServiceData2(2, ServerURL.ITEMINFO, parmMap, false);
	}

	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
		case 2:
		    if(returnJson.equals("")){
		        return;
		    }
			@SuppressWarnings("unchecked")
			Map<String, Object>object=((List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson)).get(0);
			kssjEditText.setText(object.get("billdate").toString());
			lxrEditText.setText(object.get("lxrname").toString());
			lxdhEditText.setText(object.get("phone").toString());
			jhmcEditText.setText(object.get("title").toString());
			jhlyEditText.setText(object.get("bflxname").toString());
			dqjdEditText.setText(object.get("gmmc").toString());
			jbrEditText.setText(object.get("empname").toString());
			bzEditText.setText(object.get("memo").toString());
			lxrId=object.get("lxrid").toString();
			jhlyId=object.get("bflx").toString();
			dqjdId=object.get("gmid").toString();
			jbrId=object.get("empid").toString();
			break;
		case 3:
			if (returnJson.equals("")) {
				showToastPromopt("保存成功！");
				Intent intent=new Intent();
				intent.putExtra("id", returnJsonId);
				intent.putExtra("name", jhmcEditText.getText().toString());
				setResult(RESULT_OK,intent);
				onExecuteFh();
			} else {
				showToastPromopt("保存失败！" + returnJson.substring(5));
			}
			break;
		default:
			break;
		}
	}


	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.save:
			if (validateMsg()) {
				saveXsjhDate();
			}
			break;
		case R.id.lxr_edittext:// 选择联系人
			intent.setClass(this, CommonXzlxrActivity.class);
			intent.putExtra("clientid", clientId);
			startActivityForResult(intent, 0);
			break;
		case R.id.jhly_edittext:// 选择销售机会
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "BFLX");
			startActivityForResult(intent, 1);
			break;
		case R.id.dqjd_edittext:// 选择当前进度
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "XMGM");
			startActivityForResult(intent, 2);
			break;
		case R.id.jbr_edittext:// 选择拜访方式
			intent.setClass(this, CommonXzjbrActivity.class);
			intent.putExtra("clientid", clientId);
			startActivityForResult(intent, 3);
			break;
		case R.id.kssj_edittext:// 选择预约时间
			date_init(kssjEditText);
			break;
		default:
			break;
		}
	}

	/**
	 * 验证用户输入的信息
	 * 
	 * @return
	 */
	private boolean validateMsg() {
//		if (bfnrEditText.getText().toString().equals("")) {
//			showToastPromopt("拜访内容不能为空");
//			return false;
//		}
//		if (yyCheckBox.isChecked()) {
//			if (yysjEditText.getText().toString().equals("")) {
//				showToastPromopt("预约时间不能为空");
//				return false;
//			}
//		}
	    if(jhmcEditText.getText().toString().equals("")){
            showToastPromopt("机会名称不能为空！");
            return false;
        }
        if(jbrEditText.getText().toString().equals("")){
			showToastPromopt("请选择经办人!");
			return false;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				lxrEditText.setText(data.getExtras().getString("name"));
				lxrId = data.getExtras().getString("id");
				lxdhEditText.setText(data.getExtras().getString("phone"));
			} else if (requestCode == 1) {
				jhlyEditText.setText(data.getExtras().getString("dictmc"));
				jhlyId = data.getExtras().getString("id");
			} else if (requestCode == 2) {
				dqjdId = data.getExtras().getString("id");
				dqjdEditText.setText(data.getExtras().getString("dictmc"));
			} else if (requestCode == 3) {
				jbrId = data.getExtras().getString("id");
				jbrEditText.setText(data.getExtras().getString("name"));
			}
		}
	}
}