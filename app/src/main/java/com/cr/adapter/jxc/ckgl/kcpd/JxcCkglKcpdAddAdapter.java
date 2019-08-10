package com.cr.adapter.jxc.ckgl.kcpd;

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

public class JxcCkglKcpdAddAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public JxcCkglKcpdAddAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_jxc_ckgl_kcpd_add_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhmcggTextView = (TextView) convertView.findViewById(R.id.bhmcgg);
			viewHolder.zmslTextView = (TextView) convertView.findViewById(R.id.zmsl_textview);
			viewHolder.spslTextView = (TextView) convertView.findViewById(R.id.spsl_textview);
			viewHolder.ykslTextView = (TextView) convertView.findViewById(R.id.yksl_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhmcggTextView.setText(objMap.get("goodsname")==null?objMap.get("name").toString():objMap.get("goodsname").toString());
	    viewHolder.zmslTextView.setText("账面数量："+ FigureTools.sswrFigure(objMap.get("zmsl")==null?objMap.get("zmonhand").toString():objMap.get("zmsl").toString()));
		viewHolder.spslTextView.setText("实盘数量："+FigureTools.sswrFigure(objMap.get("spsl")==null?objMap.get("sponhand").toString():objMap.get("spsl").toString()));
		int yksl=0;
		if(objMap.get("zmsl")==null){
			yksl=(int)Double.parseDouble(objMap.get("sponhand").toString())-(int)Double.parseDouble(objMap.get("zmonhand").toString());
		}else{
			yksl=(int)Double.parseDouble(objMap.get("spsl").toString())-(int)Double.parseDouble(objMap.get("zmsl").toString());
		}
		viewHolder.ykslTextView.setText("盈亏数量："+yksl);
		return convertView;
	}

	static class ViewHolder {
		TextView bhmcggTextView;
		TextView zmslTextView;
		TextView spslTextView;
		TextView ykslTextView;
	}
}