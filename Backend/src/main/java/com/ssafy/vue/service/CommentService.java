package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.CommentDto;

public interface CommentService {
	public List<CommentDto> getComment(String articleno);
	public int insertComment(CommentDto commentDto);
	public int deleteComment(String commentno);
	public Integer countComment(int articleno);
}
 