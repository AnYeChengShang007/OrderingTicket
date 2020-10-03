package com.epoint.service;

import java.util.List;

import com.epoint.dao.OrderInfoDao;
import com.epoint.domain.OrderInfo;

/**
 * @author 冯金星
 *
 */
public class OrderInfoService {

	OrderInfoDao dao = new OrderInfoDao();

	/**
	 * @param start
	 * @param size
	 * @return
	 */
	public List<OrderInfo> getList(int start, int size) {
		return dao.getList(start, size);
	}

	/**
	 * @return
	 */
	public int getTotal() {
		return dao.getTotal();
	}

	/**
	 * @param orderInfo
	 * @return
	 */
	public String save(OrderInfo orderInfo) {
		
		return dao.save(orderInfo);
	}

	/**
	 * @param id
	 * @return
	 */
	public OrderInfo getOne(String id) {
		return dao.getOne(id);
	}

	/**
	 * @param id
	 * @param ticketId 
	 * @return
	 */
	public String del(String id, String ticketId) {
		return dao.del(id,ticketId);
	}

}
