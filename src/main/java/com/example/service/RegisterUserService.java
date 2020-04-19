package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class RegisterUserService {
	
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
}
