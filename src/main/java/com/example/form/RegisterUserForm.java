package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * ユーザ登録用フォーム.
 * 
 * @author yuuki
 *
 */
public class RegisterUserForm {
	
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスが不正です")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,32}$", 
			 message = "半角小英字(a-z)、半角大英字(A-Z)、半角数字(0-9)を全て使用して、8-32文字で設定してください")
	private String password;
	
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
	@Override
	public String toString() {
		return "RegisterUserForm [mailAddress=" + mailAddress + ", password=" + password + "]";
	}
	
}
