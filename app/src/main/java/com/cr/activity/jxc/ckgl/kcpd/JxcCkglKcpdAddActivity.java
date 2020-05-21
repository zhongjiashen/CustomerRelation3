package com.cr.activity.jxc.ckgl.kcpd;

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
import com.cr.activity.jxc.KtXzspData;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.jxc.ckgl.kcpd.JxcCkglKcpdAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
 * 进销存-仓库管理-库存盘点-增加
 *
 * @author Administrator
 */
public class JxcCkglKcpdAddActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.fh)
    ImageButton fh;
    @BindView(R.id.save_imagebutton)
    ImageButton saveImagebutton;
    @BindView(R.id.pdck_edittext)
    EditText pdckEdittext;
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

    String jbrId, pdckId;
    private List<Map<String, Object>> list;
//    private LinearLayout xzspLinearLayout;
    BaseAdapter adapter;
    //    LinearLayout                      xzxsddLinearLayout;
    private int selectIndex;
    String billid;//选择完关联的单据后返回的单据的ID
    private String mDepartmentid;//部门ID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_kcpd_add);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        // searchDate();
        getMrck();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {

        xzspListview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                startActivityForResult(new Intent(activity, KcpdSpbjActivity.class)
                        .putExtra("data", (Serializable) list.get(arg2))
                        .putExtra("storeid", pdckId), 4);
//                Intent intent = new Intent();
//                intent.setClass(activity, JxcCkglKcpdXzspDetailActivity.class);
//                intent.putExtra("object", (Serializable) list.get(arg2));
//                startActivityForResult(intent, 4);
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
        adapter = new JxcCkglKcpdAddAdapter(list, this);
        xzspnumTextview.setText("共选择了" + list.size() + "商品");
        xzspListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mDepartmentid = ShareUserInfo.getKey(this, "departmentid");
        etBm.setText(ShareUserInfo.getKey(this, "depname"));
        jbrEdittext.setText(ShareUserInfo.getKey(this, "opname"));
        jbrId =  ShareUserInfo.getKey(this, "empid");
    }

    /**
     * 获取默认仓库信息
     */
    private void getMrck() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("zdbm", "STORE");
        findServiceData2(4, ServerURL.DATADICT, parmMap, false);
    }
    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "CGDD");
        parmMap.put("billid", billid);
        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false);
    }
    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "CGDD");
        parmMap.put("billid", billid);
        findServiceData2(2, ServerURL.BILLDETAIL, parmMap, false);
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (pdckEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择盘点仓库");
            return;
        } else if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        } else if (djrqEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        }

        if (TextUtils.isEmpty(mDepartmentid)){
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
            jsonObject.put("balsid", pdckId);
            jsonObject.put("departmentid", mDepartmentid);
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("memo", bzxxEdittext.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(mContext));
            double amount = 0;
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");//单据ID
                jsonObject2.put("itemno", "0");//明细ID
                jsonObject2.put("goodsid", map.get("goodsid").toString());//商品ID
                jsonObject2.put("unitid", map.get("unitid").toString());//计量单位ID
                jsonObject2.put("unitprice", FigureTools.sswrFigure(map.get("unitprice").toString()));//单价
                jsonObject2.put("unitqty", map.get("yksl").toString());//盈亏数量
                jsonObject2.put("amount", FigureTools.sswrFigure(map.get("amount").toString()));//金额
                if(map.get("cbj")!=null) {
                    jsonObject2.put("refaprice", map.get("cbj")
                            .toString());
                }
                amount += Double.parseDouble(map.get("amount").toString());//
                jsonObject2.put("batchcode", map.get("batchcode").toString());//批号
                jsonObject2.put("produceddate", map.get("produceddate").toString());//生产日期
                jsonObject2.put("validdate", map.get("validdate").toString());//有效期至
                jsonObject2.put("batchrefid", map.get("batchrefid") == null ? "" : map.get("batchrefid").toString());//
                jsonObject2.put("serialinfo", map.get("serialinfo").toString());//序列号GUID
                jsonObject2.put("zmonhand", map.get("zmsl").toString());//账面数量
                jsonObject2.put("sponhand", map.get("spsl").toString());//实盘数量
                int yksl=(int)Double.parseDouble(map.get("spsl").toString())-(int)Double.parseDouble(map.get("zmsl").toString());
                jsonObject2.put("unitqty",  yksl+"");//备注
                jsonObject2.put("memo",  map.get("memo").toString());//备注
                arrayDetail.put(jsonObject2);
                serialinfo.addAll((ArrayList<Serial>) map.get("serials"));
            }
            jsonObject.put("totalamt", FigureTools.sswrFigure(amount ));
            arrayMaster.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_balitem");
        parmMap.put("parms", "KCPD");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        parmMap.put("serialinfo", new Gson().toJson(serialinfo));
        findServiceData2(0, "billsavenew", parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        switch (returnSuccessType){
            case 0:
                if (returnJson.equals("")) {
                    showToastPromopt("保存成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showToastPromopt("保存失败" + returnJson.substring(5));
                }
                break;
            case 1://管理单据成功后把信息填到里面（主表）
                if (returnJson.equals("")) {
                    return;
                }
                Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
                        .paseJsonToObject(returnJson)).get(0);
                djrqEdittext.setText(object.get("billdate").toString());
                jbrEdittext.setText(object.get("empname").toString());
                bzxxEdittext.setText(object.get("memo").toString());
                jbrId = object.get("empid").toString();
                searchDate2();//查询订单中的商品
                break;
            case 2://管理单据成功后把信息填到里面（从表）
                list = (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
                adapter = new JxcCgglCgddDetailAdapter(list, this);
                xzspnumTextview.setText("共选择了" + list.size() + "商品");
                xzspListview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case 4://获取默认仓库信息
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    List<Map<String, Object>> ckList = (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
                    if (ckList.size() == 1) {
                        pdckEdittext.setText(ckList.get(0).get("dictmc").toString());
                        pdckId = ckList.get(0).get("id").toString();
                    }

                }
                break;
        }

    }

    private long time;


    @OnClick({R.id.save_imagebutton, R.id.pdck_edittext, R.id.xzspnum_textview, R.id.iv_scan, R.id.xzsp_linearlayout, R.id.djrq_edittext, R.id.et_bm, R.id.jbr_edittext, R.id.bzxx_edittext})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.xzsp_linearlayout:
                if (pdckEdittext.getText().toString().equals("")) {
                    showToastPromopt("请先选择盘点仓库");
                    return;
                }
                intent.putExtra("storeid", pdckId);
                intent.setClass(this, KcpdXzspActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.iv_scan:
                if (pdckEdittext.getText().toString().equals("")) {
                    showToastPromopt("请先选择盘点仓库");
                    return;
                }
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class), 18);
                break;
            case R.id.djrq_edittext:
                date_init(djrqEdittext);
                break;
            case R.id.et_bm:
                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 15);
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
//            case R.id.jbr_edittext:
//                intent.setClass(activity, CommonXzjbrActivity.class);
//                startActivityForResult(intent, 3);
//                break;
            case R.id.pdck_edittext:
                intent.setClass(activity, CkxzActivity.class);
                startActivityForResult(intent, 6);
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "STORE");
//                startActivityForResult(intent, 6);
                break;
            case R.id.save_imagebutton:
                if (time == 0 || System.currentTimeMillis() - time > 5000) {
                    searchDateSave();//保存
                    time = System.currentTimeMillis();
                } else {
                    showToastPromopt("请不要频繁点击，防止重复保存");

                }
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
                List<KtXzspData> result = new Gson().fromJson(data.getStringExtra("data"), new TypeToken<List<KtXzspData>>() {
                }.getType());
                for (int i = 0; i < result.size(); i++) {
                    KtXzspData ktXzspData = result.get(i);
                    Map<String, Object> map = new HashMap<>();
                    map.put("goodsid", ktXzspData.getGoodsid() + "");
                    map.put("code", ktXzspData.getCode());//编码
                    map.put("name", ktXzspData.getName());//名称
                    map.put("specs", ktXzspData.getSpecs());//规格
                    map.put("model", ktXzspData.getModel());//型号
                    map.put("zmsl",  ktXzspData.getOnhand());
                    map.put("spsl", ktXzspData.getNumber() + "");
                    String yksl = ( ktXzspData.getNumber() -ktXzspData.getOnhand())
                            + "";
                    map.put("yksl", yksl);
                    String amount = (ktXzspData.getAprice() * Double.parseDouble(yksl))
                            + "";
                    map.put("amount",
                            FigureTools.sswrFigure(amount + ""));
                    map.put("onhand", ktXzspData.getOnhand());//库存
                    map.put("unitprice", ktXzspData.getAprice() + "");//单价
                    map.put("unitqty", ktXzspData.getNumber() + "");//数量
                    map.put("unitname", ktXzspData.getUnitname());//单位
                    map.put("unitid", ktXzspData.getUnitid());//单位id
                    map.put("batchctrl", ktXzspData.getBatchctrl());//批次商品T
                    map.put("serialctrl", ktXzspData.getSerialctrl());//严格序列商品T
                    map.put("inf_costingtypeid", ktXzspData.getInfCostingtypeid());//严格序列商品T
                    map.put("disc", "100");
                    map.put("batchcode", ktXzspData.getCpph());//产品批号
                    map.put("batchrefid", ktXzspData.getBatchrefid());//产品批号id
                    map.put("produceddate", ktXzspData.getScrq());//生产日期
                    map.put("validdate", ktXzspData.getYxqz());//有效期至
                    map.put("memo", ktXzspData.getMemo());//备注
                    map.put("serialinfo", ktXzspData.getSerialinfo());
                    map.put("serials", ktXzspData.getMSerials());
                    map.put("cbj", ktXzspData.getCbj());
                    list.add(map);
                }
                xzspnumTextview.setText("共选择了" + list.size() + "商品");

                adapter.notifyDataSetChanged();
//                list.clear();
//                List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
//                        .getSerializableExtra("object");
//                for (int i = 0; i < cpList.size(); i++) {
//                    Map<String, Object> map = cpList.get(i);
//                    if (map.get("isDetail").equals("0")) {
//                        if (map.get("ischecked").equals("1")) {
//                            Map<String, Object> map2 = cpList.get(i + 1);
//                            map.put("zmsl", map2.get("zmsl").toString());
//                            map.put("spsl", map2.get("spsl").toString());
//                            String yksl = (Double.parseDouble(map2.get("spsl")
//                                    .toString()) - Double.parseDouble(map2.get(
//                                    "zmsl").toString()))
//                                    + "";
//                            map.put("yksl", yksl);
//                            String amount = (Double.parseDouble(map2.get("aprice")
//                                    .toString()) * Double.parseDouble(yksl))
//                                    + "";
//                            map.put("amount",
//                                    FigureTools.sswrFigure(amount + ""));
//                            map.put("batchcode", map2.get("cpph"));
//                            map.put("produceddate", map2.get("scrq"));
//                            map.put("validdate", map2.get("yxqz"));
//                            map.put("serialinfo", map2.get("serialinfo").toString());
//                            map.put("serials", map2.get("serials"));
//                            map.put("memo", map2.get("memo"));
//                            list.add(map);
//                        }
//                    }
//                }
//                xzspnumTextview.setText("共选择了" + list.size() + "商品");
//                adapter.notifyDataSetChanged();
            } else if (requestCode == 3) {// 经办人
                jbrEdittext.setText(data.getExtras().getString("name"));
                jbrId = data.getExtras().getString("id");
            } else if (requestCode == 4) {//修改选中的商品的详情
                if (data.getExtras().getSerializable("data").toString().equals("")) {//说明删除了
                    list.remove(selectIndex);
                    adapter.notifyDataSetChanged();
                } else {
                    Map<String, Object> map = (Map<String, Object>) data.getExtras()
                            .getSerializable("data");
                    list.remove(selectIndex);
                    list.add(selectIndex, map);
                    adapter.notifyDataSetChanged();
                }
                xzspnumTextview.setText("共选择了" + list.size() + "商品");
            } else if (requestCode == 5) {//选中单据成功后返回
                billid = data.getExtras().getString("billid");
                searchDate();//查询主表的数据填充
            } else if (requestCode == 6) {// 经办人
                pdckEdittext.setText(data.getExtras().getString("dictmc"));
                pdckId = data.getExtras().getString("id");
                list.clear();
                adapter.notifyDataSetChanged();
            } else if (requestCode == 15) {
                mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                etBm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                jbrId = "";
                jbrEdittext.setText("");
            } else if (requestCode == 16) {
                jbrEdittext.setText(data.getExtras().getString("CHOICE_RESULT_TEXT"));
                jbrId = data.getExtras().getString("CHOICE_RESULT_ID");
            }else if (requestCode == 18) {
                Intent intent = new Intent();
                intent.putExtra("storeid", pdckId);
                intent.putExtra("barcode", data.getStringExtra("qr"));
                intent.setClass(this,  KcpdXzspActivity.class);
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
//                if (pdckEdittext.getText().toString().equals("")) {
//                    showToastPromopt("请先选择盘点仓库");
//                    return;
//                }
//                intent.putExtra("pdckId", pdckId);
//                intent.setClass(this, JxcCkglKcpdXzspActivity.class);
//                startActivityForResult(intent, 0);
//                break;
//            case R.id.djrq_edittext:
//                date_init(djrqEdittext);
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
////            case R.id.jbr_edittext:
////                intent.setClass(activity, CommonXzjbrActivity.class);
////                startActivityForResult(intent, 3);
////                break;
//            case R.id.pdck_edittext:
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "STORE");
//                startActivityForResult(intent, 6);
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
//        }
//    }