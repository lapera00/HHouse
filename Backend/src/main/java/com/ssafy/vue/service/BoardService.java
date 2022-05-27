package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.Board;

public interface BoardService {
	public List<Board> retrieveBoard();
	public Board detailBoard(int articleno);
	public boolean writeBoard(Board board);
	public boolean updateBoard(Board board);
	public boolean deleteBoard(int articleno);
	public void updateHit(int articleno);
	public Integer selectBoardTotalCount();
	public List<Board> selectBoardLimitOffset(int limit, int offset);
}
 