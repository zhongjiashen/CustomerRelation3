package com.cr.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class MyTestDao {

	private DBOpenHelper helper;
	private SQLiteDatabase db;

	/**
	 * 实例化类的时候，创建数据库对象
	 * @param context
	 */
	public MyTestDao(Context context)
	{
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}
	
//	/**
//	 * 关键字插入到表中
//	 * @param hotSearch
//	 */
//	public void add(HotSearch hotSearch)
//	{
////		db = helper.getWritableDatabase();
//		Cursor cursor = db.rawQuery("select name from t_keyword t where t.name=?", new String[]{hotSearch.getSearchName()});
//		if(!cursor.moveToNext()){
//			db.execSQL("insert into t_keyword (name) values (?)", new Object[]{hotSearch.getSearchName()});
//		}
//	}
//	
//	/**
//	 * 查询所有的关键字信息
//	 */
//	public List<HotSearch> findAllHotSearch()
//	{
//		List<HotSearch> hotSearchList=new ArrayList<HotSearch>();
//		
//		Cursor cursor = db.rawQuery("select name from t_keyword", null);
//		while (cursor.moveToNext())
//		{
//			HotSearch hotSearch=new HotSearch();
//			hotSearch.setSearchName(cursor.getString(cursor.getColumnIndex("name")));
//			hotSearchList.add(hotSearch);
//		}
//		return hotSearchList;
//	}
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
