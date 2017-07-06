package com.cr.model;

import java.io.Serializable;
/**
 * 客户来电的基本信息
 * @author xXzX
 *
 */
public class CustomerTel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3821758239254139466L;
	private String name;//联系人名字
	private String tel;//电话号码
	private String type;//通话类型
	private String time;//通话时间
	private String duration;//通话时长


	/**
	 * 将json字符串解析成当前类集合的对象
	 */
//	public static Map<String, Object> parseJsonToObject(String jsonStr){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		try {
//			JSONObject jsonObj=new JSONObject(jsonStr);
//			parmMap.put("code", jsonObj.getString("code"));
//			if(jsonObj.getString("code").equals("0")){
//				JSONObject dataJsonObject=jsonObj.getJSONObject("data");
//				@SuppressWarnings("rawtypes")
//				Iterator it = dataJsonObject.keys();
//				List<Customer>storeBrandList=new ArrayList<Customer>();
//				List<String>lableList=new ArrayList<String>();
//				while(it.hasNext()){
//					String key=(String)it.next();
//					JSONArray jsonArray=dataJsonObject.getJSONArray(key);
//					lableList.add(key);
//					for(int j=0;j<jsonArray.length();j++){
//						JSONObject jsonObj2=jsonArray.getJSONObject(j);
//						Customer storeBrand=new Customer();
//						storeBrand.setBrandId(jsonObj2.getString("brand_id"));
//						storeBrand.setBrandName(jsonObj2.getString("brand_name"));
//						storeBrand.setBrandLogo(jsonObj2.getString("brand_logo"));
//						storeBrand.setBitmap(BitmapHelper.getBitmap(ServerURL.baseURL+storeBrand.getBrandLogo()));
//						storeBrand.setInitialLable(key);
//						storeBrandList.add(storeBrand);
//					}
//				}
//				Collections.sort(storeBrandList);
//				parmMap.put("lableList", lableList);
//				parmMap.put("data", storeBrandList);
//			}else{
//				parmMap.put("data", "");
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return parmMap;
//	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * 将json字符串解析成当前类的对象
	 */
//	public static Map<String, Object> parseJsonToObject2(String jsonStr){
//		Map<String, Object> parmMap=new HashMap<String, Object>();
//		try {
//			JSONObject jsonObj=new JSONObject(jsonStr);
//			parmMap.put("code", jsonObj.getString("code"));
//			if(jsonObj.getString("code").equals("0")){
//				JSONObject dataJsonObj=jsonObj.getJSONObject("data");
//				List<StoreBrandTest> brandList=new ArrayList<StoreBrandTest>();
//				JSONArray brandInfoJsonArray=dataJsonObj.getJSONArray("brand_info");
//				for(int i=0;i<brandInfoJsonArray.length();i++){
//					JSONObject brandJsonObject=brandInfoJsonArray.getJSONObject(i);
//					StoreBrandTest storeBrand=new StoreBrandTest();
//					storeBrand.setBrandId(brandJsonObject.getString("brand_id"));
//					storeBrand.setBrandLogo(brandJsonObject.getString("brand_logo"));
//					storeBrand.setBrandName(brandJsonObject.getString("brand_name"));
//					storeBrand.setBrandDesc(brandJsonObject.getString("brand_desc"));
//					storeBrand.setBrandCountry(brandJsonObject.getString("brand_country"));
//					storeBrand.setBitmap(BitmapHelper.getBitmap(ServerURL.baseURL+storeBrand.getBrandLogo()));
//					brandList.add(storeBrand);
//				}
//				parmMap.put("brandInfo",brandList);
//				
//				
//				JSONArray jsonArray=dataJsonObj.getJSONArray("brand_pic");
//				List<ImagePic> brandPicList=new ArrayList<ImagePic>();
//				for(int i=0;i<jsonArray.length();i++){
//					ImagePic ip=new ImagePic();
//					ip.setUrl(jsonArray.getString(i));
//					ip.setBitmap(BitmapHelper.getBitmap(ServerURL.baseURL+ip.getUrl()));
//					brandPicList.add(ip);
//				}
//				parmMap.put("brandPic", brandPicList);
//				
//				List<BussinessTypeStore> storeList=new ArrayList<BussinessTypeStore>();
//				JSONArray storeRelatedJsonArray=dataJsonObj.getJSONArray("store_related");
//				for(int i=0;i<storeRelatedJsonArray.length();i++){
//					JSONObject storeObject=storeRelatedJsonArray.getJSONObject(i);
//					BussinessTypeStore storeDetail=new BussinessTypeStore();
//					storeDetail.setStoreId(storeObject.getString("store_id"));
//					storeDetail.setStoreName(storeObject.getString("store_name"));
//					storeDetail.setStoreAddr(storeObject.getString("store_addr"));
//					storeDetail.setStoreScope(storeObject.getString("store_scope"));
//					storeDetail.setStoreLogo(storeObject.getString("store_logo"));
//					storeDetail.setLogoBitmap(BitmapHelper.getBitmap(ServerURL.baseURL+storeDetail.getStoreLogo()));
//					storeList.add(storeDetail);
//				}
//				parmMap.put("storeRelated",storeList);
//			}else{
//				parmMap.put("data", "");
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return parmMap;
//	}
//	@Override
//	public int compareTo(Customer obj) {
//		if (null == obj) {
//			return 0;   
//		}else { 
//			String str1 = this.getInitialLable();
//		    String str2 = obj.getInitialLable();
//		    return  str1.compareTo(str2);
//		}   
//	}
}
