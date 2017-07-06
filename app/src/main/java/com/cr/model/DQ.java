package com.cr.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 地区
 * @author Administrator
 *
 */
public class DQ {
	private String name;
	private String id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * 解析json字符串成当前对象
	 */
	public static List<DQ> paseJsonToObject(String jsonStr){
		List<DQ> ztList=new ArrayList<DQ>(); 
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				DQ zt=new DQ();
				zt.setName(jsonObj2.getString("cname"));
				zt.setId(jsonObj2.getString("id"));
				ztList.add(zt);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ztList;
	}
}
