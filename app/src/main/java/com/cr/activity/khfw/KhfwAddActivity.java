package com.cr.activity.khfw;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzjhActivity;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.khfw.KhfwAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 客户服务-增加
 * 
 * @author Administrator
 * 
 */
public class KhfwAddActivity extends BaseActivity implements OnClickListener {
    private ImageButton               saveImageButton;
    private EditText                   gysEditText, lxrEditText, lxdhEditText,
            djlxEditText,jhmcEditText,sqrEditText,ksrqEditText,fwfsEditText, djrqEditText, jbrEditText,bzxxEditText;
    private CustomListView            listview;
    String                            gysId="", lxrId="", jbrId="",djlxId="",jhmcId="",fwfsId="";
    private List<Map<String, Object>> list;
    private LinearLayout              xzspLinearLayout;
    BaseAdapter                       adapter;
    private int                       selectIndex;
    private ScrollView  scrollView;
    String billid;//选择完关联的单据后返回的单据的ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khfw_add);
        addFHMethod();
        initActivity();
        // searchDate();
    }

    /**
     * 初始化Activity
     */
    @SuppressWarnings("unchecked")
	private void initActivity() {
        scrollView=(ScrollView) findViewById(R.id.add_scrollview);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        gysEditText = (EditText) findViewById(R.id.gys_edittext);
        gysEditText.setOnClickListener(this);
        if(this.getIntent().hasExtra("dwObject")){
        	Map<String, Object> map=(Map<String, Object>) this.getIntent().getExtras().getSerializable("dwObject");
        	gysEditText.setText(map.get("cname").toString());
        	gysId=map.get("id").toString();
        }
        lxrEditText = (EditText) findViewById(R.id.lxr_edittext);
        lxrEditText.setOnClickListener(this);
        lxdhEditText = (EditText) findViewById(R.id.lxdh_edittext);
        djlxEditText = (EditText) findViewById(R.id.djlx_edittext);
        djlxEditText.setOnClickListener(this);
        jhmcEditText = (EditText) findViewById(R.id.jhmc_edittext);
        jhmcEditText.setOnClickListener(this);
        sqrEditText = (EditText) findViewById(R.id.sqr_edittext);
        ksrqEditText = (EditText) findViewById(R.id.ksrq_edittext);
        ksrqEditText.setOnClickListener(this);
        fwfsEditText = (EditText) findViewById(R.id.fwfs_edittext);
        fwfsEditText.setOnClickListener(this);
        listview = (CustomListView) findViewById(R.id.xzsp_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                Intent intent = new Intent();
                intent.setClass(activity, KhfwAddBjfwcpActivity.class);
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });
//        hjjeEditText = (EditText) findViewById(R.id.hjje_edittext);
        djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
        djrqEditText.setOnClickListener(this);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        djrqEditText.setText(sdf.format(new Date()));
        ksrqEditText.setText(sdf.format(new Date()));
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
        xzspLinearLayout = (LinearLayout) findViewById(R.id.xzsp_linearlayout);
        xzspLinearLayout.setOnClickListener(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new KhfwAddAdapter(list, this);
//        xzspnumTextView.setText("共选择了" + list.size() + "商品");
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        scrollView.post(new Runnable() { //滑动到最上端
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }
    /**
     * 连接网络的操作(查询主表的内容)
     */
//    private void searchDate() {
//        Map<String, Object> parmMap = new HashMap<String, Object>();
//        parmMap.put("dbname", ShareUserInfo.getDbName(context));
//        parmMap.put("parms", "CGDD");
//        parmMap.put("billid", billid);
//        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false);
//    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "CGDD");
        parmMap.put("billid", billid);
        findServiceData2(2, ServerURL.BILLDETAIL, parmMap, false);
    }
    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (gysEditText.getText().toString().equals("")) {
            showToastPromopt("请选择客户");
            return;
        }else if (djlxEditText.getText().toString().equals("")) {
            showToastPromopt("请选单据类型");
            return;
        }else if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
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
            jsonObject.put("billtypeid", djlxId);
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("clientid", gysId);//供应商ID
            jsonObject.put("lxrid", lxrId);//联系人ID
            jsonObject.put("chanceid",jhmcId);//联系人ID
            jsonObject.put("bsrq",ksrqEditText.getText().toString());
            jsonObject.put("bxr", sqrEditText.getText().toString());
            jsonObject.put("phone", lxdhEditText.getText().toString());
            jsonObject.put("sxfs", fwfsEditText.getText().toString());
            jsonObject.put("empid", jbrId);
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(context));
            
            double amount=0;
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");
                jsonObject2.put("goodsid", map.get("cpmcid").toString());
                jsonObject2.put("unitid", map.get("unitid").toString());
                jsonObject2.put("unitqty", map.get("sl").toString());
                jsonObject2.put("amount", map.get("fwfy").toString());
                if(!map.get("fwfy").toString().equals("")){
                	amount+=Double.parseDouble(map.get("fwfy").toString());
                }
                jsonObject2.put("shcd", map.get("shcdid").toString());
                jsonObject2.put("pjxh", map.get("pj").toString());
                jsonObject2.put("gzms", map.get("fwnr").toString());
                jsonObject2.put("fyyy", map.get("yy").toString());//原因
                jsonObject2.put("jjfa", map.get("jjfa").toString());
                jsonObject2.put("wxjg", map.get("fwjd").toString());//服务进度
                jsonObject2.put("wxry", map.get("fwryid").toString());
                jsonObject2.put("wxdw", map.get("wxdw").toString());
                jsonObject2.put("wcrq", map.get("wcrq").toString());//完成日期
                jsonObject2.put("wxjd", map.get("fwztid").toString());//服务状态
                jsonObject2.put("mycd", map.get("mydid").toString());//满意程度
                jsonObject2.put("zbzt", map.get("sfbzqn").toString());
                jsonObject2.put("wxdw", map.get("wxdw").toString());
                jsonObject2.put("memo ", map.get("bz").toString());//备注信息
                arrayDetail.put(jsonObject2);
            }
            jsonObject.put("amount", amount+"");
            arrayMaster.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
//        parmMap.put("tabname", "tb_porder");
        parmMap.put("parms", "KHFW");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        findServiceData2(0, ServerURL.BILLSAVE, parmMap, false);
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
//            jhdzEditText.setText(object.get("billto").toString());
//            hjjeEditText.setText(object.get("amount").toString());
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
//            xzspnumTextView.setText("共选择了" + list.size() + "商品");
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.xzsp_linearlayout:
                intent.setClass(this, KhfwAddXzfwcpActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.gys_edittext:
                intent.setClass(this, CommonXzkhActivity.class);
                startActivityForResult(intent, 1);
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
            case R.id.jbr_edittext:
                intent.setClass(activity, CommonXzjbrActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.save_imagebutton:
                searchDateSave();//保存
                break;
            case R.id.xzxsdd_linearlayout://选择销售订单
                intent.setClass(activity, KhfwActivity.class);
                intent.putExtra("select", "true");
                startActivityForResult(intent,5);
                break;
            case R.id.djlx_edittext:
                intent.putExtra("type","SHWX");
                intent.setClass(activity, CommonXzzdActivity.class);
                startActivityForResult(intent, 6);
                break;
            case R.id.jhmc_edittext:
                intent.setClass(activity, CommonXzjhActivity.class);
                intent.putExtra("clientid", gysId);
                startActivityForResult(intent, 7);
                break;
            case R.id.ksrq_edittext:
                date_init(ksrqEditText);
                break;
            case R.id.fwfs_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "FWFS");
                startActivityForResult(intent, 8);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {// 新增服务产品
                Map<String, Object> map=(Map<String, Object>) data.getExtras().getSerializable("object");
                list.add(map);
                adapter.notifyDataSetChanged();
            } else if (requestCode == 1) {
                gysEditText.setText(data.getExtras().getString("name"));
                gysId = data.getExtras().getString("id");
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
                    list.add(selectIndex, map);
                    adapter.notifyDataSetChanged();
                }
            }else if(requestCode==5){//选中单据成功后返回
                
            }else if(requestCode==6){
                djlxEditText.setText(data.getExtras().getString("dictmc"));
                djlxId = data.getExtras().getString("id");
            }else if(requestCode==7){
                jhmcEditText.setText(data.getExtras().getString("name"));
                jhmcId = data.getExtras().getString("id");
            }else if(requestCode==8){
                fwfsEditText.setText(data.getExtras().getString("dictmc"));
                fwfsId = data.getExtras().getString("id");
            }
        }
    }
}