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
	//�����ݿ�
	public void openDB(){
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	//�ر����ݿ�
	public void closeDB(){
		db.close();
	}
	//��Ҫ��ѯ������
	public String[] getColum(){
		return new String[]{DBHelper.NAME,DBHelper.AUDIO,DBHelper.PRON,DBHelper.DEF,DBHelper.XML};
	}
	//��ѯ���ݿ������е���
	public Cursor getAllWords(){
		return db.query(DBHelper.TABLE_NAME, new String[]{DBHelper.ID,DBHelper.NAME}, null, null, null, null, null);
	}
	//����id��ѯ����
	public Cursor queryById(int id){
		return db.query(DBHelper.TABLE_NAME, getColum(), DBHelper.ID +"="+id, null, null, null, null);
	}
	//����IDɾ������
	public int deleteWordById(int id){
		return db.delete(DBHelper.TABLE_NAME, DBHelper.ID+"="+id, null);
	}
	public Cursor queryBySql(String sql,String args[]){
		Log.i("���", "---------->"+sql);
		return db.rawQuery(sql, args);
	}
	//��������
	public long insertWord(DictionaryBean bean){
		ContentValues values = new ContentValues();
		values.put(DBHelper.NAME, bean.getWordName());
		values.put(DBHelper.AUDIO, bean.getDicAudio());
		values.put(DBHelper.PRON, bean.getDicPron());
		values.put(DBHelper.DEF, bean.getDicDef());
		values.put(DBHelper.XML, bean.getWordXml());
		Log.i("����", "-------------"+values.size()+"    "+DBHelper.TABLE_NAME);
		return db.insert(DBHelper.TABLE_NAME, null, values);
	}
}
