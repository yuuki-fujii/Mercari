package com.example.domain;

/**
 * カテゴリを表すドメインクラス
 * 
 * @author yuuki
 *
 */
public class Category {
	
	/** 主キー */
	private Integer id;
	/** 親キー */
	private Integer parentId;
	/** カテゴリ名 */
	private String categoryName;
	/** カテゴリ名全体 */
	private String nameAll;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", parentId=" + parentId + ", categoryName=" + categoryName + ", nameAll="
				+ nameAll + "]";
	}
	
}
