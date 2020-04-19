package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ログイン画面を表示する.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * ログインページを表示する.
	 * 
	 * @param model リクエストスコープ
	 * @param error ログイン失敗の場合true
	 * @return ログイン画面
	 */
	@RequestMapping("")
	public String toLoginPage(Model model, @RequestParam(required = false) String error) {
		if (error != null) {
			// ログイン失敗の場合はエラーメッセージを表示する
			model.addAttribute("loginError", "メールアドレスまたはパスワードが不正です");
		}
		return "login";
	}
	
}
