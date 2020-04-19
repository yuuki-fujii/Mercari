package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ユーザ編集フォーム.
 * 
 * @author yuuki
 *
 */
public class EditUserForm {
	
	/** 主キー */
	private Integer id;
	/** メールアドレス */
	@Email(message = "メールアドレスが不正な値です")
	@NotBlank(message = "メールアドレスを入力してください")
	private String mailAddress;
	
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
	@Override
	public String toString() {
		return "EditUserForm [id=" + id + ", mailAddress=" + mailAddress + "]";
	}
}
