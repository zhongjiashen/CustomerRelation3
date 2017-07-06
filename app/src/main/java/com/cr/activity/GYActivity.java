package com.cr.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.crcxj.activity.R;

/**
 * 关于页面
 * @author Administrator
 *
 */
public class GYActivity extends BaseActivity implements OnClickListener{
	private ImageButton fhButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gy);
        initActivity();
    }
    /**
	 * 初始化Activity
	 */
	private void initActivity(){
		
		fhButton=(ImageButton) findViewById(R.id.fh);
		fhButton.setOnClickListener(this);
	}
	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.fh:
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	public void executeSuccess() {
		// TODO Auto-generated method stub
		
	}
	
	
}
