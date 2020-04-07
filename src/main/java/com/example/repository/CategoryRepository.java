package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;

/**
 * categoryテーブルにアクセスするためのリポジトリ.
 * 
 * @author yuuki
 *
 */
@Repository
public class CategoryRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** テーブル名を定数化 */
	private final String TABLE_NAME = "category";
	
	/** categoryドメインを生成するRowMapper */
	public static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs,i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setParentId(rs.getInt("parent_id"));
		category.setCategoryName(rs.getString("category_name"));
		category.setNameAll(rs.getString("name_all"));
		return category;
	};
	
	
	/**
	 * 全ての大カテゴリを取得する.
	 * 
	 * @return　大カテゴリリスト
	 */
	public List <Category> findAllBigCategory(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,parent_id,category_name,name_all ");
		sql.append("FROM " + TABLE_NAME);
		sql.append(" WHERE parent_id IS NULL AND name_all IS NULL");
		
		List <Category> bigCategoryList = template.query(sql.toString(), CATEGORY_ROW_MAPPER);
		return bigCategoryList;
	}
	
	/**
	 * 全ての中カテゴリを取得する.
	 * 
	 * @return　中カテゴリリスト
	 */
	public List <Category> findAllMiddleCategory(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,parent_id,category_name,name_all ");
		sql.append("FROM " + TABLE_NAME);
		sql.append(" WHERE parent_id IS NOT NULL AND name_all IS NULL");
		
		List <Category> middleCategoryList = template.query(sql.toString(), CATEGORY_ROW_MAPPER);
		return middleCategoryList;
	}
	
	/**
	 * 全ての小カテゴリを取得する.
	 * 
	 * @return　小カテゴリリスト
	 */
	public List <Category> findAllSmallCategory(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,parent_id,category_name,name_all ");
		sql.append("FROM " + TABLE_NAME);
		sql.append(" WHERE parent_id IS NOT NULL AND name_all IS NOT NULL");
		
		List <Category> smallCategoryList = template.query(sql.toString(), CATEGORY_ROW_MAPPER);
		return smallCategoryList;
	}
	


	/**
	 * 親カテゴリidから子カテゴリを検索する.
	 * 
	 * @param parentId 親カテゴリid
	 * @return 該当する全ての小カテゴリ
	 */
	public List <Category> findChildCategoryByParentId(Integer parentId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,parent_id,category_name,name_all ");
		sql.append("FROM " + TABLE_NAME +" WHERE parent_id = :id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id",parentId);
		List <Category> middleCategoryList = template.query(sql.toString(), param,CATEGORY_ROW_MAPPER) ;
		return middleCategoryList;
	}
}
