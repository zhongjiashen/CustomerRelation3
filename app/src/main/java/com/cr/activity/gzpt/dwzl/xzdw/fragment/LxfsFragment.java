package com.cr.activity.gzpt.dwzl.xzdw.fragment;

import android.view.View;
import android.widget.ListView;

import com.crcxj.activity.R;
import com.update.base.BaseFragment;

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

    }

    @OnClick({R.id.add_imageview, R.id.del_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_imageview:
                break;
            case R.id.del_imageview:
                break;
        }
    }
}
