package com.example.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Brand;
import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.EditItemForm;
import com.example.service.BrandService;
import com.example.service.CategoryService;
import com.example.service.EditItemSerivice;
import com.example.service.ShowItemDetailService;

/**
 * 商品情報を編集するコントローラー.
 * 
 * @author yuuki
 *
 */
@Controller
@RequestMapping("/edit_item")
public class EditItemController {
	
	@Autowired
	private ShowItemDetailService showItemDetailService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private EditItemSerivice editItemSerivice;
	
	@Autowired
	private BrandService brandService;
	
	@ModelAttribute
	public EditItemForm setUpEditItemForm(Integer id) {
		Item item = showItemDetailService.getItem(id);
		EditItemForm form = new EditItemForm();
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setCategoryName(item.getCategoryNameAll());
		form.setBrandName(item.getBrandName());
		form.setCondition(item.getCondition());
		form.setDescription(item.getDescription());
		setCategoryIds(form, categoryService.findAllCategories());
		if (item.getIsSale()) {
			form.setIsSale(1);
		} else {
			form.setIsSale(0);
		}
		return form;
	}
	
	/**
	 * 商品編集画面を表示する.
	 * 
	 * @param model　リクエストスコープ
	 * @param id     主キー
	 * @param pageNumber ページ数
	 * @return 
	 */
	@RequestMapping("")
	public String toEditItem(Model model,Integer id,Integer pageNumber) {
		
		Item item = showItemDetailService.getItem(id);
		
		model.addAttribute("item", item);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("brandListForAutocomplete", brandService.getBrandListForAutocomplete());
		
		return "edit";
	}
	
	
	/**
	 * @param form 商品編集フォーム
	 * @param result 
	 * @param model　リクエストスコープ 
	 * @return update成功時 → 商品検索画面
	 * 　　　　　update失敗 → 商品編集画面
	 */
	@RequestMapping("/update")
	public String editItem(@Validated EditItemForm form, BindingResult result, Model model) throws IOException {
		
		Item item = showItemDetailService.getItem(form.getId());
		setCategoryIds(form, categoryService.findAllCategories());
		
		// カテゴリのバリデーション
		// カテゴリ null と　大中小全て選択は有効
		// 　　　　 大のみ、大中のみは無効
		if (!"".equals(form.getCategoryName())) {
			String[] categoryArray = form.getCategoryName().split("/");
			if (categoryArray.length == 1 || categoryArray.length == 2) {
				result.rejectValue("smallCategoryId", null ,"カテゴリは大/中/小全て入力するか、全て空欄のどちらかのみ有効です");
			} else {
				item.setCategoryId(form.getSmallCategoryId());
			}
		} else {
			item.setCategoryId(form.getSmallCategoryId());
		}
		
		// ブランド名で検索をかける（完全一致）
		List <Brand> brandList = brandService.findByName(form.getBrandName());
		if (brandList != null) {
			Brand brand = brandList.get(0);
			item.setBrandId(brand.getId());
		} else if ("".equals(form.getBrandName())) {
			//　ブランド名はからで入力しても良い
			item.setBrandId(null);
		} else {
			// ブランド名を入力したが、一致するものがない時はエラーを返す
			result.rejectValue("brandName", null , "ブランド名は既に登録されているものから選んでください");
		}
		
		// 画像ファイル形式チェック
		MultipartFile image = form.getImage();
		String fileExtension = null;
		try {
			fileExtension = getExtension(image.getOriginalFilename());
			
			if (!"jpg".equals(fileExtension) && !"png".equals(fileExtension)) {
				result.rejectValue("image", null , "拡張子は.jpgか.pngのみに対応しています");
			}
		} catch (Exception e) {
			result.reject("image", null , "拡張子は.jpgか.pngのみに対応しています");
		}
		
		// エラーが1つでもある場合、updateを阻止する
		if (result.hasErrors()) {
			return toEditItem(model, form.getId(), form.getPageNumber());
		}
		
		BeanUtils.copyProperties(form, item);
		// 価格を手動でセット
		item.setPrice(form.getPrice());
		
		// セール中かどうかのbooleanを手動でセット
		if (form.getIsSale() == 0) {
			item.setSale(false);
		} else if (form.getIsSale() == 1) {
			item.setSale(true);
		}
		
		// 画像ファイルをBase64形式にエンコード
		String base64FileString = Base64.getEncoder().encodeToString(image.getBytes());
		if ("jpg".equals(fileExtension)) {
			base64FileString = "data:image/jpeg;base64," + base64FileString;
		} else if ("png".equals(fileExtension)) {
			base64FileString = "data:image/png;base64," + base64FileString;
		}
		item.setImage(base64FileString);
		
		editItemSerivice.editItem(item);
		return "redirect:/item/search";
	}
	
	
	/**
	 * 商品編集画面が表示される際に、登録済みカテゴリを表示するためのメソッド.
	 * categoryNameから、bigCategoryId, middleCategoryId, smallCategoryId を求めてフォームにセットする.
	 * 
	 * @param form
	 * @param categoryList
	 */
	public void setCategoryIds(EditItemForm form, List<Category> categoryList) {
        // 一旦全てクリアーする
        form.setBigCategoryId(null);
        form.setMiddleCategoryId(null);
        form.setSmallCategoryId(null);
        
        if (form.getCategoryName() != null) {
        	String[] categoryArray = form.getCategoryName().split("/");
        	if (categoryArray.length >= 1 && !"".equals(categoryArray[0])) {
        		//　大カテゴリの値をセット
            	Category bigCategory = categoryService.getCategoryByName(categoryList, categoryArray[0]);
            	form.setBigCategoryId(bigCategory.getId());
            	if (categoryArray.length >= 2) {
            		// 中カテゴリの値をセット
                	Category middleCategory = categoryService.getCategoryByName(bigCategory.getChildCategories(), categoryArray[1]);
                    form.setMiddleCategoryId(middleCategory.getId());
                    if (categoryArray.length >= 3) {
                    	// 小カテゴリの値をセット
                        Category smallCategory = categoryService.getCategoryByName(middleCategory.getChildCategories(), categoryArray[2]);
                        form.setSmallCategoryId(smallCategory.getId());
                    }
            	}
        	}
        }
	}
	
	/*
	 * ファイル名から拡張子を返します.
	 * 
	 * @param originalFileName ファイル名
	 * 
	 * @return .を除いたファイルの拡張子
	 */
	private String getExtension(String originalFileName) throws Exception {
		if (originalFileName == null) {
			throw new FileNotFoundException();
		}
		int point = originalFileName.lastIndexOf(".");
		System.out.println(point);
		if (point == -1) {
			throw new FileNotFoundException();
		}
		return originalFileName.substring(point + 1);
	}
	
}
