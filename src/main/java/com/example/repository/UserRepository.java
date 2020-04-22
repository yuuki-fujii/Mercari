package com.example.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * usersテーブルにアクセスするためのリポジトリ.
 * 
 * @author yuuki
 *
 */
@Repository
public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	// loggerを定義
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	/** Userドメインを生成するRowMapper */
	public static final RowMapper<User> USER_ROW_MAPPER = (rs,i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setMailAddress(rs.getString("mail_address"));
		user.setPassword(rs.getString("password"));
		user.setAdmin(rs.getBoolean("is_admin"));
		return user;
	};
	
	/**
	 * ユーザ登録を行う.
	 * 
	 * @param user ユーザ情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO users (mail_address,password) VALUES (:mailAddress,:password)";
		logger.info("insert :" + user.toString());
		template.update(sql, param);
	}
	
	/**
	 * ユーザ情報を更新する.
	 * 
	 * @param user ユーザ情報
	 */
	public void update(User user) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE users SET mail_address=:mailAddress, password=:password, is_admin=:isAdmin WHERE id=:id");
		SqlParameterSource param = new MapSqlParameterSource()
									.addValue("id", user.getId())
									.addValue("mailAddress", user.getMailAddress())
									.addValue("password", user.getPassword())
									.addValue("isAdmin", user.getIsAdmin());
		logger.info("update :" + user.toString());
		template.update(sql.toString(), param);
	}
	
	
	/**
	 * ユーザ情報を削除する.
	 * 
	 * @param id 主キー
	 */
	public void delete(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM users WHERE id=:id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql.toString(), param);
	}
	
	/**
	 * メールアドレスでユーザ情報を検索・取得する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報(該当なしの場合null)
	 */
	public User findByEmail(String mailAddress) {
		
		String sql = "SELECT id, mail_address, password, is_admin FROM users WHERE mail_address=:mailAddress ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		try {
			User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 主キー検索を行う.
	 * 
	 * @param id 主キー
	 * @return 1件のユーザ情報
	 */
	public User findById(Integer id) {
		String sql = "SELECT id, mail_address, password, is_admin FROM users WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, USER_ROW_MAPPER);
	}
	
	
	/**
	 * 全ユーザの情報を取得する.
	 * 
	 * @return 全ユーザ情報
	 */
	public List <User> findForCsv(){
		StringBuilder sql = new  StringBuilder();
		sql.append("SELECT id, mail_address, password, is_admin FROM users ");
		return template.query(sql.toString(), USER_ROW_MAPPER);
	}
}
