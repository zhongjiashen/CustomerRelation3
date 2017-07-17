package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cr.activity.gzpt.dwzl.GzptDwzlJhXzxsjhActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlJhAdapter;
import com.cr.common.XListView;
import com.crcxj.activity.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 1363655717 on 2017-07-15.
 * 机会
 */

public class OpportunityView extends BaseView{
    List<Map<String, Object>> jhList = new ArrayList<Map<String, Object>>();
    private GzptDwzlJhAdapter   jhAdapter;
    private String khmc;
    private TextView xzxsjhTextView;

    /**
     * 刷新(机会)
     */
    private XListView.IXListViewListener xListViewListenerJh ;

    @SuppressWarnings("deprecation")
    private void onLoadJh() {// 停止刷新和加载功能，并且显示时间
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }

    public OpportunityView(Activity activity, String clientId, String khmc) {
        super(activity);
        this.clientId = clientId;
        this.khmc = khmc;
    }

    @Override
    protected void initViews() {
        xListViewListenerJh = new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                activity.handler.postDelayed(new Runnable() {// 实现延迟2秒加载刷新
                    @Override
                    public void run() {
                        // 不实现顶部刷新
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                activity. handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
                    @Override
                    public void run() {
                        activity.currentPage++;
                        activity.searchDateJh(3) ;
                        onLoadJh();
                    }
                }, 2000);
            }
        };
        view=  View.inflate(activity, R.layout.activity_gzpt_dwzl_jh, null);
        xListView = (XListView) view.findViewById(R.id.listview);
        jhAdapter = new GzptDwzlJhAdapter(jhList, activity);
        xListView.setXListViewListener(xListViewListenerJh);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.setClass(activity, GzptDwzlJhXzxsjhActivity.class);
                intent.putExtra("clientid", clientId);
                intent.putExtra("clientname", khmc);
                intent.putExtra("code", jhList.get(arg2 - 1).get("code").toString());
                intent.putExtra("chanceid", jhList.get(arg2 - 1).get("chanceid").toString());
                activity.startActivityForResult(intent, 3);

            }
        });
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setAdapter(jhAdapter);
        xzxsjhTextView = (TextView) view.findViewById(R.id.xzxsjh_textview);
        xzxsjhTextView.setOnClickListener(activity);
    }

    @Override
    public void initData() {
        jhList.clear();
        activity.searchDateJh(3) ;

        isFirst=true;
    }

    @Override
    public  void setData(List<Map<String, Object>> list) {
        jhList.addAll(list);
        jhAdapter.setList(jhList);
        onLoadJh();
    }
}
