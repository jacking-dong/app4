package com.zhy.berrydic.ui;

import com.zhy.berrydic.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
/**
 * ∑≠“ÎΩÁ√Ê
 * @author Zhy
 * …œŒÁ09:24:19
 * BerryDictionary
 */
public class TranslateActivity extends Activity{
	private RelativeLayout relativeLayout;
	private GridView gridView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tranword);
		setTitle("∑≠“Î");
		init();
		initGridView();
	}
	private void init(){
		relativeLayout = (RelativeLayout) findViewById(R.id.re_layout);
	}
	private void initGridView(){
		CreateMenu createMenu = new CreateMenu(this);
		gridView = createMenu.createGridView();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		relativeLayout.addView(gridView, params);
	}

}
