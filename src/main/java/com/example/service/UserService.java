package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	/**
	 * ユーザ登録を行う.
	 * 
	 * @param user ユーザ情報
	 */
	public void registerUser(User user) {
		userRepository.insert(user);
	}
	
	
	/**
	 * メールアドレスでユーザ検索を行う.
	 * 
	 * @param mailAddress メールアドレス
	 * @return 該当ユーザー ,いなければ null
	 */
	public User findByEmail(String mailAddress) {
		return userRepository.findByEmail(mailAddress);
	}
	
	/**
	 * 主キー検索を行う.
	 * 
	 * @param id 主キー
	 * @return 1件のユーザ情報
	 */
	public User findById (Integer id) {
		return userRepository.findById(id);
	}
	
	
	/**
	 * ユーザ情報を更新する.
	 * 
	 * @param user ユーザ情報
	 */
	public void update(User user) {
		userRepository.update(user);
	}
	
	/**
	 * ユーザ情報を削除する.
	 * 
	 * @param id 主キー
	 */
	public void delete(Integer id) {
		userRepository.delete(id);
	}
	
	/**
	 * 全ユーザの情報を取得する.
	 * 
	 * @return 全ユーザ情報
	 */
	public List <User> getAllUsers() {
		return userRepository.findForCsv();
	}
}
