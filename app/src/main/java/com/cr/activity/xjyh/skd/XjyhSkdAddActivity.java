package com.cr.activity.xjyh.skd;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.SelectSalesmanActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 现金银行-收款单-增加
 *
 * @author Administrator
 */
public class XjyhSkdAddActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.et_bm)
    EditText etBm;
    private ImageButton saveImageButton;
    private EditText wldwEditText, fklxEditText, jsfsEditText, zjzhEditText, dqyfEditText,
            dqyf2EditText, fkjeEditText, djrqEditText, jbrEditText;
    private String wldwId, fklxId, jsfsId, zjzhId, jbrId;

    String billid;                                    // 选择完关联的单据后返回的单据的ID
    private EditText bzxxEditText;
    private long time;

    private String mDepartmentid;//部门ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjyh_skd_add);
        ButterKnife.bind(this);
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
        wldwEditText = (EditText) findViewById(R.id.wldw_edittext);
        wldwEditText.setOnClickListener(this);
        fklxEditText = (EditText) findViewById(R.id.fklx_edittext);
        fklxEditText.setOnClickListener(this);
        jsfsEditText = (EditText) findViewById(R.id.jsfs_edittext);
        jsfsEditText.setOnClickListener(this);
        zjzhEditText = (EditText) findViewById(R.id.zjzh_edittext);
        zjzhEditText.setOnClickListener(this);
        dqyfEditText = (EditText) findViewById(R.id.dqyf_edittext);
        dqyf2EditText = (EditText) findViewById(R.id.dqyf2_edittext);
        fkjeEditText = (EditText) findViewById(R.id.fkje_edittext);
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

        mDepartmentid = ShareUserInfo.getKey(this, "departmentid");
        etBm.setText(ShareUserInfo.getKey(this, "depname"));
        jbrEditText.setText(ShareUserInfo.getKey(this, "opname"));
        jbrId =  ShareUserInfo.getKey(this, "empid");

        fklxEditText.setText("往来收款");
        fklxId ="0";
    }


    @OnClick(R.id.et_bm)
    public void onClick() {
        startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 15);
    }
    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.wldw_edittext:
                intent.setClass(this, CommonXzdwActivity.class);
                intent.putExtra("type", "");// 供应商
                startActivityForResult(intent, 1);
                break;
            case R.id.jsfs_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "PAYTYPE");
                startActivityForResult(intent, 2);
                break;
            case R.id.zjzh_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "BANK");
                startActivityForResult(intent, 3);
                break;
            case R.id.fklx_edittext://收款类型
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "SKDFKLX");
                startActivityForResult(intent, 4);
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
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
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
                //				Map<String, Object> map = new HashMap<String, Object>();
                //				map.put("name", data.getExtras().getString("name"));
                //				map.put("je", data.getExtras().getString("je"));
                //				list.add(map);
                //				adapter.notifyDataSetChanged();
            } else if (requestCode == 1) {// 往来单位
                wldwEditText.setText(data.getExtras().getString("cname"));
                wldwId = data.getExtras().getString("id");
                String types = data.getExtras().getString("types");
                if (types.equals("1") || types.equals("4")) {
                    dqyfEditText.setText(data.getExtras().getString("yf"));
                    dqyf2EditText.setText(data.getExtras().getString("yf2"));
                } else {
                    double yf = Double.parseDouble(data.getExtras().getString("yf"));
                    double yf2 = Double.parseDouble(data.getExtras().getString("yf2"));
                    dqyfEditText.setText((yf * -1) + "");
                    dqyf2EditText.setText((yf2 * -1) + "");
                }
            } else if (requestCode == 2) {// 结算类型
                jsfsEditText.setText(data.getExtras().getString("dictmc"));
                jsfsId = data.getExtras().getString("id");
            } else if (requestCode == 3) {// 资金账户
                zjzhEditText.setText(data.getExtras().getString("dictmc"));
                zjzhId = data.getExtras().getString("id");
            } else if (requestCode == 4) {// 付款方式
                fklxEditText.setText(data.getExtras().getString("dictmc"));
                fklxId = data.getExtras().getString("id");
                //				if(data.getExtras().getString("id").equals("0")){
                //                	fkjeEditText.setText("0");
                //                	fkjeEditText.setEnabled(false);
                //                	jsfsEditText.setEnabled(false);
                //                	zjzhEditText.setEnabled(false);
                //                	jsfsEditText.setText("");
                //                	zjzhEditText.setText("");
                //                	jsfsId="";
                //                	zjzhId="";
                //                }else{
                //                	fkjeEditText.setEnabled(true);
                //                	jsfsEditText.setEnabled(true);
                //                	zjzhEditText.setEnabled(true);
                //                }
            } else if (requestCode == 5) {// 经办人
                jbrEditText.setText(data.getExtras().getString("name"));
                jbrId = data.getExtras().getString("id");
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
        if (fklxEditText.getText().toString().equals("")) {
            showToastPromopt("请选择收款类型");
            return;
        }

//        if (jsfsEditText.getText().toString().equals("")) {
//            showToastPromopt("请选择结算方式");
//            return;
//        }
        if (zjzhEditText.getText().toString().equals("")) {
            showToastPromopt("请选择资金账户");
            return;
        }
        if (fkjeEditText.getText().toString().equals("")) {
            showToastPromopt("请填写收款金额");
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
        //		JSONArray arrayDetail = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid", "0");
            jsonObject.put("code", "");
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("ispc", fklxId);
            jsonObject.put("clientid", wldwId);
            jsonObject.put("departmentid ", mDepartmentid);
            jsonObject.put("exemanid", jbrId);
            jsonObject.put("paytypeid", jsfsId);
            jsonObject.put("opid", ShareUserInfo.getUserId(context));
            jsonObject.put("bankid", zjzhId);
            jsonObject.put("amount", fkjeEditText.getText().toString());
            jsonObject.put("factamount", fkjeEditText.getText().toString());
            jsonObject.put("memo", bzxxEditText.getText().toString());
            arrayMaster.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }// 代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_charge_skd");
        parmMap.put("parms", "SKD");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", "");
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
        }
    }
}