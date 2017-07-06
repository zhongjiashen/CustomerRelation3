package com.cr.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cr.activity.JhzjJrzjActivity;
import com.cr.activity.JhzjXzxmActivity;
import com.crcxj.activity.R;
import com.cr.model.JHRW;

public class JhzjZdyjhAdapter extends BaseAdapter {

	List<JHRW> list;
	private Context mContext;
	private Activity activity;

	public JhzjZdyjhAdapter(List<JHRW> list, Context context,Activity activity) {
		this.list=list;
		this.mContext = context;
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
		final JHRW sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_jhzj_zdyjh_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.xh = (TextView) convertView.findViewById(R.id.xh_textview);
			viewHolder.name = (TextView) convertView.findViewById(R.id.name_textview);
			viewHolder.xzxm = (ImageView) convertView.findViewById(R.id.xzxm_imageview);
			viewHolder.jrzj = (ImageView) convertView.findViewById(R.id.jrzj_imageview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.xh.setText((position+1)+"");
		viewHolder.name.setText(sb.getQsrq());
		viewHolder.xzxm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(activity,JhzjXzxmActivity.class);
				intent.putExtra("jhid",sb.getId());
				intent.putExtra("start", "0");
				intent.putExtra("object", sb);
				activity.startActivity(intent);
			}
		});
		viewHolder.jrzj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(activity,JhzjJrzjActivity.class);
				intent.putExtra("jhid",sb.getId());
				intent.putExtra("type","zdyjh");
				activity.startActivity(intent);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView xh;
		TextView name;
		ImageView xzxm;
		ImageView jrzj;
	}
}