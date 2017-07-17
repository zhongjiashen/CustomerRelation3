package com.cr.activity.gzpt.dwzl.main;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.cr.activity.gzpt.dwzl.main.view.BaseView;

import java.util.ArrayList;
import java.util.List;

public class FjzxPageAdapter extends PagerAdapter{
    public String[] mTitles ;
	private List<BaseView> mViewList=new ArrayList<>();

    public void setmViewList(List<BaseView> mViewList) {
        this.mViewList = mViewList;
    }

    //  @Override
public int getCount() {
    return mViewList.size();
}

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseView pager = mViewList.get(position);
        container.addView(pager.view);
        // pager.initData();// 初始化数据.... 不要放在此处初始化数据, 否则会预加载下一个页面
        return pager.view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
