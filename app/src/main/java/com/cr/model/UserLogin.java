package com.cr.model;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户登录
 * @author xXzX
 *
 */
public class UserLogin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7012011215241973653L;
	private String id;//用户ID
	private String opname;//登录操作员姓名
	private String empid;//
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpname() {
		return opname;
	}
	public void setOpname(String opname) {
		this.opname = opname;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static UserLogin parseJsonToObject(String jsonStr){
		UserLogin userLogin=new UserLogin();
		try {
			if(jsonStr.startsWith("false")){
				return null;
			}
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj=jsonArray.getJSONObject(j);
				userLogin.setId(jsonObj.getString("id"));
				userLogin.setOpname(jsonObj.getString("opname"));
				userLogin.setEmpid(jsonObj.getString("empid"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userLogin;
	}	
}
