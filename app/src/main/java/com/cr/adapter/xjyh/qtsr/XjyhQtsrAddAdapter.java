package com.cr.adapter.xjyh.qtsr;

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

public class XjyhQtsrAddAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public XjyhQtsrAddAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_xjyh_fyzc_add_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.fymcTextView = (TextView) convertView.findViewById(R.id.fymc_textview);
			viewHolder.fyjeTextView = (TextView) convertView.findViewById(R.id.fyje_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.fymcTextView.setText(objMap.get("name").toString());
		viewHolder.fyjeTextView.setText("￥"+ FigureTools.sswrFigure(objMap.get("amount").toString()));
		return convertView;
	}

	static class ViewHolder {
		TextView fymcTextView;
		TextView fyjeTextView;
	}
}