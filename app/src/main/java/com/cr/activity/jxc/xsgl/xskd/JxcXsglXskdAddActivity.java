package com.cr.activity.jxc.xsgl.xskd;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzyyActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddXzspActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddXzspDetailActivity;
import com.cr.activity.xm.XmActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.jxc.xsgl.xskd.JxcXsglXskdAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.project.ChoiceProjectActivity;
import android.text.TextUtils;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
/**
 * 进销存-销售管理-销售开单-增加
 *
 * @author Administrator
 */
public class JxcXsglXskdAddActivity extends BaseActivity implements OnClickListener {
    private ImageButton               saveImageButton;
    private ToggleButton              toggleButton;
    private TextView                  xzspnumTextView;
    private EditText                  bzxxEditText, gysEditText, lxrEditText, lxdhEditText,
            jhdzEditText, hjjeEditText, djrqEditText, jbrEditText,khqkEditText,ckckEditText,
            skjeEditText,sklxEditText,jsfsEditText,zjzhEditText,gys2EditText;
    private CustomListView            listview;
    String                            gysId="",gys2Id="", lxrId="", jbrId="", sklxId="", jsfsId="", zjzhId="",ckckId="";
    private List<Map<String, Object>> list;
    private List<Map<String, Object>> yyList=new ArrayList<Map<String,Object>>();
    private LinearLayout              xzspLinearLayout;
    BaseAdapter                       adapter;
    LinearLayout                      gldjcgLinearLayout,xzxsddLinearLayout;
    ScrollView                        addScrollView;
    private int                       selectIndex;
    String billid;//选择完关联的单据后返回的单据的ID
    
    private EditText ckEditText;
    private String ckId;
    private EditText xmEditText;
    private String xmId;

    private String mTypesname;// 单位类型
    private long time;
    private String mDepartmentid;//部门ID
    private EditText etBm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_xsgl_xskd_add);
        addFHMethod();
        initActivity();
        // searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
         etBm = (EditText) findViewById(R.id.et_bm);
        etBm.setOnClickListener(this);
    	xmEditText=(EditText) findViewById(R.id.xm_edittext);
    	xmEditText.setOnClickListener(this);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        ckckEditText=(EditText) findViewById(R.id.ckck_edittext);
        ckckEditText.setOnClickListener(this);
        ckEditText=(EditText) findViewById(R.id.ck_edittext);
        ckEditText.setOnClickListener(this);
        gysEditText = (EditText) findViewById(R.id.gys_edittext);
        gysEditText.setOnClickListener(this);
        gys2EditText = (EditText) findViewById(R.id.gys2_edittext);
        gys2EditText.setOnClickListener(this);
        lxrEditText = (EditText) findViewById(R.id.lxr_edittext);
        lxrEditText.setOnClickListener(this);
        lxdhEditText = (EditText) findViewById(R.id.lxdh_edittext);
        jhdzEditText = (EditText) findViewById(R.id.jhdz_edittext);
        jhdzEditText.setOnClickListener(this);
        listview = (CustomListView) findViewById(R.id.xzsp_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                Intent intent = new Intent();
                intent.setClass(activity, JxcCgglCgddXzspDetailActivity.class);
                intent.putExtra("object", (Serializable) list.get(arg2));
                intent.putExtra("xskd", true);
                startActivityForResult(intent, 4);
            }
        });
        hjjeEditText = (EditText) findViewById(R.id.hjje_edittext);
        djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
        djrqEditText.setOnClickListener(this);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        djrqEditText.setText(sdf.format(new Date()));
        jbrEditText = (EditText) findViewById(R.id.jbr_edittext);
        jbrEditText.setOnClickListener(this);
        bzxxEditText = (EditText) findViewById(R.id.bzxx_edittext);
        bzxxEditText.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				view.getParent().requestDisallowInterceptTouchEvent(true);
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_UP:
					view.getParent().requestDisallowInterceptTouchEvent(
							false);
					break;
				}
				return false;
			}
		});
        xzspnumTextView = (TextView) findViewById(R.id.xzspnum_textview);
        addScrollView = (ScrollView) findViewById(R.id.add_scrollview);
        gldjcgLinearLayout = (LinearLayout) findViewById(R.id.gldjcg_linearlayout);
        xzxsddLinearLayout=(LinearLayout) findViewById(R.id.xzxsdd_linearlayout);
        xzxsddLinearLayout.setOnClickListener(this);
        khqkEditText=(EditText) findViewById(R.id.khqk_edittext);
        skjeEditText=(EditText) findViewById(R.id.skje_edittext);
        sklxEditText=(EditText) findViewById(R.id.sklx_edittext);
        sklxEditText.setOnClickListener(this);
        jsfsEditText=(EditText) findViewById(R.id.jsfs_edittext);
        jsfsEditText.setOnClickListener(this);
        zjzhEditText=(EditText) findViewById(R.id.zjzh_edittext);
        zjzhEditText.setOnClickListener(this);
        
        toggleButton = (ToggleButton) findViewById(R.id.mTogBtn);
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    addScrollView.setVisibility(View.GONE);
                    gldjcgLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    addScrollView.setVisibility(View.VISIBLE);
                    gldjcgLinearLayout.setVisibility(View.GONE);
                }
            }
        });
        xzspLinearLayout = (LinearLayout) findViewById(R.id.xzsp_linearlayout);
        xzspLinearLayout.setOnClickListener(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new JxcXsglXskdAddAdapter(list, this);
        xzspnumTextView.setText("共选择了" + list.size() + "商品");
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        khqkEditText.setEnabled(false);
    }
//    /**
//     * 连接网络的操作(查询主表的内容)
//     */
//    private void searchDate() {
//        Map<String, Object> parmMap = new HashMap<String, Object>();
//        parmMap.put("dbname", ShareUserInfo.getDbName(context));
//        parmMap.put("parms", "XSKD");
//        parmMap.put("billid", billid);
//        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false);
//    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "XSKD");
        parmMap.put("billid", billid);
        findServiceData2(2, ServerURL.BILLDETAIL, parmMap, false);
    }
    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
         if(ckckEditText.getText().toString().equals("")){
            showToastPromopt("请选择出库仓库");
            return;
        }else if(gysEditText.getText().toString().equals("")){
            showToastPromopt("请选择客户");
            return;
        }else if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        }else if(djrqEditText.getText().toString().equals("")){
            showToastPromopt("请选择单据日期");
            return;
        }else if(sklxEditText.getText().toString().equals("")){
            showToastPromopt("请选择收款类型");
            return;
        }else if(sklxId.equals("1")){
            double hjje=Double.parseDouble(hjjeEditText.getText().toString().replace("￥", "").equals("")?"0":hjjeEditText.getText().toString().replace("￥", ""));
            double fkje=Double.parseDouble(skjeEditText.getText().toString().replace("￥", "").equals("")?"0":skjeEditText.getText().toString().replace("￥", ""));
            if(fkje<=0||fkje>hjje){
                showToastPromopt("收款金额不在范围内！");
                return;
            }
//            else if(jsfsEditText.getText().toString().equals("")){
//                showToastPromopt("请选择结算方式");
//                return;
//            }
                else if(zjzhEditText.getText().toString().equals("")){
                showToastPromopt("请选择资金账户");
                return;
            }
        } if (jbrEditText.getText().toString().equals("")) {
            showToastPromopt("请选择业务员");
            return;
        }
        JSONArray arrayMaster = new JSONArray();
        JSONArray arrayDetail = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid", "0");
            jsonObject.put("code", "");
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("storeid", ckckId);
            jsonObject.put("ispc", sklxId);
            jsonObject.put("clientid", gysId);//供应商ID
            jsonObject.put("linkmanid", lxrId);//联系人ID
            jsonObject.put("phone", lxdhEditText.getText().toString());
            jsonObject.put("receipt",skjeEditText.getText().toString());
            jsonObject.put("privilege","" );
            jsonObject.put("totalamt",hjjeEditText.getText().toString().replace("￥", "") );
            jsonObject.put("paytypeid", jsfsId);
            jsonObject.put("bankid",zjzhId );
            jsonObject.put("shipto", jhdzEditText.getText().toString());
             jsonObject.put("departmentid", mDepartmentid);
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("opid", ShareUserInfo.getUserId(context));
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("projectid", xmId);
            arrayMaster.put(jsonObject);
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");
                jsonObject2.put("itemno", "0");
                jsonObject2.put("goodsid", map.get("goodsid").toString());
                jsonObject2.put("unitid", map.get("unitid").toString());
                jsonObject2.put("unitprice", map.get("unitprice").toString());
                jsonObject2.put("unitqty", map.get("unitqty").toString());
                String disc=map.get("disc").toString();
                jsonObject2.put("disc", disc);
                jsonObject2.put("amount", map.get("amount").toString());
                jsonObject2.put("batchcode", map.get("batchcode").toString());
                jsonObject2.put("produceddate", map.get("produceddate").toString());
                jsonObject2.put("validdate", map.get("validdate").toString());
                jsonObject2.put("ispresent", "");
                jsonObject2.put("refertype", map.get("refertype")==null?"":map.get("refertype").toString());
                jsonObject2.put("batchrefid",  map.get("batchrefid")==null?"":map.get("batchrefid").toString());
                jsonObject2.put("referbillid ", map.get("referbillid")==null?"":map.get("referbillid").toString());
                jsonObject2.put("referitemno ", map.get("referitemno")==null?"":map.get("referitemno").toString());
                jsonObject2.put("memo", map.get("memo")==null?"":map.get("memo").toString());
                arrayDetail.put(jsonObject2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_invoice");
        parmMap.put("parms", "XSKD");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        findServiceData2(0, "billsavenew", parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson.equals("")) {
                showToastPromopt("保存成功");
                setResult(RESULT_OK);
                finish();
            } else {
                showToastPromopt("保存失败" + returnJson.substring(5));
            }
        }else if(returnSuccessType==1){//管理单据成功后把信息填到里面（主表）
            if (returnJson.equals("")) {
                return;
            }
            Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
                .paseJsonToObject(returnJson)).get(0);
            gysEditText.setText(object.get("cname").toString());
            lxrEditText.setText(object.get("lxrname").toString());
            lxdhEditText.setText(object.get("phone").toString());
            jhdzEditText.setText(object.get("billto").toString());
            hjjeEditText.setText(object.get("amount").toString());
            djrqEditText.setText(object.get("billdate").toString());
            jbrEditText.setText(object.get("empname").toString());
            bzxxEditText.setText(object.get("memo").toString());
            gysId = object.get("clientid").toString();
            lxrId = object.get("lxrid").toString();
            jbrId = object.get("empid").toString();
            searchDate2();//查询订单中的商品
        }else if(returnSuccessType==2){//管理单据成功后把信息填到里面（从表）
            list = (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
            adapter = new JxcCgglCgddDetailAdapter(list, this);
            xzspnumTextView.setText("共选择了" + list.size() + "商品");
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.xzsp_linearlayout:
            	if(ckckEditText.getText().toString().equals("")){
            		showToastPromopt("请先选择仓库信息！");
            		return;
            	}
            	intent.putExtra("rkckId", ckckId);
            	intent.putExtra("tabname", "tb_invoice");
                intent.putExtra("xskd", true);
                intent.setClass(this, JxcCgglCgddXzspActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.gys_edittext://单位选择
                intent.setClass(this, CommonXzkhActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.gys2_edittext:
                intent.setClass(this, CommonXzkhActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.lxr_edittext:
                if (gysId.equals("")) {
                    showToastPromopt("请先选择客户信息");
                    return;
                }
                intent.setClass(activity, CommonXzlxrActivity.class);
                intent.putExtra("clientid", gysId);
                startActivityForResult(intent, 2);
                break;
            case R.id.djrq_edittext:
                date_init(djrqEditText);
                break;
           case R.id.et_bm:
                startActivityForResult(new Intent(this, com.update.actiity.choose.ChooseDepartmentActivity.class), 15);
                break;
            case R.id.jbr_edittext:
                if (TextUtils.isEmpty(mDepartmentid))
                    showToastPromopt("请先选择部门");
                else
                    startActivityForResult(new Intent(this, SelectSalesmanActivity.class)
                            .putExtra("depid", mDepartmentid), 16);
//                intent.setClass(activity, CommonXzjbrActivity.class);
//                startActivityForResult(intent, 3);
                break;
            case R.id.save_imagebutton:
                if(time==0||System.currentTimeMillis()-time>5000) {
                    searchDateSave();//保存
                    time=System.currentTimeMillis();
                }else {
                    showToastPromopt("请不要频繁点击，防止重复保存");

                }
                break;
            case R.id.xzxsdd_linearlayout://选择销售订单
            	intent.putExtra("type", "XSKD_XSDD");
            	if(gys2EditText.getText().toString().equals("")){
            		showToastPromopt("请先选择客户");
            		return;
            	}
            	if(ckEditText.getText().toString().equals("")){
            		showToastPromopt("请先选择仓库");
            		return;
            	}
            	intent.putExtra("clientid", gys2Id);
            	intent.putExtra("reftypeid", "1");
                intent.setClass(activity, CommonXzyyActivity.class);
                startActivityForResult(intent,5);
                break;
            case R.id.ckck_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "STORE");
                startActivityForResult(intent, 6);
                break;
            case R.id.sklx_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "FKLX");
                startActivityForResult(intent, 7);
                break;
            case R.id.jsfs_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "PAYTYPE");
                startActivityForResult(intent, 8);
                break;
            case R.id.zjzh_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "BANK");
                startActivityForResult(intent, 9);
                break;
            case R.id.ck_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "STORE");
                startActivityForResult(intent, 11);
                break;
            case R.id.xm_edittext:
            	if(gysId.equals("")){
            		showToastPromopt("请先选择客户！");
            		return;
            	}
                startActivityForResult(new Intent(this, ChoiceProjectActivity.class)
                                .putExtra("clientid", gysId)
                                .putExtra("clientname", gysEditText.getText().toString())
                                .putExtra("typesname", mTypesname),
                        12);
//            	intent.setClass(activity, XmActivity.class);
//                intent.putExtra("clientid", gysId);
//                startActivityForResult(intent, 12);
            	break;
                
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {// 选择商品
//                list.clear();
                List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
                    .getSerializableExtra("object");
                double zje = 0;
                for (int i = 0; i < cpList.size(); i++) {
                    Map<String, Object> map = cpList.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = cpList.get(i + 1);
                            map.put("unitprice", map2.get("dj"));
                            map.put("unitqty", map2.get("sl"));
                            map.put("amount", Double.parseDouble(map2.get("dj").toString())
                                              * Double.parseDouble(map2.get("sl").toString()));
                            map.put("disc", map2.get("zkl"));
                            map.put("batchcode", map2.get("cpph"));
                            map.put("produceddate", map2.get("scrq"));
                            map.put("validdate", map2.get("yxqz"));
                            map.put("memo", map2.get("memo"));//备注
                            list.add(map);
//                            zje += Double.parseDouble(map.get("amount").toString());
                        }
                    }
                }
                for(Map<String, Object> m:list){
                    zje += Double.parseDouble(m.get("amount").toString());
                }
                xzspnumTextView.setText("共选择了" + list.size() + "商品");
                hjjeEditText.setText("￥" + FigureTools.sswrFigure(zje+"") + "");
                adapter.notifyDataSetChanged();
            } else if (requestCode == 1) {
            	if(!gysEditText.getText().toString().equals("")){
            		if(!gysEditText.getText().toString().equals(data.getExtras().getString("name"))){
            			list.removeAll(yyList);
            			adapter.notifyDataSetChanged();
            		}
            	}
            	 if(!gysEditText.getText().toString().equals(data.getExtras().getString("name"))){
                 	lxrEditText.setText("");
                 	lxrId="";
                 	lxdhEditText.setText("");		
                 	gysEditText.setText(data.getExtras().getString("name"));
                 	gysId = data.getExtras().getString("id");
                 }
                gysEditText.setText(data.getExtras().getString("name"));
                gysId = data.getExtras().getString("id");
                mTypesname = data.getStringExtra("typesname");
                khqkEditText.setText(data.getExtras().getString("qk"));
                // 清楚項目
                xmEditText.setText("");
                xmId="";
                xmEditText.setText("");
                xmId="";
            } else if (requestCode == 2) {// 联系人
                lxrEditText.setText(data.getExtras().getString("name"));
                lxrId = data.getExtras().getString("id");
                lxdhEditText.setText(data.getExtras().getString("phone"));
            } else if (requestCode == 3) {// 经办人
                jbrEditText.setText(data.getExtras().getString("name"));
                jbrId = data.getExtras().getString("id");
            } else if (requestCode == 4) {//修改选中的商品的详情
                if (data.getExtras().getSerializable("object").toString().equals("")) {//说明删除了
                    list.remove(selectIndex);
                    adapter.notifyDataSetChanged();
                } else {
                    Map<String, Object> map = (Map<String, Object>) data.getExtras()
                        .getSerializable("object");
                    list.remove(selectIndex);
                    map.put(
                        "amount",
                        map.put("amount", Double.parseDouble(map.get("unitprice").toString())
                                          * Double.parseDouble(map.get("unitqty").toString())));
                    list.add(selectIndex, map);
                    adapter.notifyDataSetChanged();
                }
                xzspnumTextView.setText("共选择了" + list.size() + "商品");
                double zje = 0;
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = list.get(i);
                    zje += Double.parseDouble(map.get("amount").toString());
                }
                hjjeEditText.setText("￥" + FigureTools.sswrFigure(zje+"") + "");
            }else if(requestCode==5){//选中单据成功后返回
            	addScrollView.setVisibility(View.VISIBLE);//隐藏关联销售单据的Linearlayout
                gldjcgLinearLayout.setVisibility(View.GONE);//显示展示详情的Linearlayout信息
                toggleButton.setChecked(false);
                list.clear();
                list.addAll((List<Map<String, Object>>) data.getExtras().getSerializable("list"));
                yyList.clear();
                yyList.addAll((List<Map<String, Object>>) data.getExtras().getSerializable("list"));
                xzspnumTextView.setText("共选择了" + list.size() + "商品");
                hjjeEditText.setText(data.getExtras().getString("totalAmount"));
                adapter.notifyDataSetChanged();
            }else if (requestCode == 6) {
            	if(!ckckEditText.getText().toString().equals(data.getExtras().getString("dictmc"))){
            		list.clear();
            		adapter.notifyDataSetChanged();
            	}
                ckckEditText.setText(data.getExtras().getString("dictmc"));
                ckckId = data.getExtras().getString("id");
            }else if (requestCode == 7) {
                sklxEditText.setText(data.getExtras().getString("dictmc"));
                sklxId = data.getExtras().getString("id");
                if(data.getExtras().getString("id").equals("0")){
                	skjeEditText.setText("0");
                	skjeEditText.setEnabled(false);
                	jsfsEditText.setEnabled(false);
                	zjzhEditText.setEnabled(false);
                	jsfsEditText.setText("");
                	zjzhEditText.setText("");
                	jsfsId="";
                	zjzhId="";
                }else{
                	skjeEditText.setEnabled(true);
                	jsfsEditText.setEnabled(true);
                	zjzhEditText.setEnabled(true);
                }
            }else if (requestCode == 8) {
                jsfsEditText.setText(data.getExtras().getString("dictmc"));
                jsfsId = data.getExtras().getString("id");
            }else if (requestCode == 9) {
                zjzhEditText.setText(data.getExtras().getString("dictmc"));
                zjzhId = data.getExtras().getString("id");
            }else if (requestCode == 10) {
                gys2EditText.setText(data.getExtras().getString("name"));
                gys2Id = data.getExtras().getString("id");
                khqkEditText.setText(data.getExtras().getString("qk"));
                gysEditText.setText(data.getExtras().getString("name"));
                gysId = data.getExtras().getString("id");
                mTypesname = data.getStringExtra("typesname");

            } else if(requestCode==11){
            	ckckEditText.setText(data.getExtras().getString("dictmc"));
            	ckckId=data.getExtras().getString("id");
            	ckEditText.setText(data.getExtras().getString("dictmc"));
            	ckId=data.getExtras().getString("id");
            }else if(requestCode==12){
            	xmEditText.setText(data.getStringExtra("title"));
            	xmId=data.getStringExtra("projectid");
            }else if(requestCode==15){
                mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                etBm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                jbrId = "";
                jbrEditText.setText("");
            }else if(requestCode==16){
                jbrEditText.setText(data.getExtras().getString("CHOICE_RESULT_TEXT"));
                jbrId = data.getExtras().getString("CHOICE_RESULT_ID");
            }
        }
    }
}