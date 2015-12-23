package com.rvidda.cn.domain;

public class News {

	    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public long getDay() {
		return day;
	}
	public void setDay(long day) {
		this.day = day;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
		public News(Long id, String thumbnail, String title, String author,
			String summary, long day, String content, long sid) {
		super();
		this.id = id;
		this.thumbnail = thumbnail;
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.day = day;
		this.content = content;
		this.sid = sid;
	}
		private Long id;
	    private String thumbnail;
	    private String title;
	    private String author;
	    private String summary;
	    private long day;
	    private String content;
	    private long sid;

}
