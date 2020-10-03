package com.epoint.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.epoint.domain.OrderInfo;
import com.epoint.service.OrderInfoService;

/**
 * 
 * @author 冯金星
 *
 */
@WebServlet("/orderinfolist")
public class OrderInfoListAction extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderInfoService service = new OrderInfoService();
		String method = request.getParameter("method");
		
		if (!StringUtils.isEmpty(method) && method.equals("getone")) {
			String id = request.getParameter("id");
			OrderInfo orderInfo = service.getOne(id);
			String res = JSON.toJSONStringWithDateFormat(orderInfo, "yyyy-MM-dd hh:mm:ss");
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("del")) {
			String id = request.getParameter("id");
			String ticketId = request.getParameter("ticketid");
			OrderInfo orderInfo = service.getOne(id);
			String res = service.del(id,ticketId);
			response.getWriter().write(res);
			return;
		}
		
		if (!StringUtils.isEmpty(method) && method.equals("save")) {
			String data = request.getParameter("data");
			OrderInfo orderInfo = JSON.parseObject(data,OrderInfo.class);
			if(orderInfo.getHavechild()==0) {
				orderInfo.setChildnumber(0);
			}
			orderInfo.setId(UUID.randomUUID().toString());
			String res = service.save(orderInfo);
			response.getWriter().write(res);
			return;
		}
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");
		int start = Integer.parseInt(pageIndex);
		int size = Integer.parseInt(pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrderInfo> list = service.getList(start,size);
		int total = service.getTotal();
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