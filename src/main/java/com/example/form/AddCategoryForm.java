package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * カテゴリ追加用フォーム.
 * 
 * @author yuuki
 *
 */
public class AddCategoryForm {
	
	/** ページ数 */
	private Integer pageNumber;
	
	/** カテゴリ名 */
	@NotBlank(message = "カテゴリ名を入力してください")
	private String name;
	
	/** 親カテゴリid (中、小カテゴリで使用) */
	private Integer parentId;
	
	/** 全カテゴリ名（小カテゴリで使用） */
	private String nameAll;

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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	@Override
	public String toString() {
		return "AddCategoryForm [pageNumber=" + pageNumber + ", name=" + name + ", parentId=" + parentId + ", nameAll="
				+ nameAll + "]";
	}
	
}
