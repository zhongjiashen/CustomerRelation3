package com.cr.activity.khfw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdjlx2Activity;
import com.cr.activity.common.CommonXzdjztActivity;
import com.cr.activity.common.CommonXzshztActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.crcxj.activity.R;

/**
 * 客户服务-查询
 * @author Administrator
 *
 */
public class KhfwSearchActivity extends BaseActivity implements OnClickListener {
    EditText  qrEditText, zrEditText,djlxEditText,djbhEditText,djztEditText,dwmcEditText;
//    TextView  shTextView;
    ImageView cxImageView;
//    Spinner   shSpinner;
    private String djlxId="",djztId="";
//    EditText  shEditText;
//    String    shId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khfw_search);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
//        khEditText = (EditText) findViewById(R.id.kh_edittext);
        qrEditText = (EditText) findViewById(R.id.qr_edittext);
        qrEditText.setOnClickListener(this);
        zrEditText = (EditText) findViewById(R.id.zr_edittext);
        zrEditText.setOnClickListener(this);
        cxImageView = (ImageView) findViewById(R.id.cx_imageview);
        cxImageView.setOnClickListener(this);
        dwmcEditText=(EditText) findViewById(R.id.dwmc_edittext);
        djbhEditText=(EditText) findViewById(R.id.djbh_edittext);
        djlxEditText = (EditText) findViewById(R.id.djlx_edittext);
        djlxEditText.setOnClickListener(this);
        djztEditText = (EditText) findViewById(R.id.djzt_edittext);
        djztEditText.setOnClickListener(this);
        
        qrEditText.setText(this.getIntent().getExtras().getString("qr"));
        zrEditText.setText(this.getIntent().getExtras().getString("zr"));
        dwmcEditText.setText(this.getIntent().getExtras().getString("dwmc"));
        djbhEditText.setText(this.getIntent().getExtras().getString("djbh"));
        djlxEditText.setText(this.getIntent().getExtras().getString("djlx"));
        djlxId=this.getIntent().getExtras().getString("djlxid");
        djztEditText.setText(this.getIntent().getExtras().getString("djzt"));
        djztId=this.getIntent().getExtras().getString("djztid");
    }

    @Override
    public void executeSuccess() {
        //		xzjhList.clear();
        if (returnJson.equals("")) {
            showToastPromopt(2);
        } else {
            //			xzjhList.addAll(JHRW.parseJsonToObject2(returnJson));
            //		    List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
            //		    for(int i=0;i<5;i++){
            //		        Map<String, Object>map=new HashMap<String, Object>();
            //		        list.add(map);
            //		    }
            //		    adapter=new JxcGgglGgddAdapter(list,this);
            //	        listView.setAdapter(adapter);
            //	        adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View arg0) {
    	Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.sh_edittext:
                intent.setClass(activity, CommonXzshztActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.qr_edittext:
                date_init(qrEditText);
                break;
            case R.id.zr_edittext:
                date_init(zrEditText);
                break;
            case R.id.djlx_edittext:
                intent.putExtra("type","*SHWX");
            	intent.setClass(activity, CommonXzzdActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.djzt_edittext:
            	intent.setClass(activity, CommonXzdjztActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.cx_imageview:
                if(qrEditText.getText().toString().equals("")){
                    showToastPromopt("起始日期必须填写！");
                    return;
                }else if(zrEditText.getText().toString().equals("")){
                    showToastPromopt("终止日期必须填写！");
                    return;
                }
                intent.putExtra("qr",qrEditText.getText().toString());
                intent.putExtra("zr",zrEditText.getText().toString());
                intent.putExtra("dwmc",dwmcEditText.getText().toString());
                intent.putExtra("djbh",djbhEditText.getText().toString());
                intent.putExtra("djlx",djlxEditText.getText().toString());
                intent.putExtra("djlxid",djlxId);
                intent.putExtra("djzt",djztEditText.getText().toString());
                intent.putExtra("djztid",djztId);
//                if(shSpinner.getSelectedItem().equals("未审核")){
//                    intent.putExtra("sh","0");
//                }else if(shSpinner.getSelectedItem().equals("已审核")){
//                    intent.putExtra("sh","1");
//                }else if(shSpinner.getSelectedItem().equals("审核中")){
//                    intent.putExtra("sh","2");
//                }
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
    			djlxEditText.setText(data.getExtras().getString("dictmc"));
    			djlxId=data.getExtras().getString("id");
    		}else if(requestCode==1){
    			djztEditText.setText(data.getExtras().getString("name"));
    			djztId=data.getExtras().getString("id");
    		}
    	}
    }
}