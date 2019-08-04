package com.zhy.berrydic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.zhy.berrydic.R;
import com.zhy.berrydic.bean.DictionaryBean;
import com.zhy.berrydic.bean.SentBean;
import com.zhy.berrydic.constant.ConstantUtil;
import com.zhy.berrydic.db.DBCommHelper;
import com.zhy.berrydic.util.CommUtil;
import com.zhy.berrydic.util.HttpUtil;
import com.zhy.berrydic.util.NetworkChecked;
import com.zhy.berrydic.util.XmlParseUtil;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
/**
 * ���ҵ��ʽ���
 * @author Zhy
 * ����03:00:29
 * BerryDictionary
 */
public class BerryDictionaryActivity extends Activity implements OnClickListener{
	private EditText wordEditText;
	private Button searchButton;
	private String contentString;
	private ArrayList<HashMap<String, String>> sentArrayList;
	private GridView gridView;
	private RelativeLayout relativeLayout;
	private DictionaryBean dictionaryBean;
	private View lineView;
	
	//��ʾ����ҵ��ʵĿؼ�
	private TextView wordText;//��ʾ����
	private TextView pronText;//��ʾ����
	private Button audioButton;//mp3��ť
	private Button noteButton;//�������ʰ�ť
	private TextView refTextView;
	private ListView listView;
	private SimpleAdapter adapter;//������
	
	private String word;
	private ProgressDialog progressDialog;
	private static final int SEARCH_END = 0x102;
	private String contentUrl = null;
	private DBCommHelper commHelper;
	
	//��Ի����й�
	private Dialog dialog;
	private View dialogView;
	private TextView msgTextView;
	private Button readButton;
	private TextToSpeech speech;
	private TextView transTextView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("���ʲ���");
        init();
        initGridView();
    }
    //��ʼ��
    private void init(){
    	wordEditText = (EditText) findViewById(R.id.inputText);
        searchButton = (Button) findViewById(R.id.searchBtn);
        relativeLayout = (RelativeLayout) findViewById(R.id.re_layout);
        wordText = (TextView) findViewById(R.id.tv_word);
        pronText = (TextView) findViewById(R.id.tv_pron);
        refTextView = (TextView) findViewById(R.id.tv_def);
        listView = (ListView) findViewById(R.id.listview);
        audioButton = (Button) findViewById(R.id.btn_aduio);
        lineView = findViewById(R.id.lines);
        noteButton = (Button) findViewById(R.id.btn_add);
        //����¼�
        searchButton.setOnClickListener(this);
        audioButton.setOnClickListener(this);
        noteButton.setOnClickListener(this);
        speech = new TextToSpeech(this, new OnInitListener() {
			
			public void onInit(int status) {
				if (status == TextToSpeech.SUCCESS) {
					int result = speech.setLanguage(Locale.ENGLISH);
					if (result == TextToSpeech.LANG_MISSING_DATA
							|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Log.e("lanageTag", "not use");
					}
				}
			}
		});
    }
    private void initGridView(){
    	CreateMenu createMenu = new CreateMenu(this);
    	gridView = createMenu.createGridView();
    	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	    relativeLayout.addView(gridView, params);
    }
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchBtn:
			if(NetworkChecked.checkNetwork(this)){
				if(wordEditText.getText().toString().trim().equals("")){
					clearUI();
				}else{
					clearUI();
					
					searchWord();
				}
			}
			break;
		case R.id.btn_aduio:
			CommUtil.playWord(dictionaryBean.getDicAudio());
			break;
		case R.id.btn_add:
			addWordToDB();
			break;
		default:
			break;
		}
	}
	//��ѯ����
	private void searchWord(){
		word = wordEditText.getText().toString().trim();
		if(null != word && !("").equals(word)){
			progressDialog = ProgressDialog.show(this, "���ڲ�ѯ....", "Loading....");
			new Thread(new Runnable() {
				public void run() {
					contentUrl = HttpUtil.getNetData(ConstantUtil.URL+word);
					dictionaryBean = XmlParseUtil.readContent(contentUrl);//
					sentArrayList = XmlParseUtil.getSentContent(contentUrl);
					Message msg = new Message();
					msg.what = SEARCH_END;
					handler.sendMessage(msg);
				}
			}
			).start();
		}
	}
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SEARCH_END:
				progressDialog.dismiss();
				showSearchResult();
				break;
				
			default:
				break;
			}
		};
	};
	//�ڽ�������ʾ��ѯ�Ľ��
	private void showSearchResult(){
		wordText.setText(word);
		noteButton.setVisibility(View.VISIBLE);
		lineView.setVisibility(View.VISIBLE);
		if(dictionaryBean.getDicAudio() != null && !dictionaryBean.getDicAudio().equals("")){
			audioButton.setVisibility(View.VISIBLE);
		}
		if(dictionaryBean.getDicPron()!= null && !dictionaryBean.getDicPron().equals("")){
			pronText.setText("["+dictionaryBean.getDicPron()+"]");
		}
		refTextView.setText(dictionaryBean.getDicDef());
		if(sentArrayList != null){
			adapter = new SimpleAdapter(this,sentArrayList,R.layout.listitem,
					new String[]{"orig" , "trans"},new int[]{R.id.tv_orig,R.id.tv_trans});
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					speechDialog(sentArrayList.get(arg2).get("orig"),sentArrayList.get(arg2).get("trans"));
				}
			});
		}
	}
	//�����Ļ
    private void clearUI(){
    	pronText.setText("");
    	refTextView.setText("");
    	noteButton.setVisibility(View.GONE);
    	audioButton.setVisibility(View.GONE);
    	lineView.setVisibility(View.GONE);
    }
    //������ʵ����ݿ�
    private void addWordToDB(){
    	commHelper = new DBCommHelper(BerryDictionaryActivity.this);
    	DictionaryBean wordBean =  new DictionaryBean();
    	wordBean.setWordName(wordText.getText().toString());
    	wordBean.setDicAudio(dictionaryBean.getDicAudio());
    	wordBean.setDicPron(pronText.getText().toString());
    	wordBean.setDicDef(refTextView.getText().toString());
    	
    	wordBean.setWordXml(contentUrl);
    	commHelper.insertWord(wordBean);
    	CommUtil.showTaost(this, "��ӳɹ�����", 1000);
    	
    }
    private void speechDialog(final String msg,String tranMsg){
    	dialog = new Dialog(this, R.style.dialog_style);
    	dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
    	dialog.setContentView(dialogView);
    	msgTextView = (TextView) dialogView.findViewById(R.id.example_tv);
    	readButton = (Button) dialogView.findViewById(R.id.read);
    	msgTextView.setText(msg);
    	transTextView = (TextView) dialogView.findViewById(R.id.tran_tv);
    	transTextView.setText("-- "+tranMsg);
    	readButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				speech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
			}
		});
    	dialog.show();
    }
    @Override
    protected void onDestroy() {
    	if(speech !=null){
    		speech.stop();
    		speech.shutdown();
    	}
    	super.onDestroy();
    }
    
}