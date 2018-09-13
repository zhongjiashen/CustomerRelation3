package com.cr.activity.jxc.cggl.cgth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzdwActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzyyActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.cggl.KtCgglSpxqActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddXzspActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddXzspDetailActivity;
import com.cr.adapter.jxc.cggl.cgdd.JxcCgglCgddDetailAdapter;
import com.cr.adapter.jxc.cggl.cgth.JxcCgglCgthAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.update.actiity.WeChatCaptureActivity;
import com.update.actiity.choose.ChooseDepartmentActivity;
import com.update.actiity.choose.KtXzfplxActivity;
import com.update.actiity.choose.SelectSalesmanActivity;
import com.update.actiity.logistics.KtWlxxActivity;
import com.update.actiity.project.ChoiceProjectActivity;
import com.update.model.KtWlxxData;
import com.update.model.Serial;
import com.update.utils.EditTextHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 进销存-采购管理-采购退货-增加
 *
 * @author Administrator
 */
public class JxcCgglCgthAddActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.save_imagebutton)
    ImageButton saveImagebutton;
    @BindView(R.id.mTogBtn)
    ToggleButton mTogBtn;
    @BindView(R.id.gys2_edittext)
    EditText gys2Edittext;
    @BindView(R.id.ck_edittext)
    EditText ckEdittext;
    @BindView(R.id.xzxsdd_linearlayout)
    LinearLayout xzxsddLinearlayout;
    @BindView(R.id.gldjcg_linearlayout)
    LinearLayout gldjcgLinearlayout;
    @BindView(R.id.rkck_edittext)
    EditText rkckEdittext;
    @BindView(R.id.gys_edittext)
    EditText gysEdittext;
    @BindView(R.id.lxr_edittext)
    EditText lxrEdittext;
    @BindView(R.id.lxdh_edittext)
    EditText lxdhEdittext;
    @BindView(R.id.et_fplx)
    EditText etFplx;
    @BindView(R.id.xm_edittext)
    EditText xmEdittext;
    @BindView(R.id.jhdz_edittext)
    EditText jhdzEdittext;
    @BindView(R.id.xzspnum_textview)
    TextView xzspnumTextview;
    @BindView(R.id.xzsp_linearlayout)
    LinearLayout xzspLinearlayout;
    @BindView(R.id.xzsp_listview)
    CustomListView xzspListview;
    @BindView(R.id.gysqk_edittext)
    EditText gysqkEdittext;
    @BindView(R.id.hjje_edittext)
    EditText hjjeEdittext;
    @BindView(R.id.tkje_edittext)
    EditText tkjeEdittext;
    @BindView(R.id.fklx_edittext)
    EditText fklxEdittext;
    @BindView(R.id.jsfs_edittext)
    EditText jsfsEdittext;
    @BindView(R.id.zjzh_edittext)
    EditText zjzhEdittext;
    @BindView(R.id.et_wlgs)
    EditText etWlgs;
    @BindView(R.id.djrq_edittext)
    EditText djrqEdittext;
    @BindView(R.id.et_bm)
    EditText etBm;
    @BindView(R.id.jbr_edittext)
    EditText jbrEdittext;
    @BindView(R.id.bzxx_edittext)
    EditText bzxxEdittext;
    @BindView(R.id.add_scrollview)
    ScrollView addScrollview;


    String gysId = "", gys2Id = "", lxrId = "", jbrId = "", rkckId = "", fklxId = "", jsfsId = "", zjzhId = "";
    private List<Map<String, Object>> list;
    private List<Map<String, Object>> yyList = new ArrayList<Map<String, Object>>();

    BaseAdapter adapter;


    private int selectIndex;
    String billid;//选择完关联的单据后返回的单据的ID


    private String ckId;

    private String xmId;
    private String mTypesname;// 单位类型
    private String mDepartmentid;//部门ID
    private KtWlxxData mWlxxData;
    //代收账户ID
    String proxybankid;
    //发票类型ID
    String billtypeid;
    private String mTaxrate;//税率

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_cggl_cgth_add);
        ButterKnife.bind(this);
        addFHMethod();
        initActivity();
        // searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        xzspListview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                Intent intent = new Intent();
                intent.putExtra("issj", etFplx.getText().toString().equals("收据"));
                intent.setClass(activity, KtCgglSpxqActivity.class);
                intent.putExtra("rkckId", rkckId);
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        djrqEdittext.setText(sdf.format(new Date()));

        bzxxEdittext.setOnTouchListener(new OnTouchListener() {
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


        mTogBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if (arg1) {
                    addScrollview.setVisibility(View.GONE);
                    gldjcgLinearlayout.setVisibility(View.VISIBLE);
                } else {
                    addScrollview.setVisibility(View.VISIBLE);
                    gldjcgLinearlayout.setVisibility(View.GONE);
                }
            }
        });
        xzspLinearlayout = (LinearLayout) findViewById(R.id.xzsp_linearlayout);
        xzspLinearlayout.setOnClickListener(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new JxcCgglCgthAddAdapter(list, this);
        xzspnumTextview.setText("共选择了" + list.size() + "商品");
        xzspListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        mDepartmentid = ShareUserInfo.getKey(this, "departmentid");
        etBm.setText(ShareUserInfo.getKey(this, "depname"));
        jbrEdittext.setText(ShareUserInfo.getKey(this, "opname"));
        jbrId = ShareUserInfo.getKey(this, "empid");


        etFplx.setText("收据");
        billtypeid = "1";
        mTaxrate = "0";


        //s设置默认退款类型
        fklxEdittext.setText("冲抵往来");
        fklxId = "0";
        tkjeEdittext.setText("0");
        EditTextHelper.EditTextEnable(false,tkjeEdittext);



        getMrck();
    }

    /**
     * 连接网络的操作(查询主表的内容)
     */
//    private void searchDate() {
//        Map<String, Object> parmMap = new HashMap<String, Object>();
//        parmMap.put("dbname", ShareUserInfo.getDbName(context));
//        parmMap.put("parms", "CGDD");
//        parmMap.put("billid", billid);
//        findServiceData2(1, ServerURL.BILLMASTER, parmMap, false);
//    }


    private long time;

    @OnClick({R.id.iv_scan, R.id.save_imagebutton, R.id.gys2_edittext, R.id.ck_edittext, R.id.xzxsdd_linearlayout, R.id.gldjcg_linearlayout, R.id.rkck_edittext, R.id.gys_edittext, R.id.lxr_edittext, R.id.lxdh_edittext, R.id.et_fplx, R.id.xm_edittext, R.id.jhdz_edittext, R.id.xzspnum_textview, R.id.xzsp_linearlayout, R.id.gysqk_edittext, R.id.hjje_edittext,  R.id.fklx_edittext, R.id.jsfs_edittext, R.id.zjzh_edittext, R.id.et_wlgs, R.id.djrq_edittext, R.id.et_bm, R.id.jbr_edittext, R.id.bzxx_edittext})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.xzsp_linearlayout:
                if (rkckEdittext.getText().toString().equals("")) {
                    showToastPromopt("请先选择仓库信息！");
                    return;
                }
                intent.putExtra("issj", etFplx.getText().toString().equals("收据"));
                intent.putExtra("taxrate", mTaxrate);
                intent.putExtra("rkckId", rkckId);
                intent.putExtra("tabname", "tb_preturn");
                intent.putExtra("type", "cgth");
                intent.setClass(this, JxcCgglCgddXzspActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.iv_scan://扫一扫选择商品
                if (rkckEdittext.getText().toString().equals("")) {
                    showToastPromopt("请先选择仓库信息！");
                    return;
                }
                startActivityForResult(new Intent(this, WeChatCaptureActivity.class), 18);
                break;
            case R.id.gys_edittext:
                intent.setClass(this, CommonXzdwActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, 1);
                break;
            case R.id.gys2_edittext:
                intent.setClass(this, CommonXzdwActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, 10);
                break;
            case R.id.lxr_edittext:
                if (gysId.equals("")) {
                    showToastPromopt("请先选择供应商信息");
                    return;
                }
                intent.setClass(activity, CommonXzlxrActivity.class);
                intent.putExtra("clientid", gysId);
                startActivityForResult(intent, 2);
                break;
            case R.id.djrq_edittext:
                date_init(djrqEdittext);
                break;
            case R.id.et_bm:
                startActivityForResult(new Intent(this, ChooseDepartmentActivity.class), 15);
                break;
            case R.id.jbr_edittext:
                if (TextUtils.isEmpty(mDepartmentid))
                    showToastPromopt("请先选择部门");
                else
                    startActivityForResult(new Intent(this, SelectSalesmanActivity.class)
                            .putExtra("depid", mDepartmentid), 16);
//                intent.setClass(activity, CommonXzjbrActivity.class);
//                startActivityForResult(intent, 3);
                break;
            case R.id.save_imagebutton:
                if (time == 0 || System.currentTimeMillis() - time > 5000) {
                    searchDateSave();//保存
                    time = System.currentTimeMillis();
                } else {
                    showToastPromopt("请不要频繁点击，防止重复保存");

                }
                break;
            case R.id.xzxsdd_linearlayout://
                intent.putExtra("type", "CGTH_CGSH");

                if (gys2Edittext.getText().toString().equals("")) {
                    showToastPromopt("请先选择供应商");
                    return;
                }
                if (ckEdittext.getText().toString().equals("")) {
                    showToastPromopt("请先选择仓库");
                    return;
                }
                intent.putExtra("clientid", gys2Id);
                intent.putExtra("reftypeid", "7");
                intent.setClass(activity, CommonXzyyActivity.class);
                startActivityForResult(intent, 5);
                break;
            case R.id.rkck_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "STORE");
                startActivityForResult(intent, 6);
                break;
            case R.id.fklx_edittext://退款类型
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "TKLX");
                startActivityForResult(intent, 7);
                break;
            case R.id.jsfs_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "PAYTYPE");
                startActivityForResult(intent, 8);
                break;
            case R.id.zjzh_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "BANK");
                startActivityForResult(intent, 9);
                break;
            case R.id.ck_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "STORE");
                startActivityForResult(intent, 11);
                break;
            case R.id.xm_edittext:
                if (gysId.equals("")) {
                    showToastPromopt("请先选择供应商！");
                    return;
                }
                startActivityForResult(new Intent(this, ChoiceProjectActivity.class)
                                .putExtra("clientid", gysId)
                                .putExtra("clientname", gysEdittext.getText().toString())
                                .putExtra("dwmc", true)
                                .putExtra("typesname", mTypesname),
                        12);
                break;

            case R.id.gldjcg_linearlayout:
                break;

            case R.id.lxdh_edittext:
                break;
            case R.id.et_fplx:
                //选择发票类型
                intent.setClass(activity, KtXzfplxActivity.class);
                intent.putExtra("djlx", "0");
                startActivityForResult(intent, 13);
                break;

            case R.id.jhdz_edittext:
                break;
            case R.id.xzspnum_textview:
                break;

            case R.id.gysqk_edittext:
                break;
            case R.id.hjje_edittext:
                break;


            case R.id.et_wlgs:
                //选择物流公司
                startActivityForResult(new Intent(this, KtWlxxActivity.class)
                                .putExtra("data", new Gson().toJson(mWlxxData))
                        , 14);
                break;

            case R.id.bzxx_edittext:
                break;
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:// 选择商品
                    List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
                            .getSerializableExtra("object");
                    for (int i = 0; i < cpList.size(); i++) {
                        Map<String, Object> map = cpList.get(i);
                        if (map.get("isDetail").equals("0")) {
                            if (map.get("ischecked").equals("1")) {
                                Map<String, Object> map2 = cpList.get(i + 1);
                                map.put("unitprice", map2.get("dj"));
                                map.put("unitqty", map2.get("sl"));
//                            map.put("amount", Double.parseDouble(map2.get("dj").toString())
//                                              * Double.parseDouble(map2.get("sl").toString()));
                                String amount = (Double.parseDouble(map2.get("taxunitprice").toString())
                                        * Double.parseDouble(map2.get("sl").toString())) + "";
                                map.put("amount", FigureTools.sswrFigure(amount + ""));
                                map.put("disc", map2.get("zkl"));
                                map.put("batchcode", map2.get("cpph"));
                                map.put("produceddate", map2.get("scrq"));
                                map.put("validdate", map2.get("yxqz"));

                                map.put("taxrate", map2.get("taxrate"));//税率
                                map.put("taxunitprice", map2.get("taxunitprice"));//含税单价
                                map.put("serialinfo", map2.get("serialinfo").toString());
                                map.put("serials", map2.get("serials"));
                                list.add(map);
                            }
                        }
                    }
                    xzspnumTextview.setText("共选择了" + list.size() + "商品");
                    double zje = 0;
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> map2 = list.get(i);
                        zje += Double.parseDouble(map2.get("amount").toString());
                    }
                    hjjeEdittext.setText("" + FigureTools.sswrFigure(zje + "") + "");
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    if (!gysEdittext.getText().toString().equals("")) {
                        if (!gysEdittext.getText().toString().equals(data.getExtras().getString("name"))) {
                            list.removeAll(yyList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    if (!gysEdittext.getText().toString().equals(data.getExtras().getString("name"))) {
                        lxrEdittext.setText("");
                        lxrId = "";
                        lxdhEdittext.setText("");
                        gysEdittext.setText(data.getExtras().getString("name"));
                        gysId = data.getExtras().getString("id");
                    }
                    lxrEdittext.setText(data.getExtras().getString("lxrname"));
                    lxrId = data.getExtras().getString("lxrid");
                    lxdhEdittext.setText(data.getExtras().getString("phone"));
                    mTypesname = data.getStringExtra("typesname");
                    gysEdittext.setText(data.getExtras().getString("name"));
                    gysId = data.getExtras().getString("id");
                    gysqkEdittext.setText(data.getExtras().getString("qk"));
                    // 清楚項目
                    xmEdittext.setText("");
                    xmId = "";
                    xmEdittext.setText("");
                    xmId = "";
                    break;
                case 2:
                    // 联系人
                    lxrEdittext.setText(data.getExtras().getString("name"));
                    lxrId = data.getExtras().getString("id");
                    lxdhEdittext.setText(data.getExtras().getString("phone"));
                    break;
                case 3:// 经办人
                    jbrEdittext.setText(data.getExtras().getString("name"));
                    jbrId = data.getExtras().getString("id");
                    break;
                case 4://修改选中的商品的详情
                    if (data.getExtras().getSerializable("object").toString().equals("")) {//说明删除了
                        list.remove(selectIndex);
                        adapter.notifyDataSetChanged();
                    } else {
                        Map<String, Object> map = (Map<String, Object>) data.getExtras()
                                .getSerializable("object");
                        map.put("amount", Double.parseDouble(map.get("taxunitprice").toString())
                                * Double.parseDouble(map.get("unitqty").toString()));
                        list.set(selectIndex, map);
                        adapter.notifyDataSetChanged();
                    }
                    xzspnumTextview.setText("共选择了" + list.size() + "商品");
                    double ze = 0;
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> map = list.get(i);
                        ze += Double.parseDouble(map.get("amount").toString());
                    }
                    hjjeEdittext.setText("" + FigureTools.sswrFigure(ze + "") + "");
                    break;
                case 5://选中单据成功后返回
                    addScrollview.setVisibility(View.VISIBLE);//隐藏关联销售单据的Linearlayout
                    gldjcgLinearlayout.setVisibility(View.GONE);//显示展示详情的Linearlayout信息
                    mTogBtn.setChecked(false);
                    list.clear();
                    list.addAll((List<Map<String, Object>>) data.getExtras().getSerializable("list"));
                    yyList.clear();
                    yyList.addAll((List<Map<String, Object>>) data.getExtras().getSerializable("list"));
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            UUID uuid = UUID.randomUUID();
                            list.get(i).put("serialinfo", uuid.toString().toUpperCase());
                            list.get(i).put("serials", new ArrayList<Serial>());//
                            list.get(i).put("taxrate", mTaxrate);
                            Double csje = Double.parseDouble(list.get(i).get("unitprice").toString()) * (Double.parseDouble(mTaxrate) + 100) / 100;
                            list.get(i).put("taxunitprice", FigureTools.sswrFigure(csje));
                            String amount = (csje
                                    * Double.parseDouble(list.get(i).get("unitqty").toString())) + "";
                            list.get(i).put("amount", FigureTools.sswrFigure(amount + ""));
                        }
                        double je = 0;
                        for (Map<String, Object> m : list) {
                            je += Double.parseDouble(m.get("amount").toString());
                        }
                        xzspnumTextview.setText("共选择了" + list.size() + "商品");
                        hjjeEdittext.setText( FigureTools.sswrFigure(je + "") + "");
                        adapter.notifyDataSetChanged();
                    }
//                    xzspnumTextview.setText("共选择了" + list.size() + "商品");
//                    hjjeEdittext.setText(data.getExtras().getString("totalAmount"));
//                    adapter.notifyDataSetChanged();
                    break;
                case 6:
                    rkckEdittext.setText(data.getExtras().getString("dictmc"));
                    rkckId = data.getExtras().getString("id");
                    break;
                case 7:
                    fklxEdittext.setText(data.getExtras().getString("dictmc"));
                    fklxId = data.getExtras().getString("id");
                    //在退款类型为退还现款的情况下改成可以修改（退款类型为冲抵往来退货金额清0）
                    if(data.getStringExtra("dictmc").equals("退还现款")){
                        EditTextHelper.EditTextEnable(true,tkjeEdittext);
                    }else {
                        tkjeEdittext.setText("0");
                        EditTextHelper.EditTextEnable(false,tkjeEdittext);
                    }
//                    if (data.getExtras().getString("id").equals("0")) {
////                	fkjeEditText.setText("0");
//                        hjjeEdittext.setEnabled(false);
//                        jsfsEdittext.setEnabled(false);
//                        zjzhEdittext.setEnabled(false);
//                        jsfsEdittext.setText("");
//                        zjzhEdittext.setText("");
//                        jsfsId = "";
//                        zjzhId = "";
//                    } else {
//                        hjjeEdittext.setEnabled(false);
//                        double je = 0;
//                        for (int i = 0; i < list.size(); i++) {
//                            Map<String, Object> map = list.get(i);
//                            je += Double.parseDouble(map.get("amount").toString());
//                        }
//                        hjjeEdittext.setText("" + FigureTools.sswrFigure(je + "") + "");
//                        jsfsEdittext.setEnabled(true);
//                        zjzhEdittext.setEnabled(true);
//                    }
                    break;
                case 8:
                    jsfsEdittext.setText(data.getExtras().getString("dictmc"));
                    jsfsId = data.getExtras().getString("id");
                    break;
                case 9:
                    zjzhEdittext.setText(data.getExtras().getString("dictmc"));
                    zjzhId = data.getExtras().getString("id");
                    break;
                case 10:
                    gys2Edittext.setText(data.getExtras().getString("name"));
                    gys2Id = data.getExtras().getString("id");
                    gysqkEdittext.setText(data.getExtras().getString("qk"));
                    gysEdittext.setText(data.getExtras().getString("name"));
                    gysId = data.getExtras().getString("id");
                    mTypesname = data.getStringExtra("typesname");
                    break;
                case 11:
                    rkckEdittext.setText(data.getExtras().getString("dictmc"));
                    rkckId = data.getExtras().getString("id");
                    ckEdittext.setText(data.getExtras().getString("dictmc"));
                    ckId = data.getExtras().getString("id");
                    break;
                case 12:
                    xmEdittext.setText(data.getStringExtra("title"));
                    xmId = data.getStringExtra("projectid");
                    break;
                case 13://选择发票类型
                    if (billtypeid.equals(data.getStringExtra("id")))
                        return;
                    etFplx.setText(data.getStringExtra("name"));
                    billtypeid = data.getStringExtra("id");
                    mTaxrate = data.getExtras().getString("taxrate");
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {

                            list.get(i).put("taxrate", mTaxrate);
                            Double csje = Double.parseDouble(list.get(i).get("unitprice").toString()) * (Double.parseDouble(mTaxrate) + 100) / 100;
                            list.get(i).put("taxunitprice", csje + "");
                            String amount = (csje
                                    * Double.parseDouble(list.get(i).get("unitqty").toString())) + "";
                            list.get(i).put("amount", FigureTools.sswrFigure(amount + ""));
                        }
                        double je = 0;
                        for (Map<String, Object> m : list) {
                            je += Double.parseDouble(m.get("amount").toString());
                        }

                        hjjeEdittext.setText("￥" + FigureTools.sswrFigure(je + "") + "");
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 14://选择物流公司
                    mWlxxData = new Gson().fromJson(data.getStringExtra("data"),
                            new TypeToken<KtWlxxData>() {
                            }.getType());
                    etWlgs.setText(mWlxxData.getLogisticname());
                    break;
                case 15:
                    mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
                    etBm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
                    jbrId = "";
                    jbrEdittext.setText("");
                    break;
                case 16:
                    jbrEdittext.setText(data.getExtras().getString("CHOICE_RESULT_TEXT"));
                    jbrId = data.getExtras().getString("CHOICE_RESULT_ID");
                    break;
                case 17:
                    break;
                case 18://扫一扫选择商品
                    if (rkckEdittext.getText().toString().equals("")) {
                        showToastPromopt("请先选择仓库信息！");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("issj", etFplx.getText().toString().equals("收据"));
                    intent.putExtra("taxrate", mTaxrate);
                    intent.putExtra("rkckId", rkckId);
                    intent.putExtra("barcode", data.getStringExtra("qr"));
                    intent.putExtra("tabname", "tb_preturn");
                    intent.putExtra("type", "cgth");
                    intent.setClass(this, JxcCgglCgddXzspActivity.class);
                    startActivityForResult(intent, 0);
//                    Map<String, Object> parmMap = new HashMap<String, Object>();
//                    parmMap.put("dbname", ShareUserInfo.getDbName(context));
//                    parmMap.put("tabname","tb_preturn");
//                    parmMap.put("storeid", rkckId);
//                    parmMap.put("goodscode", "");
//                    parmMap.put("goodstype", "");
//                    parmMap.put("goodsname","");
//                    // parmMap.put("opid", ShareUserInfo.getUserId(context));
//                    parmMap.put("barcode","12001");//新增条码
//                    parmMap.put("curpage", currentPage);
//                    parmMap.put("pagesize", pageSize);
//                    findServiceData2(3, ServerURL.SELECTGOODS, parmMap, false);
                    break;

            }
        }
    }

    /**
     * 获取默认仓库信息
     */
    private void getMrck() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("zdbm", "STORE");
        findServiceData2(4, ServerURL.DATADICT, parmMap, false);
    }


    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("parms", "CGDD");
        parmMap.put("billid", billid);
        findServiceData2(2, ServerURL.BILLDETAIL, parmMap, false);
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (rkckEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择出库仓库");
            return;
        } else if (gysEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择供应商");
            return;
        } else if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        } else if (fklxEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择退款类型");
            return;
        } else if (fklxId.equals("1")) {
            double hjje = Double.parseDouble(hjjeEdittext.getText().toString().replace("￥", "").equals("") ? "0" : hjjeEdittext.getText().toString().replace("￥", ""));
//            double fkje = Double.parseDouble(fkjeEdittext.getText().toString().replace("￥", "").equals("") ? "0" : fkjeEdittext.getText().toString().replace("￥", ""));
            //            37.采购收货、采购退货、销售开单、销售退货不再限制收款（或付款、退款）金额是否大于单据合计金额，收款（或付款、退款）金额大于0即可
            if (hjje < 0/*||fkje>hjje*/) {
                showToastPromopt("付款金额不在范围内！");
                return;
            }
//            else
//                if(jsfsEditText.getText().toString().equals("")){
//                showToastPromopt("请选择结算方式");
//                return;
//            }else
            if (zjzhEdittext.getText().toString().equals("")) {
                showToastPromopt("请选择资金账户");
                return;
            }
        }
        if (djrqEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择单据日期");
            return;
        }
        if (jbrEdittext.getText().toString().equals("")) {
            showToastPromopt("请选择业务员");
            return;
        }
        JSONArray arrayMaster = new JSONArray();
        JSONArray arrayDetail = new JSONArray();
        List<Serial> serialinfo = new ArrayList<Serial>();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid", "0");
            jsonObject.put("code", "");
            jsonObject.put("billdate", djrqEdittext.getText().toString());
            jsonObject.put("storeid", rkckId);
            jsonObject.put("backmoney", fklxId);
            jsonObject.put("paytypeid", jsfsId);
            jsonObject.put("bankid", zjzhId);
//            jsonObject.put("receipt",fkjeEditText.getText().toString());
            jsonObject.put("privilege", "");
            jsonObject.put("totalamt", hjjeEdittext.getText().toString());//合计金额
            jsonObject.put("receipt", tkjeEdittext.getText().toString());//退款金额
            jsonObject.put("clientid", gysId);//供应商ID
            jsonObject.put("linkmanid", lxrId);//联系人ID
            jsonObject.put("phone", lxdhEdittext.getText().toString());
//            jsonObject.put("billto", jhdzEditText.getText().toString());
            jsonObject.put("departmentid", mDepartmentid);
            jsonObject.put("exemanid", jbrId);

            jsonObject.put("projectid", xmId);
//            String hjje = hjjeEditText.getText().toString();
//            jsonObject.put("amount", hjje.replace("￥", ""));
            jsonObject.put("memo", bzxxEdittext.getText().toString());
            jsonObject.put("opid", ShareUserInfo.getUserId(context));

            jsonObject.put("billtypeid", billtypeid);//发票类型ID
            if (mWlxxData != null) {
                jsonObject.put("logisticid", mWlxxData.getLogisticid());//新加物流公司ID
                jsonObject.put("shipno", mWlxxData.getShipno());//物流单号
                jsonObject.put("shiptype", mWlxxData.getShiptype());//运输方式
                jsonObject.put("beartype", mWlxxData.getBeartype());//运费承担 0我方 1对方
                jsonObject.put("logisticispp", mWlxxData.getLogisticispp());//付款类型 0往来结算 1现款结算
                jsonObject.put("logisticbankid", mWlxxData.getLogisticbankid());//付款账户ID
                jsonObject.put("amount", mWlxxData.getAmount());//运费
                jsonObject.put("isnotice", mWlxxData.getIsnotice());// 通知放货 0否 1是
            }
            arrayMaster.put(jsonObject);
            for (Map<String, Object> map : list) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", "0");
                jsonObject2.put("itemno", "0");
                jsonObject2.put("goodsid", map.get("goodsid").toString());
                jsonObject2.put("unitid", map.get("unitid").toString());
                jsonObject2.put("unitprice", map.get("unitprice").toString());
                jsonObject2.put("unitqty", map.get("unitqty").toString());
                String disc = map.get("disc").toString();
                jsonObject2.put("disc", disc);
                jsonObject2.put("amount", map.get("amount").toString());
                jsonObject2.put("batchcode", map.get("batchcode").toString());
                jsonObject2.put("produceddate", map.get("produceddate").toString());
                jsonObject2.put("validdate", map.get("validdate").toString());
                jsonObject2.put("ispresent", "");

                jsonObject2.put("refertype", map.get("refertype") == null ? "" : map.get("refertype").toString());
                jsonObject2.put("batchrefid", map.get("batchrefid") == null ? "" : map.get("batchrefid").toString());
                jsonObject2.put("referbillid ", map.get("referbillid") == null ? "" : map.get("referbillid").toString());
                jsonObject2.put("referitemno ", map.get("referitemno") == null ? "" : map.get("referitemno").toString());


                jsonObject2.put("serialinfo", map.get("serialinfo").toString());//
                jsonObject2.put("taxrate", map.get("taxrate").toString());//税率%
                jsonObject2.put("taxunitprice", map.get("taxunitprice").toString());//含税单价
                jsonObject2.put("memo", "");//备注
                arrayDetail.put(jsonObject2);
                serialinfo.addAll((ArrayList<Serial>) map.get("serials"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        //		parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("tabname", "tb_preturn");
        parmMap.put("parms", "CGTH");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        parmMap.put("serialinfo", new Gson().toJson(serialinfo));
        findServiceData2(0, "billsavenew", parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        switch (returnSuccessType) {
            case 0:
                if (returnJson.equals("")) {
                    showToastPromopt("保存成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showToastPromopt("保存失败" + returnJson.substring(5));
                }
                break;
            case 3: //扫一扫选择商品
                Map<String, Object> map = ((ArrayList<Map<String, Object>>) PaseJson
                        .paseJsonToObject(returnJson)).get(0);
                map.put("unitprice", map.get("aprice"));//单价
                map.put("unitqty", "1");//數量
                map.put("disc", "100");//单价
                map.put("batchcode", "");//单价
                map.put("produceddate", "");//单价
                map.put("validdate", "");//单价
                map.put("taxrate", mTaxrate);//税率
                Double csje = Double.parseDouble(map.get("aprice").toString()) * (Double.parseDouble(mTaxrate) + 100) / 100;
                map.put("taxunitprice", csje + "");//单价
                map.put("amount", FigureTools.sswrFigure(csje.toString()));


                map.put("issj", etFplx.getText().toString().equals("收据"));
                UUID uuid = UUID.randomUUID();
                map.put("serialinfo", uuid.toString().toUpperCase());//
                map.put("serials", new ArrayList<Serial>());//
                list.add(map);
                adapter.notifyDataSetChanged();
                xzspnumTextview.setText("共选择了" + list.size() + "商品");
                break;

            case 4://获取默认仓库信息
                if (returnJson.equals("")) {
                    showToastPromopt(2);
                } else {
                    List<Map<String, Object>> ckList= (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
                    if(ckList.size()==1){
                        rkckEdittext.setText(ckList.get(0).get("dictmc").toString());
                        rkckId = ckList.get(0).get("id").toString();
                    }

                }
                break;
        }
        if (returnSuccessType == 1) {//管理单据成功后把信息填到里面（主表）
            if (returnJson.equals("")) {
                return;
            }
            Map<String, Object> object = ((List<Map<String, Object>>) PaseJson
                    .paseJsonToObject(returnJson)).get(0);
            gysEdittext.setText(object.get("cname").toString());
            lxrEdittext.setText(object.get("lxrname").toString());
            lxdhEdittext.setText(object.get("phone").toString());
            jhdzEdittext.setText(object.get("billto").toString());
//            hjjeEditText.setText(object.get("amount").toString());
           hjjeEdittext.setText(object.get("totalamt").toString());
            djrqEdittext.setText(object.get("billdate").toString());
            jbrEdittext.setText(object.get("empname").toString());
            bzxxEdittext.setText(object.get("memo").toString());
            gysId = object.get("clientid").toString();
            lxrId = object.get("lxrid").toString();
            jbrId = object.get("empid").toString();
            searchDate2();//查询订单中的商品
        } else if (returnSuccessType == 2) {//管理单据成功后把信息填到里面（从表）
            list = (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
            adapter = new JxcCgglCgddDetailAdapter(list, this);
            xzspnumTextview.setText("共选择了" + list.size() + "商品");
            xzspListview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}

// if (requestCode == 0) {// 选择商品
////                list.clear();
//         List<Map<String, Object>> cpList = (List<Map<String, Object>>) data
//        .getSerializableExtra("object");
//        for (int i = 0; i < cpList.size(); i++) {
//        Map<String, Object> map = cpList.get(i);
//        if (map.get("isDetail").equals("0")) {
//        if (map.get("ischecked").equals("1")) {
//        Map<String, Object> map2 = cpList.get(i + 1);
//        map.put("unitprice", map2.get("dj"));
//        map.put("unitqty", map2.get("sl"));
////                            map.put("amount", Double.parseDouble(map2.get("dj").toString())
////                                              * Double.parseDouble(map2.get("sl").toString()));
//        String amount = (Double.parseDouble(map2.get("dj").toString())
//        * Double.parseDouble(map2.get("sl").toString())) + "";
//        map.put("amount", FigureTools.sswrFigure(amount + ""));
//        map.put("disc", map2.get("zkl"));
//        map.put("batchcode", map2.get("cpph"));
//        map.put("produceddate", map2.get("scrq"));
//        map.put("validdate", map2.get("yxqz"));
//        list.add(map);
//        }
//        }
//        }
//        xzspnumTextView.setText("共选择了" + list.size() + "商品");
//        double zje = 0;
//        for (int i = 0; i < list.size(); i++) {
//        Map<String, Object> map2 = list.get(i);
//        zje += Double.parseDouble(map2.get("amount").toString());
//        }
//        fkjeEditText.setText("" + FigureTools.sswrFigure(zje + "") + "");
//        adapter.notifyDataSetChanged();
//        } else if (requestCode == 1) {
//        if (!gysEditText.getText().toString().equals("")) {
//        if (!gysEditText.getText().toString().equals(data.getExtras().getString("name"))) {
//        list.removeAll(yyList);
//        adapter.notifyDataSetChanged();
//        }
//        }
//        if (!gysEditText.getText().toString().equals(data.getExtras().getString("name"))) {
//        lxrEditText.setText("");
//        lxrId = "";
//        lxdhEditText.setText("");
//        gysEditText.setText(data.getExtras().getString("name"));
//        gysId = data.getExtras().getString("id");
//        }
//        lxrEditText.setText(data.getExtras().getString("lxrname"));
//        lxrId = data.getExtras().getString("lxrid");
//        lxdhEditText.setText(data.getExtras().getString("phone"));
//        mTypesname = data.getStringExtra("typesname");
//        gysEditText.setText(data.getExtras().getString("name"));
//        gysId = data.getExtras().getString("id");
//        gysqkEditText.setText(data.getExtras().getString("qk"));
//        // 清楚項目
//        xmEditText.setText("");
//        xmId = "";
//        xmEditText.setText("");
//        xmId = "";
//        } else if (requestCode == 2) {// 联系人
//        lxrEditText.setText(data.getExtras().getString("name"));
//        lxrId = data.getExtras().getString("id");
//        lxdhEditText.setText(data.getExtras().getString("phone"));
//        } else if (requestCode == 3) {// 经办人
//        jbrEditText.setText(data.getExtras().getString("name"));
//        jbrId = data.getExtras().getString("id");
//        } else if (requestCode == 4) {//修改选中的商品的详情
//        if (data.getExtras().getSerializable("object").toString().equals("")) {//说明删除了
//        list.remove(selectIndex);
//        adapter.notifyDataSetChanged();
//        } else {
//        Map<String, Object> map = (Map<String, Object>) data.getExtras()
//        .getSerializable("object");
//        list.remove(selectIndex);
//        map.put(
//        "amount",
//        map.put("amount", Double.parseDouble(map.get("unitprice").toString())
//        * Double.parseDouble(map.get("unitqty").toString())));
//        list.add(selectIndex, map);
//        adapter.notifyDataSetChanged();
//        }
//        xzspnumTextView.setText("共选择了" + list.size() + "商品");
//        double zje = 0;
//        for (int i = 0; i < list.size(); i++) {
//        Map<String, Object> map = list.get(i);
//        zje += Double.parseDouble(map.get("amount").toString());
//        }
//        fkjeEditText.setText("" + FigureTools.sswrFigure(zje + "") + "");
//        } else if (requestCode == 5) {//选中单据成功后返回
//        addScrollView.setVisibility(View.VISIBLE);//隐藏关联销售单据的Linearlayout
//        gldjcgLinearLayout.setVisibility(View.GONE);//显示展示详情的Linearlayout信息
//        toggleButton.setChecked(false);
//        list.clear();
//        list.addAll((List<Map<String, Object>>) data.getExtras().getSerializable("list"));
//        yyList.clear();
//        yyList.addAll((List<Map<String, Object>>) data.getExtras().getSerializable("list"));
//        xzspnumTextView.setText("共选择了" + list.size() + "商品");
//        fkjeEditText.setText(data.getExtras().getString("totalAmount"));
//        adapter.notifyDataSetChanged();
//        } else if (requestCode == 6) {
////            	if(!rkckEditText.getText().toString().equals(data.getExtras().getString("dictmc"))){
////            		list.clear();
////            		adapter.notifyDataSetChanged();
////            	}
//        rkckEditText.setText(data.getExtras().getString("dictmc"));
//        rkckId = data.getExtras().getString("id");
//        } else if (requestCode == 7) {
//        fklxEditText.setText(data.getExtras().getString("dictmc"));
//        fklxId = data.getExtras().getString("id");
//        if (data.getExtras().getString("id").equals("0")) {
////                	fkjeEditText.setText("0");
//        fkjeEditText.setEnabled(false);
//        jsfsEditText.setEnabled(false);
//        zjzhEditText.setEnabled(false);
//        jsfsEditText.setText("");
//        zjzhEditText.setText("");
//        jsfsId = "";
//        zjzhId = "";
//        } else {
//        fkjeEditText.setEnabled(false);
//        double zje = 0;
//        for (int i = 0; i < list.size(); i++) {
//        Map<String, Object> map = list.get(i);
//        zje += Double.parseDouble(map.get("amount").toString());
//        }
//        fkjeEditText.setText("" + FigureTools.sswrFigure(zje + "") + "");
//        jsfsEditText.setEnabled(true);
//        zjzhEditText.setEnabled(true);
//        }
//        } else if (requestCode == 8) {
//        jsfsEditText.setText(data.getExtras().getString("dictmc"));
//        jsfsId = data.getExtras().getString("id");
//        } else if (requestCode == 9) {
//        zjzhEditText.setText(data.getExtras().getString("dictmc"));
//        zjzhId = data.getExtras().getString("id");
//        } else if (requestCode == 10) {
//        gys2EditText.setText(data.getExtras().getString("name"));
//        gys2Id = data.getExtras().getString("id");
//        gysqkEditText.setText(data.getExtras().getString("qk"));
//        gysEditText.setText(data.getExtras().getString("name"));
//        gysId = data.getExtras().getString("id");
//        mTypesname = data.getStringExtra("typesname");
//
//        } else if (requestCode == 11) {
//        rkckEditText.setText(data.getExtras().getString("dictmc"));
//        rkckId = data.getExtras().getString("id");
//        ckEditText.setText(data.getExtras().getString("dictmc"));
//        ckId = data.getExtras().getString("id");
//        } else if (requestCode == 12) {
////            	xmEditText.setText(data.getExtras().getString("xmname"));
////            	xmId=data.getExtras().getString("xmid");
//        xmEditText.setText(data.getStringExtra("title"));
//        xmId = data.getStringExtra("projectid");
//        } else if (requestCode == 15) {
//        mDepartmentid = data.getStringExtra("CHOICE_RESULT_ID");
//        etBm.setText(data.getStringExtra("CHOICE_RESULT_TEXT"));
//        jbrId = "";
//        jbrEditText.setText("");
//        } else if (requestCode == 16) {
//        jbrEditText.setText(data.getExtras().getString("CHOICE_RESULT_TEXT"));
//        jbrId = data.getExtras().getString("CHOICE_RESULT_ID");
//        }
//        }