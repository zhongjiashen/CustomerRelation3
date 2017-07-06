package com.cr.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crcxj.activity.R;

public class MstxGztxAdapter extends BaseAdapter {
//	private static final String TAG = "PpPpAdapter";
//	private AsynImageLoader asynImageLoader;

//	List<GZTX> list;
	List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	private Activity activity;

	public MstxGztxAdapter(List<Map<String, Object>>list,Activity activity) {
//		this.mCount = count;
		this.list=list;
//		mImageLoader = new ImageLoader();
//		asynImageLoader = new AsynImageLoader(true,activity,false);
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
//		GZTX sb=list.get(position);
		Map<String, Object>object=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.title_textview);
			viewHolder.numTextView = (TextView) convertView.findViewById(R.id.num_textview);
			viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xyb_jt);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.titleView.setText(object.get("typename").toString());
		viewHolder.numTextView.setText(object.get("cont").toString());
		return convertView;
	}

	static class ViewHolder {
		TextView numTextView;
		TextView titleView;
		ImageView xybJtImageView;
	}
}