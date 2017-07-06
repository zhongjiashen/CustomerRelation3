package com.cr.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUserInfo {
//	//保存当前是不是第一次运行的状态
//	public static void setFirstCount(Context context, boolean isFirst) {
//		SharedPreferences sp = context.getSharedPreferences("busqueryInfo",
//				Context.MODE_PRIVATE);
//		sp.edit().putBoolean("isFirst", isFirst).commit();
//	}
//	//取出当前是不是第一次运行的状态
//	public static boolean getFirstCount(Context context){
//		//'busqueryInfo'存储这些键值对信息的xml的名字，理论上是可以随便起名字的，最好还是使用一个有意义的名字
//		//因为只生成一个文件就可以保存所有的用户重要的信息
//		SharedPreferences sp = context.getSharedPreferences("busqueryInfo",
//				Context.MODE_PRIVATE);
//		//默认是第一次运行
//		return sp.getBoolean("isFirst", true);
//	}
	
//	//保存当前的版本号信息
//	public static void setVersion(Context context,Map<String , String> map){
//		SharedPreferences sp=context.getSharedPreferences("busqueryInfo", Context.MODE_PRIVATE);
//		if(map!=null&&map.get("version")!=null&&map.get("apkName")!=null){
//			sp.edit().putString("version", map.get("version")).commit();
//			sp.edit().putString("apkName", map.get("apkName")).commit();
//		}else{
//			sp.edit().putString("version", null).commit();
//			sp.edit().putString("apkName",null).commit();
//		}
//	}
//	//获得当前的版本号信息
//	public static Map<String,String> getVersion(Context context){
//		Map<String, String> map=new HashMap<String, String>();
//		SharedPreferences sp=context.getSharedPreferences("busqueryInfo",Context.MODE_PRIVATE);
//		map.put("version", sp.getString("version",null));
//		map.put("apkName", sp.getString("apkName",null));
//		return map;
//	}
	
	//保存当前用户的ID
	public static void setUserId(Context context, String id) {
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		sp.edit().putString("userId", id).commit();
	}
	//取出当前是不是第一次运行的状态
	public static String getUserId(Context context){
		//'busqueryInfo'存储这些键值对信息的xml的名字，理论上是可以随便起名字的，最好还是使用一个有意义的名字
		//因为只生成一个文件就可以保存所有的用户重要的信息
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		//默认是1
		return sp.getString("userId", "1");
	}
	//保存当前用户的ID
		public static void setUserName(Context context, String id) {
			SharedPreferences sp = context.getSharedPreferences("CRInfo",
					Context.MODE_PRIVATE);
			sp.edit().putString("userName", id).commit();
		}
		//取出当前是不是第一次运行的状态
		public static String getUserName(Context context){
			//'busqueryInfo'存储这些键值对信息的xml的名字，理论上是可以随便起名字的，最好还是使用一个有意义的名字
			//因为只生成一个文件就可以保存所有的用户重要的信息
			SharedPreferences sp = context.getSharedPreferences("CRInfo",
					Context.MODE_PRIVATE);
			//默认是1
			return sp.getString("userName", "");
		}
	//保存当前用户的ID
	public static void setDbName(Context context, String dbName) {
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		sp.edit().putString("dbName", dbName).commit();
	}
	//取出当前是不是第一次运行的状态
	public static String getDbName(Context context){
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		//默认是1
		return sp.getString("dbName", "");
	}
	//保存当前用户链接的IP
	public static void setIP(Context context, String ip) {
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		sp.edit().putString("ip", ip).commit();
	}
	//取出当前是不是第一次运行的状态
	public static String getIP(Context context){
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		//默认是1
		return sp.getString("ip", "");
	}
	//保存当前用户链接的IP
	public static void setDK(Context context, String ip) {
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		sp.edit().putString("DK", ip).commit();
	}
	//取出当前是不是第一次运行的状态
	public static String getDK(Context context){
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		//默认是1
		return sp.getString("DK", "");
	}
	//存值
	public static void setKey(Context context,String key,String value){
		SharedPreferences sp = context.getSharedPreferences("CRInfo",
				Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	//读值
	public static String getKey(Context context,String key){
		SharedPreferences sp = context.getSharedPreferences("CRInfo",Context.MODE_PRIVATE);
		//默认是1
		return sp.getString(key, "");
	}
}
