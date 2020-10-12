/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : CustomAuthFailureHandler.java
 * Created Date : 2020. 10. 7.
 * Description  :
 */
package com.example.demo.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author Dell
 *
 */
public class CustomAuthFailureHandler implements AuthenticationFailureHandler{
	private final String DEFAULT_FAILURE_URL = "/login?error=true";
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String errorMessage = null;
		
		if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요";
		}else {
			errorMessage = "관리자에게 문의하세요";
		}
		
		request.setAttribute("errorMessage", errorMessage);
		request.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(request, response);
		
	}

}
