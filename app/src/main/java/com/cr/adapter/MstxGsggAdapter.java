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
import com.cr.model.GSGG;

public class MstxGsggAdapter extends BaseAdapter {
//	private static final String TAG = "PpPpAdapter";
//	private AsynImageLoader asynImageLoader;

	List<GSGG> list;
	private Context mContext;
//	private Activity activity;

	public MstxGsggAdapter(List<GSGG> list, Context context,Activity activity) {
//		this.mCount = count;
		this.list=list;
//		mImageLoader = new ImageLoader();
//		asynImageLoader = new AsynImageLoader(true,activity,false);
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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		GSGG sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_mstx_gsgg_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.title_textview);
			viewHolder.opdateView = (TextView) convertView.findViewById(R.id.time_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.titleView.setText(sb.getTitle());
		viewHolder.opdateView.setText("发布时间："+sb.getOpdate());
		return convertView;
	}

	static class ViewHolder {
		TextView opdateView;
		TextView titleView;
	}
}