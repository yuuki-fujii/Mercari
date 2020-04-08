package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.repository.ItemRepository;

@Service
@Transactional
public class SearchItemListService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	

	public List<Item> searchItem(SearchForm form){
		return itemRepository.findBySerachForm(form);
	}
	
	
	public Integer countData(SearchForm form) {
		return itemRepository.countData(form);
	}
	
	

}
