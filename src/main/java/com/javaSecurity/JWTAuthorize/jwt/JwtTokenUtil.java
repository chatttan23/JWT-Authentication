package com.javaSecurity.JWTAuthorize.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.javaSecurity.JWTAuthorize.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;// 24hr

	@Value("${app.jwt.secret}")
	private String secretKey;

	public String generateAccessToken(User user) {
		return Jwts.builder()
				.setSubject(user.getId() + "," + user.getEmail())
				.claim("roles",user.getRoles().toString())
				.setIssuer("Gaurav")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT EXPIRE", ex);
		}catch(IllegalArgumentException ex) {
			LOGGER.error("token is null, empty or has whitspace only",ex);
		}catch(MalformedJwtException ex) {
			LOGGER.error("JWT IS INVALID", ex);
		}catch(UnsupportedJwtException ex) {
			LOGGER.error("Jwt is not supported",ex);
		}catch(SignatureException ex) {
			LOGGER.error("Signature Validation failed",ex);
		}
		return false;
	}

	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}
}
