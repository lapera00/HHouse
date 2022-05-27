package com.ssafy.vue.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.UserDto;


public interface KakaoService {
	
	public String getAccessToken(String authorize_code) ;
	public UserDto getUserInfo(String access_Token);

}
