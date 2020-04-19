package com.example.form;

/**
 * ユーザ削除フォーム.
 * 
 * @author yuuki
 *
 */
public class DeleteUserForm {
	
	/** 主キー */
	private Integer id;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "DeleteUserForm [id=" + id + "]";
	}
	
}
