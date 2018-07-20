package com.cr.activity.xjyh.fyzc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcActivity;
import com.cr.adapter.xjyh.fyzc.XjyhFyzcAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 现金银行-费用支出-详情
 * 
 * @author Administrator
 * 
 */
public class XjyhFyzcDetailActivity extends BaseActivity implements
		OnClickListener {
	private ImageView shImageView;
	private ImageButton saveImageButton;
	private TextView djbhTextView;
	private Button shButton, sdButton;
	private EditText wldwEditText, jslxEditText, zjzhEditText, fkfsEditText,
			fkjeEditText, djrqEditText, jbrEditText, bzxxEditText,hjjeEditText;
	private String wldwId="", jslxId="", zjzhId="", fkfsId="", jbrId="";
	private CustomListView listview;
	private List<Map<String, Object>> list;
	private LinearLayout xzzcLinearLayout;
	BaseAdapter adapter;
	private int selectIndex;
	private String shzt; // 审核状态
	Map<String, Object> object;
    private EditText xmEditText;
	private EditText etBm;
	private EditText etFplx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xjyh_fyzc_detail);
		addFHMethod();
		initActivity();
		searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		etBm = (EditText) findViewById(R.id.et_bm);
		etFplx = (EditText) findViewById(R.id.et_fplx);
		saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
		saveImageButton.setOnClickListener(this);
		djbhTextView = (TextView) findViewById(R.id.djbh_textview);
		shImageView = (ImageView) findViewById(R.id.sh_imageview);
		listview = (CustomListView) findViewById(R.id.xzsp_listview);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(activity, KtXjyhFyzcAddZcActivity.class);
				intent.putExtra("object", (Serializable) list.get(arg2));
				intent.putExtra("update",false);

				startActivity(intent);
			}
		});

		wldwEditText = (EditText) findViewById(R.id.wldw_edittext);
		wldwEditText.setOnClickListener(this);
		jslxEditText = (EditText) findViewById(R.id.jslx_edittext);
		jslxEditText.setOnClickListener(this);
		zjzhEditText = (EditText) findViewById(R.id.zjzh_edittext);
		zjzhEditText.setOnClickListener(this);
		fkfsEditText = (EditText) findViewById(R.id.fkfs_edittext);
		fkfsEditText.setOnClickListener(this);
		djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
		djrqEditText.setOnClickListener(this);
		jbrEditText = (EditText) findViewById(R.id.jbr_edittext);
		jbrEditText.setOnClickListener(this);
		fkjeEditText = (EditText) findViewById(R.id.fkje_edittext);
		hjjeEditText = (EditText) findViewById(R.id.hjje_edittext);
		bzxxEditText = (EditText) findViewById(R.id.bzxx_edittext);
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
        xmEditText=(EditText) findViewById(R.id.xm_edittext);
		shButton = (Button) findViewById(R.id.sh_button);
		shButton.setOnClickListener(this);
		sdButton = (Button) findViewById(R.id.sd_button);
		sdButton.setOnClickListener(this);
		xzzcLinearLayout = (LinearLayout) findViewById(R.id.xzzc_linearlayout);
		xzzcLinearLayout.setOnClickListener(this);

		list = new ArrayList<Map<String, Object>>();
		adapter = new XjyhFyzcAddAdapter(list, this);
		listview.setAdapter(adapter);
	}

	/**
	 * 连接网络的操作(查询主表的内容)
	 */
	private void searchDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("parms", "FYKZ");
		parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
		findServiceData2(0, ServerURL.BILLMASTER, parmMap, false);
	}

	/**
	 * 连接网络的操作(删单)
	 */
	private void searchDateSd() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("tabname", "tb_expense");
		parmMap.put("pkvalue", this.getIntent().getExtras().getString("billid"));
		findServiceData2(2, ServerURL.BILLDELMASTER, parmMap, false);
	}

	/**
	 * 连接网络的操作（查询从表的内容）
	 */
	private void searchDate2() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("parms", "FYKZ");
		parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
		findServiceData2(1, ServerURL.BILLDETAIL, parmMap, false);
	}

	// /**
	// * 连接网络的操作(删单)
	// */
	// private void searchDateSd() {
	// Map<String, Object> parmMap = new HashMap<String, Object>();
	// parmMap.put("dbname", ShareUserInfo.getDbName(context));
	// parmMap.put("opid", ShareUserInfo.getUserId(context));
	// parmMap.put("tabname", "tb_pay");
	// parmMap.put("pkvalue", this.getIntent().getExtras().getString("billid"));
	// findServiceData2(2, ServerURL.BILLDELMASTER, parmMap, false);
	// }

	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
		if (returnSuccessType == 0) {
			if (returnJson.equals("")) {
				return;
			}
			object = ((List<Map<String, Object>>) PaseJson
					.paseJsonToObject(returnJson)).get(0);
			djbhTextView.setText(object.get("code").toString());
			if (object.get("shzt").toString().equals("0")) {
				shImageView.setBackgroundResource(R.drawable.wsh);
			} else if (object.get("shzt").toString().equals("1")) {
				shImageView.setBackgroundResource(R.drawable.ysh);
			} else if (object.get("shzt").toString().equals("2")) {
				shImageView.setBackgroundResource(R.drawable.shz);
			}
			wldwEditText.setText(object.get("cname").toString());
			wldwId = object.get("clientid").toString();
			jslxEditText
					.setText(object.get("ispp").toString().equals("0") ? "往来结算"
							: "现款结算");
			if(object.get("ispp").toString().equals("0")){
            	fkjeEditText.setText("0");
            	fkjeEditText.setEnabled(false);
            	fkfsEditText.setEnabled(false);
            	zjzhEditText.setEnabled(false);
            }else{
            	fkjeEditText.setEnabled(true);
            	fkfsEditText.setEnabled(true);
            	zjzhEditText.setEnabled(true);
            }
			fkfsId = object.get("paytypeid").toString();
			zjzhEditText.setText(object.get("bankname").toString());
			zjzhId = object.get("bankid").toString();
			fkfsEditText.setText(object.get("paytypename").toString());
			jslxId = object.get("paytypeid").toString();
			fkjeEditText.setText(object.get("receipt").toString());
			djrqEditText.setText(object.get("billdate").toString());
			etBm.setText(object.get("depname").toString());
			etFplx.setText(object.get("billtypename").toString());
			jbrEditText.setText(object.get("empname").toString());
			jbrId = object.get("empid").toString();
			bzxxEditText.setText(object.get("memo").toString());
			hjjeEditText.setText(object.get("amount").toString());
            xmEditText.setText(object.get("projectname").toString());
			showZdr(object);
			searchDate2();// 查询从表信息
		} else if (returnSuccessType == 1) {
			list.clear();
			if(returnJson.equals("")){
				return;
			}
			list.addAll((List<Map<String, Object>>) PaseJson
					.paseJsonToObject(returnJson));
			adapter.notifyDataSetChanged();
		} else if (returnSuccessType == 2) {
			if (returnJson.equals("")) {
				showToastPromopt("删除成功");
				setResult(RESULT_OK);
				finish();
			} else {
				showToastPromopt("删除失败" + returnJson.substring(5));
			}
		} else if (returnSuccessType == 3) {
			if (returnJson.equals("")) {
				showToastPromopt("操作成功");
				setResult(RESULT_OK);
				finish();
			} else {
				showToastPromopt("操作失败" + returnJson.substring(5));
			}
		}
	}

	/**
	 * 连接网络的操作(保存)
	 */
	private void searchDateSave() {
		if (wldwEditText.getText().toString().equals("")) {
			showToastPromopt("请选择往来单位");
			return;
		}
		if (jslxEditText.getText().toString().equals("")) {
			showToastPromopt("请选择结算类型");
			return;
		}
		if(jslxId.equals("1")){
			if (zjzhEditText.getText().toString().equals("")) {
				showToastPromopt("请选择资金账户");
				return;
			}
			if (fkfsEditText.getText().toString().equals("")) {
				showToastPromopt("请选择付款方式");
				return;
			}
			if (list.size() == 0) {
				showToastPromopt("请增加支出类型");
				return;
			}
		    double hjje=0;
		    for(Map<String, Object>m:list){
		        hjje+=Double.parseDouble(m.get("amount").toString().replace("￥", "").equals("")?"0":m.get("amount").toString().replace("￥", ""));
		    }
            double fkje=Double.parseDouble(fkjeEditText.getText().toString().replace("￥", "").equals("")?"0":fkjeEditText.getText().toString().replace("￥", ""));
            
            if(fkje<=0||fkje>hjje){
                showToastPromopt("付款金额不在范围内！");
                return;
            }
        }
		if (list.size() == 0) {
            showToastPromopt("请增加支出类型");
            return;
        }
		if (djrqEditText.getText().toString().equals("")) {
			showToastPromopt("请选择单据日期");
			return;
		} 
		if (jbrEditText.getText().toString().equals("")) {
            showToastPromopt("请选择业务员");
            return;
        }
		JSONArray arrayMaster = new JSONArray();
		JSONArray arrayDetail = new JSONArray();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("billid",
					this.getIntent().getExtras().getString("billid"));
			jsonObject.put("code", object.get("code").toString());
			jsonObject.put("billdate", djrqEditText.getText().toString());
			jsonObject.put("clientid", wldwId);
			jsonObject.put("paytypeid", fkfsId);
			jsonObject.put("ispp", jslxId);
			jsonObject.put("bankid", zjzhId);
			jsonObject.put("receipt", fkjeEditText.getText().toString());
			jsonObject.put("amount", hjjeEditText.getText().toString());
			jsonObject.put("empid", jbrId);
			jsonObject.put("memo", bzxxEditText.getText().toString());
			jsonObject.put("opid", ShareUserInfo.getUserId(context));
			arrayMaster.put(jsonObject);
			for (Map<String, Object> map : list) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("billid", this.getIntent().getExtras()
						.getString("billid"));
				jsonObject2.put("itemno", "0");
				jsonObject2.put("ietypeid", map.get("ietypeid").toString());
				jsonObject2.put("amount ", map.get("amount").toString());
				arrayDetail.put(jsonObject2);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}// 代表新增
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		// parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("tabname", "tb_expense");
		parmMap.put("parms", "FYKZ");
		parmMap.put("master", arrayMaster.toString());
		parmMap.put("detail", arrayDetail.toString());
		findServiceData2(3, ServerURL.BILLSAVE, parmMap, false);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.sh_button:
			intent.putExtra("tb", "tb_expense");
			intent.putExtra("opid", object.get("opid").toString());
			intent.putExtra("billid",
					this.getIntent().getExtras().getString("billid"));
			intent.setClass(activity, JxcCgglCgddShlcActivity.class);
			startActivityForResult(intent,6);
			break;
		case R.id.sd_button:
			new AlertDialog.Builder(activity)
			.setTitle("确定要删除当前记录吗？")
			.setNegativeButton("删除",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0,
								int arg1) {
							searchDateSd();
						}
					}).setPositiveButton("取消", null).show();
			break;
//		case R.id.xzzc_linearlayout:
//			intent.setClass(this, XjyhFyzcAddZcActivity.class);
//			startActivityForResult(intent, 0);
//			break;
//		case R.id.wldw_edittext:
//			intent.setClass(this, CommonXzdwActivity.class);
//			intent.putExtra("type", "0");// 供应商
//			startActivityForResult(intent, 1);
//			break;
//		case R.id.jslx_edittext:
//			intent.setClass(activity, CommonXzzdActivity.class);
//			intent.putExtra("type", "FKLX");
//			startActivityForResult(intent, 4);
//			break;
//		case R.id.zjzh_edittext:
//			intent.setClass(activity, CommonXzzdActivity.class);
//			intent.putExtra("type", "BANK");
//			startActivityForResult(intent, 3);
//			break;
//		case R.id.fkfs_edittext:
//			intent.setClass(activity, CommonXzzdActivity.class);
//			intent.putExtra("type", "PAYTYPE");
//			startActivityForResult(intent, 2);
//			break;
//		case R.id.djrq_edittext:
//			date_init(djrqEditText);
//			break;
//		case R.id.jbr_edittext:
//			intent.setClass(activity, CommonXzjbrActivity.class);
//			startActivityForResult(intent, 5);
//			break;
//		case R.id.save_imagebutton:
//			searchDateSave();// 保存
//			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {// 增加费用
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", data.getExtras().getString("name"));
				map.put("amount", data.getExtras().getString("amount"));
				map.put("ietypeid", data.getExtras().getString("ietypeid"));
				list.add(map);
				adapter.notifyDataSetChanged();
				double hjfy=0;
                for(Map<String, Object> m:list){
                    hjfy+=Double.parseDouble(m.get("amount").toString());
                }
                hjjeEditText.setText(FigureTools.sswrFigure(hjfy));
			} else if (requestCode == 1) {// 往来单位
				wldwEditText.setText(data.getExtras().getString("cname"));
				wldwId = data.getExtras().getString("id");
			} else if (requestCode == 2) {// 付款方式
				fkfsEditText.setText(data.getExtras().getString("dictmc"));
				fkfsId = data.getExtras().getString("id");
			} else if (requestCode == 3) {// 资金账户
				zjzhEditText.setText(data.getExtras().getString("dictmc"));
				zjzhId = data.getExtras().getString("id");
			} else if (requestCode == 4) {// 结算类型
				jslxEditText.setText(data.getExtras().getString("dictmc"));
				jslxId = data.getExtras().getString("id");
				if(data.getExtras().getString("id").equals("0")){
                	fkjeEditText.setText("0");
                	fkjeEditText.setEnabled(false);
                	fkfsEditText.setEnabled(false);
                	zjzhEditText.setEnabled(false);
                	fkfsEditText.setText("");
                	zjzhEditText.setText("");
                	fkfsId="";
                	zjzhId="";
                }else{
                	fkjeEditText.setEnabled(true);
                	fkfsEditText.setEnabled(true);
                	zjzhEditText.setEnabled(true);
                }
			} else if (requestCode == 5) {// 经办人
				jbrEditText.setText(data.getExtras().getString("name"));
				jbrId = data.getExtras().getString("id");
			}else if(requestCode==6){
                searchDate();
            }
		}
	}
}
