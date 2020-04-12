package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.form.SearchCategoryForm;
import com.example.service.CategoryService;

/**
 * カテゴリ一覧検索を行うコントローラ.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public SearchCategoryForm setUpSearchCategoryForm() {
		return new SearchCategoryForm();
	}
	
	// 1ページあたりの最大データ数を定数化
	private static final Integer COUNT_OF_PAGE_PER_PAGE = 30;
	
	@RequestMapping("/search")
	public String toSearchCategory(SearchCategoryForm form, Model model) {
		
		if (form.getPageNumber() == null) {
			form.setPageNumber(1);
		}
		
		List <Category> categoryList = categoryService.getAllSmallCategory(form);
		
		// データ数を検索
		Integer countNameAll = categoryService.countNameAll(form);
		// 総ページ数を取得
		Integer maxPage = calcMaxPage(countNameAll);
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("nowPageNumber", form.getPageNumber());
		
		// プルダウンを維持するための記述
		setCategoryIds(form, categoryService.findAllCategories());
		return "search_category";
	}
	
	/**
     * 検索完了時、カテゴリーのプルダウンを維持するために
     * categoryNameから、bigCategoryId, middleCategoryId, smallCategoryId を求めてフォームにセットする.
     *
     * @param form 商品検索フォーム
     * @param categoryList
     */
    public void setCategoryIds(SearchCategoryForm form, List<Category> categoryList) {
        // 一旦全てクリアーする
        form.setBigCategoryId(null);
        form.setMiddleCategoryId(null);
        form.setSmallCategoryId(null);
        
        // カテゴリで検索された場合
        if (form.getCategoryName() != null) {
        	// /でsplitしてカテゴリを分ける
            String[] categoryArray = form.getCategoryName().split("/");
            // !"".equals(categoryArray[0])は、カテゴリを選択せずに検索された場合、categoryNameが''（空文字：nullではない）になるため必要
            if (categoryArray.length >= 1 && !"".equals(categoryArray[0])) {
            	// 大カテゴリ群から大カテゴリを検索
                Category bigCategory = categoryService.getCategoryByName(categoryList, categoryArray[0]);
                form.setBigCategoryId(bigCategory.getId());
                // 中カテゴリが選択されている場合
                if (categoryArray.length >= 2) {
                    Category middleCategory = categoryService.getCategoryByName(bigCategory.getChildCategories(), categoryArray[1]);
                    form.setMiddleCategoryId(middleCategory.getId());
                    // 小カテゴリが選択されている場合
                    if (categoryArray.length >= 3) {
                        Category smallCategory = categoryService.getCategoryByName(middleCategory.getChildCategories(), categoryArray[2]);
                        form.setSmallCategoryId(smallCategory.getId());
                    }
                }
            }
        }
    }
	
	
	/**
	 * 総ページ数を求める.
	 * 
	 * @return 総ページ数
	 */
	private Integer calcMaxPage(Integer countOfData) {
		// 総ページ数を求める
		Integer maxPage = 0;
		// 30で割り切れる場合 （例）60 / 30 = 2ページ
		if (countOfData % 30 == 0) {
			maxPage = countOfData / COUNT_OF_PAGE_PER_PAGE;
		} else { // 30で割り切れない場合 （例）61 / 30 = 3ページ
			maxPage = countOfData / COUNT_OF_PAGE_PER_PAGE + 1;
		}
		return maxPage;
	}
}
