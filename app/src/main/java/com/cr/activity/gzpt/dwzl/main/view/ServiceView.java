package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cr.activity.khfw.KhfwDetailActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlFwAdapter;
import com.cr.common.XListView;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1363655717 on 2017-07-15.
 * 服务
 */

public class ServiceView extends BaseView {
    private TextView xzfwdTextView;
    private GzptDwzlFwAdapter fwAdapter;
    List<Map<String, Object>> fwList = new ArrayList<Map<String, Object>>();
    private int page = 1;

    public ServiceView(Activity activity) {
        super(activity);
    }

    /**
     * 刷新（服务）
     */
    private XListView.IXListViewListener xListViewListenerFw;

    @SuppressWarnings("deprecation")
    private void onLoadBf() {// 停止刷新和加载功能，并且显示时间
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }

    @Override
    protected void initViews() {
        xListViewListenerFw = new XListView.IXListViewListener() {
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
                        searchDateFw(4);
                        onLoadBf();
                    }
                }, 2000);
            }
        };
        view = View.inflate(activity, R.layout.activity_gzpt_dwzl_fw, null);
        xListView = (XListView) view.findViewById(R.id.listview);
        fwAdapter = new GzptDwzlFwAdapter(fwList, activity);
        xListView.setXListViewListener(xListViewListenerFw);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setAdapter(fwAdapter);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.putExtra("billid", fwList.get(arg2 - 1).get("id").toString());
                intent.setClass(activity, KhfwDetailActivity.class);
                activity.startActivity(intent);
            }
        });
        xzfwdTextView = (TextView) view.findViewById(R.id.xzfwd_textview);
        xzfwdTextView.setOnClickListener(activity);
    }

    @Override
    public void initData() {
        fwList.clear();
        page = 1;
        searchDateFw(4);

        isFirst = true;
    }

    @Override
    public void setData(List<Map<String, Object>> list) {
        if(list!=null) {
            fwList.addAll(list);
            fwAdapter.setList(fwList);
            onLoadBf();
        }
    }

    /**
     * 连接网络的操作(服务)
     */
    public void searchDateFw(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(activity));
        parmMap.put("clientid ", activity.clientId);
        parmMap.put("qsrq", "");
        parmMap.put("zzrq", "");
        parmMap.put("billcode", "");
        parmMap.put("billtype", "");
        parmMap.put("shzt", "");
        parmMap.put("filter", "");
        parmMap.put("curpage", page);
        parmMap.put("pagesize", pageSize);
        activity.findServiceData(type, ServerURL.SHWXINFO, parmMap);
    }
}
