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

public class JhzjJhxmAdapter extends BaseAdapter {

	List<JHRW> list;
	private Context mContext;

	public JhzjJhxmAdapter(List<JHRW> list, Context context,Activity activity) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_jhzj_jhxm_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.xh = (TextView) convertView.findViewById(R.id.xh_textview);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name_textview);
			viewHolder.zt = (TextView) convertView.findViewById(R.id.zt_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.xh.setText((position+1)+"");
		viewHolder.name.setText(sb.getJhname());
		if(sb.getLx().equals("0")){
			viewHolder.zt.setText("未审核");
		}else{
			viewHolder.zt.setText("已审核");
		}
		return convertView;
	}

	static class ViewHolder {
		TextView xh;
		TextView name;
		TextView zt;
	}
}