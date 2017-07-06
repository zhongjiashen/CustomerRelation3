package com.cr.adapter.common;

import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.crcxj.activity.R;

public class CommonXzyyAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private BaseActivity activity;
	private int selectIndex;

	public CommonXzyyAdapter(List<Map<String, Object>> list,
			BaseActivity activity) {
		this.list = list;
		this.activity = activity;
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

	public int getSelectIndex() {
		return selectIndex;
	}

	public void setSelectIndex(int selectIndex) {
		this.selectIndex = selectIndex;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final Map<String, Object> objMap = list.get(position);
//		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.activity_common_xzyy_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.mcTextView = (TextView) convertView
					.findViewById(R.id.mc_textview);
			viewHolder.bhTextView = (TextView) convertView
					.findViewById(R.id.bh_textview);
			viewHolder.rqTextView = (TextView) convertView
					.findViewById(R.id.rq_textview);
			viewHolder.itemCheckBox = (CheckBox) convertView
					.findViewById(R.id.itemcheck);
			convertView.setTag(viewHolder);
			viewHolder.itemCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean arg1) {
							if (arg1) {
								objMap.put("ischecked", "1");
							} else {
								objMap.put("ischecked", "0");
							}
							notifyDataSetChanged();
						}
					});
//		} else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
		if (objMap.get("ischecked")==null||objMap.get("ischecked").toString().equals("0")) {
			viewHolder.itemCheckBox.setChecked(false);
		} else {
			viewHolder.itemCheckBox.setChecked(true);
		}
		viewHolder.mcTextView.setText("名称："
				+ objMap.get("cname").toString());
		viewHolder.bhTextView.setText("编号：" + objMap.get("code").toString());
		viewHolder.rqTextView
				.setText("日期：" + objMap.get("billdate").toString());
		return convertView;
	}

	static class ViewHolder {
		TextView mcTextView;
		TextView bhTextView;
		TextView rqTextView;
		TextView zeTextView;
		TextView yfTextView;
		TextView wfTextView;
		CheckBox itemCheckBox;
	}
}