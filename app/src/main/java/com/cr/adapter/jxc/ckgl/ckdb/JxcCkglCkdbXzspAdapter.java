package com.cr.adapter.jxc.ckgl.ckdb;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.cr.activity.common.CommonXzphActivity;
import com.cr.activity.jxc.JxcTjXlhActivity;
import com.cr.activity.jxc.XzXlhActivity;
import com.cr.activity.jxc.ckgl.kcpd.KtSerialNumberAddActivity;
import com.cr.myinterface.SLViewValueChange;
import com.cr.myinterface.SelectValueChange;
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;
import com.google.gson.Gson;
import com.update.utils.LogUtils;

import butterknife.BindView;

public class JxcCkglCkdbXzspAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    List<Map<String, Object>> oldList;
    private BaseActivity activity;
    private int selectIndex;
    private String storeid;

    public JxcCkglCkdbXzspAdapter(List<Map<String, Object>> list,
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
                    R.layout.activity_jxc_ckgl_ckdb_xzsp_item, null);// 这个过程相当耗时间
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
                    R.layout.activity_jxc_ckgl_ckdb_xzsp_item2, null);// 这个过程相当耗时间
            viewHolder2 = new ViewHolder2();
            viewHolder2.tvSerialNumber = convertView
                    .findViewById(R.id.tv_serial_number);
            viewHolder2.djEditText = (EditText) convertView
                    .findViewById(R.id.dj_edittext);
            viewHolder2.zklEditText = (EditText) convertView
                    .findViewById(R.id.zkl_edittext);
            viewHolder2.slView = (SLView2) convertView
                    .findViewById(R.id.sl_view);
            viewHolder2.cpphEditText = (EditText) convertView
                    .findViewById(R.id.cpph_edittext);
            viewHolder2.scrqEditText = (EditText) convertView
                    .findViewById(R.id.scrq_edittext);
            viewHolder2.yxqzEditText = (EditText) convertView
                    .findViewById(R.id.yxqz_edittext);
            viewHolder2.item2LinearLayout = (LinearLayout) convertView
                    .findViewById(R.id.item2_linearlayout);
            viewHolder2.tvSl = (TextView) convertView
                    .findViewById(R.id.tv_sl);
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

            viewHolder2.djEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                    objMap.put("dj", arg0.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });
            viewHolder2.zklEditText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                    objMap.put("zkl", arg0.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });
            viewHolder2.zklEditText.setVisibility(View.GONE);
            viewHolder2.slView.setSl(Double.parseDouble(objMap.get("sl")
                    .toString()));
            viewHolder2.tvSl.setText(objMap.get("sl")
                    .toString());
            if (objMap2.get("serialctrl").equals("T")) {
                LogUtils.e("严格序列商品");
                viewHolder2.slView.setVisibility(View.GONE);
                viewHolder2.tvSl.setVisibility(View.VISIBLE);
            } else {
                viewHolder2.slView.setVisibility(View.VISIBLE);
                viewHolder2.tvSl.setVisibility(View.GONE);
            }
            viewHolder2.slView.setOnValueChange(new SLViewValueChange() {
                @Override
                public void onValueChange(double sl) {
                    objMap.put("sl", "" + sl);
                }
            });

//			viewHolder2.scrqEditText.setVisibility(View.GONE);
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
//			viewHolder2.cpphEditText.setVisibility(View.GONE);
            viewHolder2.cpphEditText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent();
                    intent.setClass(activity, CommonXzphActivity.class);
                    intent.putExtra("goodsid", objMap2.get("goodsid")
                            .toString());
                    intent.putExtra("storeid", storeid);
                    intent.putExtra("index", position);
                    activity.startActivityForResult(intent, 0);
                }
            });
            viewHolder2.tvSerialNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("billid", "0");
                    intent.putExtra("serialinfo",  objMap.get("serialinfo")
                            .toString());
                    intent.putExtra("serials", new Gson().toJson(objMap.get("serials")));
                    if (objMap2.get("serialctrl").equals("T")) {
                        LogUtils.e("严格序列商品");
                        activity.startActivityForResult(intent.setClass( activity, XzXlhActivity.class)
                                .putExtra("parms", "CKDB")
                                .putExtra("storeid", storeid)
                                .putExtra("goodsid",  objMap2.get("goodsid")
                                        .toString())
                                .putExtra("refertype", "0")
                                .putExtra("referitemno", "0"), 11);

                    } else {
                        activity.startActivityForResult(intent.setClass(activity, JxcTjXlhActivity.class),11);
                    }

//                    activity.startActivityForResult(new Intent(activity, KtSerialNumberAddActivity.class)
//                            .putExtra("itemno", "0")
//                            .putExtra("uuid", objMap.get("serialinfo")
//                                    .toString())
//                            .putExtra("position", position)
//
//
//                            .putExtra("storied", storeid)
//                            .putExtra("goodsid", objMap2.get("goodsid")
//                                    .toString())
//                            .putExtra("isStrict", objMap.get("serialctrl").toString().equals("T"))
//                            .putExtra("DATA", new Gson().toJson(objMap.get("serials")
//                            )), 11);

                }
            });
            viewHolder2.djEditText.setText(objMap.get("dj").toString());
            viewHolder2.zklEditText.setText(objMap.get("zkl").toString());

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
        EditText djEditText;
        EditText zklEditText;
        SLView2 slView;
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

        TextView tvSl;

        EditText etBz;
    }

}