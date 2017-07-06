package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目列表
 * @author xXzX
 *
 */
public class XMLB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7012011215241973653L;
	private String id;//ID号
	private String title;//名称
	private String xmjdmc;//项目进度名称
	private String upopdate;//操作日期
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getXmjdmc() {
		return xmjdmc;
	}



	public void setXmjdmc(String xmjdmc) {
		this.xmjdmc = xmjdmc;
	}



	public String getUpopdate() {
		return upopdate;
	}



	public void setUpopdate(String upopdate) {
		this.upopdate = upopdate;
	}



	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<XMLB> parseJsonToObject(String jsonStr){
		List<XMLB> jhrwList=new ArrayList<XMLB>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				XMLB jhrw=new XMLB();
				jhrw.setId(jsonObj2.getString("id"));
				jhrw.setTitle(jsonObj2.getString("title"));
				jhrw.setXmjdmc(jsonObj2.getString("xmjdmc"));
				jhrw.setUpopdate(jsonObj2.getString("upopdate"));
				jhrwList.add(jhrw);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwList;
	}	
}
