package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.service.SearchItemListService;
import com.example.service.BrandService;
import com.example.service.CategoryService;

/**
 * 商品一覧画面を表示するコントローラー.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/item")
public class SearchItemListController {
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private SearchItemListService showItemListService;
	
	@Autowired
	private CategoryService showCategoryService;
	
	@Autowired
	private BrandService brandService;
	
	@ModelAttribute
	public SearchForm setUpSearchForm() {
		return new SearchForm();
	}
	
	// 1ページあたりの最大データ数を定数化
	private static final Integer COUNT_OF_PAGE_PER_PAGE = 30;
	
	
	/**
	 * 商品検索を行うメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @param form　商品検索フォーム
	 * @return 検索結果
	 */
	@RequestMapping("/search")
	public String search(SearchForm form, Model model) {

		if (form.getPageNumber() == null) {
			form.setPageNumber(1);
		} 
		
		// データ数を検索
		Integer countOfData = showItemListService.countData(form);
		// 総ページ数を取得
		Integer maxPage = calcMaxPage(countOfData);
		
		if (form.getPageNumber() <= 0 || form.getPageNumber() > maxPage) {
			// 入力された値が0以下または最大値より大きいとき、メッセージをつけて1ページに遷移する
			model.addAttribute("errorMessage", "1〜" + maxPage + "の数字を入力してください");
			form.setPageNumber(1);
		}
		
		List <Item> itemList = showItemListService.searchItem(form);
		if (itemList.size() == 0) {
			model.addAttribute("noItemMessage", "該当する商品がありません");
		}
		
		model.addAttribute("nowPageNumber", form.getPageNumber());
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("itemList", showItemListService.searchItem(form));
		model.addAttribute("brandListForAutocomplete", brandService.getBrandListForAutocomplete());
		// プルダウンを維持するための記述
		setCategoryIds(form, getCategories());
		return "list";
	}
		

	/**
	 * JQueryのgetJSONメソッドから呼ばれる.
	 * 
	 * @return 全カテゴリ情報
	 */
	@ResponseBody
	@RequestMapping("/categories")
	public List<Category> getAllCategories(){
		return getCategories();
	}
	
    /**
     * 全カテゴリー情報を取得する.
     * セッションに保持し、セッションにない場合のみDBから取得する.
     *
     * @return
     */
	private List<Category> getCategories(){
		@SuppressWarnings("unchecked")
		List<Category> categoryList = (List<Category>) session.getAttribute("categories");
		if (categoryList == null) {
			categoryList = showCategoryService.findAllCategories();
			session.setAttribute("categoryList", categoryList);
		}
		return categoryList;
	}
	
    /**
     * 検索完了時、カテゴリーのプルダウンを維持するために
     * categoryNameから、daiCategoryId, chuCategoryId, syoCategoryId を求めてフォームにセットする.
     *
     * @param form
     * @param categoryList
     */
    private void setCategoryIds(SearchForm form, List<Category> categoryList) {
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
                Category bigCategory = getCategoryByName(categoryList, categoryArray[0]);
                form.setBigCategoryId(bigCategory.getId());
                // 中カテゴリが選択されている場合
                if (categoryArray.length >= 2) {
                    Category middleCategory = getCategoryByName(bigCategory.getChildCategories(), categoryArray[1]);
                    form.setMiddleCategoryId(middleCategory.getId());
                    // 小カテゴリが選択されている場合
                    if (categoryArray.length >= 3) {
                        Category smallCategory = getCategoryByName(middleCategory.getChildCategories(), categoryArray[2]);
                        form.setSmallCategoryId(smallCategory.getId());
                    }
                }
            }
        }
    }
	
	
    /**
     * @param categoryList カテゴリー群
     * @param categoryName カテゴリ名
     * @return
     */
    private Category getCategoryByName(List<Category> categoryList, String categoryName) {
        for (Category category : categoryList) {
        	// 完全一致した場合返す
            if (category.getName().equals(categoryName)) {
                return category;
            }
        }
        return null;
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
