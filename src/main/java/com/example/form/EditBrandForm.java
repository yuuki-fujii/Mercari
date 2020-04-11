package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * 商品編集用フォーム.
 * 
 * @author yuuki
 *
 */
public class EditBrandForm {
	
	/** ページ数 */
	private Integer pageNumber;
	/** 主キー */
	private Integer id;
	/** ブランド名 */
	@NotBlank(message = "ブランド名を入力してください")
	private String name;
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "EditBrandForm [pageNumber=" + pageNumber + ", id=" + id + ", name=" + name + "]";
	}
}
