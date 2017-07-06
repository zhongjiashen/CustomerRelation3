package com.cr.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录界面的帐套
 * @author Administrator
 *
 */
public class ZT {
	private String name;
	private String dbName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	/**
	 * 解析json字符串成当前对象
	 */
	public static List<ZT> paseJsonToObject(String jsonStr){
		List<ZT> ztList=new ArrayList<ZT>(); 
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				ZT zt=new ZT();
				zt.setName(jsonObj2.getString("name"));
				zt.setDbName(jsonObj2.getString("dbname"));
				ztList.add(zt);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ztList;
	}
}
