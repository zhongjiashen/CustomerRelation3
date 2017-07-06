package com.cr.activity.tjfx.kcbb;

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
 * 统计分析-库存报表-查询
 * @author Administrator
 *
 */
public class TjfxKcbbSearchActivity extends BaseActivity implements OnClickListener {
    EditText  ckEditText, spEditText;
    ImageView cxImageView;
    String storeid,storename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjfx_kcbb_search);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        ckEditText = (EditText) findViewById(R.id.ck_edittext);
        ckEditText.setOnClickListener(this);
        spEditText = (EditText) findViewById(R.id.sp_edittext);
        cxImageView = (ImageView) findViewById(R.id.cx_imageview);
        cxImageView.setOnClickListener(this);
        spEditText.setText(this.getIntent().getExtras().getString("goodsname"));
        ckEditText.setText(this.getIntent().getExtras().getString("storename"));
        storeid=this.getIntent().getExtras().getString("storeid");
    }


    @Override
    public void onClick(View arg0) {
    	Intent intent=new Intent();
        switch (arg0.getId()) {
            case R.id.ck_edittext:
            	intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "*STORE");
                startActivityForResult(intent, 0);
                break;
            case R.id.cx_imageview:
                intent.putExtra("storeid",storeid);
                intent.putExtra("storename",ckEditText.getText().toString());
                intent.putExtra("goodsname",spEditText.getText().toString());
                setResult(RESULT_OK, intent);
                setInputManager(false);
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode==RESULT_OK){
    		if(requestCode==0){
    			ckEditText.setText(data.getExtras().getString("dictmc"));
    			storeid = data.getExtras().getString("id");
    		}
    	}
    }

	@Override
	public void executeSuccess() {
		
	}
}