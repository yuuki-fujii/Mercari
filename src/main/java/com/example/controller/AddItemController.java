package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.AddItemForm;

@Controller
@RequestMapping("/add_item")
public class AddItemController {
	
	@ModelAttribute
	public AddItemForm setUpAddItemForm() {
		return new AddItemForm();
	}
	
	@RequestMapping("")
	public String toAddItem(Model model,Integer pageNumber) {
		
		model.addAttribute("pageNumber", pageNumber);
		return "add";
	}
	
	
	@RequestMapping("/insert")
	public String addItem(Model model,@Validated AddItemForm form,BindingResult result) {
		
		if (result.hasErrors()) {
			return toAddItem(model, form.getPageNumber());
		}
		
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		
		System.out.println(item);
		System.out.println("追加成功");
		return "redirect:/";
	}
}
