package com.cr.adapter.common;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crcxj.activity.R;

public class CommonXzspflAdapter extends BaseAdapter {
	List<Map<String, Object>> list;
	private Activity activity;
	public CommonXzspflAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_common_xzsplb_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
			viewHolder.zwTextView = (TextView) convertView.findViewById(R.id.zw_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.nameTextView.setText(sb.get("name").toString());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
		if(sb.get("lcode").toString().length()==2){
			lp.setMargins(40, 0, 10, 0);  
		}else if(sb.get("lcode").toString().length()==4){
			lp.setMargins(70, 0, 10, 0); 
		}else if(sb.get("lcode").toString().length()==6){
			lp.setMargins(100, 0, 10, 0); 
		}else if(sb.get("lcode").toString().length()==8){
			lp.setMargins(130, 0, 10, 0); 
		}else if(sb.get("lcode").toString().length()==10){
			lp.setMargins(160, 0, 10, 0); 
		}
		
		viewHolder.nameTextView.setLayoutParams(lp);  
		viewHolder.zwTextView.setText(sb.get("lcode").toString());
		return convertView;
	}

	static class ViewHolder {
		TextView nameTextView;
		TextView zwTextView;
	}
}