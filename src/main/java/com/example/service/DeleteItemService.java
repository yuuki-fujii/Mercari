package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.repository.ItemRepository;

@Service
@Transactional
public class DeleteItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	/**
	 * 商品情報を削除する.
	 * 
	 * @param id 主キー
	 */
	public void deleteItem(Integer id) {
		itemRepository.delete(id);
	}
}
