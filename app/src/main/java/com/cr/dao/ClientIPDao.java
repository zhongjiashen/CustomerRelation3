package com.cr.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cr.model.ClientIP;


public class ClientIPDao {

	private DBOpenHelper helper;
	private SQLiteDatabase db;

	/**
	 * 实例化类的时候，创建数据库对象
	 * @param context
	 */
	public ClientIPDao(Context context)
	{
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}
	
	/**
	 * 将链接的IP地址插入到表中
	 * @param hotSearch
	 */
	public void add(ClientIP clientIP)
	{
		Cursor cursor = db.rawQuery("select  name from t_client_ip t where name=? and ip=?", new String[]{clientIP.getIp(),clientIP.getDk()});
		if (!cursor.moveToNext()){
			db.execSQL("insert into t_client_ip (name,ip) values (?,?)", new Object[]{clientIP.getIp(),clientIP.getDk()});
		}
	}
	
	/**
	 * 查询所有的关键字信息
	 */
	public List<ClientIP> findAllIP()
	{
		List<ClientIP> clientIPList=new ArrayList<ClientIP>();
		
		Cursor cursor = db.rawQuery("select name,ip from t_client_ip order by id desc ", null);
		while (cursor.moveToNext())
		{
			ClientIP clientIp=new ClientIP();
			clientIp.setIp(cursor.getString(cursor.getColumnIndex("name")));
			clientIp.setDk(cursor.getString(cursor.getColumnIndex("ip")));
			clientIPList.add(clientIp);
		}
		return clientIPList;
	}
//	/**
//	 * 删除所有的关键字信息
//	 */
//	public int deleteClientIp(){
//		try {
//			db.execSQL("delete from t_client_ip");
//			return 0;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 1;
//		}
//	}
}
