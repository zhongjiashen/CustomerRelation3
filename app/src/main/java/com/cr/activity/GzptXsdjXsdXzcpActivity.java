package com.cr.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.cr.adapter.GzptXsdjXsdXzcpAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.CPMX;
import com.cr.model.LM;
import com.cr.model.XSDLB;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 新增销售单-选择产品
 * 
 * @author caiyanfei
 * @version $Id: GzptXsdjXsdXzcpActivity.java, v 0.1 2015-1-13 下午4:04:16 caiyanfei Exp $
 */
public class GzptXsdjXsdXzcpActivity extends BaseActivity implements OnClickListener{
	private EditText searchEditText;
	private Spinner xzlmSpinner;
	private ArrayAdapter<String> lmAdapter;
	private ImageButton saveButton,cxImageButton;
	private String lmid="";
	private List<LM>lmList=new ArrayList<LM>();
	private List<CPMX>cpmxList=new ArrayList<CPMX>();
	private XListView cpmxListView;
	private GzptXsdjXsdXzcpAdapter cpmxAdapter;
	private XSDLB xsdlb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		xsdlb=(XSDLB) this.getIntent().getExtras().getSerializable("object");
		setContentView(R.layout.activity_gzpt_xsdj_xsd_xzcp);
		addFHMethod();
		initActivity();
		searchLmDate();
		initListView();
	}
	
	/**
	 * 初始化Activity
	 */
	private void initActivity(){
		searchEditText=(EditText) findViewById(R.id.search_edit);
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		cxImageButton=(ImageButton) findViewById(R.id.cx);
		cxImageButton.setOnClickListener(this);
		xzlmSpinner=(Spinner) findViewById(R.id.xzlm_spinner);
		xzlmSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				lmid=lmList.get(arg2).getCode();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	/**
	 * 初始化产品明细的listview
	 */
	private void initListView(){
		cpmxListView=(XListView) findViewById(R.id.xzcp_listview);
		cpmxAdapter=new GzptXsdjXsdXzcpAdapter(cpmxList, mContext);
		cpmxListView.setPullRefreshEnable(false);
		cpmxListView.setPullLoadEnable(true);
		cpmxListView.setXListViewListener(xListViewListener);
		cpmxListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//            	List<CPMX>list=cpmxAdapter.getList();
//                cpmxList.clear();
//                list.get(arg2-1).setSelect(true);
//                cpmxList.addAll(list);
//                cpmxAdapter.notifyDataSetChanged();
//            	CPMX cpmx=cpmxAdapter.getList().get(arg2);
//            	if(cpmx.isSelect()){
//            		cpmx.setSelect(false);
//            	}else{
//            		cpmx.setSelect(true);
//            	}
//            	cpmxAdapter.notifyDataSetChanged();
            }
        });
		cpmxListView.setAdapter(cpmxAdapter);
	}
	/**
	 * 连接网络的操作（查询所有的产品）
	 */
	private void searchCPDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("typecode",lmid);
		parmMap.put("filter",searchEditText.getText().toString());
		parmMap.put("curpage",currentPage);
		parmMap.put("pagesize",pageSize);
		findServiceData(0,ServerURL.GOODSLIST,parmMap);
	}
	/**
	 * 连接网络的操作（添加商品）
	 */
	private void searchSaveCpDate(CPMX cpmx){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid",ShareUserInfo.getUserId(mContext));
		parmMap.put("id","0");
		parmMap.put("saleid",xsdlb.getSaleid());
		parmMap.put("goodsid",cpmx.getId());
		parmMap.put("qty ",cpmx.getQty());
		parmMap.put("price  ",cpmx.getPrice());
		findServiceData(2,ServerURL.SALEDETAILSAVE,parmMap);
	}
	/**
	 * 连接网络的操作(查询类目)
	 */
	private void searchLmDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		findServiceData(1,ServerURL.GOODSTYPE,parmMap);
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
					searchCPDate();
					onLoad();
				}
			}, 2000);
		}
	};
	@SuppressWarnings("deprecation")
	private void onLoad() {//停止刷新和加载功能，并且显示时间
		cpmxListView.stopRefresh();
		cpmxListView.stopLoadMore();
		cpmxListView.setRefreshTime(new Date().toLocaleString());
	}
	@Override
	public void executeSuccess() {
//		Log.v("dddd", returnJson);
		switch (returnSuccessType) {
		case 0:
			if(returnJson.equals("")){
				showToastPromopt(2);
			}else{
				cpmxList.addAll(CPMX.parseJsonToObject(returnJson));
			}
			cpmxAdapter.notifyDataSetChanged();
			break;
		case 1:
			lmList=LM.paseJsonToObject(returnJson);
			String[] dwString = new String[lmList.size()];
            for (int i = 0; i < lmList.size(); i++) {
            	dwString[i] = lmList.get(i).getName();
            }
			lmAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, dwString);
			lmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			xzlmSpinner.setAdapter(lmAdapter);
			break;
		case 2:
			if(returnJson.equals("")){
				showToastPromopt("保存成功！");
				setResult(RESULT_OK);
				finish();
			}else{
				showToastPromopt("保存失败！");
			}
			break;
		default:
			break;
		}
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.save:
			int i=0;
			for(CPMX cpmx:cpmxAdapter.getList()){
				if(cpmx.isSelect()){
//					searchSaveCpDate(cpmx);
					i++;
				}
			}
			if(i==0){
				showToastPromopt("请选择要添加的产品");
			}else if(i>1){
				showToastPromopt("目前系统只支持一次添加一个商品，请重新选择");
				break;
			}else{
				for(CPMX cpmx:cpmxAdapter.getList()){
					if(cpmx.isSelect()){
						if(cpmx.getQty()==null||cpmx.getQty().equals("0")){
							showToastPromopt("请选择产品的数量！");
						}else{
							searchSaveCpDate(cpmx);
						}
					}
				}
			}
			break;
		case R.id.cx:
			currentPage=1;
			cpmxList.clear();
			searchCPDate();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onExecuteFh() {
		setResult(RESULT_OK);
		super.onExecuteFh();
	}
}
