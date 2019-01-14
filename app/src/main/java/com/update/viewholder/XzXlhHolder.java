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

public class XzXlhHolder {




    public static ViewHolder getViewHolder(Context context, ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_xzxlh, parent, false));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_view)
        public CheckBox cbView;
        @BindView(R.id.tv_serial_number)
        public TextView tvSerialNumber;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
