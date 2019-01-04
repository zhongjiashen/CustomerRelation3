package com.cr.activity.gzpt.dwzl.xzdw;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cr.activity.gzpt.dwzl.xzdw.fragment.DwxxFragment;
import com.cr.activity.gzpt.dwzl.xzdw.fragment.LxfsFragment;
import com.crcxj.activity.R;
import com.update.adapter.fragment.TitleFragmentAdapter;
import com.update.base.BaseActivity;
import com.update.base.BaseP;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增单位（来电录入新增单位）
 *
 * @author Administrator
 */
public class XzDwActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private DwxxFragment dwxxFragment;
    private LxfsFragment lxfsFragment;

    @Override
    protected void initVariables() {
        presenter = new BaseP(this, this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_xzdw;
    }

    @Override
    protected void init() {
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(new TitleFragmentAdapter(getSupportFragmentManager(), new Fragment[]{dwxxFragment = new DwxxFragment(), lxfsFragment = new LxfsFragment()}, new String[]{"单位信息", "联系方式"}));

    }


    @OnClick({R.id.fh, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fh:
                finish();
                break;
            case R.id.save:
                Map<String, Object> dwxxMap= dwxxFragment.getData();
                if(dwxxMap.isEmpty()){
                    return;
                }
                presenter.post(0, "billsave", dwxxMap);
                break;
        }
    }
}
