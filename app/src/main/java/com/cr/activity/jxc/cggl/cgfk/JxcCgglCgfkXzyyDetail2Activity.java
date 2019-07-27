package com.cr.activity.jxc.cggl.cgfk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzphActivity;
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;

/**
 * 进销存-采购管理-采购付款-选择引用-选择的引用的详细信息
 * 
 * @author Administrator
 * 
 */
public class JxcCgglCgfkXzyyDetail2Activity extends BaseActivity implements OnClickListener {
    TextView            dwmcTextView;
    TextView            djmcTextView;
    TextView            djbhTextView;
    TextView            djrqTextView;
    TextView            zeTextView;
    TextView            yfTextView;
    TextView            wfTextView;
    TextView            scTextView;
    EditText            bcjsEditText;                          //本次结算
    ImageButton         saveImageButton;
    Map<String, Object> object = new HashMap<String, Object>();
    LinearLayout cpphLayout;
    LinearLayout scrqLayout;
    LinearLayout yxqzLayout;
    View cpphView;
    View scrqView;
    View yxqzView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_cggl_cgfk_xzyy_detail);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    @SuppressWarnings("unchecked")
    private void initActivity() {
        dwmcTextView = (TextView) findViewById(R.id.dwmc_textview);
        djmcTextView = (TextView) findViewById(R.id.djmc_textview);
        djbhTextView = (TextView) findViewById(R.id.djbh_textview);
        djrqTextView = (TextView) findViewById(R.id.djrq_textview);
        zeTextView = (TextView) findViewById(R.id.ze_textview);
        yfTextView = (TextView) findViewById(R.id.yf_textview);
        wfTextView = (TextView) findViewById(R.id.wf_textview);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        bcjsEditText = (EditText) findViewById(R.id.bcjs_edittext);
        scTextView = (TextView) findViewById(R.id.sc_textview);
        scTextView.setOnClickListener(this);
        saveImageButton.setVisibility(View.GONE);
        scTextView.setVisibility(View.GONE);
//        cpphLayout=(LinearLayout) findViewById(R.id.cpph_layout);
//    	cpphView=(View)findViewById(R.id.cpph_view);
//    	scrqLayout=(LinearLayout) findViewById(R.id.scrq_layout);
//    	scrqView=(View)findViewById(R.id.scrq_view);
//    	yxqzLayout=(LinearLayout) findViewById(R.id.yxqz_layout);
//    	yxqzView=(View)findViewById(R.id.yxqz_view);
        if (this.getIntent().hasExtra("object")) {
            object = (Map<String, Object>) this.getIntent().getExtras().getSerializable("object");
            dwmcTextView.setText("单位名称：" + object.get("cname").toString());
            if(object.get("billtypename")==null){
                djmcTextView.setVisibility(View.GONE);
            }else {
                djmcTextView.setVisibility(View.VISIBLE);
                djmcTextView.setText("单据名称：" + (object.get("billtypename") == null ? "" : object.get("billtypename").toString()));
            }
            djbhTextView.setText("单据编号：" + object.get("code").toString());
            djrqTextView.setText("单据日期：" + object.get("billdate").toString());
            zeTextView.setText("总额：￥" + FigureTools.sswrFigure(object.get("total").toString()));
            yfTextView.setText("已付：￥" + FigureTools.sswrFigure(object.get("totalrcvd").toString()));
            wfTextView.setText("未付：￥" + FigureTools.sswrFigure(object.get("shouldpayamt").toString()));
            bcjsEditText.setText((object.get("amount")==null?object.get("bcjs").toString():object.get("amount").toString()));
//            if(object.get("batchctrl")!=null&&object.get("batchctrl").toString().equals("T")){
//            	cpphLayout.setVisibility(View.VISIBLE);
//            	cpphView.setVisibility(View.VISIBLE);
//            	scrqLayout.setVisibility(View.VISIBLE);
//            	scrqView.setVisibility(View.VISIBLE);
//            	yxqzLayout.setVisibility(View.VISIBLE);
//            	yxqzView.setVisibility(View.VISIBLE);
//            }else{
//            	cpphLayout.setVisibility(View.GONE);
//            	cpphView.setVisibility(View.GONE);
//            	scrqLayout.setVisibility(View.GONE);
//            	scrqView.setVisibility(View.GONE);
//            	yxqzLayout.setVisibility(View.GONE);
//            	yxqzView.setVisibility(View.GONE);
//            }
        }
    }

    /**
     * 连接网络的操作(删除)
     */
    private void searchDate() {
        // if (null != object.get("orderid")) {
        // Map<String, Object> parmMap = new HashMap<String, Object>();
        // parmMap.put("dbname", ShareUserInfo.getDbName(context));
        // parmMap.put("tabname", "tb_porder");
        // parmMap.put("pkvalue", object.get("orderid").toString());
        // findServiceData2(0, ServerURL.BILLDELDETAIL, parmMap, false);
        // } else {
        object = null;
        Intent intent = new Intent();
        intent.putExtra("object", "");
        setResult(RESULT_OK, intent);
        finish();
        // }
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        // if (null == object.get("orderid")) {
        double bcjs=Double.parseDouble(bcjsEditText.getText().toString());
        double wfje=Double.parseDouble(object.get("shouldpayamt").toString());
//        if(bcjs<0||bcjs>wfje){
//            showToastPromopt("本次结算金额必须小于等于未付款金额！");
//            return;
//        }
        object.put("bcjs", bcjsEditText.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("object", (Serializable) object);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson.equals("")) {
                showToastPromopt("删除成功");
                setResult(RESULT_OK);
                finish();
            } else {
                showToastPromopt("删除失败" + returnJson.substring(5));
            }
        } else if (returnSuccessType == 1) {

        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.cpph_edittext:
                Intent intent = new Intent();
                intent.setClass(activity, CommonXzphActivity.class);
                intent.putExtra("goodsid", object.get("goodsid").toString());
                activity.startActivityForResult(intent, 0);
                break;
            case R.id.sc_textview:
                searchDate();
                break;
            case R.id.save_imagebutton:
                searchDateSave();
                break;
        }
    }
}