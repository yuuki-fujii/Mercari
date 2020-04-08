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
import com.example.service.ShowCategoryService;

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
	private ShowCategoryService showCategoryService;
	
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
	public String search(Model model, SearchForm form) {

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
		return "list";
	}
		
    /**
     * 全カテゴリー情報を取得する.
     * セッションに保持し、セッションにない場合のみDBから取得する.
     *
     * @return
     */
	@ResponseBody
	@RequestMapping("/categories")
	public List<Category> getAllCategories(){
		@SuppressWarnings("unchecked")
		List<Category> categoryList = (List<Category>) session.getAttribute("categories");
		if (categoryList == null) {
			categoryList = showCategoryService.findAllCategories();
			session.setAttribute("categoryList", categoryList);
		}
		return categoryList;
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
