package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

@Service
@Transactional
public class AddItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 商品を追加する.
	 * 
	 * @param item 商品情報
	 */
	public void addItem(Item item) {
		itemRepository.insert(item);
	}
}
