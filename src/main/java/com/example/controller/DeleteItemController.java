package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.DeleteItemService;

@Controller
@RequestMapping("/delete_item")
public class DeleteItemController {

	@Autowired
	private DeleteItemService deleteItemService;
	
	/**
	 * 商品を削除する
	 * 
	 * @param id itemのid
	 * @return 商品検索画面
	 */
	@RequestMapping("")
	public String deleteItem(Integer id) {
		deleteItemService.deleteItem(id);
		return "redirect:/item/search";
	}
}
