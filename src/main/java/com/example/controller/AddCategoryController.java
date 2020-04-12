package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.form.AddCategoryForm;
import com.example.service.CategoryService;

@Controller
@RequestMapping("/add_category")
public class AddCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@ModelAttribute
	public AddCategoryForm setUpAddCategoryForm() {
		return new AddCategoryForm();
	}
	
	@RequestMapping("/big")
	public String toAddBigCategory(Model model,Integer pageNumber) {
		model.addAttribute("pageNumber", pageNumber);
		return "add_big_category";
	}
	
	@RequestMapping("/big_insert")
	public String insertBig(@Validated AddCategoryForm form, BindingResult result, Model model) {
		
		// カテゴリ名が重複している場合弾く
		List <Category> categoryList = categoryService.judgeExistCategory(form.getName(), form.getParentId(), form.getNameAll());
		if (categoryList != null) {
			result.rejectValue("name", null, "このカテゴリは既に存在します");
		}
		if (result.hasErrors()) {
			return toAddBigCategory(model, form.getPageNumber());
		}
		
		Category category = new Category();
		BeanUtils.copyProperties(form, category);
		categoryService.insertCategory(category);
		return "redirect:/category/search";
	}
	
	
	@RequestMapping("/middle")
	public String toAddMiddleCategory(Model model,Integer pageNumber) {
		List <Category> categoryList = categoryService.findAllCategories();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("pageNumber", pageNumber);
		return "add_middle_category";
	}
	
	@RequestMapping("/middle_insert")
	public String insertMiddle(@Validated AddCategoryForm form, BindingResult result, Model model) {
		
		// カテゴリ名が重複している場合弾く
		List <Category> categoryList = categoryService.judgeExistCategory(form.getName(), form.getParentId(), form.getNameAll());
		if (categoryList != null) {
			result.rejectValue("name", null, "このカテゴリは既に存在します");
		}
		if (result.hasErrors()) {
			return toAddMiddleCategory(model, form.getPageNumber());
		}
		
		Category category = new Category();
		BeanUtils.copyProperties(form, category);
		categoryService.insertCategory(category);
		return "redirect:/category/search";
	}
	
	@RequestMapping("/small")
	public String toAddSmallCategory(Model model,Integer pageNumber) {
		model.addAttribute("pageNumber", pageNumber);
		return "add_small_category";
	}
	
	@RequestMapping("/small_insert")
	public String insertSmall(@Validated AddCategoryForm form, BindingResult result, Model model) {
		
		setCategoryIds(form, categoryService.findAllCategories());
		form.setParentId(form.getMiddleCategoryId());
		
		// カテゴリ名が重複している場合弾く
		List <Category> categoryList = categoryService.judgeExistCategory(form.getName(), form.getParentId(), form.getNameAll());
		System.out.println(categoryList);
		if (categoryList != null) {
			result.rejectValue("name", null, "このカテゴリは既に存在します");
		}
		if (form.getBigCategoryId() == null || form.getMiddleCategoryId() == null) {
			result.rejectValue("middleCategoryId", null, "bigCategory,middleCategoryを両方入力してください");
		}
		
		if (result.hasErrors()) {
			return toAddSmallCategory(model, form.getPageNumber());
		}
		// nameAllを作成する
		String nameAll = form.getNameAll();
		nameAll += "/" + form.getName();
		
		Category category = new Category();
		BeanUtils.copyProperties(form, category);
		category.setParentId(form.getMiddleCategoryId());
		category.setNameAll(nameAll);
		
		categoryService.insertCategory(category);
		return "redirect:/category/search";
	}
	
	/**
     * カテゴリーのプルダウンを維持するために
     * categoryNameから、bigCategoryId, middleCategoryId, smallCategoryId を求めてフォームにセットする.
     *
     * @param form 商品検索フォーム
     * @param categoryList
     */
    public void setCategoryIds(AddCategoryForm form, List<Category> categoryList) {
        // 一旦全てクリアーする
        form.setBigCategoryId(null);
        form.setMiddleCategoryId(null);
        
        	// /でsplitしてカテゴリを分ける
            String[] categoryArray = form.getNameAll().split("/");
            // !"".equals(categoryArray[0])は、カテゴリを選択せずに検索された場合、categoryNameが''（空文字：nullではない）になるため必要
            if (categoryArray.length >= 1 && !"".equals(categoryArray[0])) {
            	// 大カテゴリ群から大カテゴリを検索
                Category bigCategory = categoryService.getCategoryByName(categoryList, categoryArray[0]);
                form.setBigCategoryId(bigCategory.getId());
                // 中カテゴリが選択されている場合
                if (categoryArray.length >= 2) {
                    Category middleCategory = categoryService.getCategoryByName(bigCategory.getChildCategories(), categoryArray[1]);
                    form.setMiddleCategoryId(middleCategory.getId());
                }
            }
        }
}
