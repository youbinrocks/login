/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : MemberController.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * @author Dell
 *
 */
@Controller
@RequiredArgsConstructor
public class MemberController {
	
	@Resource(name="userServiceImpl")
	private UserService userService;
	
	private final PasswordEncoder passwordEncoder;
	private final AccountRepository accountRepository;
	private final JwtTokenProvider jwtTokenProvider;
	
	
	@RequestMapping(value= {"/", "/login"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String login(Model model) {
//		Account account = accountRepository.findByEmail(model.)
//		jwtTokenProvider.createToken(userPk, roles)
		return "/login";

	}
	
	@PostMapping(value ="/logintest")
	public String loginTest(@RequestBody Map<String, String> user){
		Account account = accountRepository.findByEmail(user.get("email"));
		
		if(!passwordEncoder.matches(user.get("password"), account.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}		
		return jwtTokenProvider.createToken(account.getUsername(), account.getRoles());
	}
	
	@GetMapping(value="/registration")
	public String registration(Model model) {
		model.addAttribute("account", new Account());
		return "/registration";
	}
	
	@PostMapping(value="/registration")
	public String createNewUser(Model model, Account account, BindingResult bindingResult) {
		try {
			Account userExists = userService.getUserByUserName(account.getUsername());
			if(userExists !=null) {
				bindingResult.rejectValue("username","error.user", "이미 있습니다");
			}
			
			if(bindingResult.hasErrors()) {
				System.out.println("error: " + bindingResult.getFieldError().toString());
			}else {
				userService.setUser(account);
				
				model.addAttribute("user", new Account());
				model.addAttribute("successMessage", "계정생성완료");
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			model.addAttribute("failMessage" , e.getMessage());		
		}
		
		return "/login";		
	}
	
	@GetMapping(value="/home")
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Account account = null;
		
		try {
			account = userService.getUserByUserName(auth.getName());
		} catch (Exception e) {
			System.out.println("E: " + e.getMessage());
		}
		model.addAttribute("username",account.getUsername());
		model.addAttribute("email",account.getEmail());
		
		return "/index";
	}
	
}
