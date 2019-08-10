package com.cr.adapter.gzpt.dwzl;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cr.tools.FigureTools;
import com.crcxj.activity.R;

import java.util.List;
import java.util.Map;

public class GzptDwzlDdAdapter extends BaseAdapter {
	List<Map<String, Object>> list;
	private Activity activity;
	public GzptDwzlDdAdapter(List<Map<String, Object>> list,Activity activity) {
		this.list=list;
		this.activity=activity;
	}
	@Override
	public int getCount() {
		if(list==null)
			return 0;
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
		notifyDataSetChanged();
    }
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final Map<String, Object> sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_dwzl_dd_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhTextView = (TextView) convertView.findViewById(R.id.bh_textview);
			viewHolder.moneyTextView = (TextView) convertView.findViewById(R.id.money_textview);
			viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.time_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhTextView.setText(sb.get("code").toString());
		viewHolder.moneyTextView.setText("￥"+ FigureTools.sswrFigure(sb.get("amount").toString()));
		viewHolder.timeTextView.setText(sb.get("billdate").toString());
		return convertView;
	}

	static class ViewHolder {
		TextView bhTextView;
		TextView moneyTextView;
		TextView timeTextView;
	}
}