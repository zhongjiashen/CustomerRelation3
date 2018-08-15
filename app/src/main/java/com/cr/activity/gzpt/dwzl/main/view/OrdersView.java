package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cr.activity.jxc.xsgl.xsdd.JxcXsglXsddDetailActivity;
import com.cr.activity.jxc.xsgl.xsdd.KtJxcXsglXsddDetailActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlDdAdapter;
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
 * 订单
 */

public class OrdersView extends BaseView{

    List<Map<String, Object>> ddList = new ArrayList<Map<String, Object>>();
    private GzptDwzlDdAdapter ddAdapter;
    private TextView xzxsddTextView ;
    private int page = 1;
    public OrdersView(Activity activity) {
        super(activity);
    }
    /**
     * 刷新（拜访）
     */
    private XListView.IXListViewListener xListViewListenerDd ;

    @SuppressWarnings("deprecation")
    private void onLoadBf() {// 停止刷新和加载功能，并且显示时间
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }
    @Override
    protected void initViews() {
        xListViewListenerDd = new XListView.IXListViewListener() {
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
                        searchDateDd(5);
                        onLoadBf();
                    }
                }, 2000);
            }
        };
        view=  View.inflate(activity, R.layout.activity_gzpt_dwzl_dd, null);
        xListView = (XListView) view.findViewById(R.id.listview);
        ddAdapter = new GzptDwzlDdAdapter(ddList, activity);
        xListView.setXListViewListener(xListViewListenerDd);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setAdapter(ddAdapter);
        xzxsddTextView = (TextView) view.findViewById(R.id.xzxsdd_textview);
        xzxsddTextView.setOnClickListener(activity);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.putExtra("billid", ddList.get(arg2 - 1).get("billid").toString());
                intent.setClass(activity, KtJxcXsglXsddDetailActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        ddList.clear();
        page=1;
        searchDateDd(5);

        isFirst=true;
    }

    @Override
    public  void setData(List<Map<String, Object>> list) {
        ddList.addAll(list);
        ddAdapter.setList(ddList);
        onLoadBf();
    }
    /**
     * 连接网络的操作(订单)
     */
    public void searchDateDd(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(activity));
        parmMap.put("opid", ShareUserInfo.getUserId(activity));
        parmMap.put("tabname", "tb_sorder");
        parmMap.put("qsrq", "1901-01-01");
        parmMap.put("zzrq", "3000-01-01");
        parmMap.put("billcode", "");
        parmMap.put("cname", "");
        parmMap.put("clientid ", activity.clientId);
        parmMap.put("curpage",page);
        parmMap.put("pagesize", pageSize);
        activity.findServiceData(type, ServerURL.BILLLIST, parmMap);
    }
}
