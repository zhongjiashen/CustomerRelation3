package com.cr.activity.jxc.ckgl.zzcx;

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
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdjlxActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.jxc.ckgl.zzcx.JxcCkglZzcxAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-仓库管理-组装拆卸-增加
 * 
 * @author Administrator
 * 
 */
public class JxcCkglZzcxAddActivity extends BaseActivity implements OnClickListener {
    private ImageButton               saveImageButton;
    private TextView                  xzspnumTextView;
    private EditText                  djlxEditText,ckEditText,bzxxEditText, djrqEditText, jbrEditText;
    private CustomListView            listview;
    String                            jbrId,djlxId,ckId;
    private List<Map<String, Object>> list;
    private TextView                  xzspnumTextView2;
    private CustomListView            listview2;
    private List<Map<String, Object>> list2;
    private LinearLayout              xzspLinearLayout2;
    BaseAdapter                       adapter2;
    private LinearLayout              xzspLinearLayout;
    BaseAdapter                       adapter;
//    LinearLayout                      xzxsddLinearLayout;
    private int                       selectIndex;
    private int                       selectIndex2;
    String billid;//选择完关联的单据后返回的单据的ID
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_zzcx_add);
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
        listview = (CustomListView) findViewById(R.id.xzsp_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                Intent intent = new Intent();
                intent.setClass(activity, JxcCkglZzcxXzspDetailActivity.class);
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });
        listview2 = (CustomListView) findViewById(R.id.xzsp2_listview);
        listview2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex2 = arg2;
                Intent intent = new Intent();
                intent.setClass(activity, JxcCkglZzcxXzspDetailActivity.class);
                intent.putExtra("object", (Serializable) list2.get(arg2));
                startActivityForResult(intent, 10);
            }
        });
        djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
        djrqEditText.setOnClickListener(this);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        djrqEditText.setText(sdf.format(new Date()));
        jbrEditText = (EditText) findViewById(R.id.jbr_edittext);
        jbrEditText.setOnClickListener(this);
        djlxEditText = (EditText) findViewById(R.id.djlx_edittext);
        djlxEditText.setOnClickListener(this);
        djlxId="T";
        djlxEditText.setText("组装单");
        ckEditText = (EditText) findViewById(R.id.ck_edittext);
        ckEditText.setOnClickListener(this);
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
        xzspLinearLayout = (LinearLayout) findViewById(R.id.xzsp_linearlayout);
        xzspLinearLayout.setOnClickListener(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new JxcCkglZzcxAddAdapter(list, this);
        xzspnumTextView.setText("共选择了" + list.size() + "商品");
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        
        xzspnumTextView2 = (TextView) findViewById(R.id.xzspnum2_textview);
        xzspLinearLayout2 = (LinearLayout) findViewById(R.id.xzsp2_linearlayout);
        xzspLinearLayout2.setOnClickListener(this);
        list2 = new ArrayList<Map<String, Object>>();
        adapter2 = new JxcCkglZzcxAddAdapter(list2, this);
        xzspnumTextView2.setText("共选择了" + list2.size() + "商品");
        listview2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "CGDD");
        parmMap.put("billid", billid);
        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false);
    }

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
     * 连接网络的操作（查询从表的内容(根据选定的主表，查询出已经选择的从表的内容)）
     */
    private void searchDate3(String goodsid) {
        list2.clear();
        adapter2.notifyDataSetChanged();
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("goodsid", goodsid);
        findServiceData2(2, ServerURL.SELECTGOODSCMB, parmMap, false);
    }
    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        }else if(djrqEditText.getText().toString().equals("")){
            showToastPromopt("请选择单据日期");
            return;
        }else if(djlxEditText.getText().toString().equals("")){
            showToastPromopt("单据类型不能为空");
            return;
        }else if(ckEditText.getText().toString().equals("")){
            showToastPromopt("仓库不能为空");
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
             jsonObject.put("billdate", djrqEditText.getText().toString());
             jsonObject.put("combinflag", djlxId);
             jsonObject.put("storeid", ckId);
             jsonObject.put("totalamt", "");
             jsonObject.put("exemanid", jbrId);
             jsonObject.put("memo", bzxxEditText.getText().toString());
             jsonObject.put("opid", ShareUserInfo.getUserId(context));
             Map<String, Object> myMap=list.get(0);
             jsonObject.put("goodsid", myMap.get("goodsid").toString());
             jsonObject.put("unitid", myMap.get("unitid").toString());
             jsonObject.put("unitqty", myMap.get("sl") == null ? myMap.get("unitqty").toString()
                 : myMap.get("sl").toString());
             jsonObject.put("unitprice", myMap.get("dj") == null ? myMap.get("unitprice").toString()
                 : myMap.get("dj").toString());
             jsonObject.put("totalamt", "");
             jsonObject.put("batchcode", "");
             jsonObject.put("produceddate", "");
             jsonObject.put("validdate", "");
             double bZe = Double.parseDouble(myMap.get("sl") == null ? myMap.get("unitqty").toString()
                 : myMap.get("sl").toString())
                          * Double.parseDouble(myMap.get("dj") == null ? myMap.get("unitprice").toString()
                              : myMap.get("dj").toString());
             double sZe = 0;
             arrayMaster.put(jsonObject);
             for (Map<String, Object> map : list2) {
                 JSONObject jsonObject2 = new JSONObject();
                 jsonObject2.put("billid", "0");
                 jsonObject2.put("itemno", "0");
                 jsonObject2.put("goodsid", map.get("goodsid").toString());
                 jsonObject2.put("unitid", map.get("unitid").toString());
                 jsonObject2.put("unitprice", map.get("dj") == null ? map.get("unitprice")
                     .toString() : map.get("dj").toString());
                 jsonObject2.put("unitqty", map.get("sl") == null ? map.get("unitqty")
                     .toString() : map.get("sl").toString());
                 jsonObject2.put("amount", "0");
                 jsonObject2.put("batchcode", "");
                 jsonObject2.put("produceddate", "");
                 jsonObject2.put("validdate", "");
                 jsonObject2.put("batchrefid", "");
                 sZe += Double.parseDouble(map.get("sl") == null ? map.get("unitqty").toString()
                     : map.get("sl").toString())
                     * Double.parseDouble(map.get("dj") == null ? map.get("unitprice").toString()
                         : map.get("dj").toString());
                 arrayDetail.put(jsonObject2);
             }
             if(sZe!=bZe){
                 showToastPromopt("组装商品金额必须等于各明细商品金额之和！");
                 return;
             }
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("tabname", "tb_combin");
        parmMap.put("parms", "ZZCX");
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
            djrqEditText.setText(object.get("billdate").toString());
            jbrEditText.setText(object.get("empname").toString());
            bzxxEditText.setText(object.get("memo").toString());
            jbrId = object.get("empid").toString();
            searchDate2();//查询订单中的商品
        }else if(returnSuccessType==2){//管理单据成功后把信息填到里面（从表）
            list2 = (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
            adapter2 = new JxcCgglCgddDetailAdapter(list2, this);
            xzspnumTextView2.setText("共选择了" + list2.size() + "商品");
            listview2.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.xzsp_linearlayout:
            	if(ckEditText.getText().toString().equals("")){
            		showToastPromopt("请先选择仓库");
            		return;
            	}
            	intent.putExtra("storeid", ckId);
                intent.setClass(this, JxcCkglZzcxXzspActivity.class);
                startActivityForResult(intent, 0);
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
            case R.id.djlx_edittext:
                intent.setClass(activity, CommonXzdjlxActivity.class);
                startActivityForResult(intent, 6);
                break;
            case R.id.ck_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "STORE");
                startActivityForResult(intent, 7);
                break;
            case R.id.xzsp2_linearlayout:
            	if(ckEditText.getText().toString().equals("")){
            		showToastPromopt("请先选择仓库");
            		return;
            	}
            	intent.putExtra("storeid", ckId);
                intent.setClass(this, JxcCkglZzcxXzsp2Activity.class);
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
            if (requestCode == 0) {// 选择商品
                list.clear();
                List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
                    .getSerializableExtra("object");
                for (int i = 0; i < cpList.size(); i++) {
                    Map<String, Object> map = cpList.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = cpList.get(i + 1);
                            map.put("dj", map2.get("dj").toString());
                            map.put("sl", map2.get("sl"));
                            list.add(map);
                            searchDate3(map.get("goodsid").toString());
                        }
                    }
                }
                xzspnumTextView.setText("共选择了" + list.size() + "商品");
                adapter.notifyDataSetChanged();
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
                xzspnumTextView.setText("共选择了" + list.size() + "商品");
            }else if(requestCode==5){//选中单据成功后返回
                billid=data.getExtras().getString("billid");
                searchDate();//查询主表的数据填充
            }else if (requestCode == 6) {// 单据类型
                djlxEditText.setText(data.getExtras().getString("name"));
                djlxId = data.getExtras().getString("id");
            }else if (requestCode == 7) {// 仓库
                ckEditText.setText(data.getExtras().getString("dictmc"));
                ckId = data.getExtras().getString("id");
            }else if(requestCode==9){
//            	list2.clear();
                List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
                    .getSerializableExtra("object");
                for (int i = 0; i < cpList.size(); i++) {
                    Map<String, Object> map = cpList.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = cpList.get(i + 1);
                            map.put("dj", map2.get("dj").toString());
                            map.put("sl", map2.get("sl").toString());
                            String amount=(Double.parseDouble(map2.get("dj").toString())
                                    * Double.parseDouble(map2.get("sl").toString()))+"";
                            map.put("amount", FigureTools.sswrFigure(amount+""));
                            list2.add(map);
                        }
                    }
                }
                xzspnumTextView2.setText("共选择了" + list2.size() + "商品");
                adapter2.notifyDataSetChanged();
            }else if (requestCode == 10) {//修改选中的商品的详情
                if (data.getExtras().getSerializable("object").toString().equals("")) {//说明删除了
                    list2.remove(selectIndex2);
                    adapter2.notifyDataSetChanged();
                } else {
                    Map<String, Object> map = (Map<String, Object>) data.getExtras()
                        .getSerializable("object");
                    list2.remove(selectIndex2);
                    list2.add(selectIndex2, map);
                    adapter2.notifyDataSetChanged();
                }
                xzspnumTextView2.setText("共选择了" + list2.size() + "商品");
            }
        }
    }
}