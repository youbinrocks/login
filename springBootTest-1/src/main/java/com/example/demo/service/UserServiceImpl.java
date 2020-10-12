/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : UserServiceImpl.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

/**
 * @author Dell
 *
 */
@Service(value="userServiceImpl")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	

	@Override
	public Account getUserByEmail(String email) throws Exception {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Account getUserByUserName(String username) throws Exception {
		return accountRepository.findByUsername(username);
	}

	@Override
	public Account setUser(Account user) throws Exception {
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		
		return accountRepository.save(user);
	}

}
