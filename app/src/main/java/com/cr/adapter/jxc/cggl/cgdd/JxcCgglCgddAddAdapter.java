package com.cr.adapter.jxc.cggl.cgdd;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cr.tools.FigureTools;
import com.crcxj.activity.R;

public class JxcCgglCgddAddAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    private Activity          activity;

    public JxcCgglCgddAddAdapter(List<Map<String, Object>> list, Activity activity) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        Map<String, Object> objMap = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(
                R.layout.activity_jxc_cggl_cgdd_detail_item, null);// 这个过程相当耗时间
            viewHolder = new ViewHolder();
            viewHolder.bhmcggTextView = (TextView) convertView.findViewById(R.id.bhmcgg);
            viewHolder.djTextView = (TextView) convertView.findViewById(R.id.dj_textview);
            viewHolder.zjTextView = (TextView) convertView.findViewById(R.id.zj_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bhmcggTextView.setText((objMap.get("goodscode") == null ? objMap.get("code")
            .toString() : objMap.get("goodscode").toString())
                                          + " "
                                          + (objMap.get("goodsname") == null ? objMap.get("name")
                                              .toString() : objMap.get("goodsname").toString())
                                          + " "
                                          + (objMap.get("specs") == null ? objMap.get("specs")
                                              .toString() : objMap.get("specs").toString())
                                          + " "
                                          + (objMap.get("model") == null ? objMap.get("model")
                                              .toString() : objMap.get("model").toString()));
        viewHolder.djTextView.setText("￥"
                                      + FigureTools.sswrFigure(objMap.get("taxunitprice").toString())
                                      + "*"
                                      + (objMap.get("sl") == null ? objMap.get("unitqty")
                                          .toString() : objMap.get("sl").toString())
                                      + ""
                                      + (objMap.get("unitname") == null ? objMap.get("unitname")
                                          .toString() : objMap.get("unitname").toString()));
        viewHolder.zjTextView.setText("￥"
                +FigureTools.sswrFigure( (objMap.get("amount") == null ? objMap.get("amount")
                                          .toString() : objMap.get("amount").toString())));
        return convertView;
    }

    static class ViewHolder {
        TextView bhmcggTextView;
        TextView djTextView;
        TextView zjTextView;
    }
}