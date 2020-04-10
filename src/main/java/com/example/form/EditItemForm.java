package com.example.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 商品情報を編集するためのフォーム.
 * 
 * @author yuuki
 *
 */
public class EditItemForm {
	
	/** 主キー */
	private Integer id;
	/** ページ数 */
	private Integer pageNumber;
	/** 商品名 */
	@NotBlank(message = "nameを入力してください")
	private String name;
	/** 価格（バリデーション用） */
	@Pattern(regexp = "^?(0|[1-9]\\d*)(\\.\\d+|)$", message = "priceが不正な値です")
	private String priceString;
	
	private double price;
	/** カテゴリid */
	// 編集画面にセットしておくために使う
	private Integer bigCategoryId; /** 大カテゴリid */
	private Integer middleCategoryId; /** 中カテゴリid */
	// DBに保存されるのは小カテゴリid
	private Integer smallCategoryId; /** 小カテゴリid */
	
	/** カテゴリ名 */
	private String categoryName; 
	/** ブランド名 */
	private String brandName;
	/** 状態 */
	private Integer condition;
	/** 説明 */
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getPriceString() {
		return priceString;
	}
	public void setPriceString(String priceString) {
		this.priceString = priceString;
	}
	// 文字列で受けたpriceをdouble型で渡す
	// 小数第2位を四捨五入する
	public double getPrice() {
		// 誤差がないようにBigDecimalで四捨五入を行い、その後double型に変換する
		BigDecimal bigDecimalPrice = new BigDecimal(price);
		bigDecimalPrice = bigDecimalPrice.setScale(1, BigDecimal.ROUND_HALF_UP);
		Double doublePrice = bigDecimalPrice.doubleValue();
		return doublePrice;
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
		return "EditItemForm [id=" + id + ", pageNumber=" + pageNumber + ", name=" + name + ", price=" + price
				+ ", bigCategoryId=" + bigCategoryId + ", middleCategoryId=" + middleCategoryId + ", smallCategoryId="
				+ smallCategoryId + ", categoryName=" + categoryName + ", brandName=" + brandName + ", condition="
				+ condition + ", description=" + description + "]";
	}
	
}
