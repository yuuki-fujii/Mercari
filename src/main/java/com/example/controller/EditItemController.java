package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
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
	
	@RequestMapping("")
	public String editItem(Model model,Integer id,Integer pageNumber) {
	
		Item item = showItemDetailService.getItem(id);
		
		model.addAttribute("item", item);
		model.addAttribute("pageNumber", pageNumber);
		
		return "edit";
	}
}
