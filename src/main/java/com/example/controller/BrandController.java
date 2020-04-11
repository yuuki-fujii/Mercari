package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Brand;
import com.example.form.AddBrandForm;
import com.example.form.EditBrandForm;
import com.example.form.SearchBrandForm;
import com.example.service.BrandService;

@Controller
@RequestMapping("/brand")
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	
	@ModelAttribute
	public SearchBrandForm setUpSearchBrandForm() {
		return new SearchBrandForm();
	}
	
	@ModelAttribute
	public AddBrandForm setUpAddBrandForm() {
		return new AddBrandForm();
	}
	
	@ModelAttribute
	public EditBrandForm setUpEditBrandForm () {
		return new EditBrandForm();
	}
	
	// 1ページあたりの最大データ数を定数化
	private static final Integer COUNT_OF_PAGE_PER_PAGE = 30;
	
	
	@RequestMapping("/search")
	public String searchBrand(SearchBrandForm form,Model model) {
		if (form.getPageNumber() == null) {
			form.setPageNumber(1);
		}
		// データ数を検索
		Integer countBrand = brandService.countBrand(form); 
		// 総ページ数を取得
		Integer maxPage = calcMaxPage(countBrand);
		List <Brand> brandList = brandService.getBrandLimited(form);
		if (brandList.size() == 0) {
			model.addAttribute("noItemMessage", "該当する商品がありません");
		}
		model.addAttribute("brandList", brandList);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("nowPageNumber", form.getPageNumber());
		model.addAttribute("brandListForAutocomplete", brandService.getBrandListForAutocomplete());
		return "search_brand";
	}
	
	/**
	 * ブランド追加画面に遷移する.
	 * 
	 * @param model リクエストスコープ 
	 * @param pageNumber ページ数
	 * @return ブランド追加画面
	 */
	@RequestMapping("/add")
	public String toAddBrand(Model model,Integer pageNumber) {
		model.addAttribute("pageNumber", pageNumber);
		return "add_brand";
	}
	
	
	/**
	 * @param form 商品追加フォーム
	 * @param result　
	 * @param model　リクエストスコープ
	 * @return　insert成功時、ブランド検索画面
	 * 　　　　　insert失敗時、商品追加画面
	 */
	@RequestMapping("/insert")
	public String insert(@Validated AddBrandForm form, BindingResult result, Model model) {
		
		List <Brand> brandList = brandService.findByName(form.getName());
		if (brandList != null) {
			result.rejectValue("name", null , "このブランド名は既に登録されています");
		}
		
		if (result.hasErrors()) {
			return toAddBrand(model, form.getPageNumber());
		}
		
		Brand brand = new Brand();
		brand.setName(form.getName());
		brandService.insert(brand);
		return "redirect:/brand/search";
	}
	
	/**
	 * 商品編集画面に遷移.
	 * 
	 * @param model リクエストスコープ
	 * @param pageNumber　ページ数
	 * @return 商品編集画面
	 */
	@RequestMapping("/edit")
	public String toEditBrand(EditBrandForm form ,Integer id, Integer pageNumber, Model model) {
		Brand brand = brandService.findById(id);
		form.setName(brand.getName());
		model.addAttribute("brand", brand);
		model.addAttribute("pageNumber", pageNumber);
		return "edit_brand";
	}
	
	@RequestMapping("/update")
	public String update(@Validated EditBrandForm form,BindingResult result,Model model) {
		List <Brand> brandList = brandService.findByName(form.getName());
		if (brandList != null) {
			result.rejectValue("name", null , "このブランド名は既に登録されています");
		}
		
		if (result.hasErrors()) {
			return toEditBrand(form,form.getId(),form.getPageNumber(),model);
		}
		
		Brand brand = brandService.findById(form.getId());
		brand.setName(form.getName());
		brandService.update(brand);
		
		return "redirect:/brand/search";
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

