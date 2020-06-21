package com.cr.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

import com.cr.activity.gzpt.dwzl.GzptDwzlActivity;
import com.cr.adapter.GzptHjzxHjzxAdapter;
import com.cr.common.XListView;
import com.cr.common.XListView.IXListViewListener;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.crcxj.activity.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressLint("SimpleDateFormat")
public class GzptKhglResultActivity extends BaseActivity implements OnClickListener {
    private GzptHjzxHjzxAdapter adapter;
    private XListView xListView;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private ImageButton fhButton;

    //	private int selectIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gzpt_khgl_result);
//		addFHMethod();
        addZYMethod();
        initActivity();
        initListView();
        searchDate();
//		searchXmjdDate();
    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        fhButton = (ImageButton) findViewById(R.id.fh);
        fhButton.setOnClickListener(this);
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        xListView = (XListView) findViewById(R.id.xlistview);
        adapter = new GzptHjzxHjzxAdapter(list, this);
        xListView.setAdapter(adapter);
        xListView.setXListViewListener(xListViewListener);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(context, GzptDwzlActivity.class);
                intent.putExtra("object", (Serializable) list.get(arg2 - 1));
                startActivityForResult(intent, 1);
                adapter.setSelectIndex(arg2);
            }
        });
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        @SuppressWarnings("unchecked")
        Map<String, Object> parmMap = (Map<String, Object>) this.getIntent().getExtras().getSerializable("object");
        parmMap.put("curpage", currentPage);
        findServiceData(0, ServerURL.JHRWZDY, parmMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
        if (returnJson.equals("")) {
            showToastPromopt(2);
        } else {
            list.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.fh:
                finish();
                break;
//		case R.id.xzlxr:
//			GSLXRDetail detail=new GSLXRDetail();
////			if(dataList.size()==0){
////				detail.setClientid("0");
////			}else{
////				detail.setClientid(dataList.get(selectIndex).getClientid());
////			}
//			Intent intent=new Intent(GzptKhglResultActivity.this,GzptKhzlLxrActivity.class);
//			intent.putExtra("object3",detail);
//			intent.putExtra("type","khgl");
//			startActivity(intent);
//			break;
            default:
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
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }
}