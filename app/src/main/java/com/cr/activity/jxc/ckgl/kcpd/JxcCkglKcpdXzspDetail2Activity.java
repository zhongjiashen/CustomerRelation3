package com.cr.activity.jxc.ckgl.kcpd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.SLView2;
import com.crcxj.activity.R;
import com.update.actiity.SerialNumberDetailsActivity;

/**
 * 进销存-仓库管理-库存盘点-选择商品-选择的商品的详细信息
 *
 * @author Administrator
 */
public class JxcCkglKcpdXzspDetail2Activity extends BaseActivity implements OnClickListener {
    TextView mcTextView;
    TextView bhTextView;
    TextView ggTextView;
    TextView xhTextView;
    TextView kcTextView;
    TextView scTextView;
    ImageButton saveImageButton;
    EditText zmslView;
    SLView2 spslView;
    SLView2 ykslView;
    LinearLayout cpphLayout;
    LinearLayout scrqLayout;
    LinearLayout yxrqLayout;
    View cpphView;
    View scrqView;
    View yxrqView;
    EditText cpphEditText;
    EditText scrqEditText;
    EditText yxqzEditText;
    Map<String, Object> object = new HashMap<String, Object>();
    TextView tvSerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_kcpd_xzsp_detail2);
        addFHMethod();
        initActivity();
    }

    /**
     * 初始化Activity
     */
    @SuppressWarnings("unchecked")
    private void initActivity() {
        tvSerialNumber = findViewById(R.id.tv_serial_number);
        tvSerialNumber.setOnClickListener(this);
        cpphLayout = (LinearLayout) findViewById(R.id.cpph_layout);
        cpphView = findViewById(R.id.cpph_view);
        scrqLayout = (LinearLayout) findViewById(R.id.scrq_layout);
        scrqView = findViewById(R.id.scrq_view);
        yxrqLayout = (LinearLayout) findViewById(R.id.yxrq_layout);
        yxrqView = findViewById(R.id.yxrq_view);
        mcTextView = (TextView) findViewById(R.id.mc_textview);
        bhTextView = (TextView) findViewById(R.id.bh_textview);
        ggTextView = (TextView) findViewById(R.id.gg_textview);
        xhTextView = (TextView) findViewById(R.id.xh_textview);
        kcTextView = (TextView) findViewById(R.id.kc_textview);
        scTextView = (TextView) findViewById(R.id.sc_textview);
        cpphEditText = (EditText) findViewById(R.id.cpph_edittext);
        scrqEditText = (EditText) findViewById(R.id.scrq_edittext);
        yxqzEditText = (EditText) findViewById(R.id.yxqz_edittext);
        scTextView.setOnClickListener(this);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        scTextView.setVisibility(View.GONE);
        saveImageButton.setVisibility(View.GONE);
        zmslView = (EditText) findViewById(R.id.zmsl_edittext);
        spslView = (SLView2) findViewById(R.id.spsl_view);
        ykslView = (SLView2) findViewById(R.id.yksl_view);

        if (this.getIntent().hasExtra("object")) {
            object = (Map<String, Object>) this.getIntent().getExtras().getSerializable("object");
            mcTextView.setText("名称："
                    + (null == object.get("name") ? object.get("goodsname").toString()
                    : object.get("name").toString()));
            bhTextView.setText("编号："
                    + (null == object.get("code") ? object.get("goodscode").toString()
                    : object.get("code").toString()));
            ggTextView.setText("规格：" + object.get("specs").toString());
            xhTextView.setText("型号：" + object.get("model").toString());
            if (object.get("onhand") == null) {
                kcTextView.setVisibility(View.GONE);
            } else {
                kcTextView.setText("库存：" + (int) Double.parseDouble(object.get("onhand").toString()));
            }
            double zmsl = 0;
            double spsl = 0;
            double yksl = 0;
            cpphEditText.setText(object.get("batchcode").toString());
            scrqEditText.setText(object.get("produceddate").toString());
            yxqzEditText.setText(object.get("validdate").toString());
            if (object.get("zmsl") != null) {
                zmsl = Double.parseDouble(object.get("zmsl").toString().equals("") ? "0" : object.get("zmsl").toString());
                spsl = Double.parseDouble(object.get("spsl").toString().equals("") ? "0" : object.get("spsl").toString());
//                yksl=Double.parseDouble(object.get("zmsl").toString())-Double.parseDouble(object.get("spsl").toString());
            } else {
                zmsl = Double.parseDouble(object.get("zmonhand").toString());
                spsl = Double.parseDouble(object.get("sponhand").toString());
//                yksl=Double.parseDouble(object.get("zmonhand").toString())-Double.parseDouble(object.get("sponhand").toString());
            }
            zmslView.setText(zmsl + "");
            spslView.setSl((int) spsl);
            ykslView.setSl((int) yksl);

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
        object.put("zmsl", zmslView.getText() + "");
        object.put("spsl", spslView.getSl() + "");
        object.put("yksl", ykslView.getSl() + "");
        Intent intent = new Intent();
        intent.putExtra("object", (Serializable) object);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void executeSuccess() {
//        if (returnSuccessType == 0) {
//            if (returnJson.equals("")) {
//                showToastPromopt("删除成功");
//                setResult(RESULT_OK);
//                finish();
//            } else {
//                showToastPromopt("删除失败" + returnJson.substring(5));
//            }
//        } else if (returnSuccessType == 1) {
//
//        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.sc_textview:
                searchDate();
                break;
            case R.id.save_imagebutton:
                searchDateSave();
                break;
            case R.id.tv_serial_number:
                startActivity(new Intent(activity, SerialNumberDetailsActivity.class)
                        .putExtra("billid", object.get("balitemid").toString())
                        .putExtra("serialinfo", object.get("serialinfo").toString())
                        .putExtra("tabname", "tb_balitem"));
                break;
        }
    }
}