package com.example.form;

/**
 * 商品検索を行うフォーム.
 * 
 * @author yuuki
 *
 */
public class SearchForm {
	
	/** 商品名 */
	private String name;
	/** 大カテゴリid */
	private Integer bigCategoryId;
	/** 中カテゴリid */
	private Integer middleCategoryId;
	/** 小カテゴリid */
	private Integer childCategoryId;
	/** ブランド名 */
	private String brand;
	/** ページ数 */
	private Integer pageNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getChildCategoryId() {
		return childCategoryId;
	}
	public void setChildCategoryId(Integer childCategoryId) {
		this.childCategoryId = childCategoryId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	@Override
	public String toString() {
		return "SearchForm [name=" + name + ", bigCategoryId=" + bigCategoryId + ", middleCategoryId="
				+ middleCategoryId + ", childCategoryId=" + childCategoryId + ", brand=" + brand + ", pageNumber="
				+ pageNumber + "]";
	}
	
}
