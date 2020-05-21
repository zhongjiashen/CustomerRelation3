package com.cr.activity.khgl.mstx.gztx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cr.activity.BaseActivity;
import com.cr.adapter.MstxGztxAdapter;
import com.cr.model.IndexModel;
import com.cr.tools.PaseJson;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.actiity.audit.MyAuditListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MstxGztxActivity extends BaseActivity {
    private BaseAdapter adapter;
    private ListView gztxListView;
    List<Map<String, Object>> gztxList = new ArrayList<Map<String, Object>>();
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mstx_gztx);
        addFHMethod();
        initActivity();
        initListView();
        searchDate();

    }

    /**
     * 初始化Activity
     */
    private void initActivity() {
        gztxListView = (ListView) findViewById(R.id.gztx_listview);
//		gztxListView.setPullRefreshEnable(false);
//		gztxListView.setPullLoadEnable(true);
//		gztxListView.setXListViewListener(xListViewListener);
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        gztxListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent;
                if (gztxList.get(arg2).get("typecode").toString().equals("MYSH")) {//我的审核列表
                    intent = new Intent(mContext, MyAuditListActivity.class);
                    startActivity(intent);
                } else {
                    mIntent = new Intent(mContext, KtGztxXxActivity.class);
                    mIntent.putExtra("typecode", gztxList.get(arg2).get("typecode").toString());
                    mIntent.putExtra("title", gztxList.get(arg2).get("typename").toString());
                    switch (gztxList.get(arg2).get("typecode").toString()) {
                        case "HTTX"://合同提醒
                            CheckOperPriv("3014");
                            break;
                        default:
                            startActivity(mIntent);
                            break;
                    }

                }


            }
        });
    }

    /**
     * 连接网络的操作
     */
    private void searchDate() {
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("dbname", ShareUserInfo.getDbName(mContext));
        parmMap.put("opid", ShareUserInfo.getUserId(mContext));
        findServiceData2(0, ServerURL.BROADCASTPROMPT, parmMap, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void executeSuccess() {
//		gztxList.clear();
        if (returnJson.equals("")) {
            showToastPromopt(2);
        } else {
            gztxList.addAll((List<Map<String, Object>>) PaseJson.paseJsonToObject(returnJson));
            adapter = new MstxGztxAdapter(gztxList, this);
            gztxListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void UserPermissionsCallBack() {
        if (!ShareUserInfo.getKey(this, "ll").equals("1")) {
            showToastPromopt("你没有该权限，请向管理员申请权限！");
            return;
        }
        startActivity(mIntent);
    }

    /**
     * 刷新
     */
//	private IXListViewListener xListViewListener = new IXListViewListener() {
//		@Override
//		public void onRefresh() {
//			handler.postDelayed(new Runnable() {//实现延迟2秒加载刷新
//				@Override
//				public void run() {
//					//不实现顶部刷新
//				}
//			},2000);
//		}
//		@Override
//		public void onLoadMore() {
//			handler.postDelayed(new Runnable() {// 实现底部延迟2秒加载更多的功能
//				@Override
//				public void run() {
//					currentPage++;
//					searchDate();
//					onLoad();
//				}
//			}, 2000);
//		}
//	};
//	@SuppressWarnings("deprecation")
//	private void onLoad() {//停止刷新和加载功能，并且显示时间
//		gztxListView.stopRefresh();
//		gztxListView.stopLoadMore();
//		gztxListView.setRefreshTime(new Date().toLocaleString());
//	}

}
