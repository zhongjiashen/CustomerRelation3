package com.cr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class TxDao {

	private DBOpenHelper helper;
	private SQLiteDatabase db;

	/**
	 * 实例化类的时候，创建数据库对象
	 * @param context
	 */
	public TxDao(Context context)
	{
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}
	
	/**
	 * 关键字插入到表中
	 * @param hotSearch
	 */
	public void add(final Map<String, Object> map)
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				db.execSQL("insert into t_tx (name,code,time,zq) values (?,?,?,?)", new Object[]{
						map.get("name").toString(),map.get("code").toString(),
						map.get("time").toString(),map.get("zq").toString()});
			}
		}).start();
	}
	
	/**
	 * 查询所有的关键字信息
	 */
	public List<Map<String, Object>> findAllHotSearch()
	{
		List<Map<String, Object>>  mapList=new ArrayList<Map<String, Object>> ();
		
		Cursor cursor = db.rawQuery("select id,name,code,time from t_keyword", null);
		while (cursor.moveToNext())
		{
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("id",cursor.getString(cursor.getColumnIndex("id")));
			map.put("name",cursor.getString(cursor.getColumnIndex("name")));
			map.put("code",cursor.getString(cursor.getColumnIndex("code")));
			map.put("time",cursor.getString(cursor.getColumnIndex("time")));
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 删除提醒信息
	 */
	public int deleteTx(int id){
		try {
			db.execSQL("delete from t_tx where id=?",new Object[]{id});
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
}
