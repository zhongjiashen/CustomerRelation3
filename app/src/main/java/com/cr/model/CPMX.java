package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cr.activity.SLView;

/**
 * 产品明细
 * @author xXzX
 *
 */
public class CPMX implements Serializable{
	private static final long serialVersionUID = -854310034204718574L;
	private String id;//明细ID
	private String saleid;//销售单ID号
	private String goodsid;//产品ID
	private String qty;//数量
	private String price;//价格
	private String zje;//合计
	private String goodscode;//产品编号
	private String goodsname;//产品名称
	private String ggxh;//规格型号
	private String unitname;//计量单位
	private String goodstype;//类别ID
	private String goodstypename;//类别名称
	private boolean isSelect;
	private SLView slView;
	
	public SLView getSlView() {
		return slView;
	}
	public void setSlView(SLView slView) {
		this.slView = slView;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSaleid() {
		return saleid;
	}
	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getZje() {
		return zje;
	}
	public void setZje(String zje) {
		this.zje = zje;
	}
	public String getGoodscode() {
		return goodscode;
	}
	public void setGoodscode(String goodscode) {
		this.goodscode = goodscode;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getGgxh() {
		return ggxh;
	}
	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(String goodstype) {
		this.goodstype = goodstype;
	}
	public String getGoodstypename() {
		return goodstypename;
	}
	public void setGoodstypename(String goodstypename) {
		this.goodstypename = goodstypename;
	}
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	/**
	 * 将json字符串解析成当前类集合的对象;
	 */
	public static List<CPMX> parseJsonToObject(String jsonStr){
		List<CPMX> qsrqList=new ArrayList<CPMX>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				CPMX mstx=new CPMX();
				mstx.setId(jsonObj2.getString("id"));
				mstx.setGoodscode(jsonObj2.getString("goodscode"));
 				mstx.setGoodsname(jsonObj2.getString("goodsname"));
 				mstx.setGgxh(jsonObj2.getString("ggxh"));
 				mstx.setGoodstype(jsonObj2.getString("goodstype"));
 				mstx.setUnitname(jsonObj2.getString("unitname"));
 				mstx.setPrice(jsonObj2.getString("price"));
 				mstx.setGoodscode(jsonObj2.getString("goodscode"));
 				mstx.setGoodstypename(jsonObj2.getString("goodstypename"));
				qsrqList.add(mstx);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return qsrqList;
	}
	/**
	 * 将json字符串解析成当前类集合的对象;
	 */
	public static List<CPMX> parseJsonToObject2(String jsonStr){
		List<CPMX> qsrqList=new ArrayList<CPMX>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				CPMX mstx=new CPMX();
				mstx.setId(jsonObj2.getString("id"));
				mstx.setSaleid(jsonObj2.getString("saleid"));
 				mstx.setGoodsid(jsonObj2.getString("goodsid"));
 				mstx.setQty(jsonObj2.getString("qty"));
 				mstx.setPrice(jsonObj2.getString("price"));
 				mstx.setZje(jsonObj2.getString("zje"));
 				mstx.setGoodscode(jsonObj2.getString("goodscode"));
 				mstx.setGoodsname(jsonObj2.getString("goodsname"));
 				mstx.setGgxh(jsonObj2.getString("ggxh"));
 				mstx.setUnitname(jsonObj2.getString("unitname"));
 				mstx.setGoodstype(jsonObj2.getString("goodstype"));
 				mstx.setGoodstypename(jsonObj2.getString("goodstypename"));
				qsrqList.add(mstx);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return qsrqList;
	}
}