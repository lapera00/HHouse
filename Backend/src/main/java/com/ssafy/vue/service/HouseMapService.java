package com.ssafy.vue.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.dto.HouseDealDto;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.HouseLikeDto;
import com.ssafy.vue.dto.SidoGugunDongCodeDto;

public interface HouseMapService {

	List<SidoGugunDongCodeDto> getSido() throws Exception;
	List<SidoGugunDongCodeDto> getGugunInSido(String sido) throws Exception;
	List<SidoGugunDongCodeDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	List<HouseDealDto> getHouseDeal(String apt) throws Exception;
	List<HouseLikeDto> getLikeHouse(String userid)throws Exception;
	boolean registLikeHouse(HouseLikeDto houseLikeDto) throws Exception;
	boolean deleteLikeHouse(HouseLikeDto houseLikeDto)throws Exception;
	HouseInfoDto getAptInfo(long aptCode) throws Exception;
}
