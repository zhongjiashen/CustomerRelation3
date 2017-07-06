package com.cr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.cr.model.WXLB;

public class GzptHjzxKhzlWhAdapter extends BaseAdapter {
	List<WXLB> list;
	private Context mContext;
	public GzptHjzxKhzlWhAdapter(List<WXLB> list, Context context) {
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
		WXLB sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_khzl_wh_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.xh = (TextView) convertView.findViewById(R.id.wh_xh);
			viewHolder.title = (TextView) convertView.findViewById(R.id.wh_sbmc);
			viewHolder.bh = (TextView) convertView.findViewById(R.id.wh_djbh);
			viewHolder.wxjd = (TextView) convertView.findViewById(R.id.wh_whjd);
			viewHolder.opdate = (TextView) convertView.findViewById(R.id.wh_bxrq);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.xh.setText((position+1)+"");
		viewHolder.title.setText(sb.getSbmc());
		viewHolder.bh.setText(sb.getBillcode());
		viewHolder.wxjd.setText(sb.getWxjdmc());
		viewHolder.opdate.setText(sb.getBsrq());
		return convertView;
	}

	static class ViewHolder {
		TextView xh;
		TextView title;
		TextView bh;
		TextView wxjd;
		TextView opdate;
	}
}