package com.cr.activity.tjfx.zjzh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cr.activity.BaseActivity;
import com.cr.adapter.tjfx.zjzh.TjfxZjzhAdapter;
import com.cr.tools.FigureTools;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

/**
 * 统计分析-资金账户
 * 
 * @author Administrator
 * 
 */
public class TjfxZjzhActivity extends BaseActivity implements OnClickListener {
    private TjfxZjzhAdapter adapter;
    private ListView          listView;
    private TextView zzcTextView;
    List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjfx_zjzh);
        addFHMethod();
        initActivity();
        initListView();
        searchDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        zzcTextView=(TextView) findViewById(R.id.zzc);
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        listView = (ListView) findViewById(R.id.xlistview);
        adapter = new TjfxZjzhAdapter(list, this);
        listView.setAdapter(adapter);
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
        parmMap.put("opid", ShareUserInfo.getUserId(context));
        findServiceData2(0, ServerURL.BANKRPT, parmMap, false);
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
        double zzc=0;
        for(Map<String, Object> m:list){
            double money=Double.parseDouble(m.get("balance").toString());
            zzc+=money;
        }
        zzcTextView.setText("总资产：￥"+FigureTools.sswrFigure(zzc)+"");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View arg0) {
        
    }
}