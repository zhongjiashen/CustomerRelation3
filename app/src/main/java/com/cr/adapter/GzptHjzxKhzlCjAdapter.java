package com.cr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.cr.model.XSDLB;

public class GzptHjzxKhzlCjAdapter extends BaseAdapter {

	List<XSDLB> list;
	private Context mContext;

	public GzptHjzxKhzlCjAdapter(List<XSDLB> list, Context context) {
		this.list=list;
		this.mContext = context;
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
		XSDLB sb=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_khzl_cjjl_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.xh = (TextView) convertView.findViewById(R.id.cj_xh);
			viewHolder.rq = (TextView) convertView.findViewById(R.id.cj_rq);
			viewHolder.djbh = (TextView) convertView.findViewById(R.id.cj_djbh);
			viewHolder.je = (TextView) convertView.findViewById(R.id.cj_je);
			viewHolder.lxr = (TextView) convertView.findViewById(R.id.cj_lxr);
			viewHolder.zt = (TextView) convertView.findViewById(R.id.cj_zt);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.xh.setText((position+1)+"");
		viewHolder.rq.setText(sb.getBilldate());
		viewHolder.djbh.setText(sb.getBillcode());
		viewHolder.je.setText(sb.getZje());
		viewHolder.lxr.setText(sb.getLxrname());
		if(sb.getZt().equals("0")){
			viewHolder.zt.setText("未审");
		}else{
			viewHolder.zt.setText("已审");
		}
		return convertView;
	}

	static class ViewHolder {
		TextView xh;
		TextView rq;
		TextView djbh;
		TextView je;
		TextView lxr;
		TextView zt;
	}
}