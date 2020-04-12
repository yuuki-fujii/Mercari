package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.EditCategoryForm;
import com.example.form.EditPageForm;

/**
 * カテゴリ編集用のコントローラ.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/edit_category")
public class EditCategoryController {
	
	@ModelAttribute
	public EditCategoryForm setUpEditCategoryForm () {
		return new EditCategoryForm();
	}
	
	@RequestMapping("/big")
	public String toEditBigCategory(EditPageForm form, Model model) {
		System.out.println(form.getCategoryName());
		model.addAttribute("pageNumber", form.getPageNumber());
		return "edit_big_category";
	}
	
}
