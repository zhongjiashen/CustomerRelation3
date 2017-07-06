package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 个人日程
 * @author xXzX
 *
 */
public class GRRC implements Serializable{
	private static final long serialVersionUID = -854310034204718574L;
	private String title;//标题
	private String qsrq;//起始日期
	private String id;//
	private String memo;//内容
	private String opdate;//操作日期
	private String isprompt;//是否提醒
	private String zq;//周期
	private boolean isCheck=false;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQsrq() {
		return qsrq;
	}
	public void setQsrq(String qsrq) {
		this.qsrq = qsrq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOpdate() {
		return opdate;
	}
	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}
	public String getIsprompt() {
		return isprompt;
	}
	public void setIsprompt(String isprompt) {
		this.isprompt = isprompt;
	}
	public String getZq() {
		return zq;
	}
	public void setZq(String zq) {
		this.zq = zq;
	}
	
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	/**
	 * 将json字符串解析成当前类集合的对象;
	 */
	public static List<GRRC> parseJsonToObject(String jsonStr){
		List<GRRC> qsrqList=new ArrayList<GRRC>();
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				GRRC mstx=new GRRC();
				mstx.setId(jsonObj2.getString("id"));
				mstx.setTitle(jsonObj2.getString("title").equals("null")?"":jsonObj2.getString("title"));
 				mstx.setQsrq((jsonObj2.getString("startdate").equals("null")?"":jsonObj2.getString("startdate"))+" "+(jsonObj2.getString("starttime").equals("null")?"":jsonObj2.getString("starttime")));
				mstx.setIsprompt(jsonObj2.getString("isused"));
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
	public static List<GRRC> parseJsonToObject2(String jsonStr){
		List<GRRC> grrcList=new ArrayList<GRRC>();
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				GRRC mstx=new GRRC();
				mstx.setId(jsonObj2.getString("id"));
				mstx.setTitle(jsonObj2.getString("title").equals("null")?"":jsonObj2.getString("title"));
 				mstx.setQsrq((jsonObj2.getString("startdate").equals("null")?"":jsonObj2.getString("startdate"))+" "+(jsonObj2.getString("starttime").equals("null")?"":jsonObj2.getString("starttime")));
 				mstx.setMemo(jsonObj2.getString("info").equals("null")?"":jsonObj2.getString("info"));
 				mstx.setOpdate(jsonObj2.getString("opdate").equals("null")?"":jsonObj2.getString("opdate"));
 				mstx.setIsprompt(jsonObj2.getString("isused").equals("null")?"":jsonObj2.getString("isused"));
// 				mstx.setZq(jsonObj2.getString("pl"));
 				grrcList.add(mstx);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return grrcList;
	}
}
