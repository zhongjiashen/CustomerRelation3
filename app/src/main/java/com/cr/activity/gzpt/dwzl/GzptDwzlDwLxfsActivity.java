package com.cr.activity.gzpt.dwzl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cr.activity.BaseActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlDwLxfsAdapter;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 工作平台-单位资料-单位-联系方式
 * 
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class GzptDwzlDwLxfsActivity extends BaseActivity implements OnClickListener {
    private String clientId;
    private BaseAdapter lxfsAdapter;
    private ListView listView;
    private List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_dwzl_dw_lxfs);
		addFHMethod();
		initActivity();
		if(this.getIntent().hasExtra("clientid")){
		    clientId=this.getIntent().getExtras().getString("clientid");
		}
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
	    listView=(ListView) findViewById(R.id.listview);
		lxfsAdapter=new GzptDwzlDwLxfsAdapter(list, activity);
		listView.setAdapter(lxfsAdapter);
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
	    Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
//        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("clientid",clientId);
        parmMap.put("Havelxr","0");
        findServiceData2(0,ServerURL.CLIENTLXFSLIST,parmMap,true);
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public void executeSuccess() {
		if(returnSuccessType==0){
            if(returnJson.equals("")){
                showToastPromopt(2);
            }else{
                list.addAll((List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson));
                String lxrName="";
                for(int i=0;i<list.size();i++){
                	Map<String, Object> m=list.get(i);
                	if(!lxrName.equals(m.get("lxrname").toString())){
                		Map<String, Object> m1=new HashMap<String, Object>();
                		m1.put("lb", "5");
                		m1.put("itemno", m.get("lxrname").toString());
                		list.add(i, m1);
                		lxrName=m.get("lxrname").toString();
                	}
                }
                lxfsAdapter.notifyDataSetChanged();
            }
        }
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		    case R.id.search_imageview:
		        searchDate();
		        break;
		    default:
			break;
		}
	}
}
