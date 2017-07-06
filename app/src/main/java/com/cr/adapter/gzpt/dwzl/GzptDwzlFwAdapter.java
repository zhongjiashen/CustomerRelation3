package com.cr.adapter.gzpt.dwzl;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class GzptDwzlFwAdapter extends BaseAdapter {
	List<Map<String, Object>> list;
	private Activity activity;
	public GzptDwzlFwAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_dwzl_fw_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhTextView = (TextView) convertView.findViewById(R.id.bh_textview);
			viewHolder.lxTextView = (TextView) convertView.findViewById(R.id.lx_textview);
			viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.time_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhTextView.setText(sb.get("code").toString());
		if(sb.get("billtypeid").toString().equals("0")){
			viewHolder.lxTextView.setText("维修");
		}else{
			viewHolder.lxTextView.setText("安装");
		}
		viewHolder.timeTextView.setText(sb.get("billdate").toString());
		return convertView;
	}

	static class ViewHolder {
		TextView bhTextView;
		TextView lxTextView;
		TextView timeTextView;
	}
}