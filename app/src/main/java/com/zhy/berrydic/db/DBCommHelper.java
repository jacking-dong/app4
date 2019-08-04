package com.zhy.berrydic.db;

import com.zhy.berrydic.bean.DictionaryBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBCommHelper {
	private Context context;
	private DBHelper helper;
	private SQLiteDatabase db = null;
	public DBCommHelper(Context context) {
		this.context = context;
		openDB();
	}
	//打开数据库
	public void openDB(){
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	//关闭数据库
	public void closeDB(){
		db.close();
	}
	//需要查询的列名
	public String[] getColum(){
		return new String[]{DBHelper.NAME,DBHelper.AUDIO,DBHelper.PRON,DBHelper.DEF,DBHelper.XML};
	}
	//查询数据库中所有单词
	public Cursor getAllWords(){
		return db.query(DBHelper.TABLE_NAME, new String[]{DBHelper.ID,DBHelper.NAME}, null, null, null, null, null);
	}
	//根据id查询单词
	public Cursor queryById(int id){
		return db.query(DBHelper.TABLE_NAME, getColum(), DBHelper.ID +"="+id, null, null, null, null);
	}
	//根据ID删除单词
	public int deleteWordById(int id){
		return db.delete(DBHelper.TABLE_NAME, DBHelper.ID+"="+id, null);
	}
	public Cursor queryBySql(String sql,String args[]){
		Log.i("语句", "---------->"+sql);
		return db.rawQuery(sql, args);
	}
	//插入数据
	public long insertWord(DictionaryBean bean){
		ContentValues values = new ContentValues();
		values.put(DBHelper.NAME, bean.getWordName());
		values.put(DBHelper.AUDIO, bean.getDicAudio());
		values.put(DBHelper.PRON, bean.getDicPron());
		values.put(DBHelper.DEF, bean.getDicDef());
		values.put(DBHelper.XML, bean.getWordXml());
		Log.i("内容", "-------------"+values.size()+"    "+DBHelper.TABLE_NAME);
		return db.insert(DBHelper.TABLE_NAME, null, values);
	}
}
