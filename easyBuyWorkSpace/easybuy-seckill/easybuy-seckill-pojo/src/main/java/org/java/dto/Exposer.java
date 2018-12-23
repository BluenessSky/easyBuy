package org.java.dto;

import java.io.Serializable;

public class Exposer implements Serializable{
	/** 是否暴漏秒杀接口(true:暴漏，false:不暴露). */
	private boolean exporsed;
	/** 对接口进行md5加密.*/
	private String md5;
	/** 参与秒杀的商品编号.*/
	private long seckillId;
	/** 当前服务器的时间. */
	private long now;
	/** 秒杀开始时间. */
	private long start;
	/** 秒杀结束时间 .*/
	private long end;
	
	
	public Exposer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Exposer(boolean exporsed, long now, long start, long end) {
		super();
		this.exporsed = exporsed;
		this.now = now;
		this.start = start;
		this.end = end;
	}
	
	public Exposer(boolean exporsed, String md5, long seckillId) {
		super();
		this.exporsed = exporsed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}
	public boolean isExporsed() {
		return exporsed;
	}
	public void setExporsed(boolean exporsed) {
		this.exporsed = exporsed;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public long getNow() {
		return now;
	}
	public void setNow(long now) {
		this.now = now;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	
}
