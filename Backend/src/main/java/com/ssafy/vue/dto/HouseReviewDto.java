package com.ssafy.vue.dto;

public class HouseReviewDto {

	private String aptCode;
	private String commentno;
	private String content;
	private String userid;
	private String regtime;
	private String username;
	private String star;
	public String getAptCode() {
		return aptCode;
	}
	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}
	public String getCommentno() {
		return commentno;
	}
	public void setCommentno(String commentno) {
		this.commentno = commentno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public HouseReviewDto(String aptCode, String commentno, String content, String userid, String regtime,
			String username, String star) {
		super();
		this.aptCode = aptCode;
		this.commentno = commentno;
		this.content = content;
		this.userid = userid;
		this.regtime = regtime;
		this.username = username;
		this.star = star;
	}
	public HouseReviewDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
