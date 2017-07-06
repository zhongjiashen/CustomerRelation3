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
import com.cr.model.CPMX;

public class GzptXsdjXsdAdapter extends BaseAdapter {

	List<CPMX> list;
	private Context mContext;
	ViewHolder viewHolder;

	public GzptXsdjXsdAdapter(List<CPMX> list, Context context) {
		this.list=list;
		this.mContext = context;
	}
	
	public List<CPMX> getList() {
		return list;
	}
	public void setList(List<CPMX> list) {
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
		viewHolder = null;
		final CPMX sb=list.get(position);
//		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_xsdj_xsd_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.lb = (TextView) convertView.findViewById(R.id.lb);
			viewHolder.bm = (TextView) convertView.findViewById(R.id.bm);
			viewHolder.sl = (TextView) convertView.findViewById(R.id.sl);
			viewHolder.dj = (TextView) convertView.findViewById(R.id.dj);
			viewHolder.selectCheckBox=(CheckBox) convertView.findViewById(R.id.select_checkbox);
			viewHolder.selectCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					sb.setSelect(arg1);
				}
			});
			convertView.setTag(viewHolder);
//		} else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
		viewHolder.name.setText(sb.getGoodsname()+" "+sb.getGgxh());
		viewHolder.lb.setText("类别："+sb.getGoodstypename());
		viewHolder.bm.setText("编码："+sb.getGoodscode());
		viewHolder.sl.setText("数量："+sb.getQty().replace(".0", ""));
		viewHolder.dj.setText("单价："+sb.getPrice());
		viewHolder.selectCheckBox.setChecked(sb.isSelect());
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView lb;
		TextView bm;
		TextView dj;
		TextView sl;
		CheckBox selectCheckBox;
	}
}