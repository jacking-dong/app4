package com.zhy.berrydic.adapter;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.berrydic.R;
import com.zhy.berrydic.bean.DictionaryBean;
import com.zhy.berrydic.db.DBCommHelper;
/**
 * ���ʱ�������
 * @author Zhy
 * ����05:29:03
 * BerryDictionary
 */
public class WordAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<DictionaryBean> list;
	private LayoutInflater inflater;
	private DBCommHelper commHelper;
	public WordAdapter(Context context,ArrayList<DictionaryBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		commHelper = new DBCommHelper(context);
	}
	public int getCount() {
		return list.size();
	}
	public Object getItem(int position) {
		return position;
	}
	public long getItemId(int position) {
		return position;
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.show_word_adapter, null);
		final TextView wordText = (TextView) convertView.findViewById(R.id.word_new);
		Button delButton = (Button) convertView.findViewById(R.id.btn_delete);
		TextView wordIdText = (TextView) convertView.findViewById(R.id.word_id_tv);
		wordText.setText(list.get(position).getWordName());
		wordIdText.setText(list.get(position).getId());
		//ɾ����ť�¼�
		delButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("�Ƿ�ɾ�����ʡ�"+wordText.getText().toString()+"��");
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						commHelper.deleteWordById(Integer.parseInt(list.get(position).getId()));
						list.remove(position);
						notifyDataSetChanged();
						Toast.makeText(context, "ɾ���ɹ���", Toast.LENGTH_SHORT).show();
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		});
		return convertView;
	}

}
