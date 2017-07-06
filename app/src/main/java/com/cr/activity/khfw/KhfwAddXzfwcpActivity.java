package com.cr.activity.khfw;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzsfActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.crcxj.activity.R;

/**
 * 客户服务-增加-新增服务产品
 * 
 * @author Administrator
 * 
 */
public class KhfwAddXzfwcpActivity extends BaseActivity implements OnClickListener {

    private ImageButton saveImageButton;
    private EditText    cpbhEditText, cpmcEditText, ggEditText, xhEditText, dwEditText, cdEditText,
            shcdEditText, slEditText, pjEditText, fwfyEditText, fwnrEditText, jjfaEditText,
            fwryEditText, sfbzqnEditText,wxdwEditText;
    private String cpmcId="",shcdId="",fwryId="",sfbzqnId="";
    private String jldwid="",unitId="";
    private View wxdwView;
    private LinearLayout wxdwLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khfw_add_xzfwcp);
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
        slEditText.setText("1");
        pjEditText = (EditText) findViewById(R.id.pj_edittext);
        fwfyEditText = (EditText) findViewById(R.id.fwfy_edittext);
        fwfyEditText.setText("0.00");
        fwnrEditText = (EditText) findViewById(R.id.fwnr_edittext);
        jjfaEditText = (EditText) findViewById(R.id.jjfa_edittext);
        fwryEditText = (EditText) findViewById(R.id.fwry_edittext);
        fwryEditText.setOnClickListener(this);
        sfbzqnEditText = (EditText) findViewById(R.id.sfbzqn_edittext);
        sfbzqnEditText.setOnClickListener(this);
        wxdwEditText=(EditText) findViewById(R.id.wxdw_edittext);
        wxdwLayout=(LinearLayout) findViewById(R.id.wxdw_layout);
        wxdwView=findViewById(R.id.wxdw_view);
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
    	if(fwryId.equals("0")){
    		if(wxdwEditText.getText().toString().equals("")){
    			showToastPromopt("外修单位不能为空！");
    			return;
    		}
    	}
        Map<String, Object> map=new HashMap<String, Object>();
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
        map.put("wxdw", wxdwEditText.getText().toString());
        map.put("yy", "");
        map.put("wcrq", "");
        map.put("bz", "");
        map.put("jldwid", jldwid);
        map.put("shcdid", shcdId);
        map.put("fwryid", fwryId);
        map.put("sfbzqnid", sfbzqnId);
        map.put("cpmcid", cpmcId);
        map.put("fwjdid","");
        map.put("fwztid","");
        map.put("mydid","");
        map.put("fwjd","");
        map.put("fwzt","");
        map.put("myd","");
        map.put("unitid", unitId);
//        Log.v("dddd", "unitid:"+unitId);
        Intent intent=new Intent();
        intent.putExtra("object", (Serializable)map);
        setResult(RESULT_OK,intent);
        finish();
    }

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
        } else if (returnSuccessType == 1) {//管理单据成功后把信息填到里面（主表）

        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.save_imagebutton:
                searchDateSave();//保存
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
                jldwid=data.getExtras().getString("jldwid");
                unitId = data.getExtras().getString("unitid");
            } else if (requestCode == 1) {//损坏程度
                shcdEditText.setText(data.getExtras().getString("dictmc"));
                shcdId = data.getExtras().getString("id");
            } else if (requestCode == 2) {// 服务人员
                fwryEditText.setText(data.getExtras().getString("dictmc"));
                fwryId = data.getExtras().getString("id");
                if(fwryId.equals("0")){
                	wxdwLayout.setVisibility(View.VISIBLE);
                	wxdwView.setVisibility(View.VISIBLE);
                }else{
                	wxdwLayout.setVisibility(View.GONE);
                	wxdwView.setVisibility(View.GONE);
                }
            } else if (requestCode == 3) {// 是否保质期内
                sfbzqnEditText.setText(data.getExtras().getString("name"));
                sfbzqnId = data.getExtras().getString("id");
            }
        }
    }
}