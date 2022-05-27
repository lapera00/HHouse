package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.dto.BoardqnaDto;
import com.ssafy.vue.mapper.BoardqnaMapper;

@Service
public class BoardqnaServiceImpl implements BoardqnaService {
	
    @Autowired
	private BoardqnaMapper boardqnaMapper;

    @Override
	public List<BoardqnaDto> retrieveBoard() {
		return boardqnaMapper.selectBoard();
	}
    
  	@Override
	public boolean writeBoard(BoardqnaDto board) {
		return boardqnaMapper.insertBoard(board) == 1;
	}

	@Override
	public BoardqnaDto detailBoard(int articleno) {
		return boardqnaMapper.selectBoardByNo(articleno);
	}

	@Override
	@Transactional
	public boolean updateBoard(BoardqnaDto board) {
		return boardqnaMapper.updateBoard(board) == 1;
	}

	@Override
	@Transactional
	public boolean deleteBoard(int articleno) {
		return boardqnaMapper.deleteBoard(articleno) == 1;
	}
}