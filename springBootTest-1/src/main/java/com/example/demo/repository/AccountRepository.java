/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : AccountRepository.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;

/**
 * @author Dell
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	public Account findByEmail(String email);
	public Account findByUsername(String username);
	
}
