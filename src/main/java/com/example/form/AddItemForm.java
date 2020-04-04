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
	@NotNull(message = "priceを入力してください")
	private double price;
	/** カテゴリーID */
	private Integer categoryId;
	/** ブランド */
	private String brand;
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
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
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
		return "AddItemForm [pageNumber=" + pageNumber + ", name=" + name + ", price=" + price + ", categoryId="
				+ categoryId + ", brand=" + brand + ", condition=" + condition + ", description=" + description + "]";
	}
	
}
