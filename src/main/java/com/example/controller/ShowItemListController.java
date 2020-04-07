package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.service.ShowCategoryService;
import com.example.service.ShowItemListService;

/**
 * 商品一覧画面を表示するコントローラー.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/")
public class ShowItemListController {
	
	@Autowired
	private ShowItemListService showItemListService;
	
	@Autowired
	private ShowCategoryService showCategoryService;
	
	
	// 1ページあたりの最大データ数を定数化
	private static final Integer COUNT_OF_PAGE_PER_PAGE = 30;
	
	@RequestMapping("")
	public String showItemList(Model model, Integer pageNumber) {
		
		// 総ページ数を取得
		Integer countOfPage = calcCountOfPage();
		// 現在のページ数を設定
		Integer nowPageNumber = pageNumber;
		
		
		// ページ数がない時は1ページ目をセット
		if (nowPageNumber == null) {
			nowPageNumber = 1; 
		}
		
		// データの開始番号を求める
		Integer startNumber = calcStartNumber(nowPageNumber);
		List <Item> itemList = showItemListService.getItemsOfOnePage(startNumber);
		
		// カテゴリをセット
		List <Category> bigCategoryList = showCategoryService.getBigCategory();

		
		
		model.addAttribute("bigCategoryList", bigCategoryList);
		
		
		model.addAttribute("nowPageNumber", nowPageNumber);
		model.addAttribute("countOfPage", countOfPage);
		model.addAttribute("itemList", itemList);
		return "list";
	}
	
	
	/**
	 * @param form ページ検索フォーム
	 * @param result BindingResult
	 * @param model　リクエストスコープ
	 * @return ユーザが検索したページ数を返す
	 */
	@RequestMapping("/search_page")
	public String searchPage(Model model,Integer nowPageNumberInt, Integer pageNumberForSearch) {
		
		// nullでGoボタンが押された場合1ページ目に遷移させる
		if (pageNumberForSearch == null) {
			return showItemList(model, 1);
		} 
		
		// 総ページ数を取得
		Integer countOfPage = calcCountOfPage();
		
		// 入力された値が0以下または最大値より大きいとき、メッセージ付きで元のページに戻す
		if (pageNumberForSearch <= 0 || pageNumberForSearch > countOfPage) {
			model.addAttribute("errorMessage", "1〜" + countOfPage + "の数字を入力してください");
			return showItemList(model, nowPageNumberInt);
		}
	
		// 問題なければ指定したページに遷移させる
		return showItemList(model, pageNumberForSearch);
	}
	
	
	
	/**
	 * 大カテゴリidから中カテゴリを検索する.
	 * 
	 * @param bigCategoryId 大カテゴリid
	 * @return 該当する中カテゴリリスト
	 */
	@ResponseBody
	@RequestMapping("/get_middle_category")
	public Map <String, List<Category>> getMiddleCategory(Integer bigCategoryId){
		
		Map <String, List<Category>> map = new HashMap<>();
		// 先に宣言しておく
		List <Category> middleCategoryList = null;
		
		// 大カテゴリ（value = 0）に戻ったら中カテゴリもリセットされる
		if ("0".equals(bigCategoryId.toString())) {
			middleCategoryList = null;
		} else {
			middleCategoryList = showCategoryService.getMiddleCategoryById(bigCategoryId);
		}
		
		map.put("middleCategoryList",middleCategoryList);
		return map;
	}
	
	
	
	
	
	/**
	 * 総ページ数を求める.
	 * 
	 * @return 総ページ数
	 */
	private Integer calcCountOfPage() {
		// 総データ数を求める
		Integer countOfData = showItemListService.countData();
		// 総ページ数を求める
		Integer countOfPage = 0;
		// 30で割り切れる場合 （例）60 / 30 = 2ページ
		if (countOfData % 30 == 0) {
			countOfPage = countOfData / COUNT_OF_PAGE_PER_PAGE;
		} else { // 30で割り切れない場合 （例）61 / 30 = 3ページ
			countOfPage = countOfData / COUNT_OF_PAGE_PER_PAGE + 1;
		}
		return countOfPage;
	}
	
	
	/**
	 * 現在のページでの開始番号 - 1 を求める.
	 * 
	 * @param pageNumber 現在のページ数
	 * @return　現在のページでの開始番号 - 1 (OFFSETで使う数字)
	 */
	private Integer calcStartNumber(Integer pageNumber) {
		Integer startNumber = 30 * (pageNumber - 1);
		return startNumber;
	}
}
