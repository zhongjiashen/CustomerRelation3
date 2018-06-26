package com.cr.activity.jxc.xsgl.xssk;

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
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzsklxActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.cggl.cgfk.JxcCgglCgfkActivity;
import com.cr.activity.jxc.cggl.cgfk.JxcCgglCgfkXzyyActivity;
import com.cr.activity.jxc.cggl.cgfk.JxcCgglCgfkXzyyDetailActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.jxc.cggl.cgfk.JxcCgglCgfkAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-销售管理-销售收款-增加
 * 
 * @author Administrator
 * 
 */
public class JxcXsglXsskAddActivity extends BaseActivity implements OnClickListener {
    private ImageButton               saveImageButton;
    private TextView                  xzspnumTextView;
    private EditText                  bzxxEditText, gysEditText,
            jhdzEditText, djrqEditText, jbrEditText,rkckEditText,
            fkjeEditText,fklxEditText,jsfsEditText,zjzhEditText,dqyfEditText,dqyf2EditText,
            cyfjeEditText,sfjeEditText;
    private CustomListView            listview;
    String                            gysId="", lxrId, jbrId,rkckId, fklxId, jsfsId, zjzhId;
    private List<Map<String, Object>> list;
    private LinearLayout              xzspLinearLayout,showYydLinearLayout;
    BaseAdapter                       adapter;
    LinearLayout                      gldjcgLinearLayout,xzxsddLinearLayout;
    ScrollView                        addScrollView;
    private int                       selectIndex;
    String billid;//选择完关联的单据后返回的单据的ID
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_xsgl_xssk_add);
        addFHMethod();
        initActivity();
        // searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        gysEditText = (EditText) findViewById(R.id.gys_edittext);
        gysEditText.setOnClickListener(this);
        dqyfEditText=(EditText) findViewById(R.id.dqyf_edittext);
        dqyf2EditText=(EditText) findViewById(R.id.dqyf2_edittext);
        cyfjeEditText=(EditText) findViewById(R.id.cyfje_edittext);
        sfjeEditText=(EditText) findViewById(R.id.sfje_edittext);
        listview = (CustomListView) findViewById(R.id.xzsp_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                Intent intent = new Intent();
                intent.setClass(activity, JxcCgglCgfkXzyyDetailActivity.class);
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });
//        hjjeEditText = (EditText) findViewById(R.id.hjje_edittext);
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
//        xzxsddLinearLayout=(LinearLayout) findViewById(R.id.xzxsdd_linearlayout);
//        xzxsddLinearLayout.setOnClickListener(this);
//        gysqkEditText=(EditText) findViewById(R.id.gysqk_edittext);
        fkjeEditText=(EditText) findViewById(R.id.fkje_edittext);
        fklxEditText=(EditText) findViewById(R.id.fklx_edittext);
        fklxEditText.setOnClickListener(this);
        jsfsEditText=(EditText) findViewById(R.id.jsfs_edittext);
        jsfsEditText.setOnClickListener(this);
        zjzhEditText=(EditText) findViewById(R.id.zjzh_edittext);
        zjzhEditText.setOnClickListener(this);
        
        xzspLinearLayout = (LinearLayout) findViewById(R.id.xzyydh_linearlayout);
        xzspLinearLayout.setOnClickListener(this);
        showYydLinearLayout = (LinearLayout) findViewById(R.id.show_yyd_layout);
        xzspLinearLayout.setOnClickListener(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new JxcCgglCgfkAddAdapter(list, this);
        xzspnumTextView.setText("共选择了" + list.size() + "引用");
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "XSSK");
        parmMap.put("billid", billid);
        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false);
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "XSSK");
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
        }else if (fklxEditText.getText().toString().equals("")) {
            showToastPromopt("请选择收款类型");
            return;
        }
//        else if(jsfsEditText.getText().toString().equals("")){
//            showToastPromopt("请选择结算方式");
//            return;
//        }
        else if(zjzhEditText.getText().toString().equals("")){
            showToastPromopt("请选择资金账户");
            return;
        }else if(fklxId.equals("1")){
            if(fkjeEditText.getText().toString().equals("")){
                showToastPromopt("请填写收款金额");
                return;
            }
        }
        if (!fklxId.equals("1")) {//不选择选择了当前预收
            if (list.size() == 0) {
                showToastPromopt("请选择单据引用");
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
//            jsonObject.put("storeid", rkckId);
            jsonObject.put("ispc", fklxId);
            jsonObject.put("paytypeid", jsfsId);
            jsonObject.put("bankid",zjzhId );
            jsonObject.put("clientid", gysId);//供应商ID
            jsonObject.put("amount", fkjeEditText.getText().toString());
            jsonObject.put("factamount", fkjeEditText.getText().toString());
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(context));
            arrayMaster.put(jsonObject);
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");
                jsonObject2.put("itemno", "0");
                jsonObject2.put("refertype", map.get("billtypeid").toString());
                jsonObject2.put("referbillid ", map.get("billid").toString());
                jsonObject2.put("amount ",map.get("bcjs")==null?map.get("amount").toString():map.get("bcjs").toString());
                arrayDetail.put(jsonObject2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_charge");
        parmMap.put("parms", "XSSK");
        parmMap.put("master", arrayMaster.toString());
        if(list.size()==0){
            parmMap.put("detail", "");
        }else{
            parmMap.put("detail", arrayDetail.toString());
        }
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
//            lxrEditText.setText(object.get("lxrname").toString());
//            lxdhEditText.setText(object.get("phone").toString());
            jhdzEditText.setText(object.get("billto").toString());
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
            xzspnumTextView.setText("共选择了" + list.size() + "商品");
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.xzyydh_linearlayout:
            	if(gysId.equals("")){
            		showToastPromopt("请选择客户");
            		return;
            	}
            	intent.putExtra("type", "XSSK_BILL");
            	intent.putExtra("clientid", gysId);
                intent.setClass(this, JxcCgglCgfkXzyyActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.gys_edittext:
                intent.setClass(this, CommonXzkhActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.lxr_edittext:
                if (gysId.equals("")) {
                    showToastPromopt("请先选择联系人信息");
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
                if(time==0||System.currentTimeMillis()-time>5000) {
                    searchDateSave();//保存
                    time=System.currentTimeMillis();
                }else {
                    showToastPromopt("请不要频繁点击，防止重复保存");

                }
                break;
            case R.id.xzxsdd_linearlayout://选择销售订单
                intent.setClass(activity, JxcCgglCgfkActivity.class);
                intent.putExtra("select", "true");
                startActivityForResult(intent,5);
                break;
            case R.id.rkck_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "STORE");
                startActivityForResult(intent, 6);
                break;
            case R.id.fklx_edittext:
                intent.setClass(activity, CommonXzsklxActivity.class);
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
                
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {// 选择引用
//                list.clear();
                List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
                    .getSerializableExtra("object");
               list.addAll(cpList);
                xzspnumTextView.setText("共选择了" + list.size() + "引用");
                double zje = 0;
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = list.get(i);
                    zje += Double.parseDouble(map.get("bcjs").toString());
                }
                fkjeEditText.setText( FigureTools.sswrFigure(zje+"") + "");
                fkjeEditText.setEnabled(false);
                adapter.notifyDataSetChanged();
            } else if (requestCode == 1) {
            	if(!gysEditText.getText().toString().equals("")){
            		if(!gysEditText.getText().toString().equals(data.getExtras().getString("name"))){
            			list.clear();
            			adapter.notifyDataSetChanged();
            		}
            	}
                gysEditText.setText(data.getExtras().getString("name"));
                gysId = data.getExtras().getString("id");
                dqyfEditText.setText(data.getExtras().getString("yf"));
                dqyf2EditText.setText(data.getExtras().getString("yf2"));
            } else if (requestCode == 2) {// 联系人
//                lxrEditText.setText(data.getExtras().getString("name"));
//                lxrId = data.getExtras().getString("id");
            } else if (requestCode == 3) {// 经办人
                jbrEditText.setText(data.getExtras().getString("name"));
                jbrId = data.getExtras().getString("id");
            } else if (requestCode == 4) {//修改选中的引用的详情
                if (data.getExtras().getSerializable("object").toString().equals("")) {//说明删除了
                    list.remove(selectIndex);
                    adapter.notifyDataSetChanged();
                } else {
                    Map<String, Object> map = (Map<String, Object>) data.getExtras()
                        .getSerializable("object");
                    list.remove(selectIndex);
                        map.put("amount", map.get("bcjs").toString());
                    list.add(selectIndex, map);
                    adapter.notifyDataSetChanged();
                }
                xzspnumTextView.setText("共选择了" + list.size() + "引用");
                double zje = 0;
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = list.get(i);
                    zje += Double.parseDouble(map.get("bcjs").toString());
                }
                fkjeEditText.setText( FigureTools.sswrFigure(zje+"") + "");
                fkjeEditText.setEnabled(false);
            }else if(requestCode==5){//选中单据成功后返回
                addScrollView.setVisibility(View.VISIBLE);//隐藏关联销售单据的Linearlayout
                gldjcgLinearLayout.setVisibility(View.GONE);//显示展示详情的Linearlayout信息
                billid=data.getExtras().getString("billid");
                searchDate();//查询主表的数据填充
            }else if (requestCode == 6) {
                rkckEditText.setText(data.getExtras().getString("dictmc"));
                rkckId = data.getExtras().getString("id");
            }else if (requestCode == 7) {
                fklxEditText.setText(data.getExtras().getString("name"));
                fklxId = data.getExtras().getString("id");
                if (data.getExtras().getString("id").equals("1")) {//选择了当前预收
                    fkjeEditText.setEnabled(true);
                    list.clear();
                    adapter.notifyDataSetChanged();
//                    xzspLinearLayout.setEnabled(false);
                    showYydLinearLayout.setVisibility(View.GONE);
                } else {
                    fkjeEditText.setEnabled(false);
//                    xzspLinearLayout.setEnabled(true);
                    showYydLinearLayout.setVisibility(View.VISIBLE);
                }
            }else if (requestCode == 8) {
                jsfsEditText.setText(data.getExtras().getString("dictmc"));
                jsfsId = data.getExtras().getString("id");
            }else if (requestCode == 9) {
                zjzhEditText.setText(data.getExtras().getString("dictmc"));
                zjzhId = data.getExtras().getString("id");
            }
        }
    }
}