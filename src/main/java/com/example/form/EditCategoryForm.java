package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * カテゴリ編集用フォーム.
 * 
 * @author yuuki
 *
 */
public class EditCategoryForm {

	/** ページ数 */
	private Integer pageNumber;
	
	/** カテゴリ名 */
	@NotBlank(message = "カテゴリ名を入力してください")
	private String name;
	
	/** 親カテゴリid (中、小カテゴリで使用) */
	private Integer parentId;
	/** カテゴリ名（大中小全て使用する） */
	private String categoryName;
	
	/** 全カテゴリ名（小カテゴリで使用） */
	private String nameAll;
	
	private Integer bigCategoryId;
	private Integer middleCategoryId;
	private Integer smallCategoryId;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
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
	public Integer getBigCategoryId() {
		return bigCategoryId;
	}
	public void setBigCategoryId(Integer bigCategoryId) {
		this.bigCategoryId = bigCategoryId;
	}
	public Integer getMiddleCategoryId() {
		return middleCategoryId;
	}
	public void setMiddleCategoryId(Integer middleCategoryId) {
		this.middleCategoryId = middleCategoryId;
	}
	public Integer getSmallCategoryId() {
		return smallCategoryId;
	}
	public void setSmallCategoryId(Integer smallCategoryId) {
		this.smallCategoryId = smallCategoryId;
	}
	@Override
	public String toString() {
		return "EditCategoryForm [pageNumber=" + pageNumber + ", name=" + name + ", parentId=" + parentId
				+ ", categoryName=" + categoryName + ", nameAll=" + nameAll + ", bigCategoryId=" + bigCategoryId
				+ ", middleCategoryId=" + middleCategoryId + ", smallCategoryId=" + smallCategoryId + "]";
	}
	
}
