package com.epoint.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.epoint.domain.OrderInfo;
import com.epoint.util.DataSourceUtil;

/**
 * @author 冯金星
 *
 */
public class OrderInfoDao {

	/**
	 * @param start
	 * @param size
	 * @return
	 */
	public List<OrderInfo> getList(int start, int size) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		List<OrderInfo> res = new ArrayList<OrderInfo>();
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "SELECT * FROM ordertinfo where 1=1 order by ? desc limit ?,?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, "ordertime");
			prepareStatement.setObject(2, start * size);
			prepareStatement.setObject(3, size);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setId((String) rs.getObject("id"));
				orderInfo.setName((String) rs.getObject("name"));
				orderInfo.setChildnumber((Integer) rs.getObject("childnumber"));
				orderInfo.setHavechild((Integer) rs.getObject("havechild"));
				orderInfo.setOrdertime((Date) rs.getObject("ordertime"));
				orderInfo.setPayment((BigDecimal) rs.getObject("payment"));
				orderInfo.setPhone((String) rs.getObject("phone"));
				orderInfo.setTicketid((String) rs.getObject("ticketid"));
				orderInfo.setIdcard((String) rs.getObject("idcard"));
				res.add(orderInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @return
	 */
	public int getTotal() {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;
		try {
			int num = 1;
			connection = DataSourceUtil.getConnection();
			String sql = "select count(1) total from ordertinfo";
			prepareStatement = connection.prepareStatement(sql);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param orderInfo
	 * @return
	 */
	public String save(OrderInfo orderInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String res = "保存失败";
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			connection.setAutoCommit(false);
			String sql = "select remain from ticketinfo where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, orderInfo.getTicketid());
			rs = prepareStatement.executeQuery();
			int num = 0;
			if(rs.next()) {
				num = rs.getInt(1);
			}
			rs.close();
			prepareStatement.close();
			if(num<orderInfo.getChildnumber()+1) {
				res = "票数不够";
			}else {
				sql = "update ticketinfo set remain = remain-? where id = ?";
				prepareStatement = connection.prepareStatement(sql);
				prepareStatement.setObject(1, orderInfo.getChildnumber()+1);
				prepareStatement.setObject(2, orderInfo.getTicketid());
				int update = prepareStatement.executeUpdate();
				rs.close();
				prepareStatement.close();
				
				sql = "INSERT INTO ticketordering.ordertinfo " + 
						"(id, name, ticketid, havechild, childnumber, payment, ordertime, phone, idcard) " + 
						"VALUES(?, ?, ?, ?, ?, ?, ?,?, ?)";
				prepareStatement = connection.prepareStatement(sql);
				prepareStatement.setObject(1, orderInfo.getId());
				prepareStatement.setObject(2, orderInfo.getName());
				prepareStatement.setObject(3, orderInfo.getTicketid());
				prepareStatement.setObject(4, orderInfo.getHavechild());
				prepareStatement.setObject(5, orderInfo.getChildnumber());
				prepareStatement.setObject(6, orderInfo.getPayment());
				prepareStatement.setObject(7, orderInfo.getOrdertime());
				prepareStatement.setObject(8, orderInfo.getPhone());
				prepareStatement.setObject(9, orderInfo.getIdcard());
				update = prepareStatement.executeUpdate();
				if(update>0) {
					res = "保存成功";
				}
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param id
	 * @return
	 */
	public OrderInfo getOne(String id) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		OrderInfo res = new OrderInfo();
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select * from ordertinfo where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, id);
			rs = prepareStatement.executeQuery();
			if(rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setId((String) rs.getObject("id"));
				orderInfo.setName((String) rs.getObject("name"));
				orderInfo.setChildnumber((Integer) rs.getObject("childnumber"));
				orderInfo.setHavechild((Integer) rs.getObject("havechild"));
				orderInfo.setOrdertime((Date) rs.getObject("ordertime"));
				orderInfo.setPayment((BigDecimal) rs.getObject("payment"));
				orderInfo.setPhone((String) rs.getObject("phone"));
				orderInfo.setTicketid((String) rs.getObject("ticketid"));
				orderInfo.setIdcard((String) rs.getObject("idcard"));
				res = orderInfo;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param id
	 * @param ticketId 
	 * @return
	 */
	public String del(String id, String ticketId) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String res = "删除失败";
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			connection.setAutoCommit(false);
			String sql = "select starttime from ticketinfo where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, ticketId);
			rs = prepareStatement.executeQuery();
			Date begin = null;
			if(rs.next()) {
				begin = (Date) rs.getObject(1);
			}
			prepareStatement.close();
			System.out.println(begin+"22222222");
			if(begin!=null  && begin.getTime()>new Date().getTime()) {
				System.out.println(132456);
				sql = "select childnumber from ordertinfo where id = ?";
				prepareStatement = connection.prepareStatement(sql);
				prepareStatement.setObject(1, id);
				rs = prepareStatement.executeQuery();
				System.out.println(prepareStatement);
				int num = 0;
				if(rs.next()) {
					num = rs.getInt(1)+1;
				}
				prepareStatement.close();
				sql = "update ticketinfo set remain = remain +? where id = ?";
				prepareStatement = connection.prepareStatement(sql);
				prepareStatement.setObject(1, num);
				prepareStatement.setObject(2, ticketId);
				prepareStatement.executeUpdate();
				System.out.println(prepareStatement);
				prepareStatement.close();
			}
			
			sql = "delete from ordertinfo where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, id);
			int update = prepareStatement.executeUpdate();
			if(update>0) {
				res = "删除成功";
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

}
