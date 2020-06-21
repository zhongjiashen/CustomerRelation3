package com.cr.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cr.adapter.MstxGrrcAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.GRRC;
import com.cr.tools.OperateMethod;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进销存--个人日程
 * @author Administrator
 *
 */
public class MstxGrrcActivity extends BaseActivity implements OnClickListener {
	private MstxGrrcAdapter adapter;
	private XListView grrcListView;
	private ImageButton addImageButton,deleteImageButton;
	List<GRRC>grrcList=new ArrayList<GRRC>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mstx_grrc);
		addFHMethod();
		initActivity();
		initListView();
		searchDate();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		grrcListView=(XListView) findViewById(R.id.grrc_listview);
		addImageButton=(ImageButton) findViewById(R.id.add);
		deleteImageButton=(ImageButton) findViewById(R.id.delete);
		addImageButton.setOnClickListener(this);
		deleteImageButton.setOnClickListener(this);
	}
	/**
	 * 初始化ListView
	 */
	private void initListView(){
		adapter=new MstxGrrcAdapter(grrcList, context, this);
		grrcListView.setAdapter(adapter);
		grrcListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent(context,MstxGrrcDetailActivity.class);
				GRRC grrc=grrcList.get(arg2-1);
				intent.putExtra("object",grrc);
				startActivityForResult(intent, 0);
			}
		});
		grrcListView.setXListViewListener(xListViewListener);
		grrcListView.setPullLoadEnable(true);
		grrcListView.setPullRefreshEnable(false);
//		grrcListView.removeHeaderView(v)
//		grrcListView.setOnItemLongClickListener(new CustomerOnItemLongClickListener(MstxGrrcActivity.this, operateMethod));
	}
	/**
	 * 连接网络的操作
	 */
	private void searchDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
//		Log.v("dddd", currentPage+":::");
		findServiceData2(0,ServerURL.RCPROMPTLIST,parmMap,false);
	}
	/**
	 * 连接网络的操作（删除数据）
	 */
	private void searchDate2(String ids){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("ids", ids);
		parmMap.put("itemtype", "GRRC");
		findServiceData2(1,ServerURL.DELDATA,parmMap,true);
	}
	@Override
	public void executeSuccess() {
		Log.v("返回结果",returnJson);
		if(returnSuccessType==0){
			grrcList.addAll(GRRC.parseJsonToObject(returnJson));
			adapter.notifyDataSetChanged();
		}else if(returnSuccessType==1){
			if(returnJson.equals("")){
				showToastPromopt("删除成功！");
				grrcList.clear();
				currentPage=0;
				searchDate();
			}else{
				showToastPromopt("删除失败！");
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.add:
		    Intent intent = new Intent(context,MstxGrrcDetailActivity.class);
//            GRRC grrc=grrcList.get(arg2);
            intent.putExtra("object","");
//            startActivity(intent);
            startActivityForResult(intent, 1);
			break;
		case R.id.delete:
			new AlertDialog.Builder(activity)
			.setTitle("确定要删除当前记录吗？")
			.setNegativeButton("删除",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0,
								int arg1) {
							int checkSize=0;
							String ids = "";
							for(GRRC grrc:adapter.getList()){
								if(grrc.isCheck()){
									if(checkSize==0){
										ids+=grrc.getId();
									}else{
										ids+=","+grrc.getId();
									}
									checkSize++;
								}
							}
							if(checkSize==0){
								showToastPromopt("请选择要删除的数据！");
							}else{
								searchDate2(ids);
							}
						}
					}).setPositiveButton("取消", null).show();
			
			break;
		default:
			break;
		}
	}
	/**
	 * 长按事件回调函数执行的操作
	 */
	OperateMethod operateMethod=new OperateMethod() {
		@Override
		public void operateData(int index) {
			grrcList.remove(index);
			adapter.notifyDataSetChanged();
		}
	};
	/**
	 * 执行返回的操作
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null){
			if(data.hasExtra("mess")&&data.getExtras().getString("mess").equals("0")){
				grrcList.clear();
				currentPage=1;
				searchDate();
				Toast.makeText(this,"刷新成功！",Toast.LENGTH_SHORT).show();
			}
		}
	};
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
					currentPage=1;
					searchDate();
					onLoad();
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
		grrcListView.stopRefresh();
		grrcListView.stopLoadMore();
		grrcListView.setRefreshTime(new Date().toLocaleString());
	}
}
