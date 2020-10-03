package com.epoint.domain;

import java.util.Date;

/**
 * @author 冯金星
 *
 */
public class TicketInfo {
	private String id;
	private String begin;
	private String end;
	private Date starttime;
	private int distance;
	private int time;
	private int price;
	private int remain;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getRemain() {
		return remain;
	}
	public void setRemain(int remain) {
		this.remain = remain;
	}
	@Override
	public String toString() {
		return "TicketInfo [id=" + id + ", begin=" + begin + ", end=" + end + ", starttime=" + starttime + ", distance="
				+ distance + ", time=" + time + ", price=" + price + ", remain=" + remain + "]";
	}
	
}
