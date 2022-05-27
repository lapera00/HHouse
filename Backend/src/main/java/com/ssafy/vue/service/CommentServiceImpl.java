package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.dto.BoardqnaDto;
import com.ssafy.vue.dto.CommentDto;
import com.ssafy.vue.mapper.BoardqnaMapper;
import com.ssafy.vue.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	
    @Autowired
	private CommentMapper commentMapper;

    @Override
	public List<CommentDto> getComment(String articleno) {
		return commentMapper.getComment(articleno);
	}

	@Override
	public int insertComment(CommentDto commentDto) {
		return commentMapper.insertComment(commentDto);
	}

	@Override
	public int deleteComment(String commentno) {
		return commentMapper.deleteComment(commentno);
	}


	@Override
	public Integer countComment(int articleno) {
		return commentMapper.countComment(articleno);
	}
 
}