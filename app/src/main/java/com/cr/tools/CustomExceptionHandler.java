package com.cr.tools;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;

public class CustomExceptionHandler extends Application {
	PendingIntent restartIntent; 
	 @Override   
	    public void onCreate() {   
	        super.onCreate();
	        // 以下用来捕获程序崩溃异常   
	        Intent intent = new Intent();   
	        // 参数1：包名，参数2：程序入口的activity   
	        intent.setClassName("com.xs.activity", "com.xs.activity.MainActivity");   
	        restartIntent = PendingIntent.getActivity(getApplicationContext(), 0,   
	                intent, Intent.FLAG_ACTIVITY_NEW_TASK);   
	        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程 
//	        Toast.makeText(CustomExceptionHandler.this.getApplicationContext(),"程序遇到意外情况，稍后将会为您重新启动程序！",Toast.LENGTH_SHORT).show();
	        
//	        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK); 
//	        registerReceiver(mBroadcastReceiver, filter); 
}   
	   
	    public UncaughtExceptionHandler restartHandler = new UncaughtExceptionHandler() {   
	        @Override   
	        public void uncaughtException(Thread thread, Throwable ex) {   
//	        	Thread.setDefaultUncaughtExceptionHandler(restartHandler);
//	        	Log.v("dddd", "啊：");
	        	ex.printStackTrace();
//	            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE); 
	            Toast.makeText(CustomExceptionHandler.this.getApplicationContext(),"程序遇到意外情况自动关闭，！",Toast.LENGTH_SHORT).show();
//	            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,   
//	                    restartIntent); // 1秒钟后重启应用   
	            MyApplication.getInstance().exit();
	        }   
	    };   
	    /**
	  	 * 接收广播信息
	  	 */
//	  	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
//	      @Override  
//	       public void onReceive(Context context, Intent intent) { 
//	           Log.v("dddd","服务广播接收器检测");
//	       }  
//	         
//	   };

}
