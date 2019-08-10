package com.cr.adapter.jxc.cggl.cgdd;

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
import com.update.utils.EditTextHelper;
import com.update.utils.LogUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JxcCgglCgddXzsp2Adapter extends BaseAdapter {

    List<Map<String, Object>> list;
    List<Map<String, Object>> oldList;
    private BaseActivity activity;
    private int selectIndex;
    private String type;//单据名称
    private String storeid;

    public JxcCgglCgddXzsp2Adapter(List<Map<String, Object>> list,
                                   BaseActivity activity, String storeid) {
        this.list = list;
        this.activity = activity;
        this.storeid = storeid;
        type = "";
    }

    public JxcCgglCgddXzsp2Adapter(List<Map<String, Object>> list,
                                   BaseActivity activity, String storeid, String type) {
        this.list = list;
        this.activity = activity;
        this.storeid = storeid;
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
            final ViewHolder2 viewHolder2 = new ViewHolder2(convertView);
            viewHolder2.tvSerialNumber.setVisibility(View.GONE);
            final Map<String, Object> objMap2 = list.get(position - 1);
            if (objMap2.get("ischecked").toString().equals("0")) {
                viewHolder2.item2Linearlayout.setVisibility(View.GONE);
            } else {
                viewHolder2.item2Linearlayout.setVisibility(View.VISIBLE);
            }


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
//					activity.x
                }
            });
            if (objMap.get("batchctrl").equals("T")) {
                viewHolder2.cpphLayout.setVisibility(View.VISIBLE);
                viewHolder2.cpphView.setVisibility(View.VISIBLE);
                viewHolder2.cpphEdittext.setText(objMap.get("cpph").toString());


                viewHolder2.scrqLayout.setVisibility(View.VISIBLE);
                viewHolder2.scrqView.setVisibility(View.VISIBLE);
                viewHolder2.scrqEdittext.setText(objMap.get("scrq").toString());


                viewHolder2.yxqzLayout.setVisibility(View.VISIBLE);
                viewHolder2.yxqzEdittext.setText(objMap.get("yxqz").toString());

            } else {
                viewHolder2.cpphLayout.setVisibility(View.GONE);
                viewHolder2.scrqLayout.setVisibility(View.GONE);
                viewHolder2.yxqzLayout.setVisibility(View.GONE);
                viewHolder2.cpphView.setVisibility(View.GONE);
                viewHolder2.scrqView.setVisibility(View.GONE);
            }
//			if (objMap2.get("serialctrl").equals("T")) {
//				LogUtils.e("严格序列商品");
//				viewHolder2.slView.setVisibility(View.GONE);
//				viewHolder2.tvSl.setVisibility(View.VISIBLE);
//			} else {
//				viewHolder2.slView.setVisibility(View.VISIBLE);
//				viewHolder2.tvSl.setVisibility(View.GONE);
//			}
            viewHolder2.djEdittext.setText(objMap.get("dj").toString());
            viewHolder2.zklEdittext.setText(objMap.get("zkl").toString());
            viewHolder2.slView.setSl(Double.parseDouble(objMap.get("sl")
                    .toString()));
            viewHolder2.djEdittext.addTextChangedListener(new TextWatcher() {

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
                    LogUtils.e(objMap.get("dj").toString());
                    if (!TextUtils.isEmpty(s)) {
                        objMap.put("dj", s.toString());
                        LogUtils.e(objMap.get("dj").toString());
                        Double csje = Double.parseDouble(s.toString()) * (Double.parseDouble(objMap.get("taxrate").toString()) + 100) / 100;
                        objMap.put("taxunitprice", FigureTools.sswrFigure(csje));
                        viewHolder2.tvHsdj.setText(objMap.get("taxunitprice").toString());

                    }
                    LogUtils.e(objMap.get("dj").toString());
                }
            });
            //判断发票类型是否是收据
            EditTextHelper.EditTextEnable(!(boolean) objMap.get("issj"), viewHolder2.etSl);
            viewHolder2.etSl.setText(objMap.get("taxrate").toString());//设置税率
            viewHolder2.etSl.addTextChangedListener(new TextWatcher() {

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
                        objMap.put("taxrate", s.toString());
                        Double csje = Double.parseDouble(objMap.get("dj").toString()) * (Double.parseDouble(s.toString()) + 100) / 100;
                        objMap.put("taxunitprice", FigureTools.sswrFigure(csje));
                        viewHolder2.tvHsdj.setText(objMap.get("taxunitprice").toString());
                    }

                }
            });


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


            LogUtils.e(objMap.get("dj").toString());
            Double csje = Double.parseDouble(objMap.get("dj").toString()) * (Double.parseDouble(objMap.get("taxrate").toString()) + 100) / 100;
            objMap.put("taxunitprice", FigureTools.sswrFigure(csje));
            viewHolder2.tvHsdj.setText(objMap.get("taxunitprice").toString());

            return convertView;
        }
    }


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
        @BindView(R.id.tv_serial_number)
        TextView tvSerialNumber;
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
        @BindView(R.id.tv_sl)
        TextView tvSl;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }


}