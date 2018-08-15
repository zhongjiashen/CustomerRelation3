package com.cr.activity.gzpt.dwzl.main.view;

import android.app.Activity;
import android.view.View;

import com.cr.activity.gzpt.dwzl.GzptDwzlActivity;
import com.cr.common.XListView;

import java.util.List;
import java.util.Map;

/**
 * Created by 1363655717 on 2017-07-15.
 */

public abstract class BaseView {

    protected XListView xListView;
    public boolean isFirst;

    protected GzptDwzlActivity activity;
    public View view;

    public int pageSize = 10;// 每页的条数
    public BaseView(Activity activity) {
        this.activity = (GzptDwzlActivity) activity;
        initViews();
    }

    /**
     * 初始化布局
     */
    protected abstract  void initViews() ;
    /**
     * 加载数据
     */
    public abstract  void initData() ;
    /**
     * 返回数据
     */
   public abstract  void setData(List<Map<String, Object>>list) ;
}
