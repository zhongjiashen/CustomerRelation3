package com.baiiu.filter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;

import java.util.List;
import java.util.Map;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class MyMenuAdapter implements MenuAdapter {
    private final Context mContext;

    private String[] titles;
    private List<View> views;
    public MyMenuAdapter(Context context, String[] titles, List<View> views) {
        this.mContext = context;
        this.titles = titles;

        this. views=views;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {

        return views.get(position);
    }

//    private View createSingleListView() {
//        SingleListView<Map> singleListView = new SingleListView<Map>(mContext)
//                .adapter(new SimpleTextAdapter<Map>(null, mContext) {
//                    @Override
//                    public String provideText(Map string) {
//                        return string.get("name").toString();
//                    }
//
//                    @Override
//                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
//                        int dp = UIUtil.dp(mContext, 15);
//                        checkedTextView.setPadding(dp, dp, 0, dp);
//                    }
//                })
//                .onItemClick(new OnFilterItemClickListener<Map>() {
//                    @Override
//                    public void onItemClick(Map item) {
//
//
//                        onFilterDone( item);
//                    }
//                });
//
//
//        singleListView.setList(mLists[0], -1);
//
//        return singleListView;
//    }
//
//
//    private void onFilterDone(Map item) {
//        if (onFilterDoneListener != null) {
//            onFilterDoneListener.onFilterDone(0, item);
//        }
//    }

}
