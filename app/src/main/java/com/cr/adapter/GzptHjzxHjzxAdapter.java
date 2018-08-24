package com.cr.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class GzptHjzxHjzxAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    private Activity activity;
    private int selectIndex;

    public GzptHjzxHjzxAdapter(List<Map<String, Object>> list, Activity activity) {
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_hjzx_hjzx_item, null);// 这个过程相当耗时间
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
            viewHolder.typeTextView = (TextView) convertView.findViewById(R.id.type_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTextView.setText(objMap.get("cname").toString());
        String type = objMap.get("types").toString();
        if (type.equals("1")) {
            viewHolder.typeTextView.setText("客户");
        } else if (type.equals("2")) {
            viewHolder.typeTextView.setText("供应商");
        } else if (type.equals("3")) {
            viewHolder.typeTextView.setText("竞争对手");
        } else if (type.equals("4")) {
            viewHolder.typeTextView.setText("渠道");
        } else if (type.equals("5")) {
            viewHolder.typeTextView.setText("员工");
        } else if (type.equals("6")) {
            viewHolder.typeTextView.setText("物流");
        }
        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView typeTextView;
    }
}