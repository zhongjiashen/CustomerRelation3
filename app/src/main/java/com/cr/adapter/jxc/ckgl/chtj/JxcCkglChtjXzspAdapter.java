package com.cr.adapter.jxc.ckgl.chtj;

import java.util.List;
import java.util.Map;

import android.text.Editable;
import android.text.TextUtils;
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
import com.cr.activity.SLView2;
import com.cr.myinterface.SLViewValueChange;
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;
import com.update.utils.LogUtils;

public class JxcCkglChtjXzspAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    List<Map<String, Object>> oldList;
    private BaseActivity activity;
    private int selectIndex;

    public JxcCkglChtjXzspAdapter(List<Map<String, Object>> list,
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
                    R.layout.activity_jxc_ckgl_chtj_xzsp_item, null);// 这个过程相当耗时间
            viewHolder = new ViewHolder();
            viewHolder.mcTextView = (TextView) convertView
                    .findViewById(R.id.mc_textview);
            viewHolder.bhTextView = (TextView) convertView
                    .findViewById(R.id.bh_textview);
            viewHolder.ggTextView = (TextView) convertView
                    .findViewById(R.id.gg_textview);
            viewHolder.xhTextView = (TextView) convertView
                    .findViewById(R.id.xh_textview);
            viewHolder.kcTextView = (TextView) convertView
                    .findViewById(R.id.kc_textview);
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
                    .setText("名称：" + objMap.get("name").toString());
            viewHolder.bhTextView
                    .setText("编号：" + objMap.get("code").toString());
            viewHolder.ggTextView.setText("规格："
                    + objMap.get("specs").toString());
            viewHolder.xhTextView.setText("型号："
                    + objMap.get("model").toString());
            viewHolder.kcTextView.setText("库存："
                    + FigureTools.sswrFigure(objMap.get("onhand").toString())
                    + objMap.get("unitname").toString());
            return convertView;
        } else {
//			if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.activity_jxc_ckgl_chtj_xzsp_item2, null);// 这个过程相当耗时间
            viewHolder2 = new ViewHolder2();
            viewHolder2.dwEditText = (EditText) convertView
                    .findViewById(R.id.dj_edittext);
            viewHolder2.slView = (SLView2) convertView
                    .findViewById(R.id.sl_view);
            viewHolder2.item2LinearLayout = (LinearLayout) convertView
                    .findViewById(R.id.item2_linearlayout);
            viewHolder2.tqdjEditText = (EditText) convertView.findViewById(R.id.tqdj_edittext);
            viewHolder2.thdjEditText = (EditText) convertView.findViewById(R.id.thdj_edittext);
            viewHolder2.etBz = convertView
                    .findViewById(R.id.et_bz);
            convertView.setTag(viewHolder2);
//			} else {
//				viewHolder2 = (ViewHolder2) convertView.getTag();
//			}
            final Map<String, Object> objMap2 = list.get(position - 1);
            if (objMap2.get("ischecked").toString().equals("0")) {
                viewHolder2.item2LinearLayout.setVisibility(View.GONE);
            } else {
                viewHolder2.item2LinearLayout.setVisibility(View.VISIBLE);
            }
            viewHolder2.etBz.setText(objMap.get("memo").toString());//设置备注
            viewHolder2.etBz.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {
                        objMap.put("memo", s.toString());

                    }
                }
            });
            viewHolder2.slView.setOnValueChange(new SLViewValueChange() {
                @Override
                public void onValueChange(double sl) {
                    LogUtils.e(sl+"");
                    objMap.put("sl", FigureTools.sswrFigure(sl));
                }
            });
//			viewHolder2.djEditText.setText(objMap.get("dj").toString());
//			viewHolder2.zklEditText.setText(objMap.get("zkl").toString());
            LogUtils.e(FigureTools.sswrFigure(objMap.get("sl").toString()));
            LogUtils.e(Double.parseDouble(FigureTools.sswrFigure(objMap.get("sl").toString())) + "");
            viewHolder2.slView.setSl(Double.parseDouble(FigureTools.sswrFigure(objMap.get("sl")
                    .toString())));
            viewHolder2.tqdjEditText.setText(objMap.get("tqdj").toString());
            viewHolder2.tqdjEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    objMap.put("tqdj", arg0.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {

                }
            });
            viewHolder2.thdjEditText.setText(objMap.get("thdj").toString());
            viewHolder2.thdjEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    objMap.put("thdj", arg0.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        TextView mcTextView;
        TextView bhTextView;
        TextView ggTextView;
        TextView xhTextView;
        TextView kcTextView;
        CheckBox itemCheckBox;
    }

    static class ViewHolder2 {
        EditText dwEditText;
        SLView2 slView;
        EditText tqdjEditText;
        EditText thdjEditText;
        EditText etBz;
        LinearLayout item2LinearLayout;
    }

}