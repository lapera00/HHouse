package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.UserDto;
import com.ssafy.vue.service.UserService;
import com.ssafy.vue.service.jwtServiceImpl;
import com.ssafy.vue.service.KakaoService;


@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private jwtServiceImpl jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private KakaoService kakaologin;
	
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		logger.info("로그인 요청");
		try {
			UserDto loginUser = userService.login(userDto);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userid", loginUser.getUserid());// key, data
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getUserid());
				userService.saveRefreshToken(userDto.getUserid(), refreshToken);
				System.out.println("dfd11f");
				logger.debug("access토큰정보 : {}", accessToken);
				logger.debug("refresh 토큰정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
				
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userid") String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = userService.userInfo(userid);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PutMapping("logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			userService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
				
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto user, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		jwtService.checkToken(token);
		
		if(token.equals(userService.getRefreshToken(user.getUserid()))) {
			String accessToken= jwtService.createAccessToken("userid", user.getUserid());
			resultMap.put("access-token", accessToken);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		}else {
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> update(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				userService.updateMember(userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> delete(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
		String userid = userDto.getUserid();

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				System.out.println("db로");
				userService.deleteMember(userid);
				System.out.println("db나옴");
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/findpw/{userid}/{email}")
	public ResponseEntity<String> findpw(@PathVariable String userid, @PathVariable String email) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("userid", userid);
		map.put("email", email);

		return new ResponseEntity<String>(userService.findpw(map), HttpStatus.OK);

	}
	
	
	@PostMapping("/regist")
	public ResponseEntity<String> register(@RequestBody UserDto userDto) throws Exception {
		if (userService.registerMember(userDto) > 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		System.out.println("여까지오나?");
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/idch/{userid}")
	public ResponseEntity<String> idcheck(@PathVariable String userid) throws Exception {
		if (userService.idcheck(userid) > 0) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@GetMapping("/klogin")
    public UserDto klogin(@RequestParam String authorize_code) throws Exception {
        String access_token = kakaologin.getAccessToken(authorize_code);
        UserDto userDto = kakaologin.getUserInfo(access_token);
        if(userService.login(userDto) == null) {
        	userService.registerMember(userDto);
        }         
        return userDto;
    }
		 
	 
//	
//	 
//	@GetMapping("/login/{userid}/{userpwd}")
//	public ResponseEntity<String> login(@PathVariable String userid, @PathVariable String userpwd) throws Exception {
//		Map<String, String> map = new HashMap<>();
//		map.put("userid", userid);
//		map.put("userpwd", userpwd);
//		if (userService.login(map) != null) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
//	
//	@GetMapping("/findpw/{userid}/{email}")
//	public ResponseEntity<String> findpw(@PathVariable String userid, @PathVariable String email) throws Exception {
//		Map<String, String> map = new HashMap<>();
//		map.put("userid", userid);
//		map.put("email", email);
//
//		return new ResponseEntity<String>(userService.findpw(map), HttpStatus.OK);
//
//	}
//	
//	@GetMapping("/mypage/{userid}")
//	public ResponseEntity<UserDto> mypage(@PathVariable String userid) throws Exception {
//
//		return new ResponseEntity<UserDto>(userService.mypage(userid), HttpStatus.OK);
//
//	}
//	
//	@PutMapping("/update")
//	public ResponseEntity<String> update(@RequestBody UserDto userDto) throws Exception {
//		if (userService.updateMember(userDto) > 0) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
//
//	@DeleteMapping("/delete/{userid}")
//	public ResponseEntity<String> delete(@PathVariable String userid) throws Exception {
//		System.out.println("Dfdfd");
//		if (userService.deleteMember(userid) > 0) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		}
//		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//	}
}