package com.rvidda.cn.domain;

import java.util.List;

public class Province {

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String id;
	private String name;
	public List<City> getList_city() {
		return list_city;
	}
	public void setList_city(List<City> list_city) {
		this.list_city = list_city;
	}
	private List<City> list_city;
}
