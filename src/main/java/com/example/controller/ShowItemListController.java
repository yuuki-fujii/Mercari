package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.SearchForm;
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
	
	@ModelAttribute
	public SearchForm setUpPageSearchForm() {
		return new SearchForm();
	}
	
	// 1ページあたりの最大データ数を定数化
	private static final Integer COUNT_OF_PAGE_PER_PAGE = 30;
	
	@RequestMapping("")
	public String ShowItemList(@Validated SearchForm form, BindingResult result, Model model, Integer pageNumber) {
		
		// 総ページ数を取得
		Integer countOfPage = calcCountOfPage();
		Integer nowPageNumber = pageNumber;
		
		
		// ページ数がない時は1ページ目をセット
		if (nowPageNumber == null) {
			nowPageNumber = 1; 
		}
		
		// フォームの入力段階で不正な値がある場合、元の画面に返す
//		if (result.hasErrors()) {
//			nowPageNumber = form.getNowPageNumberInt();
//			return ShowItemList(form, result, model, nowPageNumber);
//		}
		
		// ページ数を直接検索された際は、更新する
		if (form.getPageNumberString() != null) {
			nowPageNumber = form.getIntPageNumber();
		}
		
		// データの開始番号を求める
		Integer startNumber = calcStartNumber(nowPageNumber);
		List <Item> itemList = showItemListService.getItemsOfOnePage(startNumber);
		
		
		model.addAttribute("nowPageNumber", nowPageNumber);
		model.addAttribute("countOfPage", countOfPage);
		model.addAttribute("itemList", itemList);
		return "list";
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
