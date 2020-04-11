package com.example.form;

/**
 * 商品検索を行うフォーム.
 * 
 * @author yuuki
 *
 */
public class SearchItemForm {
	
	/** 商品名 */
	private String itemName;
	
    // 検索には使用しない
    // 検索完了時、プルダウンの状態を維持するのにJSから使用する	
	private Integer bigCategoryId; /** 大カテゴリid */
	private Integer middleCategoryId; /** 中カテゴリid */
	private Integer smallCategoryId; /** 小カテゴリid */
	
	/** カテゴリ名（検索に使用する） */
	private String categoryName;
	
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
	
	public Integer getSmallCategoryId() {
		return smallCategoryId;
	}
	public void setSmallCategoryId(Integer smallCategoryId) {
		this.smallCategoryId = smallCategoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
				+ middleCategoryId + ", smallCategoryId=" + smallCategoryId + ", categoryName=" + categoryName
				+ ", brandName=" + brandName + ", pageNumber=" + pageNumber + "]";
	}
}
