package com.cr.activity.jxc.cggl.cgfk;

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
import android.widget.ScrollView;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzfklx2Activity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcActivity;
import com.cr.adapter.jxc.cggl.cgfk.JxcCgglCgfkAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-采购管理-采购付款-详情
 * 
 * @author Administrator
 * 
 */
public class JxcCgglCgfkDetailActivity extends BaseActivity implements
		OnClickListener {
	private ImageButton saveImageButton;
	private ImageView shImageView;
	private Button shButton, sdButton;
	private TextView xzspnumTextView, djbhTextView;
	private EditText bzxxEditText, gysEditText, lxrEditText, djrqEditText,
			jbrEditText, fkjeEditText, fklxEditText, jsfsEditText,
			zjzhEditText, dqyfEditText, dqyf2EditText, cyfjeEditText,
			sfjeEditText;
	private CustomListView listview;
	String gysId = "", lxrId= "", jbrId= "", rkckId= "", fklxId= "", jsfsId= "", zjzhId= "";
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private LinearLayout xzspLinearLayout;
	BaseAdapter adapter;
	LinearLayout gldjcgLinearLayout, xzxsddLinearLayout;
	ScrollView addScrollView;
	private int selectIndex;
	String billid; // 选择完关联的单据后返回的单据的ID
	Map<String, Object> object;
	LinearLayout xzyydShowLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jxc_cggl_cgfk_detail);
		addFHMethod();
		initActivity();
		searchDate();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		billid = this.getIntent().getExtras().getString("billid");
		xzyydShowLayout = (LinearLayout) findViewById(R.id.show_yyd_layout);
		saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
		saveImageButton.setOnClickListener(this);
		djbhTextView = (TextView) findViewById(R.id.djbh_textview);
		shImageView = (ImageView) findViewById(R.id.sh_imageview);
		gysEditText = (EditText) findViewById(R.id.gys_edittext);
		gysEditText.setOnClickListener(this);
		shButton = (Button) findViewById(R.id.sh_button);
		shButton.setOnClickListener(this);
		sdButton = (Button) findViewById(R.id.sd_button);
		sdButton.setOnClickListener(this);
		dqyfEditText = (EditText) findViewById(R.id.dqyf_edittext);
		dqyf2EditText = (EditText) findViewById(R.id.dqyf2_edittext);
		cyfjeEditText = (EditText) findViewById(R.id.cyfje_edittext);
		sfjeEditText = (EditText) findViewById(R.id.sfje_edittext);
		listview = (CustomListView) findViewById(R.id.xzsp_listview);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				selectIndex = arg2;
				Intent intent = new Intent();
				intent.setClass(activity, JxcCgglCgfkXzyyDetail2Activity.class);
				Map<String, Object> map = list.get(arg2);
				map.put("cname", gysEditText.getText().toString());
				intent.putExtra("object", (Serializable) map);
				startActivityForResult(intent, 4);
			}
		});
		adapter = new JxcCgglCgfkAddAdapter(list, this);
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
		djrqEditText.setOnClickListener(this);
		jbrEditText = (EditText) findViewById(R.id.jbr_edittext);
		jbrEditText.setOnClickListener(this);
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
		xzspnumTextView = (TextView) findViewById(R.id.xzspnum_textview);
		addScrollView = (ScrollView) findViewById(R.id.add_scrollview);
		gldjcgLinearLayout = (LinearLayout) findViewById(R.id.gldjcg_linearlayout);
		fkjeEditText = (EditText) findViewById(R.id.fkje_edittext);
		fklxEditText = (EditText) findViewById(R.id.fklx_edittext);
		fklxEditText.setOnClickListener(this);
		jsfsEditText = (EditText) findViewById(R.id.jsfs_edittext);
		jsfsEditText.setOnClickListener(this);
		zjzhEditText = (EditText) findViewById(R.id.zjzh_edittext);
		zjzhEditText.setOnClickListener(this);
		xzspLinearLayout = (LinearLayout) findViewById(R.id.xzyydh_linearlayout);
		xzspLinearLayout.setOnClickListener(this);

	}

	/**
	 * 连接网络的操作(查询主表的内容)
	 */
	private void searchDate() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("parms", "CGFK");
		parmMap.put("billid", billid);
		findServiceData2(1, ServerURL.BILLMASTER, parmMap, false);
	}

	/**
	 * 连接网络的操作（查询从表的内容）
	 */
	private void searchDate2() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("parms", "CGFK");
		parmMap.put("billid", billid);
		findServiceData2(2, ServerURL.BILLDETAIL, parmMap, false);
	}

	/**
	 * 连接网络的操作(删单)
	 */
	private void searchDateSd() {
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("tabname", "tb_pay");
		parmMap.put("pkvalue", this.getIntent().getExtras().getString("billid"));
		findServiceData2(3, ServerURL.BILLDELMASTER, parmMap, false);
	}

	/**
	 * 连接网络的操作(保存)
	 */
	private void searchDateSave() {
		// if (list.size() == 0) {
		// showToastPromopt("请选择商品");
		// return;
		// }else
	    if (gysEditText.getText().toString().equals("")) {
            showToastPromopt("请选择供应商！");
            return;
        }else if (fklxEditText.getText().toString().equals("")) {
            showToastPromopt("请选择付款类型");
            return;
        }
            if (jsfsEditText.getText().toString().equals("")) {
                showToastPromopt("请选择结算方式");
                return;
            } else if (zjzhEditText.getText().toString().equals("")) {
                showToastPromopt("请选择资金账户");
                return;
            }
        if (fklxId.equals("0")) {
            if (list.size() == 0) {
                showToastPromopt("请选择引用单号");
                return;
            }
        }if (djrqEditText.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        }if (fkjeEditText.getText().toString().equals("")) {
            showToastPromopt("付款金额不能为空！");
            return;
        } if (jbrEditText.getText().toString().equals("")) {
            showToastPromopt("请选择业务员");
            return;
        }
		JSONArray arrayMaster = new JSONArray();
		JSONArray arrayDetail = new JSONArray();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("billid", billid);
			jsonObject.put("code", object.get("code").toString());
			jsonObject.put("billdate", object.get("billdate").toString());
			jsonObject.put("ispp", fklxId);
			jsonObject.put("paytypeid", jsfsId);
			jsonObject.put("bankid", zjzhId);
			jsonObject.put("clientid", gysId);// 供应商ID
			jsonObject.put("factamount", fkjeEditText.getText().toString());
			jsonObject.put("exemanid", jbrId);
			jsonObject.put("amount", fkjeEditText.getText().toString());
			jsonObject.put("memo", bzxxEditText.getText().toString());
			jsonObject.put("opid", ShareUserInfo.getUserId(context));
			arrayMaster.put(jsonObject);
			for (Map<String, Object> map : list) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("billid", billid);
				jsonObject2.put("itemno", "0");
				jsonObject2.put("refertype", map.get("refertype") == null ? map
						.get("billtypeid").toString() : map.get("refertype")
						.toString());
				jsonObject2
						.put("referbillid ",
								map.get("referbillid") == null ? map.get(
										"billid").toString() : map.get(
										"referbillid").toString());
				jsonObject2.put("amount ",
						map.get("bcjs") == null ? map.get("amount").toString()
								: map.get("bcjs").toString());
				arrayDetail.put(jsonObject2);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("tabname", "tb_pay");
		parmMap.put("parms", "CGFK");
		parmMap.put("master", arrayMaster.toString());
		if(list.size()==0){
		    parmMap.put("detail", "");
        }else{
            parmMap.put("detail", arrayDetail.toString());
        }
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
			object = ((List<Map<String, Object>>) PaseJson
					.paseJsonToObject(returnJson)).get(0);
			gysEditText.setText(object.get("cname").toString());
			if (object.get("ispp").toString().equals("0")) {
				fklxEditText.setText("应付账款");
				xzyydShowLayout.setVisibility(View.VISIBLE);
				fkjeEditText.setEnabled(false);
			} else if (object.get("ispp").toString().equals("1")) {
				fklxEditText.setText("预付账款");
				xzyydShowLayout.setVisibility(View.GONE);
				fkjeEditText.setEnabled(true);
			}
			jsfsEditText.setText(object.get("paytypename").toString());
			zjzhEditText.setText(object.get("bankname").toString());
			dqyfEditText.setText(FigureTools.sswrFigure(object.get("suramt").toString()));
			dqyf2EditText.setText(FigureTools.sswrFigure(object.get("balance").toString()));
			fkjeEditText.setText(FigureTools.sswrFigure(object.get("amount").toString()));
			cyfjeEditText.setText(FigureTools.sswrFigure(object.get("yufuje").toString()));
			sfjeEditText.setText(FigureTools.sswrFigure(object.get("factamount").toString()));
			djrqEditText.setText(object.get("billdate").toString());

			jbrEditText.setText(object.get("empname").toString());
			bzxxEditText.setText(object.get("memo").toString());
			gysId = object.get("clientid").toString();
			fklxId = object.get("ispp").toString();
			jsfsId = object.get("paytypeid").toString();
			zjzhId = object.get("bankid").toString();
			jbrId = object.get("exemanid").toString();
			djbhTextView.setText(object.get("code").toString());
			if (object.get("shzt").toString().equals("0")) {
				shImageView.setBackgroundResource(R.drawable.wsh);
			} else if (object.get("shzt").toString().equals("1")) {
				shImageView.setBackgroundResource(R.drawable.ysh);
			} else if (object.get("shzt").toString().equals("2")) {
				shImageView.setBackgroundResource(R.drawable.shz);
			}
			showZdr(object);
			searchDate2();// 查询订单中的商品
		} else if (returnSuccessType == 2) {// 管理单据成功后把信息填到里面（从表）
			if (returnJson.equals("")) {
				return;
			}
			list.clear();
			list.addAll((List<Map<String, Object>>) PaseJson
					.paseJsonToObject(returnJson));
//			list=(List<Map<String, Object>>) PaseJson
//					.paseJsonToObject(returnJson);
			xzspnumTextView.setText("共选择了" + list.size() + "引用");
			adapter.notifyDataSetChanged();
			double b=0;
			for (Map<String, Object> map : list) {
                b+=Double.parseDouble(
                        map.get("bcjs") == null ? map.get("amount").toString()
                                : map.get("bcjs").toString());
            }
			fkjeEditText.setText(b+"");
		} else if (returnSuccessType == 3) {
			if (returnJson.equals("")) {
				showToastPromopt("删除成功");
				setResult(RESULT_OK);
				finish();
			} else {
				showToastPromopt("删除失败" + returnJson.substring(5));
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
//		case R.id.xzyydh_linearlayout:
//			if (gysId.equals("")) {
//				showToastPromopt("请选择引用");
//				return;
//			}
//			intent.putExtra("type", "CGFK_BILL");
//			intent.putExtra("clientid", gysId);
//			intent.setClass(this, JxcCgglCgfkXzyyActivity.class);
//			startActivityForResult(intent, 0);
//			break;
//		case R.id.gys_edittext:
//		    intent.setClass(this, CommonXzdwActivity.class);
//            intent.putExtra("type","2");
//			startActivityForResult(intent, 1);
//			break;
//		case R.id.lxr_edittext:
//			if (gysId.equals("")) {
//				showToastPromopt("请先选择供应商信息");
//				return;
//			}
//			intent.setClass(activity, CommonXzlxrActivity.class);
//			intent.putExtra("clientid", gysId);
//			startActivityForResult(intent, 2);
//			break;
//		case R.id.djrq_edittext:
//			date_init(djrqEditText);
//			break;
//		case R.id.jbr_edittext:
//			intent.setClass(activity, CommonXzjbrActivity.class);
//			startActivityForResult(intent, 3);
//			break;
//		case R.id.save_imagebutton:
//			searchDateSave();// 保存
//			break;
//		case R.id.xzxsdd_linearlayout:// 选择销售订单
//			intent.setClass(activity, JxcCgglCgfkActivity.class);
//			intent.putExtra("select", "true");
//			startActivityForResult(intent, 5);
//			break;
//		case R.id.rkck_edittext:
//			intent.setClass(activity, CommonXzzdActivity.class);
//			intent.putExtra("type", "STORE");
//			startActivityForResult(intent, 6);
//			break;
//		case R.id.fklx_edittext:
//			intent.setClass(activity, CommonXzfklx2Activity.class);
//			startActivityForResult(intent, 7);
//			break;
//		case R.id.jsfs_edittext:
//			intent.setClass(activity, CommonXzzdActivity.class);
//			intent.putExtra("type", "PAYTYPE");
//			startActivityForResult(intent, 8);
//			break;
//		case R.id.zjzh_edittext:
//			intent.setClass(activity, CommonXzzdActivity.class);
//			intent.putExtra("type", "BANK");
//			startActivityForResult(intent, 9);
//			break;
		case R.id.sh_button:
			intent.putExtra("tb", "tb_pay");
			intent.putExtra("opid", object.get("opid").toString());
			intent.putExtra("billid",
					this.getIntent().getExtras().getString("billid"));
			intent.setClass(activity, JxcCgglCgddShlcActivity.class);
			startActivityForResult(intent,10);
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
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {// 选择引用
//				list.clear();
				List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
						.getSerializableExtra("object");
				list.addAll(cpList);
				xzspnumTextView.setText("共选择了" + list.size() + "引用");
				adapter.notifyDataSetChanged();
				double b=0;
	            for (Map<String, Object> map : list) {
	                b+=Double.parseDouble(
	                        map.get("bcjs") == null ? map.get("amount").toString()
	                                : map.get("bcjs").toString());
	            }
	            fkjeEditText.setText(b+"");
			} else if (requestCode == 1) {
				gysEditText.setText(data.getExtras().getString("name"));
				gysId = data.getExtras().getString("id");
				dqyfEditText.setText(data.getExtras().getString("yf"));
                dqyf2EditText.setText(data.getExtras().getString("yf2"));
			} else if (requestCode == 2) {// 联系人
				lxrEditText.setText(data.getExtras().getString("name"));
				lxrId = data.getExtras().getString("id");
			} else if (requestCode == 3) {// 经办人
				jbrEditText.setText(data.getExtras().getString("name"));
				jbrId = data.getExtras().getString("id");
			} else if (requestCode == 4) {// 修改选中的引用的详情
				if (data.getExtras().getSerializable("object").toString()
						.equals("")) {// 说明删除了
					list.remove(selectIndex);
					adapter.notifyDataSetChanged();
				} else {
					Map<String, Object> map = (Map<String, Object>) data
							.getExtras().getSerializable("object");
					list.remove(selectIndex);
					list.add(selectIndex, map);
					adapter.notifyDataSetChanged();
				}
				xzspnumTextView.setText("共选择了" + list.size() + "引用");
				double b=0;
	            for (Map<String, Object> map : list) {
	                b+=Double.parseDouble(
	                        map.get("bcjs") == null ? map.get("amount").toString()
	                                : map.get("bcjs").toString());
	            }
	            fkjeEditText.setText(b+"");
			} else if (requestCode == 5) {// 选中单据成功后返回
			// addScrollView.setVisibility(View.VISIBLE);//隐藏关联销售单据的Linearlayout
			// gldjcgLinearLayout.setVisibility(View.GONE);//显示展示详情的Linearlayout信息
			// billid = data.getExtras().getString("billid");
			// searchDate();//查询主表的数据填充
			} else if (requestCode == 6) {
				// rkckEditText.setText(data.getExtras().getString("dictmc"));
				// rkckId = data.getExtras().getString("id");
			} else if (requestCode == 7) {
				fklxEditText.setText(data.getExtras().getString("name"));
				fklxId = data.getExtras().getString("id");
				if (fklxId.equals("0")) {
					xzyydShowLayout.setVisibility(View.VISIBLE);
					fkjeEditText.setEnabled(false);
					double b=0;
		            for (Map<String, Object> map : list) {
		                b+=Double.parseDouble(
		                        map.get("bcjs") == null ? map.get("amount").toString()
		                                : map.get("bcjs").toString());
		            }
		            fkjeEditText.setText(b+"");
				} else if (fklxId.equals("1")) {
					xzyydShowLayout.setVisibility(View.GONE);
					fkjeEditText.setEnabled(true);
					fkjeEditText.setText("");
					list.clear();
                    adapter.notifyDataSetChanged();
				} else if (fklxId.equals("2")) {
					xzyydShowLayout.setVisibility(View.VISIBLE);
					fkjeEditText.setEnabled(true);
				}
			} else if (requestCode == 8) {
				jsfsEditText.setText(data.getExtras().getString("dictmc"));
				jsfsId = data.getExtras().getString("id");
			} else if (requestCode == 9) {
				zjzhEditText.setText(data.getExtras().getString("dictmc"));
				zjzhId = data.getExtras().getString("id");
			}else if(requestCode==10){
				searchDate();
			}
		}
	}
}