package com.zhy.berrydic.util;

import java.io.IOException;

import com.zhy.berrydic.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CommUtil {
	/**
	 * 播放单词
	 */
	public static void playWord(String path){
		MediaPlayer player = null;
		try {
			player = new MediaPlayer();
			player.setDataSource(path);
			player.prepare();
			player.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			player = null;
		}
	}
	/**
	 * 提示
	 * @param context
	 * @param msg
	 * @param time
	 */
	public static void showTaost(Context context,String msg,int time){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.toast, null);
		TextView textView = (TextView) view.findViewById(R.id.toastText);
		textView.setText(msg);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setDuration(time);
		toast.setView(view);
		toast.show();

	}

}
