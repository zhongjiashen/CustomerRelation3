package com.cr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.cr.model.XMLB;

public class GzptHjzxKhzlXmAdapter extends BaseAdapter {

	List<XMLB> list;
	private Context mContext;

	public GzptHjzxKhzlXmAdapter(List<XMLB> list, Context context) {
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
		XMLB sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_khzl_xm_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.xh = (TextView) convertView.findViewById(R.id.xm_xh);
			viewHolder.title = (TextView) convertView.findViewById(R.id.xm_xmmc);
			viewHolder.xmjdmc = (TextView) convertView.findViewById(R.id.xm_xmjd);
			viewHolder.opdate = (TextView) convertView.findViewById(R.id.xm_rq);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.xh.setText((position+1)+"");
		viewHolder.title.setText(sb.getTitle());
		viewHolder.xmjdmc.setText(sb.getXmjdmc());
		
		viewHolder.opdate.setText(sb.getUpopdate());
		return convertView;
	}

	static class ViewHolder {
		TextView xh;
		TextView title;
		TextView xmjdmc;
		TextView opdate;
	}
}