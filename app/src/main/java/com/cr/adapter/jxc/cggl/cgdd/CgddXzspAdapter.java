package com.cr.adapter.jxc.cggl.cgdd;

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
import com.cr.tools.FigureTools;
import com.crcxj.activity.R;
import com.update.utils.LogUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CgddXzspAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    List<Map<String, Object>> oldList;
    private BaseActivity activity;
    private int selectIndex;

    private String storeid;
    private boolean bz_isTrue;
    private String type;

    public CgddXzspAdapter(List<Map<String, Object>> list,
                           BaseActivity activity, String storeid) {
        this.list = list;
        this.activity = activity;
        this.storeid = storeid;
        bz_isTrue = false;
    }

    public CgddXzspAdapter(List<Map<String, Object>> list,
                           BaseActivity activity, String storeid, boolean bz_isTrue, String type) {
        this.list = list;
        this.activity = activity;
        this.storeid = storeid;
        this.bz_isTrue = bz_isTrue;
        this.type = type;
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
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
            viewHolder.itemcheck
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
                viewHolder.itemcheck.setChecked(false);
            } else {
                viewHolder.itemcheck.setChecked(true);
            }
            viewHolder.mcTextview
                    .setText("名称：" + objMap.get("name").toString());
            viewHolder.bhTextview
                    .setText("编号：" + objMap.get("code").toString());
            viewHolder.ggTextview.setText("规格："
                    + objMap.get("specs").toString());
            viewHolder.xhTextview.setText("型号："
                    + objMap.get("model").toString());
            viewHolder.kcTextview.setText("库存："
                    + FigureTools.sswrFigure(objMap.get("onhand").toString())
                    + objMap.get("unitname").toString());
            return convertView;
        } else {
//			if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.activity_jxc_cggl_cgdd_xzsp_item2, null);// 这个过程相当耗时间
            viewHolder2 = new ViewHolder2(convertView);

            viewHolder2.etBz.setText(objMap.get("memo") == null ? "" : objMap.get("memo").toString());
            convertView.setTag(viewHolder2);
//			} else {
//				viewHolder2 = (ViewHolder2) convertView.getTag();
//			}
            final Map<String, Object> objMap2 = list.get(position - 1);
            if (objMap2.get("ischecked").toString().equals("0")) {
                viewHolder2.item2Linearlayout.setVisibility(View.GONE);
            } else {
                viewHolder2.item2Linearlayout.setVisibility(View.VISIBLE);
            }
            if (bz_isTrue) {
                LogUtils.e("显示备注");
                viewHolder2.llBz.setVisibility(View.VISIBLE);
            } else {
                LogUtils.e("隐藏备注");
                viewHolder2.llBz.setVisibility(View.GONE);
            }
            viewHolder2.xzjgIv.setOnClickListener(new OnClickListener() {
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
            viewHolder2.djEdittext.addTextChangedListener(new TextWatcher() {

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
            viewHolder2.zklEdittext.addTextChangedListener(new TextWatcher() {

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
            viewHolder2.scrqEdittext.setOnClickListener(new OnClickListener() {
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
            viewHolder2.yxqzEdittext.setOnClickListener(new OnClickListener() {
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

            viewHolder2.cpphEdittext.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent();
                    intent.setClass(activity, CommonXzphActivity.class);
                    intent.putExtra("goodsid", objMap2.get("goodsid")
                            .toString());
                    intent.putExtra("storied", storeid);
                    intent.putExtra("index", position);
                    intent.putExtra("type", type);
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
            viewHolder2.djEdittext.setText(objMap.get("dj").toString());
            viewHolder2.zklEdittext.setText(objMap.get("zkl").toString());
            viewHolder2.slView.setSl(Double.parseDouble(objMap.get("sl")
                    .toString()));
            viewHolder2.cpphEdittext.setText(objMap.get("cpph").toString());
            viewHolder2.scrqEdittext.setText(objMap.get("scrq").toString());
            viewHolder2.yxqzEdittext.setText(objMap.get("yxqz").toString());
            return convertView;
        }
    }



 /*   static class ViewHolder {
        TextView mcTextView;
        TextView bhTextView;
        TextView ggTextView;
        TextView xhTextView;
        TextView kcTextView;
        CheckBox itemCheckBox;
    }*/

//    static class ViewHolder2 {
//        EditText etBz;
//        EditText djEditText;
//        ImageView xzdjImageView;
//        EditText zklEditText;
//        SLView2 slView;
//        EditText cpphEditText;
//        //		EditText cpph2EditText;
//        EditText scrqEditText;
//        EditText yxqzEditText;
//        LinearLayout item2LinearLayout;
//        LinearLayout llBz;
//        LinearLayout cpphLayout;
//        LinearLayout scrqLayout;
//        LinearLayout yxqzLayout;
//        View cpphView;
//        View scrqView;
//    }



    static class ViewHolder {
        @BindView(R.id.mc_textview)
        TextView mcTextview;
        @BindView(R.id.bh_textview)
        TextView bhTextview;
        @BindView(R.id.gg_textview)
        TextView ggTextview;
        @BindView(R.id.xh_textview)
        TextView xhTextview;
        @BindView(R.id.kc_textview)
        TextView kcTextview;
        @BindView(R.id.itemcheck)
        CheckBox itemcheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 {
        @BindView(R.id.zkl_edittext)
        EditText zklEdittext;
        @BindView(R.id.sl_view)
        SLView2 slView;
        @BindView(R.id.et_bz)
        EditText etBz;
        @BindView(R.id.ll_bz)
        LinearLayout llBz;
        @BindView(R.id.cpph_edittext)
        EditText cpphEdittext;
        @BindView(R.id.cpph_layout)
        LinearLayout cpphLayout;
        @BindView(R.id.cpph_view)
        View cpphView;
        @BindView(R.id.scrq_edittext)
        EditText scrqEdittext;
        @BindView(R.id.scrq_layout)
        LinearLayout scrqLayout;
        @BindView(R.id.scrq_view)
        View scrqView;
        @BindView(R.id.yxqz_edittext)
        EditText yxqzEdittext;
        @BindView(R.id.yxqz_layout)
        LinearLayout yxqzLayout;
        @BindView(R.id.dj_edittext)
        EditText djEdittext;
        @BindView(R.id.xzjg_iv)
        ImageView xzjgIv;
        @BindView(R.id.et_sl)
        EditText etSl;
        @BindView(R.id.tv_hsdj)
        TextView tvHsdj;
        @BindView(R.id.item2_linearlayout)
        LinearLayout item2Linearlayout;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }
}