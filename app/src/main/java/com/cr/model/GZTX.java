package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 工作提醒
 * @author xXzX
 *
 */
public class GZTX implements Serializable{
	private static final long serialVersionUID = -854310034204718574L;
	private String types;//1表示内部公告(此项已单独提出)2表示客户生日3表示职工生日4表示即将实施项目
	private String bt;//类别名称
	private String title;//标题
	private String memo;//内容
	private String opid;//当前登录操作员ID
	private String opdate;//操作日期
	private String lxrname;//联系人名字
	private String cname;//单位的名字
	private String id;//如果为职工生日，则为职工ID号，其他则为联系人ID号
	private int currentPage;//当前的条数
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOpid() {
		return opid;
	}
	public void setOpid(String opid) {
		this.opid = opid;
	}
	public String getOpdate() {
		return opdate;
	}
	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getLxrname() {
        return lxrname;
    }
    public void setLxrname(String lxrname) {
        this.lxrname = lxrname;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    /**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<GZTX> parseJsonToObject(String jsonStr){
		List<GZTX> mstxList=new ArrayList<GZTX>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				GZTX mstx=new GZTX();
				mstx.setTypes(jsonObj2.getString("types"));
				mstx.setTitle(jsonObj2.getString("title"));
//				mstx.setMemo(jsonObj2.getString("memo"));
//				mstx.setOpid(jsonObj2.getString("opid"));
//				mstx.setOpdate(jsonObj2.getString("opdate"));
//				mstx.setId(jsonObj2.getString("id"));
				mstxList.add(mstx);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mstxList;
	}
	/**
     * 将json字符串解析成当前类集合的对象
     */
    public static List<GZTX> parseJsonToObject2(String jsonStr){
        List<GZTX> mstxList=new ArrayList<GZTX>();
        try {
            JSONArray jsonArray=new JSONArray(jsonStr);
            for(int j=0;j<jsonArray.length();j++){
                JSONObject jsonObj2=jsonArray.getJSONObject(j);
                GZTX mstx=new GZTX();
                mstx.setTypes(jsonObj2.getString("types"));
                mstx.setLxrname(jsonObj2.getString("lxrname"));
                mstx.setMemo(jsonObj2.getString("memo"));
                mstx.setCname(jsonObj2.getString("cname"));
//              mstx.setOpdate(jsonObj2.getString("opdate"));
                mstx.setId(jsonObj2.getString("id"));
                mstxList.add(mstx);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mstxList;
    }
}
