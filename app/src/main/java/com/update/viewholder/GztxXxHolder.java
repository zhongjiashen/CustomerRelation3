package com.update.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crcxj.activity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GztxXxHolder {


    public static ViewHolder getViewHolder(Context context, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_gttx_xx, parent, false));
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_a)
        public  TextView tvA;
        @BindView(R.id.tv_b)
        public  TextView tvB;
        @BindView(R.id.tv_c)
        public TextView tvC;
        @BindView(R.id.tv_d)
        public TextView tvD;
        @BindView(R.id.tv_e)
        public TextView tvE;
        @BindView(R.id.tv_f)
        public TextView tvF;
        @BindView(R.id.tv_g)
        public TextView tvG;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
