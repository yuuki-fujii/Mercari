package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.DeleteCategoryForm;
import com.example.service.CategoryService;

@Controller
@RequestMapping("/delete_category")
public class DeleteCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public DeleteCategoryForm setUpDeleteCategoryForm() {
		return new DeleteCategoryForm();
	}
	
	
	@RequestMapping("")
	public String delete(DeleteCategoryForm form) {
		categoryService.deleteCategory(form);
		return "redirect:/category/search";
	}
}
