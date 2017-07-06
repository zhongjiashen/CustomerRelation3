package com.cr.adapter.gzpt.dwzl;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class GzptDwzlLxrAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public GzptDwzlLxrAdapter(List<Map<String, Object>> list,Activity activity) {
		this.list=list;
		this.activity=activity;
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
		Map<String, Object> objMap=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_dwzl_lxr_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.nameTextView.setText(objMap.get("lxrname").toString());
		if(objMap.get("isused")!=null){
            if(objMap.get("isused").toString().equals("0")){
                viewHolder.nameTextView.setTextColor(Color.parseColor("#ff0000"));
            }else{
                viewHolder.nameTextView.setTextColor(Color.parseColor("#000000"));
            }
        }
		return convertView;
	}

	static class ViewHolder {
		TextView nameTextView;
	}
}