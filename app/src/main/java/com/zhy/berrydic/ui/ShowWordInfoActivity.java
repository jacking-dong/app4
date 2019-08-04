package com.zhy.berrydic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.zhy.berrydic.R;
import com.zhy.berrydic.bean.DictionaryBean;
import com.zhy.berrydic.db.DBCommHelper;
import com.zhy.berrydic.util.CommUtil;
import com.zhy.berrydic.util.XmlParseUtil;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShowWordInfoActivity extends Activity implements OnClickListener{
	private TextView wordTextView;
	private Button audioButton;
	private TextView pronTextView;
	private TextView defTextView;
	private View linesView;
	private ListView listView;
	private String wordId = null;
	private Cursor cursor;
	private ArrayList<HashMap<String, String>> sentList;
	private DictionaryBean bean;
	private DBCommHelper commHelper;
	private SimpleAdapter adapter;
	
	//与对话框有关
	private Dialog dialog;
	private View dialogView;
	private TextView msgTextView;
	private Button readButton;
	private TextView transTextView;
	private TextToSpeech speech;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showword);
		setTitle("生词信息");
		wordId = (String) getIntent().getExtras().get("id");
		init();
		commHelper = new DBCommHelper(this);
		findWordInfoById(Integer.parseInt(wordId));
	}
	//初始化
	private void init(){
		wordTextView = (TextView) findViewById(R.id.show_tv_word);
		pronTextView = (TextView) findViewById(R.id.show_tv_pron);
		audioButton = (Button) findViewById(R.id.show_btn_aduio);
		audioButton.setOnClickListener(this);
		defTextView = (TextView) findViewById(R.id.show_tv_def);
		linesView = findViewById(R.id.show_lines);
		listView = (ListView) findViewById(R.id.show_listview);
		speech = new TextToSpeech(this, new OnInitListener() {
			public void onInit(int status) {
				if(status == TextToSpeech.SUCCESS){
					int language = speech.setLanguage(Locale.ENGLISH);
					if(language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED){
						Log.i("","");
					}
				}
			}
		});
	}
	private void findWordInfoById(int id){
		cursor = commHelper.queryById(id);
		if(cursor!=null && cursor.getCount()>0){
			cursor.moveToFirst();
			bean = new DictionaryBean();
			bean.setWordName(cursor.getString(0));
			bean.setDicAudio(cursor.getString(1));
			bean.setDicPron(cursor.getString(2));
			bean.setDicDef(cursor.getString(3));
			bean.setWordXml(cursor.getString(4));
		}
		cursor.close();
		commHelper.closeDB();
		wordTextView.setText(bean.getWordName());
		pronTextView.setText(bean.getDicPron());
		defTextView.setText(bean.getDicDef());
		linesView.setVisibility(View.VISIBLE);
		audioButton.setVisibility(View.VISIBLE);
		sentList = XmlParseUtil.getSentContent(bean.getWordXml());
		adapter = new SimpleAdapter(this,sentList,R.layout.listitem,
				new String[]{"orig" , "trans"},new int[]{R.id.tv_orig,R.id.tv_trans});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				speechDialog(sentList.get(arg2).get("orig"),sentList.get(arg2).get("trans"));
			}
		});
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show_btn_aduio:
			CommUtil.playWord(bean.getDicAudio());
			break;

		default:
			break;
		}
	}
	private void speechDialog(final String msg,String tranMsg){
		dialog = new Dialog(this, R.style.dialog_style);
		dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
		dialog.setContentView(dialogView);
		msgTextView = (TextView) dialogView.findViewById(R.id.example_tv);
		transTextView = (TextView) dialogView.findViewById(R.id.tran_tv);
		transTextView.setText("-- "+tranMsg);
		msgTextView.setText(msg);
		readButton = (Button) dialogView.findViewById(R.id.read);
		readButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				speech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
			}
		});
		dialog.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			commHelper.closeDB();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {
		if(speech!=null){
			speech.stop();
			speech.shutdown();
		}
		super.onDestroy();
	}

}
