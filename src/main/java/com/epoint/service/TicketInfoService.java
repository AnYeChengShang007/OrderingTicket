package com.epoint.service;

import java.util.List;

import com.epoint.dao.TicketInfoDao;
import com.epoint.domain.TicketInfo;

/**
 * @author 冯金星
 *
 */
public class TicketInfoService {
	
	TicketInfoDao dao = new TicketInfoDao();

	/**
	 * 分页查询车票列表
	 * @param begin
	 * @param end
	 * @param start
	 * @param startTime
	 * @param size
	 * @return
	 */
	public List<TicketInfo> getList(String begin, String end, int start, String startTime, int size) {
		return dao.getList(begin,end,start,startTime,size);
	}

	/**
	 * @param begin
	 * @param end
	 * @param startTime
	 * @return
	 */
	public int getTotal(String begin, String end, String startTime) {
		return dao.getTotal(begin,end,startTime);
	}

	/**
	 * @param id
	 * @return
	 */
	public String checkId(String id) {
		return dao.checkId(id);
	}

	/**
	 * @param ticketInfo
	 * @return
	 */
	public String save(TicketInfo ticketInfo) {
		int num = dao.save(ticketInfo);
		if(num>0) {
			return "添加成功";
		}
		return "添加失败";
	}

	/**
	 * @param id
	 * @return
	 */
	public TicketInfo getOne(String id) {
		return dao.getOne(id);
	}

	/**
	 * @return
	 */
	public List<TicketInfo> getCheckedList() {
		return dao.getCheckedList();
	}

	/**
	 * @param ticketInfo
	 * @return
	 */
	public String update(TicketInfo ticketInfo) {
		return dao.update(ticketInfo);
	}

	/**
	 * @param id
	 * @return
	 */
	public String del(String id) {
		return dao.del(id);
	}

}
