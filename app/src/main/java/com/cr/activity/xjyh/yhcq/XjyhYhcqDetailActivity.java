package com.cr.activity.xjyh.yhcq;

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
 * 现金银行-银行存取-详情
 *
 * @author Administrator
 */
public class XjyhYhcqDetailActivity extends BaseActivity implements
        OnClickListener {
    @BindView(R.id.et_bm)
    EditText etBm;
    private ImageView shImageView;
    private ImageButton saveImageButton;
    private TextView djbhTextView;
    private EditText zczhEditText, zrzhEditText, jeEditText, djrqEditText,
            jbrEditText, bzxxEditText;
    private String zczhId, zrzhId, jbrId;
    private String shzt; // 社会状态
    Map<String, Object> object;
    private Button shButton, sdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjyh_yhcq_detail);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        shButton = (Button) findViewById(R.id.sh_button);
        shButton.setOnClickListener(this);
        sdButton = (Button) findViewById(R.id.sd_button);
        sdButton.setOnClickListener(this);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        djbhTextView = (TextView) findViewById(R.id.djbh_textview);
        shImageView = (ImageView) findViewById(R.id.sh_imageview);
        zczhEditText = (EditText) findViewById(R.id.zczh_edittext);
        zczhEditText.setOnClickListener(this);
        zrzhEditText = (EditText) findViewById(R.id.zrzh_edittext);
        zrzhEditText.setOnClickListener(this);
        jeEditText = (EditText) findViewById(R.id.je_edittext);
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
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "YHCQ");
        parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
        findServiceData2(0, ServerURL.BILLMASTER, parmMap, false);
    }

    /**
     * 连接网络的操作(删单)
     */
    private void searchDateSd() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_movemoney");
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
            object = ((List<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson)).get(0);
            djbhTextView.setText(object.get("code").toString());
            if (object.get("shzt").toString().equals("0")) {
                shImageView.setBackgroundResource(R.drawable.wsh);
            } else if (object.get("shzt").toString().equals("1")) {
                shImageView.setBackgroundResource(R.drawable.ysh);
            } else if (object.get("shzt").toString().equals("2")) {
                shImageView.setBackgroundResource(R.drawable.shz);
            }
            shzt = object.get("shzt").toString();
            etBm.setText(object.get("depname").toString());
            jbrEditText.setText(object.get("empname").toString());
            jbrId = object.get("empid").toString();
            bzxxEditText.setText(object.get("memo").toString());
            zczhEditText.setText(object.get("outbankname").toString());
            zczhId = object.get("outbankid").toString();
            zrzhEditText.setText(object.get("inbankname").toString());
            zrzhId = object.get("inbankid").toString();
            djrqEditText.setText(object.get("billdate").toString());
            jeEditText.setText(object.get("amount").toString());
            showZdr(object);
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
        if (zczhEditText.getText().toString().equals("")) {
            showToastPromopt("请选择转出账户 ");
            return;
        } else if (zrzhEditText.getText().toString().equals("")) {
            showToastPromopt("请选择转入账户 ");
            return;
        } else if (djrqEditText.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        } else if (jeEditText.getText().toString().equals("")) {
            showToastPromopt("金额不能为空");
            return;
        } else if (Double.parseDouble(jeEditText.getText().toString()) <= 0) {
            showToastPromopt("金额必须大于0");
            return;
        }
        if (jbrEditText.getText().toString().equals("")) {
            showToastPromopt("请选择经办人");
            return;
        }
        if (zczhId.equals(zrzhId)) {
            showToastPromopt("转出账户和转入账户不能相同");
            return;
        }
        JSONArray arrayMaster = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid",
                    this.getIntent().getExtras().getString("billid"));
            jsonObject.put("code", object.get("code").toString());
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("outbankid", zczhId);
            jsonObject.put("inbankid", zrzhId);
            jsonObject.put("amount", jeEditText.getText().toString());
            jsonObject.put("empid", jbrId);
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(context));
            arrayMaster.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }// 代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_movemoney");
        parmMap.put("parms", "YHCQ");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", "");
        findServiceData2(3, ServerURL.BILLSAVE, parmMap, false);
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
//		case R.id.zczh_edittext:
//			intent.setClass(activity, CommonXzzdActivity.class);
//			intent.putExtra("type", "BANK");
//			startActivityForResult(intent, 1);
//			break;
//		case R.id.zrzh_edittext:
//			intent.setClass(activity, CommonXzzdActivity.class);
//			intent.putExtra("type", "BANK");
//			startActivityForResult(intent, 2);
//			break;
//		case R.id.djrq_edittext:
//			date_init(djrqEditText);
//			break;
//		case R.id.jbr_edittext:
//			intent.setClass(activity, CommonXzjbrActivity.class);
//			startActivityForResult(intent, 3);
//			break;
//		case R.id.save_imagebutton:
//			searchDateSave();// 保存
//			break;
            case R.id.sh_button:
                intent.putExtra("tb", "tb_movemoney");
                intent.putExtra("opid", object.get("opid").toString());
                intent.putExtra("billid",
                        this.getIntent().getExtras().getString("billid"));
                intent.setClass(activity, JxcCgglCgddShlcActivity.class);
                startActivityForResult(intent, 4);
                break;
            case R.id.sd_button:
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
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {//

            } else if (requestCode == 1) {// 转出账户
                zczhEditText.setText(data.getExtras().getString("dictmc"));
                zczhId = data.getExtras().getString("id");
            } else if (requestCode == 2) {// 结算类型
                zrzhEditText.setText(data.getExtras().getString("dictmc"));
                zrzhId = data.getExtras().getString("id");
            } else if (requestCode == 3) {// 资金账户
                jbrEditText.setText(data.getExtras().getString("name"));
                jbrId = data.getExtras().getString("id");
            } else if (requestCode == 4) {
                searchDate();
            }
        }
    }
}
