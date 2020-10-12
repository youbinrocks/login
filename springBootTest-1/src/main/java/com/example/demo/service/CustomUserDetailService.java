/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : CustomUserDetailService.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Role;

/**
 * @author Dell
 *
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Resource(name = "userServiceImpl")
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account user = null;
		try {
			user = userService.getUserByUserName(username);
		} catch (Exception e) {
			// TODO: handle exception
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		
		return buildUserForAuthentication(user, getUserAuthorities(user.getRoles()));
	}
	
	
	private List<GrantedAuthority> getUserAuthorities(Set<Role> userRole){
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for(Role role: userRole) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		
		
		return grantedAuthorities; 
	}
	
	private UserDetails buildUserForAuthentication(Account user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
