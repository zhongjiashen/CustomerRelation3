package com.cr.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.cr.model.DW;

public class GzptXzdwAdapter extends BaseAdapter {
	List<DW> list;
	private Context mContext;

	public GzptXzdwAdapter(List<DW> list, Context context,Activity activity) {
		this.list=list;
		this.mContext = context;
//		this.activity=activity;
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
	public List<DW> getList() {
        return list;
    }
    public void setList(List<DW> list) {
        this.list = list;
    }
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final DW sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_xzdw_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.title_textview);
//			viewHolder.opdateView = (TextView) convertView.findViewById(R.id.time_textview);
//			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.selectCheck);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.titleView.setText(sb.getName());
//		viewHolder.opdateView.setText("发布时间："+sb.getQsrq());
//		viewHolder.checkBox.setChecked(sb.isCheck());
//		viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//				sb.setCheck(arg1);
//			}
//		});
		return convertView;
	}

	static class ViewHolder {
		TextView titleView;
	}
}