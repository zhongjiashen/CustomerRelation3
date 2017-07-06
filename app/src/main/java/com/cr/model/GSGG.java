package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 公司公告
 * @author xXzX
 *
 */
public class GSGG implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3518985382270647920L;
	private String id;//1表示内部公告(此项已单独提出)2表示客户生日3表示职工生日4表示即将实施项目
	private String title;//类别名称
	private String opdate;//标题
	private String memo;//内容
	
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
	public String getOpdate() {
		return opdate;
	}
	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}


	/**
	 * 将json字符串解析成当前类集合的对象;
	 */
	public static List<GSGG> parseJsonToObject(String jsonStr){
		List<GSGG> mstxList=new ArrayList<GSGG>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				GSGG mstx=new GSGG();
				mstx.setId(jsonObj2.getString("id"));
				mstx.setTitle(jsonObj2.getString("title"));
				mstx.setOpdate(jsonObj2.getString("opdate"));
				mstxList.add(mstx);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mstxList;
	}
	/**
	 * 将json字符串解析成当前类集合的对象;
	 */
	public static List<GSGG> parseJsonToObject2(String jsonStr){
		List<GSGG> mstxList=new ArrayList<GSGG>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				GSGG mstx=new GSGG();
				mstx.setId(jsonObj2.getString("id"));
				mstx.setMemo(jsonObj2.getString("memo"));
				mstx.setTitle(jsonObj2.getString("title"));
				mstx.setOpdate(jsonObj2.getString("opdate"));
				mstxList.add(mstx);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mstxList;
	}
}
