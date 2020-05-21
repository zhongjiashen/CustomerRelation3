package com.cr.activity.jxc.ckgl.zzcx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdjlxActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcActivity;
import com.cr.adapter.jxc.ckgl.zzcx.JxcCkglZzcxAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-仓库管理-组装拆卸-详情
 *
 * @author Administrator
 */
public class JxcCkglZzcxDetailActivity extends BaseActivity implements OnClickListener {
    private ImageView shImageView;
    private ImageButton saveImageButton;
    private Button shButton, sdButton;
    private TextView xzspnumTextView, djbhTextView;
    private EditText djlxEditText, ckEditText, bzxxEditText, djrqEditText,
            jbrEditText;
    private CustomListView listview;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private LinearLayout xzspLinearLayout;
    BaseAdapter adapter;
    String jbrId, djlxId, ckId;
    private int selectIndex;
    private int selectIndex2;
    private String shzt;                                       //社会状态
    Map<String, Object> object;
    private TextView xzspnumTextView2;
    private CustomListView listview2;
    private List<Map<String, Object>> list2;
    private LinearLayout xzspLinearLayout2;
    BaseAdapter adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_zzcx_detail);
        addFHMethod();
        initActivity();
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        djbhTextView = (TextView) findViewById(R.id.djbh_textview);
        shImageView = (ImageView) findViewById(R.id.sh_imageview);
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
        xzspLinearLayout = (LinearLayout) findViewById(R.id.xzsp_linearlayout);
        xzspLinearLayout.setOnClickListener(this);
        djlxEditText = (EditText) findViewById(R.id.djlx_edittext);
        djlxEditText.setOnClickListener(this);
        ckEditText = (EditText) findViewById(R.id.ck_edittext);
        ckEditText.setOnClickListener(this);
        djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
        djrqEditText.setOnClickListener(this);
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
        xzspnumTextView.setVisibility(View.GONE);
        shButton = (Button) findViewById(R.id.sh_button);
        shButton.setOnClickListener(this);
        sdButton = (Button) findViewById(R.id.sd_button);
        sdButton.setOnClickListener(this);
        adapter = new JxcCkglZzcxAddAdapter(list, this);
        listview.setAdapter(adapter);

        xzspnumTextView2 = (TextView) findViewById(R.id.xzspnum2_textview);
        xzspLinearLayout2 = (LinearLayout) findViewById(R.id.xzsp2_linearlayout);
        xzspLinearLayout2.setOnClickListener(this);
        list2 = new ArrayList<Map<String, Object>>();
        adapter2 = new JxcCkglZzcxAddAdapter(list2, this);
        listview2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "ZZCX");
        parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
        findServiceData2(0, ServerURL.BILLMASTER, parmMap, false);
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "ZZCX");
        parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
        findServiceData2(1, ServerURL.BILLDETAIL, parmMap, false);
    }

    /**
     * 连接网络的操作（查询从表的内容(根据选定的主表，查询出已经选择的从表的内容)）
     */
    private void searchDate3(String goodsid) {
        list2.clear();
        adapter2.notifyDataSetChanged();
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("goodsid", goodsid);
        findServiceData2(1, ServerURL.SELECTGOODSCMB, parmMap, false);
    }

    /**
     * 连接网络的操作(删单)
     */
    private void searchDateSd() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("opid", ShareUserInfo.getUserId(mContext));
        parmMap.put("tabname", "tb_combin");
        parmMap.put("pkvalue", this.getIntent().getExtras().getString("billid"));
        findServiceData2(2, ServerURL.BILLDELMASTER, parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson.equals("")) {
                return;
            }
            List<Map<String, Object>> l = (List<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson);
            object = l.get(0);
            djbhTextView.setText(object.get("code").toString());
            djrqEditText.setText(object.get("billdate").toString());
            jbrEditText.setText(object.get("empname").toString());
            bzxxEditText.setText(object.get("memo").toString());
            djlxId = object.get("combinflag").toString();
            djlxEditText.setText(object.get("combinflagname").toString());
            ckId = object.get("storeid").toString();
            ckEditText.setText(object.get("storename").toString());
            if (object.get("shzt").toString().equals("0")) {
                shImageView.setBackgroundResource(R.drawable.wsh);
            } else if (object.get("shzt").toString().equals("1")) {
                shImageView.setBackgroundResource(R.drawable.ysh);
            } else if (object.get("shzt").toString().equals("2")) {
                shImageView.setBackgroundResource(R.drawable.shz);
            }
            jbrId = object.get("exemanid").toString();
            shzt = object.get("shzt").toString();
            list.clear();
            list.addAll(l);
            xzspnumTextView.setText("共选择了" + list.size() + "商品");
            adapter.notifyDataSetChanged();
            showZdr(object);
            searchDate2();//查询订单中的商品
        } else if (returnSuccessType == 1) {
//            list2.clear();
            if (returnJson.equals("")) {
                return;
            }
            list2.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
            xzspnumTextView2.setText("共选择了" + list2.size() + "商品");
            adapter2.notifyDataSetChanged();
        } else if (returnSuccessType == 2) {
            if (returnJson.equals("")) {
                showToastPromopt("删除成功");
                setResult(RESULT_OK);
                finish();
            } else {
                showToastPromopt("删除失败" + returnJson.substring(5));
            }
        } else if (returnSuccessType == 3) {
            if (returnJson.equals("")) {
                showToastPromopt("操作成功");
                setResult(RESULT_OK);
                finish();
            } else {
                showToastPromopt("操作失败" + returnJson.substring(5));
            }
        }
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (!shzt.equals("0")) {
            showToastPromopt("该单据在审核阶段，不能进行修改");
            return;
        }
        if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        } else if (djrqEditText.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        } else if (djlxEditText.getText().toString().equals("")) {
            showToastPromopt("单据类型不能为空");
            return;
        } else if (ckEditText.getText().toString().equals("")) {
            showToastPromopt("仓库不能为空");
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
            jsonObject.put("billid", this.getIntent().getExtras().getString("billid"));
            jsonObject.put("code", object.get("code").toString());
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("combinflag", djlxId);
            jsonObject.put("storeid", ckId);
            jsonObject.put("totalamt", "");
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(mContext));
            Map<String, Object> myMap = list.get(0);
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
                //                jsonObject2.put("billid", this.getIntent().getExtras().getString("billid"));
                jsonObject2.put("billid", "0");
                jsonObject2.put("itemno", "0");
                //                jsonObject2.put("goodsid", map.get("goodsid").toString());
                //                jsonObject2.put("unitid", map.get("unitid").toString());
                //                jsonObject.put("unitqty", myMap.get("sl")==null?myMap.get("unitqty").toString():myMap.get("sl").toString());
                //                jsonObject.put("unitprice", myMap.get("dj")==null?myMap.get("unitprice").toString():myMap.get("dj").toString());
                //                jsonObject2.put("amount", "0");
                //                jsonObject2.put("batchcode", "");
                //                jsonObject2.put("produceddate", "");
                //                jsonObject2.put("validdate", "");
                //                jsonObject2.put("batchrefid", "");

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
                jsonObject2.put("batchrefid", map.get("batchrefid") == null ? "" : map.get("batchrefid").toString());
                sZe += Double.parseDouble(map.get("sl") == null ? map.get("unitqty").toString()
                        : map.get("sl").toString())
                        * Double.parseDouble(map.get("dj") == null ? map.get("unitprice").toString()
                        : map.get("dj").toString());
                arrayDetail.put(jsonObject2);
            }
            if (sZe != bZe) {
                showToastPromopt("组装商品金额必须等于各明细商品金额之和！");
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        //      parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_combin");
        parmMap.put("parms", "ZZCX");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        findServiceData2(3, ServerURL.BILLSAVE, parmMap, false);
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.sh_button:
                intent.putExtra("tb", "tb_combin");
                intent.putExtra("opid", object.get("opid").toString());
                intent.putExtra("billid", this.getIntent().getExtras().getString("billid"));
                intent.setClass(activity, JxcCgglCgddShlcActivity.class);
                startActivityForResult(intent, 11);
                break;
            case R.id.sd_button:
                if (!ShareUserInfo.getKey(activity, "sc").equals("1")) {
                    showToastPromopt("你没有该权限，请向管理员申请权限！");
                    return;
                }
                new AlertDialog.Builder(activity)
                        .setTitle("确定要删除当前记录吗？")
                        .setNegativeButton("删除",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        searchDateSd();
                                    }
                                }).setPositiveButton("取消", null).show();
                break;
//            case R.id.xzsp_linearlayout:
//                intent.setClass(this, JxcCkglZzcxXzspActivity.class);
//                startActivityForResult(intent, 0);
//                break;
//            case R.id.gys_edittext:
//                intent.setClass(this, CommonXzdwActivity.class);
//                intent.putExtra("type", "2");
//                startActivityForResult(intent, 1);
//                break;
//            case R.id.djrq_edittext:
//                date_init(djrqEditText);
//                break;
//            case R.id.jbr_edittext:
//                intent.setClass(activity, CommonXzjbrActivity.class);
//                startActivityForResult(intent, 3);
//                break;
//            case R.id.save_imagebutton:
//                searchDateSave();//保存
//                break;
//            case R.id.xzsp2_linearlayout:
//                intent.setClass(this, JxcCkglZzcxXzsp2Activity.class);
//                startActivityForResult(intent, 9);
//                break;
//            case R.id.djlx_edittext:
//                intent.setClass(activity, CommonXzdjlxActivity.class);
//                startActivityForResult(intent, 6);
//                break;
//            case R.id.ck_edittext:
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "STORE");
//                startActivityForResult(intent, 7);
//                break;
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
            } else if (requestCode == 6) {// 单据类型
                djlxEditText.setText(data.getExtras().getString("name"));
                djlxId = data.getExtras().getString("id");
            } else if (requestCode == 7) {// 仓库
                ckEditText.setText(data.getExtras().getString("dictmc"));
                ckId = data.getExtras().getString("id");
            } else if (requestCode == 9) {
//                list2.clear();
                List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
                        .getSerializableExtra("object");
                for (int i = 0; i < cpList.size(); i++) {
                    Map<String, Object> map = cpList.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = cpList.get(i + 1);
                            map.put("dj", map2.get("dj").toString());
                            map.put("sl", map2.get("sl"));
                            list2.add(map);
                        }
                    }
                }
                xzspnumTextView2.setText("共选择了" + list2.size() + "商品");
                adapter2.notifyDataSetChanged();
            } else if (requestCode == 10) {//修改选中的商品的详情
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
            } else if (requestCode == 11) {
                searchDate();
                setResult(RESULT_OK);
            }
        }
    }
}
