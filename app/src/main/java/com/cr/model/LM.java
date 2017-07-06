package com.cr.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 商品的类目
 * @author Administrator
 *
 */
public class LM {
	private String name;
	private String id;
	private String code;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 解析json字符串成当前对象
	 */
	public static List<LM> paseJsonToObject(String jsonStr){
		List<LM> ztList=new ArrayList<LM>(); 
		try {
		    LM z=new LM();
            z.setName("全部");
            z.setId("0");
            z.setCode("");
            ztList.add(z);
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				LM zt=new LM();
				zt.setName(jsonObj2.getString("name"));
				zt.setId(jsonObj2.getString("id"));
				zt.setCode(jsonObj2.getString("code"));
				ztList.add(zt);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ztList;
	}
}
