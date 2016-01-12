package com.rvidda.cn.domain;

import java.util.List;

public class ZXList {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getPhoto() {
		return photo;
	}

	public void setPhoto(List<String> photo) {
		this.photo = photo;
	}

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public List<String> getNumber() {
		return number;
	}

	public void setNumber(List<String> number) {
		this.number = number;
	}

	private String title;
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
    public List<String> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<String> subjectlist) {
		this.subjectlist = subjectlist;
	}

	private List<String> subjectlist;
	private String subject;
	private String time;
	private List<String> photo;
	private List<String> name;
	private List<String> number;
    public List<String> getHx_user() {
		return hx_user;
	}

	public void setHx_user(List<String> hx_user) {
		this.hx_user = hx_user;
	}

	private List<String> hx_user;
}
