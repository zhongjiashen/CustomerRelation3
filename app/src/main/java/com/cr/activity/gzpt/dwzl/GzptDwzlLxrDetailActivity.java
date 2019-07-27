package com.cr.activity.gzpt.dwzl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.activity.MainActivity;
import com.cr.service.SocketService;
import com.cr.tools.MyApplication;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 工作平台-单位资料-联系人-联系人详情
 * 
 * @author caiyanfei
 * @version $Id: CommonXzdwActivity.java, v 0.1 2015-3-12 下午3:46:54 caiyanfei Exp $
 */
public class GzptDwzlLxrDetailActivity extends BaseActivity implements OnClickListener {
    private String lxrId, clientId;
    private EditText xmEditText, xbEditText, bmEditText, zwEditText, csrqEditText, zlxrEditText,
            jtzzEditText, grahEditText, bzEditText, lxfsEditText;
    private TextView bjlxrTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_dwzl_lxr_detail);
//        addZYMethod();
        initActivity();
        if (this.getIntent().hasExtra("lxrid")) {
            lxrId = this.getIntent().getExtras().getString("lxrid");
            clientId = this.getIntent().getExtras().getString("clientid");
        }
        if (this.getIntent().hasExtra("typesss")) {
            ImageButton imageButton = (ImageButton) findViewById(R.id.fh);
            imageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Map<String, Object> object = new HashMap<String, Object>();
                    object.put("lxrid", lxrId);
                    object.put("clientid", clientId);
                    object.put("types", "");
                    Intent intent = new Intent();
                    intent.setClass(activity, GzptDwzlActivity.class);
                    intent.putExtra("object", (Serializable) object);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            addFHMethod();
        }
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        xmEditText = (EditText) findViewById(R.id.xm_edittext);
        xbEditText = (EditText) findViewById(R.id.xb_edittext);
        bmEditText = (EditText) findViewById(R.id.bm_edittext);
        zwEditText = (EditText) findViewById(R.id.zw_edittext);
        csrqEditText = (EditText) findViewById(R.id.csrq_edittext);
        zlxrEditText = (EditText) findViewById(R.id.zlxr_edittext);
        jtzzEditText = (EditText) findViewById(R.id.jtzz_edittext);
        grahEditText = (EditText) findViewById(R.id.grah_edittext);
        bzEditText = (EditText) findViewById(R.id.bz_edittext);
        lxfsEditText = (EditText) findViewById(R.id.lxfs_edittext);
        lxfsEditText.setOnClickListener(this);
        bjlxrTextView = (TextView) findViewById(R.id.bjlxr_textview);
        bjlxrTextView.setOnClickListener(this);
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        //        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("lxrid", lxrId);
        findServiceData2(0, ServerURL.LXRINFO, parmMap, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnSuccessType == 0) {
            if (returnJson.equals("")) {
                showToastPromopt(2);
            } else {
                Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson)).get(0);
                xmEditText.setText(object.get("lxrname").toString());
                xbEditText.setText(object.get("xb").toString().equals("0") ? "女" : "男");
                bmEditText.setText(object.get("depname").toString());
                zwEditText.setText(object.get("zw").toString());
                csrqEditText.setText(object.get("csrq").toString());
                zlxrEditText.setText(object.get("mastflag").toString().equals("0") ? "否" : "是");
                jtzzEditText.setText(object.get("jtaddress").toString());
                grahEditText.setText(object.get("grah").toString());
                bzEditText.setText(object.get("memo").toString());
            }
        }
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.lxfs_edittext:
                intent.setClass(this, GzptDwzlLxrLxfsActivity.class);
                intent.putExtra("lxrid", lxrId);
                startActivity(intent);
                break;
            case R.id.bjlxr_textview:
                intent.setClass(this, GzptDwzlLxrBjlxrActivity.class);
                intent.putExtra("lxrid", lxrId);
                intent.putExtra("clientid", clientId);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //        if (resultCode == RESULT_OK) {
        if (requestCode == 0) {// 编辑联系人
            searchDate();
        }
        //        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (this.getIntent().hasExtra("typesss")) {
                Map<String, Object> object = new HashMap<String, Object>();
                object.put("lxrid", lxrId);
                object.put("clientid", clientId);
                object.put("types", "");
                Intent intent = new Intent();
                intent.setClass(activity, GzptDwzlActivity.class);
                intent.putExtra("object", (Serializable) object);
                startActivity(intent);
                finish();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
