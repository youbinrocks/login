/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : CustomErrorController.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dell
 *
 */
@Controller
public class CustomErrorController implements ErrorController{
	private final String DEFAULT_ERROR_PATH = "/error";
	
	@Override
	public String getErrorPath() {
		return DEFAULT_ERROR_PATH;
	}
	
	@RequestMapping("/error")
	public String errorHandle(HttpServletRequest request, Model model) {
		return errorHandleImpl(request, model);
	}
	
	@GetMapping(value="/access-denied")
	public String accessDenied(Model model) {
		model.addAttribute("errorCode", "403");
		model.addAttribute("errorMessage", "forbidden");
		
		return getErrorPath() + "/error";
	}
	
	private String errorHandleImpl(HttpServletRequest request, Model model) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
		
		model.addAttribute("errorCode", status.toString());
		model.addAttribute("errorMessage", httpStatus.getReasonPhrase());
		
		return getErrorPath() + "/error";
	}

}
