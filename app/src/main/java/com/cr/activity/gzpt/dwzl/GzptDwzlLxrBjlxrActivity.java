package com.cr.activity.gzpt.dwzl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.adapter.SlidePageAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlLxrBjlxrLxfsAdapter;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 工作平台-单位资料-联系人-编辑联系人
 *
 * @author Administrator
 */
public class GzptDwzlLxrBjlxrActivity extends BaseActivity implements
        OnClickListener {
    private TextView lxrxxTextView, lxfsTextView;
    private ViewPager viewPager;
    private LayoutInflater inflater;
    private View lxrxxView, lxfsView;
    private ListView lxfsListView;
    private boolean isLxfs = false; // 是否是第一次加载
    private GzptDwzlLxrBjlxrLxfsAdapter lxfsAdapter;
    List<Map<String, Object>> lxfsList = new ArrayList<Map<String, Object>>();
    private EditText xmEditText, bmEditText, zwEditText, csrqEditText,
            jtzzEditText, grahEditText, bzEditText;
    private Spinner xbSpinner;
    private CheckBox zlxrCheckBox;
    private ImageView corsor1, corsor2;
    private String lxrId = "", tel = ""; // 联系人的ID
    private ImageView addImageView, delImageView, saveImageView;


    private LinearLayout llUnitNameChoice;
    private TextView tvUnitName;
    private String mClientid = "";// 单位名称
    private String mClientname;// 单位名称
    private String mTypes;
    private Button btYwlr;
    private Button btXzlxr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_dwzl_lxr_bjlxr);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (this.getIntent().hasExtra("lxrid")) {
            lxrId = this.getIntent().getExtras().getString("lxrid");
            mClientid = this.getIntent().getExtras().getString("clientid");
            tel = this.getIntent().getExtras().getString("tel");
        }
        initActivity();
        initLxrxxListView();
        initLxfsListView();
        addFHMethod();
        searchDateDwxx(0);
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        lxrxxTextView = (TextView) findViewById(R.id.lxrxx_textview);
        lxrxxTextView.setOnClickListener(this);
        lxfsTextView = (TextView) findViewById(R.id.lxfs_textview);
        lxfsTextView.setOnClickListener(this);
        corsor1 = (ImageView) findViewById(R.id.corsor1);
        corsor2 = (ImageView) findViewById(R.id.corsor2);

        btYwlr = findViewById(R.id.bt_ywlr);
        btYwlr.setOnClickListener(this);
        btXzlxr = findViewById(R.id.bt_xzlxr);
        btXzlxr.setOnClickListener(this);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<View> viewPage = new ArrayList<View>();
        lxrxxView = inflater.inflate(R.layout.activity_gzpt_dwzl_lxr_bjlxr_lxrxx,
                null);
        lxfsView = inflater.inflate(R.layout.activity_gzpt_dwzl_lxr_bjlxr_lxfs,
                null);
        viewPage.add(lxrxxView);
        viewPage.add(lxfsView);
        SlidePageAdapter myAdapter = new SlidePageAdapter(viewPage, this);
        viewPager.setAdapter(myAdapter);
        // 下面的点图
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    viewPager.setCurrentItem(0);
                    corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                } else if (arg0 == 1) {
                    if (lxrId.equals("0")) {
                        showToastPromopt("请先保存联系人信息");
                        viewPager.setCurrentItem(0);
                        return;
                    }
                    viewPager.setCurrentItem(1);
                    corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
                    corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                    if (!isLxfs) {
                        lxfsList.clear();
                        searchDateLxfs(1);
                        isLxfs = true;
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // Log.v("dddd", "onPageScrollStateChanged");
            }
        });
    }

    /**
     * 初始化”联系人信息“页面
     */
    private void initLxrxxListView() {
        xmEditText = (EditText) lxrxxView.findViewById(R.id.xm_edittext);
        xbSpinner = (Spinner) lxrxxView.findViewById(R.id.xb_spinner);
        String[] itemStrings = {"女", "男"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, itemStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        xbSpinner.setAdapter(adapter);
        xbSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        bmEditText = (EditText) lxrxxView.findViewById(R.id.bm_edittext);
        zwEditText = (EditText) lxrxxView.findViewById(R.id.zw_edittext);
        csrqEditText = (EditText) lxrxxView.findViewById(R.id.csrq_edittext);
        csrqEditText.setOnClickListener(this);
        zlxrCheckBox = (CheckBox) lxrxxView.findViewById(R.id.zlxr_checkbox);
        jtzzEditText = (EditText) lxrxxView.findViewById(R.id.jtzz_edittext);
        grahEditText = (EditText) lxrxxView.findViewById(R.id.grah_edittext);
        bzEditText = (EditText) lxrxxView.findViewById(R.id.bz_edittext);


        llUnitNameChoice = lxrxxView.findViewById(R.id.ll_unit_name_choice);
        llUnitNameChoice.setOnClickListener(this);
        tvUnitName = lxrxxView.findViewById(R.id.tv_unit_name);

//        lxfsEditText=(EditText) findViewById(R.id.lxfs_edittext);
//        lxfsEditText.setOnClickListener(this);
    }

    /**
     * 初始化”联系方式“页面
     */
    private void initLxfsListView() {
        lxfsListView = (ListView) lxfsView.findViewById(R.id.listview);
        lxfsAdapter = new GzptDwzlLxrBjlxrLxfsAdapter(lxfsList, activity);
        lxfsListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(GzptDwzlLxrBjlxrActivity.this,
                        GzptDwzlLxrBjlxrLxfsAddActivity.class);
                intent.putExtra("id", lxfsList.get(arg2).get("id")
                        .toString());
                intent.putExtra("lb", lxfsList.get(arg2).get("lb")
                        .toString());
                intent.putExtra("itemno", lxfsList.get(arg2).get("itemno")
                        .toString());
                intent.putExtra("clientid", mClientid);
                intent.putExtra("lxrid", lxrId);
                startActivityForResult(intent, 0);
            }
        });
        lxfsListView.setAdapter(lxfsAdapter);
        addImageView = (ImageView) lxfsView.findViewById(R.id.add_imageview);
        addImageView.setOnClickListener(this);
        delImageView = (ImageView) lxfsView.findViewById(R.id.del_imageview);
        delImageView.setOnClickListener(this);
        saveImageView = (ImageView) findViewById(R.id.save);
        saveImageView.setOnClickListener(this);
    }

    /**
     * 监听事件
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.dwxx_textview:
                viewPager.setCurrentItem(0);
                corsor1.setBackgroundResource(R.drawable.index_selectcorsor);
                corsor2.setBackgroundResource(R.drawable.index_unselectcorsor);
                break;
            case R.id.lxfs_textview:
                if (lxrId.equals("0")) {
                    showToastPromopt("请先保存联系人信息");
                    return;
                }
                viewPager.setCurrentItem(1);
                corsor2.setBackgroundResource(R.drawable.index_selectcorsor);
                corsor1.setBackgroundResource(R.drawable.index_unselectcorsor);
                if (!isLxfs) {
                    lxfsList.clear();
                    searchDateLxfs(1);
                    isLxfs = true;
                }
                break;
            case R.id.add_imageview:
                intent.setClass(this, GzptDwzlLxrBjlxrLxfsAddActivity.class);
                intent.putExtra("clientid", mClientid);
                intent.putExtra("lxrid", lxrId);
                startActivityForResult(intent, 0);
                break;
            case R.id.del_imageview:
                String ids = "";
                List<Map<String, Object>> maps = lxfsAdapter.getList();
                for (int i = 0; i < maps.size(); i++) {
                    Map<String, Object> map = maps.get(i);
                    if (map.get("select") != null && map.get("select").toString().equals("true")) {
                        ids += map.get("id") + ",";
                    }
                }
                if (ids.equals("")) {
                    showToastPromopt("请选择要删除的项！");
                    return;
                }
                searchDateDelLxfs(2, ids.substring(0, ids.length() - 1));
                break;
            case R.id.save:
                searchDateSaveDw(3);
                break;
            case R.id.csrq_edittext:
                date_init(csrqEditText);
                break;
            case R.id.ll_unit_name_choice://单位选择
                startActivityForResult(new Intent(this, CommonXzdwActivity.class)
                        .putExtra("type", "0"), 11);
                break;
            case R.id.bt_ywlr:
                if (TextUtils.isEmpty(mClientid)) {
                    showToastPromopt("请选择单位！");
                    return;
                }
                Map map = new HashMap();
                map.put("clientid", mClientid);
                map.put("types", mTypes);
                startActivity(new Intent(this, GzptDwzlActivity.class).putExtra("object", (Serializable) map));
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

    /**
     * 连接网络的操作(查询联系人信息)
     */
    private void searchDateDwxx(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
//        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("lxrid", lxrId);
        findServiceData2(type, ServerURL.LXRINFO, parmMap, true);
    }

    /**
     * 连接网络的操作(查询联系方式)
     */
    private void searchDateLxfs(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("lxrid", lxrId);
//		parmMap.put("havelxr", "0");
        findServiceData(type, ServerURL.LXRLXFSLIST, parmMap);
    }

    /**
     * 连接网络的操作(保存单位信息)
     */
    private void searchDateSaveDw(int type) {
        if (xmEditText.getText().toString().equals("")) {
            showToastPromopt("姓名不能为空！");
            return;
        }
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("lxrid", lxrId);
        parmMap.put("lxrname", xmEditText.getText().toString());
        parmMap.put("xb", xbSpinner.getSelectedItem().toString().equals("女") ? "0" : "1");
        parmMap.put("clientid", mClientid);
        parmMap.put("depname", bmEditText.getText().toString());
        parmMap.put("zw", zwEditText.getText().toString());
        parmMap.put("csrq", csrqEditText.getText().toString());
        parmMap.put("mastflag", zlxrCheckBox.isChecked() ? "1" : "0");
        parmMap.put("jtaddress", jtzzEditText.getText().toString());
        parmMap.put("grah", grahEditText.getText().toString());
        parmMap.put("memo", bzEditText.getText().toString());
        findServiceData(type, ServerURL.LXRSAVE, parmMap);
    }

    /**
     * 连接网络的操作(删除联系方式)
     */
    private void searchDateDelLxfs(int type, String ids) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("ids", ids);
        parmMap.put("itemtype", "LXFS");
        findServiceData(type, ServerURL.DELDATA, parmMap);
    }

    /**
     * 连接网络的操作(单位联系方式保存)
     */

    private void SaveLxfs(String phone) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("id", "0");
        parmMap.put("clientid", mClientid);
        parmMap.put("lxrid", lxrId);
        parmMap.put("lb", phone.length() == 11 ? "2" : "1");
        parmMap.put("itemno", phone);
        findServiceData2(10, ServerURL.LXFSSAVE, parmMap, false);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 0:
                if (returnJson.equals("")) {
                    return;
                }
                Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
                        .paseJsonToObject(returnJson)).get(0);
                xmEditText.setText(object.get("lxrname").toString());
//             xbEditText.setText(object.get("xb").toString().equals("0")?"女":"男");
                if (object.get("xb").toString().equals("0")) {
                    xbSpinner.setSelection(0);
                } else {
                    xbSpinner.setSelection(1);
                }
                bmEditText.setText(object.get("depname").toString());
                zwEditText.setText(object.get("zw").toString());
                csrqEditText.setText(object.get("csrq").toString());
//             zlxrEditText.setText(object.get("mastflag").toString().equals("0")?"否":"是");
                if (object.get("mastflag").toString().equals("0")) {
                    zlxrCheckBox.setChecked(false);
                } else {
                    zlxrCheckBox.setChecked(true);
                }
                jtzzEditText.setText(object.get("jtaddress").toString());
                grahEditText.setText(object.get("grah").toString());
                bzEditText.setText(object.get("memo").toString());
                break;
            case 1:
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    lxfsList.addAll((List<Map<String, Object>>) PaseJson
                            .paseJsonToObject(returnJson));
                }
                lxfsAdapter.notifyDataSetChanged();
                break;
            case 2:
                if (returnJson.equals("")) {
                    showToastPromopt("操作成功");
                    lxfsList.clear();
                    searchDateLxfs(1);
                } else {
                    showToastPromopt("操作失败" + returnJson.substring(5));
                }
                break;
            case 3:
                if (returnJson.equals("")) {
                    lxrId = returnJsonId;
                    /**
                     * 上一个界面传过来电话号码的时候，要先保存一下
                     */
                    if (!TextUtils.isEmpty(tel)) {
                        SaveLxfs(tel);
                    } else {
                        showToastPromopt("操作成功");
                    }
                } else {
                    showToastPromopt("保存失败" + returnJson.substring(5));
                }
                break;
            case 10:
                showToastPromopt("操作成功");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:// 编辑联系人
                    // viewPager.setCurrentItem(0);
                    lxfsList.clear();
                    searchDateLxfs(1);
                    break;
                case 11://单位选择结果处理
                    mClientid = data.getStringExtra("id");
                    mClientname = data.getStringExtra("name");
                    mTypes = data.getStringExtra("types");
//                    lxrid = data.getStringExtra("lxrid");
//                    mTypesname = data.getStringExtra("typesname");
                    tvUnitName.setText(mClientname);
//                    tvContacts.setText(data.getStringExtra("lxrname"));
//                    etContactNumber.setText(data.getStringExtra("phone"));
//                    etCustomerAddress.setText(data.getStringExtra("shipto"));
                    break;
            }
//            if (requestCode == 0) {// 编辑联系人
//                // viewPager.setCurrentItem(0);
//                lxfsList.clear();
//                searchDateLxfs(1);
//            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}