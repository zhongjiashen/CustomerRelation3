package com.cr.adapter.jxc.cggl.cgdd;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.text.Editable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.cr.activity.SLView2;
import com.cr.activity.common.CommonXzphActivity;
import com.cr.activity.tjfx.kcbb.TjfxKcbbSpjg2Activity;
import com.cr.myinterface.SLViewValueChange;
import com.cr.myinterface.SelectValueChange;
import com.crcxj.activity.R;
import com.update.utils.LogUtils;

public class JxcCgglCgddXzspAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    List<Map<String, Object>> oldList;
    private BaseActivity activity;
    private int selectIndex;

    private String storeid;
    private boolean bz_isTrue;

    public JxcCgglCgddXzspAdapter(List<Map<String, Object>> list,
                                  BaseActivity activity, String storeid) {
        this.list = list;
        this.activity = activity;
        this.storeid = storeid;
        bz_isTrue = false;
    }

    public JxcCgglCgddXzspAdapter(List<Map<String, Object>> list,
                                  BaseActivity activity, String storeid, boolean bz_isTrue) {
        this.list = list;
        this.activity = activity;
        this.storeid = storeid;
        this.bz_isTrue = bz_isTrue;
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
        LogUtils.e("的说法发送");
        final Map<String, Object> objMap = list.get(position);
        if (viewType == 0) {
            // if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.activity_jxc_cggl_cgdd_xzsp_item, null);// 这个过程相当耗时间
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
                    R.layout.activity_jxc_cggl_cgdd_xzsp_item2, null);// 这个过程相当耗时间
            viewHolder2 = new ViewHolder2();
            viewHolder2.djEditText = (EditText) convertView
                    .findViewById(R.id.dj_edittext);
            viewHolder2.etBz = (EditText) convertView
                    .findViewById(R.id.et_bz);
            viewHolder2.xzdjImageView = (ImageView) convertView.findViewById(R.id.xzjg_iv);
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

            viewHolder2.cpphLayout = (LinearLayout) convertView.findViewById(R.id.cpph_layout);
            viewHolder2.scrqLayout = (LinearLayout) convertView.findViewById(R.id.scrq_layout);
            viewHolder2.yxqzLayout = (LinearLayout) convertView.findViewById(R.id.yxqz_layout);
            viewHolder2.llBz = (LinearLayout) convertView.findViewById(R.id.ll_bz);
            viewHolder2.cpphView = convertView.findViewById(R.id.cpph_view);
            viewHolder2.scrqView = convertView.findViewById(R.id.scrq_view);
            viewHolder2.etBz.setText(objMap.get("memo")==null?"":objMap.get("memo").toString());
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
            if (bz_isTrue) {
                LogUtils.e("显示备注");
                viewHolder2.llBz.setVisibility(View.VISIBLE);
            } else {
                LogUtils.e("隐藏备注");
                viewHolder2.llBz.setVisibility(View.GONE);
            }
            viewHolder2.xzdjImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent();
                    intent.setClass(activity, TjfxKcbbSpjg2Activity.class);
                    intent.putExtra("goodsid", objMap2.get("goodsid").toString());
                    intent.putExtra("storied", storeid);
                    intent.putExtra("unitid", objMap2.get("unitid").toString());
                    intent.putExtra("clientid", "0");
                    intent.putExtra("index", position + "");
                    activity.startActivityForResult(intent, 3);
                }
            });
            viewHolder2.etBz.setTag(position);
            viewHolder2.etBz.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                    objMap.put("memo", arg0.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    //关键点：1.给edittext设置tag，此tag用来与position做对比校验，验证当前选中的edittext是否为需要的控件;
//                            2.焦点判断：只有当前有焦点的edittext才有更改数据的权限，否则会造成数据紊乱
//                            3.edittext内数据变动直接直接更改datalist的数据值，以便滑动view时显示正确

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
            viewHolder2.slView.setOnValueChange(new SLViewValueChange() {
                @Override
                public void onValueChange(double sl) {
                    objMap.put("sl", "" + sl);
                }
            });
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

            viewHolder2.cpphEditText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent();
                    intent.setClass(activity, CommonXzphActivity.class);
                    intent.putExtra("goodsid", objMap2.get("goodsid")
                            .toString());
                    intent.putExtra("storied", storeid);
                    intent.putExtra("index", position);
                    activity.startActivityForResult(intent, 0);
                }
            });

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
            viewHolder2.djEditText.setText(objMap.get("dj").toString());
            viewHolder2.zklEditText.setText(objMap.get("zkl").toString());
            viewHolder2.slView.setSl(Double.parseDouble(objMap.get("sl")
                    .toString()));
            viewHolder2.cpphEditText.setText(objMap.get("cpph").toString());
            viewHolder2.scrqEditText.setText(objMap.get("scrq").toString());
            viewHolder2.yxqzEditText.setText(objMap.get("yxqz").toString());
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
        EditText etBz;
        EditText djEditText;
        ImageView xzdjImageView;
        EditText zklEditText;
        SLView2 slView;
        EditText cpphEditText;
        //		EditText cpph2EditText;
        EditText scrqEditText;
        EditText yxqzEditText;
        LinearLayout item2LinearLayout;
        LinearLayout llBz;
        LinearLayout cpphLayout;
        LinearLayout scrqLayout;
        LinearLayout yxqzLayout;
        View cpphView;
        View scrqView;
    }

}