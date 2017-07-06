package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 销售单列表
 * @author xXzX
 *
 */
public class XSDLB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7012011215241973653L;
	private String saleid;//销售单Id
	private String billcode;//单号
	private String billdate;//单据日期
	private String cname;//单位
	private String lxrname;//联系人
	private String zje;//总金额
	private String zt;//审核状态 0未审 1已审
	private String opid;//操作员ID
	private String clientid;//单位Id
	private String lxrid;//联系人ID
	private String phone;//电话
	private String sjhm;//手机号码
	private String shdz;//送货地址
	private String wkfs;//回款方式
	private String wkrq;//回款日期
	private boolean isCheck;//是否被选中
	
	
	public String getSaleid() {
		return saleid;
	}
	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}
	public String getBillcode() {
		return billcode;
	}
	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}
	public String getBilldate() {
		return billdate;
	}
	public void setBilldate(String billdate) {
		this.billdate = billdate;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getLxrname() {
		return lxrname;
	}
	public void setLxrname(String lxrname) {
		this.lxrname = lxrname;
	}
	public String getZje() {
		return zje;
	}
	public void setZje(String zje) {
		this.zje = zje;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getOpid() {
		return opid;
	}
	public void setOpid(String opid) {
		this.opid = opid;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getLxrid() {
		return lxrid;
	}
	public void setLxrid(String lxrid) {
		this.lxrid = lxrid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public String getShdz() {
		return shdz;
	}
	public void setShdz(String shdz) {
		this.shdz = shdz;
	}
	public String getWkfs() {
		return wkfs;
	}
	public void setWkfs(String wkfs) {
		this.wkfs = wkfs;
	}
	public String getWkrq() {
		return wkrq;
	}
	public void setWkrq(String wkrq) {
		this.wkrq = wkrq;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<XSDLB> parseJsonToObject(String jsonStr){
		List<XSDLB> jhrwList=new ArrayList<XSDLB>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				XSDLB jhrw=new XSDLB();
				jhrw.setSaleid(jsonObj2.getString("saleid"));
				jhrw.setBillcode(jsonObj2.getString("billcode"));
				jhrw.setBilldate(jsonObj2.getString("billdate"));
				jhrw.setCname(jsonObj2.getString("cname"));
				jhrw.setLxrname(jsonObj2.getString("lxrname"));
				jhrw.setZje(jsonObj2.getString("zje"));
				jhrw.setZt(jsonObj2.getString("zt"));
				jhrw.setOpid(jsonObj2.getString("opid"));
				jhrwList.add(jhrw);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwList;
	}
	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<XSDLB> parseJsonToObject2(String jsonStr){
		List<XSDLB> jhrwList=new ArrayList<XSDLB>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				XSDLB jhrw=new XSDLB();
				jhrw.setSaleid(jsonObj2.getString("saleid"));
				jhrw.setBillcode(jsonObj2.getString("billcode"));
				jhrw.setBilldate(jsonObj2.getString("billdate"));
				jhrw.setClientid(jsonObj2.getString("clientid"));
				jhrw.setCname(jsonObj2.getString("cname"));
				jhrw.setLxrid(jsonObj2.getString("lxrid"));
				jhrw.setLxrname(jsonObj2.getString("lxrname"));
				jhrw.setPhone(jsonObj2.getString("phone"));
				jhrw.setSjhm(jsonObj2.getString("sjhm"));
				jhrw.setShdz(jsonObj2.getString("shdz"));
				jhrw.setWkfs(jsonObj2.getString("wkfs"));
				jhrw.setWkrq(jsonObj2.getString("wkrq"));
				jhrw.setZje(jsonObj2.getString("zje"));
				jhrw.setZt(jsonObj2.getString("zt"));
				jhrw.setOpid(jsonObj2.getString("opid"));
				jhrwList.add(jhrw);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwList;
	}	
}
