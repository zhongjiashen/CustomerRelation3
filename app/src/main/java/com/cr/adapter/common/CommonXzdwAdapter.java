package com.cr.adapter.common;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class CommonXzdwAdapter extends BaseAdapter {
	List<Map<String, Object>> list;
	private Activity activity;
//	private String type;
	public CommonXzdwAdapter(List<Map<String, Object>> list,Activity activity,String type) {
		this.list=list;
		this.activity=activity;
//		this.type=type;
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_common_xzdw_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhTextView = (TextView) convertView.findViewById(R.id.bh_textview);
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
			viewHolder.gysTextView = (TextView) convertView.findViewById(R.id.gys_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhTextView.setText(sb.get("code").toString());
		viewHolder.nameTextView.setText(sb.get("cname").toString());
		String type=sb.get("types").toString();
		if(type.equals("1")){
		    viewHolder.gysTextView.setText("客户");
		}else if(type.equals("2")){
		    viewHolder.gysTextView.setText("供应商");
		}else if(type.equals("3")){
            viewHolder.gysTextView.setText("竞争对手");
        }else if(type.equals("4")){
            viewHolder.gysTextView.setText("渠道");
        }else if(type.equals("5")){
            viewHolder.gysTextView.setText("员工");
        }
		return convertView;
	}

	static class ViewHolder {
		TextView bhTextView;
		TextView nameTextView;
		TextView gysTextView;
	}
}