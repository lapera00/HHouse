package com.ssafy.vue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.CommentDto;
import com.ssafy.vue.dto.HouseReviewDto;
@Mapper
public interface HouseReviewMapper {
	public List<HouseReviewDto> getReview(String aptcode);

	public int insertReview(HouseReviewDto houseReviewDto);

	public int deleteReview(String commentno);


}