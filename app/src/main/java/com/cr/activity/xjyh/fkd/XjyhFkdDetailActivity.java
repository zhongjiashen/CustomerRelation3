package com.cr.activity.xjyh.fkd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcActivity;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 现金银行-付款单-详情
 *
 * @author Administrator
 */
public class XjyhFkdDetailActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.et_bm)
    EditText etBm;
    private ImageView shImageView;
    private ImageButton saveImageButton;
    private TextView djbhTextView;
    private Button shButton, sdButton;
    private EditText wldwEditText, fklxEditText, jsfsEditText, zjzhEditText, dqyfEditText,
            dqyf2EditText, fkjeEditText, djrqEditText, bzxxEditText, jbrEditText;
    private String wldwId, fklxId, jsfsId, zjzhId, jbrId;
    private String shzt;                                                    // 社会状态
    Map<String, Object> object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjyh_fkd_detail);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        shImageView = (ImageView) findViewById(R.id.sh_imageview);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        djbhTextView = (TextView) findViewById(R.id.djbh_textview);
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
        shButton = (Button) findViewById(R.id.sh_button);
        shButton.setOnClickListener(this);
        sdButton = (Button) findViewById(R.id.sd_button);
        sdButton.setOnClickListener(this);
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "FKD");
        parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
        findServiceData2(0, ServerURL.BILLMASTER, parmMap, false);
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    //	private void searchDate2() {
    //		Map<String, Object> parmMap = new HashMap<String, Object>();
    //		parmMap.put("dbname", ShareUserInfo.getDbName(context));
    //		parmMap.put("parms", "FKD");
    //		parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
    //		findServiceData2(1, ServerURL.BILLDETAIL, parmMap, false);
    //	}

    /**
     * 连接网络的操作(删单)
     */
    private void searchDateSd() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_pay_fkd");
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
            if (object.get("shzt").toString().equals("0")) {
                shImageView.setBackgroundResource(R.drawable.wsh);
            } else if (object.get("shzt").toString().equals("1")) {
                shImageView.setBackgroundResource(R.drawable.ysh);
            } else if (object.get("shzt").toString().equals("2")) {
                shImageView.setBackgroundResource(R.drawable.shz);
            }
            djbhTextView.setText(object.get("code").toString());
            wldwEditText.setText(object.get("cname").toString());
            wldwId = object.get("clientid").toString();
            if (object.get("ispp").toString().equals("0")) {
                fklxEditText.setText("往来付款");
            } else if (object.get("ispp").toString().equals("1")) {
                fklxEditText.setText("预付账款");
            }
            fklxId = object.get("ispp").toString();
            //			if(object.get("ispp").toString().equals("0")){
            //            	fkjeEditText.setText("0");
            //            	fkjeEditText.setEnabled(false);
            //            	jsfsEditText.setEnabled(false);
            //            	zjzhEditText.setEnabled(false);
            //            }else{
            //            	fkjeEditText.setEnabled(true);
            //            	jsfsEditText.setEnabled(true);
            //            	zjzhEditText.setEnabled(true);
            //            }
            jsfsEditText.setText(object.get("paytypename").toString());
            jsfsId = object.get("paytypeid").toString();
            djrqEditText.setText(object.get("billdate").toString());
            etBm.setText(object.get("depname").toString());
            jbrEditText.setText(object.get("empname").toString());
            jbrId = object.get("exemanid").toString();
            bzxxEditText.setText(object.get("memo").toString());
            shzt = object.get("shzt").toString();
            zjzhEditText.setText(object.get("bankname").toString());
            zjzhId = object.get("bankid").toString();
            dqyfEditText.setText(object.get("suramt").toString());
            dqyf2EditText.setText(object.get("balance").toString());
            fkjeEditText.setText(object.get("amount").toString());
            showZdr(object);
            //			searchDate2();// 查询订单中的商品
        } else if (returnSuccessType == 1) {
            //			list .addAll((List<Map<String, Object>>) PaseJson
            //					.paseJsonToObject(returnJson));
            //			adapter.notifyDataSetChanged();
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
                showToastPromopt("保存成功");
                setResult(RESULT_OK);
                finish();
            } else {
                showToastPromopt("保存失败" + returnJson.substring(5));
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
            showToastPromopt("请选择付款类型");
            return;
        }
        if (jsfsEditText.getText().toString().equals("")) {
            showToastPromopt("请选择结算方式");
            return;
        }
        if (zjzhEditText.getText().toString().equals("")) {
            showToastPromopt("请选择资金账户");
            return;
        }
        if (djrqEditText.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        }
        if (fkjeEditText.getText().toString().equals("")) {
            showToastPromopt("请填写付款金额");
            return;
        }
        if (jbrEditText.getText().toString().equals("")) {
            showToastPromopt("请选择业务员");
            return;
        }
        JSONArray arrayMaster = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid", this.getIntent().getExtras().getString("billid"));
            jsonObject.put("code", object.get("code").toString());
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("ispp", fklxId);
            jsonObject.put("clientid", wldwId);
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
        parmMap.put("tabname", "tb_pay_fkd");
        parmMap.put("parms", "FKD");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", "");
        findServiceData2(3, ServerURL.BILLSAVE, parmMap, false);
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.sh_button:
                intent.putExtra("tb", "tb_pay_fkd");
                intent.putExtra("opid", object.get("opid").toString());
                intent.putExtra("billid", this.getIntent().getExtras().getString("billid"));
                intent.setClass(activity, JxcCgglCgddShlcActivity.class);
                startActivityForResult(intent, 6);
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
//            case R.id.save_imagebutton:
//                searchDateSave();// 保存
//                break;
//            case R.id.wldw_edittext:
//                intent.setClass(this, CommonXzdwActivity.class);
//                intent.putExtra("type", "");// 供应商
//                startActivityForResult(intent, 1);
//                break;
//            case R.id.jsfs_edittext:
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "PAYTYPE");
//                startActivityForResult(intent, 2);
//                break;
//            case R.id.zjzh_edittext:
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "BANK");
//                startActivityForResult(intent, 3);
//                break;
//            case R.id.fklx_edittext:
//                intent.setClass(activity, CommonXzzdActivity.class);
//                intent.putExtra("type", "FKDFKLX");
//                startActivityForResult(intent, 4);
//                break;
//            case R.id.djrq_edittext:
//                date_init(djrqEditText);
//                break;
//            case R.id.jbr_edittext:
//                intent.setClass(activity, CommonXzjbrActivity.class);
//                startActivityForResult(intent, 5);
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {// 往来单位
                wldwEditText.setText(data.getExtras().getString("cname"));
                wldwId = data.getExtras().getString("id");
                String types = data.getExtras().getString("types");
                if (types.equals("1") || types.equals("4")) {
                    double yf = Double.parseDouble(data.getExtras().getString("yf"));
                    double yf2 = Double.parseDouble(data.getExtras().getString("yf2"));
                    dqyfEditText.setText((yf * -1) + "");
                    dqyf2EditText.setText((yf2 * -1) + "");
//                    dqyfEditText.setText("-1" + data.getExtras().getString("yf"));
//                    dqyf2EditText.setText("-1" + data.getExtras().getString("yf2"));
                } else {
                    dqyfEditText.setText(data.getExtras().getString("yf"));
                    dqyf2EditText.setText(data.getExtras().getString("yf2"));
                }
//                dqyfEditText.setText(data.getExtras().getString("yf"));
//                dqyf2EditText.setText(data.getExtras().getString("yf2"));
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
            } else if (requestCode == 6) {
                searchDate();
                setResult(RESULT_OK);
            }
        }
    }
}
