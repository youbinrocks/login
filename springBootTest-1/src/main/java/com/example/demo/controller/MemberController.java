/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : MemberController.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.controller;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Account;
import com.example.demo.service.UserService;

/**
 * @author Dell
 *
 */
@Controller
public class MemberController {
	
	@Resource(name="userServiceImpl")
	private UserService userService;
	
	
	@RequestMapping(value= {"/", "/login"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String login(Model model) {
		return "/login";
	}
	
	@RequestMapping(value="/testMapping", method=RequestMethod.GET)
	public String testMapping(Model model) {
		
		
		return "";
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
