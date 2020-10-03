package com.epoint.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 冯金星
 *
 */
public class OrderInfo {
	private String id;
	private String name;
	private String ticketid;
	private Integer havechild;
	private Integer childnumber;
	private BigDecimal payment;
	private Date ordertime;
	private String phone;
	private String idcard;
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
	public String getTicketid() {
		return ticketid;
	}
	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}
	public Integer getHavechild() {
		return havechild;
	}
	public void setHavechild(Integer havechild) {
		this.havechild = havechild;
	}
	public Integer getChildnumber() {
		return childnumber;
	}
	public void setChildnumber(Integer childnumber) {
		this.childnumber = childnumber;
	}
	public BigDecimal getPayment() {
		return payment;
	}
	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", name=" + name + ", ticketid=" + ticketid + ", havechild=" + havechild
				+ ", childnumber=" + childnumber + ", payment=" + payment + ", ordertime=" + ordertime + ", phone="
				+ phone + ", idcard=" + idcard + "]";
	}
	
}
