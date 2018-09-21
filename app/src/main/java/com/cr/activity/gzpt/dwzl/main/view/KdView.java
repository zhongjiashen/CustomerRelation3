package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshAddActivity;
import com.cr.activity.jxc.cggl.cgsh.JxcCgglCgshDetailActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdAddActivity;
import com.cr.activity.jxc.xsgl.xskd.JxcXsglXskdDetailActivity;
import com.cr.activity.khfw.KhfwDetailActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlFwAdapter;
import com.cr.adapter.gzpt.dwzl.GzptDwzlKdAdapter;
import com.cr.adapter.jxc.cggl.cgsh.JxcCgglCgshAdapter;
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
 * 开单
 */
public class KdView extends BaseView {
    private TextView xzkdTextView;
    private GzptDwzlKdAdapter kdAdapter;
    List<Map<String, Object>> kdList = new ArrayList<Map<String, Object>>();
    private int type;
    private int page = 1;

    public KdView(Activity activity, int type) {
        super(activity);
        this.type = type;
    }

    /**
     * 刷新（服务）
     */
    private XListView.IXListViewListener xListViewListenerKd;

    @SuppressWarnings("deprecation")
    private void onLoadBf() {// 停止刷新和加载功能，并且显示时间
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }

    @Override
    protected void initViews() {
        xListViewListenerKd = new XListView.IXListViewListener() {
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
                        searchDateKd(7);

                        onLoadBf();
                    }
                }, 2000);
            }
        };
        view = View.inflate(activity, R.layout.activity_gzpt_dwzl_kd, null);
        xListView = (XListView) view.findViewById(R.id.listview);
        kdAdapter = new GzptDwzlKdAdapter(kdList, activity);
        xListView.setXListViewListener(xListViewListenerKd);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setAdapter(kdAdapter);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();

                switch (type) {
                    case 0://销售开单列表
                        intent.setClass(activity, JxcXsglXskdDetailActivity.class);
                        break;
                    case 1://
                        intent.setClass(activity, JxcCgglCgshDetailActivity.class);
                        break;
                }


                intent.putExtra("billid", kdList.get(arg2 - 1).get("billid").toString());

                activity.startActivity(intent);

//                Intent intent = new Intent();
//                intent.putExtra("billid", kdList.get(arg2 - 1).get("billid").toString());
//                intent.setClass(activity, KhfwDetailActivity.class);
//                activity.startActivity(intent);
            }
        });
        xzkdTextView = (TextView) view.findViewById(R.id.xzkd_textview);
        xzkdTextView.setOnClickListener(activity);
        xzkdTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (type) {
                    case 0://销售开单列表
                        intent.setClass(activity, JxcXsglXskdAddActivity.class);
                        break;
                    case 1://
                        intent.setClass(activity, JxcCgglCgshAddActivity.class);
                        break;
                }

                activity.startActivityForResult(intent, 7);
            }
        });
    }

    @Override
    public void initData() {
        kdList.clear();
        page = 1;
        searchDateKd(7);

        isFirst = true;
    }

    @Override
    public void setData(List<Map<String, Object>> list) {
        if (list != null) {
            kdList.addAll(list);
            kdAdapter.setList(kdList);
            onLoadBf();
        }
    }

    /**
     * 连接网络的操作
     */
    private void searchDateKd(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(activity));
//        parmMap.put("clientid ", activity.clientId);
        switch (this.type) {
            case 0://销售开单列表
                parmMap.put("tabname", "tb_invoice");
                break;
            case 1://采购收货列表
                parmMap.put("tabname", "tb_received");
                break;
        }
        parmMap.put("clientid", activity.clientId);
        parmMap.put("qsrq", "1901-01-01");
        parmMap.put("zzrq", "3000-01-01");
        parmMap.put("billcode", "");
        parmMap.put("shzt", "3");
        parmMap.put("cname", "");
        parmMap.put("opid", ShareUserInfo.getUserId(activity));
        parmMap.put("curpage", page);
        parmMap.put("pagesize", pageSize);
        activity.findServiceData2(type, ServerURL.BILLLIST, parmMap, false);
    }

}