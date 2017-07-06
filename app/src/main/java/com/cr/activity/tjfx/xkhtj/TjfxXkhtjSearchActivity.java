package com.cr.activity.tjfx.xkhtj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzywyActivity;
import com.crcxj.activity.R;

/**
 * 统计分析-新客户统计-查询
 * 
 * @author Administrator
 * 
 */
public class TjfxXkhtjSearchActivity extends BaseActivity implements
		OnClickListener {
	EditText qrEditText, zrEditText, ywyEditText;
	ImageView cxImageView;
	String ywyid;
	TextView titleTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tjfx_khbftj_search);
		addFHMethod();
		initActivity();
	}

	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		qrEditText = (EditText) findViewById(R.id.qr_edittext);
		qrEditText.setOnClickListener(this);
		zrEditText = (EditText) findViewById(R.id.zr_edittext);
		zrEditText.setOnClickListener(this);
		ywyEditText = (EditText) findViewById(R.id.ywy_edittext);
		ywyEditText.setOnClickListener(this);
		cxImageView = (ImageView) findViewById(R.id.cx_imageview);
		cxImageView.setOnClickListener(this);

		qrEditText.setText(this.getIntent().getExtras().getString("qr"));
		zrEditText.setText(this.getIntent().getExtras().getString("zr"));
		ywyEditText.setText(this.getIntent().getExtras().getString("ywy"));
		ywyid = this.getIntent().getExtras().getString("ywyid");
		titleTextView=(TextView) findViewById(R.id.title_textview);
		titleTextView.setText("新客户统计");		
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
		case R.id.qr_edittext:
			date_init(qrEditText);
			break;
		case R.id.zr_edittext:
			date_init(zrEditText);
			break;
		case R.id.ywy_edittext:
			intent.setClass(activity, CommonXzywyActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.cx_imageview:
			intent.putExtra("ywyid", ywyid);
			intent.putExtra("ywy",ywyEditText.getText().toString());
			intent.putExtra("qr", qrEditText.getText().toString());
			intent.putExtra("zr", zrEditText.getText().toString());
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
		if (resultCode == RESULT_OK) {
			if (requestCode == 0) {
				ywyEditText.setText(data.getExtras().getString("name"));
				ywyid = data.getExtras().getString("id");
			}
		}
	}

	@Override
	public void executeSuccess() {

	}
}