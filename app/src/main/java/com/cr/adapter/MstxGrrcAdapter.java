package com.cr.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.cr.model.GRRC;

public class MstxGrrcAdapter extends BaseAdapter {
	List<GRRC> list;
	private Context mContext;
	private boolean isDelete=true;//是否可以使用多选删除

	public MstxGrrcAdapter(List<GRRC> list, Context context,Activity activity) {
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
	public List<GRRC> getList() {
		return list;
	}
	public void setList(List<GRRC> list) {
		this.list = list;
	}
	/**
	 * 设置是否可以多选删除
	 */
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final GRRC sb=list.get(position);
//		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_mstx_grrc_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.title_textview);
			viewHolder.opdateView = (TextView) convertView.findViewById(R.id.time_textview);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.selectCheck);
			convertView.setTag(viewHolder);
//		} else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
		viewHolder.titleView.setText(sb.getTitle());
		viewHolder.opdateView.setText("提醒时间："+(sb.getQsrq().equals("")?"无":sb.getQsrq()));
		viewHolder.checkBox.setChecked(sb.isCheck());
		viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				sb.setCheck(arg1);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView opdateView;
		TextView titleView;
		CheckBox checkBox;
	}
}