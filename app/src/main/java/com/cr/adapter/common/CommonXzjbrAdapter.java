package com.cr.adapter.common;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class CommonXzjbrAdapter extends BaseAdapter {
	List<Map<String, Object>> list;
	private Activity activity;
	public CommonXzjbrAdapter(List<Map<String, Object>> list,Activity activity) {
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
	public List<Map<String, Object>> getList() {
        return list;
    }
    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final Map<String, Object> sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_common_xzjbr_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
			viewHolder.zwTextView = (TextView) convertView.findViewById(R.id.zw_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.nameTextView.setText(sb.get("empname").toString());
//		viewHolder.zwTextView.setText(sb.get("depname").toString());
		viewHolder.zwTextView.setVisibility(View.GONE);
		return convertView;
	}

	static class ViewHolder {
		TextView nameTextView;
		TextView zwTextView;
	}
}