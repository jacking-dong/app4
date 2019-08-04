package com.zhy.berrydic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "Berry.db";
	public static final String ID="_id";
	public static final String NAME="name";
	public static final String AUDIO="audio";
	public static final String PRON="pron";	
	public static final String DEF="def";	
	public static final String XML="xml";
	
	public static final String TABLE_NAME = "berry_word";
	private String table = "create table "+TABLE_NAME+"( "+ID+" integer primary key,"+NAME+" text,"
						+AUDIO+" text,"+PRON+" text,"+DEF+" text,"+XML+" text"+")";
	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(table);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);
	}

}
