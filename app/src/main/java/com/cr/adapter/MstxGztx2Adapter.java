package com.cr.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cr.tools.FigureTools;
import com.crcxj.activity.R;

public class MstxGztx2Adapter extends BaseAdapter {
//	private static final String TAG = "PpPpAdapter";
//	private AsynImageLoader asynImageLoader;

	List<Map<String, Object>> list;
	private Activity activity;
	private String type="";

	public MstxGztx2Adapter(List<Map<String, Object>> list, Activity activity) {
//		this.mCount = count;
		this.list=list;
//		mImageLoader = new ImageLoader();
//		asynImageLoader = new AsynImageLoader(true,activity,false);
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Map<String, Object> map=list.get(position);
		if(type.equals("0")){//内部公告
			ViewHolderNbgg viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_nbgg_item, null);// 这个过程相当耗时间
				viewHolder = new ViewHolderNbgg();
				viewHolder.titleView = (TextView) convertView.findViewById(R.id.title_textview);
				viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderNbgg) convertView.getTag();
			}
			viewHolder.titleView.setText(map.get("title").toString());
		}else if(type.equals("1")){//客户生日、员工生日
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_item, null);// 这个过程相当耗时间
				viewHolder = new ViewHolder();
				viewHolder.typeView = (TextView) convertView.findViewById(R.id.type_textview);
				viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
				viewHolder.nameView = (TextView) convertView.findViewById(R.id.name_textview);
				viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if(map.get("type").equals("0")){
				viewHolder.typeView.setVisibility(View.GONE);
			}else{
				viewHolder.typeView.setVisibility(View.VISIBLE);
			}
			viewHolder.typeView.setText(map.get("title").toString());
			if(map.get("cname")==null){
				viewHolder.dwView.setVisibility(View.GONE);
			}else{
				viewHolder.dwView.setText(map.get("cname").toString());
				viewHolder.dwView.setVisibility(View.VISIBLE);
			}
			viewHolder.nameView.setText(map.get("lxrname").toString()+"    生日："+map.get("rq").toString());
			if(map.get("typecode").toString().equals("GQSP")){
	            viewHolder.xybJtImageView.setVisibility(View.INVISIBLE);
	        }else{
	            viewHolder.xybJtImageView.setVisibility(View.VISIBLE);
	        }
		}else if(type.equals("2")){//渠道提醒
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_qdtx_item, null);// 这个过程相当耗时间
				viewHolder = new ViewHolder();
				viewHolder.typeView = (TextView) convertView.findViewById(R.id.type_textview);
				viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
				viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if(map.get("type").equals("0")){
				viewHolder.typeView.setVisibility(View.GONE);
			}else{
				viewHolder.typeView.setVisibility(View.VISIBLE);
			}
			viewHolder.typeView.setText(map.get("title").toString());
			viewHolder.dwView.setText(map.get("cname").toString());
		}else if(type.equals("3")){//售后回访
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_qdtx_item, null);// 这个过程相当耗时间
				viewHolder = new ViewHolder();
				viewHolder.typeView = (TextView) convertView.findViewById(R.id.type_textview);
				viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
				viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if(map.get("type").equals("0")){
				viewHolder.typeView.setVisibility(View.GONE);
			}else{
				viewHolder.typeView.setVisibility(View.VISIBLE);
			}
			viewHolder.typeView.setText(map.get("title").toString());
			viewHolder.dwView.setText(map.get("cname").toString());
		}else if(type.equals("4")){//超期应收款
			ViewHolderDjlx viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_djlx_item, null);// 这个过程相当耗时间
				viewHolder = new ViewHolderDjlx();
				viewHolder.djlxView = (TextView) convertView.findViewById(R.id.djlx_textview);
				viewHolder.djbhView = (TextView) convertView.findViewById(R.id.djbh_textview);
				viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
				viewHolder.skjeView = (TextView) convertView.findViewById(R.id.skje_textview);
				viewHolder.skje2View = (TextView) convertView.findViewById(R.id.skje2_textview);
				viewHolder.skrqView = (TextView) convertView.findViewById(R.id.skrq_textview);
				viewHolder.skrq2View = (TextView) convertView.findViewById(R.id.skrq2_textview);
				viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderDjlx) convertView.getTag();
			}
			viewHolder.djlxView.setText("单据类型："+map.get("billtypename").toString());
			viewHolder.djbhView.setText("单据编号："+map.get("code").toString());
			viewHolder.dwView.setText(map.get("cname").toString());
			viewHolder.skjeView.setText("收款金额：");
			viewHolder.skje2View.setText("￥"+FigureTools.sswrFigure(map.get("je").toString()));
			viewHolder.skrqView.setText("限定日期：");
			viewHolder.skrq2View.setText(map.get("rq").toString());
		}else if(type.equals("5")){//超期订单
			ViewHolderDjlx viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_djlx_item, null);// 这个过程相当耗时间
				viewHolder = new ViewHolderDjlx();
				viewHolder.djlxView = (TextView) convertView.findViewById(R.id.djlx_textview);
				viewHolder.djbhView = (TextView) convertView.findViewById(R.id.djbh_textview);
				viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
				viewHolder.skjeView = (TextView) convertView.findViewById(R.id.skje_textview);
				viewHolder.skje2View = (TextView) convertView.findViewById(R.id.skje2_textview);
				viewHolder.skrqView = (TextView) convertView.findViewById(R.id.skrq_textview);
				viewHolder.skrq2View = (TextView) convertView.findViewById(R.id.skrq2_textview);
				viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderDjlx) convertView.getTag();
			}
			viewHolder.djlxView.setText("单据类型："+map.get("billtypename").toString());
            viewHolder.djbhView.setText("单据编号："+map.get("code").toString());
			viewHolder.dwView.setText(map.get("cname").toString());
			viewHolder.skjeView.setText("开单日期:");
			viewHolder.skje2View.setText(map.get("rq").toString());
			viewHolder.skrqView.setText("预定完成日期：");
			viewHolder.skrq2View.setText(map.get("wcrq").toString());
		}else if(type.equals("6")){//过期商品
			ViewHolderGqsp viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_gqsp_item, null);// 这个过程相当耗时间
				viewHolder = new ViewHolderGqsp();
				viewHolder.djlxView = (TextView) convertView.findViewById(R.id.djlx_textview);
				viewHolder.djbhView = (TextView) convertView.findViewById(R.id.djbh_textview);
				viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
				viewHolder.skjeView = (TextView) convertView.findViewById(R.id.skje_textview);
				viewHolder.skje2View = (TextView) convertView.findViewById(R.id.skje2_textview);
				viewHolder.skrqView = (TextView) convertView.findViewById(R.id.skrq_textview);
				viewHolder.skrq2View = (TextView) convertView.findViewById(R.id.skrq2_textview);
				viewHolder.kcslView = (TextView) convertView.findViewById(R.id.kcsl_textview);
				viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderGqsp) convertView.getTag();
			}
			viewHolder.djlxView.setText("商品编码："+map.get("code").toString());
			viewHolder.djbhView.setText("商品名称："+map.get("name").toString());
			viewHolder.dwView.setText("批号："+map.get("batchcode").toString());
			viewHolder.skjeView.setText("生成日期:"+map.get("crq").toString());
			viewHolder.skje2View.setText("有效日期:"+map.get("yxrq").toString());
			viewHolder.skrqView.setText("仓库："+map.get("storename").toString());
			viewHolder.kcslView.setText("库存数量："+map.get("onhand").toString());
		}else if (type.equals("10")){  //库存报警
			ViewHolderKcbj viewHolder = null;
			if(convertView==null){
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_kcbj_item,null);
				viewHolder = new ViewHolderKcbj();
				viewHolder.djlxView = (TextView)convertView.findViewById(R.id.djlx_textview);
				viewHolder.djbhView =(TextView)convertView.findViewById(R.id.djbh_textview);
				viewHolder.spmcView =(TextView)convertView.findViewById(R.id.spmc_textview);
				viewHolder.spggView =(TextView)convertView.findViewById(R.id.spgg_textview);
				viewHolder.xxkcView =(TextView)convertView.findViewById(R.id.xxkc_textview);
				viewHolder.dqkcView =(TextView)convertView.findViewById(R.id.dqkc_textview);
				viewHolder.sxkcView =(TextView)convertView.findViewById(R.id.sxkc_textview);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolderKcbj)convertView.getTag();
			}
			viewHolder.djbhView.setText("商品编码："+map.get("code").toString());
			viewHolder.spmcView.setText("商品名称："+map.get("name").toString());
			viewHolder.spggView.setText("规格："+map.get("specs").toString());
			viewHolder.xxkcView.setText("库存下限: "+map.get("downonhand").toString());
			viewHolder.dqkcView.setText("当前库存: "+map.get("onhand").toString());
			viewHolder.sxkcView.setText("库存上限："+map.get("uponhand").toString());

		} else if(type.equals("7")){//预约拜访
		    ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_item, null);// 这个过程相当耗时间
                viewHolder = new ViewHolder();
                viewHolder.typeView = (TextView) convertView.findViewById(R.id.type_textview);
                viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
                viewHolder.nameView = (TextView) convertView.findViewById(R.id.name_textview);
                viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.typeView.setTextColor(Color.parseColor("#000000"));
            viewHolder.typeView.setText("预约时间："+map.get("rq").toString());
            viewHolder.dwView.setText("单位名称："+map.get("cname").toString());
            viewHolder.nameView.setText("联系人名称："+map.get("lxrname").toString());
		}else if(type.equals("8")){//实施项目
		    ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_item, null);// 这个过程相当耗时间
                viewHolder = new ViewHolder();
                viewHolder.typeView = (TextView) convertView.findViewById(R.id.type_textview);
                viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
                viewHolder.nameView = (TextView) convertView.findViewById(R.id.name_textview);
                viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.typeView.setTextColor(Color.parseColor("#000000"));
            viewHolder.typeView.setText(map.get("cname").toString());
            viewHolder.dwView.setText("项目名称："+map.get("title").toString());
            viewHolder.nameView.setText("日期："+map.get("rq").toString());
		}else if(type.equals("9")){//超期应付款
			ViewHolderDjlx viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(activity).inflate(R.layout.activity_mstx_gztx2_djlx_item, null);// 这个过程相当耗时间
				viewHolder = new ViewHolderDjlx();
				viewHolder.djlxView = (TextView) convertView.findViewById(R.id.djlx_textview);
				viewHolder.djbhView = (TextView) convertView.findViewById(R.id.djbh_textview);
				viewHolder.dwView = (TextView) convertView.findViewById(R.id.dw_textview);
				viewHolder.skjeView = (TextView) convertView.findViewById(R.id.skje_textview);
				viewHolder.skje2View = (TextView) convertView.findViewById(R.id.skje2_textview);
				viewHolder.skrqView = (TextView) convertView.findViewById(R.id.skrq_textview);
				viewHolder.skrq2View = (TextView) convertView.findViewById(R.id.skrq2_textview);
				viewHolder.xybJtImageView=(ImageView) convertView.findViewById(R.id.xybjt_imageview);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderDjlx) convertView.getTag();
			}
			viewHolder.djlxView.setText("单据类型："+map.get("billtypename").toString());
            viewHolder.djbhView.setText("单据编号："+map.get("code").toString());
			viewHolder.dwView.setText(map.get("cname").toString());
			viewHolder.skjeView.setText("付款金额：");
			viewHolder.skje2View.setText("￥"+ FigureTools.sswrFigure(map.get("je").toString()));
			viewHolder.skrqView.setText("限定日期：");
			viewHolder.skrq2View.setText(map.get("rq").toString());
		}
		return convertView;
	}

	static class ViewHolderNbgg {//内部公告
		TextView titleView;
		ImageView xybJtImageView;//
	}
	static class ViewHolder {//员工生日、客户生日
		TextView typeView;
		TextView dwView;
		TextView nameView;
		ImageView xybJtImageView;
	}
	static class ViewHolderDjlx {//单据类型
		TextView djlxView;
		TextView djbhView;
		TextView dwView;
		TextView skjeView;
		TextView skje2View;
		TextView skrqView;
		TextView skrq2View;
		ImageView xybJtImageView;//
	}
	static class ViewHolderGqsp {//过期商品
        TextView djlxView;
        TextView djbhView;
        TextView dwView;
        TextView skjeView;
        TextView skje2View;
        TextView skrqView;
        TextView skrq2View;
        TextView kcslView;
        ImageView xybJtImageView;//
        
    }
    static class ViewHolderKcbj{  //库存报警
		TextView djlxView;
		TextView djbhView;
		TextView spmcView;
		TextView spggView;
		TextView xxkcView;
		TextView dqkcView;
		TextView sxkcView;

	}
}