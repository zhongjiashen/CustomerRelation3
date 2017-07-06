package com.cr.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cr.adapter.GzptXsdjXsdAdapter;
import com.cr.model.CPMX;
import com.cr.model.XSDLB;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class GzptXsdjXsdActivity extends BaseActivity implements OnClickListener,
                                                     OnLongClickListener {
    private XSDLB              xsdlb;
    private GzptXsdjXsdAdapter adapter;
    private TextView           titleTextView, timeTextView, jeTextView, ztTextView;
    private EditText           djbhEditText, hkfsEditText, hkrqEditText, lxrEditText, sjEditText,
            dhEditText, shdzEditText;
    private List<CPMX>         cpmxList = new ArrayList<CPMX>();
    private ListView           cpmxListView;
    private ImageButton        zdButton, cpmxButton, addButton, bjButton, deleteButton;
    private LinearLayout       zdLinearLayout;
    private int                state    = 0;                                            //默认是选择主单0 产品明细是1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_xsdj_xsd);
        addFHMethod();
        addZYMethod();
        initActivity();
        initListView();
        searchZdDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        xsdlb = (XSDLB) this.getIntent().getExtras().getSerializable("object");
        titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setOnLongClickListener(this);
        //		titleTextView.setText(xsdlb.getCname());
        timeTextView = (TextView) findViewById(R.id.time);
        //		timeTextView.setText(xsdlb.getBilldate());
        jeTextView = (TextView) findViewById(R.id.je);
        //		jeTextView.setText(xsdlb.getZje());
        ztTextView = (TextView) findViewById(R.id.zt);
        //		ztTextView.setText(xsdlb.getZt().equals("0")?"未审":"已审");
        djbhEditText = (EditText) findViewById(R.id.djbh);
        hkfsEditText = (EditText) findViewById(R.id.hkfs);
        hkrqEditText = (EditText) findViewById(R.id.hkrq);
        lxrEditText = (EditText) findViewById(R.id.lxr);
        lxrEditText.setOnLongClickListener(this);
        sjEditText = (EditText) findViewById(R.id.sj);
        sjEditText.setOnLongClickListener(this);
        dhEditText = (EditText) findViewById(R.id.dh);
        dhEditText.setOnLongClickListener(this);
        shdzEditText = (EditText) findViewById(R.id.shdz);
        shdzEditText.setOnLongClickListener(this);
        zdButton = (ImageButton) findViewById(R.id.zd);
        zdButton.setOnClickListener(this);
        cpmxButton = (ImageButton) findViewById(R.id.cpmx);
        cpmxButton.setOnClickListener(this);
        zdLinearLayout = (LinearLayout) findViewById(R.id.zd_linearlayout);
        addButton = (ImageButton) findViewById(R.id.add);
        addButton.setOnClickListener(this);
        bjButton = (ImageButton) findViewById(R.id.bj);
        bjButton.setOnClickListener(this);
        deleteButton = (ImageButton) findViewById(R.id.delete);
        deleteButton.setOnClickListener(this);
    }

    /**
     * 初始化产品明细的listview列表
     */
    private void initListView() {
        cpmxListView = (ListView) findViewById(R.id.cpmx_listview);
        adapter = new GzptXsdjXsdAdapter(cpmxList, context);
        cpmxListView.setAdapter(adapter);
    }

    /**
     * 连接网络的操作
     */
    private void searchZdDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("saleid", xsdlb.getSaleid());
        findServiceData(0, ServerURL.SALEMASTER, parmMap);
    }

    /**
     * 连接网络的操作
     */
    private void searchCpmxDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("saleid", xsdlb.getSaleid());
        findServiceData(1, ServerURL.SALEDETAIL, parmMap);
    }

    /**
     * 连接网络的操作（删除数据）
     */
    private void searchDate2(String ids) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("ids", ids);
        parmMap.put("itemtype", "SALED");
        findServiceData2(2, ServerURL.DELDATA, parmMap, true);
    }

    /**
     * 连接网络的操作（删除数据）
     */
    private void searchDate3(String ids) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("ids", ids);
        parmMap.put("itemtype", "SALEM");
        findServiceData2(3, ServerURL.DELDATA, parmMap, true);
    }

    @Override
    public void executeSuccess() {
        //		Log.v("dddd", returnJson);
        switch (returnSuccessType) {
            case 0:
                xsdlb = XSDLB.parseJsonToObject2(returnJson).get(0);
                djbhEditText.setText(xsdlb.getBillcode().equals("null") ? "" : xsdlb.getBillcode());
                hkfsEditText.setText(xsdlb.getWkfs().equals("null") ? "" : xsdlb.getWkfs());
                hkrqEditText.setText(xsdlb.getWkrq().equals("null") ? "" : xsdlb.getWkrq());
                lxrEditText.setText(xsdlb.getLxrname().equals("null") ? "" : xsdlb.getLxrname());
                sjEditText.setText(xsdlb.getSjhm().equals("null") ? "" : xsdlb.getSjhm());
                dhEditText.setText(xsdlb.getPhone().equals("null") ? "" : xsdlb.getPhone());
                shdzEditText.setText(xsdlb.getShdz().equals("null") ? "" : xsdlb.getShdz());
                titleTextView.setText(xsdlb.getCname().equals("null") ? "" : xsdlb.getCname());
                timeTextView.setText(xsdlb.getBilldate().equals("null") ? "" : xsdlb.getBilldate());
                jeTextView.setText(xsdlb.getZje().equals("null") ? "" : xsdlb.getZje());
                ztTextView.setText(xsdlb.getZt().equals("0") ? "未审" : "已审");
                break;
            case 1:
                cpmxList.clear();
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    cpmxList.addAll(CPMX.parseJsonToObject2(returnJson));
                }
                adapter.notifyDataSetChanged();
                break;
            case 2:
                if (returnJson.equals("")) {
                    showToastPromopt("删除成功！");
                    cpmxList.clear();
                    currentPage = 0;
                    searchCpmxDate();
                } else {
                    showToastPromopt("删除失败！");
                }
                break;
            case 3:
                if (returnJson.equals("")) {
                    showToastPromopt("删除成功！");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showToastPromopt("删除失败！");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.zd:
                state = 0;
                zdLinearLayout.setVisibility(View.VISIBLE);
                cpmxListView.setVisibility(View.GONE);
                zdButton.setBackgroundResource(R.drawable.zdselect);
                cpmxButton.setBackgroundResource(R.drawable.cpmxunselect);
                addButton.setVisibility(View.GONE);
                bjButton.setVisibility(View.VISIBLE);
                searchZdDate();
                break;
            case R.id.cpmx:
                state = 1;
                zdLinearLayout.setVisibility(View.GONE);
                cpmxListView.setVisibility(View.VISIBLE);
                zdButton.setBackgroundResource(R.drawable.zdunselect);
                cpmxButton.setBackgroundResource(R.drawable.cpmxselect);
                bjButton.setVisibility(View.GONE);
                addButton.setVisibility(View.VISIBLE);
                searchCpmxDate();
                break;
            case R.id.bj:
                Intent intent = new Intent();
                intent.setClass(this, GzptXsdjXzxsdActivity.class);
                intent.putExtra("object", xsdlb);
                startActivityForResult(intent, 0);
                break;
            case R.id.add:
                Intent intent2 = new Intent();
                intent2.putExtra("object", xsdlb);
                intent2.setClass(this, GzptXsdjXsdXzcpActivity.class);

                startActivityForResult(intent2, 1);
                break;
            case R.id.delete:
                if (state == 1) {
                    int checkSize = 0;
                    String ids = "";
                    for (CPMX grrc : adapter.getList()) {
                        if (grrc.isSelect()) {
                            if (checkSize == 0) {
                                ids += grrc.getId();
                            } else {
                                ids += "," + grrc.getId();
                            }
                            checkSize++;
                        }
                    }
                    if (checkSize == 0) {
                        showToastPromopt("请选择要删除的数据！");
                    } else {
                        searchDate2(ids);
                    }
                } else if (state == 0) {
                    searchDate3(xsdlb.getSaleid());
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onExecuteFh() {
        setResult(RESULT_OK);
        super.onExecuteFh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                searchZdDate();
            } else if (requestCode == 1) {
                searchCpmxDate();
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onLongClick(View arg0) {
        ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        switch (arg0.getId()) {
            case R.id.title:
                clip.setText(titleTextView.getText()); // 复制
                showToastPromopt("复制成功");
                break;
            case R.id.lxr:
                clip.setText(lxrEditText.getText().toString()); // 复制
                showToastPromopt("复制成功");
                break;
            case R.id.sj:
                clip.setText(sjEditText.getText().toString()); // 复制
                showToastPromopt("复制成功");
                break;
            case R.id.dh:
                clip.setText(dhEditText.getText().toString()); // 复制
                showToastPromopt("复制成功");
                break;
            case R.id.shdz:
                clip.setText(shdzEditText.getText().toString()); // 复制
                showToastPromopt("复制成功");
                break;

            default:
                break;
        }
        return true;
    }
}
