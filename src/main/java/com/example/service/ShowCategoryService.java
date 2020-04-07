package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.repository.CategoryRepository;

/**
 * カテゴリを表示するサービス.
 * 
 * @author yuuki
 *
 */
@Service
public class ShowCategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	/**
	 * 全ての大カテゴリを取得する.
	 * 
	 * @return 大カテゴリリスト
	 */
	public List<Category> getBigCategory(){
		return categoryRepository.findAllBigCategory();
	}
	
	
	/**
	 * 全ての中カテゴリを取得する.
	 * 
	 * @return　中カテゴリリスト
	 */
	public List<Category> getMiddleCategory(){
		return categoryRepository.findAllMiddleCategory();
	}
	
	/**
	 * 全ての小カテゴリを取得する.
	 * 
	 * @return　小カテゴリリスト
	 */
	public List<Category> getSmallCategory(){
		return categoryRepository.findAllSmallCategory();
	}
	
	/**
	 * 大カテゴリidから中カテゴリを検索する.
	 * 
	 * @param bigCategoryId 大カテゴリのid
	 * @return 大カテゴリの子の関係にあたる全ての中カテゴリ
	 */
	public List <Category> getChildCategoryById(Integer parentId){
		return categoryRepository.findChildCategoryByParentId(parentId);
	}
	
}
