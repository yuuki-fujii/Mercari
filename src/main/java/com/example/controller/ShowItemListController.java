package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SearchPageForm;
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
	
	@ModelAttribute
	public SearchPageForm setUpPageSearchForm() {
		return new SearchPageForm();
	}
	
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
		List <Category> middleCategoryList = showCategoryService.getMiddleCategory();
		List <Category> smallCategoryList = showCategoryService.getSmallCategory();
		
		
		model.addAttribute("bigCategoryList", bigCategoryList);
		model.addAttribute("middleCategoryList", middleCategoryList);
		model.addAttribute("smallCategoryList", smallCategoryList);
		
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
	public String searchPage(@Validated SearchPageForm form,BindingResult result,Model model) {
		
		try {
			if (form.getPageNumberForSearch() <= 0 || form.getPageNumberForSearch() > calcCountOfPage()) {
				result.rejectValue("pageNumberForSearch",  null, "1から" + calcCountOfPage() + "の数値を入力してください");
			}
		} catch (NullPointerException e) {
			
		}
		
		
		// もし入力値に誤りがあれば、元の画面に戻る&エラーメッセージを返す
		if (result.hasErrors()) {
			System.out.println("何かしらのエラー");
			return showItemList(model, form.getNowPageNumberInt());
		}

		return showItemList(model, form.getPageNumberForSearch());
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
