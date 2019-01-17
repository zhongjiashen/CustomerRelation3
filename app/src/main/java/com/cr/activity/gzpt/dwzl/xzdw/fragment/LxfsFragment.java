package com.cr.activity.gzpt.dwzl.xzdw.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.cr.activity.gzpt.dwzl.GzptDwzlDwBjdwLxfsAddActivity;
import com.cr.tools.ServerURL;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.update.base.BaseFragment;
import com.update.base.BaseP;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 联系方式
 */
public class LxfsFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView listview;


    @Override
    protected int getLayout() {
        return R.layout.fragment_lxfs;
    }

    @Override
    protected void init() {
        presenter = new BaseP(this, mActivity);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

    }

    @OnClick({R.id.add_imageview, R.id.del_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_imageview:
                startActivityForResult(new Intent(mActivity, GzptDwzlDwBjdwLxfsAddActivity.class)
                                .putExtra("dhhm", "")
                                .putExtra("clientid", "0")
                        , 0);

                break;
            case R.id.del_imageview:
                break;
        }
    }
}
