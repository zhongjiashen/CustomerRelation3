package com.update.viewbar.refresh;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/26 0026 下午 4:11
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/26 0026         申中佳               V1.0
 */
public class PullableRecyclerView extends RecyclerView implements Pullable {
    private boolean pullDownEnable = true; //下拉刷新开关
    private boolean pullUpEnable = true; //上拉刷新开关


    public PullableRecyclerView(Context context) {
        super(context);
        setLayoutManager(new LinearLayoutManager(context));
    }

    public PullableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutManager(new LinearLayoutManager(context));
    }

    public PullableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public boolean canPullDown() {
        if (canScrollVertically(-1))
            return false;
        else return true;
    }

    @Override
    public boolean canPullUp() {
        if (canScrollVertically(1))
            return false;
        else return true;
    }

}