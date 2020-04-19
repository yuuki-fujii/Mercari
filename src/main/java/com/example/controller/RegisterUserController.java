package com.example.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.UserService;

@Controller
@RequestMapping("/register_user")
public class RegisterUserController {
	
	@ModelAttribute
	public RegisterUserForm setUpRegisterUserForm() {
		return new RegisterUserForm();
	}
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * ユーザ登録画面に遷移する.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("")
	public String registerUser() {
		return "register";
	}
	
	
	@RequestMapping("/insert")
	public String insert(@Validated RegisterUserForm form, BindingResult result) {
		
		if (userService.findByEmail(form.getMailAddress()) != null) {
			result.rejectValue("mailAddress", null, "このメールアドレスは既に使われています");
		}
		
		if (result.hasErrors()) {
			return "register";
		}
		
		// パスワードを暗号化
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String encode = bcrypt.encode(form.getPassword());
		// フォームからドメインにプロパティ値をコピー
		User user = new User();
		BeanUtils.copyProperties(form, user);
		// ハッシュ化したパスワードをドメインにコピー
		user.setPassword(encode);
		userService.registerUser(user);
		return "redirect:/login";
	}
	
	@RequestMapping("/change_status")
	public String changeStatus(@AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();
		if (user.getIsAdmin()) { 
			user.setAdmin(false);
		} else {
			user.setAdmin(true);
		}
		userService.update(user);
		return "redirect:/logout";
	}
	
	/**
	 * パスワード形式の確認をする.
	 * 
	 * @param password パスワード
	 * @return 確認メッセージ
	 */
	@ResponseBody
	@RequestMapping("/pass-check-api")
	public Map<String, String> passCheck(String password) {
		Map<String, String> map = new HashMap<>();

		// 8-20文字、半角小英字(a-z)、半角大英字(A-Z)、半角数字(0-9)を全て使用することを条件とする
		Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,32}$");
		Matcher matcher = pattern.matcher(password);
		Boolean result = matcher.matches();

		if (result == false) {
			map.put("passwordMessage", "半角小英字(a-z)、半角大英字(A-Z)、半角数字(0-9)を全て使用して、8-32文字で設定してください");
		} else {
			map.put("passwordMessage", "このパスワードは使用可能です");
		}
		return map;
	}
	
}
