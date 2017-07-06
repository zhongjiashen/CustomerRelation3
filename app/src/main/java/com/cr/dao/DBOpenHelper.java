package com.cr.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author  coolszy
 * @blog    http://blog.csdn.net/coolszy
 * 创建本地Sqlite数据库的类
 */
public class DBOpenHelper extends SQLiteOpenHelper
{
	private static final int VERSION = 1;
	private static final String DBNAME = "CR.db";
	
	public DBOpenHelper(Context context)
	{
		super(context, DBNAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
//		Log.v("dddd", "我是小菜");
		db.execSQL("create table t_sign_in (kid integer primary key,time text,lng text,lat text)");
		db.execSQL("create table t_client_ip (id integer primary key,name text,ip text)");
		db.execSQL("create table t_tx (id integer primary key,name text,code text,time text,zq text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		
	}

}
