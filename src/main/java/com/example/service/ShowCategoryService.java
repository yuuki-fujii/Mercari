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
	 * 全カテゴリを取得する.
	 * 
	 * @return 全カテゴリ
	 */
	public List<Category> findAllCategories(){
		// 大カテゴリを取得
		List <Category> bigCategoryList = categoryRepository.findByParentId(null);
		
		for (Category bigCategory: bigCategoryList) {
			// 中カテゴリを取得
			List <Category> middleCategoryList = categoryRepository.findByParentId(bigCategory.getId());
			bigCategory.setChildCategories(middleCategoryList); 
			
			for (Category middleCategory : middleCategoryList) {
				// 小カテゴリを取得
				List <Category> smallCategoryList = categoryRepository.findByParentId(middleCategory.getId());
				middleCategory.setChildCategories(smallCategoryList);
			}
		}
		// 全カテゴリの情報が詰まった大カテゴリリストを返す
		return bigCategoryList; 
	}
	
}
