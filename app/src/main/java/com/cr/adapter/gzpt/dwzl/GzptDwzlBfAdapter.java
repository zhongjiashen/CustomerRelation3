package com.cr.adapter.gzpt.dwzl;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class GzptDwzlBfAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public GzptDwzlBfAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_dwzl_bf_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bfnrTextView = (TextView) convertView.findViewById(R.id.zj_bfnr);
			viewHolder.xmmcTextView = (TextView) convertView.findViewById(R.id.zj_xmmc);
			viewHolder.xmjdTextView = (TextView) convertView.findViewById(R.id.zj_xmjd);
			viewHolder.bfrqTextView = (TextView) convertView.findViewById(R.id.zj_bfrq);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bfnrTextView.setText(objMap.get("memo").toString());
		viewHolder.xmmcTextView.setText(objMap.get("title").toString());
		viewHolder.xmjdTextView.setText(objMap.get("xmjdmc").toString());
		viewHolder.bfrqTextView.setText(objMap.get("opdate").toString());
		return convertView;
	}

	static class ViewHolder {
		TextView bfnrTextView;
		TextView xmmcTextView;
		TextView xmjdTextView;
		TextView bfrqTextView;
	}
}