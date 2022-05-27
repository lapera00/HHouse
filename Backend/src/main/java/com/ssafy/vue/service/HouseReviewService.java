package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.CommentDto;
import com.ssafy.vue.dto.HouseReviewDto;

public interface HouseReviewService {

	public List<HouseReviewDto> getReview(String aptcode);

	public int insertReview(HouseReviewDto houseReviewDto);

	public int deleteReview(String commentno);
}
 