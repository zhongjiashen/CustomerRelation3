package com.cr.adapter.jxc.ckgl.kcpd;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.SLView2;
import com.cr.activity.common.CommonXzphActivity;
import com.cr.activity.jxc.ckgl.kcpd.KtSerialNumberAddActivity;
import com.cr.myinterface.SLViewValueChange;
import com.cr.myinterface.SelectValueChange;
import com.cr.tools.ShareUserInfo;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.actiity.EnterSerialNumberActivity;
import com.update.actiity.installation.ChooseGoodsActivity;

public class JxcCkglKcpdXzspAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    List<Map<String, Object>> oldList;
    private BaseActivity activity;
    private int selectIndex;
    private String storeid;

    public JxcCkglKcpdXzspAdapter(List<Map<String, Object>> list,
                                  BaseActivity activity, String storeid) {
        this.list = list;
        this.activity = activity;
        this.storeid = storeid;
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
                    R.layout.activity_jxc_ckgl_kcpd_xzsp_item, null);// 这个过程相当耗时间
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
                    + objMap.get("onhand").toString()
                    + objMap.get("unitname").toString());
            return convertView;
        } else {
//			if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.activity_jxc_ckgl_kcpd_xzsp_item2, null);// 这个过程相当耗时间
            viewHolder2 = new ViewHolder2();
            viewHolder2.tvSerialNumber = convertView
                    .findViewById(R.id.tv_serial_number);
            viewHolder2.zmslEditText = (EditText) convertView
                    .findViewById(R.id.zmsl_edittext);
            viewHolder2.spslView = (SLView2) convertView
                    .findViewById(R.id.spsl_view);
            viewHolder2.ykslView = (SLView2) convertView
                    .findViewById(R.id.yksl_view);
            viewHolder2.item2LinearLayout = (LinearLayout) convertView
                    .findViewById(R.id.item2_linearlayout);
            viewHolder2.cpphEditText = (EditText) convertView
                    .findViewById(R.id.cpph_edittext);
            viewHolder2.scrqEditText = (EditText) convertView
                    .findViewById(R.id.scrq_edittext);
            viewHolder2.yxqzEditText = (EditText) convertView
                    .findViewById(R.id.yxqz_edittext);
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

//			viewHolder2.zmslView.setOnValueChange(new SLViewValueChange() {
//				@Override
//				public void onValueChange(int sl) {
//					objMap.put("zmsl", "" + sl);
//				}
//			});
            viewHolder2.spslView.setOnValueChange(new SLViewValueChange() {
                @Override
                public void onValueChange(double sl) {
                    objMap.put("spsl", "" + sl);
                }
            });
            viewHolder2.ykslView.setOnValueChange(new SLViewValueChange() {
                @Override
                public void onValueChange(double sl) {
                    objMap.put("yksl", "" + sl);
                }
            });
//			viewHolder2.zmslView.setSl(Integer.parseInt(objMap.get("zmsl")
//					.toString()));
            viewHolder2.zmslEditText.setText(objMap.get("zmsl").toString());
            viewHolder2.spslView.setSl((int) Double.parseDouble(objMap.get("spsl")
                    .toString()));
            viewHolder2.ykslView.setSl((int) Double.parseDouble(objMap.get("yksl")
                    .toString()));
            viewHolder2.scrqEditText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    SelectValueChange selectValueChange = new SelectValueChange() {
                        @Override
                        public void onValueChange(String str) {
                            objMap.put("scrq", str);
                            notifyDataSetChanged();
                        }
                    };
                    activity.date_init2(selectValueChange);
                }
            });
//			viewHolder2.yxqzEditText.setVisibility(View.GONE);
            viewHolder2.yxqzEditText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    SelectValueChange selectValueChange = new SelectValueChange() {
                        @Override
                        public void onValueChange(String str) {
                            objMap.put("yxqz", str);
                            notifyDataSetChanged();
                        }
                    };
                    activity.date_init2(selectValueChange);
                }
            });

            viewHolder2.tvSerialNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    activity.startActivityForResult(new Intent(activity, KtSerialNumberAddActivity.class)
                            .putExtra("itemno", "0")
                            .putExtra("uuid", objMap.get("serialinfo")
                                    .toString())
                            .putExtra("position", position)
                            .putExtra("DATA", new Gson().toJson(objMap.get("serials")
                                   )), 11);

                }
            });
//			viewHolder2.cpphEditText.setVisibility(View.GONE);
            viewHolder2.cpphEditText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent();
                    intent.setClass(activity, CommonXzphActivity.class);
                    intent.putExtra("goodsid", objMap2.get("goodsid")
                            .toString());
                    intent.putExtra("storied", storeid);
                    intent.putExtra("index", position);
                    ShareUserInfo.getKey(activity, "cpphType");
                    ShareUserInfo.setKey(activity, "cpphType", "kcpd");
                    activity.startActivityForResult(intent, 0);
                }
            });
            viewHolder2.cpphEditText.setText(objMap.get("cpph").toString());
            viewHolder2.scrqEditText.setText(objMap.get("scrq").toString());
            viewHolder2.yxqzEditText.setText(objMap.get("yxqz").toString());
            viewHolder2.cpphView = convertView.findViewById(R.id.cpph_view);
            viewHolder2.scrqView = convertView.findViewById(R.id.scrq_view);
            viewHolder2.cpphLayout = (LinearLayout) convertView.findViewById(R.id.cpph_layout);
            viewHolder2.scrqLayout = (LinearLayout) convertView.findViewById(R.id.scrq_layout);
            viewHolder2.yxqzLayout = (LinearLayout) convertView.findViewById(R.id.yxqz_layout);
            if (objMap.get("batchctrl").equals("T")) {
                viewHolder2.cpphLayout.setVisibility(View.VISIBLE);
                viewHolder2.scrqLayout.setVisibility(View.VISIBLE);
                viewHolder2.yxqzLayout.setVisibility(View.VISIBLE);
                viewHolder2.cpphView.setVisibility(View.VISIBLE);
                viewHolder2.scrqView.setVisibility(View.VISIBLE);

            } else {
                viewHolder2.cpphLayout.setVisibility(View.GONE);
                viewHolder2.scrqLayout.setVisibility(View.GONE);
                viewHolder2.yxqzLayout.setVisibility(View.GONE);
                viewHolder2.cpphView.setVisibility(View.GONE);
                viewHolder2.scrqView.setVisibility(View.GONE);
            }

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
        EditText zmslEditText;
        SLView2 spslView;
        SLView2 ykslView;
        EditText cpphEditText;
        EditText scrqEditText;
        EditText yxqzEditText;
        LinearLayout cpphLayout;
        LinearLayout scrqLayout;
        LinearLayout yxqzLayout;
        View cpphView;
        View scrqView;
        LinearLayout item2LinearLayout;
        TextView tvSerialNumber;
    }

}