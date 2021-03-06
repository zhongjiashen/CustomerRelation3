package com.cr.adapter.jxc.ckgl.ckdb;

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

public class JxcCkglCkdbAddAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public JxcCkglCkdbAddAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_jxc_cggl_cgsh_detail_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhmcggTextView = (TextView) convertView.findViewById(R.id.bhmcgg);
			viewHolder.djTextView = (TextView) convertView.findViewById(R.id.dj_textview);
			viewHolder.zjTextView = (TextView) convertView.findViewById(R.id.zj_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhmcggTextView.setText((objMap.get("name")==null?objMap.get("goodsname").toString():objMap.get("name").toString()));
		viewHolder.djTextView.setText("调拨数量："+ FigureTools.sswrFigure(objMap.get("unitqty")==null?objMap.get("sl").toString():objMap.get("unitqty").toString()));
//		viewHolder.zjTextView.setText("￥"+Integer.parseInt(objMap.get("unitprice").toString())*Integer.parseInt(objMap.get("unitqty").toString()));
		viewHolder.zjTextView.setVisibility(View.GONE);
		return convertView;
	}

	static class ViewHolder {
		TextView bhmcggTextView;
		TextView djTextView;
		TextView zjTextView;
	}
}