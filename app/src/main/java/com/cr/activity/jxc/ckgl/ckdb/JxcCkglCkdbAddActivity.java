package com.cr.activity.jxc.ckgl.ckdb;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.CkxzActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.JxcSpbjActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.jxc.ckgl.ckdb.JxcCkglCkdbAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.actiity.WeChatCaptureActivity;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.model.Serial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 进销存-仓库管理-仓库调拨-增加
 *
 * @author Administrator
 */
public class JxcCkglCkdbAddActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.fh)
    ImageButton fh;
    @BindView(R.id.save_imagebutton)
    ImageButton saveImagebutton;
    @BindView(R.id.ckck_edittext)
    EditText ckckEdittext;
    @BindView(R.id.rkck_edittext)
    EditText rkckEdittext;
    @BindView(R.id.xzspnum_textview)
    TextView xzspnumTextview;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.xzsp_linearlayout)
    LinearLayout xzspLinearlayout;
    @BindView(R.id.xzsp_listview)
    CustomListView xzspListview;
    @BindView(R.id.djrq_edittext)
    EditText djrqEdittext;
    @BindView(R.id.et_bm)
    EditText etBm;
    @BindView(R.id.jbr_edittext)
    EditText jbrEdittext;
    @BindView(R.id.bzxx_edittext)
    EditText bzxxEdittext;
    @BindView(R.id.add_scrollview)
    ScrollView addScrollview;

    String jbrId, rkckId, ckckId;
    private List<Map<String, Object>> list;

    BaseAdapter adapter;

    private int selectIndex;
    String billid;//选择完关联的单据后返回的单据的ID
    private String mDepartmentid;//部门ID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_ckdb_add);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        // searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {


        xzspListview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
//
                Intent intent = new Intent();
                intent.setClass(activity, JxcCkglCkdbXzspDetailActivity.class);
                intent.putExtra("rkckId", ckckId);
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        djrqEdittext.setText(sdf.format(new Date()));

        bzxxEdittext.setOnTouchListener(new OnTouchListener() {
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

        list = new ArrayList<Map<String, Object>>();
        adapter = new JxcCkglCkdbAddAdapter(list, this);
        xzspnumTextview.setText("共选择了" + list.size() + "商品");
        xzspListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mDepartmentid = ShareUserInfo.getKey(this, "departmentid");
        etBm.setText(ShareUserInfo.getKey(this, "depname"));
        jbrEdittext.setText(ShareUserInfo.getKey(this, "opname"));
        jbrId = ShareUserInfo.getKey(this, "empid");
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "CKDB");
        parmMap.put("billid", billid);
        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false);
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "CKDB");
        parmMap.put("billid", billid);
        findServiceData2(2, ServerURL.BILLDETAIL, parmMap, false);
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (ckckEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择出库仓库");
            return;
        }
        if (rkckEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择入库仓库");
            return;
        }
        if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        }
        if (djrqEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        } else if (rkckId.equals(ckckId)) {
            showToastPromopt("调出仓库和调入仓库不能相同！");
            return;
        }
        if (TextUtils.isEmpty(mDepartmentid)) {
            showToastPromopt("请先选择部门");
            return;
        }
//        if (jbrEdittext.getText().toString().equals("")) {
//            showToastPromopt("请选择业务员");
//            return;
//        }
        JSONArray arrayMaster = new JSONArray();
        JSONArray arrayDetail = new JSONArray();
        List<Serial> serialinfo = new ArrayList<Serial>();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid", "0");
            jsonObject.put("code", "");
            jsonObject.put("billdate", djrqEdittext.getText().toString());
            jsonObject.put("insid", rkckId);
            jsonObject.put("outsid", ckckId);
            jsonObject.put("totalamt", "0");
            jsonObject.put("departmentid", mDepartmentid);
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("memo", bzxxEdittext.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(context));
            arrayMaster.put(jsonObject);
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");
                jsonObject2.put("itemno", "0");
                jsonObject2.put("goodsid", map.get("goodsid").toString());
                jsonObject2.put("unitid", map.get("unitid").toString());
                jsonObject2.put("unitprice", map.get("unitprice").toString());
                jsonObject2.put("unitqty", map.get("unitqty").toString());
                jsonObject2.put("amount", map.get("amount").toString());
                jsonObject2.put("batchcode", map.get("batchcode").toString());
                jsonObject2.put("produceddate", map.get("produceddate").toString());
                jsonObject2.put("validdate", map.get("validdate").toString());
                jsonObject2.put("batchrefid", map.get("batchrefid") == null ? "" : map.get("batchrefid").toString());
                jsonObject2.put("serialinfo", map.get("serialinfo").toString());//备注
                jsonObject2.put("memo", map.get("memo").toString());//备注
                arrayDetail.put(jsonObject2);
                serialinfo.addAll((ArrayList<Serial>) map.get("serials"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_allot");
        parmMap.put("parms", "CKDB");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        parmMap.put("serialinfo", new Gson().toJson(serialinfo));
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
        } else if (returnSuccessType == 1) {//管理单据成功后把信息填到里面（主表）
            if (returnJson.equals("")) {
                return;
            }
            Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson)).get(0);
//            gysEditText.setText(object.get("cname").toString());
//            lxrEditText.setText(object.get("lxrname").toString());
//            lxdhEditText.setText(object.get("phone").toString());
//            jhdzEditText.setText(object.get("billto").toString());
//            hjjeEditText.setText(object.get("amount").toString());
            djrqEdittext.setText(object.get("billdate").toString());
            jbrEdittext.setText(object.get("empname").toString());
            bzxxEdittext.setText(object.get("memo").toString());
//            gysId = object.get("clientid").toString();
//            lxrId = object.get("lxrid").toString();
            jbrId = object.get("empid").toString();
            searchDate2();//查询订单中的商品
        } else if (returnSuccessType == 2) {//管理单据成功后把信息填到里面（从表）
            list = (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
            adapter = new JxcCgglCgddDetailAdapter(list, this);
            xzspnumTextview.setText("共选择了" + list.size() + "商品");
            xzspListview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private long time;

    @OnClick({R.id.save_imagebutton, R.id.ckck_edittext, R.id.rkck_edittext, R.id.xzspnum_textview, R.id.iv_scan, R.id.xzsp_linearlayout, R.id.djrq_edittext, R.id.et_bm, R.id.jbr_edittext, R.id.bzxx_edittext})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.xzsp_linearlayout:
                if (ckckEdittext.getText().toString().equals("")) {
                    showToastPromopt("请先选择出库仓库！");
                    return;
                }
                intent.putExtra("storeid", ckckId);
                intent.setClass(this, JxcCkglCkdbXzspActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.iv_scan://扫一扫
                if (ckckEdittext.getText().toString().equals("")) {
                    showToastPromopt("请先选择出库仓库！");
                    return;
                }
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class), 18);
                break;
            case R.id.djrq_edittext:
                date_init(djrqEdittext);
                break;
            case R.id.et_bm://部门选择
                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 15);
                break;
            case R.id.jbr_edittext://业务员选择
                if (TextUtils.isEmpty(mDepartmentid))
                    showToastPromopt("请先选择部门");
                else
                    startActivityForResult(new Intent(this, SelectSalesmanActivity.class)
                            .putExtra("depid", mDepartmentid), 16);
//                intent.setClass(activity, CommonXzjbrActivity.class);
//                startActivityForResult(intent, 3);
                break;
            case R.id.save_imagebutton://保存
                if (time == 0 || System.currentTimeMillis() - time > 5000) {
                    searchDateSave();//保存
                    time = System.currentTimeMillis();
                } else {
                    showToastPromopt("请不要频繁点击，防止重复保存");

                }
                break;
            case R.id.rkck_edittext:
                intent.setClass(activity, CkxzActivity.class);
                startActivityForResult(intent, 6);
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "STORE");
//                startActivityForResult(intent, 6);
                break;
            case R.id.ckck_edittext:
                intent.setClass(activity, CkxzActivity.class);
                startActivityForResult(intent, 7);
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "STORE");
//                startActivityForResult(intent, 7);
                break;


            case R.id.xzspnum_textview:
                break;


            case R.id.bzxx_edittext:
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
//                double zje = 0;
                for (int i = 0; i < cpList.size(); i++) {
                    Map<String, Object> map = cpList.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = cpList.get(i + 1);
                            map.put("unitprice", "");
                            map.put("unitqty", map2.get("sl").toString());
                            map.put("amount", "0");
                            map.put("dj", map2.get("dj").toString());
                            map.put("disc", map2.get("zkl").toString());
                            map.put("batchcode", map2.get("cpph").toString());
                            map.put("produceddate", map2.get("scrq").toString());
                            map.put("validdate", map2.get("yxqz").toString());
                            map.put("serialinfo", map2.get("serialinfo").toString());
                            map.put("serials", map2.get("serials"));
                            map.put("batchrefid", map2.get("batchrefid"));//
                            map.put("memo", map2.get("memo"));//
                            list.add(map);
//                            zje += Double.parseDouble(map.get("amount").toString());
                        }
                    }
                }
                xzspnumTextview.setText("共选择了" + list.size() + "商品");
//                hjjeEditText.setText("￥" + FigureTools.sswrFigure(zje+"") + "");
                adapter.notifyDataSetChanged();
            } else if (requestCode == 3) {// 经办人
                jbrEdittext.setText(data.getExtras().getString("name"));
                jbrId = data.getExtras().getString("id");
            } else if (requestCode == 4) {//修改选中的商品的详情
                if (data.getExtras().getSerializable("object").toString().equals("")) {//说明删除了
                    list.remove(selectIndex);
                    adapter.notifyDataSetChanged();
                } else {
                    Map<String, Object> map = (Map<String, Object>) data.getExtras()
                            .getSerializable("object");


//                    map.put(
//                        "amount",
//                        map.put("amount", Double.parseDouble(map.get("unitprice").toString())
//                                          * Double.parseDouble(map.get("unitqty").toString())));
                    list.set(selectIndex, map);
                    adapter.notifyDataSetChanged();
                }
                xzspnumTextview.setText("共选择了" + list.size() + "商品");
//                double zje = 0;
//                for (int i = 0; i < list.size(); i++) {
//                    Map<String, Object> map = list.get(i);
//                    zje += Double.parseDouble(map.get("amount").toString());
//                }
//                hjjeEditText.setText("￥" + FigureTools.sswrFigure(zje+"") + "");
            } else if (requestCode == 5) {//选中单据成功后返回
                billid = data.getExtras().getString("billid");
                searchDate();//查询主表的数据填充
            } else if (requestCode == 6) {
                rkckEdittext.setText(data.getExtras().getString("dictmc"));
                rkckId = data.getExtras().getString("id");
            } else if (requestCode == 7) {
                if (!ckckEdittext.getText().toString().equals(data.getExtras().getString("dictmc"))) {
                    ckckEdittext.setText(data.getExtras().getString("dictmc"));
                    ckckId = data.getExtras().getString("id");
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
            } else if (requestCode == 15) {
                mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                etBm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                jbrId = "";
                jbrEdittext.setText("");
            } else if (requestCode == 16) {
                jbrEdittext.setText(data.getExtras().getString("CHOICE_RESULT_TEXT"));
                jbrId = data.getExtras().getString("CHOICE_RESULT_ID");
            } else if (requestCode == 18) {
                Intent intent = new Intent();
                intent.putExtra("storeid", ckckId);
                intent.putExtra("barcode", data.getStringExtra("qr"));
                intent.setClass(this, JxcCkglCkdbXzspActivity.class);
                startActivityForResult(intent, 0);
            }
        }
    }


}

//    @Override
//    public void onClick(View arg0) {
//        Intent intent = new Intent();
//        switch (arg0.getId()) {
//            case R.id.xzsp_linearlayout:
//                if (ckckEditText.getText().toString().equals("")) {
//                    showToastPromopt("请先选择出库仓库！");
//                    return;
//                }
//                intent.putExtra("storeid", ckckId);
//                intent.setClass(this, JxcCkglCkdbXzspActivity.class);
//                startActivityForResult(intent, 0);
//                break;
//            case R.id.djrq_edittext:
//                date_init(djrqEditText);
//                break;
//            case R.id.et_bm:
//                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 15);
//                break;
//            case R.id.jbr_edittext:
//                if (TextUtils.isEmpty(mDepartmentid))
//                    showToastPromopt("请先选择部门");
//                else
//                    startActivityForResult(new Intent(this, SelectSalesmanActivity.class)
//                            .putExtra("depid", mDepartmentid), 16);
////                intent.setClass(activity, CommonXzjbrActivity.class);
////                startActivityForResult(intent, 3);
//                break;
//            case R.id.save_imagebutton:
//                if (time == 0 || System.currentTimeMillis() - time > 5000) {
//                    searchDateSave();//保存
//                    time = System.currentTimeMillis();
//                } else {
//                    showToastPromopt("请不要频繁点击，防止重复保存");
//
//                }
//                break;
//            case R.id.rkck_edittext:
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "STORE");
//                startActivityForResult(intent, 6);
//                break;
//            case R.id.ckck_edittext:
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "STORE");
//                startActivityForResult(intent, 7);
//                break;
//        }
//    }