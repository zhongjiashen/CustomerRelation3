package com.cr.activity.xjyh.qtsr;

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

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.xm.XmActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.xjyh.fyzc.XjyhFyzcAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.actiity.project.ChoiceProjectActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * 现金银行-其他收入-增加
 *
 * @author Administrator
 */
public class XjyhQtsrAddActivity extends BaseActivity implements
        OnClickListener {
    @BindView(R.id.et_bm)
    EditText etBm;
    private ImageButton saveImageButton;
    private EditText wldwEditText, jslxEditText, zjzhEditText, fkfsEditText,
            fkjeEditText, djrqEditText, jbrEditText, hjjeEditText;
    private String wldwId = "", jslxId = "", zjzhId = "", fkfsId = "",
            jbrId = "";
    private EditText bzxxEditText;

    private CustomListView listview;
    private List<Map<String, Object>> list;
    private LinearLayout xzzcLinearLayout;
    BaseAdapter adapter;
    String billid; // 选择完关联的单据后返回的单据的ID
    private EditText xmEditText;
    private String xmId;
    private long time;
    private String mDepartmentid;//部门ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjyh_qtsr_add);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        // searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        xmEditText = (EditText) findViewById(R.id.xm_edittext);
        xmEditText.setOnClickListener(this);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        listview = (CustomListView) findViewById(R.id.xzsp_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // Intent intent = new Intent();
                // intent.setClass(activity,
                // JxcCgglCgddXzspDetailActivity.class);
                // intent.putExtra("object", (Serializable) list.get(arg2));
                // startActivityForResult(intent, 4);
            }
        });
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
        wldwEditText = (EditText) findViewById(R.id.wldw_edittext);
        wldwEditText.setOnClickListener(this);
        jslxEditText = (EditText) findViewById(R.id.jslx_edittext);
        jslxEditText.setOnClickListener(this);
        zjzhEditText = (EditText) findViewById(R.id.zjzh_edittext);
        zjzhEditText.setOnClickListener(this);
        fkfsEditText = (EditText) findViewById(R.id.fkfs_edittext);
        fkfsEditText.setOnClickListener(this);
        djrqEditText = (EditText) findViewById(R.id.djrq_edittext);
        djrqEditText.setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        djrqEditText.setText(sdf.format(new Date()));
        jbrEditText = (EditText) findViewById(R.id.jbr_edittext);
        jbrEditText.setOnClickListener(this);
        fkjeEditText = (EditText) findViewById(R.id.fkje_edittext);
        hjjeEditText = (EditText) findViewById(R.id.hjje_edittext);
        xzzcLinearLayout = (LinearLayout) findViewById(R.id.xzzc_linearlayout);
        xzzcLinearLayout.setOnClickListener(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new XjyhFyzcAddAdapter(list, this);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        mDepartmentid = ShareUserInfo.getKey(this, "departmentid");
        etBm.setText(ShareUserInfo.getKey(this, "depname"));
        jbrEditText.setText(ShareUserInfo.getKey(this, "opname"));
        jbrId = ShareUserInfo.getKey(this, "empid");

        jslxEditText.setText("往来结算");
        jslxId = "0";
        fkjeEditText.setText("0");
        fkjeEditText.setEnabled(false);
        zjzhEditText.setEnabled(false);
        fkfsEditText.setEnabled(false);
        zjzhEditText.setText("");
        zjzhId = "";
        fkfsEditText.setText("");
        fkfsId = "";
    }

    @OnClick(R.id.et_bm)
    public void onClick() {
        startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 15);
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.xzzc_linearlayout:
                intent.setClass(this, XjyhQtsrAddZcActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.wldw_edittext:
                intent.setClass(this, CommonXzdwActivity.class);
                intent.putExtra("type", "0");//
                startActivityForResult(intent, 1);
                break;
            case R.id.jslx_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "FKLX");
                startActivityForResult(intent, 4);
                break;
            case R.id.zjzh_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "BANK");
                startActivityForResult(intent, 3);
                break;
            case R.id.fkfs_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "PAYTYPE");
                startActivityForResult(intent, 2);
                break;
            case R.id.djrq_edittext:
                date_init(djrqEditText);
                break;
            case R.id.jbr_edittext:
                if (TextUtils.isEmpty(mDepartmentid))
                    showToastPromopt("请先选择部门");
                else
                    startActivityForResult(new Intent(this, SelectSalesmanActivity.class)
                            .putExtra("depid", mDepartmentid), 16);
//                intent.setClass(activity, CommonXzjbrActivity.class);
//                startActivityForResult(intent, 5);
                break;
            case R.id.save_imagebutton:
                if (time == 0 || System.currentTimeMillis() - time > 5000) {
                    searchDateSave();//保存
                    time = System.currentTimeMillis();
                } else {
                    showToastPromopt("请不要频繁点击，防止重复保存");

                }
                break;
            case R.id.xm_edittext:
                startActivityForResult(new Intent(this, ChoiceProjectActivity.class)
                        ,
                        12);
//                intent.setClass(activity, XmActivity.class);
//                startActivityForResult(intent, 12);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 15://选择部门
                    mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                    etBm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                    jbrId = "";
                    jbrEditText.setText("");
                    break;
                case 16://选择业务员
                    jbrEditText.setText(data.getExtras().getString("CHOICE_RESULT_TEXT"));
                    jbrId = data.getExtras().getString("CHOICE_RESULT_ID");
                    break;
            }
            if (requestCode == 0) {// 增加费用
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", data.getExtras().getString("name"));
                map.put("amount", data.getExtras().getString("amount"));
                map.put("ietypeid", data.getExtras().getString("ietypeid"));
                list.add(map);
                adapter.notifyDataSetChanged();
                double hjfy = 0;
                for (Map<String, Object> m : list) {
                    hjfy += Double.parseDouble(m.get("amount").toString());
                }
                hjjeEditText.setText(FigureTools.sswrFigure(hjfy));
            } else if (requestCode == 1) {// 往来单位
                wldwEditText.setText(data.getExtras().getString("cname"));
                wldwId = data.getExtras().getString("id");
            } else if (requestCode == 2) {// 付款方式
                fkfsEditText.setText(data.getExtras().getString("dictmc"));
                fkfsId = data.getExtras().getString("id");
            } else if (requestCode == 3) {// 资金账户
                zjzhEditText.setText(data.getExtras().getString("dictmc"));
                zjzhId = data.getExtras().getString("id");
            } else if (requestCode == 4) {// 结算类型
                jslxEditText.setText(data.getExtras().getString("dictmc"));
                jslxId = data.getExtras().getString("id");
                if (data.getExtras().getString("id").equals("0")) {
                    fkjeEditText.setText("0");
                    fkjeEditText.setEnabled(false);
                    zjzhEditText.setEnabled(false);
                    fkfsEditText.setEnabled(false);
                    zjzhEditText.setText("");
                    zjzhId = "";
                    fkfsEditText.setText("");
                    fkfsId = "";
                } else {
                    fkjeEditText.setEnabled(true);
                    fkfsEditText.setEnabled(true);
                    zjzhEditText.setEnabled(true);
                }
            } else if (requestCode == 5) {// 经办人
                jbrEditText.setText(data.getExtras().getString("name"));
                jbrId = data.getExtras().getString("id");
            } else if (requestCode == 12) {
                xmEditText.setText(data.getStringExtra("title"));
                xmId = data.getStringExtra("projectid");
//                xmEditText.setText(data.getExtras().getString("xmname"));
//                xmId = data.getExtras().getString("xmid");
            }
        }
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (wldwEditText.getText().toString().equals("")) {
            showToastPromopt("请选择往来单位");
            return;
        }
        if (jslxEditText.getText().toString().equals("")) {
            showToastPromopt("请选择结算类型");
            return;
        }
        if (jslxId.equals("1")) {
            if (zjzhEditText.getText().toString().equals("")) {
                showToastPromopt("请选择资金账户");
                return;
            }
//			if (fkfsEditText.getText().toString().equals("")) {
//				showToastPromopt("请选择结算方式");
//				return;
//			}
            if (list.size() == 0) {
                showToastPromopt("请增收入类型");
                return;
            }
            double hjje = 0;
            for (Map<String, Object> m : list) {
                hjje += Double.parseDouble(m.get("amount").toString()
                        .replace("￥", "").equals("") ? "0" : m.get("amount")
                        .toString().replace("￥", ""));
            }
            double fkje = Double.parseDouble(fkjeEditText.getText().toString()
                    .replace("￥", "").equals("") ? "0" : fkjeEditText.getText()
                    .toString().replace("￥", ""));
            if (fkje <= 0 || fkje > hjje) {
                showToastPromopt("收款金额不在范围内！");
                return;
            }
        }
        if (list.size() == 0) {
            showToastPromopt("请增加收入类型");
            return;
        }
        if (djrqEditText.getText().toString().equals("")) {
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
            jsonObject.put("billid", "0");
            jsonObject.put("code", "");
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("clientid", wldwId);
            jsonObject.put("paytypeid", fkfsId);
            jsonObject.put("ispc", jslxId);
            jsonObject.put("bankid", zjzhId);
            jsonObject.put("receipt", fkjeEditText.getText().toString());
            jsonObject.put("amount", hjjeEditText.getText().toString());
            jsonObject.put("departmentid ", mDepartmentid);
            jsonObject.put("empid", jbrId);
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(context));
            jsonObject.put("projectid", xmId);
            arrayMaster.put(jsonObject);
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");
                jsonObject2.put("itemno", "0");
                jsonObject2.put("ietypeid", map.get("ietypeid").toString());
                jsonObject2.put("amount ", map.get("amount").toString());
                arrayDetail.put(jsonObject2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }// 代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_income");
        parmMap.put("parms", "QTSR");
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
        } else if (returnSuccessType == 1) {// 管理单据成功后把信息填到里面（主表）
            if (returnJson.equals("")) {
                return;
            }
            Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson)).get(0);
            djrqEditText.setText(object.get("billdate").toString());
            jbrEditText.setText(object.get("empname").toString());
            jbrId = object.get("empid").toString();
            // searchDate2();//查询订单中的商品
        } else if (returnSuccessType == 2) {// 管理单据成功后把信息填到里面（从表）
            list = (List<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson);
            adapter = new JxcCgglCgddDetailAdapter(list, this);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}