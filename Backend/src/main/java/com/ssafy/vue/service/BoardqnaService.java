package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.BoardqnaDto;

public interface BoardqnaService {
	public List<BoardqnaDto> retrieveBoard();
	public BoardqnaDto detailBoard(int articleno);
	public boolean writeBoard(BoardqnaDto board);
	public boolean updateBoard(BoardqnaDto board);
	public boolean deleteBoard(int articleno);
}
