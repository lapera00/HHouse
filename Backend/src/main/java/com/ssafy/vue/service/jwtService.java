package com.ssafy.vue.service;

import java.util.Map;

import org.springframework.stereotype.Service;


public interface jwtService {
	
	<T> String createAccessToken(String key, T data);
	<T> String createRefreshToken(String key, T data);
	<T> String create(String key, T data, String subject, long expir);
	Map<String, Object> get(String key);
	String getUserId();
	boolean checkToken(String jwt);
	

}
