package com.cr.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.cr.model.GZTX;
import com.crcxj.activity.R;

public class GzptHjzxKhzlBfnrActivity extends BaseActivity {
	private TextView describeTextView;
	GZTX gztx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_hjzx_khzl_bfnr);
		describeTextView=(TextView) findViewById(R.id.describe_textview);
		String text=this.getIntent().getExtras().getString("describe");
		describeTextView.setText(text);
		addFHMethod();
	}
    @Override
    public void executeSuccess() {
    }
}
