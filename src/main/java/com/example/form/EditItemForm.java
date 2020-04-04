package com.example.form;

import javax.validation.constraints.NotBlank;

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
	@NotBlank(message = "商品名を入力してください")
	private String name;
	
	/** 価格（double型） */
	private double price;
	// カテゴリー
	
	/** ブランド */
	private String brand;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	
}
