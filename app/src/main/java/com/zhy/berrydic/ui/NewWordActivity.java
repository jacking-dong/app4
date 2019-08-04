package com.zhy.berrydic.ui;

import java.util.ArrayList;

import com.zhy.berrydic.R;
import com.zhy.berrydic.adapter.WordAdapter;
import com.zhy.berrydic.bean.DictionaryBean;
import com.zhy.berrydic.db.DBCommHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
/**
 * 生词界面
 * @author Zhy
 * 下午04:30:25
 * BerryDictionary
 */
public class NewWordActivity extends Activity{
	private RelativeLayout relativeLayout;
	private ListView listView;
	private GridView gridView;
	private ArrayList<DictionaryBean> list;
	private DBCommHelper commHelper;
	private Cursor cursor;
	private WordAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newword);
		setTitle("生词库");
		init();
		initMenu();
	}
	private void init(){
		relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout01);
		listView = (ListView) findViewById(R.id.wordbook_list);
		
		list = new ArrayList<DictionaryBean>();
		commHelper = new DBCommHelper(this);
		cursor = commHelper.getAllWords();
		cursor.moveToFirst();
		while(!cursor.isAfterLast()&& (cursor.getString(1)!=null)){
			DictionaryBean bean = new DictionaryBean();
			bean.setId(cursor.getString(0));
			bean.setWordName(cursor.getString(1));
			list.add(bean);
			cursor.moveToNext();
		}
		adapter = new WordAdapter(this, list);
		listView.setAdapter(adapter);
		//点击listview的事件
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent();
				intent.putExtra("id", list.get(arg2).getId());
				intent.setClass(NewWordActivity.this, ShowWordInfoActivity.class);
				startActivity(intent);
			}
		});
	}
	private void initMenu(){
		CreateMenu createMenu = new CreateMenu(this);
		gridView = createMenu.createGridView();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	    relativeLayout.addView(gridView, params);
	}
}
