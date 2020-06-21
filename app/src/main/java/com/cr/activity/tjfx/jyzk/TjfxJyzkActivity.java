package com.cr.activity.tjfx.jyzk;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.adapter.tjfx.jyzk.TjfxJyzkAdapter;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 统计分析-经营状况
 * 
 * @author Administrator
 * 
 */
public class TjfxJyzkActivity extends BaseActivity implements OnClickListener {
    private TjfxJyzkAdapter adapter;
    private ListView          listView;
    EditText                   searchEditText;
    ImageButton                sxButton;
    List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();
    String                     qsrq, jzrq, shzt = "0", cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjfx_jyzk);
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

        sxButton = (ImageButton) findViewById(R.id.sx);
        sxButton.setOnClickListener(this);
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        listView = (ListView) findViewById(R.id.xlistview);
        adapter = new TjfxJyzkAdapter(list, this);
        listView.setAdapter(adapter);
//        listView.setXListViewListener(xListViewListener);
//        listView.setPullLoadEnable(true);
//        listView.setPullRefreshEnable(false);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            }
        });
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(context));
        parmMap.put("qsrq", qsrq);
        parmMap.put("zzrq", jzrq);
        parmMap.put("opid", ShareUserInfo.getUserId(context));
//        parmMap.put("curpage", currentPage);
//        parmMap.put("pagesize", pageSize);
        findServiceData2(0, ServerURL.ENTERPRISEWORKRPT, parmMap, false);
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
                intent.setClass(context, TjfxJyzkSearchActivity.class);
                intent.putExtra("qr", qsrq);
                intent.putExtra("zr", jzrq);
//                intent.putExtra("kh", cname);
//                intent.putExtra("sh", shzt);
                startActivityForResult(intent, 0);
                break;
        }
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
            } 
        }
    }
}