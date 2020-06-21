package com.cr.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 *新增项目
 * 
 * @author caiyanfei
 * @version $Id: GzptXzxmActivity.java, v 0.1 2015-1-13 下午3:30:34 caiyanfei Exp $
 */
public class GzptXzxmActivity extends BaseActivity implements OnClickListener{
	
	EditText xmmcEditText;
	ImageButton saveButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_xzxm);
		addFHMethod();
		initActivity();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		xmmcEditText=(EditText) findViewById(R.id.xmmc_edit);
	}
	/**
	 * 连接网络的操作(拜访录入新增项目保存)
	 */
	private void saveXmDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("itemid","0");
		parmMap.put("clientid",ShareUserInfo.getKey(context, "clientid"));
		parmMap.put("title",xmmcEditText.getText().toString());
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		findServiceData2(0,ServerURL.ITEMSAVE,parmMap,true);
	}
	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
		case 0:
			if(returnJson.equals("")){
				showToastPromopt("保存成功！");
				Intent intent=new Intent();
				intent.putExtra("id",returnJsonId);
				intent.putExtra("title",xmmcEditText.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}else{
				showToastPromopt("保存失败,"+returnJson.substring(5));
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.save:
			if(xmmcEditText.getText().toString().equals("")){
				showToastPromopt("项目名称不能为空");
				return;
			}else{
				saveXmDate();
			}
			break;
		default:
			break;
		}
	}
}