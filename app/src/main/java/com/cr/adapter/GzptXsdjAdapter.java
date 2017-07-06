package com.cr.adapter;

import java.util.List;

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
import com.cr.model.XSDLB;

public class GzptXsdjAdapter extends BaseAdapter {

	List<XSDLB> list;
	private Context mContext;
	public GzptXsdjAdapter(List<XSDLB> list, Context context) {
		this.list=list;
		this.mContext = context;
	}
	public List<XSDLB> getList() {
		return list;
	}

	public void setList(List<XSDLB> list) {
		this.list = list;
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
		final XSDLB sb=list.get(position);
//		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_xsdj_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.xh = (CheckBox) convertView.findViewById(R.id.cj_xh);
			viewHolder.rq = (TextView) convertView.findViewById(R.id.cj_rq);
			viewHolder.djbh = (TextView) convertView.findViewById(R.id.cj_djbh);
			viewHolder.je = (TextView) convertView.findViewById(R.id.cj_je);
			viewHolder.lxr = (TextView) convertView.findViewById(R.id.cj_lxr);
			viewHolder.zt = (TextView) convertView.findViewById(R.id.cj_zt);
			convertView.setTag(viewHolder);
//		} else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
//		viewHolder.xh.setHint((position+1)+"");
		viewHolder.xh.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				sb.setCheck(arg1);
			}
		});
		viewHolder.rq.setText(sb.getBilldate());
		viewHolder.djbh.setText(sb.getBillcode());
		viewHolder.je.setText(sb.getZje());
		viewHolder.lxr.setText(sb.getLxrname());
		if(sb.getZt().equals("0")){
			viewHolder.zt.setText("未审");
		}else{
			viewHolder.zt.setText("已审");
		}
		viewHolder.xh.setChecked(sb.isCheck());
		return convertView;
	}

	static class ViewHolder {
		CheckBox xh;
		TextView rq;
		TextView djbh;
		TextView je;
		TextView lxr;
		TextView zt;
	}
}