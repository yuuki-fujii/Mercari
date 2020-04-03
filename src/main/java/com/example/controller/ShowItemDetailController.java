package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemDetailService;

@Controller
@RequestMapping("/item_detail")
public class ShowItemDetailController {
	
	@Autowired
	private ShowItemDetailService showItemDetailService;
			
			
	@RequestMapping("")
	public String showItemDetail(Model model,Integer id,Integer pageNumber) {
		System.out.println(id);
		System.out.println(pageNumber);
		
		Item item = showItemDetailService.getItem(id);
		model.addAttribute("item", item);
		model.addAttribute("pageNumber", pageNumber);
		return "detail";
	}
}
