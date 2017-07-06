package com.cr.adapter.jxc.cggl.cgfk;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class JxcCgglCgfkAddAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public JxcCgglCgfkAddAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_jxc_cggl_cgfk_add_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.yydhTextView = (TextView) convertView.findViewById(R.id.yhdh);
			viewHolder.djrqTextView = (TextView) convertView.findViewById(R.id.djrq);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.yydhTextView.setText(objMap.get("code")==null?objMap.get("code").toString():objMap.get("code").toString());
		viewHolder.djrqTextView.setText(objMap.get("billdate")==null?objMap.get("billdate").toString():objMap.get("billdate").toString());
		return convertView;
	}

	static class ViewHolder {
//		TextView bhmcggTextView;
		TextView yydhTextView;
		TextView djrqTextView;
	}
}