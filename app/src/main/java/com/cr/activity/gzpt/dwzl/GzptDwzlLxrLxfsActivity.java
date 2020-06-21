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
import com.cr.adapter.gzpt.dwzl.GzptDwzlLxrLxfsAdapter;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 工作平台-单位资料-联系人-联系方式
 * 
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class GzptDwzlLxrLxfsActivity extends BaseActivity implements OnClickListener {
    private String lxrId;
    private BaseAdapter lxfsAdapter;
    private ListView listView;
    private List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_dwzl_lxr_lxfs);
		addFHMethod();
		initActivity();
		if(this.getIntent().hasExtra("lxrid")){
		    lxrId=this.getIntent().getExtras().getString("lxrid");
		}
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
	    listView=(ListView) findViewById(R.id.listview);
		lxfsAdapter=new GzptDwzlLxrLxfsAdapter(list, activity);
		listView.setAdapter(lxfsAdapter);
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
	    Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
//        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("lxrid",lxrId);
//        parmMap.put("Havelxr","0");
        findServiceData2(0,ServerURL.LXRLXFSLIST,parmMap,true);
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public void executeSuccess() {
		if(returnSuccessType==0){
            if(returnJson.equals("")){
                showToastPromopt(2);
            }else{
                list.addAll((List<Map<String, Object>>)PaseJson.paseJsonToObject(returnJson));
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
