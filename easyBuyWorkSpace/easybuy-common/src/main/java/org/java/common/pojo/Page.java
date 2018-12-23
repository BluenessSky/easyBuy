package org.java.common.pojo;

import java.io.Serializable;

public class Page implements Serializable{
	
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Page(Integer pageCount, String url, Integer currentPage, Long count) {
		super();
		this.pageCount = pageCount;
		this.url = url;
		this.currentPage = currentPage;
		this.count = count;
	}

	/**
	 * 总页数
	 */
	private Integer pageCount;
	/***
	 * 请求分页url地址
	 */
	private String url;
	/**
	 * 当前页数
	 */
	private Integer currentPage;
	/**
	 * 总条数
	 */
	private Long count;
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}
