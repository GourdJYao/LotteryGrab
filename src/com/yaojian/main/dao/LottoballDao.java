package com.yaojian.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yaojian.main.dao.bean.Lottoball;
import com.yaojian.main.dao.utils.DBUtil;
import com.yaojian.main.log.LogUtils;

public class LottoballDao {
	public boolean insert(Lottoball lottoball) {
		DBUtil util = new DBUtil();
		Connection connetion = util.openConnection();
		String sql = "SELECT count(id) FROM lottolottery WHERE periods=?";
		PreparedStatement preparedStatement = null;
		ResultSet resulte = null;
		try {
			preparedStatement = connetion.prepareStatement(sql);
			preparedStatement.setString(1, lottoball.getPeriods());
			resulte = preparedStatement.executeQuery();
			if (resulte == null || !resulte.next()) {
				preparedStatement.close();
				// TODO 插入
				sql = "INSERT INTO lottolottery(redball_1,redball_2,redball_3,redball_4,redball_5,blueball_1,blueball_2,periods,lotterydate) VALUES (?,?,?,?,?,?,?,?,?)";
				preparedStatement = connetion.prepareStatement(sql);
				return insertData(preparedStatement, lottoball);
			} else {
				if (resulte.first()) {
					int countInt = resulte.getInt(1);
					if (countInt <= 0) {
						preparedStatement.close();
						sql = "INSERT INTO lottolottery(redball_1,redball_2,redball_3,redball_4,redball_5,blueball_1,blueball_2,periods,lotterydate) VALUES (?,?,?,?,?,?,?,?,?)";
						preparedStatement = connetion.prepareStatement(sql);
						return insertData(preparedStatement, lottoball);
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
			Lottoball lottoball) throws SQLException {
		preparedStatement.setInt(1, lottoball.getRedball_1());
		preparedStatement.setInt(2, lottoball.getRedball_2());
		preparedStatement.setInt(3, lottoball.getRedball_3());
		preparedStatement.setInt(4, lottoball.getRedball_4());
		preparedStatement.setInt(5, lottoball.getRedball_5());
		preparedStatement.setInt(6, lottoball.getBlueball_1());
		preparedStatement.setInt(7, lottoball.getBlueball_2());
		preparedStatement.setString(8, lottoball.getPeriods());
		preparedStatement.setDate(9, lottoball.getLotterydate());
		return preparedStatement.execute();
	}
}
