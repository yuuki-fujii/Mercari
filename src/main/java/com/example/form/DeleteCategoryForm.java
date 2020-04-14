package com.example.form;


/**
 * カテゴリ削除用フォーム.
 * 
 * @author yuuki
 *
 */
public class DeleteCategoryForm {
	
	private Integer bigCategoryId;
	private Integer middleCategoryId;
	private Integer smallCategoryId;
	
	
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
		return "DeleteCategoryForm [bigCategoryId=" + bigCategoryId + ", middleCategoryId=" + middleCategoryId
				+ ", smallCategoryId=" + smallCategoryId + "]";
	}
	
}
