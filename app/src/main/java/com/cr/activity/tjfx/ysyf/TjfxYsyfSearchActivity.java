package com.cr.activity.tjfx.ysyf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.crcxj.activity.R;

/**
 * 统计分析-应收应付-查询
 *
 * @author Administrator
 */
public class TjfxYsyfSearchActivity extends BaseActivity implements OnClickListener {
    EditText lxEditText, mcEditText;
    ImageView cxImageView;
    String lxid, name, qkflag;
    CheckBox cbXswqk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjfx_ysyf_search);
        addFHMethod();
        initActivity();

    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        lxEditText = (EditText) findViewById(R.id.lx_edittext);
        lxEditText.setOnClickListener(this);
        mcEditText = (EditText) findViewById(R.id.mc_edittext);
        cxImageView = (ImageView) findViewById(R.id.cx_imageview);
        cbXswqk = findViewById(R.id.cb_xswqk);
        cxImageView.setOnClickListener(this);
        lxEditText.setText(this.getIntent().getExtras().getString("lxname"));
        mcEditText.setText(this.getIntent().getExtras().getString("mc"));
        lxid = this.getIntent().getExtras().getString("lxid");
        qkflag = this.getIntent().getExtras().getString("qkflag");
        cbXswqk.setChecked(qkflag.equals("0"));
        cbXswqk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    qkflag = "0";
                else
                    qkflag = "1";
            }
        });


    }


    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.lx_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "*KHLB1");
                startActivityForResult(intent, 0);
                break;
            case R.id.cx_imageview:
                intent.putExtra("lxid", lxid);
                intent.putExtra("lxname", lxEditText.getText().toString());
                intent.putExtra("mc", mcEditText.getText().toString());
                intent.putExtra("qkflag", qkflag);
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
                lxEditText.setText(data.getExtras().getString("dictmc"));
                lxid = data.getExtras().getString("id");
            }
        }
    }

    @Override
    public void executeSuccess() {

    }
}