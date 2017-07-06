package com.cr.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 单位
 * @author Administrator
 *
 */
public class DW {
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
	public static List<DW> paseJsonToObject(String jsonStr){
		List<DW> ztList=new ArrayList<DW>(); 
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				DW zt=new DW();
				zt.setName(jsonObj2.getString("cname"));
				zt.setId(jsonObj2.getString("id"));
				ztList.add(zt);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ztList;
	}
	
	/**
	 * 根据单位ID查询单位对象
	 */
	public static List<Map<String,Object>> paseJsonToObject2(String jsonStr){
		List<Map<String,Object>> ztList=new ArrayList<Map<String,Object>>(); 
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				Map<String, Object>map=new HashMap<String, Object>();
				map.put("id", jsonObj2.get("id").toString());
				map.put("cname", jsonObj2.get("cname").toString());
				map.put("areafullname", jsonObj2.get("areafullname").toString());
				map.put("c_typeid", jsonObj2.get("c_typeid").toString());
				map.put("typename", jsonObj2.get("typename").toString());
				map.put("c_hytypeid", jsonObj2.get("c_hytypeid").toString());
				map.put("hylxname", jsonObj2.get("hylxname").toString());
				map.put("c_csourceid", jsonObj2.get("c_csourceid").toString());
				map.put("khlyname", jsonObj2.get("khlyname").toString());
				map.put("c_khtypeid", jsonObj2.get("c_khtypeid").toString());
				map.put("khtypename", jsonObj2.get("khtypename").toString());
				map.put("frdb", jsonObj2.get("frdb").toString());
				map.put("cnet", jsonObj2.get("cnet").toString());
				map.put("phone", jsonObj2.get("phone").toString());
				map.put("email", jsonObj2.get("email").toString());
				map.put("address", jsonObj2.get("address").toString());
				ztList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ztList;
	}
}
