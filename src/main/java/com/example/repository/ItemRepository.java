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
	
	
	/**
	 * 商品を検索する.
	 * 
	 * @param form 商品検索フォーム
	 * @return 該当する商品情報
	 */
	public List <Item> findBySerachForm(SearchForm form){
		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = createSql(form, params, null);
		return template.query(sql.toString(), params,ITEM_ROW_MAPPER);	
	}
	
	
	/**
	 * 検索にヒットした件数を取得する.
	 * 
	 * @param form 商品検索フォーム 
	 * @return 該当した商品の数
	 */
	public Integer countData(SearchForm form) {
		MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = createSql(form, params, "count");
        return template.queryForObject(sql.toString(), params, Integer.class);
	}
	
	
	
	/**
	 * 検索条件によって異なるSQL文を発行する.
	 * 
	 * @param form 商品検索フォーム
	 * @param params 
	 * @param mode
	 * @return
	 */
	private StringBuilder createSql(SearchForm form,MapSqlParameterSource params,String mode) {
		
		StringBuilder sql = new StringBuilder();
		
		if ("count".equals(mode)) {
			sql.append("SELECT count(*) ");
		} else {
			sql.append("SELECT i.id,i.name AS item_name,i.condition,c.id AS category_id,c.name_all AS category_name_all,");
			sql.append("i.brand_id,b.name AS brand_name,i.price,i.shipping,i.description ");
		}
		
		sql.append("FROM items i LEFT OUTER JOIN category c ON i.category_id = c.id ");
    	sql.append("LEFT OUTER JOIN brand b ON i.brand_id = b.id ");
		sql.append("WHERE 1 = 1 "); // 下記if文でwhere句を追加しやすいように常に真の条件を入れておく
		
        // カテゴリー
        if (!StringUtils.isEmpty(form.getCategoryName())) {
            sql.append("AND c.name_all LIKE :name_all ");
            params.addValue("name_all", form.getCategoryName() + "%");
        }
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
		
		if (!"count".equals(mode)) {
			Integer startNumber = calcStartNumber(form);
			sql.append("ORDER BY i.price,i.name LIMIT 30 OFFSET " + startNumber);
		}
	
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
	public void update(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE items SET name=:name , condition=:condition, category_id=:categoryId,");
		sql.append("brand_id=:brandId, price=:price, shipping=:shipping, description=:description ");
		sql.append("WHERE id=:id");
		template.update(sql.toString(), param);
	}
	
	/**
	 * 商品情報を追加する.
	 * 
	 * @param item 商品情報
	 */
	public void insert(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO items (name,condition,category_id,brand_id,price,shipping,description) ");
		sql.append("VALUES (:name,:condition,:categoryId,:brandId,:price,:shipping,:description) ");
		template.update(sql.toString(), param);
	}
	
}
