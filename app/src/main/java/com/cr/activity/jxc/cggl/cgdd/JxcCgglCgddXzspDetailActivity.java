package com.cr.activity.jxc.cggl.cgdd;

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
import com.cr.activity.SLView2;
import com.cr.activity.common.CommonXzphActivity;
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;

/**
 * 进销存-采购管理-采购订单-选择商品-选择的商品的详细信息
 * 
 * @author Administrator
 * 
 */

public class JxcCgglCgddXzspDetailActivity extends BaseActivity implements OnClickListener {
    TextView            mcTextView;
    TextView            bhTextView;
    TextView            ggTextView;
    TextView            xhTextView;
    TextView            kcTextView;
    TextView            scTextView;
    ImageButton         saveImageButton;
    EditText            djEditText;
    EditText            zklEditText;
    EditText            zjEditText;
    SLView2             slView;
    EditText            cpphEditText;
    EditText            scrqEditText;
    EditText            yxqzEditText;
    EditText            dwEditText;
    String              cpphId;
    
    LinearLayout cpphLayout;
    LinearLayout scrqLayout;
    LinearLayout yxqzLayout;
    View cpphView;
    View scrqView;
    View yxqzView;


    LinearLayout llBz;
    EditText  etBz;
    Map<String, Object> object = new HashMap<String, Object>();
    String storeid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_cggl_cgdd_xzsp_detail);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    @SuppressWarnings("unchecked")
    private void initActivity() {
    	if(this.getIntent().hasExtra("rkckId")){
    		storeid=this.getIntent().getExtras().getString("rkckId");
    	}
    	llBz=(LinearLayout) findViewById(R.id.ll_bz);
    	etBz=(EditText) findViewById(R.id.et_bz);


    	cpphLayout=(LinearLayout) findViewById(R.id.cpph_layout);
    	cpphView=findViewById(R.id.cpph_view);
    	scrqLayout=(LinearLayout) findViewById(R.id.scrq_layout);
    	scrqView=findViewById(R.id.scrq_view);
    	yxqzLayout=(LinearLayout) findViewById(R.id.yxqz_layout);
    	yxqzView=findViewById(R.id.yxqz_view);
    	
    	
        mcTextView = (TextView) findViewById(R.id.mc_textview);
        bhTextView = (TextView) findViewById(R.id.bh_textview);
        ggTextView = (TextView) findViewById(R.id.gg_textview);
        xhTextView = (TextView) findViewById(R.id.xh_textview);
        kcTextView = (TextView) findViewById(R.id.kc_textview);
        scTextView = (TextView) findViewById(R.id.sc_textview);
        scTextView.setOnClickListener(this);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        djEditText = (EditText) findViewById(R.id.dj_edittext);
        zklEditText = (EditText) findViewById(R.id.zkl_edittext);
        cpphEditText = (EditText) findViewById(R.id.cpph_edittext);
        cpphEditText.setOnClickListener(this);
        scrqEditText = (EditText) findViewById(R.id.scrq_edittext);
        scrqEditText.setOnClickListener(this);
        yxqzEditText = (EditText) findViewById(R.id.yxqz_edittext);
        yxqzEditText.setOnClickListener(this);
        zjEditText = (EditText) findViewById(R.id.zj_edittext);
        dwEditText = (EditText) findViewById(R.id.dw_edittext);
        slView = (SLView2) findViewById(R.id.sl_view);
        if (this.getIntent().hasExtra("object")) {
            object = (Map<String, Object>) this.getIntent().getExtras().getSerializable("object");
            mcTextView.setText("名称："
                               + (null == object.get("name") ? object.get("goodsname").toString()
                                   : object.get("name").toString()));
            bhTextView.setText("编号："
                               + (null == object.get("code") ? object.get("goodscode").toString()
                                   : object.get("code").toString()));
            ggTextView.setText("规格：" + object.get("specs").toString());
            xhTextView.setText("型号：" + object.get("model").toString());
            if (object.get("onhand") == null) {
                kcTextView.setVisibility(View.GONE);
            } else {
                kcTextView.setText("库存："
                                   + (int) Double.parseDouble(object.get("onhand").toString())
                                   + object.get("unitname").toString());
            }
            if(getIntent().getBooleanExtra("xskd",false)){
                llBz.setVisibility(View.VISIBLE);
                etBz.setText(object.get("memo")==null?"":object.get("memo").toString());
            }else {
                llBz.setVisibility(View.GONE);
            }
            dwEditText.setText(object.get("unitname")==null?object.get("unit_name").toString():object.get("unitname").toString());
            djEditText.setText(object.get("unitprice").toString());
            zklEditText.setText(object.get("disc").toString());
            zjEditText.setText(object.get("amount").toString());
            cpphEditText.setText(object.get("batchcode").toString());
            scrqEditText.setText(object.get("produceddate").toString());
            yxqzEditText.setText(object.get("validdate").toString());
            slView.setSl((int) Double.parseDouble(object.get("unitqty").toString()));
            //            if(null != object.get("orderid")){
            //                saveImageButton.setVisibility(View.GONE);
            //                findViewById(R.id.sc_linearLayout).setVisibility(View.GONE);
            //            }
            
            if(object.get("batchctrl").toString().equals("T")){
            	cpphLayout.setVisibility(View.VISIBLE);
            	cpphView.setVisibility(View.VISIBLE);
            	scrqLayout.setVisibility(View.VISIBLE);
            	scrqView.setVisibility(View.VISIBLE);
            	yxqzLayout.setVisibility(View.VISIBLE);
            	yxqzView.setVisibility(View.VISIBLE);
            }else{
            	cpphLayout.setVisibility(View.GONE);
            	cpphView.setVisibility(View.GONE);
            	scrqLayout.setVisibility(View.GONE);
            	scrqView.setVisibility(View.GONE);
            	yxqzLayout.setVisibility(View.GONE);
            	yxqzView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 删除
     */
    private void searchDate() {
        object = null;
        Intent intent = new Intent();
        intent.putExtra("object", "");
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 保存
     */
    private void searchDateSave() {
        //        if (null == object.get("orderid")) {
        object.put("unitprice", FigureTools.sswrFigure(djEditText.getText().toString()));
        object.put("unitqty", slView.getSl() + "");
        object.put(
            "amount",
            FigureTools.sswrFigure(Double.parseDouble(djEditText.getText().toString())
                                   * slView.getSl() + ""));
        object.put("disc", zklEditText.getText().toString());
        object.put("batchcode", cpphEditText.getText().toString());
        object.put("produceddate", scrqEditText.getText().toString());
        object.put("validdate", yxqzEditText.getText().toString());
        object.put("memo", etBz.getText().toString());
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
                intent.putExtra("storied", storeid);
                intent.putExtra("goodsid", object.get("goodsid").toString());
                activity.startActivityForResult(intent, 0);
                break;
            case R.id.scrq_edittext:
                date_init(scrqEditText);
                break;
            case R.id.yxqz_edittext:
                date_init(yxqzEditText);
                break;
            case R.id.sc_textview:
                searchDate();
                break;
            case R.id.save_imagebutton:
                searchDateSave();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                cpphEditText.setText(data.getExtras().getString("name"));
                scrqEditText.setText(data.getExtras().getString("scrq"));
                yxqzEditText.setText(data.getExtras().getString("yxrq"));
                cpphId = data.getExtras().getString("id");
            }
        }
    }
}