package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GSLXRChild implements Serializable{
	/**
	 * 工作平台-呼叫中心-组员信息
	 */
	private static final long serialVersionUID = 1L;
	
	private String jhbid;
	private String id;
	private String clientid;
	private String lxrid;//联系
	private String lxrname;//联系人名称
	
	public String getJhbid() {
		return jhbid;
	}
	public void setJhbid(String jhbid) {
		this.jhbid = jhbid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLxrid() {
		return lxrid;
	}
	public void setLxrid(String lxrid) {
		this.lxrid = lxrid;
	}
	public String getLxrname() {
		return lxrname;
	}
	public void setLxrname(String lxrname) {
		this.lxrname = lxrname;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	/**
     * 将json字符串解析成当前类集合的对象(用于查询联系人使用)
     */
    public static List<GSLXRChild> parseJsonToObject(String jsonStr){
        List<GSLXRChild> childList=new ArrayList<GSLXRChild>();
        try {
            JSONArray jsonArray=new JSONArray(jsonStr);
            for(int j=0;j<jsonArray.length();j++){
                JSONObject jsonObj2=jsonArray.getJSONObject(j);
                GSLXRChild child=new GSLXRChild();
                child.setId(jsonObj2.getString("id"));
                child.setClientid(jsonObj2.getString("clientid"));
                child.setLxrid(jsonObj2.getString("cname"));
                child.setLxrname(jsonObj2.getString("lxrname"));
                childList.add(child);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return childList;
    }
}
