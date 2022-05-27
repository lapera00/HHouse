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

import com.ssafy.vue.dto.BoardqnaDto;
import com.ssafy.vue.service.BoardqnaService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/qna")
public class BoardqnaController {

	private static final Logger logger = LoggerFactory.getLogger(BoardqnaController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardqnaService boardqnaService;

	@GetMapping
	public ResponseEntity<List<BoardqnaDto>> retrieveBoard() throws Exception {
		logger.debug("retrieveBoard - 호출");
		return new ResponseEntity<List<BoardqnaDto>>(boardqnaService.retrieveBoard(), HttpStatus.OK);
	}

	@GetMapping("{articleno}")
	public ResponseEntity<BoardqnaDto> detailBoard(@PathVariable int articleno) {
		logger.debug("detailBoard - 호출");
		return new ResponseEntity<BoardqnaDto>(boardqnaService.detailBoard(articleno), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> writeBoard(@RequestBody BoardqnaDto board) {
		logger.debug("writeBoard - 호출");
		if (boardqnaService.writeBoard(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@PutMapping("{articleno}")
	public ResponseEntity<String> updateBoard(@RequestBody BoardqnaDto board) {
		logger.debug("updateBoard - 호출");
		logger.debug("" + board);
		
		if (boardqnaService.updateBoard(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("{articleno}")
	public ResponseEntity<String> deleteBoard(@PathVariable int articleno) {
		logger.debug("deleteBoard - 호출");
		if (boardqnaService.deleteBoard(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}