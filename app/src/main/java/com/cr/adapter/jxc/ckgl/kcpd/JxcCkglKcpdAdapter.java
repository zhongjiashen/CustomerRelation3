package com.cr.adapter.jxc.ckgl.kcpd;

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

public class JxcCkglKcpdAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private Activity activity;
	private int selectIndex;

	public JxcCkglKcpdAdapter(List<Map<String, Object>> list,Activity activity) {
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_jxc_ckgl_kcpd_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.bhTextView = (TextView) convertView.findViewById(R.id.bh_textview);
			viewHolder.rqTextView = (TextView) convertView.findViewById(R.id.rq_textview);
			viewHolder.shImageView = (ImageView) convertView.findViewById(R.id.sh_imageview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bhTextView.setText(objMap.get("code").toString());
		viewHolder.rqTextView.setText(objMap.get("billdate").toString());
		if(objMap.get("shzt").toString().equals("0")){
		    viewHolder.shImageView.setBackgroundResource(R.drawable.wsh);
		}else if(objMap.get("shzt").toString().equals("1")){
		    viewHolder.shImageView.setBackgroundResource(R.drawable.ysh);
		}else if(objMap.get("shzt").toString().equals("2")){
		    viewHolder.shImageView.setBackgroundResource(R.drawable.shz);
        }
		return convertView;
	}

	static class ViewHolder {
		TextView bhTextView;
		TextView rqTextView;
		ImageView shImageView;
	}
}