package com.cr.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

import com.cr.adapter.GzptXsdjAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.XSDLB;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
/**
 * 工作平台-销售单据
 * @author Administrator
 *
 */
public class GzptXsdjActivity extends BaseActivity implements OnClickListener{
	
	private XListView xsdjListView;
	private GzptXsdjAdapter xsdjAdapter;
	private ImageButton addButton;
	List<XSDLB> xsdjList=new ArrayList<XSDLB>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_xsdj);
        initActivity();
        searchDateXsdj(0);
        addFHMethod();
    }
    /**
	 * 初始化Activity
	 */
	private void initActivity(){
		xsdjListView=(XListView) findViewById(R.id.xsdj_listview);
		xsdjAdapter=new GzptXsdjAdapter(xsdjList, mContext);
		xsdjListView.setAdapter(xsdjAdapter);
		xsdjListView.setXListViewListener(xListViewListener);
		xsdjListView.setPullLoadEnable(true);
		xsdjListView.setPullRefreshEnable(false);
		xsdjListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(GzptXsdjActivity.this,GzptXsdjXsdActivity.class);
				intent.putExtra("object", xsdjList.get(arg2-1));
				startActivityForResult(intent, 0);
			}
		});
		addButton=(ImageButton) findViewById(R.id.add);
		addButton.setOnClickListener(this);
//		deleteButton=(ImageButton) findViewById(R.id.delete);
//		deleteButton.setOnClickListener(this);
	}
		
	
	/**
	 * 连接网络的操作(销售单据)
	 */
	private void searchDateXsdj(int type){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid", ShareUserInfo.getUserId(mContext));
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData(type,ServerURL.SALELISTOPER,parmMap);
	}
	/**
	 * 连接网络的操作（删除数据）
	 */
	private void searchDate2(String ids){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("ids", ids);
		parmMap.put("itemtype", "SALEM");
		findServiceData2(1,ServerURL.DELDATA,parmMap,true);
	}
	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add:
			Intent intent=new Intent();
			intent.setClass(GzptXsdjActivity.this,GzptXsdjXzxsdActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.delete:
			int checkSize=0;
			String ids = "";
			for(XSDLB xsdlb:xsdjAdapter.getList()){
				if(xsdlb.isCheck()){
					if(checkSize==0){
						ids+=xsdlb.getSaleid();
					}else{
						ids+=","+xsdlb.getSaleid();
					}
					checkSize++;
				}
			}
			if(checkSize==0){
				showToastPromopt("请选择要删除的数据！");
			}else{
				searchDate2(ids);
			}
			break;
		default:
			break;
		}
	}
	@Override
	public void executeSuccess() {
		switch (returnSuccessType) {
		case 0:
//			xsdjList.clear();
			xsdjList.addAll(XSDLB.parseJsonToObject(returnJson));
			xsdjAdapter.notifyDataSetChanged();
			break;
		case 1:
			if(returnJson.equals("")){
				xsdjList.clear();
				showToastPromopt("删除成功！");
				currentPage=1;
				searchDateXsdj(0);
			}else{
				showToastPromopt("删除失败！");
			}
			break;
		default:
			break;
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
					searchDateXsdj(0);
					onLoad();
				}
			}, 2000);
		}
	};
	@SuppressWarnings("deprecation")
	private void onLoad() {//停止刷新和加载功能，并且显示时间
		xsdjListView.stopRefresh();
		xsdjListView.stopLoadMore();
		xsdjListView.setRefreshTime(new Date().toLocaleString());
	}
	/**
	 * 执行返回的操作
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==0){
			xsdjList.clear();
			currentPage=1;
			searchDateXsdj(0);
		}else if(requestCode==1){
			xsdjList.clear();
			currentPage=1;
			searchDateXsdj(0);
		}
	};
}
