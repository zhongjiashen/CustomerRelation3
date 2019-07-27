package com.update.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.crcxj.activity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JxcXzphHolder {




    public static ViewHolder getViewHolder(Context context, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_jxc_xzph, parent, false));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ph)
        public TextView tvPh;
        @BindView(R.id.tv_scrq)
        public TextView tvScrq;
        @BindView(R.id.tv_yxrq)
        public TextView tvYxrq;
        @BindView(R.id.tv_kysl)
        public TextView tvKysl;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
