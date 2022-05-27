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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.HouseDealDto;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.HouseLikeDto;
import com.ssafy.vue.dto.SidoGugunDongCodeDto;
import com.ssafy.vue.service.HouseMapService;

@RestController
@RequestMapping("/map")
@CrossOrigin("*")
public class HouseMapController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private HouseMapService haHouseMapService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunDongCodeDto>> sido() throws Exception {
		logger.debug("sido : {}", haHouseMapService.getSido());
		return new ResponseEntity<List<SidoGugunDongCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunDongCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		logger.debug("gugun : {}", haHouseMapService.getGugunInSido(sido));
		return new ResponseEntity<List<SidoGugunDongCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<SidoGugunDongCodeDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		
		return new ResponseEntity<List<SidoGugunDongCodeDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception {
		
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong), HttpStatus.OK);
	}
	
	@GetMapping("/deal")
	public ResponseEntity<List<HouseDealDto>> deal(@RequestParam("apt") String apt) throws Exception {
		logger.debug("deal - 호출");
		return new ResponseEntity<List<HouseDealDto>>(haHouseMapService.getHouseDeal(apt), HttpStatus.OK);
	}
	
	@GetMapping("/like")
	public ResponseEntity<List<HouseLikeDto>> getLikeHouse(@RequestParam("userid") String userid ) throws Exception{
		logger.debug("getLikeHouse - 호출");
		return new ResponseEntity<List<HouseLikeDto>> (haHouseMapService.getLikeHouse(userid),HttpStatus.OK);
	}
	
	@PostMapping("/like")
	public ResponseEntity<String> registLikeHouse(@RequestBody HouseLikeDto houseLikeDto) throws Exception{
		logger.debug("registLikeHouse - 호출");
		System.out.println(houseLikeDto.getAptCode());
		System.out.println(houseLikeDto.getUserid());
		if(haHouseMapService.registLikeHouse(houseLikeDto)) {
			return new ResponseEntity<String>(SUCCESS ,HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	@DeleteMapping("/like")
	public ResponseEntity<String> deleteLikeHouse(@RequestParam("userid") String userid, @RequestParam("aptCode") long aptCode) throws Exception{
		logger.debug("deleteLikeHouse - 호출");
		HouseLikeDto houseLikeDto = new HouseLikeDto();
		houseLikeDto.setAptCode(aptCode);
		houseLikeDto.setUserid(userid);
		if(haHouseMapService.deleteLikeHouse(houseLikeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String> (FAIL,HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/aptinfo")
		public ResponseEntity<HouseInfoDto> getAptInfo(@RequestParam("aptCode") long aptCode) throws Exception {
		return new ResponseEntity<HouseInfoDto>(haHouseMapService.getAptInfo(aptCode), HttpStatus.OK);
	}
	
}
