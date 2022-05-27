package com.ssafy.vue.dto;

import io.swagger.annotations.ApiModelProperty;

public class CommentDto {

	private int articleno;
	private int commentno;
	private String content;
	private String userid;
	private String regtime;
	private String username;
	public int getArticleno() {
		return articleno;
	}
	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}
	public int getCommentno() {
		return commentno;
	}
	public void setCommentno(int commentno) {
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
	public CommentDto(int articleno, int commentno, String content, String userid, String regtime, String username) {
		super();
		this.articleno = articleno;
		this.commentno = commentno;
		this.content = content;
		this.userid = userid;
		this.regtime = regtime;
		this.username = username;
	}
	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
