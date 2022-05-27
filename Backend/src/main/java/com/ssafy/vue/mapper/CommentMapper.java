package com.ssafy.vue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.CommentDto;
@Mapper
public interface CommentMapper {
	public List<CommentDto> getComment(String articleno);

	public int insertComment(CommentDto commentDto);

	public int deleteComment(String commentno);

	public Integer countComment(int articleno);
}