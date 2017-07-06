package com.cr.activity.xjyh.qtsr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.crcxj.activity.R;

/**
 * 现金银行-其他收入-添加支出项
 * 
 * @author Administrator
 * 
 */
public class XjyhQtsrAddZcActivity extends BaseActivity implements OnClickListener {
    private EditText fymcEditText,jeEditText;
    private ImageView saveImageView;
    private String fymcId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjyh_qtsr_add_zc);
        initActivity();
        addFHMethod();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        fymcEditText=(EditText) findViewById(R.id.fymc_edittext);
        fymcEditText.setOnClickListener(this);
        jeEditText=(EditText) findViewById(R.id.je_edittext);
        saveImageView=(ImageView) findViewById(R.id.save);
        saveImageView.setOnClickListener(this);
    }

    /**
     * 监听事件
     */
    @Override
    public void onClick(View view) {
    	Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.save:
            	if(fymcEditText.getText().toString().equals("")){
            		showToastPromopt("请选择费用名称");
            		return;
            	}
            	if(jeEditText.getText().toString().equals("")){
            		showToastPromopt("请填写金额信息");
            		return;
            	}
                intent.putExtra("name", fymcEditText.getText().toString());
                intent.putExtra("ietypeid", fymcId);
                intent.putExtra("amount", jeEditText.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.fymc_edittext:
            	intent.putExtra("type", "SRLB");
            	intent.setClass(activity, CommonXzzdActivity.class);
            	startActivityForResult(intent, 0);
            	break;
            default:
                break;
        }
    }

    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 0:
                if(returnJson.equals("")){
                    showToastPromopt("操作成功");
                    setResult(RESULT_OK);
                    finish();
                }else {
                    showToastPromopt("保存失败"+returnJson.substring(5));
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode==RESULT_OK){
    		if(requestCode==0){
    			fymcId=data.getExtras().getString("id");
    			fymcEditText.setText(data.getExtras().getString("dictmc"));
    		}
    	}
    }
}