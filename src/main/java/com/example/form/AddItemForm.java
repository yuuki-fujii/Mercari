package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商品を追加するためのフォーム.
 * 
 * @author yuuki
 *
 */
public class AddItemForm {
	
	/** ページ数 */
	private Integer pageNumber;
	/** 商品名 */
	@NotBlank(message = "nameを入力してください")
	private String name;
	/** 価格 */
	private double price;
	/** カテゴリid */
	// 追加画面にセットしておくために使う
	private Integer bigCategoryId; /** 大カテゴリid */
	private Integer middleCategoryId; /** 中カテゴリid */
	// DBに保存されるのは小カテゴリid
	private Integer smallCategoryId; /** 小カテゴリid */
	/** カテゴリ名 */
	private String categoryName;
	
	/** ブランド */
	private String brandName;
	/** 状態 */
	@NotNull(message = "conditionを入力してください")
	private Integer condition;
	/** 説明 */
	@NotBlank(message = "descriptionを入力してください")
	private String description;
	
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "AddItemForm [pageNumber=" + pageNumber + ", name=" + name + ", price=" + price + ", bigCategoryId="
				+ bigCategoryId + ", middleCategoryId=" + middleCategoryId + ", smallCategoryId=" + smallCategoryId
				+ ", categoryName=" + categoryName + ", brandName=" + brandName + ", condition=" + condition
				+ ", description=" + description + "]";
	}
}
