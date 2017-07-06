package com.cr.adapter.jxc.cggl.cgdd;

import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;

public class JxcCgglCgddShlcAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	private BaseActivity activity;
	private int selectIndex;
	private String shjb="";
	private String opid;

	public JxcCgglCgddShlcAdapter(List<Map<String, Object>> list,BaseActivity activity,String opid) {
		this.list=list;
		this.activity=activity;
//		this.opid=opid;
	}
	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}
	
	public String getOpid() {
		return opid;
	}
	public void setOpid(String opid) {
		this.opid = opid;
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
    public String getShjb() {
        return shjb;
    }
    public void setShjb(String shjb) {
        this.shjb = shjb;
    }
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final Map<String, Object> objMap=list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.activity_jxc_cggl_cgdd_shlc_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.shCheckBox=(CheckBox) convertView.findViewById(R.id.sh_checkbox);
			viewHolder.shjbTextView = (TextView) convertView.findViewById(R.id.shjb_textview);
			viewHolder.bmTextView = (TextView) convertView.findViewById(R.id.bm_textview);
			viewHolder.shrTextView = (TextView) convertView.findViewById(R.id.shr_textview);
			viewHolder.shztTextView=(TextView) convertView.findViewById(R.id.shzt_textview);
			viewHolder.shrqTextView=(TextView) convertView.findViewById(R.id.shrq_textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(objMap.get("levels").toString().equals("1")){
		    viewHolder.shjbTextView.setText("一级审核");
		}else if(objMap.get("levels").toString().equals("2")){
		    viewHolder.shjbTextView.setText("二级审核");
		}else if(objMap.get("levels").toString().equals("3")){
		    viewHolder.shjbTextView.setText("三级审核");
        }else if(objMap.get("levels").toString().equals("4")){
            viewHolder.shjbTextView.setText("四级审核");
        }else if(objMap.get("levels").toString().equals("5")){
            viewHolder.shjbTextView.setText("五级审核");
        }
		viewHolder.bmTextView.setText(objMap.get("depname").toString());
		viewHolder.shrTextView.setText(objMap.get("opname").toString());
		viewHolder.shztTextView.setText(objMap.get("shzt").toString().equals("0")?"未审核":"已审核");
		viewHolder.shrqTextView.setText(objMap.get("shrq1").toString());
		viewHolder.shCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                if(arg1){
                    String userId=ShareUserInfo.getUserId(activity);
                    if(!userId.equals(objMap.get("opid").toString())){
                        activity.showToastPromopt("您没有该级别的审核权限！");
                        arg0.setChecked(false);
                    }else{
                        if(shjb.equals("")){
                            shjb=objMap.get("levels").toString();
                            opid=objMap.get("opid").toString();
                        }else{
                            activity.showToastPromopt("一次只能审核一个级别的信息！");
                            arg0.setChecked(false);
                        }
                    }
                }else{
                    shjb="";
                }
            }
        });
		return convertView;
	}

	static class ViewHolder {
	    CheckBox shCheckBox;
		TextView shjbTextView;
		TextView bmTextView;
		TextView shrTextView;
		TextView shztTextView;
		TextView shrqTextView;
	}
}