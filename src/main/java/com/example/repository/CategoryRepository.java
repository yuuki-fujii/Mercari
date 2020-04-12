package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import com.example.domain.Category;
import com.example.form.SearchCategoryForm;

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
	
	/** categoryドメインを生成するRowMapper */
	public static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs,i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setParentId(rs.getInt("parent_id"));
		category.setName(rs.getString("name"));
		category.setNameAll(rs.getString("name_all"));
		return category;
	};
	
	/**
	 * 親カテゴリidから子カテゴリを検索する.
	 * 
	 * @param parentId 親カテゴリid
	 * @return 該当する全ての小カテゴリ
	 */
	public List <Category> findByParentId(Integer parentId){
		MapSqlParameterSource param = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,parent_id,name,name_all FROM category ");
		if (parentId != null) {
			// 中カテゴリ or 小カテゴリの検索
			sql.append("WHERE parent_id = :parentId ");
			param.addValue("parentId", parentId);
		} else {
			// 大カテゴリの検索
			sql.append("WHERE parent_id is null ");
		}
		sql.append("ORDER BY id");
		return template.query(sql.toString(), param,CATEGORY_ROW_MAPPER);
	}
	
	
	/**
	 * 1ページ分のname_allを取得する.
	 * 
	 * @return 1ページ分の小カテゴリ
	 */
	public List <Category> findAllSmallCategory(SearchCategoryForm form){
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = createSql(form, params, null);
		return template.query(sql.toString(),params,CATEGORY_ROW_MAPPER);
	}
	
	/**
	 * name_allを持つカテゴリのデータ数を返す.
	 * 
	 * @param form カテゴリ検索フォーム　
	 * @return　name_allを持つカテゴリのデータ数
	 */
	public Integer countNameAll(SearchCategoryForm form) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = createSql(form, params, "count");
		return template.queryForObject(sql.toString(), params, Integer.class);
	}
	
	
	private StringBuilder createSql(SearchCategoryForm form, MapSqlParameterSource params, String mode) {
		StringBuilder sql = new StringBuilder();
		
		if ("count".equals(mode)) {
			sql.append("SELECT count(name_all) ");
		} else {
			sql.append("SELECT id,parent_id,name,name_all ");
		}
		sql.append("FROM category WHERE parent_id IS NOT NULL AND name_all IS NOT NUll ");
		
        // カテゴリー
        if (!StringUtils.isEmpty(form.getCategoryName())) {
            sql.append("AND name_all LIKE :name_all ");
            params.addValue("name_all", form.getCategoryName() + "%");
        }
        
		if (!"count".equals(mode)) {
			Integer startNumber = calcStartNumber(form);
			sql.append("ORDER BY id LIMIT 30 OFFSET " + startNumber);
		}
		return sql;
	}
	
	
	
	/**
	 * 大中小それぞれのカテゴリ名で検索する.
	 * 
	 * @param name カテゴリ名
	 * @param parentId　親カテゴリid
	 * @param nameAll　全カテゴリ名
	 * @return 　カテゴリリスト
	 */
	public List<Category> judgeExistCategory(String name, Integer parentId, String nameAll){ 
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = createSqlForFindByName(name, parentId, nameAll, params);
		List<Category> categoryList = template.query(sql.toString(), params ,CATEGORY_ROW_MAPPER);
		if (categoryList.size() == 0) {
			return null;
		} else {
			return categoryList;
		}
	}
	
	/**
	 * 大中小のカテゴリ名が既に存在するかどうかを検索するSQL文を発行する.
	 * 
	 * @param name カテゴリ名
	 * @param parentId 親カテゴリid
	 * @param nameAll 全カテゴリ名
	 * @return SQL文
	 */
	private StringBuilder createSqlForFindByName(String name, Integer parentId, String nameAll, MapSqlParameterSource params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,parent_id,name,name_all FROM category ");
		if (parentId == null) { 
			sql.append("WHERE parent_id IS NULL AND name = :name"); // 大カテゴリの場合
			params.addValue("name", name);
		}
		return sql;
	}
	
	
	/**
	 * 大中小カテゴリをインサートする.
	 * 
	 * @param category カテゴリ
	 */
	public void insertCategory(Category category) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(category);;
		StringBuilder sql = createSqlForInsert(category);
		template.update(sql.toString(), params);
	}
	
	
	/**
	 * 大中小カテゴリによって異なるSQL分を発行するメソッド.
	 * 
	 * @param category カテゴリ
	 * @param params
	 * @return SQL文
	 */
	private StringBuilder createSqlForInsert(Category category) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO category ");
		if (category.getParentId() == null) {
			sql.append("(name) VALUES (:name) ");
		}
		return sql;
	}
	
	
	/**
	 * 現在のページでの開始番号 - 1 を求める.
	 * 
	 * @param form 商品検索フォーム
	 * @return 現在のページでの開始番号 - 1 (OFFSETで使う数字)
	 */
	private Integer calcStartNumber(SearchCategoryForm form) {
		Integer pageNumber = form.getPageNumber();
		Integer startNumber = 30 * (pageNumber - 1);
		return startNumber;
	}
}
