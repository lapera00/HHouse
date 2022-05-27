package com.ssafy.vue.dto;

public class addressDto {
	String sidoName;
	String gugunName;
	String dongName;
	String dongCode;
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public addressDto(String no, String sidoName, String gugunName, String dongName, String dongCode, String lat,
			String lng) {
		super();
		this.sidoName = sidoName;
		this.gugunName = gugunName;
		this.dongName = dongName;
		this.dongCode = dongCode;
	}

}
