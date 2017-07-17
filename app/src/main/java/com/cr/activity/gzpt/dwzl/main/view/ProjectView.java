package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cr.activity.xm.BjxmActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlXmAdapter;
import com.cr.common.XListView;
import com.crcxj.activity.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 1363655717 on 2017-07-15.
 * 项目
 */

public class ProjectView extends BaseView{

    List<Map<String, Object>> xmList = new ArrayList<Map<String, Object>>();
    private GzptDwzlXmAdapter xmAdapter;
    private TextView  xzxmTextView ;
    public ProjectView(Activity activity) {
        super(activity);
    }
    /**
     * 刷新(项目)
     */
    private XListView.IXListViewListener xListViewListenerXm ;

    @SuppressWarnings("deprecation")
    private void onLoadJh() {// 停止刷新和加载功能，并且显示时间
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }
    @Override
    protected void initViews() {
        xListViewListenerXm = new XListView.IXListViewListener() {
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
                        activity.searchDateXm(6) ;
                        onLoadJh();

                    }
                }, 2000);
            }
        };
        view=  View.inflate(activity, R.layout.activity_gzpt_dwzl_xm, null);
        xListView = (XListView) view.findViewById(R.id.listviewxm);
        xmAdapter = new GzptDwzlXmAdapter(xmList, activity);
        xListView.setXListViewListener(xListViewListenerXm);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setAdapter(xmAdapter);
        xzxmTextView = (TextView) view.findViewById(R.id.xzxm_textview);
        xzxmTextView.setOnClickListener(activity);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.putExtra("xmid", xmList.get(arg2 - 1).get("projectid").toString());
                intent.setClass(activity, BjxmActivity.class);
                activity.startActivityForResult(intent, 6);
            }
        });
    }

    @Override
    public void initData() {
        xmList.clear();
        activity.searchDateXm(6) ;
        isFirst=true;
    }

    @Override
    public void setData(List<Map<String, Object>> list) {
        xmList.addAll(list);
        xmAdapter.setList(xmList);


    }
}
