package com.cr.adapter.jxc.xsgl.xssk;

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

public class JxcXsglXsskDetailAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;

	public JxcXsglXsskDetailAdapter(List<Map<String, Object>> list,Activity activity) {
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
		viewHolder.bhmcggTextView.setText(objMap.get("code").toString()+" / "+objMap.get("name").toString()+" / "+objMap.get("specs").toString()+" / "+objMap.get("model").toString());
		viewHolder.djTextView.setText("￥"+FigureTools.sswrFigure(objMap.get("unitprice").toString())+"*"+objMap.get("unitqty").toString()+""+objMap.get("unitname").toString());
//		viewHolder.zjTextView.setText("￥"+Integer.parseInt(objMap.get("unitprice").toString())*Integer.parseInt(objMap.get("unitqty").toString()));
		viewHolder.zjTextView.setText("￥"+FigureTools.sswrFigure(Double.parseDouble(objMap.get("unitprice").toString())*Double.parseDouble(objMap.get("unitqty").toString())));
		return convertView;
	}

	static class ViewHolder {
		TextView bhmcggTextView;
		TextView djTextView;
		TextView zjTextView;
	}
}