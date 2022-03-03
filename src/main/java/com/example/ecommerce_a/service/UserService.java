package com.example.ecommerce_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.repository.UserRepository;

/**
 * ユーザー情報を操作するサービス
 * 
 * @author kashimamiyu
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザー情報を登録します
	 * 
	 * @param user
	 */
	public void insert(User user) {
		userRepository.insert(user);
	}
	
	/**
	 * メールアドレスからユーザー情報を取得します。
	 * 
	 * @param email
	 * @return ユーザー情報
	 */
	public User findByMailAddress(String email) {
		return userRepository.findByEmail(email);
	}
}
