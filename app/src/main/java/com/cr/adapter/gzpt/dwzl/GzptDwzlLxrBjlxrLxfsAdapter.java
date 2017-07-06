package com.cr.adapter.gzpt.dwzl;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.crcxj.activity.R;

public class GzptDwzlLxrBjlxrLxfsAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Activity activity;

	public GzptDwzlLxrBjlxrLxfsAdapter(List<Map<String, Object>> list,Activity activity) {
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
	public List<Map<String, Object>> getList() {
        return list;
    }
    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
    @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		Map<String, Object> objMap=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_dwzl_dw_bjdw_lxfs_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
			viewHolder.addrTextView = (TextView) convertView.findViewById(R.id.addr_textview);
			viewHolder.selectCheckBox = (CheckBox) convertView.findViewById(R.id.select_checkbox);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(objMap.get("lb").toString().equals("1")){
		    viewHolder.nameTextView.setText("固定电话");
		}else if(objMap.get("lb").toString().equals("2")){
		    viewHolder.nameTextView.setText("手机号码");
		}else if(objMap.get("lb").toString().equals("3")){
		    viewHolder.nameTextView.setText("电子邮件");
        }else if(objMap.get("lb").toString().equals("4")){
            viewHolder.nameTextView.setText("Q Q号码");
        }
		if(objMap.get("select")!=null&&objMap.get("select").toString().equals("true")){
            viewHolder.selectCheckBox.setChecked(true);
        }else{
            viewHolder.selectCheckBox.setChecked(false);
        }
		viewHolder.addrTextView.setText(objMap.get("itemno").toString());
		viewHolder.selectCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                list.get(position).put("select",arg1);
            }
        });
		return convertView;
	}

	static class ViewHolder {
		TextView nameTextView;
		TextView addrTextView;
		CheckBox selectCheckBox;
	}
}