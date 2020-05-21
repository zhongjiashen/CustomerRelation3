package com.cr.activity.khfw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.common.CommonXzjbrActivity;
import com.cr.activity.common.CommonXzjhActivity;
import com.cr.activity.common.CommonXzkhActivity;
import com.cr.activity.common.CommonXzlxrActivity;
import com.cr.activity.common.CommonXzzdActivity;
import com.cr.activity.jxc.cggl.cgdd.JxcCgglCgddShlcActivity;
import com.cr.adapter.khfw.KhfwAddAdapter;
import com.cr.tools.CustomListView;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 客户服务-详情
 * @author Administrator
 *
 */
public class KhfwDetailActivity extends BaseActivity implements OnClickListener {
    private ImageView                 shImageView;
    private ImageButton               saveImageButton;
    private Button                    shButton, sdButton;
    private TextView                  djbhTextView;
    private EditText                  gysEditText, lxrEditText, lxdhEditText, djlxEditText,
            jhmcEditText, sqrEditText, ksrqEditText, fwfsEditText, djrqEditText, jbrEditText,
            bzxxEditText,fwzjEditText;
    private CustomListView            listview;
    private List<Map<String, Object>> list;
    private LinearLayout              xzspLinearLayout;
    BaseAdapter                       adapter;
    String                            gysId, lxrId, jbrId,djlxId,jhmcId,fwfsId;
    private int                       selectIndex;
    private String                    shzt;               //社会状态
    Map<String, Object>               object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khfw_detail);
        addFHMethod();
        initActivity();
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        saveImageButton = (ImageButton) findViewById(R.id.save_imagebutton);
        saveImageButton.setOnClickListener(this);
        djbhTextView = (TextView) findViewById(R.id.djbh_textview);
        shImageView = (ImageView) findViewById(R.id.sh_imageview);
        gysEditText = (EditText) findViewById(R.id.gys_edittext);
        gysEditText.setOnClickListener(this);
        lxrEditText = (EditText) findViewById(R.id.lxr_edittext);
        lxrEditText.setOnClickListener(this);
        lxdhEditText = (EditText) findViewById(R.id.lxdh_edittext);
        djlxEditText = (EditText) findViewById(R.id.djlx_edittext);
        djlxEditText.setOnClickListener(this);
        jhmcEditText = (EditText) findViewById(R.id.jhmc_edittext);
        jhmcEditText.setOnClickListener(this);
        sqrEditText = (EditText) findViewById(R.id.sqr_edittext);
        ksrqEditText = (EditText) findViewById(R.id.ksrq_edittext);
        ksrqEditText.setOnClickListener(this);
        fwfsEditText = (EditText) findViewById(R.id.fwfs_edittext);
        fwfsEditText.setOnClickListener(this);
        listview = (CustomListView) findViewById(R.id.xzsp_listview);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectIndex = arg2;
                Intent intent = new Intent();
                intent.setClass(activity, KhfwAddBjfwcpActivity.class);
                intent.putExtra("object", (Serializable) list.get(arg2));
                startActivityForResult(intent, 4);
            }
        });
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
        xzspLinearLayout = (LinearLayout) findViewById(R.id.xzsp_linearlayout);
        xzspLinearLayout.setOnClickListener(this);
        list = new ArrayList<Map<String, Object>>();
        adapter = new KhfwAddAdapter(list, this);
//        xzspnumTextView.setText("共选择了" + list.size() + "商品");
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        shButton = (Button) findViewById(R.id.sh_button);
        shButton.setOnClickListener(this);
        
        sdButton = (Button) findViewById(R.id.sd_button);
        sdButton.setOnClickListener(this);
        fwzjEditText=(EditText) findViewById(R.id.fwzj_edittext);
        fwzjEditText.setOnTouchListener(new OnTouchListener() {
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
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "KHFW");
        parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
        findServiceData2(0, ServerURL.BILLMASTER, parmMap, false);
    }

    /**
     * 连接网络的操作（查询从表的内容）
     */
    private void searchDate2() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("parms", "KHFW");
        parmMap.put("billid", this.getIntent().getExtras().getString("billid"));
        findServiceData2(1, ServerURL.BILLDETAIL, parmMap, false);
    }

    /**
     * 连接网络的操作(删单)
     */
    private void searchDateSd() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("opid", ShareUserInfo.getUserId(mContext));
        parmMap.put("tabname", "tb_sreturn");
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
            gysEditText.setText(object.get("cname").toString());
            lxrEditText.setText(object.get("lxrname").toString());
            lxdhEditText.setText(object.get("phone").toString());
            djlxEditText.setText(object.get("billtypename").toString());
            jhmcEditText.setText(object.get("chancename").toString());
            sqrEditText.setText(object.get("bxr").toString());
            ksrqEditText.setText(object.get("bsrq").toString());
            djrqEditText.setText(object.get("billdate").toString());
            jbrEditText.setText(object.get("empanme").toString());
            bzxxEditText.setText(object.get("memo").toString());
            fwfsEditText.setText(object.get("sxfs").toString());
            fwzjEditText.setText(object.get("wxzj").toString());
            djbhTextView.setText(object.get("code").toString());
            if (object.get("shzt").toString().equals("0")) {
                shImageView.setBackgroundResource(R.drawable.wsh);
                shButton.setText("审核");
            } else if (object.get("shzt").toString().equals("1")) {
                shImageView.setBackgroundResource(R.drawable.ysh);
                shButton.setText("撤消");
            } else if (object.get("shzt").toString().equals("2")) {
                shImageView.setBackgroundResource(R.drawable.shz);
            }
            gysId = object.get("clientid").toString();
            lxrId = object.get("lxrid").toString();
            jbrId = object.get("empid").toString();
            djlxId = object.get("billtypeid").toString();
            jhmcId = object.get("chanceid").toString();
            fwfsId = object.get("sxfs").toString();
            shzt = object.get("shzt").toString();
            showZdr(object);
            searchDate2();//查询订单中的商品
        } else if (returnSuccessType == 1) {
            if(returnJson.equals("")){
                return;
            }
            list = (List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson);
            adapter = new KhfwAddAdapter(list, this);
//            xzspnumTextView.setText("共选择了" + list.size() + "商品");
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
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
        }else if(returnSuccessType==4){
            if(returnJson.equals("")){
                showToastPromopt("操作成功");
                searchDate();
            }else{
                showToastPromopt("操作失败"+returnJson.substring(5));
            }
        }
    }

    /**
     * 连接网络的操作(保存)
     */
    private void searchDateSave() {
        if (!shzt.equals("0")) {
            showToastPromopt("该单据在审核阶段，不能进行修改");
            return;
        }
        if (gysEditText.getText().toString().equals("")) {
            showToastPromopt("请选择客户");
            return;
        }else if (djlxEditText.getText().toString().equals("")) {
            showToastPromopt("请选单据类型");
            return;
        }else if (list.size() == 0) {
            showToastPromopt("请选择商品");
            return;
        } if (jbrEditText.getText().toString().equals("")) {
            showToastPromopt("请选择业务员");
            return;
        }
        JSONArray arrayMaster = new JSONArray();
        JSONArray arrayDetail = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("billid", this.getIntent().getExtras().getString("billid"));
            jsonObject.put("code", object.get("code").toString());
            jsonObject.put("billtypeid", djlxId);
            jsonObject.put("billdate", djrqEditText.getText().toString());
            jsonObject.put("clientid", gysId);//供应商ID
            jsonObject.put("lxrid", lxrId);//联系人ID
            jsonObject.put("chanceid",jhmcId);//联系人ID
            jsonObject.put("bsrq",ksrqEditText.getText().toString());
            jsonObject.put("bxr", sqrEditText.getText().toString());
            jsonObject.put("phone", lxdhEditText.getText().toString());
            jsonObject.put("sxfs", fwfsEditText.getText().toString());
            jsonObject.put("wxzj", fwzjEditText.getText().toString());
            jsonObject.put("memo", bzxxEditText.getText().toString());
            jsonObject.put("empid", jbrId);
            jsonObject.put("opid", ShareUserInfo.getUserId(mContext));
            double amount=0;
            for (Map<String, Object> map : list) {
                String sl=map.get("sl")==null?map.get("unitqty").toString():map.get("sl").toString();
                String pj=map.get("pj")==null?map.get("pjxh").toString():map.get("pj").toString();
                String fwnr=map.get("fwnr")==null?map.get("gzms").toString():map.get("fwnr").toString();
                String jjfa=map.get("jjfa")==null?map.get("jjfa").toString():map.get("jjfa").toString();
                String yy=map.get("yy")==null?map.get("fyyy").toString():map.get("yy").toString();
                String wcrq=map.get("wcrq")==null?map.get("wcrq").toString():map.get("wcrq").toString();
                String bz=map.get("bz")==null?map.get("memo").toString():map.get("bz").toString();
                String cpmcId=map.get("cpmcid")==null?map.get("goodsid").toString():map.get("cpmcid").toString();
                String shcdId=map.get("shcdid")==null?map.get("shcd").toString():map.get("shcdid").toString();
                String fwryId=map.get("fwryid")==null?map.get("wxry").toString():map.get("fwryid").toString();
                String sfbzqnId=map.get("sfbzqnid")==null?map.get("zbzt").toString():map.get("sfbzqnid").toString();
                String fwjdId=map.get("fwjdid")==null?map.get("wxjg").toString():map.get("fwjdid").toString();
                String fwztId=map.get("fwztid")==null?map.get("wxjd").toString():map.get("fwztid").toString();
                String mydId=map.get("mydid")==null?map.get("mycd").toString():map.get("mydid").toString();
                String utilId=map.get("unitid")==null?map.get("unitid").toString():map.get("unitid").toString();
                String fwfy=map.get("fwfy")==null?map.get("amount").toString():map.get("fwfy").toString();
                if(!fwfy.equals("")){
                	amount+=Double.parseDouble(fwfy);
                }
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("billid", this.getIntent().getExtras().getString("billid"));
                jsonObject2.put("goodsid",  cpmcId);
                jsonObject2.put("unitid", utilId);
                jsonObject2.put("unitqty", sl);
                jsonObject2.put("amount", fwfy);
                jsonObject2.put("shcd", shcdId);
                jsonObject2.put("pjxh", pj);
                jsonObject2.put("wxdw", map.get("wxdw").toString());
                jsonObject2.put("gzms", fwnr);
                jsonObject2.put("fyyy", yy);//原因
                jsonObject2.put("jjfa", jjfa);
                jsonObject2.put("wxjg", fwjdId);//服务进度
                jsonObject2.put("wxry", fwryId);
                jsonObject2.put("wcrq",wcrq);//完成日期
                jsonObject2.put("wxjd ", fwztId);//服务状态
                jsonObject2.put("mycd ", mydId);//满意程度
                jsonObject2.put("zbzt ", sfbzqnId);
                jsonObject2.put("memo ", bz);//备注信息
                arrayDetail.put(jsonObject2);
            }
            jsonObject.put("amount", amount+"");
            arrayMaster.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }//代表新增
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        //      parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("parms", "KHFW");
        parmMap.put("master", arrayMaster.toString());
        parmMap.put("detail", arrayDetail.toString());
        findServiceData2(3, ServerURL.BILLSAVE, parmMap, false);
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.sh_button:
//            	intent.putExtra("tb", "shwx");
//            	intent.putExtra("opid", object.get("opid").toString());
//                intent.putExtra("billid", this.getIntent().getExtras().getString("billid"));
//                intent.setClass(activity, JxcCgglCgddShlcActivity.class);
//                startActivityForResult(intent,9);
            	if(object.get("shzt").toString().equals("0")){
            		searchDateSh("1");
            	}else {
            		searchDateSh("0");
            	}
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
            case R.id.xzsp_linearlayout:
                intent.setClass(this, KhfwAddXzfwcpActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.gys_edittext:
                intent.setClass(this, CommonXzkhActivity.class);
                startActivityForResult(intent, 1);
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
                date_init(djrqEditText);
                break;
            case R.id.jbr_edittext:
                intent.setClass(activity, CommonXzjbrActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.save_imagebutton:
                searchDateSave();//保存
                break;
            case R.id.xzxsdd_linearlayout://选择销售订单
                intent.setClass(activity, KhfwActivity.class);
                intent.putExtra("select", "true");
                startActivityForResult(intent,5);
                break;
            case R.id.djlx_edittext:
                intent.putExtra("type","SHWX");
                intent.setClass(activity, CommonXzzdActivity.class);
                startActivityForResult(intent, 6);
                break;
            case R.id.jhmc_edittext:
                intent.setClass(activity, CommonXzjhActivity.class);
                intent.putExtra("clientid", gysId);
                startActivityForResult(intent, 7);
                break;
            case R.id.ksrq_edittext:
                date_init(ksrqEditText);
                break;
            case R.id.fwfs_edittext:
                intent.setClass(activity, CommonXzzdActivity.class);
                intent.putExtra("type", "FWFS");
                startActivityForResult(intent, 8);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {// 选择商品
                Map<String, Object> map=(Map<String, Object>) data.getExtras().getSerializable("object");
                list.add(map);
                adapter.notifyDataSetChanged();
            } else if (requestCode == 1) {
                gysEditText.setText(data.getExtras().getString("name"));
                gysId = data.getExtras().getString("id");
            } else if (requestCode == 2) {// 联系人
                lxrEditText.setText(data.getExtras().getString("name"));
                lxrId = data.getExtras().getString("id");
            } else if (requestCode == 3) {// 经办人
                jbrEditText.setText(data.getExtras().getString("name"));
                jbrId = data.getExtras().getString("id");
            } else if (requestCode == 4) {//修改选中的商品的详情
                if (data.getExtras().getSerializable("object").toString().equals("")) {//说明删除了
                    list.remove(selectIndex);
                    adapter.notifyDataSetChanged();
                } else {
                    Map<String, Object> map = (Map<String, Object>) data.getExtras()
                        .getSerializable("object");
                    list.remove(selectIndex);
                    list.add(selectIndex, map);
                    adapter.notifyDataSetChanged();
                }
            }else if(requestCode==5){//选中单据成功后返回
//               
            }else if(requestCode==6){
                djlxEditText.setText(data.getExtras().getString("dictmc"));
                djlxId = data.getExtras().getString("id");
            }else if(requestCode==7){
                jhmcEditText.setText(data.getExtras().getString("name"));
                jhmcId = data.getExtras().getString("id");
            }else if(requestCode==8){
                fwfsEditText.setText(data.getExtras().getString("dictmc"));
                fwfsId = data.getExtras().getString("id");
            }else if(requestCode==9){
                searchDate();
            }
        }
    }
    
    /**
     * 连接网络的操作(审核)
     */
    private void searchDateSh(String shzt) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("tabname", "SHWX");
        parmMap.put("pkvalue",this.getIntent().getExtras().getString("billid"));
        parmMap.put("levels", "0");
        parmMap.put("opid", ShareUserInfo.getUserId(mContext));
        parmMap.put("shzt", shzt);
        findServiceData2(4, ServerURL.BILLSH, parmMap, false);
    }
}
