package com.cr.activity.khfw;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzsfActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.crcxj.activity.R;

/**
 * 客户服务-增加-编辑服务产品
 * 
 * @author Administrator
 * 
 */
public class KhfwAddBjfwcpActivity extends BaseActivity implements
		OnClickListener {
    private Button                    sdButton;
	private ImageButton saveImageButton;
	private EditText cpbhEditText, cpmcEditText, ggEditText, xhEditText,
			dwEditText, cdEditText, shcdEditText, slEditText, pjEditText,
			fwfyEditText, fwnrEditText, jjfaEditText, fwryEditText,
			sfbzqnEditText, fwjdEditText, fwztEditText, yyEditText,
			wcrqEditText, mydEditText, bzEditText, wxdwEditText;
	private String cpmcId = "", shcdId = "", fwryId = "", sfbzqnId = "",
			fwjdId="", fwztId="", mydId="",unitId="";
	private String jldwid = "";
	private Map<String, Object> map;
	private View wxdwView;
	private LinearLayout wxdwLayout;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_khfw_add_bjfwcp);
		addFHMethod();
		initActivity();
		// searchDate();
		map = (Map<String, Object>) this.getIntent().getExtras()
				.getSerializable("object");
		initData();// 填充数据
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
		saveImageButton.setOnClickListener(this);
		cpbhEditText = (EditText) findViewById(R.id.cpbh_edittext);
		cpmcEditText = (EditText) findViewById(R.id.cpmc_edittext);
		cpmcEditText.setOnClickListener(this);
		ggEditText = (EditText) findViewById(R.id.gg_edittext);
		xhEditText = (EditText) findViewById(R.id.xh_edittext);
		dwEditText = (EditText) findViewById(R.id.dw_edittext);
		cdEditText = (EditText) findViewById(R.id.cd_edittext);
		shcdEditText = (EditText) findViewById(R.id.shcd_edittext);
		shcdEditText.setOnClickListener(this);
		slEditText = (EditText) findViewById(R.id.sl_edittext);
		pjEditText = (EditText) findViewById(R.id.pj_edittext);
		fwfyEditText = (EditText) findViewById(R.id.fwfy_edittext);
		fwnrEditText = (EditText) findViewById(R.id.fwnr_edittext);
		jjfaEditText = (EditText) findViewById(R.id.jjfa_edittext);
		fwryEditText = (EditText) findViewById(R.id.fwry_edittext);
		fwryEditText.setOnClickListener(this);
		sfbzqnEditText = (EditText) findViewById(R.id.sfbzqn_edittext);
		sfbzqnEditText.setOnClickListener(this);
		fwjdEditText = (EditText) findViewById(R.id.fwjd_edittext);
		fwjdEditText.setOnClickListener(this);
		fwztEditText = (EditText) findViewById(R.id.fwzt_edittext);
		fwztEditText.setOnClickListener(this);
		yyEditText = (EditText) findViewById(R.id.yy_edittext);
		wcrqEditText = (EditText) findViewById(R.id.wcrq_edittext);
		wcrqEditText.setOnClickListener(this);
		mydEditText = (EditText) findViewById(R.id.myd_edittext);
		mydEditText.setOnClickListener(this);
		bzEditText = (EditText) findViewById(R.id.bzxx_edittext);
		wxdwEditText = (EditText) findViewById(R.id.wxdw_edittext);
		wxdwLayout = (LinearLayout) findViewById(R.id.wxdw_layout);
		wxdwView = findViewById(R.id.wxdw_view);
		sdButton = (Button) findViewById(R.id.sd_button);
        sdButton.setOnClickListener(this);
	}
	 /**
     * 连接网络的操作(删除)
     */
    private void searchDateDel() {
        Intent intent = new Intent();
        intent.putExtra("object", "");
        setResult(RESULT_OK, intent);
        finish();
        //        }
    }
	/**
	 * 将带来的数据填充到页面中
	 */
	private void initData() {
		String cpbh = map.get("bh") == null ? map.get("code").toString() : map
				.get("bh").toString();
		String cpmc = map.get("mc") == null ? map.get("goodsname").toString()
				: map.get("mc").toString();
		String gg = map.get("gg") == null ? map.get("specs").toString() : map
				.get("gg").toString();
		String xh = map.get("xh") == null ? map.get("model").toString() : map
				.get("xh").toString();
		String dw = map.get("dw") == null ? map.get("unitname").toString()
				: map.get("dw").toString();
		// String
		// cd=map.get("cd")==null?map.get("place").toString():map.get("cd").toString();
		String shcd = map.get("shcdname") == null ? map.get("shcdname").toString()
				: map.get("shcdname").toString();
		String sl = map.get("sl") == null ? map.get("unitqty").toString() : map
				.get("sl").toString();
		String pj = map.get("pj") == null ? map.get("pjxh").toString() : map
				.get("pj").toString();
		String fwfy = map.get("fwfy") == null ? map.get("amount").toString()
				: map.get("fwfy").toString();
		String fwnr = map.get("fwnr") == null ? map.get("gzms").toString()
				: map.get("fwnr").toString();
		String jjfa = map.get("jjfa") == null ? map.get("jjfa").toString()
				: map.get("jjfa").toString();
		String fwry = map.get("fwry") == null ? map.get("wxryname").toString()
				: map.get("fwry").toString();
		String sfbzqn = map.get("sfbzqn") == null ? (map.get("zbzt").toString()
				.equals("0") ? "否" : "是") : (map.get("sfbzqn").toString()
				.equals("0") ? "否" : "是");
		String fwjd = map.get("fwjd") == null ? map.get("wxjgname").toString()
				: map.get("fwjd").toString();
		String fwzt = map.get("fwzt") == null ? map.get("wxjdname").toString()
				: map.get("fwzt").toString();
		String yy = map.get("yy") == null ? map.get("fyyy").toString() : map
				.get("yy").toString();
		String wcrq = map.get("wcrq") == null ? map.get("wcrq").toString()
				: map.get("wcrq").toString();
		String myd = map.get("myd") == null ? map.get("mycdname").toString()
				: map.get("myd").toString();
		String bz = map.get("bz") == null ? map.get("memo").toString() : map
				.get("bz").toString();
		String wxdw = map.get("wxdw").toString();
		cpmcId = map.get("cpmcid") == null ? map.get("goodsid").toString()
				: map.get("cpmcid").toString();
		shcdId = map.get("shcdid") == null ? map.get("shcd").toString() : map
				.get("shcdid").toString();
		fwryId = map.get("fwryid") == null ? map.get("wxry").toString() : map
				.get("fwryid").toString();
		sfbzqnId = map.get("sfbzqnid") == null ? map.get("zbzt").toString()
				: map.get("sfbzqnid").toString();
		fwjdId = map.get("fwjdid") == null ? map.get("wxjg").toString() : map
				.get("fwjdid").toString();
		fwztId = map.get("fwztid") == null ? map.get("wxjd").toString() : map
				.get("fwztid").toString();
		mydId = map.get("mydid") == null ? map.get("mycd").toString():map
				.get("mydid").toString();
		unitId=map.get("unitid") == null ? "" : map
            .get("unitid").toString();
		cpbhEditText.setText(cpbh);
		cpmcEditText.setText(cpmc);
		ggEditText.setText(gg);
		xhEditText.setText(xh);
		dwEditText.setText(dw);
		// cdEditText.setText(cd);
		shcdEditText.setText(shcd);
		slEditText.setText(sl);
		pjEditText.setText(pj);
		fwfyEditText.setText(fwfy);
		fwnrEditText.setText(fwnr);
		jjfaEditText.setText(jjfa);
		fwryEditText.setText(fwry);
		wxdwEditText.setText(wxdw);
		if (fwryId.equals("0")) {
			wxdwLayout.setVisibility(View.VISIBLE);
			wxdwView.setVisibility(View.VISIBLE);
		} else {
			wxdwLayout.setVisibility(View.GONE);
			wxdwView.setVisibility(View.GONE);
		}
		sfbzqnEditText.setText(sfbzqn);
		fwjdEditText.setText(fwjd);
		fwztEditText.setText(fwzt);
		yyEditText.setText(yy);
		wcrqEditText.setText(wcrq);
		mydEditText.setText(myd);
		bzEditText.setText(bz);

	}

	/**
	 * 连接网络的操作(保存)
	 */
	private void searchDateSave() {
		if (fwryId.equals("0")) {
			if (wxdwEditText.getText().toString().equals("")) {
				showToastPromopt("外修单位不能为空！");
				return;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bh", cpbhEditText.getText().toString());
		map.put("mc", cpmcEditText.getText().toString());
		map.put("gg", ggEditText.getText().toString());
		map.put("xh", xhEditText.getText().toString());
		map.put("dw", dwEditText.getText().toString());
		map.put("cd", cdEditText.getText().toString());
		map.put("shcdname", shcdEditText.getText().toString());
		map.put("sl", slEditText.getText().toString());
		map.put("pj", pjEditText.getText().toString());
		map.put("fwfy", fwfyEditText.getText().toString());
		map.put("fwnr", fwnrEditText.getText().toString());
		map.put("jjfa", jjfaEditText.getText().toString());
		map.put("fwry", fwryEditText.getText().toString());
		map.put("sfbzqn", sfbzqnEditText.getText().toString());
		map.put("yy", yyEditText.getText().toString());
		map.put("wcrq", wcrqEditText.getText().toString());
		map.put("bz", bzEditText.getText().toString());
		map.put("jldwid", jldwid);
		map.put("shcdid", shcdId);
		map.put("fwryid", fwryId);
		map.put("sfbzqnid", sfbzqnId);
		map.put("cpmcid", cpmcId);
		map.put("fwjdid", fwjdId);
		map.put("fwztid", fwztId);
		map.put("mydid", mydId);
		map.put("wxdw", wxdwEditText.getText().toString());
		map.put("fwjd", fwjdEditText.getText().toString());
		if(fwjdId.equals("")){
			map.put("fwzt","");
		}else{
			map.put("fwzt", fwjdId.equals("1")?"服务中":"服务完成");
		}
		map.put("myd", mydEditText.getText().toString());
		map.put("unitid", unitId);
		Intent intent = new Intent();
		intent.putExtra("object", (Serializable) map);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void executeSuccess() {

	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.save_imagebutton:
			searchDateSave();// 保存
			break;
		case R.id.cpmc_edittext:
			intent.setClass(activity, KhfwXzspActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.shcd_edittext:
			intent.setClass(activity, CommonXzzdActivity.class);
			intent.putExtra("type", "SHCD");
			startActivityForResult(intent, 1);
			break;
		case R.id.fwry_edittext:
			intent.setClass(activity, CommonXzzdActivity.class);
			intent.putExtra("type", "WXRY");
			startActivityForResult(intent, 2);
			break;
		case R.id.sfbzqn_edittext:
			intent.setClass(activity, CommonXzsfActivity.class);
			startActivityForResult(intent, 3);
			break;
		case R.id.fwjd_edittext:
			intent.setClass(activity, CommonXzzdActivity.class);
			intent.putExtra("type", "WXJG");
			startActivityForResult(intent, 4);
			break;
		case R.id.fwzt_edittext:
			intent.setClass(activity, CommonXzzdActivity.class);
			intent.putExtra("type", "WXJD");
			startActivityForResult(intent, 5);
			break;
		case R.id.wcrq_edittext:
			date_init(wcrqEditText);
			break;
		case R.id.myd_edittext:
			intent.setClass(activity, CommonXzzdActivity.class);
			intent.putExtra("type", "MYCD");
			startActivityForResult(intent, 6);
			break;
		case R.id.sd_button:
		    new AlertDialog.Builder(activity)
			.setTitle("确定要删除当前记录吗？")
			.setNegativeButton("删除",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0,
								int arg1) {
							searchDateDel();
						}
					}).setPositiveButton("取消", null).show();
		    break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {// 选择商品
				cpmcEditText.setText(data.getExtras().getString("mc"));
				cpmcId = data.getExtras().getString("id");
				cpbhEditText.setText(data.getExtras().getString("bh"));
				ggEditText.setText(data.getExtras().getString("gg"));
				xhEditText.setText(data.getExtras().getString("xh"));
				dwEditText.setText(data.getExtras().getString("dw"));
				cdEditText.setText(data.getExtras().getString("cd"));
				jldwid = data.getExtras().getString("jldwid");
				unitId = data.getExtras().getString("unitid");
			} else if (requestCode == 1) {// 损坏程度
				shcdEditText.setText(data.getExtras().getString("dictmc"));
				shcdId = data.getExtras().getString("id");
			} else if (requestCode == 2) {// 服务人员
				fwryEditText.setText(data.getExtras().getString("dictmc"));
				fwryId = data.getExtras().getString("id");
				if (fwryId.equals("0")) {
					wxdwLayout.setVisibility(View.VISIBLE);
					wxdwView.setVisibility(View.VISIBLE);
				} else {
					wxdwLayout.setVisibility(View.GONE);
					wxdwView.setVisibility(View.GONE);
				}
			} else if (requestCode == 3) {// 是否保质期内
				sfbzqnEditText.setText(data.getExtras().getString("name"));
				sfbzqnId = data.getExtras().getString("id");
			} else if (requestCode == 4) {// 服务 进度
				fwjdEditText.setText(data.getExtras().getString("dictmc"));
				fwjdId = data.getExtras().getString("id");
			} else if (requestCode == 5) {// 服务状态
				fwztEditText.setText(data.getExtras().getString("dictmc"));
				fwztId = data.getExtras().getString("id");
			} else if (requestCode == 6) {// 满意程度
				mydEditText.setText(data.getExtras().getString("dictmc"));
				mydId = data.getExtras().getString("id");
			}
		}
	}
}