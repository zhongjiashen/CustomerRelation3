package com.cr.activity.jxc.cggl.cgdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.cr.activity.BaseActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddShlcAdapter;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-采购管理-采购订单-审核流程
 * 
 * @author Administrator
 * 
 */
public class JxcCgglCgddShlcActivity extends BaseActivity implements OnClickListener {
    private JxcCgglCgddShlcAdapter adapter;
    private ListView          listView;
    String billid;
    private ImageView cxImageView,shImageView,qxImageView;
    List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();
    private String tableName;
    private String opid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_cggl_cgdd_shlc);
        addFHMethod();
        initActivity();
        initListView();
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
    	tableName=this.getIntent().getExtras().getString("tb");
        if(this.getIntent().hasExtra("billid")){
            billid=this.getIntent().getExtras().getString("billid");
        }
        if(this.getIntent().hasExtra("opid")){
        	opid=this.getIntent().getExtras().getString("opid");
        }
        cxImageView=(ImageView) findViewById(R.id.cx_imageview);
        cxImageView.setOnClickListener(this);
        shImageView=(ImageView) findViewById(R.id.sh_imageview);
        shImageView.setOnClickListener(this);
        qxImageView=(ImageView) findViewById(R.id.qx_imageview);
        qxImageView.setOnClickListener(this);
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        listView = (ListView) findViewById(R.id.xlistview);
        adapter = new JxcCgglCgddShlcAdapter(list, this,opid);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                Intent intent = new Intent(context, JxcCgglCgddDetailActivity.class);
//                intent.putExtra("billid", list.get(arg2-1).get("billid").toString());
//                startActivityForResult(intent,1);
//                adapter.setSelectIndex(arg2);
//            }
//        });
    }
    /**
     * 连接网络的操作(审核)
     */
    private void searchDateSh(String shzt) {
        if(adapter.getShjb().equals("")){
            showToastPromopt("请先选择审核级别!");
            return ;
        }
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("tabname", tableName);
        parmMap.put("pkvalue",billid);
        parmMap.put("levels", adapter.getShjb());
        parmMap.put("opid", adapter.getOpid());    
        parmMap.put("shzt", shzt);
        findServiceData2(1, ServerURL.BILLSH, parmMap, false);
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("tabname", tableName);
        parmMap.put("pkvalue", billid);
        findServiceData2(0, ServerURL.BILLSHLIST, parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if(returnSuccessType==0){
            if (returnJson.equals("")) {
                showToastPromopt(2);
            } else {
                list.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
            }
        }else if(returnSuccessType==1){
            if(returnJson.equals("")){
                showToastPromopt("操作成功");
                setResult(RESULT_OK);
                finish();
            }else{
                showToastPromopt("操作失败"+returnJson.substring(5));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.cx_imageview:
                searchDateSh("0");
                break;
            case R.id.sh_imageview:
                searchDateSh("1");
                break;
            case R.id.qx_imageview:
                finish();
                break;
        }
    }
}