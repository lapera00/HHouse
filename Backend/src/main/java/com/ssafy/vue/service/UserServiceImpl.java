package com.ssafy.vue.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.UserDto;
import com.ssafy.vue.mapper.UserMapper;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;

//	@Override
//	public UserDto login(Map<String, String> map) throws Exception {
//		return userMapper.login(map);
//	}
//
	@Override
	public int registerMember(UserDto userDto) throws Exception {
		return userMapper.registerMember(userDto);
	}
//


//	@Override
//	public UserDto mypage(String userId) throws Exception {
//		return userMapper.mypage(userId);
//	}
//
	@Override
	public String findpw(Map<String, String> map) throws Exception {
		return userMapper.findpw(map);
	}

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public UserDto login(UserDto userDto) throws Exception {
		if(userDto.getUserid() == null || userDto.getUserpwd() == null)
			return null;
		return sqlSession.getMapper(UserMapper.class).login(userDto);
	}

	@Override
	public UserDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(userid);
	}

	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		sqlSession.getMapper(UserMapper.class).saveRefreshToken(map);
		
	}

	@Override
	public String getRefreshToken(String userid) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(UserMapper.class).getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		sqlSession.getMapper(UserMapper.class).deleteRefreshToken(map);
		
	}
	
	@Override
	public int updateMember(UserDto userDto) throws Exception {
//		return userMapper.updateMember(userDto);
		return sqlSession.getMapper(UserMapper.class).updateMember(userDto);
	}
	
	
	@Override
	public int deleteMember(String userId) throws Exception {
		return sqlSession.getMapper(UserMapper.class).deleteMember(userId);
	}


	@Override
	public int idcheck(String userid) throws Exception {
		return userMapper.idcheck(userid);
	}

	
}
