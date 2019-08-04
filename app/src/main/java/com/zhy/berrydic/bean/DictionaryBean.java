package com.zhy.berrydic.bean;
/**
 * 实体类
 * @author Zhy
 * 下午01:30:19
 * BerryDictionary
 */
public class DictionaryBean {
	private String dicAudio;
	private String dicLang;
	private String dicPron;
	private String dicDef;
	private String wordXml;
	private String wordName;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWordName() {
		return wordName;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	public String getDicAudio() {
		return dicAudio;
	}
	public void setDicAudio(String dicAudio) {
		this.dicAudio = dicAudio;
	}
	public String getDicLang() {
		return dicLang;
	}
	public void setDicLang(String dicLang) {
		this.dicLang = dicLang;
	}
	public String getDicPron() {
		return dicPron;
	}
	public void setDicPron(String dicPron) {
		this.dicPron = dicPron;
	}
	public String getDicDef() {
		return dicDef;
	}
	public void setDicDef(String dicDef) {
		this.dicDef = dicDef;
	}
	public String getWordXml() {
		return wordXml;
	}
	public void setWordXml(String wordXml) {
		this.wordXml = wordXml;
	}
}
