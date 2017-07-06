package com.cr.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;

import com.cr.model.CustomerTel;


public class TelDao {
	private Context context;
	public TelDao() {
		
	}
	public TelDao(Context context) {
		this.context=context;
	}
	public List<CustomerTel> findTel(String time){
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calenda=Calendar.getInstance();
		long first = 0;
		long end = 0;
		try {
			Date date=sfd.parse(time+" 00:00:00");
			calenda.setTime(date);
			first=calenda.getTimeInMillis();
			Date date2=sfd.parse(time+" 23:59:59");
			calenda.setTime(date2);
			end=calenda.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] selectionArgs = {first+"",end+""};
		String selection= Calls.DATE+">? and "+ Calls.DATE+"<?";
		Cursor cursor = context.getContentResolver().query(Calls.CONTENT_URI,new String[] {Calls.NUMBER, Calls.CACHED_NAME, Calls.TYPE, Calls.DATE, Calls.DURATION},
				selection, selectionArgs, Calls.DEFAULT_SORT_ORDER);
		List<CustomerTel> customerTelList=new ArrayList<CustomerTel>();
		if(cursor.moveToFirst()){                                                                                  
			do{                                                                                                    
//				CallLog calls =new CallLog();                                                                    
				//号码                                                                                               
				String number = cursor.getString(cursor.getColumnIndex(Calls.NUMBER));                             
				//呼叫类型                                                                                             
				String type;                                                                                       
				switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(Calls.TYPE)))) {                   
					case Calls.INCOMING_TYPE:                                                                          
					type = "呼入";                                                                                   
					break;                                                                                         
					case Calls.OUTGOING_TYPE:                                                                          
					type = "呼出";                                                                                   
					break;                                                                                         
					case Calls.MISSED_TYPE:                                                                            
					type = "未接";                                                                                   
					break;                                                                                         
					default:                                                                                           
					type = "挂断";//应该是挂断.根据我手机类型判断出的                                                                
					break;                                                                                         
				}                                                                                                  
				Long l=Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(Calls.DATE)));  
				//呼叫时间                                                                                             
//				String time = sfd.format(date);  
//				Log.v("dddd", "l:"+l);
				//联系人                                                                                              
				 String name = cursor.getString(cursor.getColumnIndexOrThrow(Calls.CACHED_NAME));
				 if(name==null){
					 name="陌生人";
				 }
				 Calendar c=Calendar.getInstance();
				 c.setTimeInMillis(l);
				//通话时间,单位:s                                                                                        
				String duration = cursor.getString(cursor.getColumnIndexOrThrow(Calls.DURATION));
				CustomerTel customerTel=new CustomerTel();
				customerTel.setTel(number);
				customerTel.setType(type);
				customerTel.setTime(sfd.format(c.getTime()));
				customerTel.setName(name);
				customerTelList.add(customerTel);
				customerTel.setDuration("时长："+duration+"秒");
//				if(i>16){
//					break;
//				}
			}while(cursor.moveToNext());  
		} 
		if(!cursor.isClosed()){
			cursor.close();
		}
		return customerTelList;
	}
}