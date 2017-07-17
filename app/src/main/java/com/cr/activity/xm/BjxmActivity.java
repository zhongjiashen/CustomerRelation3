package com.cr.activity.xm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑项目
 * 
 * @author Administrator
 * 
 */
public class BjxmActivity extends BaseActivity implements
		OnClickListener {

	private EditText bhEditText,dwmcEditText,dwlxEditText,xghtEditText,xmmcEditText,xmlxEditText,xmlyEditText,
	ysjeEditText,qsrqEditText,jzrqEditText,zxjgEditText,xmmdEditText,djrqEditText,jbrEditText,zdrEditText;
	private String dwmcId, xmlxId, xmlyId, zxjgId,ywyId;
	ImageButton saveButton;
	private TextView scxmTextView;//删除项目

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bjxm);
		addFHMethod();
		initActivity();
		searchXmDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		saveButton = (ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		
		bhEditText = (EditText) findViewById(R.id.bh_edittext);
		dwmcEditText = (EditText) findViewById(R.id.dwmc_edittext);
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
		jbrEditText = (EditText) findViewById(R.id.jbr_edittext);
		jbrEditText.setOnClickListener(this);
		zdrEditText = (EditText) findViewById(R.id.zdr_edittext);
		
		scxmTextView=(TextView) findViewById(R.id.scxm_textview);
		scxmTextView.setOnClickListener(this);
	}

	/**
	 * 连接网络的操作(保存)
	 */
	private void saveXsjhDate() {
		// Log.v("dddd", this.getIntent().getExtras().getString("jhbid"));
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("projectid", this.getIntent().getExtras().getString("xmid"));
		parmMap.put("billdate", djrqEditText.getText().toString());
		parmMap.put("title", xmmcEditText.getText().toString());
		parmMap.put("clientid", dwmcId);
		parmMap.put("contractid", xghtEditText.getText().toString());//
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
	
	/**
	 * 连接网络的操作(查询项目)
	 */
	private void searchXmDate() {
		// Log.v("dddd", this.getIntent().getExtras().getString("jhbid"));
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("projectid", this.getIntent().getExtras().getString("xmid"));
		findServiceData2(2, ServerURL.PROJECTINFO, parmMap, false);
	}
	/**
	 * 连接网络的操作(删除项目)
	 */
	private void deleteXmDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("tabname", "tb_project");
		parmMap.put("pkvalue", this.getIntent().getExtras().getString("xmid"));
		findServiceData2(4, ServerURL.BILLDELMASTER, parmMap, false);
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
			bhEditText.setText(object.get("code").toString());
			dwmcEditText.setText(object.get("Cname").toString());
			dwmcId=object.get("clientid").toString();
			dwlxEditText.setText(object.get("typesname").toString());
			xghtEditText.setText(object.get("contractname").toString());
			xmmcEditText.setText(object.get("title").toString());
			xmlxEditText.setText(object.get("xmlxname").toString());
			xmlxId=object.get("projecttype").toString();
			xmlyEditText.setText(object.get("xmlyname").toString());
			xmlyId=object.get("sourceid").toString();
		    ysjeEditText.setText(object.get("amount").toString());
			qsrqEditText.setText(object.get("qsrq").toString());
			jzrqEditText.setText(object.get("zzrq").toString());
			zxjgEditText.setText(object.get("finishedname").toString());
			zxjgId=object.get("finished").toString();
			xmmdEditText.setText(object.get("objective").toString());
			djrqEditText.setText(object.get("billdate").toString());
			jbrEditText.setText(object.get("Empname").toString());
			ywyId=object.get("Empid").toString();
			zdrEditText.setText(object.get("opname").toString());
			break;
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
		case 4:
			if (returnJson.equals("")) {
				showToastPromopt("删除成功！");
				Intent intent=new Intent();
//				intent.putExtra("id", returnJsonId);
//				intent.putExtra("name", jhmcEditText.getText().toString());
				setResult(RESULT_OK,intent);
				onExecuteFh();
			} else {
				showToastPromopt("删除失败！" + returnJson.substring(5));
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
			intent.putExtra("type", "PROJXMLY");
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
		case R.id.jbr_edittext:
            intent.setClass(activity, CommonXzjbrActivity.class);
            startActivityForResult(intent, 4);
            break;
		case R.id.scxm_textview:
			new AlertDialog.Builder(activity)
			.setTitle("确定要删除当前记录吗？")
			.setNegativeButton("删除",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0,
								int arg1) {
							deleteXmDate();
						}
					}).setPositiveButton("取消", null).show();
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
				jbrEditText.setText(data.getExtras().getString("name"));
			}
		}
	}
}