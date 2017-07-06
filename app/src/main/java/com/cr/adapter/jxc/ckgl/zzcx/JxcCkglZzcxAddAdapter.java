package com.cr.adapter.jxc.ckgl.zzcx;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class JxcCkglZzcxAddAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public JxcCkglZzcxAddAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_jxc_ckgl_zzcx_add_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhmcggTextView = (TextView) convertView.findViewById(R.id.bhmcgg);
			viewHolder.djTextView = (TextView) convertView.findViewById(R.id.dj_textview);
			viewHolder.slTextView = (TextView) convertView.findViewById(R.id.sl_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhmcggTextView.setText(objMap.get("goodsname")==null?objMap.get("name").toString():objMap.get("goodsname").toString());
	    viewHolder.djTextView.setText("单价：￥"+(objMap.get("dj")==null?objMap.get("unitprice").toString():objMap.get("dj").toString()));
		viewHolder.slTextView.setText("数量："+(objMap.get("sl")==null?objMap.get("unitqty").toString():objMap.get("sl").toString()));
		return convertView;
	}

	static class ViewHolder {
		TextView bhmcggTextView;
		TextView djTextView;
		TextView slTextView;
	}
}