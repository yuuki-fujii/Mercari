package com.example.form;

/**
 * カテゴリ検索用フォーム.
 * 
 * @author yuuki
 *
 */
public class SearchCategoryForm {
	
	/** ページ数 */
	private Integer pageNumber;
	
    // 検索には使用しない
    // 検索完了時、プルダウンの状態を維持するのにJSから使用する	
	private Integer bigCategoryId; /** 大カテゴリid */
	private Integer middleCategoryId; /** 中カテゴリid */
	private Integer smallCategoryId; /** 小カテゴリid */
	
	/** カテゴリ名（検索に使用する） */
	private String categoryName;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
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

	@Override
	public String toString() {
		return "SearchCategoryForm [pageNumber=" + pageNumber + ", bigCategoryId=" + bigCategoryId
				+ ", middleCategoryId=" + middleCategoryId + ", smallCategoryId=" + smallCategoryId + ", categoryName="
				+ categoryName + "]";
	}
	
}
