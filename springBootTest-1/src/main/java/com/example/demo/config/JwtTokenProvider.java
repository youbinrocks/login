/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : JwtTokenProvider.java
 * Created Date : 2020. 10. 8.
 * Description  :
 */
package com.example.demo.config;

import org.springframework.stereotype.Component;

/**
 * @author Dell
 *
 */
@Component
public class JwtTokenProvider {
	private String secretKey;
	private long validateInMillSeconds;
	
	
	public String createToken(String subject) {
		
		return "";
	}
	

}
