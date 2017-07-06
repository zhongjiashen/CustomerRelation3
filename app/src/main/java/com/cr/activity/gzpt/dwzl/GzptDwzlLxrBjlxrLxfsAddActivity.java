package com.cr.activity.gzpt.dwzl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.cr.activity.BaseActivity;
import com.cr.model.SJZD;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 工作平台-单位资料-联系人-编辑联系人-联系方式-添加/修改
 * 
 * @author Administrator
 * 
 */
public class GzptDwzlLxrBjlxrLxfsAddActivity extends BaseActivity implements OnClickListener {
    private EditText dhEditText;
    private String clientId,lxrId;
    private Spinner lxfsSpinner;
    private String id="";
    private String lxfsId;
    List<SJZD>lxfsList=new ArrayList<SJZD>();
    private ArrayAdapter<String> lxfsAdapter;
    private ImageView saveImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_dwzl_dw_bjdw_lxfs_add);
        initActivity();
        addFHMethod();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        dhEditText=(EditText) findViewById(R.id.dh_edittext);
        lxfsSpinner=(Spinner) findViewById(R.id.lx_spinner);
        lxfsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                lxfsId=lxfsList.get(arg2).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        saveImageView=(ImageView) findViewById(R.id.save);
        saveImageView.setOnClickListener(this);
        if(this.getIntent().hasExtra("clientid")){
            clientId=this.getIntent().getExtras().getString("clientid");
            lxrId=this.getIntent().getExtras().getString("lxrid");
            lxfsId=this.getIntent().getExtras().getString("lxrid");
        }
        if(this.getIntent().hasExtra("id")){
            id=this.getIntent().getExtras().getString("id");
            lxfsId=this.getIntent().getExtras().getString("lb");
//            clientId=this.getIntent().getExtras().getString("clientid");
            dhEditText.setText(this.getIntent().getExtras().getString("itemno"));
            lxfsSpinner.setVisibility(View.GONE);
        }else{
            searchLxfsDate();//如果是新增，
        }
    }

    /**
     * 连接网络的操作(单位联系方式保存)
     */
    private void searchDateSave() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("id", id.equals("")?"0":id);
        parmMap.put("clientid", clientId);
        parmMap.put("lxrid", lxrId);
        parmMap.put("lb", lxfsId);
        parmMap.put("itemno",dhEditText.getText().toString());
        findServiceData(0, ServerURL.LXFSSAVE, parmMap);
    }
    /**
     * 连接网络的操作(查询联系方式)
     */
    private void searchLxfsDate(){
        Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("zdbm","LXFS");
        findServiceData2(1,ServerURL.DATADICT,parmMap,false);
    }


    /**
     * 监听事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                searchDateSave();
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
            case 1:
                lxfsList=SJZD.parseJsonToObject(returnJson);
                String[] bffsString = new String[lxfsList.size()];
                for (int i = 0; i < lxfsList.size(); i++) {
                    bffsString[i] = lxfsList.get(i).getDictmc();
                }
                lxfsAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item,bffsString);
                lxfsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lxfsSpinner.setAdapter(lxfsAdapter);
                break;
            default:
                break;
        }
    }
}