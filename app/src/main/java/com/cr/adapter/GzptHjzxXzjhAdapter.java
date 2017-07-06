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
import com.cr.model.JHRW;

public class GzptHjzxXzjhAdapter extends BaseAdapter {

	List<JHRW> list;
	private Context mContext;

	public GzptHjzxXzjhAdapter(List<JHRW> list, Context context,Activity activity) {
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
		JHRW sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_hjzx_xzjh_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.xh = (TextView) convertView.findViewById(R.id.xh_textview);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name_textview);
			viewHolder.ksrq = (TextView) convertView.findViewById(R.id.ksrq_textview);
			viewHolder.jsrq = (TextView) convertView.findViewById(R.id.jsrq_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.xh.setText((position+1)+"");
		viewHolder.name.setText(sb.getJhname());
		viewHolder.ksrq.setText(sb.getQsrq());
		viewHolder.jsrq.setText(sb.getZzrq());
		return convertView;
	}

	static class ViewHolder {
		TextView xh;
		TextView name;
		TextView ksrq;
		TextView jsrq;
	}
}