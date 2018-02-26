package com.update.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crcxj.activity.R;
import com.update.viewholder.ViewHolderFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/26 0026 上午 9:59
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/26 0026         申中佳               V1.0
 */
public abstract class BaseRecycleAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    public static int NULL_DATA = 0;


    private List mList;
    private T mViewHolder;

    public BaseRecycleAdapter( List list) {
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                viewHolder = new NullDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_has_no_data, parent, false));
                break;
            case 1:
                viewHolder = MyonCreateViewHolder();
                break;
        }
        return viewHolder;
    }
    protected abstract RecyclerView.ViewHolder MyonCreateViewHolder();
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1)
            MyonBindViewHolder((T) holder, position);

    }

    protected abstract void MyonBindViewHolder(T holder, int position);

    @Override
    public int getItemCount() {
        if (mList == null||mList.size() == 0)
            return 1;
        else
            return mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (mList == null || mList.size() == 0)
            return NULL_DATA;
        else
            return 1;
    }

    static class NullDataViewHolder extends RecyclerView.ViewHolder {

        NullDataViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
