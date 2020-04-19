package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	
	private final com.example.domain.User user;
	
	/**
	 * コンストラクタ.
	 * 
	 * @param user 
	 */
	public LoginUser(com.example.domain.User user,Collection<GrantedAuthority> authorityList) {
		// スーパークラスのユーザーID、パスワードに値をセットする
		// 実際の認証はスーパークラスのユーザーID、パスワードで行われる
		super(user.getMailAddress(), user.getPassword(), authorityList);
		this.user = user;
	}
	
	/**
	 * @return Userドメイン
	 */
	public com.example.domain.User getUser() {
		return this.user;
	}
}
