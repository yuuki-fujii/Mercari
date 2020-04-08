package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SearchForm;
import com.example.service.ShowCategoryService;
import com.example.service.SearchItemListService;

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
		
		// 総ページ数を取得
		Integer maxPage = calcMaxPage();
		
		if (form.getPageNumber() == null) {
			form.setPageNumber(1);
		} else if (form.getPageNumber() <= 0 || form.getPageNumber() > maxPage) {
			// 入力された値が0以下または最大値より大きいとき、メッセージをつけて1ページに遷移する
			model.addAttribute("errorMessage", "1〜" + maxPage + "の数字を入力してください");
			form.setPageNumber(1);
		}
		
		System.out.println(form);
		
		List <Item> itemList = showItemListService.searchItem(form);
		
		if (itemList.size() == 0) {
			model.addAttribute("noItemMessage", "該当する商品がありません");
		}
		
		// カテゴリをセット
		List <Category> bigCategoryList = showCategoryService.getBigCategory();
		
		model.addAttribute("bigCategoryList", bigCategoryList);
		model.addAttribute("nowPageNumber", form.getPageNumber());
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("itemList", itemList);
		return "list";
	}
		
	
	/**
	 * 大カテゴリidから中カテゴリを検索する.
	 * 
	 * @param bigCategoryId 大カテゴリid
	 * @return 該当する中カテゴリリスト
	 */
	@ResponseBody
	@RequestMapping("/get_child_category")
	public Map <String, List<Category>> getMiddleCategory(Integer parentId){
		
		Map <String, List<Category>> map = new HashMap<>();
		// 先に宣言しておく
		List <Category> childCategoryList = null;
		
		// 大カテゴリ（value = 0）に戻ったら中カテゴリもリセットされる
		if ("0".equals(parentId.toString()) || "-1".equals(parentId.toString()) ) {
			childCategoryList = null;
		} else {
			childCategoryList = showCategoryService.getChildCategoryById(parentId);
		}
		
		map.put("childCategoryList",childCategoryList);
		return map;
	}
	

	/**
	 * 総ページ数を求める.
	 * 
	 * @return 総ページ数
	 */
	private Integer calcMaxPage() {
		// 総データ数を求める
		Integer countOfData = showItemListService.countData();
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
