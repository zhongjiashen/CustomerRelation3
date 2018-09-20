package com.cr.adapter.jxc.cggl.cgfk;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;

public class JxcCgglCgfkXzyyAdapter extends BaseAdapter {

	List<Map<String, Object>> list;
	List<Map<String, Object>> oldList;
	private BaseActivity activity;
	private int selectIndex;

	public JxcCgglCgfkXzyyAdapter(List<Map<String, Object>> list,
			BaseActivity activity) {
		this.list = list;
		this.activity = activity;
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
	public int getItemViewType(int position) {
		if (list.get(position).get("isDetail").equals("0")) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		int viewType = getItemViewType(position);
		ViewHolder viewHolder = null;
		ViewHolder2 viewHolder2 = null;
		final Map<String, Object> objMap = list.get(position);
		if (viewType == 0) {
			// if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.activity_jxc_cggl_cgfk_xzyy_item, null);// 这个过程相当耗时间
			viewHolder = new ViewHolder();
			viewHolder.mcTextView = (TextView) convertView
					.findViewById(R.id.mc_textview);
			viewHolder.bhTextView = (TextView) convertView
					.findViewById(R.id.bh_textview);
			viewHolder.rqTextView = (TextView) convertView
					.findViewById(R.id.rq_textview);
			viewHolder.zeTextView = (TextView) convertView
					.findViewById(R.id.ze_textview);
			viewHolder.yfTextView = (TextView) convertView
					.findViewById(R.id.yf_textview);
			viewHolder.wfTextView = (TextView) convertView
					.findViewById(R.id.wf_textview);
			viewHolder.itemCheckBox = (CheckBox) convertView
					.findViewById(R.id.itemcheck);
			convertView.setTag(viewHolder);
			viewHolder.itemCheckBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean arg1) {
							if (arg1) {
								objMap.put("ischecked", "1");
							} else {
								objMap.put("ischecked", "0");
							}
							notifyDataSetChanged();
						}
					});
			// } else {
			// viewHolder = (ViewHolder) convertView.getTag();
			// }
			if (objMap.get("ischecked").toString().equals("0")) {
				viewHolder.itemCheckBox.setChecked(false);
			} else {
				viewHolder.itemCheckBox.setChecked(true);
			}
			viewHolder.mcTextView
					.setText("名称：" + objMap.get("billtypename").toString());
			viewHolder.bhTextView
					.setText("编号：" + objMap.get("code").toString());
			viewHolder.rqTextView.setText("日期："
					+ objMap.get("billdate").toString());
			viewHolder.zeTextView.setText("总额："
					+ objMap.get("total").toString());
			DecimalFormat df = new DecimalFormat("#,##0.00");
			viewHolder.yfTextView.setText("已付："+ FigureTools.sswrFigure(Double.parseDouble(objMap.get("totalrcvd").toString()))
					);
			viewHolder.wfTextView.setText("未付："+FigureTools.sswrFigure(Double.parseDouble(objMap.get("shouldpayamt").toString())));
			return convertView;
		} else {
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(
						R.layout.activity_jxc_cggl_cgfk_xzyy_item2, null);// 这个过程相当耗时间
				viewHolder2 = new ViewHolder2();
				viewHolder2.bcjsEditText = (EditText) convertView
						.findViewById(R.id.bcjs_edittext);


				viewHolder2.bcjsEditText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED| InputType.TYPE_NUMBER_FLAG_DECIMAL);


				viewHolder2.item2LinearLayout = (LinearLayout) convertView
						.findViewById(R.id.item2_linearlayout);
				convertView.setTag(viewHolder2);
			} else {
				viewHolder2 = (ViewHolder2) convertView.getTag();
			}
			final Map<String, Object> objMap2 = list.get(position - 1);
			if (objMap2.get("ischecked").toString().equals("0")) {
				viewHolder2.item2LinearLayout.setVisibility(View.GONE);
			} else {
				viewHolder2.item2LinearLayout.setVisibility(View.VISIBLE);

			}
			
			viewHolder2.bcjsEditText.addTextChangedListener(new TextWatcher() {
                
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    objMap.put("bcjs",arg0);
                }
                
                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }
                
                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });
			viewHolder2.bcjsEditText.setText(objMap.get("bcjs").toString());
			return convertView;
		}
	}

	static class ViewHolder {
		TextView mcTextView;
		TextView bhTextView;
		TextView rqTextView;
		TextView zeTextView;
		TextView yfTextView;
		TextView wfTextView;
		CheckBox itemCheckBox;
	}

	static class ViewHolder2 {
		EditText bcjsEditText;
		LinearLayout item2LinearLayout;
	}

}