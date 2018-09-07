package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cr.activity.xm.BjxmActivity;
import com.cr.adapter.gzpt.dwzl.GzptDwzlXmAdapter;
import com.cr.common.XListView;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.project.ProjectActivity;
import com.update.actiity.project.ProjectManagementActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1363655717 on 2017-07-15.
 * 项目
 */

public class ProjectView extends BaseView {

    List<Map<String, Object>> xmList = new ArrayList<Map<String, Object>>();
    private GzptDwzlXmAdapter xmAdapter;
    private TextView xzxmTextView;
    private int page = 1;

    public ProjectView(Activity activity) {
        super(activity);
    }

    /**
     * 刷新(项目)
     */
    private XListView.IXListViewListener xListViewListenerXm;

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

                activity.handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
                    @Override
                    public void run() {
                        page++;
                        searchDateXm(6);
                        onLoadJh();

                    }
                }, 2000);
            }
        };
        view = View.inflate(activity, R.layout.activity_gzpt_dwzl_xm, null);
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
                activity.startActivityForResult(new Intent(activity, ProjectActivity.class)
                        .putExtra("billid", xmList.get(arg2 - 1).get("billid").toString()), 6);
//                Intent intent = new Intent();
//                intent.putExtra("xmid", xmList.get(arg2 - 1).get("billid").toString());
//                intent.setClass(activity, BjxmActivity.class);
//                activity.startActivityForResult(intent, 6);
            }
        });
    }

    @Override
    public void initData() {
        xmList.clear();
        page = 1;
        searchDateXm(6);
        isFirst = true;
    }

    @Override
    public void setData(List<Map<String, Object>> list) {
        if(list!=null) {
            xmList.addAll(list);
            xmAdapter.setList(xmList);
        }

    }

    /**
     * 连接网络的操作(項目)
     */
    public void searchDateXm(int type) {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(activity));
        parmMap.put("tabname", "tb_project");
        parmMap.put("opid", ShareUserInfo.getUserId(activity));
        parmMap.put("clientid", activity.clientId);
        parmMap.put("qsrq", "1901-01-01");
        parmMap.put("zzrq", "3000-01-01");
        parmMap.put("cname", "");
        parmMap.put("title", "");
        parmMap.put("curpage", page);
        parmMap.put("pagesize", pageSize);
        activity.findServiceData(type, ServerURL.BILLLIST, parmMap);
    }
}
