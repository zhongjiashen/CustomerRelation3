package com.cr.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.crcxj.activity.R;
import com.cr.activity.SLView;
import com.cr.model.CPMX;
import com.cr.myinterface.SLViewValueChange;

public class GzptXsdjXsdXzcpAdapter extends BaseAdapter {

	List<CPMX> list;
	private Context mContext;
	ViewHolder viewHolder;
	List<SLView>slList=new ArrayList<SLView>();

	public GzptXsdjXsdXzcpAdapter(List<CPMX> list, Context context) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_gzpt_xsdj_xsd_xzcp_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			viewHolder.lb = (TextView) convertView.findViewById(R.id.lb);
			viewHolder.bm = (TextView) convertView.findViewById(R.id.bm);
			viewHolder.dj = (EditText) convertView.findViewById(R.id.dj);
			viewHolder.sl = (SLView) convertView.findViewById(R.id.sl);
			viewHolder.sl.setOnValueChange(new SLViewValueChange() {
				@Override
				public void onValueChange(double sl) {
					sb.setQty(sl+"");
				}
			});
//			sb.setSlView(viewHolder.sl);
			viewHolder.selectCheckBox=(CheckBox) convertView.findViewById(R.id.select_checkbox);
//			viewHolder.selectCheckBox.setChecked(sb.isSelect());
			viewHolder.selectCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					sb.setSelect(arg1);
				}
			});
			
			viewHolder.selectCheckBox.setChecked(sb.isSelect());
//			viewHolder.selectCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				@Override
//				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//					sb.setSelect(arg1);
//					if(arg1){
//						if(sb.getSlView().getSl()==0){
//							Toast.makeText(mContext, "请选择数量",Toast.LENGTH_SHORT).show();
////							sb.setSelect(false);
////							viewHolder.selectCheckBox.setChecked(false);
//						}else{
//							sb.setQty(sb.getSlView().getSl()+"");
//						}
//					}
//				}
//			});
			convertView.setTag(viewHolder);
//		} else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
		viewHolder.name.setText(sb.getGoodsname()+" "+sb.getGgxh());
		viewHolder.lb.setText("类别："+sb.getGoodstypename());
		viewHolder.bm.setText("编码："+sb.getGoodscode());
		viewHolder.sl.setSl(Integer.parseInt(sb.getQty()==null?"0":sb.getQty()));
		viewHolder.dj.setText(sb.getPrice());
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView lb;
		TextView bm;
		EditText dj;
		SLView sl;
		CheckBox selectCheckBox;
	}
}