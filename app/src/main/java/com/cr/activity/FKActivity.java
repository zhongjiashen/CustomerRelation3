package com.cr.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class FKActivity extends BaseActivity implements OnClickListener{
	private ImageButton saveButton;
	private Spinner spinner;
	private EditText nrEditText;
	private String itemName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fk);
        initActivity();
        addFHMethod();
    }
    /**
	 * 初始化Activity
	 */
	private void initActivity(){
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		spinner=(Spinner) findViewById(R.id.lx_spinner);
		String[]itemStrings={"建议","投诉"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, itemStrings);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				itemName=spinner.getSelectedItem().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		nrEditText=(EditText) findViewById(R.id.nr_edittext);
	}
	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.save:
			searchDate();
			break;
		default:
			break;
		}
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opname", ShareUserInfo.getUserName(context));
//		Log.v("dddd", spinner.getSelectedItem().toString()+"");
//		Log.v("dddd", itemName.equals("建议")?"1":"2"+":值");
		parmMap.put("lb", itemName.equals("建议")?"1":"2");
		parmMap.put("info", nrEditText.getText().toString());
		findServiceData(0,ServerURL.SUGGESTION,parmMap);
	}
	@Override
	public void executeSuccess() {
		if(returnJson.equals("")){
			showToastPromopt("保存成功！");
			finish();
		}
	}
}