package com.example.form;

import javax.validation.constraints.NotNull;

/**
 * 検索用フォーム
 * 
 * @author yuuki
 *
 */
public class SearchPageForm {
	

	
	/** 現在のページ数 hiddenで隠しておく */
	private Integer nowPageNumberInt;
	
	/** 検索用ページ数 ユーザが入力する */
	@NotNull(message = "数字を入力してください")
	private Integer pageNumberForSearch;

	public Integer getNowPageNumberInt() {
		return nowPageNumberInt;
	}

	public void setNowPageNumberInt(Integer nowPageNumberInt) {
		this.nowPageNumberInt = nowPageNumberInt;
	}

	public Integer getPageNumberForSearch() {
		return pageNumberForSearch;
	}

	public void setPageNumberForSearch(Integer pageNumberForSearch) {
		this.pageNumberForSearch = pageNumberForSearch;
	}

	@Override
	public String toString() {
		return "SearchForm [nowPageNumberInt=" + nowPageNumberInt + ", pageNumberForSearch=" + pageNumberForSearch
				+ "]";
	}
	
}
