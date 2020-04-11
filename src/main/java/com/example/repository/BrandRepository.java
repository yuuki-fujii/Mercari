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

import com.example.domain.Brand;
import com.example.form.SearchBrandForm;

@Repository
public class BrandRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** brandドメインを生成するRowMapper */
	public static final RowMapper<Brand> BRAND_ROW_MAPPER = (rs,i) -> {
		Brand brand = new Brand();
		brand.setId(rs.getInt("id"));
		brand.setName(rs.getString("name"));
		return brand;
	};
	
	
	/**
	 * 全ブランドを取得する. （オートコンプリート用）
	 * 
	 * @return　全ブランド
	 */
	public List<Brand> findAll(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name FROM brand ORDER BY name ");
		return template.query(sql.toString(), BRAND_ROW_MAPPER);
	}
	
	
	/**
	 * 主キー検索.
	 * 
	 * @param id 主キー
	 * @return　ブランド
	 */
	public Brand findById(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name FROM brand WHERE id=:id ");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql.toString(), param, BRAND_ROW_MAPPER);
	}
	
	
	/**
	 * ブランド名を変更する.
	 * 
	 * @param brand ブランド
	 */
	public void update(Brand brand) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(brand);
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE brand SET name = :name WHERE id=:id");
		template.update(sql.toString(), param);
	}
	
	
	/**
	 * ブランドを削除し、該当するブランドを持つitemsのbrand_idをnullにする
	 * 
	 * 
	 * @param id 主キー
	 */
	public void deleteById(Integer id){
		StringBuilder sql = new StringBuilder();
		sql.append("WITH deleted AS (DELETE FROM brand WHERE id = :id RETURNING id) ");
		sql.append("UPDATE items SET brand_id = null ");
		sql.append("WHERE brand_id IN (SELECT id FROM deleted)");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql.toString(), param);
	}
	
	/**
	 * ページングのために30件分のブランドを取得する
	 * 
	 * @param form ブランド検索フォーム
	 * @return　30件のブランド情報
	 */
	public List<Brand> findlimited(SearchBrandForm form){ 
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = createSql(form, params, null);
		return template.query(sql.toString(),params,BRAND_ROW_MAPPER);
	}
	
	
	/**
	 * 全ブランドの数を取得する.
	 * 
	 * @return  全ブランド数
	 */
	public Integer countBrand(SearchBrandForm form) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		StringBuilder sql = createSql(form, param, "count");
		return template.queryForObject(sql.toString(),param,Integer.class);
	}
	
	
	/**
	 * 検索条件によって異なるSQLを発行する.
	 * 
	 * @param form ブランド検索フォーム
	 * @param params パラメーター
	 * @param mode ブランドを検索するか、データ数を検索するか 
	 * @return SQl
	 */
	private StringBuilder createSql(SearchBrandForm form, MapSqlParameterSource params, String mode) {
		StringBuilder sql = new StringBuilder();
		
		if ("count".equals(mode)) {
			sql.append("SELECT count(*) ");
		} else {
			sql.append("SELECT id,name ");
		}
		sql.append("FROM brand WHERE 1 = 1 ");
		
		// ブランド名　あいまい検索
		if (!StringUtils.isEmpty(form.getName())) {
			sql.append("AND name LIKE :name "); 
			params.addValue("name", "%" + form.getName() + "%");
		}
		
		if (!"count".equals(mode)) {
			Integer startNumber = calcStartNumber(form);
			sql.append("ORDER BY name LIMIT 30 OFFSET " + startNumber);
		}
		return sql;
	}
	

	/** 
	 * ブランド名で検索し、完全一致するものを返す.
	 * ブランドのバリデーションに使う
	 * 
	 * @param name ブランド名
	 * @return　該当するブランド
	 */
	public List<Brand> findByName(String name){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name FROM brand WHERE name = :name");
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List <Brand> brandList = template.query(sql.toString(),param, BRAND_ROW_MAPPER);
		
		if (brandList.size() == 0) {
			return null;
		} 
		return brandList;
	}
	
	
	/**
	 * 新規ブランドを追加する.
	 * 
	 * @param brand ブランド
	 */
	public void insert(Brand brand) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(brand);
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO brand (name) ");
		sql.append("VALUES (:name) ");
		template.update(sql.toString(), param);
	}
	
	
	
	/**
	 * 現在のページでの開始番号 - 1 を求める.
	 * 
	 * @param form 商品検索フォーム
	 * @return 現在のページでの開始番号 - 1 (OFFSETで使う数字)
	 */
	private Integer calcStartNumber(SearchBrandForm form) {
		Integer pageNumber = form.getPageNumber();
		Integer startNumber = 30 * (pageNumber - 1);
		return startNumber;
	}
	
}
