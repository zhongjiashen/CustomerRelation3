package com.cr.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cr.model.DQ;
import com.cr.model.DW;
import com.cr.model.GSLXRDetail;
import com.cr.model.SJZD;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 客户资料-新增单位
 * 
 * @author Administrator
 * 
 */
public class GzptKhzlXzdwActivity extends BaseActivity implements
		OnClickListener {
	private GSLXRDetail gslxrDetail;
	EditText dwEditText, gh1EditText, sj1EditText, qq1EditText, wzEditText,
			dzEditText, frdwEditText, yxEditText;
	private Spinner dqsSpinner, dqs2Spinner, dqqSpinner, khdjSpinner,
			hylxSpinner, khlySpinner, khlxSpinner;
	private ArrayAdapter<String> dqsAdapter, dqs2Adapter, dqqAdapter,
			khdjAdapter, hylxAdapter, khlyAdapter, khlxAdapter;
	List<SJZD> khdjList = new ArrayList<SJZD>();
	List<SJZD> hylxList = new ArrayList<SJZD>();
	List<SJZD> khlyList = new ArrayList<SJZD>();
	List<SJZD> khlxList = new ArrayList<SJZD>();
	List<DQ> dqsList = new ArrayList<DQ>();
	List<DQ> dqs2List = new ArrayList<DQ>();
	List<DQ> dqqList = new ArrayList<DQ>();
	Map<String, Object> dwMap = null;
	String dwmcid, dqsid, dqs2id, dqqid, khdjid, hylxid, khlyid, khlxid;
	private TextView titleTextView;
	private ImageButton saveButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_khzl_xzdw);
		initActivity();
		addFHMethod();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		saveButton = (ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		dwEditText = (EditText) findViewById(R.id.bf_dwmc);
		dqsSpinner = (Spinner) findViewById(R.id.bf_dqs);
		dqsSpinner.setOnItemSelectedListener(itemSelectedListenerDqs);
		dqs2Spinner = (Spinner) findViewById(R.id.bf_dqs2);
		dqs2Spinner.setOnItemSelectedListener(itemSelectedListenerDqs2);
		dqqSpinner = (Spinner) findViewById(R.id.bf_dqq);
		dqqSpinner.setOnItemSelectedListener(itemSelectedListenerDqq);
		khdjSpinner = (Spinner) findViewById(R.id.bf_khdj);
		khdjSpinner.setOnItemSelectedListener(itemSelectedListenerKhdj);
		hylxSpinner = (Spinner) findViewById(R.id.bf_hylx);
		hylxSpinner.setOnItemSelectedListener(itemSelectedListenerHylx);
		khlySpinner = (Spinner) findViewById(R.id.bf_khly);
		khlySpinner.setOnItemSelectedListener(itemSelectedListenerKhly);
		khlxSpinner = (Spinner) findViewById(R.id.bf_khlx);
		khlxSpinner.setOnItemSelectedListener(itemSelectedListenerKhlx);
		yxEditText = (EditText) findViewById(R.id.bf_yx);
		gh1EditText = (EditText) findViewById(R.id.bf_gh1);
		sj1EditText = (EditText) findViewById(R.id.bf_sj1);
		wzEditText = (EditText) findViewById(R.id.bf_wz);
		qq1EditText = (EditText) findViewById(R.id.bf_qq1);
		// yxEditText=(EditText) findViewById(R.id.bf_yx);
		dzEditText = (EditText) findViewById(R.id.bf_dz);
		frdwEditText = (EditText) findViewById(R.id.bf_frdb);
		titleTextView = (TextView) findViewById(R.id.title_textview);
		if (this.getIntent().hasExtra("object")) {
			dwMap = new HashMap<String, Object>();
			titleTextView.setText("编辑单位");
			gslxrDetail = (GSLXRDetail) this.getIntent().getExtras()
					.getSerializable("object");
			searchDwDate();
			// searchKhdjDate();
		} else {
			// searchDwDate();
			searchKhdjDate();
			titleTextView.setText("新增单位");
		}
	}

	/**
	 * 连接网络的操作(查询地区(省))
	 */
	private void searchDqSDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("levels", "2");
		parmMap.put("parentid", "0");
		findServiceData2(1, ServerURL.AREALIST, parmMap, false);
	}

	/**
	 * 连接网络的操作(查询地区(市))
	 */
	private void searchDqS2Date() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("levels", "3");
		parmMap.put("parentid", dqsid);
		findServiceData2(6, ServerURL.AREALIST, parmMap, false);
	}

	/**
	 * 连接网络的操作(查询地区(区))
	 */
	private void searchDqqDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("levels", "4");
		parmMap.put("parentid", dqs2id);
		findServiceData2(7, ServerURL.AREALIST, parmMap, false);
	}

	/**
	 * 连接网络的操作(查询客户等级)
	 */
	private void searchKhdjDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("zdbm", "KHDJ");
		findServiceData2(2, ServerURL.DATADICT, parmMap, false);
	}

	/**
	 * 连接网络的操作(查询行业类型)
	 */
	private void searchHylxDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("zdbm", "HYLX");
		findServiceData2(3, ServerURL.DATADICT, parmMap, false);
	}

	/**
	 * 连接网络的操作(查询客户来源)
	 */
	private void searchKhlyDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("zdbm", "KHLY");
		findServiceData2(4, ServerURL.DATADICT, parmMap, false);
	}

	/**
	 * 连接网络的操作(查询客户类型)
	 */
	private void searchKhlxDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("zdbm", "KHLX");
		findServiceData2(5, ServerURL.DATADICT, parmMap, false);
	}

	/**
	 * 连接网络的操作(保存单位信息)
	 */
	private void saveDwDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		if(this.getIntent().hasExtra("object")){
			parmMap.put("clientid", dwMap.get("id").toString());
		}else{
			parmMap.put("clientid", 0);
		}
		parmMap.put("cname", dwEditText.getText().toString());
		parmMap.put("areafullname", dqsSpinner.getSelectedItem().toString()
				+ "/" + dqs2Spinner.getSelectedItem().toString() + "/"
				+ dqqSpinner.getSelectedItem().toString());
		parmMap.put("typeid", khdjid);
		parmMap.put("hytypeid", hylxid);
		parmMap.put("csourceid", khlyid);
		parmMap.put("khtypeid", khlxid);
		parmMap.put("phone", gh1EditText.getText().toString());
		parmMap.put("frdb", frdwEditText.getText().toString());
		parmMap.put("cnet", wzEditText.getText().toString());
		// parmMap.put("qq",qq1EditText.getText().toString());
		parmMap.put("email", yxEditText.getText().toString());
		parmMap.put("address", dzEditText.getText().toString());
		findServiceData2(8, ServerURL.CLIENTSAVE, parmMap, false);
	}

	/**
	 * 连接网络查询单位的信息
	 */
	private void searchDwDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("clientid", gslxrDetail.getClientid());
		findServiceData2(9, ServerURL.CLIENTINFO, parmMap, false);
	}

	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.save:
			if (dwEditText.getText().toString().equals("")) {
				showToastPromopt("请输入单位名称信息！");
				return;
			}
			saveDwDate();
			break;
		default:
			break;
		}
	}

	/**
	 * 数据返回成功执行的方法
	 * 
	 * @see BaseActivity#executeSuccess()
	 */
	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
		case 0:
			break;
		case 1:
			String sheng = "";
			int index = 0;
			if (dwMap != null) {
				String[] s = dwMap.get("areafullname").toString().split("/");
				if (s.length > 1) {
					sheng = dwMap.get("areafullname").toString().split("/")[1];
				}
			}
			dqsList = DQ.paseJsonToObject(returnJson);
			String[] dqString = new String[dqsList.size()];
			for (int i = 0; i < dqsList.size(); i++) {
				dqString[i] = dqsList.get(i).getName();
				if (dqsList.get(i).getName().equals(sheng)) {
					index = i;
				}
			}
			dqsAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, dqString);
			dqsAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dqsSpinner.setAdapter(dqsAdapter);
			dqsSpinner.setSelection(index);
			break;
		case 2:
			String khdjid = "";
			int index2 = 0;
			if (dwMap != null) {
				khdjid = dwMap.get("c_typeid").toString();
			}
			khdjList = SJZD.parseJsonToObject(returnJson);
			String[] bffsString = new String[khdjList.size()];
			for (int i = 0; i < khdjList.size(); i++) {
				bffsString[i] = khdjList.get(i).getDictmc();
				if (khdjList.get(i).getId().equals(khdjid)) {
					index2 = i;
				}
			}
			khdjAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, bffsString);
			khdjAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			khdjSpinner.setAdapter(khdjAdapter);
			khdjSpinner.setSelection(index2);
			searchHylxDate();
			break;
		case 3:
			String hylxid = "";
			int index3 = 0;
			if (dwMap != null) {
				hylxid = dwMap.get("c_hytypeid").toString();
			}
			hylxList = SJZD.parseJsonToObject(returnJson);
			String[] hylxString = new String[hylxList.size()];
			for (int i = 0; i < hylxList.size(); i++) {
				hylxString[i] = hylxList.get(i).getDictmc();
				if (hylxList.get(i).getId().equals(hylxid)) {
					index3 = i;
				}
			}
			hylxAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, hylxString);
			hylxAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			hylxSpinner.setAdapter(hylxAdapter);
			hylxSpinner.setSelection(index3);
			searchKhlyDate();
			break;
		case 4:
			String khlyid = "";
			int index4 = 0;
			if (dwMap != null) {
				khlyid = dwMap.get("c_csourceid").toString();
			}
			khlyList = SJZD.parseJsonToObject(returnJson);
			String[] khlyString = new String[khlyList.size()];
			for (int i = 0; i < khlyList.size(); i++) {
				khlyString[i] = khlyList.get(i).getDictmc();
				if (khlyList.get(i).getId().equals(khlyid)) {
					index4 = i;
				}
			}
			khlyAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, khlyString);
			khlyAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			khlySpinner.setAdapter(khlyAdapter);
			khlySpinner.setSelection(index4);
			searchKhlxDate();
			break;
		case 5:
			String khlxid = "";
			int index5 = 0;
			if (dwMap != null) {
				khlxid = dwMap.get("c_khtypeid").toString();
			}
			khlxList = SJZD.parseJsonToObject(returnJson);
			String[] khlxString = new String[khlxList.size()];
			for (int i = 0; i < khlxList.size(); i++) {
				khlxString[i] = khlxList.get(i).getDictmc();
				if (khlxList.get(i).getId().equals(khlxid)) {
					index5 = i;
				}
			}
			khlxAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, khlxString);
			khlxAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			khlxSpinner.setAdapter(khlxAdapter);
			khlxSpinner.setSelection(index5);
			searchDqSDate();
			break;
		case 6:
			String sheng6 = "";
			int index6 = 0;
			if (dwMap != null) {
				String[] s6 = dwMap.get("areafullname").toString().split("/");
				if (s6.length > 2) {
					sheng6 = dwMap.get("areafullname").toString().split("/")[2];
				}
			}
			dqs2List = DQ.paseJsonToObject(returnJson);
			String[] dqs2String = new String[dqs2List.size()];
			for (int i = 0; i < dqs2List.size(); i++) {
				dqs2String[i] = dqs2List.get(i).getName();
				// Log.v("dddd",dqs2List.get(i).getName()+":::"+sheng6);
				if (dqs2List.get(i).getName().equals(sheng6)) {
					index6 = i;
				}
			}
			dqs2Adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, dqs2String);
			dqs2Adapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dqs2Spinner.setAdapter(dqs2Adapter);
			dqs2Spinner.setSelection(index6);
			// searchDqqDate();
			break;
		case 7:
			String sheng7 = "";
			int index7 = 0;
			if (dwMap != null) {
				String[] s7 = dwMap.get("areafullname").toString().split("/");
				if (s7.length > 3) {
					sheng7 = dwMap.get("areafullname").toString().split("/")[3];
				}
			}
			dqqList = DQ.paseJsonToObject(returnJson);
			String[] dqqString = new String[dqqList.size()];
			for (int i = 0; i < dqqList.size(); i++) {
				dqqString[i] = dqqList.get(i).getName();
				if (dqqList.get(i).getName().equals(sheng7)) {
					index7 = i;
				}
			}
			dqqAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, dqqString);
			dqqAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			dqqSpinner.setAdapter(dqqAdapter);
			dqqSpinner.setSelection(index7);
			// searchKhdjDate();
			break;
		case 8:
			if (returnJson.equals("")) {
				showToastPromopt("保存成功");
				Intent intent = new Intent();
				intent.putExtra("id", returnJsonId);
				intent.putExtra("name", dwEditText.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			} else {
				showToastPromopt("保存失败" + returnJson.substring(5));
			}
			break;
		case 9:
			// Log.v("dddd",returnJson);
			dwMap = DW.paseJsonToObject2(returnJson).get(0);
			dwEditText
					.setText(dwMap.get("cname").toString().equals("null") ? ""
							: dwMap.get("cname").toString());
			frdwEditText
					.setText(dwMap.get("frdb").toString().equals("null") ? ""
							: dwMap.get("frdb").toString());
			gh1EditText
					.setText(dwMap.get("phone").toString().equals("null") ? ""
							: dwMap.get("phone").toString());
			wzEditText.setText(dwMap.get("cnet").toString().equals("null") ? ""
					: dwMap.get("cnet").toString());
			yxEditText
					.setText(dwMap.get("email").toString().equals("null") ? ""
							: dwMap.get("email").toString());
			dzEditText
					.setText(dwMap.get("address").toString().equals("null") ? ""
							: dwMap.get("address").toString());
			searchKhdjDate();
			break;
		default:
			break;
		}
	}

	OnItemSelectedListener itemSelectedListenerXb = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	OnItemSelectedListener itemSelectedListenerDqs = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			dqsid = dqsList.get(arg2).getId();
			searchDqS2Date();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	OnItemSelectedListener itemSelectedListenerDqs2 = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			dqs2id = dqs2List.get(arg2).getId();
			searchDqqDate();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	OnItemSelectedListener itemSelectedListenerDqq = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	OnItemSelectedListener itemSelectedListenerKhdj = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			khdjid = khdjList.get(arg2).getId();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	OnItemSelectedListener itemSelectedListenerHylx = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			hylxid = hylxList.get(arg2).getId();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	OnItemSelectedListener itemSelectedListenerKhly = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			khlyid = khlyList.get(arg2).getId();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	OnItemSelectedListener itemSelectedListenerKhlx = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			khlxid = khlxList.get(arg2).getId();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
}