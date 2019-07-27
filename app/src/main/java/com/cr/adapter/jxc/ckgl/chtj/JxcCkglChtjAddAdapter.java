package com.cr.adapter.jxc.ckgl.chtj;

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

public class JxcCkglChtjAddAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public JxcCkglChtjAddAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_jxc_ckgl_chtj_add_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhmcggTextView = (TextView) convertView.findViewById(R.id.bhmcgg);
			viewHolder.djTextView = (TextView) convertView.findViewById(R.id.dj_textview);
			viewHolder.zjTextView = (TextView) convertView.findViewById(R.id.zj_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhmcggTextView.setText(objMap.get("goodsname")==null?objMap.get("name").toString():objMap.get("goodsname").toString());
	    viewHolder.djTextView.setText("调前单价：￥"+ FigureTools.sswrFigure((objMap.get("tqdj")==null?objMap.get("aoprice").toString():objMap.get("tqdj").toString())));
		viewHolder.zjTextView.setText("调后单价：￥"+FigureTools.sswrFigure((objMap.get("thdj")==null?objMap.get("anprice").toString():objMap.get("thdj").toString())));
		return convertView;
	}

	static class ViewHolder {
		TextView bhmcggTextView;
		TextView djTextView;
		TextView zjTextView;
	}
}