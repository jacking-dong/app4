package com.zhy.berrydic.util;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import com.zhy.berrydic.bean.DictionaryBean;
import com.zhy.berrydic.bean.SentBean;

import android.util.Xml;

public class XmlParseUtil {
	/**
	 * 获得XmlPullParser对象
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	private static XmlPullParser getXMLPullParse(String xml) throws Exception{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream,"UTF-8");
		XmlPullParser parser = Xml.newPullParser();//利用Android中提供的Xml类，来获得xml解析的对象XmlPullParse
		parser.setInput(inputStreamReader);//设置输入流
		return parser;
	}
	/**
	 * 解析xml
	 * @param xmlString
	 */
	public static DictionaryBean readContent(String xmlString){
		DictionaryBean dictionaryBean = null;
		try {
			XmlPullParser pullParser = getXMLPullParse(xmlString);
			int eventType = pullParser.getEventType();
			String sent = null;
			dictionaryBean = new DictionaryBean();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("lang".equals(pullParser.getName())) 
					{				
						dictionaryBean.setDicLang(pullParser.nextText());			
					}
					if ("audio".equals(pullParser.getName())) 
					{				
						dictionaryBean.setDicAudio(pullParser.nextText());			
					}
					if ("pron".equals(pullParser.getName())) 
					{				
						dictionaryBean.setDicPron(pullParser.nextText());			
					}
					if ("def".equals(pullParser.getName())) 
					{				
						dictionaryBean.setDicDef(pullParser.nextText());			
					}
					break;
				}
				eventType = pullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictionaryBean;
	}
	public static ArrayList<HashMap<String, String>> getSentContent(String param){
		ArrayList<String> origList = null;
		ArrayList<String> transList = null;
		ArrayList<HashMap<String, String>> sentList = null;
		try {
			XmlPullParser pullParser = getXMLPullParse(param);
			sentList = new ArrayList<HashMap<String,String>>();
			origList = new ArrayList<String>();
			transList = new ArrayList<String>();
			int eventType = pullParser.getEventType();
			SentBean sentBean = new SentBean();
			String sent = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("sent".equals(pullParser.getName())) 
					{				
						sent=pullParser.getName();						
					}
					if(sent!=null)
					{
						if("orig".equals(pullParser.getName())){
							origList.add(replcace(pullParser.nextText()));
						}
						if("trans".equals(pullParser.getName())){
							transList.add(pullParser.nextText());
						}
					}
					break;
				}
				eventType = pullParser.next();
			}
			
			HashMap<String, String> map=null;
			for(int i=0;i<origList.size();i++)
			{
				map=new HashMap<String, String>();
				map.put("orig", origList.get(i));
				map.put("trans", transList.get(i));
				sentList.add(map);
			}
			return sentList;			
		} catch (Exception ex) {
			return null;
		} finally {
			param = null;
		    origList = null;
		    transList = null;
		}
	}
	/**
	 * 替换某些内容中的<em>和</em>
	 * @param param
	 * @return
	 */
	private static String replcace(String param){
		param = param.replace("<em>", "");
		param = param.replace("</em>", "");
		return param;
	}
}
