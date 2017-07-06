package com.cr.adapter.khfw;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class KhfwAddAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public KhfwAddAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_jxc_cggl_cgdd_detail_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhmcggTextView = (TextView) convertView.findViewById(R.id.bhmcgg);
			viewHolder.djTextView = (TextView) convertView.findViewById(R.id.dj_textview);
			viewHolder.zjTextView = (TextView) convertView.findViewById(R.id.zj_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String bh=objMap.get("bh")==null?objMap.get("code").toString():objMap.get("bh").toString();
		String mc=objMap.get("mc")==null?objMap.get("goodsname").toString():objMap.get("mc").toString();
		String gg=objMap.get("gg")==null?objMap.get("specs").toString():objMap.get("gg").toString();
		String xh=objMap.get("xh")==null?objMap.get("model").toString():objMap.get("xh").toString();
		String fwjd=objMap.get("fwjd")==null?objMap.get("wxjgname").toString():objMap.get("fwjd").toString();
		String fwzt=objMap.get("fwzt")==null?objMap.get("wxjdname").toString():objMap.get("fwzt").toString();
		viewHolder.bhmcggTextView.setText(bh+" / "+mc+" / "+gg+" / "+xh);
		viewHolder.djTextView.setText("服务进度："+(fwjd.equals("")?"无":fwjd));
		viewHolder.zjTextView.setText("服务状态："+(fwzt.equals("")?"无":fwzt));
		return convertView;
	}

	static class ViewHolder {
		TextView bhmcggTextView;
		TextView djTextView;
		TextView zjTextView;
	}
}