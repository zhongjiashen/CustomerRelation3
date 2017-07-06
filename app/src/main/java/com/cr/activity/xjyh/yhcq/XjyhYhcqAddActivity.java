package com.cr.activity.xjyh.yhcq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 现金银行-银行存取-增加
 * 
 * @author Administrator
 * 
 */
public class XjyhYhcqAddActivity extends BaseActivity implements
		OnClickListener {
	private ImageButton saveImageButton;
	private EditText zczhEditText,zrzhEditText,jeEditText,djrqEditText,jbrEditText,bzxxEditText;
	private String zczhId,zrzhId, jbrId;

	String billid; // 选择完关联的单据后返回的单据的ID

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xjyh_yhcq_add);
		addFHMethod();
		initActivity();
		// searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
		saveImageButton.setOnClickListener(this);
		zczhEditText = (EditText) findViewById(R.id.zczh_edittext);
		zczhEditText.setOnClickListener(this);
		zrzhEditText = (EditText) findViewById(R.id.zrzh_edittext);
		zrzhEditText.setOnClickListener(this);
		jeEditText = (EditText) findViewById(R.id.je_edittext);
		djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
		djrqEditText.setOnClickListener(this);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        djrqEditText.setText(sdf.format(new Date()));
		jbrEditText = (EditText) findViewById(R.id.jbr_edittext);
		jbrEditText.setOnClickListener(this);
		bzxxEditText=(EditText) findViewById(R.id.bzxx_edittext);
		bzxxEditText.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				view.getParent().requestDisallowInterceptTouchEvent(true);
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_UP:
					view.getParent().requestDisallowInterceptTouchEvent(
							false);
					break;
				}
				return false;
			}
		});
	}

	/**
	 * 连接网络的操作(保存)
	 */
	private void searchDateSave() {
	    if (zczhEditText.getText().toString().equals("")) {
            showToastPromopt("请选择转出账户 ");
            return;
        } else if (zrzhEditText.getText().toString().equals("")) {
            showToastPromopt("请选择转入账户 ");
            return;
        } else if (djrqEditText.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        }else if (jeEditText.getText().toString().equals("")) {
            showToastPromopt("金额不能为空");
            return;
        }else if(Double.parseDouble(jeEditText.getText().toString())<=0){
            showToastPromopt("金额必须大于0");
            return;
        } if (jbrEditText.getText().toString().equals("")) {
            showToastPromopt("请选择经办人");
            return;
        }if(zczhId.equals(zrzhId)){
            showToastPromopt("转出账户和转入账户不能相同");
            return;
        }
		JSONArray arrayMaster = new JSONArray();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("billid", "0");
			jsonObject.put("code", "");
			jsonObject.put("billdate", djrqEditText.getText().toString());
			jsonObject.put("outbankid", zczhId);
			jsonObject.put("inbankid", zrzhId);
			jsonObject.put("amount", jeEditText.getText().toString());
			jsonObject.put("empid", jbrId);
			jsonObject.put("memo",bzxxEditText.getText().toString());
			jsonObject.put("opid", ShareUserInfo.getUserId(context));
			arrayMaster.put(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}// 代表新增
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		// parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("tabname", "tb_movemoney");
		parmMap.put("parms", "YHCQ");
		parmMap.put("master", arrayMaster.toString());
		parmMap.put("detail", "");
		findServiceData2(0, ServerURL.BILLSAVE, parmMap, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
		if (returnSuccessType == 0) {
			if (returnJson.equals("")) {
				showToastPromopt("保存成功");
				setResult(RESULT_OK);
				finish();
			} else {
				showToastPromopt("保存失败" + returnJson.substring(5));
			}
		} else if (returnSuccessType == 1) {// 管理单据成功后把信息填到里面（主表）
			if (returnJson.equals("")) {
				return;
			}
			Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
					.paseJsonToObject(returnJson)).get(0);
			djrqEditText.setText(object.get("billdate").toString());
			jbrEditText.setText(object.get("empname").toString());
			jbrId = object.get("empid").toString();
			// searchDate2();//查询订单中的商品
		}
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.zczh_edittext:
			intent.setClass(activity, CommonXzzdActivity.class);
			intent.putExtra("type", "BANK");
			startActivityForResult(intent, 1);
			break;
		case R.id.zrzh_edittext:
			intent.setClass(activity, CommonXzzdActivity.class);
			intent.putExtra("type", "BANK");
			startActivityForResult(intent, 2);
			break;
		case R.id.djrq_edittext:
			date_init(djrqEditText);
			break;
		case R.id.jbr_edittext:
			intent.setClass(activity, CommonXzjbrActivity.class);
			startActivityForResult(intent, 3);
			break;
		case R.id.save_imagebutton:
			searchDateSave();// 保存
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {// 增加费用
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("name", data.getExtras().getString("name"));
//				map.put("je", data.getExtras().getString("je"));
//				list.add(map);
//				adapter.notifyDataSetChanged();
			} else if (requestCode == 1) {// 转出账户
				zczhEditText.setText(data.getExtras().getString("dictmc"));
				zczhId = data.getExtras().getString("id");
			} else if (requestCode == 2) {// 结算类型
				zrzhEditText.setText(data.getExtras().getString("dictmc"));
				zrzhId = data.getExtras().getString("id");
			} else if (requestCode == 3) {// 资金账户
				jbrEditText.setText(data.getExtras().getString("name"));
				jbrId = data.getExtras().getString("id");
			} 
		}
	}
}