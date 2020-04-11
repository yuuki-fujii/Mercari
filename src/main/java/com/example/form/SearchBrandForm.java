package com.example.form;

/**
 * ブランド検索用のフォーム.
 * 
 * @author yuuki
 *
 */
public class SearchBrandForm {
	
	/** ページ数 */
	private Integer pageNumber;
	/** ブランド名 */
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	@Override
	public String toString() {
		return "SearchBrandForm [pageNumber=" + pageNumber + ", name=" + name + "]";
	}
}
