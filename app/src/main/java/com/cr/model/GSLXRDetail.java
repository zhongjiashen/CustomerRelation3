package com.cr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 联系人详细信息
 * @author xXzX
 *
 */
public class GSLXRDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7012011215241973653L;
	private String id;//ID
	private String lxrname;//联系人名称
	private String clientid;//单位Id
	private String cname;//单位名称
	private String xb;//性别 0女 1男
	private String areafullname;//地区全称
	private String typeid;//客户等级Id
	private String typename;//客户等级名称
	private String hytypeid;//行业类型Id
	private String hylxname;//行业类型名字
	private String csourceid;//客户来源id
	private String khlyname;//客户来源名称
	private String khtypeid;//客户类型ID
	private String khtypename;//客户类型名称
	private String phone1;//固定电话1
	private String phone2;//固定电话2
	private String sjhm1;//手机号码1
	private String sjhm2;//手机号码2
	private String qq1;//Qq1
	private String qq2;//Qq2
	private String sremail1;//Email1
	private String sremail2;//Email2
	private String dwzz;//单位住址
	private String jtzz;//家庭住址
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLxrname() {
		return lxrname;
	}
	public void setLxrname(String lxrname) {
		this.lxrname = lxrname;
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
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getAreafullname() {
		return areafullname;
	}
	public void setAreafullname(String areafullname) {
		this.areafullname = areafullname;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getHytypeid() {
		return hytypeid;
	}

	public void setHytypeid(String hytypeid) {
		this.hytypeid = hytypeid;
	}
	public String getHylxname() {
		return hylxname;
	}
	public void setHylxname(String hylxname) {
		this.hylxname = hylxname;
	}
	public String getCsourceid() {
		return csourceid;
	}
	public void setCsourceid(String csourceid) {
		this.csourceid = csourceid;
	}

	public String getKhlyname() {
		return khlyname;
	}

	public void setKhlyname(String khlyname) {
		this.khlyname = khlyname;
	}

	public String getKhtypeid() {
		return khtypeid;
	}
	public void setKhtypeid(String khtypeid) {
		this.khtypeid = khtypeid;
	}

	public String getKhtypename() {
		return khtypename;
	}

	public void setKhtypename(String khtypename) {
		this.khtypename = khtypename;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getSjhm1() {
		return sjhm1;
	}
	public void setSjhm1(String sjhm1) {
		this.sjhm1 = sjhm1;
	}

	public String getSjhm2() {
		return sjhm2;
	}
	public void setSjhm2(String sjhm2) {
		this.sjhm2 = sjhm2;
	}

	public String getQq1() {
		return qq1;
	}

	public void setQq1(String qq1) {
		this.qq1 = qq1;
	}
	public String getQq2() {
		return qq2;
	}

	public void setQq2(String qq2) {
		this.qq2 = qq2;
	}

	public String getSremail1() {
		return sremail1;
	}

	public void setSremail1(String sremail1) {
		this.sremail1 = sremail1;
	}
	public String getSremail2() {
		return sremail2;
	}

	public void setSremail2(String sremail2) {
		this.sremail2 = sremail2;
	}
	public String getDwzz() {
		return dwzz;
	}
	public void setDwzz(String dwzz) {
		this.dwzz = dwzz;
	}
	public String getJtzz() {
		return jtzz;
	}
	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}
	/**
	 * 将json字符串解析成当前类集合的对象
	 */
	public static List<GSLXRDetail> parseJsonToObject(String jsonStr){
		List<GSLXRDetail> jhrwjhbfList=new ArrayList<GSLXRDetail>();
		try {
			JSONArray jsonArray=new JSONArray(jsonStr);
			for(int j=0;j<jsonArray.length();j++){
				JSONObject jsonObj2=jsonArray.getJSONObject(j);
				GSLXRDetail jhrwjhbf=new GSLXRDetail();
				jhrwjhbf.setId(jsonObj2.getString("id"));
				jhrwjhbf.setLxrname(jsonObj2.getString("lxrname"));
				jhrwjhbf.setClientid(jsonObj2.getString("clientid"));
				jhrwjhbf.setCname(jsonObj2.getString("cname"));
				jhrwjhbf.setXb(jsonObj2.getString("xb"));
				jhrwjhbf.setAreafullname(jsonObj2.getString("areafullname"));
				jhrwjhbf.setTypeid(jsonObj2.getString("typeid"));
				jhrwjhbf.setTypename(jsonObj2.getString("typename"));
				jhrwjhbf.setHytypeid(jsonObj2.getString("hytypeid"));
				jhrwjhbf.setHylxname(jsonObj2.getString("hylxname"));
				jhrwjhbf.setCsourceid(jsonObj2.getString("csourceid"));
				jhrwjhbf.setKhlyname(jsonObj2.getString("khlyname"));
				jhrwjhbf.setKhtypeid(jsonObj2.getString("khtypeid"));
				jhrwjhbf.setKhtypename(jsonObj2.getString("khtypename"));
				jhrwjhbf.setPhone1(jsonObj2.getString("phone"));
				jhrwjhbf.setPhone2(jsonObj2.getString("phone1"));
				jhrwjhbf.setSjhm1(jsonObj2.getString("sjhm"));
				jhrwjhbf.setSjhm2(jsonObj2.getString("sjhm1"));
				jhrwjhbf.setQq1(jsonObj2.getString("qq"));
				jhrwjhbf.setQq2(jsonObj2.getString("qq1"));
				jhrwjhbf.setSremail1(jsonObj2.getString("sremail"));
				jhrwjhbf.setSremail2(jsonObj2.getString("sremail1"));
				jhrwjhbf.setDwzz(jsonObj2.getString("bgaddress"));
				jhrwjhbf.setJtzz(jsonObj2.getString("jtaddress"));
				jhrwjhbfList.add(jhrwjhbf);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jhrwjhbfList;
	}	
	/**
     * 将json字符串解析成当前类集合的对象,查询单位的时候使用
     */
    public static List<GSLXRDetail> parseJsonToObject2(String jsonStr){
        List<GSLXRDetail> jhrwjhbfList=new ArrayList<GSLXRDetail>();
        try {
            JSONArray jsonArray=new JSONArray(jsonStr);
            for(int j=0;j<jsonArray.length();j++){
                JSONObject jsonObj2=jsonArray.getJSONObject(j);
                GSLXRDetail jhrwjhbf=new GSLXRDetail();
                jhrwjhbf.setId(jsonObj2.getString("id"));
                jhrwjhbf.setCname(jsonObj2.getString("cname"));
                jhrwjhbf.setAreafullname(jsonObj2.getString("areafullname"));
                jhrwjhbf.setTypeid(jsonObj2.getString("typeid"));
                jhrwjhbf.setTypename(jsonObj2.getString("typename"));
                jhrwjhbf.setHytypeid(jsonObj2.getString("hytypeid"));
                jhrwjhbf.setHylxname(jsonObj2.getString("hylxname"));
                jhrwjhbf.setCsourceid(jsonObj2.getString("csourceid"));
                jhrwjhbf.setKhlyname(jsonObj2.getString("khlyname"));
                jhrwjhbf.setKhtypeid(jsonObj2.getString("khtypeid"));
                jhrwjhbf.setKhtypename(jsonObj2.getString("khtypename"));
//                jhrwjhbf.setPhone1(jsonObj2.getString("phone"));
//                jhrwjhbf.setPhone2(jsonObj2.getString("phone1"));
//                jhrwjhbf.setSjhm1(jsonObj2.getString("sjhm"));
//                jhrwjhbf.setSjhm2(jsonObj2.getString("sjhm1"));
//                jhrwjhbf.setQq1(jsonObj2.getString("qq"));
//                jhrwjhbf.setQq2(jsonObj2.getString("qq1"));
//                jhrwjhbf.setSremail1(jsonObj2.getString("sremail"));
//                jhrwjhbf.setSremail2(jsonObj2.getString("sremail1"));
//                jhrwjhbf.setDwzz(jsonObj2.getString("bgaddress"));
//                jhrwjhbf.setJtzz(jsonObj2.getString("jtaddress"));
                jhrwjhbfList.add(jhrwjhbf);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jhrwjhbfList;
    }
}
