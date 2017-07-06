package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GSLXRGroup implements Serializable{
	/**
	 * 工作平台-呼叫中心-群组信息
	 */
	private static final long serialVersionUID = 1L;
	
	private String jhbid;
	private String id;
	private String clientid;//单位Id
	private String cname;//单位名称
	private boolean isSelect;//是否选中
	List<GSLXRChild> gslxrChilds=new ArrayList<GSLXRChild>();
	
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
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public List<GSLXRChild> getGslxrChilds() {
		return gslxrChilds;
	}
	public void setGslxrChilds(List<GSLXRChild> gslxrChilds) {
		this.gslxrChilds = gslxrChilds;
	}
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	/**
     * 将json字符串解析成当前类集合的对象
     */
    public static List<GSLXRGroup> parseJsonToObject(String jsonStr){
        List<GSLXRGroup> groupList=new ArrayList<GSLXRGroup>();
        List<GSLXRChild> childList=new ArrayList<GSLXRChild>();
        try {
            JSONArray jsonArray=new JSONArray(jsonStr);
            for(int j=0;j<jsonArray.length();j++){
                JSONObject jsonObj2=jsonArray.getJSONObject(j);
                GSLXRChild child=new GSLXRChild();
                child.setClientid(jsonObj2.getString("clientid"));
                child.setLxrid(jsonObj2.getString("lxrid"));
                child.setLxrname(jsonObj2.getString("lxrname"));
                childList.add(child);
                GSLXRGroup group=new GSLXRGroup();
                group.setClientid(jsonObj2.getString("clientid"));
                group.setCname(jsonObj2.getString("cname"));
                group.setSelect(false);
                int state=0;
                for(GSLXRGroup g:groupList){
                	if(g.getClientid().equals(group.getClientid())){
                		state=1;
                	}
                }
                if(state==0){
                	groupList.add(group);
                }
                
            }
            for(GSLXRGroup group:groupList){
            	List<GSLXRChild> childs=new ArrayList<GSLXRChild>();
            	for(GSLXRChild child:childList){
            		if(group.getClientid().equals(child.getClientid())){
            			childs.add(child);
            		}
            	}
            	group.setGslxrChilds(childs);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupList;
    }
    /**
     * 将json字符串解析成当前类集合的对象
     */
    public static List<GSLXRGroup> parseJsonToObject2(String jsonStr){
        List<GSLXRGroup> groupList=new ArrayList<GSLXRGroup>();
        List<GSLXRChild> childList=new ArrayList<GSLXRChild>();
        try {
            JSONArray jsonArray=new JSONArray(jsonStr);
            for(int j=0;j<jsonArray.length();j++){
                JSONObject jsonObj2=jsonArray.getJSONObject(j);
                GSLXRChild child=new GSLXRChild();
                child.setClientid(jsonObj2.getString("clientid"));
                child.setLxrid(jsonObj2.getString("id"));
                child.setLxrname(jsonObj2.getString("lxrname"));
                childList.add(child);
                GSLXRGroup group=new GSLXRGroup();
                group.setClientid(jsonObj2.getString("clientid"));
                group.setCname(jsonObj2.getString("cname"));
                int state=0;
                for(GSLXRGroup g:groupList){
                	if(g.getClientid().equals(group.getClientid())){
                		state=1;
                	}
                }
                if(state==0){
                	groupList.add(group);
                }
                
            }
            for(GSLXRGroup group:groupList){
            	List<GSLXRChild> childs=new ArrayList<GSLXRChild>();
            	for(GSLXRChild child:childList){
            		if(group.getClientid().equals(child.getClientid())){
            			childs.add(child);
            		}
            	}
            	group.setGslxrChilds(childs);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupList;
    }
}
