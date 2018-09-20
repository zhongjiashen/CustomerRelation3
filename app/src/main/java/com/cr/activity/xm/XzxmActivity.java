package com.cr.activity.xm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 新增项目
 * 
 * @author Administrator
 * 
 */
public class XzxmActivity extends BaseActivity implements
		OnClickListener {
	private TextView dwmcEditText;

	private EditText bhEditText,dwlxEditText,xghtEditText,xmmcEditText,xmlxEditText,xmlyEditText,
	ysjeEditText,qsrqEditText,jzrqEditText,zxjgEditText,xmmdEditText,djrqEditText,ywyEditText,zdrEditText;
	private String dwmcId, xmlxId, xmlyId, zxjgId,ywyId;
	ImageButton saveButton;
	Map<String, Object> dwObject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xzxm);
		addFHMethod();
		initActivity();
		dwObject=((Map<String, Object>) this.getIntent().getExtras().getSerializable("dwObject"));

		setDwlx(getIntent().getExtras().getString("types"));
		dwlxEditText.setFocusable(false);
		dwmcEditText.setText(dwObject.get("cname").toString());
		dwmcId=dwObject.get("id").toString();
		zxjgId = "1";
		zxjgEditText.setText("前期准备");
//		searchXsjhDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		saveButton = (ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		
		bhEditText = (EditText) findViewById(R.id.bh_edittext);
		dwmcEditText = (TextView) findViewById(R.id.dwmc_edittext);
		dwmcEditText.setOnClickListener(this);
		dwlxEditText = (EditText) findViewById(R.id.dwlx_edittext);
		xghtEditText = (EditText) findViewById(R.id.xght_edittext);
		xmmcEditText = (EditText) findViewById(R.id.xmmc_edittext);
		xmlxEditText = (EditText) findViewById(R.id.xmlx_edittext);
		xmlxEditText.setOnClickListener(this);
		xmlyEditText = (EditText) findViewById(R.id.xmly_edittext);
		xmlyEditText.setOnClickListener(this);
		ysjeEditText = (EditText) findViewById(R.id.ysje_edittext);
		qsrqEditText = (EditText) findViewById(R.id.qsrq_edittext);
		qsrqEditText.setOnClickListener(this);
		jzrqEditText = (EditText) findViewById(R.id.jzrq_edittext);
		jzrqEditText.setOnClickListener(this);
		zxjgEditText = (EditText) findViewById(R.id.zxjg_edittext);
		zxjgEditText.setOnClickListener(this);
		xmmdEditText = (EditText) findViewById(R.id.xmmd_edittext);
		djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
		djrqEditText.setOnClickListener(this);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		djrqEditText.setText(sdf.format(new Date()));
		ywyEditText = (EditText) findViewById(R.id.ywy_edittext);
		ywyEditText.setOnClickListener(this);
		zdrEditText = (EditText) findViewById(R.id.zdr_edittext);
	}

	/**
	 * 连接网络的操作(现在销售机会保存)
	 */
	private void saveXsjhDate() {
		Toast.makeText(this,"保存",Toast.LENGTH_SHORT).show();
		// Log.v("dddd", this.getIntent().getExtras().getString("jhbid"));
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("projectid", "0");
		parmMap.put("billdate", djrqEditText.getText().toString());
		parmMap.put("title", xmmcEditText.getText().toString());
		parmMap.put("clientid", dwmcId);
		parmMap.put("contractid", "");//
		parmMap.put("qsrq", qsrqEditText.getText().toString());
		parmMap.put("zzrq", jzrqEditText.getText().toString());
//		parmMap.put("gmid", dqjdId);
		parmMap.put("projecttype", xmlxId);// 
		parmMap.put("sourceid", xmlyId);// 
		parmMap.put("objective", xmmdEditText.getText().toString());// 
		parmMap.put("amount", ysjeEditText.getText().toString());// 
		parmMap.put("finished", zxjgId);// 
//		parmMap.put("info", xmgk);// 
		parmMap.put("empid", ywyId);//
		findServiceData2(3, ServerURL.PROJECTSAVE, parmMap, false);
	}
	
//	/**
//	 * 连接网络的操作(查询销售机会)
//	 */
//	private void searchXsjhDate() {
//		// Log.v("dddd", this.getIntent().getExtras().getString("jhbid"));
//		Map<String, Object> parmMap = new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("chanceid", chanceid);
//		findServiceData2(2, ServerURL.ITEMINFO, parmMap, false);
//	}

	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
//		case 2:
//		    if(returnJson.equals("")){
//		        return;
//		    }
//			@SuppressWarnings("unchecked")
//			Map<String, Object>object=((List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson)).get(0);
//			kssjEditText.setText(object.get("billdate").toString());
//			lxrEditText.setText(object.get("lxrname").toString());
//			lxdhEditText.setText(object.get("phone").toString());
//			jhmcEditText.setText(object.get("title").toString());
//			jhlyEditText.setText(object.get("bflxname").toString());
//			dqjdEditText.setText(object.get("gmmc").toString());
//			jbrEditText.setText(object.get("empname").toString());
//			bzEditText.setText(object.get("memo").toString());
//			lxrId=object.get("lxrid").toString();
//			jhlyId=object.get("bflx").toString();
//			dqjdId=object.get("gmid").toString();
//			jbrId=object.get("empid").toString();
//			break;
		case 3:
			if (returnJson.equals("")) {
				showToastPromopt("保存成功！");
				Intent intent=new Intent();
//				intent.putExtra("id", returnJsonId);
//				intent.putExtra("name", jhmcEditText.getText().toString());
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
		case R.id.dwmc_edittext:// 选择单位
			intent.setClass(this, CommonXzdwActivity.class);
//			intent.putExtra("clientid", clientId);
			startActivityForResult(intent, 0);
			break;
		case R.id.xmlx_edittext:// 项目类型
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "PROJXMLX");
			startActivityForResult(intent, 1);
			break;
		case R.id.xmly_edittext:// 项目来源
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "PROJXMLY");
			startActivityForResult(intent, 2);
			break;
		case R.id.zxjg_edittext:// 执行结果
			intent.setClass(this, CommonXzzdActivity.class);
			intent.putExtra("type", "XMZXJG");
			startActivityForResult(intent, 3);
			break;
		case R.id.qsrq_edittext:// 起始日期
			date_init(qsrqEditText);
			break;
		case R.id.jzrq_edittext:// 截止日期
			date_init(jzrqEditText);
			break;
		case R.id.djrq_edittext:// 截止日期
			date_init(djrqEditText);
			break;
		case R.id.ywy_edittext:
            intent.setClass(activity, CommonXzjbrActivity.class);
            startActivityForResult(intent, 4);
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
		if (xmmcEditText.getText().toString().equals("")) {
			showToastPromopt("请填写项目名称！");
			return false;
		}
//		if (ywyEditText.getText().toString().equals("")) {
//			showToastPromopt("请选择业务员");
//			return false;
//		}

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
//	    if(jhmcEditText.getText().toString().equals("")){
//            showToastPromopt("机会名称不能为空！");
//            return false;
//        }
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				dwmcEditText.setText(data.getExtras().getString("name"));
				dwmcId = data.getExtras().getString("id");
				setDwlx(data.getExtras().getString("types"));
			} else if (requestCode == 1) {
				xmlxEditText.setText(data.getExtras().getString("dictmc"));
				xmlxId = data.getExtras().getString("id");
			} else if (requestCode == 2) {
				xmlyId = data.getExtras().getString("id");
				xmlyEditText.setText(data.getExtras().getString("dictmc"));
			} else if (requestCode == 3) {
				zxjgId = data.getExtras().getString("id");
				zxjgEditText.setText(data.getExtras().getString("dictmc"));
			}else if (requestCode == 4) {
				ywyId = data.getExtras().getString("id");
				ywyEditText.setText(data.getExtras().getString("name"));
			}
		}
	}

	/**
	 * 单位类型赋值
	 * @param type
	 */
	private void setDwlx(String type){
		if(type.equals("1")){
			dwlxEditText.setText("客户");
		}else if(type.equals("2")){
			dwlxEditText.setText("供应商");
		}else if(type.equals("3")){
			dwlxEditText.setText("竞争对手");
		}else if(type.equals("4")){
			dwlxEditText.setText("渠道");
		}else if(type.equals("5")){
			dwlxEditText.setText("员工");
		}
	}

}