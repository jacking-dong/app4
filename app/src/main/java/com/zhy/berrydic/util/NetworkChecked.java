package com.zhy.berrydic.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 检查是否有网络
 * @author Zhy
 * 上午10:05:30
 * BerryDictionary
 */
public class NetworkChecked {
	public static boolean checkNetwork(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isAvailable()){//判断网络是否存在并且可用
			return true;
		}else{
			return false;
		}
	}
}
