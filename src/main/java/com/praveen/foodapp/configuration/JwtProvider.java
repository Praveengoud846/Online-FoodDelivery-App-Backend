package com.praveen.foodapp.configuration;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

	//Format of JWT [Header].[Payload].[Signature]
	//HMACSHA256( base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
	
	private SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	//1st method to generate token
	public String generateToken(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		//here authorities is changed to string from granted authority coz JWT provider will not allow granted authority
		String roles =populateAuthorities(authorities);
		String jwt= Jwts.builder().setIssuedAt(new Date())
				.setExpiration((new Date(new Date().getTime()+86400000)))
				.claim("email", auth.getName())
				.claim("authurities", roles)
				.signWith(key)
				.compact();
		return jwt;
	}
	
	//2nd method to get email from the token, claims are known as payload
	public String getEmailFromJwtToken(String jwt){
		jwt=jwt.substring(7);
		Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claims.get("email"));
		return email;
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auths= new HashSet<String>();
		for (GrantedAuthority authority : authorities) {
			auths.add(authority.getAuthority());
		}
		return String.join(",", auths);
	}
}
