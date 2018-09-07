package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cr.activity.gzpt.dwzl.GzptDwzlBfBflrDetailActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlBfAdapter;
import com.cr.common.XListView;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1363655717 on 2017-07-15.
 * 拜访
 */

public class VisitView extends BaseView {
    List<Map<String, Object>> bfList = new ArrayList<Map<String, Object>>();
    private GzptDwzlBfAdapter bfAdapter;
    private TextView bflrTextView;

    private int page = 1;
    /**
     * 刷新（拜访）
     */
    private XListView.IXListViewListener xListViewListenerBf ;

    @SuppressWarnings("deprecation")
    private void onLoadBf() {// 停止刷新和加载功能，并且显示时间
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }
    public VisitView(Activity activity) {
        super(activity);

    }

    @Override
    protected void initViews() {
        xListViewListenerBf = new XListView.IXListViewListener() {
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
                activity.handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
                    @Override
                    public void run() {
                        page++;
                        searchDateBf(2);
                        onLoadBf();
                    }
                }, 2000);
            }
        };
        view = View.inflate(activity, R.layout.activity_gzpt_dwzl_bf, null);
        xListView = (XListView) view.findViewById(R.id.listview);
        bfAdapter = new GzptDwzlBfAdapter(bfList, activity);
        xListView.setXListViewListener(xListViewListenerBf);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setAdapter(bfAdapter);
        bflrTextView = (TextView) view.findViewById(R.id.bflr_textview);
        bflrTextView.setOnClickListener(activity);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.setClass(activity, GzptDwzlBfBflrDetailActivity.class);
                intent.putExtra("clientname", activity.khmc);
                intent.putExtra("clientid", activity.clientId);
                intent.putExtra("object", (Serializable) bfList.get(arg2 - 1));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        bfList.clear();
        page=1;
       searchDateBf(2);

        isFirst=true;
    }

    @Override
    public void setData(List<Map<String, Object>> list) {
        if(list!=null) {
            bfList.addAll(list);
            bfAdapter.setList(bfList);
            onLoadBf();
        }
    }

    /**
     * 连接网络的操作(拜访)
     */
    public void searchDateBf(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(activity));
        parmMap.put("opid", ShareUserInfo.getUserId(activity));
        parmMap.put("clientid ", activity.clientId);
        parmMap.put("curpage", page);
        parmMap.put("pagesize", pageSize);
        activity.findServiceData(type, ServerURL.VISITINFO, parmMap);
    }
}
