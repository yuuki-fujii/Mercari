package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.EditItemForm;
import com.example.service.EditItemSerivice;
import com.example.service.ShowItemDetailService;

/**
 * 商品情報を編集するコントローラー.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/edit_item")
public class EditItemController {
	
	@Autowired
	private ShowItemDetailService showItemDetailService;
	
	@Autowired
	private EditItemSerivice editItemSerivice;
	
	@ModelAttribute
	public EditItemForm setUpEditItemForm(Integer id) {
		Item item = showItemDetailService.getItem(id);
		EditItemForm form = new EditItemForm();
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setBrand(item.getBrand());
		form.setCondition(item.getCondition());
		form.setDescription(item.getDescription());
		
		return form;
	}
	
	@RequestMapping("")
	public String toEditItem(Model model,Integer id,Integer pageNumber) {
		
		Item item = showItemDetailService.getItem(id);
		
		model.addAttribute("item", item);
		model.addAttribute("pageNumber", pageNumber);
		
		return "edit";
	}
	
	@RequestMapping("/update")
	public String editItem(Model model,@Validated EditItemForm form,BindingResult result) {
		
		if (result.hasErrors()) {
			return toEditItem(model, form.getId(), form.getPageNumber());
		}
		
		Item item = showItemDetailService.getItem(form.getId());
		
		System.out.println("-- 更新前のitem --");
		System.out.println(item);
		
		BeanUtils.copyProperties(form, item);
		
		System.out.println("-- 更新後のitem --");
		System.out.println(item);
		
		editItemSerivice.editItem(item);
		return "redirect:/";
	}
		
}
