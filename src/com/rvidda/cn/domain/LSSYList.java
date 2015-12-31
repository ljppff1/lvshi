package com.rvidda.cn.domain;

import java.util.List;

public class LSSYList {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWenti() {
		return wenti;
	}

	public void setWenti(String wenti) {
		this.wenti = wenti;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<String> getBiaoqian() {
		return biaoqian;
	}

	public void setBiaoqian(List<String> biaoqian) {
		this.biaoqian = biaoqian;
	}

	private String name;
	private String wenti;
	private String photo;
	private List<String> biaoqian;

}
