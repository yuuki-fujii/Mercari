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
	private String name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", parentId=" + parentId + ", name=" + name + ", nameAll=" + nameAll + "]";
	}
}
