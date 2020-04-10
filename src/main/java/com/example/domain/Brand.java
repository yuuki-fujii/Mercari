package com.example.domain;

/**
 * ブランドを表すドメインクラス.
 * 
 * @author yuuki
 *
 */
public class Brand {
	
	/** 主キー */
	private Integer id;
	/** ブランド名 */
	private String name;

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
	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + "]";
	}
}
