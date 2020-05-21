package com.cr.activity.jxc.cggl.cgdd;

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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cr.activity.BaseActivity;
import com.cr.activity.CkxzActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzyyActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddAddAdapter;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-采购管理-采购订单-增加
 * 
 * @author Administrator
 * 
 */
@Deprecated
public class JxcCgglCgddAddActivity extends BaseActivity implements OnClickListener {
    private ImageButton               saveImageButton;
    private ToggleButton              toggleButton;
    
    private EditText rkckEditText;
    
    private TextView                  xzspnumTextView;
    private EditText                  bzxxEditText, gysEditText, lxrEditText, lxdhEditText,
            jhdzEditText, hjjeEditText, djrqEditText, jbrEditText;
    /**
     * 部门
     */
    private EditText etBm;
    /**
     * 发票类型
     */
    private EditText etFplx;
    private CustomListView            listview;
    String                            gysId       = "", lxrId = "", jbrId = "";
    private List<Map<String, Object>> list;
    private LinearLayout              xzspLinearLayout;
    BaseAdapter                       adapter;
    LinearLayout                      gldjcgLinearLayout, xzxsddLinearLayout;
    ScrollView                        addScrollView;
    private int                       selectIndex;
    String                            billid;                                  //选择完关联的单据后返回的单据的ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_cggl_cgdd_add);
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
        
        rkckEditText=(EditText) findViewById(R.id.rkck_edittext);
        rkckEditText.setOnClickListener(this);
        
        gysEditText = (EditText) findViewById(R.id.gys_edittext);
        gysEditText.setOnClickListener(this);
        lxrEditText = (EditText) findViewById(R.id.lxr_edittext);
        lxrEditText.setOnClickListener(this);
        lxdhEditText = (EditText) findViewById(R.id.lxdh_edittext);
        etBm = (EditText) findViewById(R.id.et_bm);
        etBm.setOnClickListener(this);
        jhdzEditText = (EditText) findViewById(R.id.jhdz_edittext);
        jhdzEditText.setOnClickListener(this);
        listview = (CustomListView) findViewById(R.id.xzsp_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                Intent intent = new Intent();
                intent.setClass(activity, JxcCgglCgddXzspDetailActivity.class);
//                intent.putExtra("rkckId", value)
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });
        hjjeEditText = (EditText) findViewById(R.id.hjje_edittext);
        djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
        djrqEditText.setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
        xzxsddLinearLayout = (LinearLayout) findViewById(R.id.xzxsdd_linearlayout);
        xzxsddLinearLayout.setOnClickListener(this);
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
        adapter = new JxcCgglCgddAddAdapter(list, this);
        xzspnumTextView.setText("共选择了" + list.size() + "商品");
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "CGDD_XSDD");
        parmMap.put("clientid", "");
        parmMap.put("qsrq", "");
        parmMap.put("zzrq", billid);
        parmMap.put("billid", billid);
        findServiceData2(1, ServerURL.REFBILLMASTER, parmMap, false);
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "CGDD");
        parmMap.put("billid", billid);
        findServiceData2(2, ServerURL.REFBILLDETAIL, parmMap, false);
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (gysEditText.getText().toString().equals("")) {
            showToastPromopt("供应商不能为空");
            return;
        }
        if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        }
        if (djrqEditText.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        }
//        if (jbrEditText.getText().toString().equals("")) {
//            showToastPromopt("请选择业务员");
//            return;
//        }
        JSONArray arrayMaster = new JSONArray();
        JSONArray arrayDetail = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid", "0");
            jsonObject.put("code", "");
            jsonObject.put("clientid", gysId);//供应商ID
            jsonObject.put("linkmanid", lxrId);//联系人ID
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("phone", lxdhEditText.getText().toString());
            jsonObject.put("billto", jhdzEditText.getText().toString());
            String hjje = hjjeEditText.getText().toString();
            jsonObject.put("amount", hjje.replace("￥", ""));
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(mContext));
            arrayMaster.put(jsonObject);
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");
                jsonObject2.put("itemno", "0");
                jsonObject2.put("goodsid", map.get("goodsid").toString());
                jsonObject2.put("unitid", map.get("unitid").toString());
                jsonObject2.put("unitprice", map.get("unitprice").toString());
                jsonObject2.put("unitqty", map.get("unitqty").toString());
                String disc = map.get("disc").toString();
                //                if(disc.length()>0){
                //                	jsonObject2.put("disc", disc.substring(0,disc.lastIndexOf(".")));
                //                }else{
                //                	jsonObject2.put("disc", "");
                //                }
                jsonObject2.put("disc", disc);
                jsonObject2.put("amount", map.get("amount").toString());
                jsonObject2.put("batchcode", map.get("batchcode").toString());
                jsonObject2.put("produceddate", map.get("produceddate").toString());
                jsonObject2.put("validdate", map.get("validdate").toString());
                jsonObject2.put("refertype", map.get("refertype")==null?"":map.get("refertype").toString());
                jsonObject2.put("batchrefid",  map.get("batchrefid")==null?"":map.get("batchrefid").toString());
                jsonObject2.put("referbillid ", map.get("referbillid")==null?"":map.get("referbillid").toString());
                jsonObject2.put("referitemno ", map.get("referitemno")==null?"":map.get("referitemno").toString());
                arrayDetail.put(jsonObject2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_porder");
        parmMap.put("parms", "CGDD");
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
        } else if (returnSuccessType == 1) {//管理单据成功后把信息填到里面（主表）
            if (returnJson.equals("")) {
                return;
            }
            Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
                .paseJsonToObject(returnJson)).get(0);
            gysEditText.setText(object.get("cname").toString());
            lxrEditText.setText(object.get("lxrname").toString());
            lxdhEditText.setText(object.get("phone").toString());
            jhdzEditText.setText(object.get("billto").toString());
            hjjeEditText.setText(FigureTools.sswrFigure(object.get("amount").toString()));
            djrqEditText.setText(object.get("billdate").toString());
            jbrEditText.setText(object.get("empname").toString());
            bzxxEditText.setText(object.get("memo").toString());
            gysId = object.get("clientid").toString();
            lxrId = object.get("lxrid").toString();
            jbrId = object.get("empid").toString();
            searchDate2();//查询订单中的商品
        } else if (returnSuccessType == 2) {//管理单据成功后把信息填到里面（从表）
            list = (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
            adapter = new JxcCgglCgddDetailAdapter(list, this);
            xzspnumTextView.setText("共选择了" + list.size() + "商品");
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    private long time;
    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.xzsp_linearlayout:
            	intent.putExtra("rkckId", "0");
            	intent.putExtra("tabname", "tb_porder");
                intent.setClass(this, JxcCgglCgddXzsp2Activity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.gys_edittext:
                intent.setClass(this, CommonXzdwActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, 1);
                break;
            case R.id.lxr_edittext:
                if (gysId.equals("")) {
                    showToastPromopt("请先选择供应商信息");
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
            	if(rkckEditText.getText().toString().equals("")){
            		showToastPromopt("请先选择仓库");
            		return;
            	}
                intent.putExtra("type", "CGDD_XSDD");
                intent.putExtra("clientid", "0");
                intent.putExtra("reftypeid", "1");
                intent.setClass(activity, CommonXzyyActivity.class);
                startActivityForResult(intent, 5);
                break;
            case R.id.rkck_edittext:
                intent.setClass(activity, CkxzActivity.class);
                startActivityForResult(intent, 6);
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "STORE");
//                startActivityForResult(intent, 6);
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
                            String amount = (Double.parseDouble(map2.get("dj").toString()) * Double
                                .parseDouble(map2.get("sl").toString())) + "";
                            map.put("amount", FigureTools.sswrFigure(amount + ""));
                            map.put("disc", map2.get("zkl"));
                            map.put("batchcode", map2.get("cpph"));
                            map.put("produceddate", map2.get("scrq"));
                            map.put("validdate", map2.get("yxqz"));
                            list.add(map);
                            //                            zje += Double.parseDouble(map.get("amount").toString());
                        }
                    }
                }
                for (Map<String, Object> m : list) {
                    zje += Double.parseDouble(m.get("amount").toString());
                }
                xzspnumTextView.setText("共选择了" + list.size() + "商品");
                hjjeEditText.setText("￥" + FigureTools.sswrFigure(zje + "") + "");
                adapter.notifyDataSetChanged();
            } else if (requestCode == 1) {
                if(!gysEditText.getText().toString().equals(data.getExtras().getString("name"))){
                	lxrEditText.setText("");
                	lxrId="";
                	lxdhEditText.setText("");		
                	gysEditText.setText(data.getExtras().getString("name"));
                	gysId = data.getExtras().getString("id");
                }
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
                        Double.parseDouble(map.get("unitprice").toString())
                                * Double.parseDouble(map.get("unitqty").toString()));
                    list.add(selectIndex, map);
                    adapter.notifyDataSetChanged();
                }
                xzspnumTextView.setText("共选择了" + list.size() + "商品");
                double zje = 0;
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = list.get(i);
                    zje += Double.parseDouble(map.get("amount").toString());
                }
                hjjeEditText.setText("￥" + FigureTools.sswrFigure(zje + "") + "");
            } else if (requestCode == 5) {//选中单据成功后返回
                addScrollView.setVisibility(View.VISIBLE);//隐藏关联销售单据的Linearlayout
                gldjcgLinearLayout.setVisibility(View.GONE);//显示展示详情的Linearlayout信息
                toggleButton.setSelected(false);
                toggleButton.setChecked(false);
                list.clear();
                list.addAll((List<Map<String, Object>>) data.getExtras().getSerializable("list"));
                xzspnumTextView.setText("共选择了" + list.size() + "商品");
                
                adapter.notifyDataSetChanged();
                hjjeEditText.setText(data.getExtras().getString("totalAmount"));
            }else if(requestCode==6){
            	rkckEditText.setText(data.getExtras().getString("dictmc"));
            }
        }
    }
}