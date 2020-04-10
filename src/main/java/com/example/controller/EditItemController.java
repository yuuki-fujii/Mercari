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

import com.example.domain.Brand;
import com.example.domain.Item;
import com.example.form.EditItemForm;
import com.example.service.BrandService;
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
	
	@Autowired
	private BrandService brandService;
	
	@ModelAttribute
	public EditItemForm setUpEditItemForm(Integer id) {
		Item item = showItemDetailService.getItem(id);
		EditItemForm form = new EditItemForm();
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setBrandName(item.getBrandName());
		form.setCondition(item.getCondition());
		form.setDescription(item.getDescription());
		return form;
	}
	
	@RequestMapping("")
	public String toEditItem(Model model,Integer id,Integer pageNumber) {
		
		Item item = showItemDetailService.getItem(id);
		
		model.addAttribute("item", item);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("brandListForAutocomplete", brandService.getBrandListForAutocomplete());
		
		return "edit";
	}
	
	@RequestMapping("/update")
	public String editItem(@Validated EditItemForm form, BindingResult result, Model model) {
		
		Item item = showItemDetailService.getItem(form.getId());
		System.out.println("-- 更新前のitem --");
		System.out.println(item);
		
		List <Brand> brandList = brandService.findByName(form.getBrandName());
		if (brandList != null) {
			Brand brand = brandList.get(0);
			item.setBrandId(brand.getId());
		} else if ("".equals(form.getBrandName())) {
			item.setBrandId(null);
		} else {
			result.rejectValue("brandName", null , "ブランド名は既に登録されているものから選んでください");
		}
		
		
		if (result.hasErrors()) {
			return toEditItem(model, form.getId(), form.getPageNumber());
		}
		
		BeanUtils.copyProperties(form, item);
		
		System.out.println("-- 更新後のitem --");
		System.out.println(item);
		
		editItemSerivice.editItem(item);
		return "redirect:/item/search";
	}
		
}
