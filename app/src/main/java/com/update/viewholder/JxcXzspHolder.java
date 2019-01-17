package com.update.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.crcxj.activity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JxcXzspHolder {



    public static ViewHolder getViewHolder(Context context, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_jxc_xzsp, parent, false));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mc)
        public TextView tvMc;
        @BindView(R.id.tv_bh)
        public TextView tvBh;
        @BindView(R.id.tv_gg)
        public TextView tvGg;
        @BindView(R.id.tv_xh)
        public TextView tvXh;
        @BindView(R.id.tv_kc)
        public TextView tvKc;
        @BindView(R.id.cb_view)
        public CheckBox cbView;
        @BindView(R.id.tv_xlh)
        public TextView tvXlh;
        @BindView(R.id.tv_sl)
        public TextView tvSl;
        @BindView(R.id.sl_view)
        public SLView2 slView;
        @BindView(R.id.et_bz)
        public EditText etBz;
        @BindView(R.id.ll_bz)
        public LinearLayout llBz;
        @BindView(R.id.et_cpph)
        public EditText etCpph;
        @BindView(R.id.ll_cpph)
        public LinearLayout llCpph;
        @BindView(R.id.et_scrq)
        public EditText etScrq;
        @BindView(R.id.ll_scrq)
        public LinearLayout llScrq;
        @BindView(R.id.et_yxqz)
        public EditText etYxqz;
        @BindView(R.id.ll_yxqz)
        public LinearLayout llYxqz;
        @BindView(R.id.et_cbj)
        public EditText etCbj;
        @BindView(R.id.ll_cbj)
        public LinearLayout llCbj;
        @BindView(R.id.et_dj)
        public EditText etDj;
        @BindView(R.id.iv_xzjg)
        public ImageView ivXzjg;
        @BindView(R.id.et_sl)
        public EditText etSl;
        @BindView(R.id.tv_hsdj)
        public TextView tvHsdj;
        @BindView(R.id.ll_bottom)
        public LinearLayout llBottom;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
