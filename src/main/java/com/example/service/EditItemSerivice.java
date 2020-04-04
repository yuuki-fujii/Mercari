package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品を編集するためのサービス.
 * 
 * @author yuuki
 *
 */
@Service
@Transactional
public class EditItemSerivice {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	/**
	 * 商品情報を更新する.
	 * 
	 * @param item 商品情報
	 */
	public void editItem(Item item) {
		itemRepository.updateItem(item);
	}
	
}
