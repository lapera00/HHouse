package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.dto.HouseReviewDto;
import com.ssafy.vue.mapper.HouseReviewMapper;

@Service
public class HouseReviewServiceImpl implements HouseReviewService {
	
    @Autowired
	private HouseReviewMapper houseReviewMapper;

	@Override
	public List<HouseReviewDto> getReview(String aptcode) {
		return houseReviewMapper.getReview(aptcode);
	}

	@Override
	public int insertReview(HouseReviewDto houseReviewDto) {
		return houseReviewMapper.insertReview(houseReviewDto);
	}

	@Override
	public int deleteReview(String commentno) {
		return houseReviewMapper.deleteReview(commentno);
	}
    
    


 
}