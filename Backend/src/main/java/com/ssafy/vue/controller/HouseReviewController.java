package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.Board;
import com.ssafy.vue.dto.CommentDto;
import com.ssafy.vue.dto.HouseReviewDto;
import com.ssafy.vue.service.BoardService;
import com.ssafy.vue.service.CommentService;
import com.ssafy.vue.service.HouseReviewService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/housereview")
public class HouseReviewController {

	private static final Logger logger = LoggerFactory.getLogger(HouseReviewController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private HouseReviewService houseReviewService;

	@GetMapping("{aptcode}")
	public ResponseEntity<List<HouseReviewDto>> getReview(@PathVariable String aptcode) {
		logger.debug("getreview - 호출");
		return new ResponseEntity<List<HouseReviewDto>>(houseReviewService.getReview(aptcode), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> insertReview(@RequestBody HouseReviewDto houseReviewDto){
		if(houseReviewService.insertReview(houseReviewDto) > 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("{commentno}")
	public ResponseEntity<String> deleteReview(@PathVariable String commentno){
		if(houseReviewService.deleteReview(commentno) > 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
//	
//	@GetMapping("/count/{articleno}")
//	public ResponseEntity<Integer> commentcount(@PathVariable int articleno) {
//		return new ResponseEntity<Integer>(commentService.countComment(articleno), HttpStatus.OK);
//	}
	

}