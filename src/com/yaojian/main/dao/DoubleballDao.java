package com.yaojian.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yaojian.main.dao.bean.Doubleball;
import com.yaojian.main.dao.utils.DBUtil;
import com.yaojian.main.log.LogUtils;

public class DoubleballDao {
	public boolean insert(Doubleball redball) {
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT count(id) FROM doublelottery WHERE periods=?";
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			preparedStatement.setString(1, redball.getPeriods());
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				preparedStatement.close();
				// TODO 插入
				sql = "INSERT INTO doublelottery(redball_1,redball_2,redball_3,redball_4,redball_5,redball_6,blueball_1,periods,lotterydate) VALUES (?,?,?,?,?,?,?,?,?)";
				preparedStatement = connetion.prepareStatement(sql);
				return insertData(preparedStatement, redball);
			} else {
				if (resulte.first()) {
					int countInt = resulte.getInt(1);
					if (countInt <= 0) {
						preparedStatement.close();
						sql = "INSERT INTO doublelottery(redball_1,redball_2,redball_3,redball_4,redball_5,redball_6,blueball_1,periods,lotterydate) VALUES (?,?,?,?,?,?,?,?,?)";
						preparedStatement = connetion.prepareStatement(sql);
						return insertData(preparedStatement, redball);
					}
				}
			}
		} catch (SQLException e) {
			LogUtils.e("SQLException:", e);
			e.printStackTrace();
		} finally {
			try {
				if (resulte != null) {
					resulte.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				util.closeConn(connetion);
			} catch (Exception e) {
				LogUtils.e("Exception:", e);
				e.printStackTrace();
			}
		}

		return false;
	}

	private boolean insertData(PreparedStatement preparedStatement,
			Doubleball redball) throws SQLException {
		preparedStatement.setInt(1, redball.getRedball_1());
		preparedStatement.setInt(2, redball.getRedball_2());
		preparedStatement.setInt(3, redball.getRedball_3());
		preparedStatement.setInt(4, redball.getRedball_4());
		preparedStatement.setInt(5, redball.getRedball_5());
		preparedStatement.setInt(6, redball.getRedball_6());
		preparedStatement.setInt(7, redball.getBlueball_1());
		preparedStatement.setString(8, redball.getPeriods());
		preparedStatement.setDate(9, redball.getLotterydate());
		return preparedStatement.execute();
	}
}
