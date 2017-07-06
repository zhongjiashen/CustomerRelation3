package com.cr.activity.xjyh.fkd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzshztActivity;
import com.crcxj.activity.R;

/**
 * 现金银行-付款单-查询
 * @author Administrator
 *
 */
public class XjyhFkdSearchActivity extends BaseActivity implements OnClickListener {
    EditText  khEditText, qrEditText, zrEditText;
    TextView  shTextView;
    ImageView cxImageView;
    EditText  shEditText;
    String    shId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjyh_fkd_search);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        khEditText = (EditText) findViewById(R.id.kh_edittext);
        qrEditText = (EditText) findViewById(R.id.qr_edittext);
        qrEditText.setOnClickListener(this);
        zrEditText = (EditText) findViewById(R.id.zr_edittext);
        zrEditText.setOnClickListener(this);
        cxImageView = (ImageView) findViewById(R.id.cx_imageview);
        cxImageView.setOnClickListener(this);
        
        qrEditText.setText(this.getIntent().getExtras().getString("qr"));
        zrEditText.setText(this.getIntent().getExtras().getString("zr"));
        khEditText.setText(this.getIntent().getExtras().getString("kh"));
        shEditText = (EditText) findViewById(R.id.sh_edittext);
        shEditText.setOnClickListener(this);
        if(this.getIntent().getExtras().getString("sh").equals("0")){
            shId="0";
            shEditText.setText("未审核");
        }else if(this.getIntent().getExtras().getString("sh").equals("1")){
            shId="1";
            shEditText.setText("已审核");
        }else if(this.getIntent().getExtras().getString("sh").equals("2")){
            shId="2";
            shEditText.setText("审核中");
        }
    }

    /**
     * 连接网络的操作
     */
    //	private void searchDate(){
    //		Map<String, Object> parmMap=new HashMap<String, Object>();
    //		parmMap.put("dbname", ShareUserInfo.getDbName(context));
    //		parmMap.put("opid", ShareUserInfo.getUserId(context));
    //		parmMap.put("curpage", currentPage);
    //		parmMap.put("pagesize", pageSize);
    //		findServiceData2(0,ServerURL.JHRWGZZJRZJ,parmMap,false);
    //	}
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
        Intent intent=new Intent();
        switch (arg0.getId()) {
            case R.id.sh_edittext:
                intent.setClass(activity, CommonXzshztActivity.class);
                startActivityForResult(intent, 0);
                break;
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
                intent.putExtra("qr",qrEditText.getText().toString());
                intent.putExtra("zr",zrEditText.getText().toString());
                intent.putExtra("kh",khEditText.getText().toString());
                intent.putExtra("sh", shId);
                setResult(RESULT_OK, intent);
                setInputManager(false);
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==0){
                shEditText.setText(data.getExtras().getString("name"));
                shId=data.getExtras().getString("id");
            }
        }
    }
}