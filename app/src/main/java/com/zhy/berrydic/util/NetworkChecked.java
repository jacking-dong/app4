package com.zhy.berrydic.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ����Ƿ�������
 * @author Zhy
 * ����10:05:30
 * BerryDictionary
 */
public class NetworkChecked {
	public static boolean checkNetwork(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isAvailable()){//�ж������Ƿ���ڲ��ҿ���
			return true;
		}else{
			return false;
		}
	}
}
