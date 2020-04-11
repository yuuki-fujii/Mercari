package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Brand;
import com.example.form.SearchBrandForm;
import com.example.repository.BrandRepository;

/**
 * ブランドを表示するサービス.
 * 
 * @author yuuki
 *
 */
@Service
@Transactional
public class BrandService {
	
	@Autowired
	private BrandRepository brandRepository;
	
	/** 
	 * ブランド名で検索し、完全一致するものを返す.
	 * 
	 * @param name ブランド名
	 * @return　該当するブランド
	 */
	public List<Brand> findByName(String name){
		return brandRepository.findByName(name);
	}
	
	
	/**
	 * 全ブランド情報を取得する.
	 * 
	 * @return 全ブランド情報
	 */
	public List <Brand> getAllBrand(){
		return brandRepository.findAll();
	}
	
	
	/**
	 * ページングのために30件分のブランドを取得する
	 * 
	 * @param form ブランド検索フォーム
	 * @return　30件のブランド情報
	 */
	public List<Brand> getBrandLimited(SearchBrandForm form){
		return brandRepository.findlimited(form);
	}
	
	/**
	 * 全ブランドの数を取得する.
	 * 
	 * @return  全ブランド数
	 */
	public Integer countBrand(SearchBrandForm form) {
		return brandRepository.countBrand(form);
	}
	
	
	/**
	 * ブランドを追加する.
	 * 
	 * @param brand ブランド
	 */
	public void insert(Brand brand) {
		brandRepository.insert(brand);
	}
	
	/**
	 * オートコンプリート用にJavaScriptの配列の中身を文字列で作る.
	 * 
	 * @return オートコンプリート用にJavaScriptの配列の文字列
	 */
	public StringBuilder getBrandListForAutocomplete() {
		StringBuilder brandListForAutocomplete = new StringBuilder();
		List <Brand> brandList = brandRepository.findAll();		
		for (int i = 0; i < brandList.size(); i++) {
			if (i != 0) {
				brandListForAutocomplete.append(",");
			}
			Brand brand = brandList.get(i);
			brandListForAutocomplete.append("\"");
			brandListForAutocomplete.append(brand.getName());
			brandListForAutocomplete.append("\"");
		}
		return brandListForAutocomplete;
	}
	
}
