package com.cr.activity.jxc.ckgl.kcbd;

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
import com.crcxj.activity.R;

/**
 * 进销存-仓库管理-库存变动-选择商品-选择的商品的详细信息
 * 
 * @author Administrator
 * 
 */
public class JxcCkglKcbdXzspDetail2Activity extends BaseActivity implements OnClickListener {
    TextView            mcTextView;
    TextView            bhTextView;
    TextView            ggTextView;
    TextView            xhTextView;
    TextView            kcTextView;
    TextView            scTextView;
    ImageButton         saveImageButton;
    EditText            dwEditText;
    SLView2             slView;
    LinearLayout cpphLayout;
    LinearLayout scrqLayout;
    LinearLayout yxqzLayout;
    View cpphView;
    View scrqView;
    View yxqzView;
    EditText            cpphEditText;
    EditText            scrqEditText;
    EditText            yxqzEditText;
    String storeid="";
    Map<String, Object> object = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_kcbd_xzsp_detail);
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
    	cpphLayout=(LinearLayout) findViewById(R.id.cpph_layout);
    	cpphView=findViewById(R.id.cpph_view);
    	scrqLayout=(LinearLayout) findViewById(R.id.scrq_layout);
    	scrqView=findViewById(R.id.scrq_view);
    	yxqzLayout=(LinearLayout) findViewById(R.id.yxqz_layout);
    	yxqzView=findViewById(R.id.yxqz_view);
    	cpphEditText = (EditText) findViewById(R.id.cpph_edittext);
//        cpphEditText.setOnClickListener(this);
        scrqEditText = (EditText) findViewById(R.id.scrq_edittext);
//        scrqEditText.setOnClickListener(this);
        yxqzEditText = (EditText) findViewById(R.id.yxqz_edittext);
//        yxqzEditText.setOnClickListener(this);
        mcTextView = (TextView) findViewById(R.id.mc_textview);
        bhTextView = (TextView) findViewById(R.id.bh_textview);
        ggTextView = (TextView) findViewById(R.id.gg_textview);
        xhTextView = (TextView) findViewById(R.id.xh_textview);
        kcTextView = (TextView) findViewById(R.id.kc_textview);
        scTextView = (TextView) findViewById(R.id.sc_textview);
        scTextView.setOnClickListener(this);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        dwEditText = (EditText) findViewById(R.id.dw_edittext);
        slView = (SLView2) findViewById(R.id.sl_view);
        slView.setEnabled(false);
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
            if(object.get("onhand")==null){
                kcTextView.setVisibility(View.GONE);
            }else{
                kcTextView.setText("库存：" + (int) Double.parseDouble(object.get("onhand").toString()));
            } 
            dwEditText.setText(object.get("unitname").toString());
            double sl=0;
            if (object.get("bdsl") != null) {
                sl=Double.parseDouble(object.get("bdsl").toString());
            } else {
                sl=Double.parseDouble(object.get("unitqty").toString());
            }
            slView.setSl((int)sl);
            cpphEditText.setText(object.get("batchcode").toString());
            scrqEditText.setText(object.get("produceddate").toString());
            yxqzEditText.setText(object.get("validdate").toString());
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
        scTextView.setVisibility(View.GONE);
        saveImageButton.setVisibility(View.GONE);
    }

    /**
     * 连接网络的操作(删除)
     */
    private void searchDate() {
        object = null;
        Intent intent = new Intent();
        intent.putExtra("object", "");
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        object.put("dw", dwEditText.getText().toString());
        object.put("bdsl", slView.getSl() + "");
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
            case R.id.sc_textview:
                searchDate();
                break;
            case R.id.save_imagebutton:
                searchDateSave();
                break;
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
//                cpphId = data.getExtras().getString("id");
            }
        }
    }
}