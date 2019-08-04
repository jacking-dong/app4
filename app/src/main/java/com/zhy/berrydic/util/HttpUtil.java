package com.zhy.berrydic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 获取网络的数据
 * @author Zhy
 * 下午02:39:54
 * BerryDictionary
 */
public class HttpUtil {
	/**
	 * 获取数据
	 * @param urlString url
	 * @return
	 */
	public static String getNetData(String urlString){
		URLConnection conn = null;
		try {
			URL url = new URL(urlString);
			conn = url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream mInputStream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));
			String readContent;
			int line = 0;
			StringBuffer buffer = new StringBuffer();
			while((readContent = reader.readLine()) != null){
				if(line != 0){
					buffer.append("\n");
				}
				buffer.append(readContent);
				line++;
			}
			reader.close();
			return buffer.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
