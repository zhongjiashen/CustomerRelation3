package com.cr.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.cr.model.CustomerTel;

public class GzptXzldAdapter extends BaseAdapter {
	List<CustomerTel> list;
	private Context mContext;
	public GzptXzldAdapter(List<CustomerTel> list, Context context,Activity activity) {
		this.list=list;
		this.mContext = context;
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
		CustomerTel jhrwgslxr=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_xzld_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.nameView = (TextView) convertView.findViewById(R.id.title_textview);
			viewHolder.typeView = (TextView) convertView.findViewById(R.id.model_textview);
			viewHolder.telView = (TextView) convertView.findViewById(R.id.tel_textview);
			viewHolder.timeView = (TextView) convertView.findViewById(R.id.time_textview);
			viewHolder.durationView = (TextView) convertView.findViewById(R.id.addr_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.nameView.setText(jhrwgslxr.getName());
		viewHolder.durationView.setText(jhrwgslxr.getDuration());
		viewHolder.typeView.setText(jhrwgslxr.getType());
		viewHolder.telView.setText(jhrwgslxr.getTel());
		viewHolder.timeView.setText(jhrwgslxr.getTime());
		return convertView;
	}

	static class ViewHolder {
		TextView nameView;
		TextView telView;
		TextView typeView;
		TextView durationView;
		TextView timeView;
	}
}