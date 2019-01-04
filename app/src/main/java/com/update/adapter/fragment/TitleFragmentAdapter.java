package com.update.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TitleFragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] mFragments;
    public String[] mTitles ;
    public TitleFragmentAdapter(FragmentManager fm, Fragment[] mFragments, String[] titles) {
        super(fm);
        this.mFragments = mFragments;
        mTitles=titles;
    }

    public void setBaseFragments(Fragment[] baseFragments) {
        mFragments = baseFragments;
    }


    @Override
    public Fragment getItem(int i) {
        return mFragments[i];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}