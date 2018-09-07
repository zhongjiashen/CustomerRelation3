package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cr.activity.gzpt.dwzl.GzptDwzlLxrDetailActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlLxrAdapter;
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
 * 联系人
 */

public class ContactView extends BaseView {

    private GzptDwzlLxrAdapter  lxrAdapter;
    List<Map<String, Object>>   lxrList  = new ArrayList<Map<String, Object>>();

    private TextView  xzlxrTextView;

    private int page=1;
    /**
     * 刷新(联系人)
     */
    private XListView.IXListViewListener xListViewListenerLxr;

    @SuppressWarnings("deprecation")
    private void onLoadLxr() {// 停止刷新和加载功能，并且显示时间
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(new Date().toLocaleString());
    }

    public ContactView(Activity activity) {
        super(activity);

        page=1;
    }

    @Override
    protected void initViews() {
        xListViewListenerLxr = new XListView.IXListViewListener() {
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
                        searchDateLxr(1,page);
                        onLoadLxr();
                    }
                }, 2000);
            }
        };
        view = View.inflate(activity, R.layout.activity_gzpt_dwzl_lxr, null);
        xListView = (XListView) view.findViewById(R.id.listview);
        lxrAdapter = new GzptDwzlLxrAdapter(lxrList, activity);
        xListView.setXListViewListener(xListViewListenerLxr);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(activity, GzptDwzlLxrDetailActivity.class);
                intent.putExtra("lxrid", lxrList.get(arg2 - 1).get("id").toString());
                intent.putExtra("clientid", activity.clientId);
                activity.startActivity(intent);
            }
        });
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setAdapter(lxrAdapter);
        xzlxrTextView = (TextView) view .findViewById(R.id.xzlxr_textview);
        xzlxrTextView.setOnClickListener(activity);
    }

    @Override
    public void initData() {
        lxrList.clear();
       searchDateLxr(1, 1);

        isFirst=true;
    }

    @Override
    public void setData(List<Map<String, Object>> list) {
        if(list!=null) {
            lxrList.addAll(list);
            lxrAdapter.setList(lxrList);
            onLoadLxr();
        }
    }

    /**
     * 连接网络的操作(联系人)
     */
    public void searchDateLxr(int type,int page) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(activity));
        // parmMap.put("opid", ShareUserInfo.getUserId(context));
        parmMap.put("clientid", activity.clientId);
        parmMap.put("lxrname", "");
        parmMap.put("curpage", page);
        parmMap.put("pagesize", pageSize);
       activity. findServiceData(type, ServerURL.LXRLIST, parmMap);
    }
}
