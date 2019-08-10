package com.cr.activity.jxc.ckgl.chtj;

import java.io.Serializable;
import java.util.HashMap;
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
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzphActivity;
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;
import com.update.utils.LogUtils;

/**
 * 进销存-仓库管理-存货调价-选择商品-选择的商品的详细信息
 * 
 * @author Administrator
 * 
 */
public class JxcCkglChtjXzspDetailActivity extends BaseActivity implements OnClickListener {
    TextView            mcTextView;
    TextView            bhTextView;
    TextView            ggTextView;
    TextView            xhTextView;
    TextView            kcTextView;
    TextView            scTextView;
    ImageButton         saveImageButton;
    EditText            dwEditText;
    EditText             slEditText;
    EditText            tqdjEditText;
    EditText            tqjeEditText;
    EditText            thdjEditText;
    EditText            thjeEditText;
    EditText            tzjeEditText;
    Map<String, Object> object = new HashMap<String, Object>();
    EditText etBz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_ckgl_chtj_xzsp_detail);
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
        scTextView.setOnClickListener(this);
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        dwEditText = (EditText) findViewById(R.id.dw_edittext);
        slEditText = (EditText) findViewById(R.id.sl_edittext);
        tqdjEditText = (EditText) findViewById(R.id.tqdj_edittext);
        tqjeEditText = (EditText) findViewById(R.id.tqje_edittext);
        thdjEditText = (EditText) findViewById(R.id.thdj_edittext);
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


        thdjEditText.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                thjeEditText.setText(Double.parseDouble(slEditText.getText().toString().equals("")?"0":arg0.toString())*Double.parseDouble(arg0.toString().equals("")?"0":arg0.toString())+"");
                String tqje=tqjeEditText.getText().toString().equals("")?"0":tqjeEditText.getText().toString();
                String thje=thjeEditText.getText().toString().equals("")?"0":thjeEditText.getText().toString();
                tzjeEditText.setText(Double.parseDouble(tqje)-Double.parseDouble(thje)+"");
            }
            
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        thjeEditText = (EditText) findViewById(R.id.thje_edittext);
        tzjeEditText = (EditText) findViewById(R.id.tzje_edittext);
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
            etBz.setText(object.get("memo").toString());
            if(object.get("onhand")==null){
                kcTextView.setVisibility(View.GONE);
            }else{
                kcTextView.setText("库存：" + (int) Double.parseDouble(object.get("onhand").toString()));
            } 
            dwEditText.setText(object.get("unitname").toString());
            double tqdj=0;
            double thdj=0;
            double sl=0;
            if (object.get("tqdj") != null) {
                LogUtils.e(object.get("sl").toString());
                tqdj = Double.parseDouble(object.get("tqdj").toString().equals("")?"0":object.get("tqdj").toString());
                thdj=  Double.parseDouble(object.get("thdj").toString().equals("")?"0":object.get("thdj").toString());
                sl=Double.parseDouble(object.get("sl").toString());
            } else {
                tqdj = Double.parseDouble(object.get("aoprice").toString());
                thdj=  Double.parseDouble(object.get("anprice").toString());
                sl=Double.parseDouble(object.get("unitqty").toString());
            }
//            slView.setSl((int)sl);
            slEditText.setText(FigureTools.sswrFigure(sl));
            tqdjEditText.setText(FigureTools.sswrFigure(tqdj+""));
            tqjeEditText.setText(FigureTools.sswrFigure((tqdj*sl)+""));
            thdjEditText.setText(FigureTools.sswrFigure(thdj+""));
            thjeEditText.setText(FigureTools.sswrFigure((thdj*sl)+""));
            tzjeEditText.setText(FigureTools.sswrFigure((tqdj*sl-thdj*sl) + ""));
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
        object.put("dw", dwEditText.getText().toString());
        object.put("sl", slEditText.getText().toString());
        object.put("tqdj", tqdjEditText.getText().toString());
        object.put("tqje", tqjeEditText.getText().toString());
        object.put("thdj", thdjEditText.getText().toString());
        object.put("thje", thjeEditText.getText().toString());
        object.put("tzje", tzjeEditText.getText().toString());
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
            case R.id.sc_textview:
                searchDate();
                break;
            case R.id.save_imagebutton:
                searchDateSave();
                break;
        }
    }
}