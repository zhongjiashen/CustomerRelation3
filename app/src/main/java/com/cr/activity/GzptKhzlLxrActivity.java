package com.cr.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.cr.model.DQ;
import com.cr.model.DW;
import com.cr.model.GSLXRChild;
import com.cr.model.GSLXRDetail;
import com.cr.model.SJZD;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 编辑和添加联系人
 * 
 * @author caiyanfei
 * @version $Id: GzptKhzlLxrActivity.java, v 0.1 2015-1-15 下午5:43:21 caiyanfei Exp $
 */
public class GzptKhzlLxrActivity extends BaseActivity implements OnClickListener {
	private TextView titleTextView;
	private GSLXRDetail gslxrDetail;
	private EditText lxrEditText,gh1EditText,gh2EditText,sj1EditText,sj2EditText,
		qq1EditText,qq2EditText,yx1EditText,yx2EditText,dwzzEditText,jtzzEditText,
		dwmcEditText,khdjEditText,hylxEditText,khlyEditText,khlxEditText,dqsEditText,
		dqs2EditText,dqqEditText;
	private Spinner xbSpinner;
	private ArrayAdapter<String> xbAdapter;
	List<SJZD>khdjList=new ArrayList<SJZD>();
	List<SJZD>hylxList=new ArrayList<SJZD>();
	List<SJZD>khlyList=new ArrayList<SJZD>();
	List<SJZD>khlxList=new ArrayList<SJZD>();
	List<DW>dwList=new ArrayList<DW>();
	List<DQ>dqsList=new ArrayList<DQ>();
	List<DQ>dqs2List=new ArrayList<DQ>();
	List<DQ>dqqList=new ArrayList<DQ>();
	String selectDwName="";//新增的单位的名字
	int selectDwIndex=0;//新增的单位的位置信息
	
	String dwmcid,xbid,dqsid,dqs2id,dqqid,khdjid,hylxid,khlyid,khlxid;
	private ImageButton saveButton,addDwButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gzpt_khzl_lxr);
		initActivity();
		addFHMethod();
//		addZYMethod();
//		searchDwmcDate();
//		searchKhdjDate();
//		searchDqSDate();
	}
	/**
	 * 初始化Activity
	 */
	private void initActivity() {
		saveButton=(ImageButton) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		addDwButton=(ImageButton) findViewById(R.id.addDw);
		addDwButton.setOnClickListener(this);
		titleTextView=(TextView) findViewById(R.id.title_textview);
		dwmcEditText=(EditText) findViewById(R.id.bf_dwmc);
		dwmcEditText.setOnClickListener(this);
		lxrEditText=(EditText) findViewById(R.id.bf_lxr);
		xbSpinner=(Spinner) findViewById(R.id.bf_xb);
		xbSpinner.setOnItemSelectedListener(itemSelectedListenerXb);
		String[]xbStrings={"男","女"};
		xbAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,xbStrings);
		xbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        xbSpinner.setAdapter(xbAdapter);
//		dqsSpinner=(Spinner) findViewById(R.id.bf_dqs);
//		dqsSpinner.setOnItemSelectedListener(itemSelectedListenerDqs);
//		dqs2Spinner=(Spinner) findViewById(R.id.bf_dqs2);
//		dqs2Spinner.setOnItemSelectedListener(itemSelectedListenerDqs2);
//		dqqSpinner=(Spinner) findViewById(R.id.bf_dqq);
//		dqqSpinner.setOnItemSelectedListener(itemSelectedListenerDqq);
        dqsEditText=(EditText) findViewById(R.id.bf_dqs);
        dqs2EditText=(EditText) findViewById(R.id.bf_dqs2);
        dqqEditText=(EditText) findViewById(R.id.bf_dqq);
//		khdjSpinner=(Spinner) findViewById(R.id.bf_khdj);
//		khdjSpinner.setOnItemSelectedListener(itemSelectedListenerKhdj);
//		hylxSpinner=(Spinner) findViewById(R.id.bf_hylx);
//		hylxSpinner.setOnItemSelectedListener(itemSelectedListenerHylx);
//		khlySpinner=(Spinner) findViewById(R.id.bf_khly);
//		khlySpinner.setOnItemSelectedListener(itemSelectedListenerKhly);
//		khlxSpinner=(Spinner) findViewById(R.id.bf_khlx);
//		khlxSpinner.setOnItemSelectedListener(itemSelectedListenerKhlx);
		khdjEditText=(EditText) findViewById(R.id.bf_khdj);
		hylxEditText=(EditText) findViewById(R.id.bf_hylx);
		khlyEditText=(EditText) findViewById(R.id.bf_khly);
		khlxEditText=(EditText) findViewById(R.id.bf_khlx);
		gh1EditText=(EditText) findViewById(R.id.bf_gh1);
		gh2EditText=(EditText) findViewById(R.id.bf_gh2);
		sj1EditText=(EditText) findViewById(R.id.bf_sj1);
		yx1EditText=(EditText) findViewById(R.id.bf_yx1);
		yx2EditText=(EditText) findViewById(R.id.bf_yx2);
		if(this.getIntent().hasExtra("tel")){
		    String tel=this.getIntent().getExtras().getString("tel");
		    if(tel.substring(0, 1).equals("1")&&tel.length()==11){
		        sj1EditText.setText(tel);
		    }else{
		        gh1EditText.setText(tel);
		    }
		}
		sj2EditText=(EditText) findViewById(R.id.bf_sj2);
		qq1EditText=(EditText) findViewById(R.id.bf_qq1);
		qq2EditText=(EditText) findViewById(R.id.bf_qq2);
		dwzzEditText=(EditText) findViewById(R.id.bf_dwzz);
		jtzzEditText=(EditText) findViewById(R.id.bf_jtzz);
		if(getIntent().hasExtra("object")){
			gslxrDetail=(GSLXRDetail) this.getIntent().getExtras().getSerializable("object");
			titleTextView.setText("编辑联系人");
			lxrEditText.setText(gslxrDetail.getLxrname().equals("null")?"":gslxrDetail.getLxrname());
			gh1EditText.setText(gslxrDetail.getPhone1().equals("null")?"":gslxrDetail.getPhone1());
			gh2EditText.setText(gslxrDetail.getPhone2().equals("null")?"":gslxrDetail.getPhone2());
			sj1EditText.setText(gslxrDetail.getSjhm1().equals("null")?"":gslxrDetail.getSjhm1());
			sj2EditText.setText(gslxrDetail.getSjhm2().equals("null")?"":gslxrDetail.getSjhm2());
			qq1EditText.setText(gslxrDetail.getQq1().equals("null")?"":gslxrDetail.getQq1());
			qq2EditText.setText(gslxrDetail.getQq2().equals("null")?"":gslxrDetail.getQq2());
			dwmcEditText.setText(gslxrDetail.getCname().equals("null")?"":gslxrDetail.getCname());
			dwmcid=gslxrDetail.getClientid();
			dwzzEditText.setText(gslxrDetail.getDwzz().equals("null")?"":gslxrDetail.getDwzz());
			jtzzEditText.setText(gslxrDetail.getJtzz().equals("null")?"":gslxrDetail.getJtzz());
			yx1EditText.setText(gslxrDetail.getSremail1().equals("null")?"":gslxrDetail.getSremail1());
			yx2EditText.setText(gslxrDetail.getSremail2().equals("null")?"":gslxrDetail.getSremail2());
			khdjEditText.setText(gslxrDetail.getTypename());
			khdjid=gslxrDetail.getTypeid();
			hylxEditText.setText(gslxrDetail.getHylxname());
			hylxid=gslxrDetail.getHytypeid();
			khlyEditText.setText(gslxrDetail.getKhlyname());
			khlyid=gslxrDetail.getCsourceid();
			khlxEditText.setText(gslxrDetail.getKhtypename());
			khlxid=gslxrDetail.getKhtypeid();
			String[] name=gslxrDetail.getAreafullname().split("/");
			dqsEditText.setText(name.length>1?name[1]:"");
            dqs2EditText.setText(name.length>2?name[2]:"");
            dqqEditText.setText(name.length>3?name[3]:"");
			if(gslxrDetail.getXb().equals("1")){
			    xbSpinner.setSelection(0);
			}else{
			    xbSpinner.setSelection(1);
			}
		}else{
			titleTextView.setText("新增联系人");
		}
		if(getIntent().hasExtra("object2")){
		    gslxrDetail=(GSLXRDetail) this.getIntent().getExtras().getSerializable("object2");
		    dwmcEditText.setText(gslxrDetail.getCname().equals("null")?"":gslxrDetail.getCname());
            dwmcid=gslxrDetail.getClientid();
		    khdjEditText.setText(gslxrDetail.getTypename());
            khdjid=gslxrDetail.getTypeid();
            hylxEditText.setText(gslxrDetail.getHylxname());
            hylxid=gslxrDetail.getHytypeid();
            khlyEditText.setText(gslxrDetail.getKhlyname());
            khlyid=gslxrDetail.getCsourceid();
            khlxEditText.setText(gslxrDetail.getKhtypename());
            khlxid=gslxrDetail.getKhtypeid();
            String[] name=gslxrDetail.getAreafullname().split("/");
            dqsEditText.setText(name.length>1?name[1]:"");
            dqs2EditText.setText(name.length>2?name[2]:"");
            dqqEditText.setText(name.length>3?name[3]:"");
		}
		if(getIntent().hasExtra("object3")){
			gslxrDetail=(GSLXRDetail) this.getIntent().getExtras().getSerializable("object3");
			searchDwDate(gslxrDetail.getClientid());
		}
	}
	/**
	 * 连接网络的操作(查询单位名称)
	 */
//	private void searchDwmcDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("opid", ShareUserInfo.getUserId(context));
//		parmMap.put("filter","");
//		parmMap.put("curpage",currentPage);
//		parmMap.put("pagesize",pageSize);
//		findServiceData2(0,ServerURL.CLIENTLIST,parmMap,false);
//	}
	/**
	 * 连接网络的操作(查询地区(省))
	 */
//	private void searchDqSDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("levels","2");
//		parmMap.put("parentid","0");
//		findServiceData2(1,ServerURL.AREALIST,parmMap,false);
//	}
	/**
	 * 连接网络的操作(查询地区(市))
	 */
//	private void searchDqS2Date(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("levels","3");
//		parmMap.put("parentid",dqsid);
//		findServiceData2(6,ServerURL.AREALIST,parmMap,false);
//	}
	/**
	 * 连接网络的操作(查询地区(区))
	 */
//	private void searchDqqDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("levels","4");
//		parmMap.put("parentid",dqs2id);
//		findServiceData2(7,ServerURL.AREALIST,parmMap,false);
//	}
//	/**
//	 * 连接网络的操作(查询客户等级)
//	 */
//	private void searchKhdjDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("zdbm","KHDJ");
//		findServiceData2(2,ServerURL.DATADICT,parmMap,false);
//	}
//	/**
//	 * 连接网络的操作(查询行业类型)
//	 */
//	private void searchHylxDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("zdbm","HYLX");
//		findServiceData2(3,ServerURL.DATADICT,parmMap,false);
//	}
//	/**
//	 * 连接网络的操作(查询客户来源)
//	 */
//	private void searchKhlyDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("zdbm","KHLY");
//		findServiceData2(4,ServerURL.DATADICT,parmMap,false);
//	}
//	/**
//	 * 连接网络的操作(查询客户类型)
//	 */
//	private void searchKhlxDate(){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("dbname", ShareUserInfo.getDbName(context));
//		parmMap.put("zdbm","KHLX");
//		findServiceData2(5,ServerURL.DATADICT,parmMap,false);
//	}
	/**
     * 连接网络的操作(查询单位信息)
     */
    private void searchDwDate(String id){
        Map<String, Object> parmMap=new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("clientid",id);
        findServiceData2(9,ServerURL.CLIENTINFO,parmMap,false);
    }
	/**
	 * 连接网络的操作(保存联系人信息)
	 */
	private void savelxrDate(){
		Map<String, Object> parmMap=new HashMap<String, Object>();
		parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
		parmMap.put("opid",ShareUserInfo.getUserId(mContext));
		parmMap.put("lxrid",gslxrDetail==null?0:gslxrDetail.getId());
		parmMap.put("lxrname",lxrEditText.getText().toString());
		parmMap.put("clientid",dwmcid);
		parmMap.put("areafullname",dqsEditText.getText().toString()+"/"+dqs2EditText.getText().toString()+"/"+dqqEditText.getText().toString());
		parmMap.put("typeid",khdjid);
		parmMap.put("xb",xbSpinner.getSelectedItem().toString().equals("男")?"1":"0");
		parmMap.put("hytypeid",hylxid);
		parmMap.put("csourceid",khlyid);
		parmMap.put("khtypeid",khlxid);
		parmMap.put("phone",gh1EditText.getText().toString());
		parmMap.put("phone1",gh2EditText.getText().toString());
		parmMap.put("sjhm",sj1EditText.getText().toString());
		parmMap.put("sjhm1",sj2EditText.getText().toString());
		parmMap.put("qq",qq1EditText.getText().toString());
		parmMap.put("qq1",qq2EditText.getText().toString());
		parmMap.put("email",yx1EditText.getText().toString());
		parmMap.put("email1",yx2EditText.getText().toString());
		parmMap.put("bgaddress",dwzzEditText.getText().toString());
		parmMap.put("jtaddress",jtzzEditText.getText().toString());
		findServiceData2(8,ServerURL.LXRSAVE,parmMap,false);
	}
	/**
     * 监听事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.save:
        	if(validateMsg()){
        		savelxrDate();
        	}
        	break;
        case R.id.addDw:
        	Intent intent=new Intent(GzptKhzlLxrActivity.this,GzptKhzlXzdwActivity.class);
        	startActivityForResult(intent,0);
        	break;
        case R.id.bf_dwmc:
            Intent intent2=new Intent(GzptKhzlLxrActivity.this,GzptXzdwActivity.class);
            startActivityForResult(intent2,1);
            break;
        default:
            break;
        }
    }
    /**
	 * 数据返回成功执行的方法
	 * @see BaseActivity#executeSuccess()
	 */
	@Override
	public void executeSuccess() {
	    switch (returnSuccessType) {
//            case 0:
//            	dwList=DW.paseJsonToObject(returnJson);
//    			String[] dwString = new String[dwList.size()];
//                for (int i = 0; i < dwList.size(); i++) {
//                	dwString[i] = dwList.get(i).getName();
//                	if(!selectDwName.equals("")){
//                		if(dwString[i].equals(selectDwName)){
//                			selectDwIndex=i;
//                		}
//                	}
//                }
//                dwmcAdapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_spinner_item,dwString);
//                dwmcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                dwmcSpinner.setAdapter(dwmcAdapter);
//                dwmcSpinner.setSelection(selectDwIndex);
////                searchKhdjDate();
//            	break;
//            case 1:
//                String sheng="";
//                int index=0;
//                if(gslxrDetail!=null){
//                	String[] s=gslxrDetail.getAreafullname().split("/");
//                	if(s.length>1){
//                		sheng=gslxrDetail.getAreafullname().split("/")[1];
//                	}
//                }
//            	dqsList=DQ.paseJsonToObject(returnJson);
//    			String[] dqString = new String[dqsList.size()];
//                for (int i = 0; i < dqsList.size(); i++) {
//                	dqString[i] = dqsList.get(i).getName();
//                	if(dqsList.get(i).getName().equals(sheng)){
//                	    index=i;
//                	}
//                }
//                dqsAdapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_spinner_item,dqString);
//                dqsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                dqsSpinner.setAdapter(dqsAdapter);
//                dqsSpinner.setSelection(index);
//            	break;
//            case 2:
//                String khdjid="";
//                int index2=0;
//                if(gslxrDetail!=null){
//                    khdjid=gslxrDetail.getTypeid();
//                }
//            	khdjList=SJZD.parseJsonToObject(returnJson);
//    			String[] bffsString = new String[khdjList.size()];
//                for (int i = 0; i < khdjList.size(); i++) {
//                	bffsString[i] = khdjList.get(i).getDictmc();
//                	if(khdjList.get(i).getId().equals(khdjid)){
//                	    index2=i;
//                	}
//                }
//                khdjAdapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_spinner_item,bffsString);
//                khdjAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                khdjSpinner.setAdapter(khdjAdapter);
//                khdjSpinner.setSelection(index2);
////                searchHylxDate();
//            	break;
//            case 3:
//                String hylxid="";
//                int index3=0;
//                if(gslxrDetail!=null){
//                    hylxid=gslxrDetail.getHytypeid();
//                }
//            	hylxList=SJZD.parseJsonToObject(returnJson);
//    			String[] hylxString = new String[hylxList.size()];
//                for (int i = 0; i < hylxList.size(); i++) {
//                	hylxString[i] = hylxList.get(i).getDictmc();
//                	if(hylxList.get(i).getId().equals(hylxid)){
//                	    index3=i;
//                	}
//                }
//                hylxAdapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_spinner_item,hylxString);
//                hylxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                hylxSpinner.setAdapter(hylxAdapter);
//                hylxSpinner.setSelection(index3);
////                searchKhlyDate();
//            	break;
//            case 4:
//                String khlyid="";
//                int index4=0;
//                if(gslxrDetail!=null){
//                    khlyid=gslxrDetail.getCsourceid();
//                }
//            	khlyList=SJZD.parseJsonToObject(returnJson);
//    			String[] khlyString = new String[khlyList.size()];
//                for (int i = 0; i < khlyList.size(); i++) {
//                	khlyString[i] = khlyList.get(i).getDictmc();
//                	if(khlyList.get(i).getId().equals(khlyid)){
//                	    index4=i;
//                	}
//                }
//                khlyAdapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_spinner_item,khlyString);
//                khlyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                khlySpinner.setAdapter(khlyAdapter);
//                khlySpinner.setSelection(index4);
////                searchKhlxDate();
//            	break;
//            case 5:
//                String khlxid="";
//                int index5=0;
//                if(gslxrDetail!=null){
//                    khlxid=gslxrDetail.getKhtypeid();
//                }
//            	khlxList=SJZD.parseJsonToObject(returnJson);
//    			String[] khlxString = new String[khlxList.size()];
//                for (int i = 0; i < khlxList.size(); i++) {
//                	khlxString[i] = khlxList.get(i).getDictmc();
//                	if(khlxList.get(i).getId().equals(khlxid)){
//                        index5=i;
//                    }
//                }
//                khlxAdapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_spinner_item,khlxString);
//                khlxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                khlxSpinner.setAdapter(khlxAdapter);
//                khlxSpinner.setSelection(index5);
//                searchDqSDate();
//            	break;
//            case 6:
//                String sheng6="";
//                int index6=0;
//                if(gslxrDetail!=null){
//                	String[] s6=gslxrDetail.getAreafullname().split("/");
//                	if(s6.length>2){
//                		sheng6=gslxrDetail.getAreafullname().split("/")[2];
//                	}
//                }
//            	dqs2List=DQ.paseJsonToObject(returnJson);
//    			String[] dqs2String = new String[dqs2List.size()];
//                for (int i = 0; i < dqs2List.size(); i++) {
//                	dqs2String[i] = dqs2List.get(i).getName();
////                	Log.v("dddd",dqs2List.get(i).getName()+":::"+sheng6);
//                	if(dqs2List.get(i).getName().equals(sheng6)){
//                        index6=i;
//                    }
//                }
//                dqs2Adapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_spinner_item,dqs2String);
//                dqs2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                dqs2Spinner.setAdapter(dqs2Adapter);
//                dqs2Spinner.setSelection(index6);
////                searchDqqDate();
//            	break;
//            case 7:
//                String sheng7="";
//                int index7=0;
//                if(gslxrDetail!=null){
//                	String[] s7=gslxrDetail.getAreafullname().split("/");
//                	if(s7.length>3){
//                		sheng7=gslxrDetail.getAreafullname().split("/")[3];
//                	}
//                }
//            	dqqList=DQ.paseJsonToObject(returnJson);
//    			String[] dqqString = new String[dqqList.size()];
//                for (int i = 0; i < dqqList.size(); i++) {
//                	dqqString[i] = dqqList.get(i).getName();
//                	if(dqqList.get(i).getName().equals(sheng7)){
//                        index7=i;
//                    }
//                }
//                dqqAdapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_spinner_item,dqqString);
//                dqqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                dqqSpinner.setAdapter(dqqAdapter);
//                dqqSpinner.setSelection(index7);
////                searchKhdjDate();
//            	break;
            case 8:
            	if(returnJson.equals("")){
            		showToastPromopt("保存成功");
            		if(this.getIntent().hasExtra("type")){//如果是客户管理进来的新增联系人
            		    GSLXRChild child=new GSLXRChild();
            		    child.setLxrid(returnJsonId);
            		    child.setClientid(dwmcid);
            		    Intent intent=new Intent(GzptKhzlLxrActivity.this,GzptHjzxKhzlActivity.class);
            		    intent.putExtra("object",child);
            		    startActivity(intent);
            		}else{
            		    Intent intent=new Intent();
            		    intent.putExtra("lxrid",returnJsonId);
            		    setResult(RESULT_OK,intent);
            		}
            		onExecuteFh();
            	}else{
            		showToastPromopt("保存失败"+returnJson.substring(5));
            	}
            	break;
            case 9:
                GSLXRDetail detail=GSLXRDetail.parseJsonToObject2(returnJson).get(0);
                dwmcid=detail.getClientid();
                dwmcEditText.setText(detail.getCname());
                khdjEditText.setText(detail.getTypename());
                khdjid=detail.getTypeid();
                hylxEditText.setText(detail.getHylxname());
                hylxid=detail.getHytypeid();
                khlyEditText.setText(detail.getKhlyname());
                khlyid=detail.getCsourceid();
                khlxEditText.setText(detail.getKhtypename());
                khlxid=detail.getKhtypeid();
                String[] name=detail.getAreafullname().split("/");
                dqsEditText.setText(name.length>1?name[1]:"");
                dqs2EditText.setText(name.length>2?name[2]:"");
                dqqEditText.setText(name.length>3?name[3]:"");
                break;
            default:
                break;
        }
	}
	OnItemSelectedListener itemSelectedListenerDwmc=new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			dwmcid=dwList.get(arg2).getId();
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
	OnItemSelectedListener itemSelectedListenerXb=new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};
//	OnItemSelectedListener itemSelectedListenerDqs=new OnItemSelectedListener() {
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			dqsid=dqsList.get(arg2).getId();
//			searchDqS2Date();
//		}
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//		}
//	};
//	OnItemSelectedListener itemSelectedListenerDqs2=new OnItemSelectedListener() {
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			dqs2id=dqs2List.get(arg2).getId();
//			searchDqqDate();
//		}
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//		}
//	};
//	OnItemSelectedListener itemSelectedListenerDqq=new OnItemSelectedListener() {
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//		}
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//		}
//	};
//	OnItemSelectedListener itemSelectedListenerKhdj=new OnItemSelectedListener() {
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			khdjid=khdjList.get(arg2).getId();
//		}
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//		}
//	};
//	OnItemSelectedListener itemSelectedListenerHylx=new OnItemSelectedListener() {
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			hylxid=hylxList.get(arg2).getId();
//		}
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//		}
//	};
//	OnItemSelectedListener itemSelectedListenerKhly=new OnItemSelectedListener() {
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			khlyid=khlyList.get(arg2).getId();
//		}
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//		}
//	};
//	OnItemSelectedListener itemSelectedListenerKhlx=new OnItemSelectedListener() {
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			khlxid=khlxList.get(arg2).getId();
//		}
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//		}
//	};
	
	/**
	 * 验证用户输入的信息
	 * @return
	 */
	private boolean validateMsg(){
		if(lxrEditText.getText().toString().equals("")){
			showToastPromopt("联系人不能为空");
			return false;
		}else if(gh1EditText.getText().toString().equals("")&&gh2EditText.getText().toString().equals("")&&sj1EditText.getText().toString().equals("")&&sj2EditText.getText().toString().equals("")){
			showToastPromopt("固话和手机必须填写一个");
			return false;
		}
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	    if(resultCode==RESULT_OK){
	        if(requestCode==0){
	            dwmcEditText.setText(data.getExtras().getString("name"));
                dwmcid=data.getExtras().getString("id");
	        }else if(requestCode==1){
	            dwmcEditText.setText(data.getExtras().getString("name"));
	            dwmcid=data.getExtras().getString("id");
	        }
	        searchDwDate(dwmcid);
	    }
		super.onActivityResult(requestCode, resultCode, data);
	}
}