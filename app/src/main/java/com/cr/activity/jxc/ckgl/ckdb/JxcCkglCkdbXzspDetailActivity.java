package com.cr.activity.jxc.ckgl.ckdb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.SLView2;
import com.cr.activity.common.CommonXzphActivity;
import com.cr.activity.jxc.JxcTjXlhActivity;
import com.cr.activity.jxc.XzXlhActivity;
import com.cr.activity.jxc.ckgl.kcpd.KtSerialNumberAddActivity;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.model.Serial;

/**
 * 进销存-仓库管理-仓库调拨-选择商品-选择的商品的详细信息
 *
 * @author Administrator
 */
public class JxcCkglCkdbXzspDetailActivity extends BaseActivity implements
        OnClickListener {
    TextView mcTextView;
    TextView bhTextView;
    TextView ggTextView;
    TextView xhTextView;
    TextView kcTextView;
    TextView scTextView;
    ImageButton saveImageButton;
    EditText djEditText;
    EditText zklEditText;
    EditText zjEditText;
    SLView2 slView;
    TextView tvSl;
    EditText cpphEditText;
    EditText scrqEditText;
    EditText yxqzEditText;
    EditText dwEditText;
    String cpphId;

    LinearLayout cpphLayout;
    LinearLayout scrqLayout;
    LinearLayout yxrqLayout;
    View cpphView;
    View scrqView;
    View yxrqView;
    Map<String, Object> object = new HashMap<String, Object>();
    TextView tvSerialNumber;
    EditText etBz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_ckdb_xzsp_detail);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    @SuppressWarnings("unchecked")
    private void initActivity() {
        mcTextView = (TextView) findViewById(R.id.mc_textview);
        bhTextView = (TextView) findViewById(R.id.bh_textview);
        ggTextView = (TextView) findViewById(R.id.gg_textview);
        xhTextView = (TextView) findViewById(R.id.xh_textview);
        kcTextView = (TextView) findViewById(R.id.kc_textview);
        scTextView = (TextView) findViewById(R.id.sc_textview);
        tvSl = findViewById(R.id.tv_sl);
        scTextView.setOnClickListener(this);
        tvSerialNumber = findViewById(R.id.tv_serial_number);
        tvSerialNumber.setOnClickListener(this);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        djEditText = (EditText) findViewById(R.id.dj_edittext);
        zklEditText = (EditText) findViewById(R.id.zkl_edittext);
        cpphEditText = (EditText) findViewById(R.id.cpph_edittext);
        cpphEditText.setOnClickListener(this);
        scrqEditText = (EditText) findViewById(R.id.scrq_edittext);
        scrqEditText.setOnClickListener(this);
        yxqzEditText = (EditText) findViewById(R.id.yxqz_edittext);
        yxqzEditText.setOnClickListener(this);
        zjEditText = (EditText) findViewById(R.id.zj_edittext);
        dwEditText = (EditText) findViewById(R.id.dw_edittext);
        slView = (SLView2) findViewById(R.id.sl_view);
        etBz = (EditText) findViewById(R.id.et_bz);
        etBz.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1,
                                      int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    object.put("memo", s.toString());

                }
            }
        });
        if (this.getIntent().hasExtra("object")) {
            object = (Map<String, Object>) this.getIntent().getExtras()
                    .getSerializable("object");
            mcTextView.setText("名称："
                    + (null == object.get("name") ? object.get("goodsname")
                    .toString() : object.get("name").toString()));
            bhTextView.setText("编号："
                    + (null == object.get("code") ? object.get("goodscode")
                    .toString() : object.get("code").toString()));
            ggTextView.setText("规格：" + object.get("specs").toString());
            xhTextView.setText("型号：" + object.get("model").toString());
//			kcTextView.setText("库存："
//					+ (int) Double.parseDouble(object.get("onhand").toString())
//					+ object.get("unitname").toString());
            dwEditText.setText(object.get("unitname").toString());
            djEditText.setText(object.get("unitprice").toString());
//			zklEditText.setText(object.get("disc").toString());
//			zjEditText.setText(object.get("amount").toString());
            cpphEditText.setText(object.get("batchcode").toString());
            scrqEditText.setText(object.get("produceddate").toString());
            yxqzEditText.setText(object.get("validdate").toString());


            cpphLayout = (LinearLayout) findViewById(R.id.cpph_layout);
            cpphView = findViewById(R.id.cpph_view);
            scrqLayout = (LinearLayout) findViewById(R.id.scrq_layout);
            scrqView = findViewById(R.id.scrq_view);
            yxrqLayout = (LinearLayout) findViewById(R.id.yxrq_layout);
            yxrqView = findViewById(R.id.yxrq_view);
            etBz.setText(object.get("memo").toString());
            if (object.get("serialctrl").toString().equals("T")) {
                tvSl.setVisibility(View.VISIBLE);
                slView.setVisibility(View.GONE);
                tvSl.setText(object.get("unitqty").toString());
            } else {
                tvSl.setVisibility(View.GONE);
                slView.setVisibility(View.VISIBLE);
                slView.setSl((int) Double.parseDouble(object.get("unitqty")
                        .toString()));
            }

            if (object.get("batchctrl").toString().equals("T")) {
                cpphLayout.setVisibility(View.VISIBLE);
                cpphView.setVisibility(View.VISIBLE);
                scrqLayout.setVisibility(View.VISIBLE);
                scrqView.setVisibility(View.VISIBLE);
                yxrqLayout.setVisibility(View.VISIBLE);
                yxrqView.setVisibility(View.VISIBLE);
            } else {
                cpphLayout.setVisibility(View.GONE);
                cpphView.setVisibility(View.GONE);
                scrqLayout.setVisibility(View.GONE);
                scrqView.setVisibility(View.GONE);
                yxrqLayout.setVisibility(View.GONE);
                yxrqView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 连接网络的操作(删除)
     */
    private void searchDate() {
        object = null;
        Intent intent = new Intent();
        intent.putExtra("object", "");
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        object.put("unitprice", djEditText.getText().toString());
        object.put("unitqty", slView.getSl() + "");
        object.put("dj", djEditText.getText().toString());
//		object.put(
//				"amount",
//				Double.parseDouble(djEditText.getText().toString())
//						* slView.getSl());
        object.put("disc", "");
        object.put("batchcode", cpphEditText.getText().toString());
        object.put("produceddate", scrqEditText.getText().toString());
        object.put("validdate", yxqzEditText.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("object", (Serializable) object);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson.equals("")) {
                showToastPromopt("删除成功");
                setResult(RESULT_OK);
                finish();
            } else {
                showToastPromopt("删除失败" + returnJson.substring(5));
            }
        } else if (returnSuccessType == 1) {

        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.cpph_edittext:
                Intent intent = new Intent();
                intent.setClass(activity, CommonXzphActivity.class);
                intent.putExtra("goodsid", object.get("goodsid").toString());
                activity.startActivityForResult(intent, 0);
                break;
            case R.id.tv_serial_number:
                //严格序列号商品处理
                Intent intent1 = new Intent();
                intent1.putExtra("position", 0);
                intent1.putExtra("billid", "0");
                intent1.putExtra("serialinfo",object.get("serialinfo").toString());
                intent1.putExtra("serials",  new Gson().toJson(object.get("serials")));
                if (object.get("serialctrl").toString().equals("T")) {
                    intent1.putExtra("refertype", "0");
                    intent1.putExtra("referitemno", "0");
                    startActivityForResult(intent1.setClass(activity, XzXlhActivity.class)
                                    .putExtra("parms", "CKDB")
                                    .putExtra("storeid", getIntent().getStringExtra("rkckId"))
                                    .putExtra("goodsid",object.get("goodsid").toString())
                            , 11);
                }else {
                    startActivityForResult(intent1.setClass(activity, JxcTjXlhActivity.class),11);
                }
//                startActivityForResult(new Intent(activity, KtSerialNumberAddActivity.class)
//                        .putExtra("itemno", "0")
//                        .putExtra("uuid", object.get("serialinfo")
//                                .toString())
//
//                        .putExtra("storied", getIntent().getStringExtra("rkckId"))
//                        .putExtra("goodsid", object.get("goodsid").toString())
//                        .putExtra("isStrict", object.get("serialctrl").toString().equals("T"))
//
//                        .putExtra("position", 0)
//
//
//                        .putExtra("DATA", new Gson().toJson(object.get("serials")
//                        )), 11);
                break;
            case R.id.scrq_edittext:
                date_init(scrqEditText);
                break;
            case R.id.yxqz_edittext:
                date_init(yxqzEditText);
                break;
            case R.id.sc_textview:
                searchDate();
                break;
            case R.id.save_imagebutton:
                searchDateSave();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                cpphEditText.setText(data.getExtras().getString("name"));
                cpphId = data.getExtras().getString("id");
            } else if (requestCode == 11) {
                List<Serial> serials = new Gson().fromJson(data.getExtras().getString("data"), new TypeToken<List<Serial>>() {
                }.getType());
                object.put("serials", serials);
                //严格序列号商品处理
                if (object.get("serialctrl").toString().equals("T")) {
                    object.put("unitqty", serials.size() + ".0");
                    tvSl.setText(object.get("unitqty").toString());
                }

//                int index = data.getExtras()
//                        .getInt("position");
//                object.put("serials", new Gson().fromJson(data.getExtras().getString("DATA"), new TypeToken<List<Serial>>() {
//                }.getType()));

            }
        }
    }
}