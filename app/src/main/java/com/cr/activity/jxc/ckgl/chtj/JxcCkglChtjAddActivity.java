package com.cr.activity.jxc.ckgl.chtj;

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
import android.text.TextUtils;
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
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.jxc.ckgl.chtj.JxcCkglChtjAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.utils.LogUtils;

/**
 * 进销存-仓库管理-存货调价-增加
 * 
 * @author Administrator
 * 
 */
public class JxcCkglChtjAddActivity extends BaseActivity implements OnClickListener {
    private ImageButton               saveImageButton;
    private TextView                  xzspnumTextView;
    private EditText                  bzxxEditText, djrqEditText, jbrEditText,wjphEditText;
    private CustomListView            listview;
    String                            jbrId;
    private List<Map<String, Object>> list;
    private LinearLayout              xzspLinearLayout;
    BaseAdapter                       adapter;
//    LinearLayout                      xzxsddLinearLayout;
    private int                       selectIndex;
    String billid;//选择完关联的单据后返回的单据的ID
 private String mDepartmentid;//部门ID
    private EditText etBm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_chtj_add);
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
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        listview = (CustomListView) findViewById(R.id.xzsp_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                Intent intent = new Intent();
                intent.setClass(activity, JxcCkglChtjXzspDetailActivity.class);
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });
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
        wjphEditText = (EditText) findViewById(R.id.wjph_edittext);
        xzspnumTextView = (TextView) findViewById(R.id.xzspnum_textview);
        xzspLinearLayout = (LinearLayout) findViewById(R.id.xzsp_linearlayout);
        xzspLinearLayout.setOnClickListener(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new JxcCkglChtjAddAdapter(list, this);
        xzspnumTextView.setText("共选择了" + list.size() + "商品");
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        mDepartmentid = ShareUserInfo.getKey(this, "departmentid");
        etBm.setText(ShareUserInfo.getKey(this, "depname"));
        jbrEditText.setText(ShareUserInfo.getKey(this, "opname"));
        jbrId =  ShareUserInfo.getKey(this, "empid");
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
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if(wjphEditText.getText().toString().equals("")){
            showToastPromopt("文件批号不能为空！");
            return;
        }
        if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        }
        if(djrqEditText.getText().toString().equals("")){
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
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("fileno",wjphEditText.getText().toString() );
            jsonObject.put("departmentid", mDepartmentid);
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(context));
            double tzce=0;
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");
                jsonObject2.put("itemno", "0");
                jsonObject2.put("goodsid", map.get("goodsid").toString());
                jsonObject2.put("unitid", map.get("unitid").toString());
                String sl=map.get("sl")==null?map.get("unitqty").toString():map.get("sl").toString();
                String thdj=map.get("thdj")==null?map.get("anprice").toString():map.get("thdj").toString();
                String tqdj=map.get("tqdj")==null?map.get("aoprice").toString():map.get("tqdj").toString();
                jsonObject2.put("unitqty",sl );
                jsonObject2.put("anprice", thdj);
                jsonObject2.put("aoprice", tqdj);
                jsonObject2.put("memo", map.get("memo").toString());//备注
                tzce=(Double.parseDouble(thdj)-Double.parseDouble(tqdj))*Double.parseDouble(sl);
                arrayDetail.put(jsonObject2);
            }
            jsonObject.put("totalamt",tzce );
            arrayMaster.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("tabname", "tb_adjap");
        parmMap.put("parms", "CHTJ");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        findServiceData2(0, "billsave", parmMap, false);
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
                intent.setClass(this, JxcCkglChtjXzspActivity.class);
                startActivityForResult(intent, 0);
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
                for (int i = 0; i < cpList.size(); i++) {
                    Map<String, Object> map = cpList.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = cpList.get(i + 1);
                            map.put("dw", map2.get("dw").toString());
                            map.put("sl", map2.get("sl"));
                            LogUtils.e(map2.get("sl").toString());
                            map.put("tqdj", map2.get("tqdj").toString());
                            map.put("thdj", map2.get("thdj").toString());
                            map.put("memo", map2.get("memo").toString());
                            list.add(map);
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