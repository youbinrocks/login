/**
 * Author       : 박유빈
 * Company      : NHNDiquest
 * Team         : 검색그룹 / TA사업 2팀
 * FileName     : JwtTokenProvider.java
 * Created Date : 2020. 10. 8.
 * Description  :
 */
package com.example.demo.config;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.demo.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;


/**
 * @author Dell
 *
 */
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	private String secretKey = "youbinrocks";
	
	//토큰 유효시간 30분
	private long tokenValidTime = 30 * 60 * 100L;
	private final UserDetailsService userDetailsService;

	@SuppressWarnings("deprecation")
	public String createToken(String userPk, Set<Role> roles) {
		Claims claims = Jwts.claims().setSubject(userPk); // JWT payload에 저장되는 정보단위
		claims.put("roles", roles); // 정보는 key/valueㅎ 쌍 
		Date now = new Date();
		
		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(new Date(now.getTime() + tokenValidTime))
					.signWith(SignatureAlgorithm.HS256, secretKey)
					.compact();
	}
	
	
	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());		
	}
	
	//토큰에서 회원 정보 추출
	@SuppressWarnings("deprecation")
	public String getUserPk(String token) {		
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	//Request의 Header에서 token값을 가져온다 
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	//토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	

}
