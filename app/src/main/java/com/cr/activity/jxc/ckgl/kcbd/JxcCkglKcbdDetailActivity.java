package com.cr.activity.jxc.ckgl.kcbd;

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
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcActivity;
import com.cr.adapter.jxc.ckgl.kcbd.JxcCkglKcbdAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 进销存-仓库管理-库存变动-详情
 *
 * @author Administrator
 */
public class JxcCkglKcbdDetailActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.et_bm)
    EditText etBm;
    private ImageView shImageView;
    private ImageButton saveImageButton;
    private Button shButton, sdButton;
    private TextView xzspnumTextView, djbhTextView;
    private EditText bdlxEditText, bdckEditText, bzxxEditText, djrqEditText, jbrEditText;
    private CustomListView listview;
    String jbrId, bdlxId, bdckId;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private LinearLayout xzspLinearLayout;
    BaseAdapter adapter;
    private int selectIndex;
    private String shzt = "";                                               //社会状态
    Map<String, Object> object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_kcbd_detail);
        ButterKnife.bind(this);
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
                intent.setClass(activity, JxcCkglKcbdXzspDetail2Activity.class);
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });
        xzspLinearLayout = (LinearLayout) findViewById(R.id.xzsp_linearlayout);
        xzspLinearLayout.setOnClickListener(this);
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
        shButton = (Button) findViewById(R.id.sh_button);
        shButton.setOnClickListener(this);
        sdButton = (Button) findViewById(R.id.sd_button);
        sdButton.setOnClickListener(this);
        bdlxEditText = (EditText) findViewById(R.id.bdlx_edittext);
        bdlxEditText.setOnClickListener(this);
        bdckEditText = (EditText) findViewById(R.id.bdck_edittext);
        bdckEditText.setOnClickListener(this);
        adapter = new JxcCkglKcbdAddAdapter(list, this);
        listview.setAdapter(adapter);
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "KCBD");
        parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
        findServiceData2(0, ServerURL.BILLMASTER, parmMap, false);
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "KCBD");
        parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
        findServiceData2(1, ServerURL.BILLDETAIL, parmMap, false);
    }

    /**
     * 连接网络的操作(删单)
     */
    private void searchDateSd() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("opid", ShareUserInfo.getUserId(mContext));
        parmMap.put("tabname", "tb_inout");
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
            object = ((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson)).get(0);
            djbhTextView.setText(object.get("code").toString());
            djrqEditText.setText(object.get("billdate").toString());
            if (object.get("depname") != null) {
                etBm.setText(object.get("depname").toString());
            }
            jbrEditText.setText(object.get("empname").toString());
            bzxxEditText.setText(object.get("memo").toString());
            if (object.get("shzt").toString().equals("0")) {
                shImageView.setBackgroundResource(R.drawable.wsh);
            } else if (object.get("shzt").toString().equals("1")) {
                shImageView.setBackgroundResource(R.drawable.ysh);
            } else if (object.get("shzt").toString().equals("2")) {
                shImageView.setBackgroundResource(R.drawable.shz);
            }
            jbrId = object.get("exemanid").toString();
            shzt = object.get("shzt").toString();
            bdlxId = object.get("satid").toString();
            bdlxEditText.setText(object.get("satname").toString());
            bdckId = object.get("storeid").toString();
            bdckEditText.setText(object.get("storename").toString());
            showZdr(object);
            searchDate2();//查询订单中的商品
        } else if (returnSuccessType == 1) {
            if (returnJson.equals("")) {
                return;
            }
            list.clear();
            list.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
            xzspnumTextView.setText("共选择了" + list.size() + "商品");
            adapter.notifyDataSetChanged();
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
        if (bdckEditText.getText().toString().equals("")) {
            showToastPromopt("请选择变动仓库");
            return;
        } else if (bdlxEditText.getText().toString().equals("")) {
            showToastPromopt("请选择变动类型");
            return;
        } else if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        } else if (djrqEditText.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        }
        if (jbrEditText.getText().toString().equals("")) {
            showToastPromopt("请选择业务员");
            return;
        }
        JSONArray arrayMaster = new JSONArray();
        JSONArray arrayDetail = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid", this.getIntent().getExtras().getString("billid"));
            jsonObject.put("code", object.get("code").toString());
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("satid", bdlxId);
            jsonObject.put("storeid", bdckId);
            jsonObject.put("totalamt", "0");
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(mContext));
            arrayMaster.put(jsonObject);
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", this.getIntent().getExtras().getString("billid"));
                jsonObject2.put("itemno", "0");
                jsonObject2.put("goodsid", map.get("goodsid").toString());
                jsonObject2.put("unitid", map.get("unitid").toString());
                jsonObject2.put("unitprice", "");
                jsonObject2.put("unitqty", map.get("bdsl") == null ? map.get("unitqty").toString() : map.get("bdsl").toString());
                jsonObject2.put("amount", "0");
                jsonObject2.put("batchcode", "");
                jsonObject2.put("produceddate", "");
                jsonObject2.put("validdate", "");
                jsonObject2.put("batchrefid", map.get("batchrefid") == null ? "" : map.get("batchrefid").toString());
                arrayDetail.put(jsonObject2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        //      parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_inout");
        parmMap.put("parms", "KCBD");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        findServiceData2(3, ServerURL.BILLSAVE, parmMap, false);
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.sh_button:
                intent.putExtra("tb", "tb_inout");
                intent.putExtra("opid", object.get("opid").toString());
                intent.putExtra("billid", this.getIntent().getExtras().getString("billid"));
                intent.setClass(activity, JxcCgglCgddShlcActivity.class);
                startActivityForResult(intent, 8);
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
//            	if(bdckEditText.getText().toString().equals("")){
//            		showToastPromopt("请先选择变动仓库！");
//            		return;
//            	}
//                intent.setClass(this, JxcCkglKcbdXzspActivity.class);
//                intent.putExtra("storeId", bdckId);
//                startActivityForResult(intent, 0);
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
//            case R.id.bdlx_edittext:
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "KCBD");
//                startActivityForResult(intent, 6);
//                break;
//            case R.id.bdck_edittext:
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
//                list.clear();
                List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
                        .getSerializableExtra("object");
                for (int i = 0; i < cpList.size(); i++) {
                    Map<String, Object> map = cpList.get(i);
                    if (map.get("isDetail").equals("0")) {
                        if (map.get("ischecked").equals("1")) {
                            Map<String, Object> map2 = cpList.get(i + 1);
                            map.put("bdsl", map2.get("bdsl"));
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
            } else if (requestCode == 6) {// 变动类型
                bdlxEditText.setText(data.getExtras().getString("dictmc"));
                bdlxId = data.getExtras().getString("id");
            } else if (requestCode == 7) {// 变动仓库
                bdckEditText.setText(data.getExtras().getString("dictmc"));
                bdckId = data.getExtras().getString("id");
            } else if (requestCode == 8) {
                searchDate();
                setResult(RESULT_OK);
            }
        }
    }
}
