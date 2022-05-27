package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.HouseDealDto;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.HouseLikeDto;
import com.ssafy.vue.dto.SidoGugunDongCodeDto;
import com.ssafy.vue.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private HouseMapMapper houseMapMapper;

	@Override
	public List<SidoGugunDongCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunDongCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<SidoGugunDongCodeDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return houseMapMapper.getAptInDong(dong);
	}
	
	@Override
	public List<HouseDealDto> getHouseDeal(String apt) throws Exception {
		return houseMapMapper.getHouseDeal(apt);
	}

	@Override
	public List<HouseLikeDto> getLikeHouse(String userid) throws Exception {
		return houseMapMapper.getLikeHouse(userid);
	}
	
	@Override
	public boolean registLikeHouse(HouseLikeDto houseLikeDto) throws Exception {
		System.out.println("registLikeHouse service");
		return houseMapMapper.registLikeHouse(houseLikeDto) == 1;
	}

	@Override
	public boolean deleteLikeHouse(HouseLikeDto houseLikeDto) throws Exception {
		System.out.println("deleteLikeHouse serivce");
		System.out.println(houseLikeDto.getAptCode());
		System.out.println(houseLikeDto.getUserid());
		return houseMapMapper.deleteLikeHouse(houseLikeDto) == 1;
	}

	@Override
	public HouseInfoDto getAptInfo(long aptCode) throws Exception {
		return houseMapMapper.getAptInfo(aptCode);
	}

}
