package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;


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
	
	/** テーブル名を定数化 */
	private final String TABLE_NAME = "items";
	
	/** itemドメインを生成するRowMapper */
	public static final RowMapper<Item> ITEM_ROW_MAPPER = (rs,i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategory(rs.getString("category_name"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getDouble("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		return item;
	};
	
//	private ResultSetExtractor<List<Item>> ITEM_RESULT_SET_EXTRACTOR = (rs) -> {
//		List <Item> itemList = new ArrayList<>();
//		while (rs.next()) {
//			Item item = new Item();
//			item.setId(rs.getInt("id"));
//			item.setName(rs.getString("name"));
//			item.setCondition(rs.getInt("condition"));
//			item.setCategory(rs.getString("category_name"));
//			item.setBrand(rs.getString("brand"));
//			item.setPrice(rs.getDouble("price"));
//			item.setShipping(rs.getInt("shipping"));
//			item.setDescription(rs.getString("description"));
//			itemList.add(item);
//		}
//		return itemList;
//	};
	
	

	/**
	 * 1ページ分の商品情報を求める.
	 * 
	 * @param startNumber　データの開始番号-1 (例)1番目から表示したい場合、OFFSET 0にする
	 * @return 1ページ分の商品情報
	 */
	public List<Item> findItemsOfOnePage(Integer startNumber){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id,i.name,i.condition,c.name_all AS category_name,");
		sql.append("i.brand,i.price,i.shipping,i.description ");
		sql.append("FROM " + TABLE_NAME + " i LEFT OUTER JOIN category c ON i.category = c.id ");
		sql.append("ORDER BY i.price,i.name LIMIT 30 OFFSET " + startNumber);
		
		List <Item> itemList = template.query(sql.toString(), ITEM_ROW_MAPPER);
		return itemList;
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
		sql.append("SELECT i.id,i.name,i.condition,c.name_all AS category_name,i.brand,i.price,i.shipping,i.description ");
		sql.append("FROM " + TABLE_NAME + " i LEFT OUTER JOIN category c ON i.category = c.id ");
		sql.append("WHERE i.id=:id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql.toString(), param, ITEM_ROW_MAPPER);
		return item;
	}
	
}
