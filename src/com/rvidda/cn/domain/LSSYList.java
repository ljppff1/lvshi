package com.rvidda.cn.domain;

import java.util.List;

import android.text.Spannable;

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
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    public String getHx_user() {
		return hx_user;
	}

	public void setHx_user(String hx_user) {
		this.hx_user = hx_user;
	}

	private String hx_user;
    public String getSubject_lawyer_id() {
		return subject_lawyer_id;
	}

	public void setSubject_lawyer_id(String subject_lawyer_id) {
		this.subject_lawyer_id = subject_lawyer_id;
	}

	private String subject_lawyer_id;
	private String userid;
	private String wenti;
	private String photo;
	private List<String> biaoqian;
    public Spannable getSp() {
		return sp;
	}

	public void setSp(Spannable sp) {
		this.sp = sp;
	}

	private Spannable sp;
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
    public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	private String subject;
	private String body;
	private String number;
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private String time;
	
	
	
}
