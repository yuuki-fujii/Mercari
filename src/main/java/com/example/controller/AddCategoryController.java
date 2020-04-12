package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.form.AddCategoryForm;
import com.example.service.CategoryService;

@Controller
@RequestMapping("/add_category")
public class AddCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@ModelAttribute
	public AddCategoryForm setUpAddCategoryForm() {
		return new AddCategoryForm();
	}
	
	@RequestMapping("/big")
	public String toAddBigCategory(Model model,Integer pageNumber) {
		model.addAttribute("pageNumber", pageNumber);
		return "add_big_category";
	}
	
	
	@RequestMapping("/big_insert")
	public String insertBig(@Validated AddCategoryForm form, BindingResult result, Model model) {
		
		List <Category> categoryList = categoryService.judgeExistCategory(form.getName(), form.getParentId(), form.getNameAll());
		if (categoryList != null) {
			result.rejectValue("name", null, "このカテゴリは既に存在します");
		}
		if (result.hasErrors()) {
			return toAddBigCategory(model, form.getPageNumber());
		}
		Category category = new Category();
		BeanUtils.copyProperties(form, category);
		categoryService.insertCategory(category);
		return "redirect:/category/search";
	}
	
	
	
	
	
	@RequestMapping("/middle")
	public String toAddMiddleCategory(Model model,Integer pageNumber) {
		model.addAttribute("pageNumber", pageNumber);
		return "add_middle_category";
	}
	
	@RequestMapping("/small")
	public String toAddSmallCategory(Model model,Integer pageNumber) {
		model.addAttribute("pageNumber", pageNumber);
		return "add_small_category";
	}
	
}
