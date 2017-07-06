package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SJZD implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 196770200975159538L;
	private String id;//字典ID号
	private String zdbm;//字典项目类型编码
	private String dictmc;//字典项目名称
	private String sxh;//顺序号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZdbm() {
		return zdbm;
	}
	public void setZdbm(String zdbm) {
		this.zdbm = zdbm;
	}
	public String getDictmc() {
		return dictmc;
	}
	public void setDictmc(String dictmc) {
		this.dictmc = dictmc;
	}
	public String getSxh() {
		return sxh;
	}
	public void setSxh(String sxh) {
		this.sxh = sxh;
	}
	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<SJZD> parseJsonToObject(String jsonStr){
		List<SJZD> jhrwList=new ArrayList<SJZD>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
//			SJZD s=new SJZD();
//            s.setId("0");
////          jhrw.setZdbm(jsonObj2.getString("zdbm"));
//            s.setDictmc("全部");
////          jhrw.setSxh(jsonObj2.getString("sxh"));
//            jhrwList.add(s);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				SJZD jhrw=new SJZD();
				jhrw.setId(jsonObj2.getString("id"));
//				jhrw.setZdbm(jsonObj2.getString("zdbm"));
				jhrw.setDictmc(jsonObj2.getString("dictmc"));
//				jhrw.setSxh(jsonObj2.getString("sxh"));
				jhrwList.add(jhrw);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwList;
	}	
	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<SJZD> parseJsonToObject2(String jsonStr){
		List<SJZD> jhrwList=new ArrayList<SJZD>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			SJZD s=new SJZD();
            s.setId("0");
//          jhrw.setZdbm(jsonObj2.getString("zdbm"));
            s.setDictmc("全部");
//          jhrw.setSxh(jsonObj2.getString("sxh"));
            jhrwList.add(s);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				SJZD jhrw=new SJZD();
				jhrw.setId(jsonObj2.getString("id"));
//				jhrw.setZdbm(jsonObj2.getString("zdbm"));
				jhrw.setDictmc(jsonObj2.getString("dictmc"));
//				jhrw.setSxh(jsonObj2.getString("sxh"));
				jhrwList.add(jhrw);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwList;
	}
	
}
