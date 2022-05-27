package com.ssafy.vue.dto;

public class UserDto {

	private String userid;
	private String userpwd;
	private String username;
	private String email;
	private String joindate;
	private int authority;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJoindate() {
		return joindate;
	}
	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	public UserDto(String userid, String userpwd, String username, String email, String joindate, int authority) {
		super();
		this.userid = userid;
		this.userpwd = userpwd;
		this.username = username;
		this.email = email;
		this.joindate = joindate;
		this.authority = authority;
	}
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
	
	
	
}
