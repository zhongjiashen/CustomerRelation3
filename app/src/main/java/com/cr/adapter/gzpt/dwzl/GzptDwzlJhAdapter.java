package com.cr.adapter.gzpt.dwzl;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

import java.util.List;
import java.util.Map;

public class GzptDwzlJhAdapter extends BaseAdapter {
	List<Map<String, Object>> list;
	private Activity activity;
	public GzptDwzlJhAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_dwzl_jh_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhTextView = (TextView) convertView.findViewById(R.id.bh_textview);
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
			viewHolder.jdTextView = (TextView) convertView.findViewById(R.id.jd_textview);
			viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.time_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhTextView.setText(sb.get("code").toString());
		viewHolder.nameTextView.setText(sb.get("title").toString());
		viewHolder.jdTextView.setText("当前进度："+sb.get("gmmc").toString());
		viewHolder.timeTextView.setText(sb.get("billdate").toString());
		return convertView;
	}

	static class ViewHolder {
		TextView bhTextView;
		TextView nameTextView;
		TextView jdTextView;
		TextView timeTextView;
	}
}