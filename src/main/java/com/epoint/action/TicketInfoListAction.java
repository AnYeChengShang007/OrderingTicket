package com.epoint.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.epoint.domain.TicketInfo;
import com.epoint.service.TicketInfoService;

/**
 * 
 * @author 冯金星
 *
 */
@WebServlet("/ticketinfolist")
public class TicketInfoListAction extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TicketInfoService service = new TicketInfoService();
		String method = request.getParameter("method");
		if (!StringUtils.isEmpty(method) && method.equals("checkId")) {
			String id = request.getParameter("id");
			String res = service.checkId(id);
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("save")) {
			String data = request.getParameter("data");
			TicketInfo ticketInfo = JSON.parseObject(data,TicketInfo.class);
			String res = service.save(ticketInfo);
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("update")) {
			String data = request.getParameter("data");
			TicketInfo ticketInfo = JSON.parseObject(data,TicketInfo.class);
			String res = service.update(ticketInfo);
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("del")) {
			String id = request.getParameter("id");
			String res = service.del(id);
			response.getWriter().write(res);
			return;
		}
		
		
		if (!StringUtils.isEmpty(method) && method.equals("getone")) {
			String id = request.getParameter("id");
			TicketInfo ticketInfo = service.getOne(id);
			
			String res = JSON.toJSONStringWithDateFormat(ticketInfo, "yyyy-MM-dd hh:mm");
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("getchecklist")) {
			List<TicketInfo> list = service.getCheckedList();
			String res = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd hh:mm");
			response.getWriter().write(res);
			return;
		}
		
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		String startTime = request.getParameter("starttime");
		int total = service.getTotal(begin,end,startTime);
		int start = Integer.parseInt(pageIndex);
		int size = Integer.parseInt(pageSize);
		List<TicketInfo> list = service.getList(begin,end,start,startTime,size);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", list);
		map.put("total", total);
		String res = JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd hh:mm:ss");
		response.getWriter().write(res);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}