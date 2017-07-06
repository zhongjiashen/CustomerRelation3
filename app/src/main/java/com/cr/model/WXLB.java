package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 维修列表
 * @author xXzX
 *
 */
public class WXLB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7012011215241973653L;
	private String id;//ID号
	private String billcode;//单据编号
	private String sbmc;//设备名称
	private String sbsl;//设备数量
	private String wxjdmc;//维修进度名称
	private String bsrq;//保修日期
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBillcode() {
		return billcode;
	}
	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}
	public String getSbmc() {
		return sbmc;
	}
	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}

	public String getSbsl() {
		return sbsl;
	}
	public void setSbsl(String sbsl) {
		this.sbsl = sbsl;
	}
	public String getWxjdmc() {
		return wxjdmc;
	}
	public void setWxjdmc(String wxjdmc) {
		this.wxjdmc = wxjdmc;
	}
	public String getBsrq() {
		return bsrq;
	}
	public void setBsrq(String bsrq) {
		this.bsrq = bsrq;
	}

	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<WXLB> parseJsonToObject(String jsonStr){
		List<WXLB> jhrwList=new ArrayList<WXLB>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				WXLB jhrw=new WXLB();
				jhrw.setId(jsonObj2.getString("id"));
				jhrw.setBillcode(jsonObj2.getString("billcode"));
				jhrw.setSbmc(jsonObj2.getString("sbmc"));
				jhrw.setSbsl(jsonObj2.getString("sbsl"));
				jhrw.setWxjdmc(jsonObj2.getString("wxjdmc"));
				jhrw.setBsrq(jsonObj2.getString("bsrq"));
				jhrwList.add(jhrw);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwList;
	}	
}
