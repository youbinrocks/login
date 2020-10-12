/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : UserService.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.service;

import com.example.demo.model.Account;

/**
 * @author Dell
 *
 */
public interface UserService {
	public Account getUserByEmail(String email) throws Exception;
	public Account getUserByUserName(String username) throws Exception;
	public Account setUser(Account user) throws Exception;

}
