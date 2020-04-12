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
import com.example.form.EditCategoryForm;
import com.example.service.CategoryService;

/**
 * カテゴリ編集用のコントローラ.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/edit_category")
public class EditCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public EditCategoryForm setUpEditCategoryForm () {
		return new EditCategoryForm();
	}
	
	@RequestMapping("/big")
	public String toEditBigCategory(Integer pageNumber, Model model) {
		model.addAttribute("pageNumber", pageNumber);
		return "edit_big_category";
	}
	
	@RequestMapping("/update")
	public String update(@Validated EditCategoryForm form, BindingResult result, Model model) {
		
		setCategoryIds(form, categoryService.findAllCategories());
		System.out.println(form);
		// カテゴリ名が重複している場合弾く
		List <Category> categoryList = categoryService.judgeExistCategory(form.getAfterName(), form.getParentId(), form.getNameAll());
		if (categoryList != null) {
			result.rejectValue("afterName", null, "このカテゴリは既に存在します");
		}
		if (result.hasErrors()) {
			return toEditBigCategory(form.getPageNumber(), model);
		}
		
		categoryService.updateCategory(form);
		return "redirect:/category/search";
	}
	
	
	/**
     * 検索完了時、カテゴリーのプルダウンを維持するために
     * categoryNameから、bigCategoryId, middleCategoryId, smallCategoryId を求めてフォームにセットする.
     *
     * @param form 商品検索フォーム
     * @param categoryList
     */
    public void setCategoryIds(EditCategoryForm form, List<Category> categoryList) {
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
	
}
