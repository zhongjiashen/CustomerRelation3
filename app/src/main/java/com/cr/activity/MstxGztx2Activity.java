package com.cr.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.cr.activity.gzpt.dwzl.GzptDwzlActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddDetailActivity;
import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshDetailActivity;
import com.cr.activity.jxc.xsgl.xsdd.JxcXsglXsddDetailActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdDetailActivity;
import com.cr.adapter.MstxGztx2Adapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.GSGG;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class MstxGztx2Activity extends BaseActivity {
	private MstxGztx2Adapter adapter;
	private XListView gztxListView;
	private TextView titleTextView;
	List<Map<String, Object>>gztxList=new ArrayList<Map<String, Object>>();
	private String typecode="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mstx_gztx2);
		addFHMethod();
		initActivity();
		initListView();
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		titleTextView=(TextView) findViewById(R.id.title);
		gztxListView=(XListView) findViewById(R.id.gztx_listview);
		gztxListView.setPullRefreshEnable(false);
		gztxListView.setPullLoadEnable(true);
		gztxListView.setXListViewListener(xListViewListener);
		typecode=this.getIntent().getExtras().get("typecode").toString();
		adapter=new MstxGztx2Adapter(gztxList, MstxGztx2Activity.this);
		gztxListView.setAdapter(adapter);
		if(typecode.equals("NBGG")){
			adapter.setType("0");
			titleTextView.setText("内部公告");
		}else if(typecode.equals("KHSR")){
			titleTextView.setText("客户生日");
			adapter.setType("1");
		}else if(typecode.equals("YGSR")){
			titleTextView.setText("员工生日");
			adapter.setType("1");
		}else if(typecode.equals("SSXM")){
			adapter.setType("8");
			titleTextView.setText("实施项目");
		}else if(typecode.equals("YYBF")){
			titleTextView.setText("预约拜访");
			adapter.setType("7");
		}else if(typecode.equals("CQYS")){
			titleTextView.setText("超期应收款");
			adapter.setType("4");
		}else if(typecode.equals("CQYF")){
			titleTextView.setText("超期应付款");
			adapter.setType("9");
		}else if(typecode.equals("CQDD")){
			titleTextView.setText("超期订单");
			adapter.setType("5");
		}else if(typecode.equals("GQSP")){
			titleTextView.setText("过期商品");
			adapter.setType("6");
		}else if(typecode.equals("QDTX")){
			titleTextView.setText("渠道提醒");
			adapter.setType("2");
		}else if(typecode.equals("SHHF")){
			titleTextView.setText("售后回访");
			adapter.setType("3");
		}else if(typecode.equals("KCBJ")){
			titleTextView.setText("库存报警");
			adapter.setType("10");
		}
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		
		gztxListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				String typecode=gztxList.get(arg2-1).get("typecode").toString();
				if(typecode.equals("NBGG")){
					GSGG gsgg=new GSGG();
					gsgg.setId(gztxList.get(arg2-1).get("id").toString());
					gsgg.setTitle(gztxList.get(arg2-1).get("title").toString());
					gsgg.setOpdate("");
					Intent intent = new Intent(context,MstxGsggDetailActivity.class);
					intent.putExtra("object",gsgg);
					startActivity(intent);
				}else if(typecode.equals("KHSR")){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("clientid", gztxList.get(arg2-1).get("clientid").toString());
					map.put("types","1");
					Intent intent = new Intent(context,GzptDwzlActivity.class);
					intent.putExtra("object",(Serializable)map);
					startActivity(intent);
				}else if(typecode.equals("YGSR")){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("clientid", gztxList.get(arg2-1).get("clientid").toString());
					map.put("types","5");
					Intent intent = new Intent(context,GzptDwzlActivity.class);
					intent.putExtra("object",(Serializable)map);
					startActivity(intent);
				}else if(typecode.equals("SSXM")){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("clientid", gztxList.get(arg2-1).get("clientid").toString());
					map.put("types","1");
					Intent intent = new Intent(context,GzptDwzlActivity.class);
					intent.putExtra("object",(Serializable)map);
					startActivity(intent);
				}else if(typecode.equals("YYBF")){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("clientid", gztxList.get(arg2-1).get("clientid").toString());
					Intent intent = new Intent(context,GzptDwzlActivity.class);
					map.put("types","");
					intent.putExtra("object",(Serializable)map);
					startActivity(intent);
				}else if(typecode.equals("SHHF")){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("clientid", gztxList.get(arg2-1).get("clientid").toString());
					Intent intent = new Intent(context,GzptDwzlActivity.class);
					map.put("types","");
					intent.putExtra("object",(Serializable)map);
					startActivity(intent);
				}else if(typecode.equals("CQYS")){
					Intent intent = new Intent(context,JxcXsglXskdDetailActivity.class);
					intent.putExtra("billid",gztxList.get(arg2-1).get("id").toString());
					startActivity(intent);
				}else if(typecode.equals("CQYF")){
					Intent intent = new Intent(context,JxcCgglCgshDetailActivity.class);
					intent.putExtra("billid",gztxList.get(arg2-1).get("id").toString());
					startActivity(intent);
				}else if(typecode.equals("CQDD")){
				    String billtype=gztxList.get(arg2-1).get("billtype").toString();
					if(billtype.equals("22")){
						Intent intent = new Intent(context,JxcCgglCgddDetailActivity.class);
						intent.putExtra("billid",gztxList.get(arg2-1).get("id").toString());
						startActivity(intent);
					}else if(billtype.equals("21")){
						Intent intent = new Intent(context,JxcXsglXsddDetailActivity.class);
						intent.putExtra("billid",gztxList.get(arg2-1).get("id").toString());
						startActivity(intent);
					}
				}else if(typecode.equals("GQSP")){
					
				}else if(typecode.equals("QDTX")){
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("clientid", gztxList.get(arg2-1).get("clientid").toString());
					map.put("types","1");
					Intent intent = new Intent(context,GzptDwzlActivity.class);
					intent.putExtra("object",(Serializable)map);
					startActivity(intent);
				}else if (typecode.equals("KCBJ")){

				}
			}
		});
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("typecode ", this.getIntent().getExtras().get("typecode").toString());
		parmMap.put("curpage",currentPage);
        parmMap.put("pagesize", pageSize);
		findServiceData2(0,ServerURL.BROADCASTPROMPTXX,parmMap,false);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void executeSuccess() {
//		gztxList.clear();
	    if(returnJson.equals("")){
	        showToastPromopt(2);
	    }else{
	        gztxList.addAll((List<Map<String,Object>>)PaseJson.paseJsonToObject(returnJson));
	        String type="";
	        for(Map<String, Object> m:gztxList){
	            if(m.get("title")!=null){
	                if(m.get("title").toString().equals(type)){
	                    m.put("type","0");
	                }else{
	                    m.put("type","1");
	                    type=m.get("title").toString();
	                }
	            }
	        }
	        adapter.notifyDataSetChanged();
	    }
	}
	
	/**
	 * 刷新
	 */
	private IXListViewListener xListViewListener = new IXListViewListener() {
		@Override
		public void onRefresh() {
			handler.postDelayed(new Runnable() {//实现延迟2秒加载刷新
				@Override
				public void run() {
					//不实现顶部刷新
				}
			},2000);
		}
		@Override
		public void onLoadMore() {
			handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
				@Override
				public void run() {
					currentPage++;
					searchDate();
					onLoad();
				}
			}, 2000);
		}
	};
	@SuppressWarnings("deprecation")
	private void onLoad() {//停止刷新和加载功能，并且显示时间
		gztxListView.stopRefresh();
		gztxListView.stopLoadMore();
		gztxListView.setRefreshTime(new Date().toLocaleString());
	}
}