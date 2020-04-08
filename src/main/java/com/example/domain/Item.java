

package com.example.domain;

/**
 * 商品を表すドメインクラス.
 * 
 * @author yuuki
 *
 */
public class Item {
	
	/** 主キー */
	private Integer id;
	/** 商品名 */
	private String name;
	/** 状態 */
	private Integer condition;
	/** カテゴリーID */
	private Integer categoryId;
	/** 全カテゴリー名 */
	private String categoryNameAll;
	/** ブランドID */
	private Integer brandId;
	/** ブランド */
	private String brandName;
	/** 価格 */
	private double price;
	/** 配送 */
	private Integer shipping;
	/** 説明 */
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryNameAll() {
		return categoryNameAll;
	}
	public void setCategoryNameAll(String categoryNameAll) {
		this.categoryNameAll = categoryNameAll;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getShipping() {
		return shipping;
	}
	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	// プロパティ
    public String getBigCategoryName() {
        return categoryNameAll != null ? categoryNameAll.split("/")[0] : "";
    }

    public String getMiddleCategoryName() {
        return categoryNameAll != null ? categoryNameAll.split("/")[1] : "";
    }

    public String getSmallCategoryName() {
        return categoryNameAll != null ? categoryNameAll.split("/")[2] : "";
    }
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", categoryId=" + categoryId
				+ ", categoryNameAll=" + categoryNameAll + ", brandId=" + brandId + ", brandName=" + brandName
				+ ", price=" + price + ", shipping=" + shipping + ", description=" + description + "]";
	}
}
