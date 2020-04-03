package com.example.form;

import javax.validation.constraints.Pattern;

/**
 * 検索用フォーム
 * 
 * @author yuuki
 *
 */
public class SearchForm {
	
	/** ページ数 */
	@Pattern(regexp = "/^[0-9０-９]+$/", message = "数字を入力してください")
	private String pageNumberString;
	
	/** 現在のページ数 hiddenで隠しておく */
	private Integer nowPageNumberInt;
	
	public String getPageNumberString() {
		return pageNumberString;
	}
	// Integer型のpageNumberを返すメソッド
	public Integer getIntPageNumber() {
		return Integer.parseInt(pageNumberString);
	}
	
	public void setPageNumberString(String pageNumberString) {
		this.pageNumberString = pageNumberString;
	}
	
	public Integer getNowPageNumberInt() {
		return nowPageNumberInt;
	}
	public void setNowPageNumberInt(Integer nowPageNumberInt) {
		this.nowPageNumberInt = nowPageNumberInt;
	}
	
	@Override
	public String toString() {
		return "SearchForm [pageNumberString=" + pageNumberString + ", nowPageNumberInt=" + nowPageNumberInt + "]";
	}
	
}
