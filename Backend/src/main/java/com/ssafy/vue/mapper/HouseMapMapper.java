package com.ssafy.vue.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.dto.HouseDealDto;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.HouseLikeDto;
import com.ssafy.vue.dto.SidoGugunDongCodeDto;

public interface HouseMapMapper {

	List<SidoGugunDongCodeDto> getSido() throws SQLException;
	List<SidoGugunDongCodeDto> getGugunInSido(String sido) throws SQLException;
	List<SidoGugunDongCodeDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	List<HouseDealDto> getHouseDeal(String apt) throws SQLException;
	List<HouseLikeDto> getLikeHouse(String userid)throws SQLException;
	int registLikeHouse(HouseLikeDto houseLikeDto) throws SQLException;
	int deleteLikeHouse(HouseLikeDto houseLikeDto) throws SQLException;
	HouseInfoDto getAptInfo(long aptCode) throws SQLException;
}
