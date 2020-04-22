package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.form.SearchItemForm;
import com.example.repository.ItemRepository;

@Service
@Transactional
public class SearchItemListService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	
	/**
	 * 全商品の情報を取得する(CSV出力用).
	 * 
	 * @return 全商品の情報
	 */
	public List <Item> getAllItems(){
		return itemRepository.findForCsv();
	}
	
	/**
	 * 商品検索を行う.
	 * 
	 * @param form 商品検索フォーム
	 * @return  検索に該当する商品
	 */
	public List<Item> searchItem(SearchItemForm form){
		return itemRepository.findBySerachForm(form);
	}
	
	
	/**
	 * 検索ヒット数をカウントする.
	 * 
	 * @param form 商品検索フォーム
	 * @return 検索ヒット数
	 */
	public Integer countData(SearchItemForm form) {
		return itemRepository.countData(form);
	}
	
	

}
