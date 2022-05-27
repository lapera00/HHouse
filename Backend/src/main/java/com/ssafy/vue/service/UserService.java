package com.ssafy.vue.service;

import java.util.Map;

import com.ssafy.vue.dto.UserDto;


public interface UserService {

//	UserDto login(Map<String, String> map) throws Exception;
//	
	int registerMember(UserDto memberDto) throws Exception;
//	
	int updateMember(UserDto memberDto) throws Exception;
//	
	int deleteMember(String userId) throws Exception;
//
//	UserDto mypage(String userId) throws Exception;
//
	String findpw(Map<String, String> map) throws Exception;
	
	public UserDto login(UserDto userDto) throws Exception;
	public UserDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public String getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	public int idcheck(String userid) throws Exception;
	
}
