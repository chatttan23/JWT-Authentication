package com.javaSecurity.JWTAuthorize.api;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javaSecurity.JWTAuthorize.jwt.JwtTokenFilter;
import com.javaSecurity.JWTAuthorize.jwt.JwtTokenUtil;
import com.javaSecurity.JWTAuthorize.model.User;

@RestController
public class AuthApi {
	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	JwtTokenFilter jwtTokenFilter;

	@GetMapping("/home")
	public ModelAndView model() {
		return new ModelAndView("login");
	}

	@PostMapping(value = "/auth/login", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> login(@RequestHeader @RequestBody @Valid AuthRequest request) {
		try {
			Authentication authentication = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			Gson gsonBuilder = new GsonBuilder().create();
			String jsonFromPojo = gsonBuilder.toJson(authentication);
			System.out.println(jsonFromPojo);
			User user = (User) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(user);
			AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		}

	}
}

//curl -v -H "Content-Type: application/json" -d "{\"email\":\"gaurav@gmail.com\",\"password\": \"gaurav123\"}" localhost:8049/auth/login