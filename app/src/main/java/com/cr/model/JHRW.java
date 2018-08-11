package com.cr.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计划任务
 * 
 * @author xXzX
 * 
 */
public class JHRW implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7012011215241973653L;
	private String id;// ID号
	private String nd;// 年度
	private String jhname;// 计划名称
	private String jhid;// 计划id
	private String qsrq;// 计划起始日期
	private String zzrq;// 计划结束日期
	private String memo;// 备注
	private String lx;// 计划类型 0按单位 1按联系人
	private String lxmc;// 计划类型名称
	private String shzt;//审核状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getJhname() {
		return jhname;
	}

	public void setJhname(String jhname) {
		this.jhname = jhname;
	}

	public String getQsrq() {
		return qsrq;
	}

	public void setQsrq(String qsrq) {
		this.qsrq = qsrq;
	}

	public String getZzrq() {
		return zzrq;
	}

	public void setZzrq(String zzrq) {
		this.zzrq = zzrq;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getLxmc() {
		return lxmc;
	}

	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}

	public String getJhid() {
		return jhid;
	}

	public void setJhid(String jhid) {
		this.jhid = jhid;
	}

	public String getShzt() {
		return shzt;
	}

	public void setShzt(String shzt) {
		this.shzt = shzt;
	}

	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<JHRW> parseJsonToObject(String jsonStr) {
		List<JHRW> jhrwList = new ArrayList<JHRW>();
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject jsonObj2 = jsonArray.getJSONObject(j);
				JHRW jhrw = new JHRW();
				jhrw.setId(jsonObj2.getString("id"));
				jhrw.setJhname(jsonObj2.getString("jhname"));
				jhrw.setQsrq(jsonObj2.getString("qsrq"));
				jhrw.setZzrq(jsonObj2.getString("zzrq"));
				// jhrw.setNd(jsonObj2.getString("nd"));

				// jhrw.setMemo(jsonObj2.getString("memo"));

//				jhrw.setShzt(jsonObj2.getString("shzt"));
				// jhrw.setLx(jsonObj2.getString("lx"));
				// jhrw.setLxmc(jsonObj2.getString("lxmc"));
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
	public static List<JHRW> parseJsonToObject2(String jsonStr) {
		List<JHRW> jhrwList = new ArrayList<JHRW>();
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject jsonObj2 = jsonArray.getJSONObject(j);
				JHRW jhrw = new JHRW();
				jhrw.setId(jsonObj2.getString("id"));
				// jhrw.setNd(jsonObj2.getString("nd"));
				// jhrw.setJhname(jsonObj2.getString("jhname"));
				// jhrw.setMemo(jsonObj2.getString("memo"));
				jhrw.setQsrq(jsonObj2.getString("jhrq"));
				jhrw.setShzt(jsonObj2.getString("shzt"));
				// jhrw.setZzrq(jsonObj2.getString("zzrq"));
				// jhrw.setLx(jsonObj2.getString("lx"));
				// jhrw.setLxmc(jsonObj2.getString("lxmc"));
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
	public static List<JHRW> parseJsonToObject6(String jsonStr) {
		List<JHRW> jhrwList = new ArrayList<JHRW>();
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject jsonObj2 = jsonArray.getJSONObject(j);
				JHRW jhrw = new JHRW();
				jhrw.setId(jsonObj2.getString("id"));
				// jhrw.setNd(jsonObj2.getString("nd"));
				// jhrw.setJhname(jsonObj2.getString("jhname"));
				// jhrw.setMemo(jsonObj2.getString("memo"));
				jhrw.setQsrq(jsonObj2.getString("fullweekno"));
				jhrw.setShzt(jsonObj2.getString("shzt"));
				// jhrw.setZzrq(jsonObj2.getString("zzrq"));
				// jhrw.setLx(jsonObj2.getString("lx"));
				// jhrw.setLxmc(jsonObj2.getString("lxmc"));
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
	public static List<JHRW> parseJsonToObject5(String jsonStr) {
		List<JHRW> jhrwList = new ArrayList<JHRW>();
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject jsonObj2 = jsonArray.getJSONObject(j);
				JHRW jhrw = new JHRW();
				jhrw.setId(jsonObj2.getString("id"));
				// jhrw.setNd(jsonObj2.getString("nd"));
				// jhrw.setJhname(jsonObj2.getString("jhname"));
				// jhrw.setMemo(jsonObj2.getString("memo"));
				jhrw.setQsrq(jsonObj2.getString("jhrqc"));
				jhrw.setShzt(jsonObj2.getString("shzt"));
				// jhrw.setZzrq(jsonObj2.getString("zzrq"));
				// jhrw.setLx(jsonObj2.getString("lx"));
				// jhrw.setLxmc(jsonObj2.getString("lxmc"));
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
	public static List<JHRW> parseJsonToObject3(String jsonStr) {
		List<JHRW> jhrwList = new ArrayList<JHRW>();
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject jsonObj2 = jsonArray.getJSONObject(j);
				JHRW jhrw = new JHRW();
				jhrw.setId(jsonObj2.getString("id"));
				jhrw.setJhname(jsonObj2.getString("items"));
				jhrw.setLx(jsonObj2.getString("shzt"));
				jhrwList.add(jhrw);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwList;
	}

	/**
	 * 将json字符串解析成当前类集合的对象（计划编辑执行）
	 */
	public static List<Map<String, Object>> parseJsonToObject4(String jsonStr) {
		List<Map<String, Object>> jhrwList = new ArrayList<Map<String, Object>>();
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject jsonObj2 = jsonArray.getJSONObject(j);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("jhid", jsonObj2.get("jhid").toString());
				map.put("itemid", jsonObj2.get("itemid").toString());
				map.put("itemname",
						jsonObj2.get("items").toString().equals("null") ? ""
								: jsonObj2.get("items").toString());
				map.put("jhnr",
						jsonObj2.get("jhnr").toString().equals("null") ? ""
								: jsonObj2.get("jhnr").toString());
				map.put("zdcl",
						jsonObj2.get("zdcl").toString().equals("null") ? ""
								: jsonObj2.get("zdcl").toString());
				map.put("zxcl",
						jsonObj2.get("zxcl").toString().equals("null") ? ""
								: jsonObj2.get("zxcl").toString());
				map.put("zxjg", jsonObj2.get("zxjg").toString().equals("null")?"":jsonObj2.get("zxjg").toString());
				map.put("zxzt", jsonObj2.get("shzt").toString().equals("null")?"":jsonObj2.get("shzt").toString());
				map.put("zxyy", jsonObj2.get("zxyy").toString().equals("null")?"":jsonObj2.get("zxyy").toString());
				map.put("issy", jsonObj2.get("issy").toString().equals("null")?"":jsonObj2.get("issy").toString());
				// map.put("syrq",
				// jsonObj2.get("syrq").toString().equals("1900-01-01 00:00")?"":jsonObj2.get("syrq").toString().equals("null")?"":jsonObj2.get("syrq").toString());
				map.put("syrq", jsonObj2.get("syrq").toString());
				map.put("isprompt", jsonObj2.get("isused").toString());
				map.put("pl", "1");
				map.put("qsrq",
						(jsonObj2.get("startdate").toString().equals("null") ? ""
								: jsonObj2.get("startdate").toString())
								+ " "
								+ (jsonObj2.get("starttime").toString()
										.equals("null") ? "" : jsonObj2.get(
										"starttime").toString()));
				map.put("shzt", jsonObj2.get("shzt").toString());
				jhrwList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwList;
	}
}
