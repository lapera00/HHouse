package com.ssafy.vue.mapper;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.vue.dto.UserDto;



public interface UserMapper {

//	UserDto login(Map<String, String> map) throws Exception;
//	
	int registerMember(UserDto userDto) throws Exception;
//	
	int updateMember(UserDto userDto) throws Exception;
//	
	int deleteMember(String userId) throws Exception;
//
//	UserDto mypage(String userId) throws Exception;
//
	String findpw(Map<String, String> map) throws Exception;
	
	public UserDto login(UserDto userDto) throws SQLException;
	public UserDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	public String getRefreshToken(String userid) throws SQLException;
	int idcheck(String userid) throws SQLException;
	
}
