package com.cr.activity.gzpt.dwzl;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzjhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.sales.AddSalesOpportunitiesActivity;

/**
 * 客户资料-拜访-拜访录入
 * @author Administrator
 *
 */
public class GzptDwzlBfBflrActivity extends BaseActivity implements OnClickListener {

    EditText         gsmcEditText, lxrEditText, xsjhEditText, dqjdEditText, bffsEditText,
            yysjEditText, bfnrEditText;
    private CheckBox yyCheckBox;
    private String   lxrId, xsjhId, dqjdId, bffsId;
    ImageButton      saveButton;
    ImageView        xzxsjhImageView;
    String           clientId, clientName;
    Map<String, Object> object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_dwzl_bf_bflr);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    @SuppressWarnings("unchecked")
	private void initActivity() {
        if (this.getIntent().hasExtra("clientid")) {
            clientId = this.getIntent().getExtras().getString("clientid");
            clientName = this.getIntent().getExtras().getString("clientname");
        }
        object=((Map<String, Object>) this.getIntent().getExtras().getSerializable("object"));
        saveButton = (ImageButton) findViewById(R.id.save);
        saveButton.setOnClickListener(this);
        xzxsjhImageView = (ImageView) findViewById(R.id.addXsjh);
        xzxsjhImageView.setOnClickListener(this);
        yyCheckBox = (CheckBox) findViewById(R.id.yy_checkbox);
        gsmcEditText = (EditText) findViewById(R.id.gsmc_edittext);
        gsmcEditText.setText(clientName);
        lxrEditText = (EditText) findViewById(R.id.lxr_edittext);
        lxrEditText.setOnClickListener(this);
        xsjhEditText = (EditText) findViewById(R.id.xsjh_edittext);
        xsjhEditText.setOnClickListener(this);
        dqjdEditText = (EditText) findViewById(R.id.dqjd_edittext);
        dqjdEditText.setOnClickListener(this);
        bffsEditText = (EditText) findViewById(R.id.bffs_edittext);
        bffsEditText.setOnClickListener(this);
        yysjEditText = (EditText) findViewById(R.id.yysj_edittext);
        yysjEditText.setOnClickListener(this);
        bfnrEditText = (EditText) findViewById(R.id.bfnr_edittext);
    }

    /**
     * 连接网络的操作(拜访录入保存)
     */
    private void saveBflrDate() {
    	Map<String, Object> parmMap = new HashMap<String, Object>();
        String lb="";
        if(ShareUserInfo.getKey(mContext, "khzlname").equals("hjzx")){
            lb="1";
            parmMap.put("referid", object.get("id").toString());
        }else if(ShareUserInfo.getKey(mContext, "khzlname").equals("yybf")){
            lb="2";
            parmMap.put("referid", object.get("id").toString());
        }else if(ShareUserInfo.getKey(mContext, "khzlname").equals("shhf")){
            lb="3";
            parmMap.put("referid", object.get("id").toString());
        }else if(ShareUserInfo.getKey(mContext, "khzlname").equals("xzld")){
            lb="4";
            parmMap.put("referid", clientId);
        }else if(ShareUserInfo.getKey(mContext, "khzlname").equals("khgl")){
           lb="5" ;
           parmMap.put("referid", clientId);
        }
        //		Log.v("dddd", this.getIntent().getExtras().getString("jhbid"));
        parmMap.put("lb", lb);
        
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("opid", ShareUserInfo.getUserId(mContext));
        parmMap.put("id", "0");
        parmMap.put("clientid", clientId);
        parmMap.put("lxrid", lxrId);
        parmMap.put("chanceid", xsjhId);//机会
        parmMap.put("title", xsjhEditText.getText().toString());//机会名称
//        parmMap.put("rcid", "1");
        parmMap.put("bflx", bffsId);
        parmMap.put("gmid", dqjdId);
//        parmMap.put("isnewcjd", "");//是否是新成交
        parmMap.put("isyy", yyCheckBox.isChecked() ? "1" : "0");//是否预约
        parmMap.put("nextdate", yysjEditText.getText().toString());//预约日期
        parmMap.put("memo", bfnrEditText.getText().toString());//
        findServiceData2(3, ServerURL.JHRWRECORD, parmMap, false);
    }

    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 3:
                if (returnJson.equals("")) {
                    showToastPromopt("保存成功！");
                    setResult(RESULT_OK);
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
                    saveBflrDate();
                }
                break;
            case R.id.addXsjh://新增销售机会
            	 intent.setClass(this,AddSalesOpportunitiesActivity.class);
                 // intent.putExtra("lxrid", "0");
                 intent.putExtra("clientid", clientId);
                 intent.putExtra("clientname", clientName);
                 startActivityForResult(intent, 4);
                break;
            case R.id.lxr_edittext://选择联系人
                intent.setClass(this, CommonXzlxrActivity.class);
                intent.putExtra("clientid", clientId);
                startActivityForResult(intent, 0);
                break;
            case R.id.xsjh_edittext://选择销售机会
                intent.setClass(this, CommonXzjhActivity.class);
                intent.putExtra("clientid", clientId);
                startActivityForResult(intent, 1);
                break;
            case R.id.dqjd_edittext://选择当前进度
                intent.setClass(this, CommonXzzdActivity.class);
                intent.putExtra("type", "XMGM");
                startActivityForResult(intent, 2);
                break;
            case R.id.bffs_edittext://选择拜访方式
                intent.setClass(this, CommonXzzdActivity.class);
                intent.putExtra("type", "BFLX");
                startActivityForResult(intent, 3);
                break;
            case R.id.yysj_edittext://选择预约时间
                time_init(yysjEditText);
                date_init(yysjEditText);
                break;
            default:
                break;
        }
    }

    /**
     * 验证用户输入的信息
     * @return
     */
    private boolean validateMsg() {
        if (bfnrEditText.getText().toString().equals("")) {
            showToastPromopt("拜访内容不能为空");
            return false;
        }
        if (yyCheckBox.isChecked()) {
            if (yysjEditText.getText().toString().equals("")) {
                showToastPromopt("预约时间不能为空");
                return false;
            }
        }
        if(bffsEditText.getText().toString().equals("")){
            showToastPromopt("请选择拜访方式！");
            return false;
        }else if(dqjdEditText.getText().toString().equals("")){
            showToastPromopt("请选择当前进度！");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                lxrEditText.setText(data.getExtras().getString("name"));
                lxrId = data.getExtras().getString("id");
                
            } else if (requestCode == 1) {
                xsjhEditText.setText(data.getExtras().getString("name"));
                xsjhId = data.getExtras().getString("id");
            } else if (requestCode == 2) {
                dqjdId = data.getExtras().getString("id");
                dqjdEditText.setText(data.getExtras().getString("dictmc"));
            } else if (requestCode == 3) {
                bffsId = data.getExtras().getString("id");
                bffsEditText.setText(data.getExtras().getString("dictmc"));
            }else if(requestCode==4){
            	xsjhId=data.getExtras().getString("id");
            	xsjhEditText.setText(data.getExtras().getString("name"));
            }
        }
    }
    
    
}