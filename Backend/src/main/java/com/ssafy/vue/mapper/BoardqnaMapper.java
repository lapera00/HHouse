package com.ssafy.vue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.dto.BoardqnaDto;

@Mapper
public interface BoardqnaMapper {
	public List<BoardqnaDto> selectBoard();
	public BoardqnaDto selectBoardByNo(int articleno);
	public int insertBoard(BoardqnaDto board);
	public int updateBoard(BoardqnaDto board);
	public int deleteBoard(int articleno);
}