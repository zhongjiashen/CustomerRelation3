package com.cr.activity.jxc.xsgl.xsdd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.adapter.jxc.xsgl.xsdd.JxcXsglXsddAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 进销存-销售管理-销售订单
 * 
 * @author Administrator
 * 
 */
public class JxcXsglXsddActivity extends BaseActivity implements OnClickListener {
    private JxcXsglXsddAdapter adapter;
    private XListView          listView;
    EditText                   searchEditText;
    ImageButton                sxButton, xzButton;
    List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();
    String                     qsrq, jzrq, shzt = "0", cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxc_xsgl_xsdd);
        addFHMethod();
        initActivity();
        initListView();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-");
        qsrq = sdf.format(new Date()) + "01";
        jzrq = sdf.format(new Date()) + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        searchEditText = (EditText) findViewById(R.id.search);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    list.clear();
                    currentPage = 1;
                    searchDate();
                    return true;
                }
                return false;
            }
        });

        sxButton = (ImageButton) findViewById(R.id.sx);
        sxButton.setOnClickListener(this);
        xzButton = (ImageButton) findViewById(R.id.xz);
        xzButton.setOnClickListener(this);
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        listView = (XListView) findViewById(R.id.xlistview);
        adapter = new JxcXsglXsddAdapter(list, this);
        listView.setAdapter(adapter);
        listView.setXListViewListener(xListViewListener);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(false);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(context, KtJxcXsglXsddDetailActivity.class);
                intent.putExtra("billid", list.get(arg2-1).get("billid").toString());
                if(JxcXsglXsddActivity.this.getIntent().hasExtra("select")){//如果是添加订单时候关联的操作
                    setResult(RESULT_OK,intent);
                    finish();
                }else{//否则就是正常情况的打开
                    startActivityForResult(intent,1);
                    adapter.setSelectIndex(arg2);
                }
            }
        });
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("tabname", "tb_sorder");
        parmMap.put("qsrq", qsrq);
        parmMap.put("zzrq", jzrq);
        parmMap.put("shzt", shzt);//默认未审核
        parmMap.put("billcode", searchEditText.getText().toString());
        parmMap.put("cname", cname);
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("curpage", currentPage);
        parmMap.put("pagesize", pageSize);
        findServiceData2(0, ServerURL.BILLLIST, parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
    	if(returnJson.equals("nmyqx")){
    		Toast.makeText(this,"您没有该功能菜单的权限！请与管理员联系！", Toast.LENGTH_SHORT)
			.show();
    		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 300);
    		return;
    	}
        if (returnJson.equals("")) {
            showToastPromopt(2);
        } else {
            list.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        switch (arg0.getId()) {
            case R.id.sx:
                intent.setClass(context, JxcXsglXsddSearchActivity.class);
                intent.putExtra("qr", qsrq);
                intent.putExtra("zr", jzrq);
                intent.putExtra("kh", cname);
                intent.putExtra("sh", shzt);
                startActivityForResult(intent, 0);
                break;
            case R.id.xz:
                if (!ShareUserInfo.getKey(activity, "xz").equals("1")) {
                    showToastPromopt("你没有该权限，请向管理员申请权限！");
                    return;
                }
                intent.setClass(context, KtJxcXsglXsddAddActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    /**
     * 刷新
     */
    private IXListViewListener xListViewListener = new IXListViewListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         handler.postDelayed(new Runnable() {// 实现延迟2秒加载刷新
                                                                 @Override
                                                                 public void run() {
                                                                     // 不实现顶部刷新
                                                                 }
                                                             }, 2000);
                                                     }

                                                     @Override
                                                     public void onLoadMore() {
                                                         handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
                                                                 @Override
                                                                 public void run() {
                                                                     currentPage++;
                                                                     searchDate();
                                                                     onLoad();
                                                                 }
                                                             }, 2000);
                                                     }
                                                 };

    @SuppressWarnings("deprecation")
    private void onLoad() {// 停止刷新和加载功能，并且显示时间
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime(new Date().toLocaleString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                qsrq = data.getExtras().getString("qr");
                jzrq = data.getExtras().getString("zr");
                shzt = data.getExtras().getString("sh");
                cname = data.getExtras().getString("kh");
                list.clear();
                currentPage = 1;
                searchDate();
            } else if (requestCode == 1) {//新增成功
                list.clear();
                currentPage = 1;
                searchDate();
            }
        }
    }
}