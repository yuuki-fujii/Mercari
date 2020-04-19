package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン画面を表示する.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping("")
	public String toLoginPage() {
		return "login";
	}
}
