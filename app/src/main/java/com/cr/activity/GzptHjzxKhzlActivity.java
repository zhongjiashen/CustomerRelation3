package com.cr.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cr.adapter.GzptHjzxKhzlCjAdapter;
import com.cr.adapter.GzptHjzxKhzlWhAdapter;
import com.cr.adapter.GzptHjzxKhzlXmAdapter;
import com.cr.adapter.GzptHjzxKhzlZjAdapter;
import com.cr.adapter.SlidePageAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.model.GSLXRChild;
import com.cr.model.GSLXRDetail;
import com.cr.model.WXLB;
import com.cr.model.XMLB;
import com.cr.model.XSDLB;
import com.cr.model.ZJJL;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
/**
 * 工作平台-呼叫中心-客户资料
 * @author Administrator
 *
 */
public class GzptHjzxKhzlActivity extends BaseActivity implements OnClickListener{
	private TextView bfTextView,zjTextView,xmTextView,whTextView,cjTextView,khzlTextView;
	private LinearLayout bottomLayout,operLayout;
	private ViewPager viewPager;
	private LayoutInflater inflater;
	private View bfView,zjView,xmView,whView,cjView;
	private XListView zjListView,xmListView,whListView,cjListView;
	private ImageView gh1ImageView,gh2ImageView,sj1ImageView,sj2ImageView,dx1ImageView,dx2ImageView,qq1ImageView,qq2ImageView;
	private boolean isZj=false,isXm=false,isWh=false,isCj=false;//是否是第一次加载
	private GzptHjzxKhzlZjAdapter zjAdapter;
	private GzptHjzxKhzlXmAdapter xmAdapter;
	private GzptHjzxKhzlWhAdapter whAdapter;
	private GzptHjzxKhzlCjAdapter cjAdapter;
	List<ZJJL> zjList=new ArrayList<ZJJL>();
	List<XMLB> xmList=new ArrayList<XMLB>();
	List<WXLB> whList=new ArrayList<WXLB>();
	List<XSDLB> cjList=new ArrayList<XSDLB>();
	private GSLXRChild child;
	private GSLXRDetail gslxrDetail;
	private EditText dwmcEditText,lxrEditText,xbEditText,dqEditText,
	khdjEditText,hylxEditText,khlyEditText,khlxEditText,gh1EditText,
	gh2EditText,sj1EditText,sj2EditText,qq1EditText,qq2EditText,dwzzEditText,
	jtzzEditText,yx1EditText,yx2EditText;
	private ImageView corsor1,corsor2,corsor3,corsor4,corsor5;
	private ImageView oper;
	private PopupWindow mPopupWindow;
	
	private TextView bflrTextView,bjlxrTextView,xzxsdTextView;
	private String jhbid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_khzl);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initActivity();
        initZjListView();
        initXmListView();
        initWhListView();
        initCjListView();
        initBfListView();
        searchDateBf(0);
        addFHMethod();
    }
    /**
	 * 初始化Activity
	 */
	private void initActivity(){
		child=(GSLXRChild) this.getIntent().getExtras().getSerializable("object");
		if(this.getIntent().hasExtra("jhbid")){
			jhbid=this.getIntent().getExtras().getString("jhbid");
		}else{
			jhbid="0";
		}
		bflrTextView=(TextView) findViewById(R.id.bflr);
		bflrTextView.setOnClickListener(this);
		bjlxrTextView=(TextView) findViewById(R.id.bjlxr);
		bjlxrTextView.setOnClickListener(this);
//		xzlxrTextView=(TextView) findViewById(R.id.xzlxr);
//		xzlxrTextView.setOnClickListener(this);
		oper=(ImageView) findViewById(R.id.oper);
		oper.setOnClickListener(this);
		operLayout=(LinearLayout) findViewById(R.id.operLayout);
		operLayout.setOnClickListener(this);
		xzxsdTextView=(TextView) findViewById(R.id.xzxsd);
		xzxsdTextView.setOnClickListener(this);
		khzlTextView=(TextView) findViewById(R.id.khzlname);
		bottomLayout=(LinearLayout) findViewById(R.id.bottomLayout);
//		Log.v("dddd", ShareUserInfo.getKey(context, "khzlname"+"::"));
		if(ShareUserInfo.getKey(context, "khzlname").equals("hjzx")){
		    khzlTextView.setText("呼叫中心");
		}else if(ShareUserInfo.getKey(context, "khzlname").equals("yybf")){
            khzlTextView.setText("预约拜访");
        }else if(ShareUserInfo.getKey(context, "khzlname").equals("shhf")){
            khzlTextView.setText("售后回访");
        }else if(ShareUserInfo.getKey(context, "khzlname").equals("khgl")){
            khzlTextView.setText("客户管理");
        }else if(ShareUserInfo.getKey(context, "khzlname").equals("xzld")){
            khzlTextView.setText("新增来电");
        }
		
		bfTextView=(TextView) findViewById(R.id.bf_textview);
		bfTextView.setOnClickListener(this);
		zjTextView=(TextView) findViewById(R.id.zj_textview);
		zjTextView.setOnClickListener(this);
		xmTextView=(TextView) findViewById(R.id.xm_textview);
		xmTextView.setOnClickListener(this);
		whTextView=(TextView) findViewById(R.id.wh_textview);
		whTextView.setOnClickListener(this);
		cjTextView=(TextView) findViewById(R.id.cjjl_textview);
		cjTextView.setOnClickListener(this);
		
		corsor1=(ImageView) findViewById(R.id.corsor1);
		corsor2=(ImageView) findViewById(R.id.corsor2);
		corsor3=(ImageView) findViewById(R.id.corsor3);
		corsor4=(ImageView) findViewById(R.id.corsor4);
		corsor5=(ImageView) findViewById(R.id.corsor5);
		
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		List<View> viewPage = new ArrayList<View>();
		bfView=inflater.inflate(R.layout.activity_gzpt_khzl_bf, null);
		zjView=inflater.inflate(R.layout.activity_gzpt_khzl_zj, null);
		xmView=inflater.inflate(R.layout.activity_gzpt_khzl_xm, null);
		whView=inflater.inflate(R.layout.activity_gzpt_khzl_wh, null);
		cjView=inflater.inflate(R.layout.activity_gzpt_khzl_cjjl, null);
        viewPage.add(bfView);
        viewPage.add(zjView);
        viewPage.add(xmView);
        viewPage.add(whView);
        viewPage.add(cjView);
		SlidePageAdapter myAdapter = new SlidePageAdapter(viewPage, this);  
	    viewPager.setAdapter(myAdapter); 
      //下面的点图
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
			   if(arg0==0) {
				   viewPager.setCurrentItem(0);
				   corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
                   corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                   corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                   corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                   corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
			   }else if (arg0==1){
			    	 viewPager.setCurrentItem(1);
					 corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
					 corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
					 corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
					 corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
					 corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
					 if(!isZj){
						 zjList.clear();
						 searchDateZj(1);
						 isZj=true;
					 }
			   }else if (arg0==2){
					viewPager.setCurrentItem(2);
					 corsor3.setBackgroundResource(R.drawable.index_selectcorsor);
	                   corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
	                   corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
	                   corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
	                   corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
	                   if(!isXm){
	                	     xmList.clear();
	                    	 searchDateXm(2);
	                    	 isXm=true;
	                     }
			   }else if (arg0==3){
					viewPager.setCurrentItem(3);
					 corsor4.setBackgroundResource(R.drawable.index_selectcorsor);
	                   corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
	                   corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
	                   corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
	                   corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
	                   if(!isWh){
	                	   whList.clear();
	                    	 searchDateWh(3);
	                    	 isWh=true;
	                     }
			   }else if (arg0==4){
			       corsor5.setBackgroundResource(R.drawable.index_selectcorsor);
                   corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                   corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
                   corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
                   corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                   if(!isCj){
                	   cjList.clear();
                	   searchDateCj(4);
                  	 isCj=true;
                   }
			   }
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
//				Log.v("dddd", "onPageScrollStateChanged");
			}
		});
	}
	
	/*
     * 创建PopupWindow
     */ 
    @SuppressWarnings("deprecation")
	private void initPopuptWindow(View v) { 
        LayoutInflater layoutInflater = LayoutInflater.from(this); 
        View setView = layoutInflater.inflate(R.layout.activity_gzpt_khzl_oper, null); 
        // 创建一个PopupWindow 
        // 参数1：contentView 指定PopupWindow的内容 
        mPopupWindow = new PopupWindow(setView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());   
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        // 设置popupWindow的布局
        Rect outRect = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        int titleHeight = (int) getResources().getDimension(R.dimen.khzl_bottom);
        TextView bjdwTextView=(TextView) setView.findViewById(R.id.bjdw_textview);
        TextView xzlxrTextView=(TextView) setView.findViewById(R.id.xzlxr_textview);
        bjdwTextView.setOnClickListener(this);
        xzlxrTextView.setOnClickListener(this);
//        mPopupWindow.showAsDropDown(khzlTextView,0, 0,Gravity.BOTTOM);
        mPopupWindow.showAtLocation(bottomLayout, Gravity.RIGHT|Gravity.TOP, 0,outRect.height()-titleHeight*2-outRect.top+20);
    }
	/**
	 * 初始化”拜访“页面
	 */
	private void initBfListView(){
		dwmcEditText=(EditText) bfView.findViewById(R.id.bf_dwmc);
		lxrEditText=(EditText) bfView.findViewById(R.id.bf_lxr);
		xbEditText=(EditText) bfView.findViewById(R.id.bf_xb);
		dqEditText=(EditText) bfView.findViewById(R.id.bf_dq);
		khdjEditText=(EditText) bfView.findViewById(R.id.bf_khdj);
		hylxEditText=(EditText) bfView.findViewById(R.id.bf_hylx);
		khlyEditText=(EditText) bfView.findViewById(R.id.bf_khly);
		khlxEditText=(EditText) bfView.findViewById(R.id.bf_khlx);
		gh1EditText=(EditText) bfView.findViewById(R.id.bf_gh1);
		gh2EditText=(EditText) bfView.findViewById(R.id.bf_gh2);
		sj1EditText=(EditText) bfView.findViewById(R.id.bf_sj1);
		sj2EditText=(EditText) bfView.findViewById(R.id.bf_sj2);
		qq1EditText=(EditText) bfView.findViewById(R.id.bf_qq1);
		qq2EditText=(EditText) bfView.findViewById(R.id.bf_qq2);
		dwzzEditText=(EditText) bfView.findViewById(R.id.bf_dwzz);
		jtzzEditText=(EditText) bfView.findViewById(R.id.bf_jtzz);
		yx1EditText=(EditText) bfView.findViewById(R.id.bf_yx1);
		yx2EditText=(EditText) bfView.findViewById(R.id.bf_yx2);
		gh1ImageView=(ImageView) bfView.findViewById(R.id.gh1_imageview);
		gh1ImageView.setOnClickListener(this);
		gh2ImageView=(ImageView) bfView.findViewById(R.id.gh2_imageview);
		gh2ImageView.setOnClickListener(this);
		sj1ImageView=(ImageView) bfView.findViewById(R.id.sj1_imageview);
		sj1ImageView.setOnClickListener(this);
		sj2ImageView=(ImageView) bfView.findViewById(R.id.sj2_imageview);
		sj2ImageView.setOnClickListener(this);
		dx1ImageView=(ImageView) bfView.findViewById(R.id.dx1_imageview);
		dx1ImageView.setOnClickListener(this);
		dx2ImageView=(ImageView) bfView.findViewById(R.id.dx2_imageview);
		dx2ImageView.setOnClickListener(this);
		gh1ImageView=(ImageView) bfView.findViewById(R.id.gh1_imageview);
		gh1ImageView.setOnClickListener(this);
		qq1ImageView=(ImageView) bfView.findViewById(R.id.qq1_imageview);
		qq1ImageView.setOnClickListener(this);
		qq2ImageView=(ImageView) bfView.findViewById(R.id.qq2_imageview);
		qq2ImageView.setOnClickListener(this);
	}
	/**
	 * 初始化”最近“页面
	 */
	private void initZjListView(){
		zjListView=(XListView) zjView.findViewById(R.id.zj_listview);
		zjAdapter=new GzptHjzxKhzlZjAdapter(zjList, context);
		zjListView.setXListViewListener(xListViewListenerZj);
		zjListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent=new Intent(GzptHjzxKhzlActivity.this,GzptHjzxKhzlBfnrActivity.class);
                intent.putExtra("describe", zjList.get(arg2-1).getMemo());
                startActivity(intent);
            }
        });
		zjListView.setPullLoadEnable(true);
		zjListView.setPullRefreshEnable(false);
		zjListView.setAdapter(zjAdapter);
	}
	/**
	 * 初始化”项目“页面
	 */
	private void initXmListView(){
		xmListView=(XListView) xmView.findViewById(R.id.xm_listview);
		xmAdapter=new GzptHjzxKhzlXmAdapter(xmList, context);
		xmListView.setXListViewListener(xListViewListenerXm);
		xmListView.setPullLoadEnable(true);
		xmListView.setPullRefreshEnable(false);
		xmListView.setAdapter(xmAdapter);
	}
	/**
	 * 初始化”维护“页面
	 */
	private void initWhListView(){
		whListView=(XListView) whView.findViewById(R.id.wh_listview);
		whAdapter=new GzptHjzxKhzlWhAdapter(whList, context);
		whListView.setXListViewListener(xListViewListenerWh);
		whListView.setPullLoadEnable(true);
		whListView.setPullRefreshEnable(false);
		whListView.setAdapter(whAdapter);
	}
	/**
	 * 初始化”成交记录“页面
	 */
	private void initCjListView(){
		cjListView=(XListView) cjView.findViewById(R.id.cjjl_listview);
		cjAdapter=new GzptHjzxKhzlCjAdapter(cjList, context);
		cjListView.setXListViewListener(xListViewListenerCj);
		cjListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(GzptHjzxKhzlActivity.this,GzptXsdjXsdActivity.class);
				intent.putExtra("object", cjList.get(arg2-1));
				startActivityForResult(intent,4);
			}
		});
		cjListView.setPullLoadEnable(true);
		cjListView.setPullRefreshEnable(false);
		cjListView.setAdapter(cjAdapter);
	}
	/**
	 * 连接网络的操作(拜访)
	 */
	private void searchDateBf(int type){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("lxrid",child.getLxrid());
		findServiceData(type,ServerURL.LXRINFO,parmMap);
	}
	/**
	 * 连接网络的操作(最近)
	 */
	private void searchDateZj(int type){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("opid", ShareUserInfo.getUserId(context));
		parmMap.put("lxrid",child.getLxrid());
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData(type,ServerURL.VISITINFO,parmMap);
	}
	/**
	 * 连接网络的操作(项目)
	 */
	private void searchDateXm(int type){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("clientid ",child.getClientid());
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData(type,ServerURL.ITEMLIST,parmMap);
	}
	/**
	 * 连接网络的操作(维护)
	 */
	private void searchDateWh(int type){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("clientid ",child.getClientid());
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData(type,ServerURL.SHWXINFO,parmMap);
	}
	/**
	 * 连接网络的操作(成交记录)
	 */
	private void searchDateCj(int type){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(context));
		parmMap.put("clientid ",child.getClientid());
		parmMap.put("curpage", currentPage);
		parmMap.put("pagesize", pageSize);
		findServiceData(type,ServerURL.SALELISTCLIENT,parmMap);
	}
	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View view) {
		Intent intent=new Intent();
		switch (view.getId()) {
		case R.id.bflr:
			intent.putExtra("object", gslxrDetail);
			intent.putExtra("jhbid",jhbid);
			intent.setClass(GzptHjzxKhzlActivity.this,GzptBflrActivity.class);
			startActivityForResult(intent,1);
			break;
		case R.id.bjlxr:
			intent.putExtra("object", gslxrDetail);
			intent.setClass(GzptHjzxKhzlActivity.this,GzptKhzlLxrActivity.class);
			startActivityForResult(intent,0);
			break;
		case R.id.xzlxr_textview:
		    intent.putExtra("object2", gslxrDetail);
			intent.setClass(GzptHjzxKhzlActivity.this,GzptKhzlLxrActivity.class);
			startActivityForResult(intent,3);
			break;
		case R.id.bjdw_textview:
			intent.putExtra("object", gslxrDetail);
			intent.setClass(GzptHjzxKhzlActivity.this,GzptKhzlXzdwActivity.class);
			startActivityForResult(intent,3);
			break;
		case R.id.xzxsd:
			intent.setClass(GzptHjzxKhzlActivity.this,GzptXsdjXzxsdActivity.class);
			intent.putExtra("object2",gslxrDetail);
			startActivityForResult(intent,2);
			break;
		case R.id.bf_textview:
			viewPager.setCurrentItem(0);
			 corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
             corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
			break;
		case R.id.zj_textview:
			viewPager.setCurrentItem(1);
			 corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
             corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
             if(!isZj){
				 zjList.clear();
				 searchDateZj(1);
				 isZj=true;
			 }
			break;
		case R.id.xm_textview:
			viewPager.setCurrentItem(2);
			 corsor3.setBackgroundResource(R.drawable.index_selectcorsor);
             corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
             if(!isXm){
        	     xmList.clear();
            	 searchDateXm(2);
            	 isXm=true;
             }
			break;
		case R.id.wh_textview:
			viewPager.setCurrentItem(3);
			 corsor4.setBackgroundResource(R.drawable.index_selectcorsor);
             corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor5.setBackgroundResource(R.drawable.index_unselectcorsor);
             if(!isWh){
          	   whList.clear();
              	 searchDateWh(3);
              	 isWh=true;
               }
			break;
		case R.id.cjjl_textview:
			viewPager.setCurrentItem(4);
			 corsor5.setBackgroundResource(R.drawable.index_selectcorsor);
             corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor3.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor4.setBackgroundResource(R.drawable.index_unselectcorsor);
             corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
             if(!isCj){
          	   cjList.clear();
          	   searchDateCj(4);
            	 isCj=true;
             }
             break;
		case R.id.gh1_imageview:
			String number = gh1EditText.getText().toString();
			if(number.equals("")){
				showToastPromopt("电话号码为空");
			}else{
				//用intent启动拨打电话   
				Intent gh1intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));  
				startActivity(gh1intent);  
			}
			break;
		case R.id.gh2_imageview:
			String number2 = gh2EditText.getText().toString();
			if(number2.equals("")){
				showToastPromopt("电话号码为空");
			}else{
				//用intent启动拨打电话   
				Intent gh2intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number2));  
				startActivity(gh2intent);  
			}
			break;
		case R.id.sj1_imageview:
			String number3 = sj1EditText.getText().toString();
			if(number3.equals("")){
				showToastPromopt("电话号码为空");
			}else{
				//用intent启动拨打电话   
				Intent sj1intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number3));  
				startActivity(sj1intent);  
			}
			break;
		case R.id.sj2_imageview:
			String number4 = sj2EditText.getText().toString();
			if(number4.equals("")){
				showToastPromopt("电话号码为空");
			}else{
				//用intent启动拨打电话   
				Intent sj2intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number4));  
				startActivity(sj2intent);  
			}
			break;
		case R.id.dx1_imageview:
			String number5 = sj1EditText.getText().toString();
			if(number5.equals("")){
				showToastPromopt("电话号码为空");
			}else{
				Intent intentdx1 = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number5));  
				this.startActivity(intentdx1);
			}
			break;
		case R.id.dx2_imageview:
			String number6 = sj1EditText.getText().toString();
			if(number6.equals("")){
				showToastPromopt("电话号码为空");
			}else{
				Intent intentdx2 =  new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number6));  
				this.startActivity(intentdx2);
			}
			break;
		case R.id.qq1_imageview:
			if(qq1EditText.getText().toString().equals("")){
				showToastPromopt("请输入QQ号！");
			}else{
				String url="mqqwpa://im/chat?chat_type=wpa&uin="+qq1EditText.getText().toString()+"&version=1";
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
			break;
		case R.id.qq2_imageview:
			if(qq2EditText.getText().toString().equals("")){
				showToastPromopt("请输入QQ号！");
			}else{
				String url="mqqwpa://im/chat?chat_type=wpa&uin="+qq2EditText.getText().toString()+"&version=1";
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
			break;
		case R.id.operLayout:
			initPopuptWindow(view);
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
				return;
			}
			gslxrDetail=GSLXRDetail.parseJsonToObject(returnJson).get(0);
			dwmcEditText.setText(gslxrDetail.getCname().equals("null")?"":gslxrDetail.getCname());
			lxrEditText.setText(gslxrDetail.getLxrname().equals("null")?"":gslxrDetail.getLxrname());
			xbEditText.setText(gslxrDetail.getXb().equals("0")?"女":"男");
			dqEditText.setText(gslxrDetail.getAreafullname().equals("null")?"":gslxrDetail.getAreafullname());
			khdjEditText.setText(gslxrDetail.getTypename().equals("null")?"":gslxrDetail.getTypename());
			hylxEditText.setText(gslxrDetail.getHylxname().equals("null")?"":gslxrDetail.getHylxname());
			khlyEditText.setText(gslxrDetail.getKhlyname().equals("null")?"":gslxrDetail.getKhlyname());
			khlxEditText.setText(gslxrDetail.getKhtypename().equals("null")?"":gslxrDetail.getKhtypename());
			gh1EditText.setText(gslxrDetail.getPhone1().equals("null")?"":gslxrDetail.getPhone1());
			gh2EditText.setText(gslxrDetail.getPhone2().equals("null")?"":gslxrDetail.getPhone2());
			sj1EditText.setText(gslxrDetail.getSjhm1().equals("null")?"":gslxrDetail.getSjhm1());
			sj2EditText.setText(gslxrDetail.getSjhm2().equals("null")?"":gslxrDetail.getSjhm2());
			qq1EditText.setText(gslxrDetail.getQq1().equals("null")?"":gslxrDetail.getQq1());
			qq2EditText.setText(gslxrDetail.getQq2().equals("null")?"":gslxrDetail.getQq2());
			yx1EditText.setText(gslxrDetail.getSremail1().equals("null")?"":gslxrDetail.getSremail1());
			yx2EditText.setText(gslxrDetail.getSremail2().equals("null")?"":gslxrDetail.getSremail2());
			dwzzEditText.setText(gslxrDetail.getDwzz().equals("null")?"":gslxrDetail.getDwzz());
			jtzzEditText.setText(gslxrDetail.getJtzz().equals("null")?"":gslxrDetail.getJtzz());
			ShareUserInfo.setKey(context, "clientid", gslxrDetail.getClientid());
			break;
		case 1:
//			zjList.clear();
			if(returnJson.equals("")){
				showToastPromopt(2);
			}else{
				zjList.addAll(ZJJL.parseJsonToObject(returnJson));
			}
			zjAdapter.notifyDataSetChanged();
			break;
		case 2:
//			xmList.clear();
			if(returnJson.equals("")){
				showToastPromopt(2);
			}else{
				xmList.addAll(XMLB.parseJsonToObject(returnJson));
			}
			xmAdapter.notifyDataSetChanged();
			break;
		case 3:
//			whList.clear();
			if(returnJson.equals("")){
				showToastPromopt(2);
			}else{
				whList.addAll(WXLB.parseJsonToObject(returnJson));
			}
			whAdapter.notifyDataSetChanged();
			break;
		case 4:
//			cjList.clear();
			if(returnJson.equals("")){
				showToastPromopt(2);
			}else{
				cjList.addAll(XSDLB.parseJsonToObject(returnJson));
			}
			cjAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	} 
	/**
	 * 刷新
	 */
	private IXListViewListener xListViewListenerZj = new IXListViewListener() {
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
					searchDateZj(1);
					onLoadZj();
				}
			}, 2000);
		}
	};
	@SuppressWarnings("deprecation")
	private void onLoadZj() {//停止刷新和加载功能，并且显示时间
		zjListView.stopRefresh();
		zjListView.stopLoadMore();
		zjListView.setRefreshTime(new Date().toLocaleString());
	}
	/**
	 * 刷新
	 */
	private IXListViewListener xListViewListenerXm = new IXListViewListener() {
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
					searchDateXm(2);
					onLoadXm();
				}
			}, 2000);
		}
	};
	@SuppressWarnings("deprecation")
	private void onLoadXm() {//停止刷新和加载功能，并且显示时间
		xmListView.stopRefresh();
		xmListView.stopLoadMore();
		xmListView.setRefreshTime(new Date().toLocaleString());
	}
	/**
	 * 刷新
	 */
	private IXListViewListener xListViewListenerWh = new IXListViewListener() {
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
					searchDateWh(3);
					onLoadWh();
				}
			}, 2000);
		}
	};
	@SuppressWarnings("deprecation")
	private void onLoadWh() {//停止刷新和加载功能，并且显示时间
		whListView.stopRefresh();
		whListView.stopLoadMore();
		whListView.setRefreshTime(new Date().toLocaleString());
	}
	/**
	 * 刷新
	 */
	private IXListViewListener xListViewListenerCj = new IXListViewListener() {
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
					searchDateCj(4);
					onLoadCj();
				}
			}, 2000);
		}
	};
	@SuppressWarnings("deprecation")
	private void onLoadCj() {//停止刷新和加载功能，并且显示时间
		whListView.stopRefresh();
		whListView.stopLoadMore();
		whListView.setRefreshTime(new Date().toLocaleString());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if(resultCode==RESULT_OK){
	        if(requestCode==0){//编辑联系人
	            viewPager.setCurrentItem(0);
	            searchDateBf(0);
	        }else if(requestCode==1){//拜访录入
	        	if(isZj){
					 zjList.clear();
					 isZj=true;
					 searchDateZj(1);
				 }
	        	viewPager.setCurrentItem(1);
	        }else if(requestCode==2){//新增销售单
	        	if(isCj){
					 cjList.clear();
					 isCj=true;
					 searchDateCj(4);
				 }
	        	viewPager.setCurrentItem(4);
	        }else if(requestCode==3){//新增联系人
	            viewPager.setCurrentItem(0);
	            if(data.hasExtra("lxrid")){
	                child.setLxrid(data.getExtras().getString("lxrid"));
	            }
                searchDateBf(0);
	        }else if(requestCode==4){
	        	if(isCj){
					 cjList.clear();
					 isCj=true;
					 searchDateCj(4);
				 }
	        	viewPager.setCurrentItem(4);
	        }
	        
	    }
	}
}
