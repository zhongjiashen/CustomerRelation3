package com.cr.adapter.gzpt.dwzl;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

import java.util.List;
import java.util.Map;

/**
 * 项目的适配器
 *
 * @author Administrator
 */
public class GzptDwzlXmAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    private Activity activity;
    private int selectIndex;

    public GzptDwzlXmAdapter(List<Map<String, Object>> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_dwzl_xm_item, null);// 这个过程相当耗时间
            viewHolder = new ViewHolder();
            viewHolder.xmbhTextView = (TextView) convertView.findViewById(R.id.xmbh_textview);
            viewHolder.xmmcTextView = (TextView) convertView.findViewById(R.id.xmmc_textview);
            viewHolder.dwmcTextView = (TextView) convertView.findViewById(R.id.dwmc_textview);
            viewHolder.dwlxTextView = (TextView) convertView.findViewById(R.id.dwlx_textview);
            viewHolder.rqTextView = (TextView) convertView.findViewById(R.id.rq_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.xmbhTextView.setText("项目编号：" + objMap.get("code").toString());
        viewHolder.xmmcTextView.setText("项目名称：" + objMap.get("title").toString());
        viewHolder.dwmcTextView.setText("单位名称：" + objMap.get("cname").toString());
        viewHolder.dwlxTextView.setText("单位类型：" + objMap.get("typesname").toString());
        viewHolder.rqTextView.setText(objMap.get("billdate").toString());
        return convertView;
    }

    static class ViewHolder {
        TextView xmmcTextView;
        TextView xmbhTextView;
        TextView dwmcTextView;
        TextView dwlxTextView;
        TextView rqTextView;
    }
}