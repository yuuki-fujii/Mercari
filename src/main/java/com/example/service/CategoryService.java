package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Category;
import com.example.form.SearchCategoryForm;
import com.example.repository.CategoryRepository;

/**
 * カテゴリを表示するサービス.
 * 
 * @author yuuki
 *
 */
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
    private HttpSession session;
	
	/**
	 *  全カテゴリの情報が詰まった大カテゴリリストを作成し返す.
	 * 
	 * @return 全カテゴリ
	 */
	public List<Category> findAllCategories(){
		@SuppressWarnings("unchecked")
		List<Category> categoryList = (List<Category>) session.getAttribute("categories");
		
		if (categoryList == null) {
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
			session.setAttribute("categoryList", bigCategoryList);
			return bigCategoryList;
		} 
		return categoryList;
	}
	
	
	/**
	 * 全てのname_allを取得する.
	 * 
	 * @return  全小カテゴリ
	 */
	public List <Category> getAllSmallCategory(SearchCategoryForm form){
		return categoryRepository.findAllSmallCategory(form);
	}
	
	
	/**
	 * name_allを持つカテゴリのデータ数を返す.
	 * 
	 * @param form カテゴリ検索フォーム　
	 * @return　name_allを持つカテゴリのデータ数
	 */
	public Integer countNameAll(SearchCategoryForm form) {
		return categoryRepository.countNameAll(form);
	}
	
	
    /**
     * @param categoryList カテゴリー群
     * @param categoryName カテゴリ名
     * @return
     */
    public Category getCategoryByName(List<Category> categoryList, String categoryName) {
        for (Category category : categoryList) {
        	// 完全一致した場合返す
            if (category.getName().equals(categoryName)) {
                return category;
            }
        }
        return null;
    }
}
