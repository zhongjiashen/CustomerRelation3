package com.cr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.cr.model.IndexModel;

public class IndexAdapter extends BaseAdapter {

	private Context mContext;
	List<IndexModel> adapterlist;
	public IndexAdapter() {
		
	}
	public IndexAdapter(Context mContext,List<IndexModel> list){
		this.mContext = mContext;
		this.adapterlist=list;
	}
	@Override
	public int getCount() {
		return adapterlist.size();
	}

	@Override
	public Object getItem(int position) {
		return adapterlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		IndexModel im=adapterlist.get(position);
		viewHolder = new ViewHolder();
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_index_index_item, null);
			viewHolder.logoImageView = (ImageView)convertView.findViewById(R.id.logo_img);
			viewHolder.nameTextView = (TextView)convertView.findViewById(R.id.logo_name);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.nameTextView.setText(im.getLogoName());
		viewHolder.logoImageView.setBackgroundResource(im.getLogoId());
		return convertView;
	}
	
	public static class ViewHolder{
		ImageView logoImageView;//信息类型的图标
		TextView nameTextView;//标题
	}
}
