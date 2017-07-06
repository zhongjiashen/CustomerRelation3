package com.cr.adapter.tjfx.ysyf;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class TjfxYsyfAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    private Activity          activity;
    private int               selectIndex;

    public TjfxYsyfAdapter(List<Map<String, Object>> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        Map<String, Object> objMap = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.activity_tjfx_ysyf_item,
                null);// 这个过程相当耗时间
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
            viewHolder.ysTextView = (TextView) convertView.findViewById(R.id.ys_textview);
            viewHolder.ys2TextView = (TextView) convertView.findViewById(R.id.ys2_textview);
            viewHolder.qkTextView = (TextView) convertView.findViewById(R.id.qk_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTextView.setText(objMap.get("cname").toString());
        if (objMap.get("types").toString().equals("1")
            || objMap.get("types").toString().equals("4")) {
            viewHolder.ysTextView.setText("应收：" + objMap.get("balance").toString());
            viewHolder.ys2TextView.setText("预收：" + objMap.get("suramt").toString());
        } else {
            viewHolder.ysTextView.setText("应付：" + objMap.get("balance").toString());
            viewHolder.ys2TextView.setText("预付：" + objMap.get("suramt").toString());
        }
        viewHolder.qkTextView.setText("欠款：" + objMap.get("oweamt").toString());
        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView ysTextView;
        TextView ys2TextView;
        TextView qkTextView;
    }
}