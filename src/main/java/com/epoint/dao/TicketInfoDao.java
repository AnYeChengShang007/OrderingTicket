package com.epoint.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.epoint.domain.TicketInfo;
import com.epoint.util.DataSourceUtil;

/**
 * @author 冯金星
 *
 */
public class TicketInfoDao {

	/**
	 * 分页查询车票列表
	 * 
	 * @param begin
	 * @param end
	 * @param start
	 * @param startTime
	 * @param size
	 * @return
	 */
	public List<TicketInfo> getList(String begin, String end, int start, String startTime, int size) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		List<TicketInfo> res = new ArrayList<TicketInfo>();
		ResultSet rs = null;
		try {
			int num = 1;
			connection = DataSourceUtil.getConnection();
			String sql = "select * from ticketinfo where 1=1";
			if (!StringUtils.isEmpty(begin)) {
				sql += " and begin like concat(\'%\',?,\'%\') ";
			}
			if (!StringUtils.isEmpty(end)) {
				sql += " and end like concat(\'%\',?,\'%\') ";
			}
			if (!StringUtils.isEmpty(startTime)) {
				sql += " and DATE_FORMAT(starttime,\'%Y-%m-%d\') = ? ";
			}
			sql += " limit ?,?";
			prepareStatement = connection.prepareStatement(sql);
			if (!StringUtils.isEmpty(begin)) {
				prepareStatement.setObject(num++, begin);
			}
			if (!StringUtils.isEmpty(end)) {
				prepareStatement.setObject(num++, end);
			}
			if (!StringUtils.isEmpty(startTime)) {
				prepareStatement.setObject(num++, startTime);
			}
			prepareStatement.setObject(num++, start * size);
			prepareStatement.setObject(num++, size);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				TicketInfo ticketInfo = new TicketInfo();
				ticketInfo.setBegin((String) rs.getObject("begin"));
				ticketInfo.setDistance((int) rs.getObject("distance"));
				ticketInfo.setEnd((String) rs.getObject("end"));
				ticketInfo.setId((String) rs.getObject("id"));
				ticketInfo.setPrice((int) rs.getObject("price"));
				ticketInfo.setRemain((int) rs.getObject("remain"));
				ticketInfo.setStarttime((Date) rs.getObject("starttime"));
				ticketInfo.setTime((int) rs.getObject("time"));
				res.add(ticketInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param begin
	 * @param end
	 * @param startTime
	 * @return
	 */
	public int getTotal(String begin, String end, String startTime) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;
		try {
			int num = 1;
			connection = DataSourceUtil.getConnection();
			String sql = "select count(1) total from ticketinfo where 1=1";
			if (!StringUtils.isEmpty(begin)) {
				sql += " and begin like concat(\'%\',?,\'%\') ";
			}
			if (!StringUtils.isEmpty(end)) {
				sql += " and end like concat(\'%\',?,\'%\') ";
			}
			if (!StringUtils.isEmpty(startTime)) {
				sql += " and starttime = ? ";
			}
			prepareStatement = connection.prepareStatement(sql);
			if (!StringUtils.isEmpty(begin)) {
				prepareStatement.setObject(num++, begin);
			}
			if (!StringUtils.isEmpty(end)) {
				prepareStatement.setObject(num++, end);
			}
			if (!StringUtils.isEmpty(startTime)) {
				prepareStatement.setObject(num++, startTime);
			}
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
	 * @param id
	 * @return
	 */
	public String checkId(String id) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String res = "true";
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select * from ticketinfo where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, id);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				res = "false";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param ticketInfo
	 * @return
	 */
	public int save(TicketInfo ticketInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int res = 0;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "INSERT INTO ticketordering.ticketinfo "
					+ "(id, begin, end, starttime, distance, time, price, remain) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, ticketInfo.getId());
			prepareStatement.setObject(2, ticketInfo.getBegin());
			prepareStatement.setObject(3, ticketInfo.getEnd());
			prepareStatement.setObject(4, ticketInfo.getStarttime());
			prepareStatement.setObject(5, ticketInfo.getDistance());
			prepareStatement.setObject(6, ticketInfo.getTime());
			prepareStatement.setObject(7, ticketInfo.getPrice());
			prepareStatement.setObject(8, ticketInfo.getRemain());
			res = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param id
	 * @return
	 */
	public TicketInfo getOne(String id) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		TicketInfo res = null;
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "SELECT * " + "FROM ticketordering.ticketinfo where id=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, id);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				TicketInfo ticketInfo = new TicketInfo();
				ticketInfo.setBegin((String) rs.getObject("begin"));
				ticketInfo.setDistance((int) rs.getObject("distance"));
				ticketInfo.setEnd((String) rs.getObject("end"));
				ticketInfo.setId((String) rs.getObject("id"));
				ticketInfo.setPrice((int) rs.getObject("price"));
				ticketInfo.setRemain((int) rs.getObject("remain"));
				ticketInfo.setStarttime((Date) rs.getObject("starttime"));
				ticketInfo.setTime((int) rs.getObject("time"));
				res = ticketInfo;
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
	public List<TicketInfo> getCheckedList() {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		List<TicketInfo> res = new ArrayList<>();
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "select * from ticketinfo where DATE_FORMAT(starttime,\'%Y-%m-%d\') > DATE_FORMAT(now(),\'%Y-%m-%d\')";
			prepareStatement = connection.prepareStatement(sql);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				TicketInfo ticketInfo = new TicketInfo();
				ticketInfo.setBegin((String) rs.getObject("begin"));
				ticketInfo.setDistance((int) rs.getObject("distance"));
				ticketInfo.setEnd((String) rs.getObject("end"));
				ticketInfo.setId((String) rs.getObject("id"));
				ticketInfo.setPrice((int) rs.getObject("price"));
				ticketInfo.setRemain((int) rs.getObject("remain"));
				ticketInfo.setStarttime((Date) rs.getObject("starttime"));
				ticketInfo.setTime((int) rs.getObject("time"));
				res.add(ticketInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtil.close(connection, prepareStatement, rs);
		}

		return res;
	}

	/**
	 * @param ticketInfo
	 * @return
	 */
	public String update(TicketInfo ticketInfo) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String res = "修改失败";
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			String sql = "update ticketinfo set distance=?,time=?,price=? where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, ticketInfo.getDistance());
			prepareStatement.setObject(2, ticketInfo.getTime());
			prepareStatement.setObject(3, ticketInfo.getPrice());
			prepareStatement.setObject(4, ticketInfo.getId());
			int num = prepareStatement.executeUpdate();
			if (num > 0) {
				res = "修改成功";
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
	 * @return
	 */
	public String del(String id) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String res = "删除失败";
		ResultSet rs = null;

		try {
			connection = DataSourceUtil.getConnection();
			connection.setAutoCommit(false);
			String sql = "delete from ticketinfo where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, id);
			int num = prepareStatement.executeUpdate();
			prepareStatement.close();
			sql = "delete from ordertinfo where ticketid = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, id);
			num = prepareStatement.executeUpdate();
			res = "删除成功";
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
