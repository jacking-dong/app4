package com.zhy.berrydic.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.zhy.berrydic.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 底部菜单条
 * @author Zhy
 * 下午03:26:26
 * BerryDictionary
 */
public class CreateMenu {
	private GridView gridView;
	private int[] menu_toolbar_image_array = new int[]{R.drawable.search_btn,R.drawable.tran_btn,R.drawable.note_btn};
	private String[] menu_toolbar_name_array = new String[]{"查词","翻译","生词本"};
	private  Context context;
	public CreateMenu(Context context) {//构造函数
		this.context = context;
	}
	public GridView createGridView(){
		gridView = new GridView(context);
		gridView.setBackgroundResource(R.drawable.bg_toolbar);
		gridView.setCacheColorHint(Color.TRANSPARENT);
		gridView.setNumColumns(3);
		gridView.setGravity(Gravity.CENTER);
		gridView.setVerticalSpacing(5);
		gridView.setHorizontalSpacing(5);
		gridView.setAdapter(getAdapter(menu_toolbar_image_array, menu_toolbar_name_array));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent();
				switch (arg2) {
				case 0:
					intent.setClass(context, BerryDictionaryActivity.class);
					context.startActivity(intent);
					break;
				case 1:
					intent.setClass(context, TranslateActivity.class);
					context.startActivity(intent);
					break;
				case 2:
					intent.setClass(context, NewWordActivity.class);
					context.startActivity(intent);
					break;

				default:
					break;
				}
			}
		});
		return gridView;
	}
	private SimpleAdapter getAdapter(int[] imageNameArray,String[] menuNameArray){
		ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageNameArray[i]);
			map.put("itemText", menuNameArray[i]);
			arrayList.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(context, arrayList, R.layout.item_menu,
				new String[] { "itemImage", "itemText" }, new int[]{R.id.item_image,R.id.item_text});
		return adapter;
	}

}
