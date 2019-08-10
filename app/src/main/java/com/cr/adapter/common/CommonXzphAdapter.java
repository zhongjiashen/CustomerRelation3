package com.cr.adapter.common;

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

public class CommonXzphAdapter extends BaseAdapter {
	List<Map<String, Object>> list;
	private Activity activity;
	public CommonXzphAdapter(List<Map<String, Object>> list,Activity activity) {
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
	public List<Map<String, Object>> getList() {
        return list;
    }
    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final Map<String, Object> sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_common_xzph_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhTextView = (TextView) convertView.findViewById(R.id.bh_textview);
			viewHolder.time1TextView = (TextView) convertView.findViewById(R.id.time1_textview);
			viewHolder.time2TextView = (TextView) convertView.findViewById(R.id.time2_textview);
			viewHolder.kyslTextView = (TextView) convertView.findViewById(R.id.kysl_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhTextView.setText("批号："+sb.get("batchcode").toString());
		viewHolder.time1TextView.setText("生产日期："+sb.get("produceddate").toString());
		viewHolder.time2TextView.setText("有效日期："+sb.get("validdate").toString());
		viewHolder.kyslTextView.setText("可用数量："+ FigureTools.sswrFigure(sb.get("onhand").toString()));
		return convertView;
	}

	static class ViewHolder {
		TextView bhTextView;
		TextView time1TextView;
		TextView time2TextView;
		TextView kyslTextView;
	}
}