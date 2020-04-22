

package com.example.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 商品を表すドメインクラス.
 * 
 * @author yuuki
 *
 */
@JsonPropertyOrder({"主キー","商品名","状態","カテゴリ名","ブランド名","価格","配送","説明","セール中","画像"})
public class Item {
	
	/** 主キー */
	@JsonProperty("主キー")
	private Integer id;
	/** 商品名 */
	@JsonProperty("商品名")
	private String name;
	/** 状態 */
	@JsonProperty("状態")
	private Integer condition;
	/** カテゴリーID */
	private Integer categoryId;
	/** 全カテゴリー名 */
	@JsonProperty("カテゴリ名")
	private String categoryNameAll;
	/** ブランドID */
	private Integer brandId;
	/** ブランド */
	@JsonProperty("ブランド名")
	private String brandName;
	/** 価格 */
	@JsonProperty("価格")
	private double price;
	/** 配送 */
	@JsonProperty("配送")
	private Integer shipping;
	/** 説明 */
	@JsonProperty("説明")
	private String description;
	/** セール中かどうか */
	@JsonProperty("セール中")
	private boolean isSale;
	/** 画像 */
	@JsonProperty("画像")
	private String image;
	
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
	public boolean getIsSale() {
		return isSale;
	}
	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	// セール中の価格を取得（今回は一律10%割）
	public double getSalePrice() {
		return price * 0.9;
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", categoryId=" + categoryId
				+ ", categoryNameAll=" + categoryNameAll + ", brandId=" + brandId + ", brandName=" + brandName
				+ ", price=" + price + ", shipping=" + shipping + ", description=" + description + ", isSale=" + isSale
				+ ", image=" + image + "]";
	}
}
