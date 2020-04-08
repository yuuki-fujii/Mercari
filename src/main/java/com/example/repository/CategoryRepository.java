package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
}
