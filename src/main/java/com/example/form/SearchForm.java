package com.example.form;

/**
 * 商品検索を行うフォーム.
 * 
 * @author yuuki
 *
 */
public class SearchForm {
	
	/** 商品名 */
	private String itemName;
	/** 大カテゴリid */
	private Integer bigCategoryId;
	/** 中カテゴリid */
	private Integer middleCategoryId;
	/** 小カテゴリid */
	private Integer childCategoryId;
	/** ブランド名 */
	private String brandName;
	/** ページ数 */
	private Integer pageNumber;
	
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	@Override
	public String toString() {
		return "SearchForm [itemName=" + itemName + ", bigCategoryId=" + bigCategoryId + ", middleCategoryId="
				+ middleCategoryId + ", childCategoryId=" + childCategoryId + ", brandName=" + brandName
				+ ", pageNumber=" + pageNumber + "]";
	}
	
}
