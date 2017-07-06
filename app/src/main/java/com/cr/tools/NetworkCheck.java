package com.cr.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态检测类
 * @author  
 *
 */
public class NetworkCheck {
	
	/**
	 * 测网络状态的方法
	 * @param context
	 * @return
	 */
	public static NetworkInfo check(Context context){
		
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统的连接服务
		NetworkInfo activeNetInfo = connectivityManager
				.getActiveNetworkInfo();// 获取网络的连接情况
//		Log.v("dddd",activeNetInfo.getTypeName());
		return activeNetInfo;
	}

}
