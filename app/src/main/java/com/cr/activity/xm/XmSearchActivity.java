package com.cr.activity.xm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.cr.activity.BaseActivity;
import com.crcxj.activity.R;

/**
 * 项目-查询
 * @author Administrator
 *
 */
public class XmSearchActivity extends BaseActivity implements OnClickListener {
    EditText  dwmcEditText, xmmcEditText;
    EditText  qrEditText, zrEditText;
    ImageView cxImageView;
    String qsrq,jzrq,dwmc,xmmc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xm_search);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        dwmcEditText = (EditText) findViewById(R.id.dwmc_edittext);
        xmmcEditText = (EditText) findViewById(R.id.xmmc_edittext);
        qrEditText = (EditText) findViewById(R.id.qr_edittext);
        qrEditText.setOnClickListener(this);
        zrEditText = (EditText) findViewById(R.id.zr_edittext);
        zrEditText.setOnClickListener(this);
        cxImageView = (ImageView) findViewById(R.id.cx_imageview);
        cxImageView.setOnClickListener(this);
        
        qrEditText.setText(this.getIntent().getExtras().getString("qsrq"));
        zrEditText.setText(this.getIntent().getExtras().getString("jzrq"));
        dwmcEditText.setText(this.getIntent().getExtras().getString("dwmc"));
        xmmcEditText.setText(this.getIntent().getExtras().getString("xmmc"));
    }


    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.qr_edittext:
                date_init(qrEditText);
                break;
            case R.id.zr_edittext:
                date_init(zrEditText);
                break;
            case R.id.cx_imageview:
                if(qrEditText.getText().toString().equals("")){
                    showToastPromopt("起始日期必须填写！");
                    return;
                }else if(zrEditText.getText().toString().equals("")){
                    showToastPromopt("终止日期必须填写！");
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("qsrq",qrEditText.getText().toString());
                intent.putExtra("jzrq",zrEditText.getText().toString());
                intent.putExtra("dwmc",dwmcEditText.getText().toString());
                intent.putExtra("xmmc",xmmcEditText.getText().toString());
                setResult(RESULT_OK, intent);
                setInputManager(false);
                finish();
                break;
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    	// TODO Auto-generated method stub
//    	super.onActivityResult(requestCode, resultCode, data);
//    	if(resultCode==RESULT_OK){
//    		if(requestCode==0){
//    			ckEditText.setText(data.getExtras().getString("dictmc"));
//    			storeid = data.getExtras().getString("id");
//    		}
//    	}
//    }

	@Override
	public void executeSuccess() {
		
	}
}