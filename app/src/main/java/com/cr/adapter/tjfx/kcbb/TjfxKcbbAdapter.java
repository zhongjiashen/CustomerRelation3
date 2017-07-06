package com.cr.adapter.tjfx.kcbb;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crcxj.activity.R;

public class TjfxKcbbAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;
	private int selectIndex;

	public TjfxKcbbAdapter(List<Map<String, Object>> list,Activity activity) {
		this.list=list;
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
	public int getSelectIndex() {
        return selectIndex;
    }
    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Map<String, Object> objMap=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_tjfx_kcbb_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhTextView = (TextView) convertView.findViewById(R.id.bh_textview);
			viewHolder.mcTextView = (TextView) convertView.findViewById(R.id.mc_textview);
			viewHolder.ggTextView = (TextView) convertView.findViewById(R.id.gg_textview);
			viewHolder.xhTextView = (TextView) convertView.findViewById(R.id.xh_textview);
			viewHolder.kcTextView = (TextView) convertView.findViewById(R.id.kc_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhTextView.setText("编号："+objMap.get("goodscode").toString());
		viewHolder.mcTextView.setText("名称："+objMap.get("goodsname").toString());
		viewHolder.ggTextView.setText("规格："+objMap.get("specs").toString());
		viewHolder.xhTextView.setText("型号："+objMap.get("model").toString());
		viewHolder.kcTextView.setText("库存："+objMap.get("onhand").toString()+" "+
				objMap.get("unitname").toString());
		String sl=objMap.get("onhand").toString();
		if(Double.parseDouble(sl)<0){
			viewHolder.kcTextView.setTextColor(Color.parseColor("#ff0000"));
		}else{
			viewHolder.kcTextView.setTextColor(Color.parseColor("#0000ff"));
		}
		return convertView;
	}

	static class ViewHolder {
		TextView bhTextView;
		TextView mcTextView;
		TextView ggTextView;
		TextView xhTextView;
		TextView kcTextView;
	}
}