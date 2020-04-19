package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.DeleteUserForm;
import com.example.form.EditUserForm;
import com.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@ModelAttribute
	public EditUserForm setUpEditCategoryForm () {
		return new EditUserForm();
	}
	
	
	@RequestMapping("")
	public String toUserList(Model model) {
		List <User> userList = userService.getAllUsers();
		model.addAttribute("userList", userList);
		return "userList";
	}
	
	
	@RequestMapping("/to_edit")
	public String toEdit(Integer id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "edit_user";
	}
	
	
	@RequestMapping("/update")
	public String update(@Validated EditUserForm form, BindingResult result, Model model) {
		User registerdUser = userService.findByEmail(form.getMailAddress());
		if (registerdUser != null && registerdUser.getId() != form.getId()) {
			result.rejectValue("mailAddress", null, "このメールアドレスは既に使われています");
		}
		if (result.hasErrors()) {
			return toEdit(form.getId(), model);
		}
		
		User user = userService.findById(form.getId());
		BeanUtils.copyProperties(form, user);
		System.out.println(user);
		userService.update(user);
		return "redirect:/user";
	}
	
	@RequestMapping("/delete")
	public String delete(@AuthenticationPrincipal LoginUser loginUser, DeleteUserForm form) {
		userService.delete(form.getId());
		User user = loginUser.getUser();
		// ログイン中のアカウントを削除した場合、ログイン画面に遷移
		if (user.getId() == form.getId()) {
			return "redirect:/login";
		} 
		return "redirect:/user";
	}
	
}
