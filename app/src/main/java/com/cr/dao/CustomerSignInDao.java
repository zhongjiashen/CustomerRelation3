package com.cr.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cr.model.CustomerSignIn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class CustomerSignInDao {

	private DBOpenHelper helper;
	private SQLiteDatabase db;
	private String nowDate;

	/**
	 * 实例化类的时候，创建数据库对象
	 * @param context
	 */
	public CustomerSignInDao(Context context)
	{
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nowDate=sdf.format(new Date());
	}
	
	/**
	 * 签到记录插入到表中
	 * @param hotSearch
	 */
	public void add(CustomerSignIn customerSignIn)
	{
		db.execSQL("insert into t_sign_in (time,lng,lat) values (?,?,?)", new Object[]{nowDate,customerSignIn.getLng(),customerSignIn.getLat()});
	}
	
	/**
	 * 查询所有的关键字信息
	 */
	public List<CustomerSignIn> findAllHotSearch()
	{
		List<CustomerSignIn> customerSignInList=new ArrayList<CustomerSignIn>();
		
		Cursor cursor = db.rawQuery("select time,lng,lat from t_sign_in order by time desc", null);
		while (cursor.moveToNext())
		{
			CustomerSignIn customerSignIn=new CustomerSignIn();
			customerSignIn.setTime(cursor.getString(cursor.getColumnIndex("time")));
			customerSignIn.setLng("经维度"+cursor.getString(cursor.getColumnIndex("lng")));
			customerSignIn.setLat(","+cursor.getString(cursor.getColumnIndex("lat")));
			customerSignInList.add(customerSignIn);
		}
		return customerSignInList;
	}
//	/**
//	 * 删除所有的关键字信息
//	 */
//	public int deleteAllHotSearch(){
//		try {
//			db.execSQL("delete from t_keyword");
//			return 0;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 1;
//		}
//	}
}
