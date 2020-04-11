package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * ブランド追加用フォーム.
 * 
 * @author yuuki
 *
 */
public class AddBrandForm {
	/** ページ数 */
	private Integer pageNumber;
	/** ブランド名 */
	@NotBlank(message = "ブランド名を入力してください")
	private String name;
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "AddBrandForm [pageNumber=" + pageNumber + ", name=" + name + "]";
	}
}
