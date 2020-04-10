package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Brand;

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
		sql.append("SELECT id,name FROM brand ORDER BY id ");
		return template.query(sql.toString(), BRAND_ROW_MAPPER);
	}
	
	
	/** 
	 * ブランド名で検索し、完全一致するものを返す.
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
	
}
