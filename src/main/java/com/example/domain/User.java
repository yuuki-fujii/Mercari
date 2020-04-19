package com.example.domain;

/**
 * ユーザー情報を表すドメイン.
 * 
 * @author yuuki
 *
 */
public class User {
	
	/** 主キー */
	private Integer id;
	/** メールアドレス */
	private String mailAddress;
	/** パスワード */
	private String password;
	/** 管理者権限 */
	private boolean isAdmin;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", mailAddress=" + mailAddress + ", password=" + password + ", isAdmin=" + isAdmin
				+ "]";
	}
}
