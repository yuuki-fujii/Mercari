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

import com.example.domain.Item;
import com.example.form.SearchForm;


/**
 * itemsテーブルにアクセスするためのリポジトリ.
 * 
 * @author yuuki
 *
 */
@Repository
public class ItemRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	/** itemドメインを生成するRowMapper */
	public static final RowMapper<Item> ITEM_ROW_MAPPER = (rs,i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("item_name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategoryId(rs.getInt("category_id"));
		item.setCategoryNameAll(rs.getString("category_name_all"));
		item.setBrandId(rs.getInt("brand_id"));
		item.setBrandName(rs.getString("brand_name"));
		item.setPrice(rs.getDouble("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		return item;
	};
	
	
	public List <Item> findBySerachForm(SearchForm form){
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = createSql(form, params, null);
		return template.query(sql.toString(), params,ITEM_ROW_MAPPER);
		
	}
	
	
	
	private StringBuilder createSql(SearchForm form,MapSqlParameterSource params,String mode) {
		
		StringBuilder sql = new StringBuilder();
		
		// 共通部分
		sql.append("SELECT i.id,i.name AS item_name,i.condition,c.id AS category_id,c.name_all AS category_name_all,");
		sql.append("i.brand_id,b.name AS brand_name,i.price,i.shipping,i.description ");
		sql.append("FROM items i LEFT OUTER JOIN category c ON i.category_id = c.id ");
    	sql.append("LEFT OUTER JOIN brand b ON i.brand_id = b.id ");
		sql.append("WHERE 1 = 1 "); // 下記if文でwhere句を追加しやすいように常に真の条件を入れておく
		
		// 商品名曖昧検索
		if (!StringUtils.isEmpty(form.getItemName())) {
			sql.append("AND i.name LIKE :name ");
			params.addValue("name", "%" + form.getItemName() + "%");
		} 
		
		// ブランド名検索　完全一致
		if (!StringUtils.isEmpty(form.getBrandName())) {
			sql.append("AND b.name = :brandName ");
			params.addValue("brandName", form.getBrandName());
		}
		
		Integer startNumber = calcStartNumber(form);
		sql.append("ORDER BY i.price,i.name LIMIT 30 OFFSET "+ startNumber);
		
		return sql;
	}
	
	
	




	

	/**
	 * 現在のページでの開始番号 - 1 を求める.
	 * 
	 * @param form 商品検索フォーム
	 * @return 現在のページでの開始番号 - 1 (OFFSETで使う数字)
	 */
	private Integer calcStartNumber(SearchForm form) {
		Integer pageNumber = form.getPageNumber();
		Integer startNumber = 30 * (pageNumber - 1);
		return startNumber;
	}
	
	
	/**
	 * ページング機能に必要な総ページ数 は 「総データ数 ÷ 1ページあたりのデータ数」で求める。
	 * そのため、商品の総データ数を取得するメソッド.
	 * 
	 * @return データ数
	 */
	public Integer countData() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(id) FROM items");
		SqlParameterSource param = new MapSqlParameterSource();
		Integer count = template.queryForObject(sql.toString(), param, Integer.class);
		return count;
	}
	
	
	/**
	 * 主キー検索を行う.
	 * 
	 * @param id 主キー
	 * @return　1件の商品情報
	 */
	public Item findById(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id,i.name AS item_name,i.condition,c.id AS category_id,c.name_all AS category_name_all,");
		sql.append("i.brand_id,b.name AS brand_name,i.price,i.shipping,i.description ");
		sql.append("FROM items i LEFT OUTER JOIN category c ON i.category_id = c.id ");
		sql.append("LEFT OUTER JOIN brand b ON i.brand_id = b.id ");
		sql.append("WHERE i.id=:id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql.toString(), param, ITEM_ROW_MAPPER);
		return item;
	}
	
	
	/**
	 * 商品情報を更新する.
	 * 
	 * @param item 商品情報
	 */
	public void updateItem(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE items SET name=:name , condition=:condition, category=:categoryId,");
		sql.append("brand=:brand, price=:price, shipping=:shipping, description=:description ");
		sql.append("WHERE id=:id");
		
		template.update(sql.toString(), param);
		
	}
	
}
